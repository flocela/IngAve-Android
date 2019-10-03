package com.olfybsppa.inglesaventurero.webscenelistactivity;

import android.content.ContentResolver;

import com.olfybsppa.inglesaventurero.exceptions.TracedException;
import com.olfybsppa.inglesaventurero.listeners.WorkProgressListener;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPScene;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.BackgroundResolver;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.SceneResolver;
import com.olfybsppa.inglesaventurero.worker.Worker;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class SceneToCP extends Worker {

  private CPScene cpScene;
  private ContentResolver cR;
  private String CPSceneIntoCP = "SceneToCP:";
  private LinkedList<WorkProgressListener> listeners = new LinkedList<>();
  private String directory;

  public SceneToCP(CPScene cpScene, ContentResolver cR, String directory) {
    this.cpScene = cpScene;
    this.cR = cR;
    this.directory = directory;
    if (cpScene == null) {
      TracedException tr = new TracedException(new NullPointerException(),
                                               CPSceneIntoCP +
                                               "cpScene is null");
      notifyListeners(WebActivity.SCENE_BUILDER_TAG, true, -1, tr.getMessage());
    }
    if (cR == null) {
      TracedException tr = new  TracedException(new NullPointerException(),
                                                CPSceneIntoCP +
                                                "Content Resolver is null.");
      notifyListeners(WebActivity.SCENE_BUILDER_TAG, true, -1, tr.getMessage());
    }
  }

  public void work () {
    try {
      if (cpScene == null)
        return;
      SceneResolver sceneResolver = new SceneResolver(new ResolverWrapper(cR));
      HashMap<Integer, String> oldBackgrounds = getBackgrounds(cpScene.getSceneName());

      sceneResolver.insertCPScene(cpScene);

      Collection<String> newBkgdNames = cpScene.getBkgdFilenames();
      clearBkgdsNotFoundInNewBackgroundList(oldBackgrounds, newBkgdNames);
      workDone = true;
    }
    catch (Exception e) {
      exception = new TracedException(e, CPSceneIntoCP + " " + cpScene.getSceneName());
    }
  }

  public void addListener (WorkProgressListener listener) {
    this.listeners.add(listener);
  }

  public void notifyListeners (String tag,
                               boolean isCancelled,
                               int percentDone,
                               String info) {
    for (WorkProgressListener listener : listeners) {
      listener.notifyProgress(tag, isCancelled, percentDone, info);
    }
  }

  private HashMap<Integer, String> getBackgrounds (String sceneName) {
    SceneResolver sceneResolver = new SceneResolver(new ResolverWrapper(cR));
    return sceneResolver.filenamesForScene(sceneName);
  }

  private void clearBkgdsNotFoundInNewBackgroundList(HashMap<Integer, String> oldBackgrounds,
                                                     Collection<String> newBackgrounds) {
    BackgroundResolver backgroundResolver = new BackgroundResolver(new ResolverWrapper(cR));
    Iterator<Integer> iterator = oldBackgrounds.keySet().iterator();
    while (iterator.hasNext()) {
      Integer oldBackgroundId = iterator.next();
      String oldBackgroundName = oldBackgrounds.get(oldBackgroundId);
      if (!newBackgrounds.contains(oldBackgroundName)) {
        int numOfPages = backgroundResolver.retreiveNumOfPagesUsingBackground(oldBackgroundId);
        if (numOfPages <= 0) {
          File file = new File(directory + oldBackgroundName);
          if (file.exists())
            file.delete();
        }
      }
    }
  }

}
