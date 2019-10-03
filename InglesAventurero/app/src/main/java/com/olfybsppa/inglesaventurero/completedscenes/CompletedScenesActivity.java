package com.olfybsppa.inglesaventurero.completedscenes;

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
import com.olfybsppa.inglesaventurero.openingactivity.CompletedScenesLoaderWatcher;
import com.olfybsppa.inglesaventurero.openingactivity.DelayedPrompter;
import com.olfybsppa.inglesaventurero.openingactivity.NewDataHerald;
import com.olfybsppa.inglesaventurero.openingactivity.SceneListFragment;
import com.olfybsppa.inglesaventurero.openingactivity.SceneTitleAdapter;
import com.olfybsppa.inglesaventurero.openingactivity.SceneTitleView;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.start.SuperFragmentActivity;

public class CompletedScenesActivity extends SuperFragmentActivity
                             implements UsesListFragment,
                                        DialogDoneListener,
                                        DelayedPrompter.Listener,
                                        SceneListFragment.IdentifyableItemListener<SceneTitleView>,
                                        NewDataHerald.Listener {

  public static final String SCENE_LIST_TAG = "SCENE_LIST_TAG";
  private String[] infoInListItems = {BaseColumns._ID,
                                      LinesCP.scene_name,
                                      LinesCP.english_title,
                                      LinesCP.spanish_title,
                                      LinesCP.type_1,
                                      LinesCP.english_description,
                                      LinesCP.spanish_description,
                                      LinesCP.finished};
  private DelayedPrompter promptHandler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeToolbar();
    initColors();
    Fragment listFragment = new SceneListFragment();
    addFragmentIfNotAddedASL(listFragment, SCENE_LIST_TAG, R.id.frame_for_scene_list);
  }

  @Override
  public void onResume() {
    super.onResume();
    this.promptHandler = new DelayedPrompter(this);
  }

  @Override
  protected void initStartingVariables(Bundle args) {}

  @Override
  protected void initView() {
    setContentView(R.layout.list_activity);
  }


  @Override
  protected void onPause() {
    super.onPause();
    if (null != promptHandler) {
      promptHandler.removeCallbacksAndMessages(null);
      promptHandler = null;
    }
  }

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return true;
  }

  private void initColors() {
    View view = findViewById(R.id.separator);
    //view.setBackgroundColor(ContextCompat.getColor(this, R.color.completedPrimary));
  }

  public void initializeList(String tag) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    ListFragment listFragment = (ListFragment)fragmentManager.findFragmentByTag(tag);
    SceneTitleAdapter sceneTitleAdapter = new SceneTitleAdapter(this, null, 0);
    //sceneTitleAdapter.setAsUnclickable();
    CompletedScenesLoaderWatcher cursorLoaderWatcher
      = new CompletedScenesLoaderWatcher(sceneTitleAdapter,
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
  public void listUpdated(int sentNumOfRows) {
  }

  @Override
  public void notifyDialogDone(int requestCode, boolean isCancelled, Bundle returnedBundle) {}

  @Override
  public void itemClicked(SceneTitleView view, int rowId) {}

  @Override
  public void gotsNewData(int numOfRows) {
    if (null != promptHandler)
      this.promptHandler.sendPrompterMessage(numOfRows);
  }

  @Override
  public void errorGettingData(Bundle bundle) {}

  private void initializeToolbar () {
    Toolbar toolbar = (Toolbar)findViewById(R.id.stage_toolbar);
    toolbar.setTitle(null);
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_upward_white));

    getSupportActionBar().setDisplayShowTitleEnabled(false);
    TextView header = (TextView)findViewById(R.id.toolbar_title);
    header.setText(getResources().getString(R.string.completed_scenes));
  }
}