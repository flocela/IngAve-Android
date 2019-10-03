package com.olfybsppa.inglesaventurero.completedscenes;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;

import com.olfybsppa.inglesaventurero.openingactivity.CallBackHeralder;
import com.olfybsppa.inglesaventurero.openingactivity.NewDataHerald;

import java.util.ArrayList;

public abstract class CursorLoaderWatcher extends CallBackHeralder {
  protected CursorAdapter adapter;
  protected ArrayList<NewDataHerald.Listener> listeners = new ArrayList<>();
  protected Context context;
  protected Uri uri;
  protected String[] projection;

  public CursorLoaderWatcher(CursorAdapter adapter,
                             Context context,
                             String[] projection,
                             Uri uri) {
    this.adapter = adapter; //At this point adapter is probably empty.
    this.context = context;
    this.projection = projection;
    this.uri = uri;
  }

  @Override
  public void addListener (NewDataHerald.Listener listener) {
    if (listener != null) {
      listeners.add(listener);
    }
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    //TODO catch errors and send error message to Listener.
    this.adapter.swapCursor(data);
    for (NewDataHerald.Listener listener : listeners) {
      listener.gotsNewData(data.getCount());
    }
  }

  @Override
  public void onLoaderReset(Loader<Cursor> arg0) {
    this.adapter.swapCursor(null);
  }

}
