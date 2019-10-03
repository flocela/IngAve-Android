package com.olfybsppa.inglesaventurero.stageactivity;

import android.view.ViewGroup;

public interface FlasherI {
  void clearFlash(ViewGroup viewGroup);
  void flash(ViewGroup viewGroup);
  boolean isOn(ViewGroup viewGroup);
  void turnOnWords(ViewGroup viewGroup);
}
