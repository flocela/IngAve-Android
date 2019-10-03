package com.olfybsppa.inglesaventurero.webscenelistactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;

import com.olfybsppa.inglesaventurero.collectors.SceneInfo;
import com.olfybsppa.inglesaventurero.exceptions.TracedException;
import com.olfybsppa.inglesaventurero.openingactivity.NewDataHerald;
import com.olfybsppa.inglesaventurero.utils.Ez;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListLoaderWatcher extends CallBacksReporter {

  private WebListAdapter adapter;
  protected ArrayList<NewDataHerald.Listener> listeners = new ArrayList<>();
  private Context context;
  private String urlString;
  //TODO this gets garbage collected when we end WebActivity, right?

  public ListLoaderWatcher (Context context, String urlString, WebListAdapter listAdapter) {
    this.context = context;
    this.urlString = urlString;
    this.adapter = listAdapter;
  }

  @Override
  public void addListener (NewDataHerald.Listener listener) {
    if (listener != null) {
      listeners.add(listener);
    }
  }

  @Override
  public Loader<List<SceneInfo>> onCreateLoader(int id, Bundle args) {
    return new DownloadableSceneListLoader(context, urlString);
  }

  @Override
  public void onLoadFinished(Loader<List<SceneInfo>> loader, List<SceneInfo> data) {
    if (((DownloadableSceneListLoader)loader).exceptionOccurred()) {
      TracedException tracedException
        = ((DownloadableSceneListLoader)loader).getTracedException();
      for (NewDataHerald.Listener listener: listeners) {        ;
        listener.errorGettingData(Ez.oneStringBundle(ERROR, tracedException.getMessage()));
      }
    }
    else if (null == data) {
      for (NewDataHerald.Listener listener: listeners) {        ;
        listener.errorGettingData(Ez.oneStringBundle(ERROR, "Data was null."));
      }
    }
    else {
      Collections.sort(data, new SceneInfo.SpanishTitleComparator());
      adapter.setData(data);
      for (NewDataHerald.Listener listener: listeners) {
        listener.gotsNewData(data.size());
      }
    }
  }

  @Override
  public void onLoaderReset(Loader<List<SceneInfo>> loader) {
    adapter.setData(new ArrayList<SceneInfo>());
  }

}
