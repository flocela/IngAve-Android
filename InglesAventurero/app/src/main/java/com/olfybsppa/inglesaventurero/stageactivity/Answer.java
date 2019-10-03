package com.olfybsppa.inglesaventurero.stageactivity;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.Matchable;

import java.util.ArrayList;
import java.util.List;

/*
 * Case 1.a: answer matches a previously matched reply.
 * Case 1.b: answer matches a previously unmatched reply and now turn the page.
 * Case 1.c: answer matches a previously unmatched reply and that is the end of the story.
 * Case 1.d: answer matches a previously unmatched reply and now play the next hint.
 * Case 2.a: answer does not match. There are no replies in the page.
 * Case 2.b: answer does not match. None of the replies matched, there are unmatched replies.
 * Case 2.c: answer does not match. None of the replies matched, there are no unmatched replies.
 */
public class Answer {
  private boolean           matchablesExist;
  private Matchable.Limited limitedMatchable;
  private List<Matchable>   aptMatchables = new ArrayList<>();
  private boolean           matchableAlreadyMatched;
  private boolean           pageAlreadyMatched;

  public Answer (boolean matchablesExist) {
    this.matchablesExist = matchablesExist;
  }

  public boolean matchablesExist() {
    return matchablesExist;
  }
  public boolean isMatched () {
    if (null != limitedMatchable)
      return limitedMatchable.isMatched();
    return false;
  }

  public boolean pagePreviouslyMatched() {
    return pageAlreadyMatched;
  }

  public boolean particularMatchablePreviouslyMatched() {
    return matchableAlreadyMatched;
  }

  public int getGroupID () {
    if (null != limitedMatchable)
      return limitedMatchable.getGroupID();
    return -1;
  }

  public int getLinePosition () {
    if (null != limitedMatchable)
      return limitedMatchable.getLinePosition();
    return -1;
  }

  public ArrayList<String> getAptMatchableStrings () {
    ArrayList<String> replyStrings = new ArrayList<>();
    for (Matchable matchable : aptMatchables) {
      ArrayList<String> strings = matchable.getStringLines();
      replyStrings.addAll(strings);
    }
    return replyStrings;
  }

  public String getFirstResponse () {
    if (null != limitedMatchable) {
      ArrayList<String> responses = limitedMatchable.getResponses();
      if (null != responses && !responses.isEmpty())
        return responses.get(0);
    }
    return "";
  }

  public Answer setLimitedMatchable (Matchable.Limited limitedMatchable) {
    this.limitedMatchable = limitedMatchable;
    return this;
  }

  public void setMatchedLineAlreadyMatched(boolean alreadyMatched) {
    this.matchableAlreadyMatched = alreadyMatched;
  }
  public void setPagePrevMatched(boolean pageAlreadyMatched) {
    this.pageAlreadyMatched = pageAlreadyMatched;
  }

  public void setAptMatchables(ArrayList<Matchable> replies) {
    aptMatchables = replies;
  }

  public int getFollowingPage () {
    return this.limitedMatchable.getFollowingPage();
  }

  public static Answer REPLIES_DO_NOT_EXIST = new Answer(false);
}
