package com.olfybsppa.inglesaventurero.stageactivity.collectors;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.olfybsppa.inglesaventurero.stageactivity.Actor;
import com.olfybsppa.inglesaventurero.stageactivity.LineView;
import com.olfybsppa.inglesaventurero.stageactivity.ReplyView;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.utils.Ez;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPReply;

import java.util.ArrayList;

public class Reply implements Matchable {
  private CPReply cpReply;
  private boolean matched = false;
  private boolean previouslyMatched;

  public static String[] columns = new String[] { //note page_name not included.
    LinesCP.pos_id,
    LinesCP.reply_group_id,
    LinesCP.next_page_name,
    LinesCP.normal_start_time,
    LinesCP.normal_end_time,
    LinesCP.slow_start_time,
    LinesCP.slow_end_time,
    LinesCP.wfw_english,
    LinesCP.wfw_spanish,
    LinesCP.english_phrase,
    LinesCP.spanish_phrase,
    LinesCP.eng_explanation,
    LinesCP.spn_explanation,
    LinesCP.regex
  };

  public Reply (Integer positionInPage, Integer replyGroup) {
    this.cpReply = new CPReply(positionInPage, replyGroup);
  }

  public String getEngExplanation() {
    return cpReply.getEngExplanation();
  }

  public String getEnglishPhrase() {
    return cpReply.getEnglishPhrase();
  }

  public int getFollowingPage() {
    return cpReply.getNextPage();
  }

  public int getGroupId() {
    return cpReply.getReplyGroup();
  }

  public ReplyInfo getInfo () {
    return new ReplyInfo(getGroupId(), getPosition(), matched);
  }

  @Override
  public LineView getLineView(Context context, Actor actor) {
    ReplyView replyView = new ReplyView(context, null, actor);
    replyView.initialize(this);
    return replyView;
  }

  @Override
  public ArrayList<Matchable> getMatchables() {
    ArrayList<Matchable> matches = new ArrayList<>();
    matches.add(this);
    return matches;
  }

  public int getNormalStartTime() {
    return cpReply.getNormalStartTime();
  }

  public int getNormalEndTime() {
    return cpReply.getNormalEndTime();
  }

  public int getPosition() {
    return cpReply.getPositionInPage();
  }

  public String getRegex () {
    return cpReply.getRegex();
  }

  public int getSlowEndTime () {
    return cpReply.getSlowEndTime();
  }

  public int getSlowStartTime () {
    return cpReply.getSlowStartTime();
  }

  public String getSpnExplanation() {
    return cpReply.getSpnExplanation();
  }

  public String getSpanishPhrase() {
    return cpReply.getSpanishPhrase();
  }

  public String getWFWEnglish() {
    return cpReply.getWfwEnglish();
  }

  public String getWFWSpanish() {
    return cpReply.getWfwSpanish();
  }

  @Override
  public ArrayList<String> getStringLines() {
    ArrayList<String> strings = new ArrayList<>();
    strings.add(getEnglishPhrase());
    return strings;
  }

  @Override
  public boolean isMatchable() {return true;}

  public boolean isMatched () {
    return matched;
  }

  public Reply setEngPhrase(String englishPhrase) {
    this.cpReply.setEnglishPhrase(englishPhrase);
    return this;
  }

  public Reply setEngExplanation(String explanation) {
    this.cpReply.setEngExplanation(explanation);
    return this;
  }

  public Reply setSpnExplanation(String explanation) {
    this.cpReply.setSpnExplanation(explanation);
    return this;
  }

  public Reply setFollowingPage(Integer nextPage) {
    if (nextPage != null)
      this.cpReply.setNextPage(nextPage);
    return this;
  }

  public Reply setMatched (boolean matched) {
    this.matched = matched;
    return this;
  }

  public Reply setNormalEndTime(int normalEndTime) {
    this.cpReply.setNormalEndTime(normalEndTime);
    return this;
  }

  public Reply setNormalStartTime(int normalStartTime) {
    this.cpReply.setNormalStartTime(normalStartTime);
    return this;
  }

  public Reply setPositionInPage (Integer positionInPage) {
    this.cpReply.setPositionInPage(positionInPage);
    return this;
  }

  public Reply setRegex (String regex) {
    this.cpReply.setRegex(regex);
    return this;
  }

  public Reply setSlowEndTime (int slowEndTime) {
    this.cpReply.setSlowEndTime(slowEndTime);
    return this;
  }

  public Reply setSpnPhrase(String spanishPhrase) {
    this.cpReply.setSpanishPhrase(spanishPhrase);
    return this;
  }

  public Reply setSlowStartTime (int slowStartTime) {
    this.cpReply.setSlowStartTime(slowStartTime);
    return this;
  }

  public Reply setTimes (int normalStartTime,
                         int normalEndTime,
                         int slowStartTime,
                         int slowEndTime) {
    this.cpReply.setNormalStartTime(normalStartTime);
    this.cpReply.setNormalEndTime(normalEndTime);
    this.cpReply.setSlowStartTime(slowStartTime);
    this.cpReply.setSlowEndTime(slowEndTime);
    return this;
  }

  public Reply setWFWEng(String wfwEnglish) {
    this.cpReply.setWFWEnglish(wfwEnglish);
    return this;
  }
  
  public Reply setWFWSpn (String wfwSpanish) {
    this.cpReply.setWFWSpanish(wfwSpanish);
    return this;
  }

  public void  clearMatch() {
    this.matched = false;
    this.previouslyMatched = false;
  }

  @Override
  public Matchable.Limited match (ArrayList<String> responses) {
    NonMatchedLimited nonMatchedLimited = new NonMatchedLimited(responses);
    String regex = getRegex();
    if (regex == null) {
      return nonMatchedLimited;
    }
    if (responses != null && !responses.isEmpty()) {
      boolean tempIsMatched = matched;
      for (String possible: responses) {
        if (possible.matches(regex) || possible.equals(getEnglishPhrase())) {
          matched = true;
          if (tempIsMatched)
            previouslyMatched = true;
          Reply.Limited replyL = new Reply.Limited();
          replyL.matchedWith = possible;
          replyL.possibleMatches = responses;
          return replyL;
        }
      }
    }
    return nonMatchedLimited;
  }

  @Override
  public boolean equals (Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass()!= this.getClass()) return false;

    Reply other = (Reply)obj;
    return (
        this.matched == other.matched &&
        this.previouslyMatched == other.previouslyMatched &&
        (cpReply == other.cpReply || (cpReply != null && cpReply.equals(other.cpReply)))
    );
  }

  @Override
  public int hashCode () {
    final int prime = 31;
    int result = 1;
    result = prime * result + (matched ? 1 : 0);
    result = prime * result + (previouslyMatched? 1: 0);
    result = prime * result + ((cpReply == null)? 0 : cpReply.hashCode());
    return result;
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
  public String toString () {
    return getEnglishPhrase() + ", " + getGroupId() + ", " + getPosition();
  }

  public class Limited implements Matchable.Limited {
    public ArrayList<String> possibleMatches; // have tried matching with these.
    public String matchedWith;

    @Override
    public boolean isMatched () {
      return Reply.this.isMatched();
    }

    @Override
    public String getMatchedWith() {
      return matchedWith;
    }

    @Override
    public int getFollowingPage () {
      if (!Reply.this.isMatched())
        return -1;
      return Reply.this.getFollowingPage();
    }

    @Override
    public ArrayList<String> getResponses() {
      return possibleMatches;
    }

    @Override
    public int getGroupID () {
      if (!Reply.this.isMatched())
        return -1;
      return cpReply.getReplyGroup();
    }

    @Override
    public int getLinePosition () {
      if (!Reply.this.isMatched())
        return -1;
      return cpReply.getPositionInPage();
    }
  }

  public static Reply extractReply (Cursor replyCursor) {
    Integer position   = getInt(replyCursor, LinesCP.pos_id);
    Integer replyGroup = getInt(replyCursor, LinesCP.reply_group_id);
    Reply reply = new Reply(position, replyGroup);
    reply.setFollowingPage(getInt(replyCursor, LinesCP.next_page_name));
    reply.setTimes(getInt(replyCursor, LinesCP.normal_start_time),
                   getInt(replyCursor, LinesCP.normal_end_time),
                   getInt(replyCursor, LinesCP.slow_start_time),
                   getInt(replyCursor, LinesCP.slow_end_time));
    reply.setEngPhrase(getString(replyCursor, LinesCP.english_phrase));
    reply.setSpnPhrase(getString(replyCursor, LinesCP.spanish_phrase));
    reply.setWFWEng(getString(replyCursor, LinesCP.wfw_english));
    reply.setWFWSpn(getString(replyCursor, LinesCP.wfw_spanish));
    reply.setRegex(getString(replyCursor, LinesCP.regex));
    reply.setEngExplanation(getString(replyCursor, LinesCP.eng_explanation));
    reply.setSpnExplanation(getString(replyCursor, LinesCP.spn_explanation));
    return reply;
  }

  public static ArrayList<Reply>  extractReplies (ContentResolver cR, Integer pageRowId) {
    ArrayList<Reply> replies = new ArrayList<>();
    Cursor repliesCursor = cR.query(LinesCP.replyTableUri,
                                    Reply.columns,
                                    Ez.where(LinesCP.page_id, "" + pageRowId),
                                    null,
                                    null);
    if (repliesCursor != null)  {
      if (repliesCursor.moveToFirst()) {
        while (!repliesCursor.isAfterLast()) {
          replies.add(extractReply(repliesCursor));
          repliesCursor.moveToNext();
        }
      }
      repliesCursor.close();
    }
    return replies;
  }
  
  public Reply clone() {
    Reply clone = new Reply(cpReply.getPositionInPage(), cpReply.getReplyGroup());
    clone.setFollowingPage(cpReply.getNextPage());
    clone.setNormalStartTime(cpReply.getNormalStartTime());
    clone.setNormalEndTime(cpReply.getNormalEndTime());
    clone.setSlowStartTime(cpReply.getSlowStartTime());
    clone.setSlowEndTime(cpReply.getSlowEndTime());
    clone.setEngPhrase(cpReply.getEnglishPhrase());
    clone.setSpnPhrase(cpReply.getSpanishPhrase());
    clone.setWFWEng(cpReply.getWfwEnglish());
    clone.setWFWSpn(this.cpReply.getWfwSpanish());
    clone.setEngExplanation(this.cpReply.getEngExplanation());
    clone.setSpnExplanation(this.cpReply.getSpnExplanation());
    clone.setRegex(this.cpReply.getRegex());
    clone.matched = this.matched;
    clone.previouslyMatched = this.previouslyMatched;
    return clone;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeByte((byte)(matched ? 1 : 0));
    dest.writeByte((byte)(previouslyMatched ? 1 : 0));
    dest.writeInt(cpReply.getPositionInPage());
    dest.writeInt(cpReply.getReplyGroup());
    dest.writeInt(cpReply.getNextPage());
    dest.writeInt(cpReply.getNormalStartTime());
    dest.writeInt(cpReply.getNormalEndTime());
    dest.writeInt(cpReply.getSlowStartTime());
    dest.writeInt(cpReply.getSlowEndTime());
    dest.writeString(cpReply.getEnglishPhrase());
    dest.writeString(cpReply.getWfwEnglish());
    dest.writeString(cpReply.getWfwSpanish());
    dest.writeString(cpReply.getSpanishPhrase());
    dest.writeString(cpReply.getEngExplanation());
    dest.writeString(cpReply.getSpnExplanation());
    dest.writeString(cpReply.getRegex());
  }

  public static final Parcelable.Creator<Reply> CREATOR
    = new Parcelable.Creator<Reply>() {
    public Reply createFromParcel(Parcel in) {
      return new Reply(in);
    }

    public Reply[] newArray(int size) {
      return new Reply[size];
    }
  };

  private Reply (Parcel in) {
    matched = in.readByte() != 0;
    previouslyMatched = in.readByte() != 0;
    cpReply = new CPReply(in.readInt(), in.readInt());
    cpReply.setNextPage(in.readInt());
    cpReply.setNormalStartTime(in.readInt());
    cpReply.setNormalEndTime(in.readInt());
    cpReply.setSlowStartTime(in.readInt());
    cpReply.setSlowEndTime(in.readInt());
    cpReply.setEnglishPhrase(in.readString());
    cpReply.setWFWEnglish(in.readString());
    cpReply.setWFWSpanish(in.readString());
    cpReply.setSpanishPhrase(in.readString());
    cpReply.setEngExplanation(in.readString());
    cpReply.setSpnExplanation(in.readString());
    cpReply.setRegex(in.readString());
  }

  private static int getInt (Cursor cursor, String columnName) {
    return cursor.getInt(cursor.getColumnIndex(columnName));
  }

  private static String getString (Cursor cursor, String columnName) {
    return cursor.getString(cursor.getColumnIndex(columnName));
  }
}
