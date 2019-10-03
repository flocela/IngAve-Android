package com.olfybsppa.inglesaventurero.stageactivity.collectors;


public interface Measure {
  int     getFollowingPage();
  int     getName();
  boolean isFirst ();
  boolean isLast ();
  void    reset();
}
