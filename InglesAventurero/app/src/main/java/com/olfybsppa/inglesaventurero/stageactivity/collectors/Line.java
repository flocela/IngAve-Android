package com.olfybsppa.inglesaventurero.stageactivity.collectors;

import android.content.Context;
import android.os.Parcelable;

import com.olfybsppa.inglesaventurero.stageactivity.Actor;
import com.olfybsppa.inglesaventurero.stageactivity.LineView;

public interface Line extends Comparable<Line>, Parcelable {
  int      getGroupId();
  int      getFollowingPage();
  LineView getLineView(Context context, Actor actor);
  int      getNormalEndTime();
  int      getNormalStartTime();
  int      getPosition();
  int      getSlowStartTime();
  int      getSlowEndTime();
  boolean  isMatchable();

  interface Limited {
    int     getFollowingPage();
    int     getGroupID();
  }
}