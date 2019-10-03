package com.olfybsppa.inglesaventurero.stageactivity;


import android.support.v7.app.AppCompatActivity;

public interface PlayerInterface {
  void close();
  boolean isReady(AppCompatActivity activity);
  void requestPause();
  void requestPause(int AtNewPageName);
  // shadow is returned to StageActivity and is the argument for pauseOnPage(int)
  // and continueWithpage(int)
  int requestStart (int startTime, int playTime, int pageId, int positionInPage, int shadow, int delay);
}
