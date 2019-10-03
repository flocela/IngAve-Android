package com.olfybsppa.inglesaventurero.openingactivity;


import android.os.Bundle;

public interface NewDataHerald {

  void addListener(Listener listener);

  interface Listener {
    void gotsNewData (int numOfDataPoints);
    void errorGettingData (Bundle bundle);
  }
}