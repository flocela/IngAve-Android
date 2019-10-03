package com.olfybsppa.inglesaventurero.stageactivity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class Player implements PlayerInterface {
  private PlayerFragment playerFragment;
  private String fragTag;
  private PlayHandler playHandler;
  private String STARTTIME = "START_TIME";
  private String PLAYTIME  = "PLAY_TIME";
  private String PAGE_ID   = "PAGE_ID";
  private String POSITION  = "POSITION";
  private String SHADOW    = "SHADOW";
  private int currPageName = -1;
  /**
   * filepath is private directory for Activity. Realize that this is a real dependency.
   * Expect dialog .mp3 files to be saved in private directory.
   */
  public Player(PlayerFragment playerFragment, String tag) {
    this.playerFragment = playerFragment;
    playHandler = new PlayHandler();
    this.fragTag = tag;
  }

  public void close() {
    this.playHandler.removeCallbacksAndMessages(null);
    this.playHandler = null;
  }

  @Override
  public boolean isReady (AppCompatActivity activity) {
    FragmentManager fm = activity.getSupportFragmentManager();
    if (null == fragTag)
      return false;
    return (null != fm.findFragmentByTag(fragTag));
  }

  @Override
  public void requestPause() {
    if (playerFragment != null) {
      if (playHandler != null)
        playHandler.removeCallbacksAndMessages(null);
      playerFragment.requestPause();
      currPageName = -1;
    }
  }

  @Override
  public void requestPause(int atNewPageName) {
    //atNewPageName is the page of the viewpage, not the currPageName that is playing.
    if (playerFragment != null && atNewPageName != currPageName) {
      if (playHandler != null)
        playHandler.removeCallbacksAndMessages(null);
      playerFragment.requestPause();
      currPageName = -1;
    }
  }

  public int requestStart(int startTime, int playTime, int pageId, int positionInPage, int shadow, int delayTime) {
    if (playerFragment != null) {
      if (playHandler != null) {
        playHandler.startAt(startTime, playTime, pageId, positionInPage, shadow, delayTime);
        return 1;
      }
      else
        return -2;
    }
    else {
      return -2;
    }
  }

  private void startPlaying(int startTime, int playTime, int pageId, int positionInPage, int shadow) {
    if (playerFragment != null) {
      playerFragment.requestStart(startTime, playTime, pageId, positionInPage, shadow);
      currPageName = pageId;
    }
  }

  private class PlayHandler extends Handler {
    @Override
    public void handleMessage (Message inputMessage) {
      Bundle bundle = inputMessage.getData();
      startPlaying(bundle.getInt(STARTTIME),
                   bundle.getInt(PLAYTIME),
                   bundle.getInt(PAGE_ID),
                   bundle.getInt(POSITION),
                   bundle.getInt(SHADOW));
    }

    private void startAt (int startTime, int playTime, Integer pageId, Integer position, int shadow, int waitTime) {
      Message message = obtainMessage();
      Bundle bundle = new Bundle();
      bundle.putInt(STARTTIME, startTime);
      bundle.putInt(PLAYTIME, playTime);
      bundle.putInt(PAGE_ID, pageId);
      bundle.putInt(POSITION, position);
      bundle.putInt(SHADOW, shadow);
      message.setData(bundle);
      sendMessageDelayed(message, waitTime);
    }
  }
}
