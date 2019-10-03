package com.olfybsppa.inglesaventurero.stageactivity;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class TrackerService extends Service {
  private Tracker tracker;
  private final IBinder mBinder = new LocalBinder();
  boolean isStarted = false;

  @Override
  public int onStartCommand (Intent intent, int flags, int startId) {
    if (null == tracker && !isStarted) {
      new Thread (new Runnable() {
        @Override
        public void run () {
          isStarted = true;
          tracker = AlphabeticalTracker.getTracker();
          isStarted = false;
        }
      }).start();
    }
    return Service.START_NOT_STICKY;
  }

  public class LocalBinder extends Binder {
    public TrackerService getService() {
      return TrackerService.this;
    }
  }

  @Override
  public IBinder onBind (Intent intent) {
    return mBinder;
  }

}
