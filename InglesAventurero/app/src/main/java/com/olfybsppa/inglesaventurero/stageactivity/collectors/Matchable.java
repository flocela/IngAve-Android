package com.olfybsppa.inglesaventurero.stageactivity.collectors;

import java.util.ArrayList;

public interface Matchable extends Line {
  void                 clearMatch();
  boolean              isMatched();
  ArrayList<Matchable> getMatchables(); // TODO this really could be getMatchableStrings().
  ArrayList<String>    getStringLines();
  Limited              match (ArrayList<String> possible);

  interface Limited extends Line.Limited {
    int               getLinePosition();
    String            getMatchedWith();
    ArrayList<String> getResponses();
    boolean           isMatched ();
  }
}