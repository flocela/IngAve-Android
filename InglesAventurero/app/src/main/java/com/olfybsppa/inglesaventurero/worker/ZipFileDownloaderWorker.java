package com.olfybsppa.inglesaventurero.worker;


import com.olfybsppa.inglesaventurero.exceptions.TracedException;
import com.olfybsppa.inglesaventurero.listeners.WorkProgressListener;
import com.olfybsppa.inglesaventurero.utils.MaP;
import com.olfybsppa.inglesaventurero.utils.UriDeterminer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ZipFileDownloaderWorker extends Worker {

  private static final int DOWNLOAD_BUFFER_SIZE = 32768;

  private String zipURLString;
  private String phoneZipFile = "temp.zip";
  private String DownloaderFailed = "ZipFileDownloaderWorker";
  private ArrayList<WorkProgressListener> workProgressListeners = new ArrayList<>();

  public ZipFileDownloaderWorker (String zipURL, String phoneZipFile) {
    this.zipURLString = zipURL;
    this.phoneZipFile = phoneZipFile;
  }

  @Override
  public void work () {
    URL zipURL = null;
    try {
      zipURL = new URL(zipURLString);
    }
    catch (Exception e) {
      TracedException tracedException =
        new TracedException (e, DownloaderFailed + " zipURLString ");
      throw tracedException;
    }

    FileOutputStream newPhoneZipFileStream = null;
    OutputStream outStream = null;
    BufferedInputStream inStream = null;
    try {
      newPhoneZipFileStream = new FileOutputStream(phoneZipFile);
      byte[] data = new byte[DOWNLOAD_BUFFER_SIZE];
      int bytesRead = 0;
      URLConnection connection;
      connection = zipURL.openConnection();
      connection.setUseCaches(false);
      inStream = new BufferedInputStream(connection.getInputStream());
      outStream = new BufferedOutputStream(newPhoneZipFileStream, DOWNLOAD_BUFFER_SIZE);
      int count = 0;
      while((bytesRead = inStream.read(data, 0, data.length)) >= 0)
      { if (this.cancelRequested) {
          this.cancelCompleted = true;
          break;
        }
        outStream.write(data, 0, bytesRead);
        count++;
        if (60 == count) {
          notifyListeners(MaP.ZIP_FILE_DOWNLOADER, false, 25, null);
        }
        if (120 == count) {
          notifyListeners(MaP.ZIP_FILE_DOWNLOADER, false, 50, null);
        }
        if (320 == count) {
          notifyListeners(MaP.ZIP_FILE_DOWNLOADER, false, 75, null);
          // Testing IOException throw new IOException("IOException Fake");
        }
      }
      outStream.close();
      inStream.close();
      newPhoneZipFileStream.close();
      if (this.isCancelCompleted()) {
        File tempFile = new File(phoneZipFile);
        if (tempFile.exists()) {
          tempFile.delete();
        }
      }
      if (bytesRead <= 0) //File has been deleted and all bytes has been read.
        workDone = true;
    }
    catch(Exception e){
      TracedException tracedException = new TracedException (e, DownloaderFailed + " url: " +
                                                                UriDeterminer.getScenId(zipURLString) +
                                                                ". phoneZipFile is " + phoneZipFile);
      throw tracedException;
    }
    finally {
      try {
        if (null != outStream)
          outStream.close();
        if (null != inStream)
          inStream.close();
        if (null != newPhoneZipFileStream)
          newPhoneZipFileStream.close();
      } catch (Exception e) {
        //Do nothing, the important exception was the previous one.
      }
    }
  }

  public void addListener (WorkProgressListener listener) {
    workProgressListeners.add(listener); //It's the SceneMaker.
  }

  private void notifyListeners (String tag,
                                boolean cancelled,
                                int percentDone,
                                String info) {
    for (WorkProgressListener listener : workProgressListeners) {
      listener.notifyProgress(tag, cancelled, percentDone, info);
    }
  }
}

