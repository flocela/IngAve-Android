package com.olfybsppa.inglesaventurero.stageactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.olfybsppa.inglesaventurero.stageactivity.dialog.EspressoDialog;
import com.olfybsppa.inglesaventurero.utils.Ez;


public class EspressoPlayer implements PlayerInterface {
  private DialogPresenter notifier;
  private String tag;

  public EspressoPlayer(DialogPresenter notifier, String tag) {
    this.tag = tag;
    this.notifier = notifier;
  }

  @Override
  public int requestStart(int startTime,
                          int playTime,
                          final int pageId,
                          final int position,
                          int shadow,
                          int delayTime) {
    Bundle bundle = Ez.oneStringBundle(EspressoDialog.TIMES, "" + startTime + " for: " +playTime);
    bundle.putInt(EspressoDialog.PAGE_ID, pageId);
    bundle.putInt(EspressoDialog.POSITION, position);
    bundle.putInt(EspressoDialog.SHADOW, shadow);
    bundle.putString(EspressoDialog.TAG, tag);
    notifier.showDialog(EspressoDialog.newInstance(bundle));
    return startTime;
  }

  @Override
  public void close() {}

  @Override
  public void requestPause() {}

  @Override
  public void requestPause(int pageName) {}

  @Override
  public boolean isReady(AppCompatActivity activity) {
    return true;
  }
}
