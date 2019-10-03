package com.olfybsppa.inglesaventurero.webscenelistactivity;


import android.support.v4.app.LoaderManager;

import com.olfybsppa.inglesaventurero.collectors.SceneInfo;
import com.olfybsppa.inglesaventurero.openingactivity.NewDataHerald;

import java.util.List;

public abstract class CallBacksReporter implements LoaderManager.LoaderCallbacks<List<SceneInfo>> {

  public static String ERROR = "ERROR";
  public abstract void addListener(NewDataHerald.Listener listener);

}
