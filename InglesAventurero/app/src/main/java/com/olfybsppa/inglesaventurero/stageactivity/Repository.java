package com.olfybsppa.inglesaventurero.stageactivity;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.Page;

import java.util.ArrayList;

public class Repository implements ReadableRepository {
  private Tracker tracker;
  private Listener listener;

  public Repository(Tracker tracker) {
    this.tracker = tracker;
  }

  @Override
  public void addListener(Listener listener) {
    this.listener = listener;
  }

  public void clearEndOfStory(int position) {
    tracker.clearStoryFrom(position);
    listener.notifyDataSetChanged();
  }

  /**
   * If the responses are a match, then changes are made to the pages in the repository
   * and which pages are in the current story will change.
   * The listener's notifyDataSetChanged() method is called if the repository's
   * current story changes.
   * @param lines
   * @param position
   * @return
   */
  public Answer applyLines(ArrayList<String> lines, int position) {
    Page page = tracker.getPage(position);
    Answer answer = page.acceptResponses(lines);
    if (answer.isMatched()) {
      tracker.changeEnding(position, answer.getFollowingPage());
      listener.notifyDataSetChanged();
    }
    return answer;
  }

  @Override
  public int getCount() {
    return tracker.getCount();
  }

  @Override
  public Page getPage(int position) {
    return tracker.getPage(position);
  }

  public boolean isSceneCompleted() {
    return tracker.isCurrentStoryComplete();
  }

  @Override
  public Page getPageFromName(int pageName) {
    return tracker.getPageFromId(pageName);
  }
}