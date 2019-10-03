package com.olfybsppa.inglesaventurero.stageactivity;


import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;

public class SceneCompletedService extends IntentService {

  public static String SCENE_ID   = "SCENE_ID";
  public static String COMPLETED  = "COMPLETED";
  private boolean completed;

  public SceneCompletedService() {
    super("ClearSceneService");
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {
    if (!intent.hasExtra(COMPLETED) || !intent.hasExtra(SCENE_ID))
      return;
    int id = intent.getIntExtra(SCENE_ID, -1);
    completed = intent.getBooleanExtra(COMPLETED, false);
    ResolverWrapper rw = new ResolverWrapper(getContentResolver());
    rw.updateSceneCompleteness(id, completed);
  }
}
