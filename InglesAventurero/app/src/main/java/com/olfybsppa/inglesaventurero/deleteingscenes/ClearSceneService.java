package com.olfybsppa.inglesaventurero.deleteingscenes;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.PageResolver;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.SceneResolver;

import java.io.File;
import java.util.Collection;

public class ClearSceneService extends IntentService {

  public static String SCENE_ID   = "SCENE_ID";
  public static String SCENE_NAME = "SCENE_NAME";
  private int sceneId;
  private String sceneName;

  public ClearSceneService() {
    super("ClearSceneService");
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {
    String id = intent.getStringExtra(SCENE_ID);
    sceneId = Integer.parseInt(id);
    sceneName = intent.getStringExtra(SCENE_NAME);
    ResolverWrapper rw = new ResolverWrapper(getContentResolver());
    SceneResolver sceneResolver = new SceneResolver(rw);
    PageResolver  pageResolver  = new PageResolver(rw);
    Collection<String> toDeleteJPGS = pageResolver.retrieveBackgroundFilesToDelete(sceneId);
    File dir = getDir(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
    String dirString = dir.getAbsolutePath() + "/";
    for (String filename : toDeleteJPGS) {
      File file = new File(dirString + filename);
      if (file.exists())
        file.delete();
    }
    File file = new File(dirString + sceneName + ".m4a");
    if (file.exists())
      file.delete();

    sceneResolver.clearScene(sceneId);
  }
}