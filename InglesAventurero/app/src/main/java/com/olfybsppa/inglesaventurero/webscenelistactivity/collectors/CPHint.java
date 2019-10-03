package com.olfybsppa.inglesaventurero.webscenelistactivity.collectors;


import android.content.ContentValues;
import android.database.Cursor;

import com.olfybsppa.inglesaventurero.start.LinesCP;

public class CPHint {
  // TESTING DONE
  private int    positionInPage  = -1;
  private int    groupId = -1;
  private int    nextPage        = -1;
  private int    normalStartTime = -1;
  private int    normalEndTime   = -1;
  private int    slowStartTime   = -1;
  private int    slowEndTime     = -1;
  private String englishPhrase;
  private String spanishPhrase;
  private String wfwEnglish;
  private String wfwSpanish;
  private String spnExplanation;
  private String engExplanation;

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

  public CPHint(int positionInPage, int groupId) {
    this.positionInPage = positionInPage;
    this.groupId = groupId;
  }

  // SETTERS
  
  public void setPositionInPage (int positionInPage) {
    this.positionInPage = positionInPage;
  }

  public void setNextPage(Integer pageId) {
    if (pageId != null)
     this.nextPage = pageId;
  }

  public void setNormalStartTime(int normalStartTime) {
    this.normalStartTime = normalStartTime;
  }

  public void setNormalEndTime(int normalEndTime) {
    this.normalEndTime = normalEndTime;
  }

  public void setSlowStartTime(int slowStartTime) {
    this.slowStartTime = slowStartTime;
  }

  
  public void setSlowEndTime(int slowEndTime) {
    this.slowEndTime = slowEndTime;
  }

  public void setTimes (int nStart, int nEnd, int sStart, int sEnd) {
    this.normalStartTime = nStart;
    this.normalEndTime = nEnd;
    this.slowStartTime = sStart;
    this.slowEndTime = sEnd;
  }

  public void setEnglishPhrase(String englishPhrase) {
    this.englishPhrase = englishPhrase;
  }

  public void setSpanishPhrase(String spanishPhrase) {
    this.spanishPhrase = spanishPhrase;
  }

  public void setWFWEnglish (String wfwEnglish) {
    this.wfwEnglish = wfwEnglish;
  }

  public void setWFWSpanish (String wfwSpanish) {
    this.wfwSpanish = wfwSpanish;
  }

  public void setSpnExplanation(String explanation) {
    this.spnExplanation = explanation;
  }

  public void setEngExplanation(String explanation) {
    this.engExplanation = explanation;
  }
  // GETTERS

  public int getPositionInPage() {
    return positionInPage;
  }

  public int getGroupId () {
    return this.groupId;
  }

  public Integer getNextPage() {
    return this.nextPage;
  }

  public int getNormalStartTime() {
    return normalStartTime;
  }

  public int getNormalEndTime() {
    return normalEndTime;
  }

  public int getSlowStartTime() {
    return slowStartTime;
  }

  public int getSlowEndTime() {
    return slowEndTime;
  }

  public String getEnglishPhrase() {
    return englishPhrase;
  }

  public String getWfwEnglish() {
    return wfwEnglish;
  }

  public String getWfwSpanish() {
    return wfwSpanish;
  }

  public String getSpanishPhrase() {
    return spanishPhrase;
  }

  public String getEngExplanation () {
    return engExplanation;
  }

  public String getSpnExplanation () { return spnExplanation; }

  public CPHint clone() {
    CPHint hint = new CPHint(this.positionInPage, this.groupId);
    hint.setNextPage(this.nextPage);
    hint.setNormalStartTime(this.normalStartTime);
    hint.setNormalEndTime(this.normalEndTime);
    hint.setSlowStartTime(this.slowStartTime);
    hint.setSlowEndTime(this.slowEndTime);
    hint.setEnglishPhrase(this.englishPhrase);
    hint.setSpanishPhrase(this.spanishPhrase);
    hint.setWFWEnglish(this.wfwEnglish);
    hint.setWFWSpanish(this.wfwSpanish);
    hint.setEngExplanation(this.engExplanation);
    hint.setSpnExplanation(this.spnExplanation);
    return hint;
  }

  public ContentValues getContentValues (int sceneId, int pageRowId) {
    ContentValues values = new ContentValues();
    values.put(LinesCP.page_id, pageRowId);
    values.put(LinesCP.scene_id, sceneId);
    values.put(LinesCP.hint_group_id, this.groupId);
    values.put(LinesCP.pos_id, this.getPositionInPage());
    values.put(LinesCP.next_page_name, this.getNextPage());
    values.put(LinesCP.normal_start_time, this.getNormalStartTime());
    values.put(LinesCP.normal_end_time, this.getNormalEndTime());
    values.put(LinesCP.slow_start_time, this.getSlowStartTime());
    values.put(LinesCP.slow_end_time, this.getSlowEndTime());
    values.put(LinesCP.wfw_english, this.getWfwEnglish());
    values.put(LinesCP.wfw_spanish, this.getWfwSpanish());
    values.put(LinesCP.english_phrase, this.getEnglishPhrase());
    values.put(LinesCP.spanish_phrase, this.getSpanishPhrase());
    values.put(LinesCP.eng_explanation, this.getEngExplanation());
    values.put(LinesCP.spn_explanation, this.getSpnExplanation());
    return values;
  }

  public static CPHint extractCPHint(Cursor cursor) {
    Integer position = cursor.getInt(cursor.getColumnIndex(LinesCP.pos_id));
    Integer groupId = cursor.getInt(cursor.getColumnIndex(LinesCP.hint_group_id));
    CPHint hint = new CPHint(position, groupId);
    hint.setNextPage(cursor.getInt(cursor.getColumnIndex(LinesCP.next_page_name)));
    hint.setTimes(cursor.getInt(cursor.getColumnIndex(LinesCP.normal_start_time)),
      cursor.getInt(cursor.getColumnIndex(LinesCP.normal_end_time)),
      cursor.getInt(cursor.getColumnIndex(LinesCP.slow_start_time)),
      cursor.getInt(cursor.getColumnIndex(LinesCP.slow_end_time)));
    hint.setEnglishPhrase(cursor.getString(cursor.getColumnIndex(LinesCP.english_phrase)));
    hint.setSpanishPhrase(cursor.getString(cursor.getColumnIndex(LinesCP.spanish_phrase)));
    hint.setWFWEnglish(cursor.getString(cursor.getColumnIndex(LinesCP.wfw_english)));
    hint.setWFWSpanish(cursor.getString(cursor.getColumnIndex(LinesCP.wfw_spanish)));
    hint.setEngExplanation(cursor.getString(cursor.getColumnIndex(LinesCP.eng_explanation)));
    hint.setSpnExplanation(cursor.getString(cursor.getColumnIndex(LinesCP.spn_explanation)));
    return hint;
  }

  @Override
  public boolean equals (Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    CPHint other = (CPHint)obj;
    return positionInPage  == other.positionInPage  &&
           groupId         == other.groupId         &&
           nextPage        == other.nextPage        &&
           normalStartTime == other.normalStartTime &&
           normalEndTime   == other.normalEndTime   &&
           slowStartTime   == other.slowStartTime   &&
           slowEndTime     == other.slowEndTime     &&
           (englishPhrase == other.englishPhrase ||
             (englishPhrase !=null && englishPhrase.equals(other.englishPhrase)))  &&
           (spanishPhrase == other.spanishPhrase ||
             (spanishPhrase != null && spanishPhrase.equals(other.spanishPhrase)))  &&
           (wfwEnglish == other.wfwEnglish ||
             (wfwEnglish != null && wfwEnglish.equals(other.wfwEnglish)))       &&
           (wfwSpanish == other.wfwSpanish ||
             (wfwSpanish != null && wfwSpanish.equals(other.wfwSpanish)))       &&
           (engExplanation == other.engExplanation ||
             (engExplanation != null && engExplanation.equals(other.engExplanation))) &&
           (spnExplanation == other.spnExplanation ||
             (spnExplanation != null && spnExplanation.equals(other.spnExplanation)));
  }

  @Override
  public int hashCode () {
    final int prime = 31;
    int result = 1;
    result = prime * result + positionInPage;
    result = prime * result + groupId;
    result = prime * result + nextPage;
    result = (int)(prime * result + normalStartTime);
    result = (int)(prime * result + normalEndTime);
    result = (int)(prime * result + slowStartTime);
    result = (int)(prime * result + slowEndTime);
    result = prime * result + ((englishPhrase == null)? 0 : englishPhrase.hashCode());
    result = prime * result + ((spanishPhrase == null)? 0 : spanishPhrase.hashCode());
    result = prime * result + ((wfwEnglish == null)? 0 : wfwEnglish.hashCode());
    result = prime * result + ((wfwSpanish == null)? 0 : wfwSpanish.hashCode());
    result = prime * result + ((engExplanation == null)? 0 : engExplanation.hashCode());
    result = prime * result + ((spnExplanation == null)? 0 : spnExplanation.hashCode());
    return result;
  }
}
