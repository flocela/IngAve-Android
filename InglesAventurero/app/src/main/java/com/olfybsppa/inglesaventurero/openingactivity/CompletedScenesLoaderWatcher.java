package com.olfybsppa.inglesaventurero.openingactivity;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;

import com.olfybsppa.inglesaventurero.completedscenes.CursorLoaderWatcher;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.utils.Ez;

public class CompletedScenesLoaderWatcher extends CursorLoaderWatcher {

  public CompletedScenesLoaderWatcher(CursorAdapter adapter,
                                      Context context,
                                      String[] projection,
                                      Uri uri) {
    super(adapter, context, projection, uri);
    this.adapter = adapter; //At this point adapter is probably empty.
    this.context = context;
    this.projection = projection;
    this.uri = uri;
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(context,
                            uri,
                            projection,
                            Ez.where(LinesCP.finished, "1"),
                            null,
                            LinesCP.scene_name);
  }
}
