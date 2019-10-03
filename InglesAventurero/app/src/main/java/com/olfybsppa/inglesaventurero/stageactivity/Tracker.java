package com.olfybsppa.inglesaventurero.stageactivity;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.Hint;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.HintInfo;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Leader;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Matchable;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Measure;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Page;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Reply;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.ReplyInfo;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.ReplyLineSet;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.ReplySetInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Tracker {

  private int currPosition = 0;
  private int calculatedCount = 0;
  private HashMap<Integer, Page> pagesHash;
  LinkedList<Page> currStory = new LinkedList<Page>();

  /**
   *
   * @param pagesHash each mapping is a pageName as an integer and maps to a Page.
   *                     Note that handing over a reference which is not copied inside
   *                     the method, could change pagesHash reference.
   */
  public Tracker(HashMap<Integer, Page> pagesHash) {
    this.pagesHash = pagesHash;
    currStory.add(findDesignatedFirstPage());
    setCurrScriptCountOnly();
  }

  // Setter methods

  public void setCurrentPosition(int position) {
    if (position >= this.calculatedCount) {
      throw new IllegalArgumentException("Can't move to a position that is " +
        "farther than the current Scripts' number of pages.");
    }
    this.currPosition = position;
  }

  // Deletes all measures after measurePosition. Then adds new measure at end of story.
  public void changeEnding (int measurePosition, Integer measureNameID) {
    if (measureNameID != -1 && !pagesHash.containsKey(measureNameID)) {
      throw new IllegalArgumentException("That measureNameID doesn't" +
        " match any pages in the story.");
    }
    if (measurePosition >= this.calculatedCount) {
      throw new IllegalArgumentException("That measure position is too far out. " +
        "It is further than the current Scripts' number of pages.");
    }
    cutOffAfter(measurePosition);
    increaseCurrStoryToPosition(measurePosition);
    if (currStory.size()-1 == measurePosition) {
      if (measureNameID != -1) {
        currStory.add(pagesHash.get(measureNameID));
      }
    }
    setCurrScriptCountOnly();
  }

  public void clearStoryFrom(int pos) {
    if (pos >= this.calculatedCount) {
      throw new IllegalArgumentException("That measure doesn't exist, it is " +
        "farther than the current Scripts' number of pages.");
    }
    setCurrentPosition(pos);
    while (currStory.size()-1 > pos) {
      Measure measure = currStory.removeLast();
      measure.reset();
    }
    currStory.getLast().inUseReset();
    HashSet<Integer> dontChange = new HashSet<>();
    for (Page page :currStory) {
      dontChange.add(page.getName());
    }
    Iterator<Integer> pageNamesIterator = pagesHash.keySet().iterator();
    while (pageNamesIterator.hasNext()) {
      int currPageName = pageNamesIterator.next();
      if (!dontChange.contains(currPageName)) {
        Page page = pagesHash.get(currPageName);
        page.reset();
      }
    }
  }

  // script is already made, but this sets up where the user is currently at.
  public void setCurrStory (ArrayList<Integer> wantedStory) {//TODO no one uses this.
    for (int ii=1; ii< wantedStory.size(); ii++) {
      changeEnding(ii - 1, wantedStory.get(ii));
    }
    setCurrentPosition(wantedStory.size() - 1);
  }

  // Getter methods

  public int getCurrPosition () {
    return currPosition;
  }

  public int getNextNameId () {
    Page currMeasure = getCurrMeasure();
    return currMeasure.getFollowingPage();
  }

  public int getCount() {
    return calculatedCount;
  }

  // TODO test again, had a bug.
  public boolean positionExists (int position) {
    if (position < 0 || position >= this.calculatedCount)
      return false;
    else
      return true;
  }

  public Page getPage(int position) {//TODO why is this called so many times when app starts.
    if (position >= this.calculatedCount) {
      return null;
    }
    increaseCurrStoryToPosition(position);
    if (position < currStory.size())
      return currStory.get(position);
    else
      return null;
  }

  public Page getCurrMeasure() {
    return this.getPage(currPosition);
  }

  public ArrayList<Integer> getCurrentPath() {
    ArrayList<Integer> path = new ArrayList<>();
    for (int ii=0; ii<=this.currPosition; ii++) {
      Page measure = currStory.get(ii);
      if (measure == null) return null;
      path.add(measure.getName());
    }
    return path;
  }

  public ArrayList<ReplyInfo> getReplyInfos () {
    ArrayList<ReplyInfo> replyInfos = new ArrayList<>();
    Iterator<Page> pages = pagesHash.values().iterator();
    while (pages.hasNext()) {
      Page page = pages.next();
      ArrayList<Matchable> matchables = page.getMatchables();
      for (Matchable matchable : matchables) {
        if (matchable instanceof Reply)
          replyInfos.add(((Reply)matchable).getInfo());
      }
    }
    return replyInfos;
  }

  public ArrayList<ReplySetInfo> getReplySetInfos () {
    ArrayList<ReplySetInfo> replySetInfos = new ArrayList<>();
    Iterator<Page> pages = pagesHash.values().iterator();
    while (pages.hasNext()) {
      Page page = pages.next();
      ArrayList<Matchable> matchables = page.getMatchables();
      for (Matchable matchable : matchables) {
        if (matchable instanceof ReplyLineSet)
          replySetInfos.add(((ReplyLineSet)matchable).getInfo());
      }
    }
    return replySetInfos;
  }

  public ArrayList<HintInfo> getHintInfos () {
    ArrayList<HintInfo> hintInfos = new ArrayList<>();
    Iterator<Page> pages = pagesHash.values().iterator();
    while (pages.hasNext()) {
      Page page = pages.next();
      ArrayList<Leader> leaders = page.getLeads();
      for (Leader lead : leaders) {
        if (lead instanceof Hint)
          hintInfos.add(((Hint)lead).getInfo());
      }
    }
    return hintInfos;
  }

  // NEEDS TESTING
  /**
   * returns -1 if not in script.
   */
  public int getPositionOfMeasure (int measureNameId) {
    for (int ii=0; ii< currStory.size(); ii++) {
      Page measure = currStory.get(ii);
      if (measure.getName() == measureNameId) {
        return ii;
      }
    }
    return -1;
  }

  public Page getPageFromId(int pageId) {
    return pagesHash.get(pageId);
  }

  public boolean isCurrentStoryComplete () {
    Page lastPage = currStory.getLast();
    if (!lastPage.isLast())
      return false;
    else {
      for (Page page : currStory) {
        if (!page.isMatched())
          return false;
      }
    }
    return true;
  }

  // Doesn't actually add pages to script, but counts how many pages the script
  // should have.
  // Add pages to script through lazy instantiation later.
  private void setCurrScriptCountOnly () {
    calculatedCount = currStory.size();
    Page currLastMeasure = currStory.getLast();
    while (!currLastMeasure.isLast()) {
      currLastMeasure = pagesHash.get(new Integer(currLastMeasure.getFollowingPage()));
      calculatedCount++;
    }
  }

  private Page findDesignatedFirstPage () {
    for (Page measure : pagesHash.values()) {
      if (measure.isFirst()) return measure;
    }
    return null; // should never happen.
  }

  private void increaseCurrStoryToPosition (int position) {
    Page currLastMeasure = currStory.get(currStory.size()-1);
    Integer nextDefaultMeasureID = currLastMeasure.getFollowingPage();
    while (nextDefaultMeasureID != null &&
           -1 != currLastMeasure.getFollowingPage() &&
           position > currStory.size() - 1) {
      nextDefaultMeasureID = currLastMeasure.getFollowingPage();
      currLastMeasure = pagesHash.get(nextDefaultMeasureID);
      currStory.add(currLastMeasure);
    }
  }

  private void cutOffAfter(int measurePosition) {
    while (currStory.size()-1 > measurePosition) {
      currStory.removeLast();
    }
  }
}
