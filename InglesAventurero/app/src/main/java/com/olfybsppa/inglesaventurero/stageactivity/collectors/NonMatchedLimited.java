package com.olfybsppa.inglesaventurero.stageactivity.collectors;


import java.util.ArrayList;

public class NonMatchedLimited implements Matchable.Limited {

  private ArrayList<String> responses = new ArrayList<String>();

  public NonMatchedLimited (ArrayList<String> responses) {
    this.responses = responses;
  }

  @Override
  public ArrayList<String> getResponses() {
    return responses;
  }

  @Override
  public String getMatchedWith() {
    return null;
  }

  @Override
  public boolean isMatched() {
    return false;
  }

  @Override
  public int getFollowingPage() {
    return -1;
  }

  @Override
  public int getGroupID() {
    return -1;
  }

  @Override
  public int getLinePosition() {
    return -1;
  }
}
