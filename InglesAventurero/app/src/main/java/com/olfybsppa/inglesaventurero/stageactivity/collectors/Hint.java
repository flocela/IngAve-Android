package com.olfybsppa.inglesaventurero.stageactivity.collectors;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.olfybsppa.inglesaventurero.stageactivity.Actor;
import com.olfybsppa.inglesaventurero.stageactivity.HintView;
import com.olfybsppa.inglesaventurero.stageactivity.LineView;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.utils.Ez;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPHint;

import java.util.ArrayList;

public class Hint implements Leader {
  private CPHint cpHint;
  private boolean heard;

  public static String[] columns = new String[] {
    LinesCP.pos_id,
    LinesCP.hint_group_id,
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
    LinesCP.spn_explanation};

  public Hint (int positionInPage, int groupId) {
    this.cpHint = new CPHint(positionInPage, groupId);
  }

  public void setEngPhrase(String englishPhrase) {
    cpHint.setEnglishPhrase(englishPhrase);
  }

  public void setEngExplanation (String explanation) {
    cpHint.setEngExplanation(explanation);
  }

  public void setSpnExplanation (String explanation) {
    cpHint.setSpnExplanation(explanation);
  }

  public void setFollowingPage(Integer pageId) {
    if (pageId != null)
      cpHint.setNextPage(pageId);
  }

  @Override
  public void setHeard (boolean heard) {
    this.heard = heard;
  }

  public void setNormalEndTime(int normalEndTime) {
    cpHint.setNormalEndTime(normalEndTime);
  }

  public void setNormalStartTime(int normalStartTime) {
    cpHint.setNormalStartTime(normalStartTime);
  }

  public void setSlowEndTime(int slowEndTime) {
    cpHint.setSlowEndTime(slowEndTime);
  }

  public void setSlowStartTime(int slowStartTime) {
    cpHint.setSlowStartTime(slowStartTime);
  }

  public void setSpnPhrase(String spanishPhrase) {
    cpHint.setSpanishPhrase(spanishPhrase);
  }

  public void setTimes (int nStart, int nEnd, int sStart, int sEnd) {
    cpHint.setNormalStartTime(nStart);
    cpHint.setNormalEndTime(nEnd);
    cpHint.setSlowStartTime(sStart);
    cpHint.setSlowEndTime(sEnd);
  }

  public void setWFWEng(String wfwEnglish) {
    cpHint.setWFWEnglish(wfwEnglish);
  }

  public void setWFWSpn(String wfwSpanish) {
    cpHint.setWFWSpanish(wfwSpanish);
  }

  public String getEngExplanation() {
    return cpHint.getEngExplanation();
  }

  public String getSpnExplanation() {
    return cpHint.getSpnExplanation();
  }

  public String getEnglishPhrase() {
    return cpHint.getEnglishPhrase();
  }

  public int getFollowingPage() {
    return cpHint.getNextPage();
  }
  @Override
  public int getGroupId() {
    return cpHint.getGroupId();
  }

  public HintInfo getInfo () {
    return new HintInfo(getGroupId(), getPosition(), wasHeard());
  }

  @Override
  public LineView getLineView(Context context, Actor actor) {
    HintView view = new HintView(context, null, actor);
    view.initialize(this);
    return view;
  }

  public int getNormalEndTime() {
    return cpHint.getNormalEndTime();
  }

  public int getNormalStartTime() {
    return cpHint.getNormalStartTime();
  }

  public int getPosition() {
    return cpHint.getPositionInPage();
  }

  public int getSlowStartTime() {
    return cpHint.getSlowStartTime();
  }

  public int getSlowEndTime() {
    return cpHint.getSlowEndTime();
  }

  public String getSpanishPhrase() {
    return cpHint.getSpanishPhrase();
  }

  public String getWFWEnglish() {
    return cpHint.getWfwEnglish();
  }

  public String getWFWSpanish() {
    return cpHint.getWfwSpanish();
  }

  @Override
  public boolean isMatchable() {return false;}

  public boolean wasHeard () {
    return heard;
  }

  @Override
  public boolean equals (Object object) {
    if (object == this)
      return true;
    if (object == null || object.getClass() != this.getClass())
      return false;
    Hint other = (Hint)object;
    return (
      this.heard == other.heard &&
      (cpHint == other.cpHint || (cpHint != null && cpHint.equals(other.cpHint)))
    );
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (heard? 1 : 0);
    result = prime * result + ((cpHint == null)? 0 : cpHint.hashCode());
    return result;
  }

  public Hint clone() {
    Hint cloneH = new Hint(cpHint.getPositionInPage(), cpHint.getGroupId());
    cloneH.setFollowingPage(cpHint.getNextPage());
    cloneH.setHeard(heard);
    cloneH.setNormalStartTime(cpHint.getNormalStartTime());
    cloneH.setNormalEndTime(cpHint.getNormalEndTime());
    cloneH.setSlowStartTime(cpHint.getSlowStartTime());
    cloneH.setSlowEndTime(cpHint.getSlowEndTime());
    cloneH.setEngPhrase(cpHint.getEnglishPhrase());
    cloneH.setSpnPhrase(cpHint.getSpanishPhrase());
    cloneH.setWFWEng(cpHint.getWfwEnglish());
    cloneH.setWFWSpn(cpHint.getWfwSpanish());
    cloneH.setEngExplanation(cpHint.getEngExplanation());
    cloneH.setSpnExplanation(cpHint.getSpnExplanation());
    return cloneH;
  }

  public static ArrayList<Hint> extractHints (ContentResolver cR, int pageRowId) {
    ArrayList<Hint> hints = new ArrayList<>();
    Cursor hintsCursor = cR.query(LinesCP.hintTableUri,
                                  Hint.columns,
                                  Ez.where(LinesCP.page_id, "" + pageRowId),
                                  null,
                                  null);
    if (hintsCursor != null)
      if (hintsCursor.moveToFirst()) {
        while (!hintsCursor.isAfterLast()) {
          hints.add(extractHint(hintsCursor));
          hintsCursor.moveToNext();
      }
        hintsCursor.close();
    }
    return hints;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public String toString () {
    return getEnglishPhrase() + ", " + getGroupId() + ", " + getPosition();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeByte((byte)(heard ? 1 : 0));
    dest.writeInt(cpHint.getPositionInPage());
    dest.writeInt(cpHint.getGroupId());
    dest.writeInt(cpHint.getNextPage());
    dest.writeInt(cpHint.getNormalStartTime());
    dest.writeInt(cpHint.getNormalEndTime());
    dest.writeInt(cpHint.getSlowStartTime());
    dest.writeInt(cpHint.getSlowEndTime());
    dest.writeString(cpHint.getEnglishPhrase());
    dest.writeString(cpHint.getWfwEnglish());
    dest.writeString(cpHint.getWfwSpanish());
    dest.writeString(cpHint.getSpanishPhrase());
    dest.writeString(cpHint.getEngExplanation());
    dest.writeString(cpHint.getSpnExplanation());
  }

  public static final Parcelable.Creator<Hint> CREATOR
    = new Parcelable.Creator<Hint>() {
    public Hint createFromParcel(Parcel in) {
      return new Hint(in);
    }

    public Hint[] newArray(int size) {
      return new Hint[size];
    }
  };

  @Override
  public int compareTo(Line other) {
    if (this.getGroupId() < other.getGroupId())
      return -1;
    else if (this.getGroupId() > other.getGroupId())
      return 1;
    else {
      if (this.getPosition() < other.getPosition())
        return -1;
      else if (this.getPosition() == other.getPosition())
        return 0;
      else return 1;
    }
  }

  // Note: heard isn't in here! Db doesn't keep track of heard.
  public static Hint extractHint(Cursor cursor) {
    Integer position = getInt(cursor, LinesCP.pos_id);
    Integer groupId  = getInt(cursor, LinesCP.hint_group_id);
    Hint hint = new Hint(position, groupId);
    hint.setFollowingPage(getInt(cursor, LinesCP.next_page_name));
    hint.setTimes(getInt(cursor, LinesCP.normal_start_time),
                  getInt(cursor, LinesCP.normal_end_time),
                  getInt(cursor, LinesCP.slow_start_time),
                  getInt(cursor, LinesCP.slow_end_time));
    hint.setEngPhrase(getString(cursor, LinesCP.english_phrase));
    hint.setSpnPhrase(getString(cursor,LinesCP.spanish_phrase));
    hint.setWFWEng(getString(cursor, LinesCP.wfw_english));
    hint.setWFWSpn(getString(cursor, LinesCP.wfw_spanish));
    hint.setEngExplanation(getString(cursor, LinesCP.eng_explanation));
    hint.setSpnExplanation(getString(cursor, LinesCP.spn_explanation));
    return hint;
  }

  private Hint (Parcel in) {
    heard = in.readByte() != 0;
    int position = in.readInt();
    cpHint = new CPHint(position, in.readInt());
    cpHint.setNextPage(in.readInt());
    cpHint.setNormalStartTime(in.readInt());
    cpHint.setNormalEndTime(in.readInt());
    cpHint.setSlowStartTime(in.readInt());
    cpHint.setSlowEndTime(in.readInt());
    cpHint.setEnglishPhrase(in.readString());
    cpHint.setWFWEnglish(in.readString());
    cpHint.setWFWSpanish(in.readString());
    cpHint.setSpanishPhrase(in.readString());
    cpHint.setEngExplanation(in.readString());
    cpHint.setSpnExplanation(in.readString());
  }


  private static int getInt (Cursor cursor, String columnName) {
    return cursor.getInt(cursor.getColumnIndex(columnName));
  }

  private static String getString (Cursor cursor, String columnName) {
    return cursor.getString(cursor.getColumnIndex(columnName));
  }
}
