package com.olfybsppa.inglesaventurero.stageactivity;


import android.support.v4.app.DialogFragment;

import java.util.ArrayList;

public interface Actor {
  void noticeGroupHorizontalScroll(int groupID);
  void noticeResponses(ArrayList<String> responses);
  void showDialog(DialogFragment dialog);
  void playAgain(int groupId, int position);
  void playSlow(int groupId, int position);
}