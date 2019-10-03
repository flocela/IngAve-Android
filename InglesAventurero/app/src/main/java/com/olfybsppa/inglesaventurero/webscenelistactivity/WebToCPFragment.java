package com.olfybsppa.inglesaventurero.webscenelistactivity;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.listeners.WorkProgressListener;
import com.olfybsppa.inglesaventurero.nonUIFragments.WorkerFragment;
import com.olfybsppa.inglesaventurero.utils.Ez;
import com.olfybsppa.inglesaventurero.utils.MaP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPScene;
import com.olfybsppa.inglesaventurero.worker.Worker;

import java.io.File;

public class WebToCPFragment extends WorkerFragment implements WorkProgressListener {

  private boolean workDone = false;
  private String  zipWebURL;
  private String  sceneInfoURL;
  private String  phoneZipFileTemp;
  private String  phZipFullFileName;
  private String  spanishTitle;
  private boolean cancelledWork;
  private Worker currWorker;
  /*
    Needs the following:
    1. MaP.BACKGROUNDS_MP3_ZIP_URL  Url to zip file containing background and mp3 files.
    2. MaP.SCENE_INFO_URL           Url to scene info, pages, hints, replies...
    3. MaP.SPANISH_TITLE            title in spanish
   */

  public static WebToCPFragment newInstance (Bundle sceneInfoAndServerName) {
    WebToCPFragment importerAndBuilder = new WebToCPFragment();
    importerAndBuilder.setArguments(sceneInfoAndServerName);
    return importerAndBuilder;
  }

  @Override
  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle args = (savedInstanceState != null)? savedInstanceState : getArguments();
    File dir = this.getActivity().getDir(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
    if (null != args) {
      this.zipWebURL              = args.getString(MaP.BACKGROUNDS_MP3_ZIP_URL);
      this.sceneInfoURL           = args.getString(MaP.SCENE_INFO_URL);
      this.spanishTitle           = args.getString(MaP.SPANISH_TITLE);
      this.phoneZipFileTemp       = Ez.generateRandomWord()+(".zip");
      this.phZipFullFileName = dir.getAbsolutePath() + "/" + this.phoneZipFileTemp;
    }
  }

  @Override
  protected boolean work () {
    SceneMaker sceneMaker = createSceneMaker();
    currWorker = sceneMaker;
    sceneMaker.work();
    SceneToCP sceneToCP = createSceneToCP(sceneMaker.getCpScene());
    if (!sceneMaker.workIsDone() || sceneToCP == null) {
      recordException(sceneMaker);
      return false;
    }
    ZipfileDownloader zipfileDownloader = createZipfileDownloader();
    zipfileDownloader.work();
    if (!zipfileDownloader.workIsDone()) {
      recordException(zipfileDownloader);
      return false;
    }
    notifyProgress(WebActivity.SCENE_BUILDER_TAG, false, 90, spanishTitle);
    sceneToCP.work();
    workDone = sceneToCP.workIsDone();
    if (!sceneToCP.workIsDone()) {
      recordException((sceneToCP));
      return false;
    }
    notifyProgress(WebActivity.SCENE_BUILDER_TAG, false, 100, spanishTitle);
    return true;
  }

  @Override
  protected boolean workIsDone() {
    return workDone;
  }

  @Override
  public void notifyProgress(String tag,
                             boolean cancelled,
                             int percentDone,
                             String info) {
     synchronized(this.mThread) {
       if (mReady && !mQuitting) {
           ((WorkProgressListener)getActivity()).
             notifyProgress(this.getTag(), cancelled, percentDone, spanishTitle + info);
       }
     }
  }

  public void cancelWork() {
    this.cancelledWork = true;
    currWorker.setCancelRequestedToTrue();
  }

  private SceneMaker createSceneMaker () {
    SceneMaker sceneMaker = new SceneMaker(zipWebURL, sceneInfoURL, phZipFullFileName);
    sceneMaker.addListener(this);
    return sceneMaker;
  }

  private ZipfileDownloader createZipfileDownloader () {
    ZipfileDownloader downloader = new ZipfileDownloader(zipWebURL, phZipFullFileName);
    downloader.addListener(this);
    return downloader;
  }

  private SceneToCP createSceneToCP (CPScene scene) {
    Activity activity = getActivity();
    if (activity == null || scene == null)
      return null;
    ContentResolver cR = activity.getContentResolver();
    File dir = this.getActivity().getDir(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
    SceneToCP sceneToCP = new SceneToCP(scene, cR, dir.getAbsolutePath() + "/");
    sceneToCP.addListener(this);
    return sceneToCP;
  }

  @Override
  protected void toDoOnActivityCreated (Bundle savedInstanceState) {}

  private void recordException (Worker worker) {
    if (worker.wasException()) {
      exceptionString = worker.getException().getMessage();
      ((WorkProgressListener)getActivity()).
        notifyProgress(this.getTag(), false, -1, exceptionString);
    }
  }


}