package com.olfybsppa.inglesaventurero.stageactivity;

import android.support.v4.app.Fragment;


public abstract class PositionFragment extends Fragment {
  private int currPosition = -1; // get rid of this, and just have getCurrPos and setCurrPos be abstract.

  public int getCurrPosition () {
    return currPosition;
  }

  public abstract int getName ();

  public void setCurrPosition (int pos) {
    this.currPosition = pos;
  }

}
