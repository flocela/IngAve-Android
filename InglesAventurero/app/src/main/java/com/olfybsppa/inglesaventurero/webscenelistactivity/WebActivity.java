package com.olfybsppa.inglesaventurero.webscenelistactivity;


import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.collectors.SceneInfo;
import com.olfybsppa.inglesaventurero.dialogs.DialogDoneListener;
import com.olfybsppa.inglesaventurero.dialogs.ExceptionDialog;
import com.olfybsppa.inglesaventurero.dialogs.ProgressDialogFragment;
import com.olfybsppa.inglesaventurero.listeners.UsesListFragment;
import com.olfybsppa.inglesaventurero.listeners.WorkProgressListener;
import com.olfybsppa.inglesaventurero.openingactivity.NewDataHerald;
import com.olfybsppa.inglesaventurero.openingactivity.SceneListFragment;
import com.olfybsppa.inglesaventurero.start.SettingsActivity;
import com.olfybsppa.inglesaventurero.start.SuperFragmentActivity;
import com.olfybsppa.inglesaventurero.utils.Ez;
import com.olfybsppa.inglesaventurero.utils.MaP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.dialog.HelpDialog;
import com.olfybsppa.inglesaventurero.webscenelistactivity.dialog.NoConnectionDialog;

import java.util.ArrayList;

public class WebActivity extends SuperFragmentActivity
                         implements UsesListFragment,
                                    SceneListFragment.IdentifyableItemListener<WebTitleView>,
                                    ExceptionHandler.Listener,
                                    WorkProgressListener,
                                    DialogDoneListener,
                                    NewDataHerald.Listener {

  private String urlString  = "http://www.appsbyflo.com/ingles_aventurero/scenes/";
  public static final String    SCENE_LIST_TAG    = "SCENE_LIST_TAG";
  public static final String    SCENE_BUILDER_TAG = "SCENE_BUILDER";
  protected static final String LIST_PROGRESS_DIALOG_TAG  = "LIST_PROGRESS_DIALOG_TAG";
  protected static final String SCENE_PROGRESS_DIALOG_TAG = "SCENE_PROGRESS_DIALOG_TAG";
  private static final String   NO_CONNECTION_DIALOG_TAG  = "NO_CONNECTION_DIALOG_TAG";
  private int NO_CONNECTION_REQUEST_CODE = 33;

  private ExceptionHandler exceptionHandler;
  private DownloadingProgressHandler progressHandler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.list_activity);
    initColors();
    initializeToolbar();
    exceptionHandler = new ExceptionHandler(this);
    progressHandler = new DownloadingProgressHandler();
  }

  @Override
  public void onResume() {
    super.onResume();
    if (serviceIsConnected()) {
      Fragment listFragment = new SceneListFragment();
      addFragmentIfNotAddedASL(listFragment, SCENE_LIST_TAG, R.id.frame_for_scene_list);
      setListProgressDialog(getRString(R.string.downloadinglist));
    }
    else {
      setNoConnectionDialog();
    }
    //FOR TESTING
    /*Bundle bundle = Ez.oneStringBundle(ExceptionDialog.MESSAGE_ENG, "Jack and Jill went up the hill to fetch a pail of water.");
    bundle.putString(ExceptionDialog.MESSAGE_SPN, "Jack and Jill in spanish went up the hill to fetch a pail of water");
    showIOExceptionDialog(bundle);*/
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.web_activity_actions, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_help:
        HelpDialog helpDialog = HelpDialog.newInstance();
        showDialogFragmentASL(helpDialog);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onDestroy () {
    super.onDestroy();
    synchronized (this) {
      if (null != progressHandler) {
        progressHandler.removeCallbacksAndMessages(null);
        progressHandler = null;
      }
    }
    synchronized (LIST_PROGRESS_DIALOG_TAG) {
      if (null != exceptionHandler) {
        exceptionHandler.removeCallbacksAndMessages(null);
        exceptionHandler = null;
      }
    }
  }

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return true;
  }

  @Override
  public void errorGettingData (Bundle bundle) {
    synchronized (this) {
      if (null != progressHandler)
        progressHandler.closeProgressDialogForList();
    }
    if (bundle != null && bundle.containsKey(CallBacksReporter.ERROR)) {
      noticeExceptionOccurred(bundle.getString(CallBacksReporter.ERROR)); //TODO CHECK THIS
    }
  }

  @Override
  public void gotsNewData(int numOfDataPoints) {
    synchronized (this) {
      if (null != progressHandler)
        progressHandler.closeProgressDialogForList();
    }
    if (numOfDataPoints != 0) {
      SharedPreferences preferences =
        PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
      java.util.Map map = preferences.getAll();
      Boolean difficultyToast = (Boolean)map.get(SettingsActivity.WEB_ACTIVITY_TOAST);
      if (difficultyToast) {
        Toast toast = Toast.makeText(this,
          getRString(R.string.difficulty_instructions_spn),
          Toast.LENGTH_LONG);
        ((TextView)((ViewGroup)toast.getView()).getChildAt(0)).setTextSize(20);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        SharedPreferences.Editor prefEditor = preferences.edit();
        prefEditor.putBoolean(SettingsActivity.WEB_ACTIVITY_TOAST, false);
        prefEditor.commit();
      }
    }
  }

  @Override
  public void initializeList(String tag) {
    WebListAdapter adapter =
      new WebListAdapter(this, R.layout.web_scene_title, new ArrayList<SceneInfo>());
    ListLoaderWatcher watcher = new ListLoaderWatcher(this, urlString, adapter);
    watcher.addListener(this);
    FragmentManager fragmentManager = getSupportFragmentManager();
    ListFragment listFragment = (ListFragment)fragmentManager.findFragmentByTag(tag);
    listFragment.setListAdapter(adapter);
    getSupportLoaderManager().initLoader(0, null, watcher);
    ListView listView = listFragment.getListView();
    if (listView != null) {
      ColorDrawable colorDrawable =
        new ColorDrawable(ContextCompat.getColor(this,R.color.webListItemBg));
      listView.setDivider(colorDrawable);
      listView.setDividerHeight(1);
    }
  }

  @Override
  protected void initStartingVariables(Bundle args) {}

  @Override
  protected void initView() {}

  @Override
  public void itemClicked(WebTitleView view, int id) {
    SceneInfo sceneInfo = new SceneInfo();
    sceneInfo.setSceneName(view.getSceneName());
    sceneInfo.setEnglishTitle(view.getEnglishTitle());
    sceneInfo.setDifficulty(""+view.getDifficulty());
    sceneInfo.setSpanishTitle(view.getSpanishTitle());
    sceneInfo.setWebId(view.getIdentifier());
    makeScene(sceneInfo);
  }

  @Override
  public void notifyDialogDone (int requestCode,
                                boolean isCancelled,
                                Bundle returnedBundle) {
    if (requestCode == NO_CONNECTION_REQUEST_CODE) finish();
  }

  //Regularly called on non-ui thread.
  @Override
  public void notifyProgress(String tag,
                             boolean isCancelled,
                             int percentDone,
                             String info) {
    if (percentDone != -1 && !isCancelled && SCENE_BUILDER_TAG == tag) {
      if (percentDone == 100) {
        showNotification(getRString(R.string.hundredPercent), info, R.drawable.ic_all_done);
        synchronized (this) {
          if (null != progressHandler)
            progressHandler.updateProgressDialogForScene(100, info);
        }
      }
      else {
        synchronized (this) {
          if (null != progressHandler)
            progressHandler.updateProgressDialogForScene(percentDone, info);
        }
      }
    }
    else if (percentDone == -1) {
      synchronized (this) {
        if (null != progressHandler)
          progressHandler.downloadingSceneException(info);
      }
    }
  }

  @Override
  public void showExceptionDialog(Bundle errorBundle) {
    showDialogFragmentASL(ExceptionDialog.newInstance(errorBundle));
  }

  @Override
  public void showIOExceptionDialog(Bundle errorBundle) {
    showDialogFragmentASL(ExceptionDialog.newInstance(errorBundle));
  }

  public void stopDownloadingScene () {
    WebToCPFragment webToCPFragment = (WebToCPFragment) findFragByTag(SCENE_BUILDER_TAG);
    if (webToCPFragment != null)
      webToCPFragment.cancelWork();
  }

  public class DownloadingProgressHandler extends Handler {
    public String DOWNLOADING_SCENE           = "DOWNLOADING_SCENE";
    public String DOWNLOADING_LIST            = "DOWNLOADING_LIST";
    public String LIST_COMPLETE               = "LIST_COMPLETE";
    public String PERCENT_DONE                = "PERCENT_DONE";
    public String DOWNLOADING_SCENE_EXCEPTION = "DOWNLOADING_SCENE_EXCEPTION";
    public String DOWNLOADING_LIST_EXCEPTION  = "DOWNLOADING_LIST_EXCEPTION";

    @Override
    public void handleMessage (Message msg) {
      Bundle bundle = msg.getData();

      if (bundle.containsKey(DOWNLOADING_LIST)) {
        setListProgressDialog(bundle.getString(DOWNLOADING_LIST));
      }
      else if (bundle.containsKey(LIST_COMPLETE)) {
        closeDownloadingProgressDialog(LIST_PROGRESS_DIALOG_TAG);
      }
      else if (bundle.containsKey(DOWNLOADING_SCENE)) {
        ProgressDialogFragment progFragment =
          (ProgressDialogFragment) findFragByTag(SCENE_PROGRESS_DIALOG_TAG);
        int percentDone = bundle.getInt(PERCENT_DONE);
        String songTitle = bundle.getString(DOWNLOADING_SCENE);
        if (percentDone == 0)
          startDownloadingSceneProgressDialog(songTitle);
        else if (progFragment != null && percentDone > 0 && percentDone < 100) {
          progFragment.setMessage(downloadingString(songTitle, percentDone));
        }
        else if (percentDone == 100) {
          closeDownloadingProgressDialog(SCENE_PROGRESS_DIALOG_TAG);
          finish();
        }
      }
      else if (bundle.containsKey(DOWNLOADING_SCENE_EXCEPTION)) {
        closeDownloadingProgressDialog(SCENE_PROGRESS_DIALOG_TAG);
        synchronized (LIST_PROGRESS_DIALOG_TAG) {
          if (null != exceptionHandler)
            exceptionHandler.exceptionOccurred(bundle.getString(DOWNLOADING_SCENE_EXCEPTION));
        }
      }
      else if (bundle.containsKey(DOWNLOADING_LIST_EXCEPTION)) {
        closeDownloadingProgressDialog(LIST_PROGRESS_DIALOG_TAG);
        synchronized (LIST_PROGRESS_DIALOG_TAG) {
          if (null != exceptionHandler)
            exceptionHandler.exceptionOccurred(bundle.getString(DOWNLOADING_LIST_EXCEPTION));
        }
      }
    }

    public boolean closeProgressDialogForList () {
      Message message = this.obtainMessage();
      Bundle bundle = Ez.oneIntBundle(LIST_COMPLETE, 100);
      message.setData(bundle);
      return this.sendMessage(message);
    }

    public boolean updateProgressDialogForScene(int percentDone, String sceneName) {
      Bundle bundle = Ez.oneStringBundle(DOWNLOADING_SCENE, sceneName);
      bundle.putInt(PERCENT_DONE, percentDone);
      Message message = this.obtainMessage();
      message.setData(bundle);
      return this.sendMessage(message);
    }

    public boolean downloadingSceneException (String info) {
      Message message = this.obtainMessage();
      Bundle bundle = Ez.oneStringBundle(DOWNLOADING_SCENE_EXCEPTION, info);
      message.setData(bundle);
      return this.sendMessage(message);
    }
  }

  private void closeDownloadingProgressDialog(String TAG) {
    Fragment progFragment = findFragByTag(TAG);
    if (progFragment != null)
      if (progFragment instanceof ProgressDialogFragment) {
        removeFragmentASL(TAG);
      }
  }

  private String downloadingString (String songTitle, int percent) {
    if (songTitle.endsWith("null")) {
      songTitle = songTitle.substring(0, songTitle.length() - 4);
    }
    StringBuilder builder =
      new StringBuilder(getRString(R.string.downloadingscene));
    builder.append(" ")
      .append(songTitle)
      .append(".");
    if (percent == 25) {
      builder.append("\n");
      builder.append(getRString(R.string.twentyfivePercent));
    }
    else if (percent == 50)  {
      builder.append("\n");
      builder.append(getRString(R.string.fiftyPercent));
    }
    else if (percent == 75) {
      builder.append("\n");
      builder.append(getRString(R.string.seventyFivePercent));
    }
    else if (percent == 90) {
      builder.append("\n");
      builder.append(getRString(R.string.ninety));
    }
    return builder.toString();
  }

  private void initColors() {
    View view = findViewById(R.id.separator);
    //view.setBackgroundColor(ContextCompat.getColor(this, R.color.webListPrimary));
  }

  private void initDownloadingProgressDialog(String TAG, String message) {
    ProgressDialogFragment progressFragment =
      (ProgressDialogFragment) findFragByTag(TAG);
    if (progressFragment == null) {
      progressFragment = ProgressDialogFragment.newInstance
        (Ez.oneStringBundle(ProgressDialogFragment.MESSAGE, message));
      showDialogFragmentASL(progressFragment, TAG);
    }
  }

  private void initializeToolbar () {
    final Toolbar stageToolbar = (Toolbar) findViewById(R.id.stage_toolbar);
    setSupportActionBar(stageToolbar);
    stageToolbar.setTitle(null);
    stageToolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_upward_white));
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    TextView header = (TextView)findViewById(R.id.toolbar_title);
    header.setText(getRString(R.string.scenes_from_the_web));
  }

  private void makeScene (SceneInfo sceneInfo) {
    Bundle bundle = Ez.convertToBundle(sceneInfo);
    bundle.putString(MaP.BACKGROUNDS_MP3_ZIP_URL,
      MaP.getBackgroundsZipUrl(sceneInfo.getWebId()));
    bundle.putString(MaP.SCENE_INFO_URL, MaP.getSceneXMLURL(sceneInfo.getWebId()));
    Fragment sceneBuilder = WebToCPFragment.newInstance(bundle);
    addFragmentRemovePrevious(sceneBuilder, SCENE_BUILDER_TAG);
    synchronized (this) {
      if (null != progressHandler)
        progressHandler.
          updateProgressDialogForScene(0, downloadingString(sceneInfo.getSpanishTitle(), 0));
    }
  }

  private void noticeExceptionOccurred(String info) {
    Bundle bundle = new Bundle();
    bundle.putString(ExceptionDialog.MESSAGE_ENG, info);
    bundle.putString(ExceptionDialog.MESSAGE_SPN, info);
    bundle.putString(MaP.MESSAGE, info);
    synchronized (LIST_PROGRESS_DIALOG_TAG) {
      if (null != exceptionHandler)
        this.exceptionHandler.sendExceptionMessage(bundle);
    }
  }

  private boolean serviceIsConnected () {
    ConnectivityManager cm = (ConnectivityManager) this
      .getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
    if (null != networkInfo && networkInfo.isConnected()) {
      return true;
    }
    return false;
  }

  private void showNotification (String percentDoneMessage, String scene, int iconId) {
    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
    mBuilder.setContentTitle(scene);
    mBuilder.setContentText(percentDoneMessage);
    mBuilder.setSmallIcon(iconId);
    NotificationManager mNotificationManager =
      (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    mNotificationManager.notify(MaP.NOTIFICATION_ID, mBuilder.build());
  }

  private void setListProgressDialog(String message) {
    ListFragment listFragment = (ListFragment) findFragByTag(SCENE_LIST_TAG);
    if (listFragment != null) {
      ListView listView = listFragment.getListView();
      if (listView == null) {
        initDownloadingProgressDialog(LIST_PROGRESS_DIALOG_TAG, message);
      }
      else if (listView != null && listView.getCount() == 0) {
        initDownloadingProgressDialog(LIST_PROGRESS_DIALOG_TAG, message);
      }
      else if (listView != null && listView.getCount() > 0) {
        removeFragmentASL(LIST_PROGRESS_DIALOG_TAG);
      }
    }
    else {
      initDownloadingProgressDialog(LIST_PROGRESS_DIALOG_TAG, message);
    }
  }

  private void setNoConnectionDialog () {
    NoConnectionDialog noConnectionDialog =
      NoConnectionDialog.newInstance(new Bundle());
    noConnectionDialog.setTargetFragment(null, NO_CONNECTION_REQUEST_CODE);
    addFragmentIfNotPresentASL(noConnectionDialog, NO_CONNECTION_DIALOG_TAG);
  }

  private void startDownloadingSceneProgressDialog(String message) {
    initDownloadingProgressDialog(SCENE_PROGRESS_DIALOG_TAG, message);
  }
}