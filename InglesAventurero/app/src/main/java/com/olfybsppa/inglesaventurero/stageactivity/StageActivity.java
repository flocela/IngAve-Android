package com.olfybsppa.inglesaventurero.stageactivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.dialogs.AdvisoryDialog;
import com.olfybsppa.inglesaventurero.dialogs.DialogDoneListener;
import com.olfybsppa.inglesaventurero.dialogs.ExceptionDialog;
import com.olfybsppa.inglesaventurero.listeners.WorkProgressListener;
import com.olfybsppa.inglesaventurero.stageactivity.dialog.BackgroundInfoDialog;
import com.olfybsppa.inglesaventurero.stageactivity.dialog.LinesShownDialog;
import com.olfybsppa.inglesaventurero.stageactivity.dialog.StageHelpDialog;
import com.olfybsppa.inglesaventurero.stageactivity.dialog.VelocityDialog;
import com.olfybsppa.inglesaventurero.stageactivity.dialog.VoiceAttributionsDialog;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.start.SettingsActivity;
import com.olfybsppa.inglesaventurero.start.SuperFragmentActivity;
import com.olfybsppa.inglesaventurero.utils.Ez;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.olfybsppa.inglesaventurero.R.id.mic;
import static com.olfybsppa.inglesaventurero.stageactivity.ImageInfoWorkerF.PAGE_NAME;

public class StageActivity extends SuperFragmentActivity
                           implements WorkProgressListener,
                                      DialogPresenter,
                                      DialogDoneListener,
                                      SharedPreferences.OnSharedPreferenceChangeListener {
  //Intent should have:
  public static String STORY        = "STORY";
  public static String STORY_ROW_ID = "STORY_ROW_ID";
  public static String TITLE_ENG    = "TITLE_ENG";
  /** Test stories TEST_STORY, ESPRESS_STORY, SILENT_STORY are used in script tests.
   * Use as extra in StageActivity's startActivity().
   */
  public static String TEST_STORY     = "TEST_STORY";
  public static String ESPRESSO_STORY = "ESPRESSO_STORY";
  public static String SILENT_STORY   = "SILENT_STORY";

  public static int PLAYER_DIDNT_FINISH = 50;
  public static int MIC_REQ_CODE        = 1000;

  private static String TRACKER_M_TAG  = "TRACKER_M_TAG";
  private static String IMAGE_INFO_TAG = "IMAGE_INFO_TAG";
  private static String VOICE_INFO_TAG = "VOICE_INFO_TAG";
  private static String PLAYER_TAG     = "PLAYER_TAG";

  private String story;
  private String titleEng;
  private int storyRowId = -1;
  private DirectorI director;
  private PlayerInterface player;
  private DirectorHandler directorHandler;
  private UIThreadHandler uiThreadHandler;
  private FloatingActionButton floatingActionButton;

  @Override
  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    NestedScrollView scrollView = (NestedScrollView) findViewById (R.id.nest_scrollview);
    scrollView.setFillViewport (true);
    initToolbar();
    setVolumeControlStream(AudioManager.STREAM_MUSIC);
    directorHandler = new DirectorHandler();
    uiThreadHandler = new UIThreadHandler();
  }

  @Override
  protected void initView() {
    setContentView(R.layout.stage_activity);
  }

  @Override
  protected void initStartingVariables(Bundle args) {
    titleEng   = args.getString(TITLE_ENG);
    story      = args.getString(STORY);
    storyRowId = args.getInt(STORY_ROW_ID);
  }

  @Override
  public void onResume() {
    super.onResume();
    if (uiThreadHandler == null)
      uiThreadHandler = new UIThreadHandler();
    if (directorHandler == null)
      directorHandler = new DirectorHandler();
    if (director != null)
      director.open();
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    preferences.registerOnSharedPreferenceChangeListener(this);
    uiThreadHandler.initDirAndMicWhenReady();
  }

  @Override
  protected void onPause() {
    super.onPause();
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    preferences.unregisterOnSharedPreferenceChangeListener(this);

    synchronized (story) {
      if (directorHandler != null) {
        directorHandler.removeCallbacksAndMessages(null);
        directorHandler = null;
      }
      if (director != null) { //director is sometimes null because of voice recognition
        //dialog that puts activity to onStop, then onDestroy where director is destroyed.
        director.close();
      }
    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(STORY, story);
    outState.putInt(STORY_ROW_ID, storyRowId);
    outState.putString(TITLE_ENG, titleEng);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (player != null)
      player.close();
    //Okay to disengage uiThreadHandler in onDestroy because all of the commits it leads
    //to are allowWithStateLoss.
    synchronized (this) {
      if (uiThreadHandler != null) {
        uiThreadHandler.removeCallbacksAndMessages(null);
        uiThreadHandler = null;
      }
    }
  }

  public void onActivityResult (int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK  && requestCode == MIC_REQ_CODE) {
      if (uiThreadHandler != null) {
        uiThreadHandler.sendRecognizerData(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS));
      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.stage_activity_actions, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_scene_and_pict_info:
        if (director != null) {
          int pageName = director.getCurrPageName();
          Bundle bundleA = Ez.oneIntBundle(ImageInfoWorkerF.STORY_ID, storyRowId);
          bundleA.putInt(PAGE_NAME, pageName);
          ImageInfoWorkerF imageInfoF = ImageInfoWorkerF.newInstance(bundleA);
          addFragmentIfNotPresentASL(imageInfoF, IMAGE_INFO_TAG);
        }
        return true;
      case R.id.action_scene_voice_info:
        Bundle bundle = Ez.oneIntBundle(ImageInfoWorkerF.STORY_ID, storyRowId);
        VoiceInfoWorkerF voiceInfoF = VoiceInfoWorkerF.newInstance(bundle);
        addFragmentIfNotPresentASL(voiceInfoF, VOICE_INFO_TAG);
        return true;
      case R.id.action_clear_scene:
        if (director != null)
          director.clearEndOfStory();
        return true;
      case R.id.action_help:
        StageHelpDialog stageHelpDialog = StageHelpDialog.newInstance(null);
        showDialog(stageHelpDialog);
        return true;
      case R.id.action_spanish_shown:
        SharedPreferences preferencesA =
          PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Map mapA = preferencesA.getAll();
        Integer currSpnShownH = getResources().getInteger(R.integer.lines_shown_default_h);
        Object valueH = mapA.get(SettingsActivity.SPN_SHOWN_H);
        if (!(valueH instanceof String)){
          currSpnShownH = (Integer)valueH;
        }
        Integer currSpnShownR = getResources().getInteger(R.integer.lines_shown_default_r);
        Object valueR = mapA.get(SettingsActivity.SPN_SHOWN_R);
        if (!(valueR instanceof String)) {
          currSpnShownR = (Integer)valueR;
        }
        Bundle spnBundle = Ez.oneIntBundle(LinesShownDialog.CURR_SPN_SHOWN_H,
                                           currSpnShownH);
        spnBundle.putInt(LinesShownDialog.CURR_SPN_SHOWN_R, currSpnShownR);
        LinesShownDialog linesShownDialog = LinesShownDialog.newInstance(spnBundle);
        showDialog(linesShownDialog);
        return true;
      case R.id.action_velocity:
        SharedPreferences preferencesB = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Map mapB = preferencesB.getAll();
        Integer velocity = getResources().getInteger(R.integer.wait_times_default);
        Object valueB = mapB.get(SettingsActivity.WAIT_TIMES);
        if (!(valueB instanceof String)){
          velocity = (Integer)valueB;
        }
        Bundle bundleB = Ez.oneIntBundle(VelocityDialog.CURR_VELOCITY, velocity);
        VelocityDialog velocityDialog = VelocityDialog.newInstance(bundleB);
        showDialog(velocityDialog);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    if (key.equals(SettingsActivity.WAIT_TIMES) && director != null) {
      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
      Map map = preferences.getAll();
      Integer velocity = getResources().getInteger(R.integer.wait_times_default);
      Object value = map.get(SettingsActivity.WAIT_TIMES);
      if (!(value instanceof String)){
        velocity = (Integer)value;
      }
      int[] velocityArr = null;
      switch (velocity) {
        case -1:
          velocityArr = getResources().getIntArray(R.array.wait_times_2);
          break;
        case 0:
          velocityArr = getResources().getIntArray(R.array.wait_times_0);
          break;
        case 1:
          velocityArr = getResources().getIntArray(R.array.wait_times_1);
          break;
        case 2:
          velocityArr = getResources().getIntArray(R.array.wait_times_2);
          break;
        case 3:
          velocityArr = getResources().getIntArray(R.array.wait_times_3);
          break;
        case 4:
          velocityArr = getResources().getIntArray(R.array.wait_times_4);
          break;
        case 5:
          velocityArr = getResources().getIntArray(R.array.wait_times_5);
          break;
      }
      director.changeVelocity(velocityArr);
    }
  }

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return true;
  }

  public PlayerInterface getPlayer() {
    return player;
  }

  public String getStory () {
    return story;
  }

  @Override
  public void notifyDialogDone(int requestCode, boolean isCancelled, Bundle returnedBundle) {}

  //notifyProgress regularly called from a separate thread.
  @Override
  public void notifyProgress(String tag, boolean cancelled, int done, final String info) {
    if ((tag.equals(TRACKER_M_TAG) && done == 100) ||
        (tag.equals(PLAYER_TAG) && done == 110)) {
      synchronized (this) {
        if (uiThreadHandler != null)
          uiThreadHandler.initDirAndMicWhenReady();
      }
    }
    else if (tag.equals(PLAYER_TAG)) {
      if (null != info && !info.isEmpty()) {
        String[] split = info.split(",");
        int pageId = Integer.parseInt(split[0]);
        int position = Integer.parseInt(split[1]);
        int shadow = Integer.parseInt(split[2]);
        synchronized (story) {
          if (directorHandler != null) {
            if (done != 100)
              directorHandler.notifyDHintFinished(pageId, position, PLAYER_DIDNT_FINISH);
            else
              directorHandler.notifyDHintFinished(pageId, position, shadow);
          }
        }
      }
    }
    else if (tag.equals(IMAGE_INFO_TAG) && done == 100) {
      ImageInfoWorkerF fragment = (ImageInfoWorkerF) findFragByTag(IMAGE_INFO_TAG);
      ArrayList<Attribution> attributions = fragment.getAttributions();
      removeFragmentASL(IMAGE_INFO_TAG);
      Bundle bundle = new Bundle();
      bundle.putParcelableArrayList(BackgroundInfoDialog.ATTRIBUTIONS, attributions);
      BackgroundInfoDialog bgInfoDialog = BackgroundInfoDialog.newInstance(bundle);
      showDialogFragmentASL(bgInfoDialog);
    }
    else if (tag.equals(VOICE_INFO_TAG) && done == 100) {
      VoiceInfoWorkerF frag = (VoiceInfoWorkerF) findFragByTag(VOICE_INFO_TAG);
      HashMap<String, String> voiceAttrs = frag.getAttributions();
      removeFragmentASL(VOICE_INFO_TAG);
      Bundle bundle = new Bundle();
      bundle.putString(VoiceAttributionsDialog.VOICE_ATTR_SPN,
                       voiceAttrs.get(LinesCP.voice_attr_spn));
      bundle.putString(VoiceAttributionsDialog.VOICE_ATTR_ENG,
                       voiceAttrs.get(LinesCP.voice_attr_eng));
      VoiceAttributionsDialog dialog = VoiceAttributionsDialog.newInstance(bundle);
      showDialogFragmentASL(dialog);
    }
  }

  @Override
  public void possiblyShow (DialogFragment dialog, String settingsPreferences) {
    SharedPreferences preferences =
      PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
    Map map = preferences.getAll();
    if (settingsPreferences!= null && (Boolean)map.get(settingsPreferences)) {
      showDialogFragmentASL(dialog, SettingsActivity.OPTIONS_PROMPT);
    }
  }

  @Override
  public void showDialog(DialogFragment dialog) {
    showDialogFragmentASL(dialog);
  }

  private class StageSceneCatalogue implements SceneCatalogue {
    @Override
    public void sceneFinished() {
      Intent msgIntent = new Intent(StageActivity.this, SceneCompletedService.class);
      msgIntent.putExtra(SceneCompletedService.SCENE_ID, storyRowId);
      msgIntent.putExtra(SceneCompletedService.COMPLETED, true);
      startService(msgIntent);
    }
  }

  private class DirectorHandler extends Handler {
    private String PAGE_NAME          = "PAGE_NAME";
    private String POSITION           = "POSITION";
    private String SHADOW             = "SHADOW";
    private String NOTIFY_END_HINT    = "NOTIFY_END_HINT";

    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (director == null)
        return;
      Bundle bundle = msg.getData();
      if (bundle.containsKey(NOTIFY_END_HINT)) {
        director.notifyStoppedPlaying(bundle.getInt(PAGE_NAME),
                                             bundle.getInt(POSITION),
                                             bundle.getInt(SHADOW));
      }
    }

    public void notifyDHintFinished(int pageName, int position, int shadow) {
      Message message = obtainMessage();
      Bundle bundle = Ez.oneStringBundle(NOTIFY_END_HINT, NOTIFY_END_HINT);
      bundle.putInt(PAGE_NAME, pageName);
      bundle.putInt(POSITION, position);
      bundle.putInt(SHADOW, shadow);
      message.setData(bundle);
      sendMessage(message);
    }
  }

  private class UIThreadHandler extends Handler {
    private String INIT_DIRECTOR_AND_MIC = "INIT_DIRECTOR_AND_MIC";
    private String MIC_REQ_STRING        = "MIC_REQ_STRING";
    private String RECOGNIZER_DATA       = "RECOGNIZER_DATA";
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      Bundle bundle = msg.getData();
      if (bundle.containsKey(INIT_DIRECTOR_AND_MIC)) {
        initializeDirectorAndMicWhenReady();
      }
      else if (bundle.containsKey(MIC_REQ_STRING)) {
        if (director != null) {
          openDirectorAndSendData(bundle);
        }
      }
    }

    private void openDirectorAndSendData (Bundle bundle) {
      director.open();
      director
        .receiveUserInput(bundle.getStringArrayList(RECOGNIZER_DATA));
    }

    public void sendRecognizerData(ArrayList<String> lines) {
      Message message = obtainMessage();
      Bundle bundle = Ez.oneStringBundle(MIC_REQ_STRING, MIC_REQ_STRING);
      bundle.putStringArrayList(RECOGNIZER_DATA, lines);
      message.setData(bundle);
      sendMessage(message);
    }

    public void initDirAndMicWhenReady() {
      Message message = obtainMessage();
      Bundle bundle = Ez.oneStringBundle(INIT_DIRECTOR_AND_MIC, INIT_DIRECTOR_AND_MIC);
      message.setData(bundle);
      sendMessage(message);
    }

    //Has to be called from UI thread. See comment below.
    //Initilize director and mic only when both TrackerFragment and MediaPlayerFragment
    //are ready.
    private void initializeDirectorAndMicWhenReady () {
      TrackerMakerFragment trackerF = (TrackerMakerFragment) findFragByTag(TRACKER_M_TAG);
      if (player != null &&
          player.isReady(StageActivity.this) &&
          trackerF != null &&
          trackerF.getTracker() != null) {
        if (director == null)
          initDirector(trackerF.getTracker());
        if (floatingActionButton == null)
          initMic();
        return;
      }
      if (trackerF == null) {
        Bundle storyArgs = Ez.oneStringBundle(TrackerMakerFragment.STORY, story);
        addFragmentIfNotPresentASL(TrackerMakerFragment.newInstance(storyArgs),
                                   TRACKER_M_TAG);
      }
      if (null == player || !player.isReady(StageActivity.this)) {
        PlayerProducer playerProducer =
          new PlayerProducer(StageActivity.this, PLAYER_TAG, story, StageActivity.this);
        //Player creates a handler when instantiated by PlayerProducer, so this has to be
        //called on the UI thread.
        if (null == playerProducer && !PlayerProducer.hasFile(StageActivity.this, story))
          noFileDialog();
        else
          player = playerProducer.producePlayer();
      }
    }
  }

  private boolean hasVoiceRecognition() {
    //TODO is this the only check to do for voice recognition?
    PackageManager pm = this.getPackageManager();
    List<ResolveInfo> activities
      = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH),0);
    return (activities.size()==0) ? false : true;
  }

  private void initDirector (Tracker tracker) {
    if (director != null)
      return;
    SharedPreferences preferences =
      PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
    Map map = preferences.getAll();
    Producer producer = new Producer (tracker,
                                      story,
                                      getSupportFragmentManager(),
                                      (ViewPager) findViewById(R.id.pager),
                                      player,
                                      this,
                                      new StageSceneCatalogue(),
                                      map,
                                      getResources());
    director = producer.getDirector();
  }

  private void initMic () {
    floatingActionButton = (FloatingActionButton)findViewById(mic);
    floatingActionButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!hasVoiceRecognition()) {
          recognizerDialog();
        }
        else {
          Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
          intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
          intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
          intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
          intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 4);
          intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hable");
          intent.
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS,
              2000);
          try {
            player.requestPause();
            startActivityForResult(intent, MIC_REQ_CODE);
          } catch (ActivityNotFoundException e) {
            recognizerDialog();
          }
        }
      }
    });
  }

  private void initToolbar () {
    final Toolbar toolbar = (Toolbar) findViewById(R.id.stage_toolbar);
    setSupportActionBar(toolbar);
    toolbar.setTitle(null);
    toolbar.setNavigationIcon(drawable(R.drawable.ic_arrow_upward_white));
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    TextView textView = (TextView)findViewById(R.id.toolbar_title);
    if (titleEng != null)
      textView.setText(titleEng);
    else
      textView.setText(story);
  }

  private String string (int rId) {
    return getResources().getString(rId);
  }

  private Drawable drawable (int rId) {
    return ContextCompat.getDrawable(this,rId);
  }

  private void noFileDialog () {
    Bundle bundle = Ez.oneStringBundle(AdvisoryDialog.TITLE_ENG,
      string(R.string.scene_incomplete_eng));
    bundle.putString(AdvisoryDialog.TITLE_SPN, string(R.string.scene_incomplete_spn));
    bundle.putString(AdvisoryDialog.MESSAGE_ENG, string(R.string.download_again_eng));
    bundle.putString(AdvisoryDialog.MESSAGE_SPN, string(R.string.download_again_spn));
    AdvisoryDialog dialog = AdvisoryDialog.newInstance(bundle);
    showDialogFragmentASL(dialog);
  }

  private void recognizerDialog () {
    Bundle bundle = new Bundle();
    bundle.putString(ExceptionDialog.TITLE_ENG, "Error");
    bundle.putString(ExceptionDialog.TITLE_SPN, "Error");
    bundle.putString(ExceptionDialog.MESSAGE_ENG, "Voice Recognition isn't working.");
    bundle.putString(ExceptionDialog.MESSAGE_SPN, "El reconocimiento de voz no funciona.");
    ExceptionDialog exceptionDialog =  ExceptionDialog.newInstance(bundle);
    showDialogFragmentASL(exceptionDialog);
  }
}