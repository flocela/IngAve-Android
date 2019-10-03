package com.olfybsppa.inglesaventurero.stageactivity;


import java.util.ArrayList;

public interface DirectorI {
  void changeVelocity(int[] times);
  void clearEndOfStory();
  void close();
  void open();
  void continueWithPage(int shadow);
  int  getCurrPageName();
  void notifyStoppedPlaying(int page, int position, int shadow);
  void receiveUserInput(ArrayList<String> lines);
  void setDoneWithInstructions(boolean done);
}
