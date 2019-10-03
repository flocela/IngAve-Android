package com.olfybsppa.inglesaventurero.stageactivity.collectors;


import android.content.Context;

import com.olfybsppa.inglesaventurero.stageactivity.Actor;
import com.olfybsppa.inglesaventurero.stageactivity.Answer;
import com.olfybsppa.inglesaventurero.stageactivity.LineView;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static java.util.Collections.sort;

public class Page implements Measure  {

  private CPPage  cppage = new CPPage();
  private String  bgFilename = null;
  private ArrayList<Matchable> mMatchables = new ArrayList<>(); //in order.
  private ArrayList<Leader>    mLeaders    = new ArrayList<>(); //in order.
  private ArrayList<Line>      mLines      = new ArrayList<>(); //in order.
  private int lastMatchableGroupID = -1;
  private int firstMatchableGroupID = 101;
  public Page(int pageName,
              ArrayList<Leader> origLeaders,
              ArrayList<Matchable> origMatchables) {
    setPageName(pageName);
    if (null == origMatchables)
      origMatchables = new ArrayList<>();
    if (null == origLeaders)
      origLeaders = new ArrayList<>();
    mMatchables = groupSets(origMatchables);
    setLastMatchableGroupID();
    setFirstMatchableGroupID();
    mLeaders = origLeaders;
    mLines.addAll(mMatchables);
    mLines.addAll(mLeaders);
    sort(mLines);
  }

  /**
   * Only allowed to match with the earliest unmatched matchable or an earlier already
   * matched matchable. Not allowed to have a page with a non-matched matchable between
   * two matched matchables.
   * If there isn't a reply that matches any of the responses,
   * then return NonMatchedLimited.
   */
  public Answer acceptResponses(ArrayList<String> responses) {
    if (!matchablesExist())
      return Answer.REPLIES_DO_NOT_EXIST;

    Answer answer = new Answer(true);
    if (allAreMatched()) {
      answer.setPagePrevMatched(true);
      Matchable.Limited matched = matchAnyMatchable(responses);
      if (matched.isMatched()) {
        answer.setMatchedLineAlreadyMatched(true);
      }
      return answer.setLimitedMatchable(matched);
    }

    Matchable.Limited result = matchUnMatched(responses);
    if (result.isMatched()) {
      setHintsBeforeGroupIDAsHeard(result.getGroupID());
      return answer.setLimitedMatchable(result);
    }

    //the earliest unmatched matchable did not match, try to match an earlier matchable
    Matchable.Limited match = matchAlreadyMatched(responses);
    if (match.isMatched()) {
      answer.setMatchedLineAlreadyMatched(true);
    }
    else {
      answer.setAptMatchables(getAptMatchables()); //only gets set if no matches.
    }
    return answer.setLimitedMatchable(match);
  }

  public boolean endsWithLeader() {
    return (mLines.get(mLines.size()-1) instanceof Leader);
  }

  public Leader getAptLeader() {
    Matchable earliestUnmatched = getEarliestUnMatched();
    Leader lead = null;
    if (null != earliestUnmatched) {
      int unmatchedGroupID = earliestUnmatched.getGroupId();
      ArrayList<Leader> leads = getPrecedingLeads(unmatchedGroupID);
      for (Leader aLead : leads) {
        if (!aLead.wasHeard()) {
          lead = aLead;
          break;
        }
      }
    }
    else {
      ArrayList<Leader> leads = getFinalLeaders();
      if (leads.isEmpty())
        return lead;
      if (!mMatchables.isEmpty() &&
        leads.get(leads.size()-1).getGroupId() <
          mMatchables.get(mMatchables.size()-1).getGroupId())
        return lead;
      for (Leader aLead : leads) {
        if (!aLead.wasHeard()) {
          lead = aLead;
          break;
        }
      }
    }
    return lead;
  }


  public Leader getNextLeaderAt (int groupID) {
    if (mLeaders.size() <= 0) {
      return null;
    }
    for (int ii=0; ii<mLeaders.size(); ii++) {
      if (mLeaders.get(ii).getGroupId() == groupID) {
        return mLeaders.get(ii);
      }
    }
    return null;
  }

  public boolean isNextMatchableAt(int groupID) {
    if (mMatchables.size() <= 0)
      return false;
    for (int ii=0; ii<mMatchables.size(); ii++) {
      if (mMatchables.get(ii).getGroupId() == groupID)
        return true;
    }
    return false;
  }

  public ArrayList<Matchable> getAptMatchables () {
    ArrayList<Matchable> matchables = new ArrayList<>();
    Matchable earliestUnmatched = getEarliestUnMatched();
    if (null != earliestUnmatched)
      matchables = earliestUnmatched.getMatchables();
    return matchables;
  }

  public String getBackgroundFilename() {
    return this.bgFilename;
  }

  @Override
  public int getFollowingPage() {
    if (!matchablesExist()) {
      Leader lastLead = getLastLead();
      if (null == lastLead)
        return -1;
      return lastLead.getFollowingPage();
    }
    else {
      Matchable lastMatchable = getLastMatchable();
      if (null == lastMatchable)
        return -1;
      return lastMatchable.getFollowingPage();
    }
  }

  public ArrayList<Leader> getLeads() {
    return mLeaders;
  }

  public Line getLine(int groupId, int position) {
    for (Line line: mLines) {
      if (line.getGroupId() == groupId) {
        if (line instanceof ReplyLineSet) {
          ArrayList<Reply> replies = ((ReplyLineSet) line).getReplies();
          for (Reply reply : replies) {
            if (reply.getPosition() == position)
              return reply;
          }
        }
        else {
          return line;
        }
      }
    }
    return null;
  }

  public int getGroupIDOfPosition (int position) {
    for (Line line : mLines) {
      if (line.getPosition() == position) {
        return line.getGroupId();
      }
    }
    return -1;
  }

  public LineView getLineView (Context context, int groupID, Actor actor) {
    for (Line line : mLines) {
      if (line.getGroupId() == groupID)
        return line.getLineView(context, actor);
    }
    return null;
  }

  public List<LineView> getLineViews (Context context, Actor actor) {
    ArrayList<LineView> lineViews = new ArrayList<>();
    for (Line line : mLines) {
      lineViews.add(line.getLineView(context, actor));
    }
    return lineViews;
  }

  public ArrayList<Matchable> getMatchables () {
    return mMatchables;
  }

  @Override
  public int getName() {
    return cppage.getPageName();
  }

  public boolean onlyHasHints () {
    return mMatchables.isEmpty();
  }

  public boolean isFirst() {
    return cppage.isFirst();
  }

  public boolean isLast() {
    if (mLines.isEmpty())
      return false;
    if (getFollowingPage() == -1)
      return true;
    else
      return false;
  }

  public boolean isMatched () {
    for (Matchable matchable : mMatchables) {
      if (!matchable.isMatched())
        return false;
    }
    return true;
  }

  public void setAsFirstPage(boolean isFirst) {
    cppage.setAsFirst(isFirst);
  }

  public void setBackgroundFilename(String filename) {
    this.bgFilename = filename;
  }

  public void haveHeard (int positionInPage) {
    for (Leader leader : mLeaders) {
      if (leader.getPosition() == positionInPage)
        leader.setHeard(true);
    }
  }

  public void noticeGroupScroll (int groupID) {
    for (Line line : mLines) {
      if (line.getGroupId() == groupID && line instanceof LineGroup)
        ((LineGroup)line).noticeGroupScroll();
    }
  }

  public void noticeGroupScroll (int groupID, int position) {
    for (Line line : mLines) {
      if (line.getGroupId() == groupID && line instanceof LineGroup) {
        int origPosition = line.getPosition();
        if (line.getPosition() != position) {
          do {
            ((LineGroup)line).noticeGroupScroll();
          } while (line.getPosition() != position && origPosition != line.getPosition());
        }
      }
    }
  }

  public int getLastMatchableGroupID () {
    return this.lastMatchableGroupID;
  }

  @Override
  public void reset() {
    clearReplyGroups();
    clearHints();
  }

  public void inUseReset() {
    clearReplyGroups();
    for (Leader leader : mLeaders) {
      if (leader.getGroupId() > firstMatchableGroupID) {
       leader.setHeard(false);
      }
    }
  }

  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;

    Page other = (Page) obj;
    boolean returnBoolean =
      (cppage == other.cppage ||
        (cppage != null && cppage.equals(other.cppage))) &&
      (bgFilename == other.bgFilename ||
        (bgFilename != null && bgFilename.equals(other.bgFilename))) &&
      (mLines == other.mLines ||
        (mLines.equals(other.mLines)));
    return returnBoolean;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((cppage == null) ? 0 : cppage.hashCode());
    result = prime * result +
      ((bgFilename == null) ? 0 : bgFilename.hashCode());
    result = prime * result +
      ((mLines == null) ? 0 : mLines.hashCode());
    return result;
  }

  @Override
  public String toString() {
    return ""+cppage.getPageName();
  }

  // private methods //
  private ArrayList<Matchable> groupSets (ArrayList<Matchable> matchables) {
    HashMap<Integer, Matchable> matchableHashMap = new HashMap<>();

    HashMap<Integer, ArrayList<Reply>> repliesHash = new HashMap<>();
    for (Matchable matchable : matchables) {
      if (matchable instanceof Reply) {
        Reply reply = (Reply) matchable;
        if (repliesHash.containsKey(reply.getGroupId())) {
          ArrayList<Reply> replies = repliesHash.get(reply.getGroupId());
          replies.add(reply);
        } else {
          ArrayList<Reply> replies = new ArrayList<>();
          replies.add(reply);
          repliesHash.put(reply.getGroupId(), replies);
        }
      }
      else {
        matchableHashMap.put(matchable.getGroupId(), matchable); //TODO can't have a lone reply with the same groupID as this lineset. Also can't have two linesets with the same groupID, they will over run each other in the hashmap.
      }
    }

    Set<Integer> keys = repliesHash.keySet();
    Iterator<Integer> iterator = keys.iterator();
    while (iterator.hasNext()) {
      int groupId = iterator.next();
      ArrayList<Reply> replies = repliesHash.get(groupId);
      sort(replies);
      if (replies.size() > 1) {
        Matchable replyLineSet = new ReplyLineSet(replies);
        matchableHashMap.put(groupId, replyLineSet);
      }
      else {
        matchableHashMap.put(groupId, replies.get(0));
      }
    }
    ArrayList<Matchable> matchables1 = new ArrayList<>();
    matchables1.addAll(matchableHashMap.values());
    Collections.sort(matchables1);
    return matchables1;
  }

  private Matchable getEarliestUnMatched () {
    Matchable earliest = null;
    for (int ii=0; ii<mMatchables.size(); ii++) {
      if (!mMatchables.get(ii).isMatched()) {
        return mMatchables.get(ii);
      }
    }
    return earliest;
  }

  private Matchable.Limited matchUnMatched (ArrayList<String> responses) {
    Matchable earliestUnMatched = getEarliestUnMatched();
    return earliestUnMatched.match(responses);
  }

  private Page setPageName(Integer pageName) {
    cppage.setPageName(pageName);
    return this;
  }

  private int getGroupIDOfEarliestUnMatched() {
    return getEarliestUnMatched().getGroupId();
  }

  private boolean matchablesExist () {
    return !mMatchables.isEmpty();
  }

  private boolean allAreMatched () {
    return (null == getEarliestUnMatched());
  }

  // only try to match from index 0 upto and including latest.
  private Matchable.Limited matchEarliestAndNotGreaterThan(int latestGroupID,
                                                           ArrayList<String> responses) {
    if (latestGroupID == 0 || mMatchables.isEmpty())
      return new NonMatchedLimited(responses);
    else {
      int ii=0;
      Matchable matchable = mMatchables.get(0);
      while (matchable.getGroupId() < latestGroupID){
        Matchable.Limited currResult = matchable.match(responses);
        if (currResult.isMatched())
          return currResult;
        matchable = mMatchables.get(++ii);
      }
      return new NonMatchedLimited(responses);
    }
  }

  private Matchable.Limited matchAlreadyMatched (ArrayList<String> responses) {
    int earliestUnmatchedIndex = getGroupIDOfEarliestUnMatched();
    return matchEarliestAndNotGreaterThan(earliestUnmatchedIndex, responses);
  }

  private Matchable.Limited matchAnyMatchable(ArrayList<String> responses) {
    for (int ii=0; ii<mMatchables.size(); ii++) {
      Matchable matchable = mMatchables.get(ii);
      Matchable.Limited currResult = matchable.match(responses);
      if (currResult.isMatched())
        return currResult;
    }
    return new NonMatchedLimited(responses);
  }

  private void clearReplyGroups() {
    for (Matchable matchable : mMatchables) {
      matchable.clearMatch();
    }
  }

  private Leader getLastLead() {
    for (int ii = mLeaders.size()-1; ii>-1; ii--) {
        return mLeaders.get(ii);
    }
    return null;
  }

  private Matchable getLastMatchable () {
    if (mMatchables.isEmpty())
      return null;
    return mMatchables.get(mMatchables.size()-1);
  }

  private ArrayList<Leader> getPrecedingLeads(int groupId) {
    ArrayList<Leader> leads = new ArrayList<>();

    if (mLeaders.isEmpty())
      return leads;
    int lastLeaderGroupIdx = -1;
    int leadGroupIdx = mLeaders.size()-1;
    for (int ii=leadGroupIdx; ii>-1; ii--) {
      if (mLeaders.get(ii).getGroupId() < groupId) {
        lastLeaderGroupIdx = ii;
        break;
      }
    }
    if (lastLeaderGroupIdx == -1)
      return leads;

    int comparisonGroupID = mLeaders.get(lastLeaderGroupIdx).getGroupId();
    int firstLeaderGroupIdx = lastLeaderGroupIdx;
    for (int jj=lastLeaderGroupIdx; jj>-1; jj--) {
      int currGroupId = mLeaders.get(jj).getGroupId();
      if (!(currGroupId == comparisonGroupID || currGroupId == comparisonGroupID-1))
        break;
      firstLeaderGroupIdx = jj;
      comparisonGroupID = currGroupId;
    }

    for (int ii=firstLeaderGroupIdx; ii<=lastLeaderGroupIdx; ii++) {
      leads.add(mLeaders.get(ii));
    }
    return leads;
  }

  private ArrayList<Leader> getFinalLeaders() {
    ArrayList<Leader> leads = new ArrayList<>();
    if (mLines.isEmpty() || mLines.get(mLines.size()-1) instanceof Matchable || mLeaders.isEmpty())
      return leads;

    int comparisonGroupID = mLeaders.get(mLeaders.size()-1).getGroupId();
    int firstLeaderGroupIdx = mLeaders.size()-1;
    for (int ii=mLeaders.size()-1; ii>-1; ii--) {
      int currGroupId = mLeaders.get(ii).getGroupId();
      if (currGroupId != comparisonGroupID && currGroupId != comparisonGroupID-1)
        firstLeaderGroupIdx = ii;
      comparisonGroupID = currGroupId;
    }

    for (int ii=firstLeaderGroupIdx; ii<mLeaders.size(); ii++) {
      leads.add(mLeaders.get(ii));
    }
    return mLeaders;
  }

  private void setLastMatchableGroupID () {
    for (Matchable matchable : mMatchables) {
      if (matchable.getGroupId() > this.lastMatchableGroupID)
        this.lastMatchableGroupID = matchable.getGroupId();
    }
  }

  private void setFirstMatchableGroupID () {
    for (Matchable matchable : mMatchables) {
      if (matchable.getGroupId() < this.firstMatchableGroupID)
        this.firstMatchableGroupID = matchable.getGroupId();
    }
  }

  private void setHintsBeforeGroupIDAsHeard(int groupID) {
    for (Leader leader : mLeaders) {
      if (leader.getGroupId() < groupID)
        leader.setHeard(true);
    }
  }

  private void clearHints () {
    for (Leader leader : mLeaders) {
      leader.setHeard(false);
    }
  }
}
