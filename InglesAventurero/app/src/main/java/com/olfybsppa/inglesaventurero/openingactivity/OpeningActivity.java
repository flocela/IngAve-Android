package com.olfybsppa.inglesaventurero.openingactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.completedscenes.CompletedScenesActivity;
import com.olfybsppa.inglesaventurero.deleteingscenes.DeleteSceneActivity;
import com.olfybsppa.inglesaventurero.dialogs.AdvisoryDialog;
import com.olfybsppa.inglesaventurero.dialogs.DialogDoneListener;
import com.olfybsppa.inglesaventurero.listeners.UsesListFragment;
import com.olfybsppa.inglesaventurero.stageactivity.StageActivity;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.start.SettingsActivity;
import com.olfybsppa.inglesaventurero.start.SuperFragmentActivity;
import com.olfybsppa.inglesaventurero.utils.Ez;
import com.olfybsppa.inglesaventurero.webscenelistactivity.WebActivity;

import java.io.File;
import java.util.Map;

/**
 * In charge of waiting for user to click on a scene and then playing it.
 * Contains SceneListFragment (which shows already downloaded scenes.)
 * In charge of starting the activity 'list of downloadable scenes', if user presses download menu item.
 */
public class OpeningActivity extends SuperFragmentActivity
                             implements UsesListFragment,
                                        DialogDoneListener,
                                        DelayedPrompter.Listener,
                                        SceneListFragment.IdentifyableItemListener<SceneTitleView>,
                                        NewDataHerald.Listener {

  public static final String OPENING_INSTR_TAG = "OPENING_INSTR_TAG";
  public static final String SCENE_LIST_TAG    = "SCENE_LIST_TAG";
  public static final int REQUEST_DOWNLOAD_LIST = 1001;
  private DelayedPrompter promptHandler;
  private String[] infoInListItems = {BaseColumns._ID,
                                      LinesCP.scene_name,
                                      LinesCP.english_title,
                                      LinesCP.spanish_title,
                                      LinesCP.type_1,
                                      LinesCP.english_description,
                                      LinesCP.spanish_description,
                                      LinesCP.finished};
  private int numOfActiveScenes = 0;
  private String NUM_OF_ACTIVE_SCENES = "NUM_OF_ACTIVE_SCENES";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeToolbar();
    initializeNumOfActiveScenes();
  }

  @Override
  protected void initStartingVariables(Bundle args) {
    this.promptHandler = new DelayedPrompter(this);
  }

  @Override
  protected void initView() {
    setContentView(R.layout.list_activity);
  }

  @Override
  protected void onStart() {
    super.onStart();
    ListFragment listFragment = (ListFragment) findFragByTag(SCENE_LIST_TAG);
    if (listFragment != null) {
      ListView listView = listFragment.getListView();
      if (listView != null) {
        listView.setDivider(new ColorDrawable(ContextCompat.getColor(this,
          R.color.webListItemBg)));
        listView.setDividerHeight(1);
      }
      if (listView != null && listView.getCount() == 1)
        promptHandler.sendPrompterMessage(listView.getCount()); // will recieve delayed
        // message in method listUpdated(int sentNumOfRows);
    }
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    if (savedInstanceState.containsKey(NUM_OF_ACTIVE_SCENES))
      numOfActiveScenes = savedInstanceState.getInt(NUM_OF_ACTIVE_SCENES);
  }

  @Override
  public void onResume() {
    super.onResume();
    TextView header = (TextView)findViewById(R.id.toolbar_title);
    header.setText(getResources().getString(R.string.your_scenes));
    Fragment listFragment = new SceneListFragment();
    addFragmentIfNotAddedASL(listFragment, SCENE_LIST_TAG, R.id.frame_for_scene_list);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(NUM_OF_ACTIVE_SCENES, numOfActiveScenes + 1);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (null != promptHandler) {
      promptHandler.removeCallbacksAndMessages(null);
      promptHandler = null;
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main_activity_actions, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_download:
        numOfActiveScenes = numOfActiveScenes + 1;
        Intent downloadableSongsListIntent = new Intent(this, WebActivity.class);
        startActivity(downloadableSongsListIntent);
        return true;
      case R.id.action_delete:
        Intent deleteSongListIntent = new Intent(this, DeleteSceneActivity.class);
        startActivity(deleteSongListIntent);
        return true;
      case R.id.action_completed_scenes:
        Intent intent = new Intent(this, CompletedScenesActivity.class);
        startActivity(intent);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public void initializeList(String tag) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    ListFragment listFragment = (ListFragment)fragmentManager.findFragmentByTag(tag);
    SceneTitleAdapter sceneTitleAdapter = new SceneTitleAdapter(this, null, 0);
    ActiveScenesLoaderWatcher cursorLoaderWatcher
      = new ActiveScenesLoaderWatcher(sceneTitleAdapter,
                                      this,
                                      infoInListItems,
                                      LinesCP.sceneTableUri);
    cursorLoaderWatcher.addListener(this);
    listFragment.setListAdapter(sceneTitleAdapter);
    getSupportLoaderManager().initLoader(0, null, cursorLoaderWatcher);
    ListView listView = listFragment.getListView();
    if (listView != null) {
      ColorDrawable colorDrawable =
        new ColorDrawable(ContextCompat.getColor(this,R.color.webListItemBg));
      listView.setDivider(colorDrawable);
      listView.setDividerHeight(1);
    }
  }

  @Override
  public void itemClicked(SceneTitleView view, int rowId) {
    String storyName = view.getSceneName();
    String titleEng  = view.getEnglishTitle();
    File dir = getDir(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
    String fullMP3Name = dir.getAbsolutePath() + "/" + storyName + ".m4a";
    File file = new File(fullMP3Name);
    if (!file.exists()) {
      Bundle bundle = Ez.oneStringBundle(AdvisoryDialog.TITLE_ENG, getResources().getString(R.string.scene_incomplete_eng));
      bundle.putString(AdvisoryDialog.TITLE_SPN, getResources().getString(R.string.scene_incomplete_spn));
      bundle.putString(AdvisoryDialog.MESSAGE_ENG, getResources().getString(R.string.download_again_eng));
      bundle.putString(AdvisoryDialog.MESSAGE_SPN, getResources().getString(R.string.download_again_spn));
      AdvisoryDialog dialog = AdvisoryDialog.newInstance(bundle);
      showDialogFragmentASL(dialog);
    }
    else {
      Intent intent = new Intent(OpeningActivity.this, StageActivity.class);
      if (titleEng != null)
        intent.putExtra(StageActivity.TITLE_ENG, titleEng);
      intent.putExtra(StageActivity.STORY, storyName);
      intent.putExtra(StageActivity.STORY_ROW_ID, rowId);
      startActivity(intent);
    }
  }

  @Override
  public void listUpdated(int sentNumOfRows) {
    showInstructionDialog(sentNumOfRows);
    SharedPreferences sharedPreferences =
      PreferenceManager.getDefaultSharedPreferences(this);
    SharedPreferences.Editor prefEditor = sharedPreferences.edit();
    prefEditor.putInt(SettingsActivity.NUM_OF_ACTIVE_SCENES, sentNumOfRows);
    prefEditor.commit();

    TextView header = (TextView)findViewById(R.id.toolbar_title);
    header.setText(getResources().getString(R.string.your_scenes));
  }

  @Override
  public void notifyDialogDone(int requestCode, boolean isCancelled, Bundle returnedBundle) {
    switch (requestCode) {
      case REQUEST_DOWNLOAD_LIST: {
        if (!isCancelled) {
          Intent downloadableScenesListIntent = new Intent(this, WebActivity.class);
          startActivity(downloadableScenesListIntent);
        }
        break;
      }
    }
  }

  @Override
  public void gotsNewData(int numOfRows) {
    if (null != promptHandler)
      promptHandler.sendPrompterMessage(numOfRows);
  }

  public void errorGettingData(Bundle bundle) {}

  private void initializeToolbar () {
    final Toolbar stageToolbar = (Toolbar) findViewById(R.id.stage_toolbar);
    stageToolbar.setTitle(null);
    stageToolbar.setLogo(R.drawable.ic_logo);
    setSupportActionBar(stageToolbar);
    stageToolbar.setNavigationIcon(null);
    getSupportActionBar().setDisplayShowTitleEnabled(false);

    TextView textView = (TextView)findViewById(R.id.toolbar_title);
    textView.setText(getResources().getString(R.string.your_scenes));
  }

  private void initializeNumOfActiveScenes () {
    PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
    Map map = preferences.getAll();
    Object object = map.get(SettingsActivity.NUM_OF_ACTIVE_SCENES);
    if (object instanceof String)
      numOfActiveScenes = Integer.parseInt((String)object);
    else
      numOfActiveScenes = (int)object;
  }

  private void showInstructionDialog(int numOfRows) {
    DialogFragment dialogFragment = (DialogFragment) this.findFragByTag(OPENING_INSTR_TAG);
    if (dialogFragment == null) {
      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
      Map map = preferences.getAll();
      PromptCoordinator promptCoordinator =
        new PromptCoordinator(map, numOfRows);
      dialogFragment = promptCoordinator.getCurrPromptDialog();
      showDialogFragmentASL(dialogFragment, OPENING_INSTR_TAG);
    }
  }
}