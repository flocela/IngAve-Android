package com.olfybsppa.inglesaventurero.stageactivity;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.Page;

import java.util.ArrayList;

public interface CollViewUpdater {
  void clearHilights(int pageName);
  void clearHilights(int pageName, int groupId);
  void clearMatches(int pageName);
  void flash(int groupId, int waitTime);
  void hilightWords(int pageName, int groupId, int position);
  void match(Page page, ArrayList<String> lines);
  void scrollTo(int pageName, int groupID, int scrollTime);
}
