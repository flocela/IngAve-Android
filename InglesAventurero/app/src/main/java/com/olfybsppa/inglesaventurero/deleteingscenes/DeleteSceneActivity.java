package com.olfybsppa.inglesaventurero.deleteingscenes;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.dialogs.DialogDoneListener;
import com.olfybsppa.inglesaventurero.listeners.UsesListFragment;
import com.olfybsppa.inglesaventurero.openingactivity.ActiveScenesLoaderWatcher;
import com.olfybsppa.inglesaventurero.openingactivity.NewDataHerald;
import com.olfybsppa.inglesaventurero.openingactivity.SceneListFragment;
import com.olfybsppa.inglesaventurero.openingactivity.SceneTitleAdapter;
import com.olfybsppa.inglesaventurero.openingactivity.SceneTitleView;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.start.SuperFragmentActivity;

public class DeleteSceneActivity extends SuperFragmentActivity
                                 implements UsesListFragment,
                                            DialogDoneListener,
                                            SceneListFragment.IdentifyableItemListener<SceneTitleView>,
                                            NewDataHerald.Listener{
  private static int DELETE_CONFIRMATION_REQ_CODE = 99;
	public static String DIALOG_TAG = "DIALOG_TAG";
  private String[] infoList = {BaseColumns._ID,
                               LinesCP.scene_name,
                               LinesCP.english_title,
                               LinesCP.spanish_title,
                               LinesCP.type_1,
                               LinesCP.spanish_description,
                               LinesCP.english_description,
                               LinesCP.finished};
  public static String SCENE_LIST_TAG = "SCENE_LIST_TAG";

  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    initializeToolbar();
    initColors();
    Fragment listFragment = new SceneListFragment();
    addFragmentIfNotAddedASL(listFragment, SCENE_LIST_TAG, R.id.frame_for_scene_list);
	}

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return true;
  }

  @Override
  public void gotsNewData(int numOfDataPoints) {
    if (numOfDataPoints == 0)
      finish();
  }

  @Override
  public void errorGettingData(Bundle bundle) {}

  @Override
  protected void initView() {
    setContentView(R.layout.list_activity);
  }

  public void initializeList(String tag) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    ListFragment listFragment = (ListFragment)fragmentManager.findFragmentByTag(tag);
    SceneTitleAdapter sceneTitleAdapter = new SceneTitleAdapter(this, null, 0);
    ActiveScenesLoaderWatcher cursorLoaderWatcher
      = new ActiveScenesLoaderWatcher(sceneTitleAdapter,
                                      this,
                                      infoList,
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
    Bundle bundle = new Bundle();
    bundle.putString(DeleteConfirmationDialog.ENGLISH_SCENE, view.getEnglishTitle());
    bundle.putString(DeleteConfirmationDialog.SPANISH_SCENE, view.getSpanishTitle());
    bundle.putInt(DeleteConfirmationDialog.ID, rowId);
    bundle.putString(DeleteConfirmationDialog.SCENE_NAME, view.getSceneName());
    DeleteConfirmationDialog dcD = DeleteConfirmationDialog.newInstance(bundle);
    dcD.setTargetFragment(null, DELETE_CONFIRMATION_REQ_CODE);
    showDialogFragmentASL(dcD, DIALOG_TAG);
  }

  @Override
  public void notifyDialogDone (int requestCode,
                                boolean isCancelled,
                                Bundle returnedBundle) {
    if (requestCode == DELETE_CONFIRMATION_REQ_CODE && isCancelled == false) {
      if (returnedBundle.getBoolean(DeleteConfirmationDialog.DELETE)) {
        int sceneId = returnedBundle.getInt(DeleteConfirmationDialog.ID);
        String sceneName = returnedBundle.getString(DeleteConfirmationDialog.SCENE_NAME);
        Intent msgIntent = new Intent(this, ClearSceneService.class);
        msgIntent.putExtra(ClearSceneService.SCENE_ID, ""+sceneId);
        msgIntent.putExtra(ClearSceneService.SCENE_NAME, sceneName);
        startService(msgIntent);
      }
    }
  }

  @Override
  protected void initStartingVariables(Bundle args) {}

  private void initColors() {
    View view = findViewById(R.id.separator);
    //view.setBackgroundColor(ContextCompat.getColor(this, R.color.deletePrimary));
  }

  private void initializeToolbar () {
    final Toolbar stageToolbar = (Toolbar) findViewById(R.id.stage_toolbar);
    setSupportActionBar(stageToolbar);
    stageToolbar.setTitle(null);
    stageToolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_upward_white));
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    TextView header = (TextView)findViewById(R.id.toolbar_title);
    header.setText(getResources().getString(R.string.delete_scene));
  }
}
