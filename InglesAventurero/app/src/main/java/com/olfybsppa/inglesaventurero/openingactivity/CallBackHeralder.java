package com.olfybsppa.inglesaventurero.openingactivity;


import android.database.Cursor;
import android.support.v4.app.LoaderManager;


public abstract class CallBackHeralder implements LoaderManager.LoaderCallbacks<Cursor> {

  public abstract void addListener(NewDataHerald.Listener listener);

}
