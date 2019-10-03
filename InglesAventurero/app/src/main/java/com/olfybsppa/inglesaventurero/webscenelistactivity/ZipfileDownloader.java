package com.olfybsppa.inglesaventurero.webscenelistactivity;

import com.olfybsppa.inglesaventurero.exceptions.TracedException;
import com.olfybsppa.inglesaventurero.listeners.WorkProgressListener;
import com.olfybsppa.inglesaventurero.worker.UnzipperWorker;
import com.olfybsppa.inglesaventurero.worker.Worker;
import com.olfybsppa.inglesaventurero.worker.ZipFileDownloaderWorker;

import java.util.LinkedList;

public class ZipfileDownloader extends Worker implements WorkProgressListener {
  private ZipFileDownloaderWorker downloader;
  private UnzipperWorker          unzipper;

  private String zipWebURL;
  private String phoneZipFullFileString;

  private String ZIPFILE_DOWNLOADER = "ZipfileDownloader";
  private LinkedList<WorkProgressListener> listeners = new LinkedList<>();
  private Worker currWorker;

  public ZipfileDownloader(String zipWebURL, String phoneZipFullFileString) {
    this.zipWebURL = zipWebURL;
    this.phoneZipFullFileString = phoneZipFullFileString;
  }

  // mp3 file and background .jpg files are in a zipped file from the web.
  // text for scene is from xml text from the web.
  public void work() {
    try {
      if (cancelRequested) {
        cancelCompleted = true;
        return;
      }
      this.downloader = new ZipFileDownloaderWorker(this.zipWebURL,
                                                    phoneZipFullFileString);
      this.downloader.addListener(this);
      this.currWorker = this.downloader;
      this.downloader.work();
      if (downloader.workIsDone())
        notifyListeners(WebActivity.SCENE_BUILDER_TAG, false, 75, null);
    }
    catch(Exception e) {
      exception = new TracedException(e, ZIPFILE_DOWNLOADER);
      return;
    }
    try {
      if (cancelRequested) {
        cancelCompleted = true;
        return;
      }
      if (downloader != null && downloader.workIsDone()) {
        unzipper = new UnzipperWorker(this.phoneZipFullFileString);
        unzipper.work();
        if (unzipper.workIsDone())
          workDone = true;
      }
    }
    catch (Exception e) {
      exception = new TracedException(e, ZIPFILE_DOWNLOADER);
      return;
    }
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
