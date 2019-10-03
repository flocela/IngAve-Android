package com.olfybsppa.inglesaventurero.stageactivity;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

import com.olfybsppa.inglesaventurero.utils.Ez;

public class TestPlayer implements PlayerInterface {
  private Context context;
  private int hintPlayTime;
  String tag;
  int placement = 0;
  TestHandler handler;
  private static String STARTTIME = "STARTIME";
  private static String PLAYTIME  = "PLAYTIME";
  private static String PAGE_NAME   = "PAGE_NAME";
  private static String POSITION  = "POSITION";
  private static String SHADOW    = "SHADOW";

  public TestPlayer (Context context, int hintPlayTime, String tag) {
    this.context = context;
    this.hintPlayTime = hintPlayTime;
    this.tag = tag;
    handler = new TestHandler();
  }
  @Override
  public int requestStart(int startTime, int playTime, final int pageName, final int position, final int shadow, int delayTime) {
    Toast toast = Toast.makeText(context, "" + startTime + " for: " +playTime, Toast.LENGTH_SHORT);
    toast.setGravity(getPlacement(), 0, 0);
    toast.show();
    handler.pretendHintIsPlaying(pageName, position, shadow); //Simulates the hint finishing playing in hintPlayTime.
    return startTime;
  }

  @Override
  public void requestPause() {
    handler.removeCallbacks(null);
  }

  @Override
  public void requestPause(int pageName) {

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

  private int getPlacement() {
    int returnValue = placement;
    if (placement == 0)
      returnValue = Gravity.BOTTOM;
    else if (placement == 1)
      returnValue = Gravity.CENTER;
    else
      returnValue = Gravity.TOP;

    placement = placement + 1;
    if (placement > 2)
      placement = 0;

    return returnValue;
  }

}
