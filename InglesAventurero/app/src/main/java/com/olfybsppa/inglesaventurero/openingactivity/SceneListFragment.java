package com.olfybsppa.inglesaventurero.openingactivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.olfybsppa.inglesaventurero.listeners.UsesListFragment;

import java.util.ArrayList;

/**
 * Initialized with an adapter in onActivityCreated. Per ListFragment (the super class)
 * shows the listview according to the adapter.
 *
 * Sends a notice to OpeningActivity with the scene id when scene is clicked.
 */
public class SceneListFragment<T extends View> extends ListFragment {

  private ArrayList<IdentifyableItemListener> listeners = new ArrayList<>();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onActivityCreated (Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    configureListView();
    connectActivityWithListFragment();
  }

  public interface IdentifyableItemListener<T> {
    void itemClicked(T view, int rowId);
  }

  public void addListListener (IdentifyableItemListener listener) {
    this.listeners.add(listener);
  }

  @Override
  public void onListItemClick (ListView lv, View v, int position, long id) {
    lv.clearChoices();
    lv.requestLayout();
    lv.setItemChecked(position, false);
    for (IdentifyableItemListener listener : listeners) {
      listener.itemClicked((T)v, (int)id);
    }
  }

  private void configureListView() {
    ListView lv = getListView();
    lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    lv.setCacheColorHint(Color.BLACK);
    lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (view instanceof View.OnLongClickListener) {
          ((View.OnLongClickListener)view).onLongClick(view);
        }
        return true;
      }
    });
  }

  private void connectActivityWithListFragment() {
    addActivityAsClickListener();
    initializeFromCallingActivity();
  }

  private void addActivityAsClickListener() {
    Activity activity = getActivity();
    if (activity instanceof IdentifyableItemListener) {
      addListListener((IdentifyableItemListener) getActivity());
    }
  }

  private void initializeFromCallingActivity() {
    Activity activity = getActivity();
    if (activity instanceof UsesListFragment) {
      ((UsesListFragment)activity).initializeList(this.getTag());
    }
  }
}