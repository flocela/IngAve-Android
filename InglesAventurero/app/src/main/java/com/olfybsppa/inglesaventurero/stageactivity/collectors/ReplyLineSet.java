package com.olfybsppa.inglesaventurero.stageactivity.collectors;


import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.olfybsppa.inglesaventurero.stageactivity.Actor;
import com.olfybsppa.inglesaventurero.stageactivity.LineView;
import com.olfybsppa.inglesaventurero.stageactivity.RLineSetView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReplyLineSet implements Matchable, LineGroup{

  private int lastViewed = 0;
  private ArrayList<Reply> mReplies = new ArrayList<>();
  private int followingPage = -2; //-1 is taken up as being last page.

  public ReplyLineSet(List<Reply> replies) {
    for (Reply reply : replies) {
      mReplies.add(reply);
    }
    Collections.sort(mReplies);
  }

  @Override
  public int getFollowingPage() {
    if (followingPage != -2)
      return followingPage;
    else
      return mReplies.get(lastViewed).getFollowingPage();
  }

  @Override
  public int getGroupId() {
    return mReplies.get(0).getGroupId();
  }

  public ReplySetInfo getInfo () {
    Reply reply = mReplies.get(lastViewed);
    return new ReplySetInfo(reply.getGroupId(), reply.getPosition());
  }

  @Override
  public LineView getLineView(Context context, Actor actor) {
    Reply reply = mReplies.get(lastViewed);
    RLineSetView view = new RLineSetView(context, null, actor);
    view.initialize(reply);
    return view;
  }

  @Override
  public ArrayList<Matchable> getMatchables() {
    ArrayList<Matchable> matchables = new ArrayList<>();
    for (Reply reply: mReplies) {
      matchables.add(reply);
    }
    return matchables;
  }

  @Override
  public int getNormalEndTime() {
    return mReplies.get(lastViewed).getNormalEndTime();
  }

  @Override
  public int getNormalStartTime() {
    return mReplies.get(lastViewed).getNormalStartTime();
  }

  @Override
  public int getSlowStartTime() {
    return mReplies.get(lastViewed).getSlowStartTime();
  }

  @Override
  public int getSlowEndTime() {
    return mReplies.get(lastViewed).getSlowEndTime();
  }

  @Override
  public int getPosition() {
    return mReplies.get(lastViewed).getPosition();
  }

  public ArrayList<Reply> getReplies() {
    ArrayList<Reply> replies = new ArrayList<>();
    for (Line line: mReplies) {
      if (line instanceof Reply) {
        replies.add((Reply) line);
      }
    }
    return replies;
  }

  public Reply[] getRepliesAsArray () {
    Reply[] replies = new Reply[mReplies.size()];
    for (int ii = 0; ii<mReplies.size(); ii++) {
      replies[ii] = mReplies.get(ii);
    }
    return replies;
  }

  @Override
  public ArrayList<String> getStringLines() {
    ArrayList<String> replies = new ArrayList<>();
    for (Reply reply : mReplies)  {
      replies.addAll(reply.getStringLines());
    }
    return replies;
  }

  @Override
  public boolean isMatchable () {
    return true;
  }

  public boolean isMatched () {
    for (Reply reply: mReplies) {
      if (reply.isMatched())
        return true;
    }
    return false;
  }

  public void setLastViewed (int position) {
    for (int ii=0; ii<mReplies.size(); ii++) {
      if (mReplies.get(ii).getPosition() == position) {
        lastViewed = ii;
        break;
      }
    }
  }

  @Override
  public Matchable.Limited match (ArrayList<String> possible) {
    if (null == possible || possible.isEmpty()) {
      return new NonMatchedLimited(new ArrayList<String>());
    }
    for (Reply reply : mReplies) {
      Matchable.Limited matchedL = reply.match(possible);
      if (matchedL.isMatched()) {
        followingPage = matchedL.getFollowingPage();
        return matchedL;
      }
    }
    NonMatchedLimited nonMatchedLimited = new NonMatchedLimited(possible);
    return nonMatchedLimited;
  }

  @Override
  public void clearMatch () {
    for (Reply reply : mReplies) {
      reply.clearMatch();
    }
  }

  @Override
  public int compareTo(Line other) {
    if (this.getGroupId() < other.getGroupId())
      return -1;
    else if (this.getGroupId() > other.getGroupId())
      return 1;
    else {
      if (this.getPosition() < other.getPosition())
        return -1;
      else if (this.getPosition() > other.getPosition())
        return 1;
      else return 0;
    }
  }

  @Override
  public void noticeGroupScroll() {
    this.increaseLastViewedByOne();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeList(mReplies);
    dest.writeInt(lastViewed);
    dest.writeInt(followingPage);
  }

  public static final Parcelable.Creator<ReplyLineSet> CREATOR
    = new Parcelable.Creator<ReplyLineSet>() {
    public ReplyLineSet createFromParcel(Parcel in) {
      ReplyLineSet lineSet =
        new ReplyLineSet(in.readArrayList(Reply.class.getClassLoader()));
      lineSet.lastViewed = in.readInt();
      lineSet.followingPage = in.readInt();
      return lineSet;
    }

    public ReplyLineSet[] newArray(int size) {
      return new ReplyLineSet[size];
    }
  };

  @Override
  public boolean equals (Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass()!= this.getClass()) return false;
    ReplyLineSet other = (ReplyLineSet)obj;
    return (
      this.lastViewed == other.lastViewed &&
      this.followingPage == other.followingPage &&
        (mReplies == other.mReplies ||
          mReplies != null && mReplies.equals(other.mReplies))
      );
  }

  @Override
  public int hashCode () {
    final int prime = 31;
    int result = 1;
    result = prime * result + lastViewed;
    result = prime * result + followingPage;
    for (Reply reply : mReplies) {
      result = prime * result + ((reply == null)? 0 : reply.hashCode());
    }
    return result;
  }

  @Override
  public String toString () {
    return mReplies.get(lastViewed).getEnglishPhrase() + ", " + getGroupId() + ", " + getPosition();
  }

  private void increaseLastViewedByOne() {
    if (lastViewed == mReplies.size() - 1)
      lastViewed = 0;
    else
      lastViewed = lastViewed + 1;
  }
}