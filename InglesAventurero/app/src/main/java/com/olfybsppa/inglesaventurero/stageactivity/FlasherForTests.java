package com.olfybsppa.inglesaventurero.stageactivity;

import android.view.ViewGroup;

public class FlasherForTests implements FlasherI {
  @Override
  public void flash(ViewGroup viewGroup) {}

  @Override
  public void clearFlash(ViewGroup viewGroup) {}

  @Override
  public void turnOnWords(ViewGroup viewGroup) {}

  @Override
  public boolean isOn(ViewGroup viewGroup) {
    return false;
  }

}
