package com.olfybsppa.inglesaventurero.stageactivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.olfybsppa.inglesaventurero.utils.Ez;

public class SilentPlayer implements PlayerInterface {
  private Context context;
  private int hintPlayTime;
  private String tag;
  TestHandler handler;
  private static String STARTTIME = "STARTIME";
  private static String PLAYTIME  = "PLAYTIME";
  private static String PAGE_NAME = "PAGE_NAME";
  private static String POSITION  = "POSITION";
  private static String SHADOW    = "SHADOW";

  public SilentPlayer(Context context, int time, String tag) {
    this.context = context;
    this.hintPlayTime = time;
    this.tag = tag;
    handler = new TestHandler();
  }

  @Override
  public int requestStart(int startTime, int playTime, final int pageName, final int position, final int shadow, int delayTime) {
    if (handler != null) {
      handler.pretendHintIsPlaying(pageName, position, shadow); //Simulates the hint finishing playing in hintPlayTime.//Simulates the hint finishing playing in hintPlayTime.
    }
    return startTime;
  }

  @Override
  public void close() {
    this.handler.removeCallbacksAndMessages(null);
    this.handler = null;
  }

  @Override
  public boolean isReady(AppCompatActivity activity) {
    return true;
  }

  @Override
  public void requestPause() {
    handler.removeCallbacks(null);
  }

  @Override
  public void requestPause(int pageName) {

  }

  private class TestHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      Bundle bundle = msg.getData();
      ((StageActivity)context).notifyProgress(tag,
        false,
        100,
        "" + bundle.getInt(PAGE_NAME) +
          ","+bundle.getInt(POSITION) +
          ","+bundle.get(SHADOW));
    }

    public void pretendHintIsPlaying (int pageName, int position, int shadow) {
      Bundle bundle = Ez.oneIntBundle(PAGE_NAME, pageName);
      bundle.putInt(POSITION, position);
      bundle.putInt(SHADOW, shadow);
      Message message = obtainMessage();
      message.setData(bundle);
      sendMessageDelayed(message, hintPlayTime);

    }
  }
}
