package com.olfybsppa.inglesaventurero.webscenelistactivity;

import com.olfybsppa.inglesaventurero.exceptions.TracedException;
import com.olfybsppa.inglesaventurero.listeners.WorkProgressListener;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPScene;
import com.olfybsppa.inglesaventurero.worker.Worker;

import java.util.LinkedList;

public class SceneMaker extends Worker implements WorkProgressListener {
  private XMLDocumentImporter     xmlDocImporter;
  private DocToSceneWorker        docToSceneWorker;
  private CPScene                 cpScene;
  private String sceneInfoURL;

  private String SCENE_MAKER = "SceneMaker";
  private LinkedList<WorkProgressListener> listeners = new LinkedList<>();
  private Worker currWorker;

  public SceneMaker(String zipWebURL, String sceneURL, String phoneZipFullFileString) {
    this.sceneInfoURL = sceneURL;
  }

  // mp3 file and background .jpg files are in a zipped file from the web.
  // text for scene is from xml text from the web.
  public void work() {
    try {
      if (cancelRequested) {
        cancelCompleted = true;
        return;
      }
      xmlDocImporter = new XMLDocumentImporter(sceneInfoURL);
      xmlDocImporter.work();
    }
    catch(Exception e) {
        exception = new TracedException(e, SCENE_MAKER);
      return;
    }
    try {
      if (cancelRequested) {
        cancelCompleted = true;
        return;
      }
      docToSceneWorker = new DocToSceneWorker(xmlDocImporter.getDocument());
      docToSceneWorker.work();
      cpScene = docToSceneWorker.getScene();
      if (docToSceneWorker.workIsDone())
        workDone = true;
    }
    catch(Exception e) {
      exception = new TracedException(e, SCENE_MAKER);
      return;
    }
  }

  public CPScene getCpScene () {
    return cpScene;
  }

  @Override
  public void notifyProgress(String tag,
                             boolean cancelled,
                             int percentDone,
                             String info) {
    notifyListeners(tag, cancelled, percentDone, info);
  }

  public void addListener (WorkProgressListener wpListener) {
    listeners.add(wpListener);
  }

  @Override
  public void setCancelRequestedToTrue() {
    super.setCancelRequestedToTrue();
    if (currWorker != null)
      currWorker.setCancelRequestedToTrue();
  }

  private void notifyListeners (String tag, boolean wasCancelled,
                                int percentDone,
                                String info) {
    for (WorkProgressListener listener : listeners) {
      listener.notifyProgress(WebActivity.SCENE_BUILDER_TAG, wasCancelled, percentDone, info);
    }
  }
}
