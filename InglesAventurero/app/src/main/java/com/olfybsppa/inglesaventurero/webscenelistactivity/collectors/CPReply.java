package com.olfybsppa.inglesaventurero.webscenelistactivity.collectors;


import android.content.ContentValues;
import android.database.Cursor;

import com.olfybsppa.inglesaventurero.start.LinesCP;
// TESTED
public class CPReply {
  private int     positionInPage;
  private int     replyGroup = -1; // different from hint
  private int     nextPage = -1;
  private int    normalStartTime;
  private int    normalEndTime;
  private int    slowStartTime;
  private int    slowEndTime;
  private String englishPhrase;
  private String spanishPhrase;
  private String wfwEnglish;
  private String wfwSpanish;
  private String engExplanation;
  private String spnExplanation;
  private String regex;//different from hint

  public static String[] columns = new String[] {
    LinesCP.pos_id,
    LinesCP.page_name,
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

  public CPReply(Integer positionInPage, Integer replyGroup) {
    this.replyGroup = replyGroup;
    this.positionInPage = positionInPage;
  }

  // SETTERS

  public CPReply setGroupId (int groupId) {
    this.replyGroup = groupId;
    return this;
  }

  public CPReply setPositionInPage (Integer positionInPage) {
    this.positionInPage = positionInPage;
    return this;
  }

  public CPReply setNextPage (Integer toPage) {
    if (toPage != null)
      this.nextPage = toPage;
    return this;
  }

  public CPReply setNormalStartTime(int normalStartTime) {
    this.normalStartTime = normalStartTime;
    return this;
  }

  public CPReply setNormalEndTime(int normalEndTime) {
    this.normalEndTime = normalEndTime;
    return this;
  }

  public CPReply setSlowStartTime (int slowStartTime) {
    this.slowStartTime = slowStartTime;
    return this;
  }

  public CPReply setSlowEndTime (int slowEndTime) {
    this.slowEndTime = slowEndTime;
    return this;
  }

  public CPReply setTimes (int normalStartTime,
                           int normalEndTime,
                           int slowStartTime,
                           int slowEndTime) {
    this.normalStartTime = normalStartTime;
    this.normalEndTime = normalEndTime;
    this.slowStartTime = slowStartTime;
    this.slowEndTime = slowEndTime;
    return this;
  }

  public CPReply setEnglishPhrase(String englishPhrase) {
    this.englishPhrase = englishPhrase;
    return this;
  }

  public CPReply setSpanishPhrase (String spanishPhrase) {
    this.spanishPhrase = spanishPhrase;
    return this;
  }

  public CPReply setWFWEnglish(String wfwEnglish) {
    this.wfwEnglish = wfwEnglish;
    return this;
  }

  public CPReply setWFWSpanish (String wfwSpanish) {
    this.wfwSpanish = wfwSpanish;
    return this;
  }

  public CPReply setEngExplanation(String explanation) {
    this.engExplanation = explanation;
    return this;
  }

  public CPReply setSpnExplanation(String explanation) {
    this.spnExplanation = explanation;
    return this;
  }

  public CPReply setRegex (String regex) {
    this.regex = regex;
    return this;
  }

  // GETTERS

  public String getEnglishPhrase() {
    return this.englishPhrase;
  }

  public Integer getNextPage () {
    return this.nextPage;
  }

  public int getPositionInPage() {
    return positionInPage;
  }

  public Integer getReplyGroup () {
    return this.replyGroup;
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

  public int getNormalStartTime() {
    return normalStartTime;
  }

  public int getNormalEndTime() {
    return normalEndTime;
  }

  public int getSlowStartTime () {
    return slowStartTime;
  }

  public int getSlowEndTime () {
    return slowEndTime;
  }

  public String getEngExplanation() {
    return engExplanation;
  }

  public String getSpnExplanation() {
    return spnExplanation;
  }
  
  public String getRegex () {
    return regex;
  }

  // LOGIC METHODS

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;

    CPReply other = (CPReply)obj;
    return positionInPage  == other.positionInPage  &&
      replyGroup      == other.replyGroup      &&
      nextPage        == other.nextPage        &&
      normalStartTime == other.normalStartTime &&
      normalEndTime   == other.normalEndTime   &&
      slowStartTime   == other.slowStartTime   &&
      slowEndTime     == other.slowEndTime     &&
      (englishPhrase == other.englishPhrase ||
        (englishPhrase != null && englishPhrase.equals(other.englishPhrase))) &&
      (spanishPhrase == other.spanishPhrase ||
        (spanishPhrase != null && spanishPhrase.equals(other.spanishPhrase))) &&
      (wfwEnglish == other.wfwEnglish ||
        (wfwEnglish != null && wfwEnglish.equals(other.wfwEnglish)))          &&
      (wfwSpanish == other.wfwSpanish ||
        (wfwSpanish != null && wfwSpanish.equals(other.wfwSpanish)))          &&
      (engExplanation == other.engExplanation ||
        (engExplanation != null && engExplanation.equals(other.engExplanation))) &&
      (spnExplanation == other.spnExplanation ||
        (spnExplanation != null && spnExplanation.equals(other.spnExplanation))) &&
      (regex == other.regex ||
        (regex != null && regex.equals(other.regex)));
  }

  @Override
  public int hashCode () {
    final int prime = 31;
    int result = 1;
    result = prime * result + positionInPage;
    result = prime * result + replyGroup;
    result = prime * result + nextPage;
    result = prime * result + (int)normalStartTime;
    result = prime * result + (int)normalEndTime;
    result = prime * result + (int)slowStartTime;
    result = prime * result + (int)slowEndTime;
    result = prime * result + ((englishPhrase == null)? 0 : englishPhrase.hashCode());
    result = prime * result + ((spanishPhrase == null)? 0 : spanishPhrase.hashCode());
    result = prime * result + ((wfwEnglish == null)? 0 : wfwEnglish.hashCode());
    result = prime * result + ((wfwSpanish == null)? 0 : wfwSpanish.hashCode());
    result = prime * result + ((engExplanation == null)? 0 : engExplanation.hashCode());
    result = prime * result + ((spnExplanation == null)? 0 : spnExplanation.hashCode());
    result = prime * result + ((regex == null)? 0 : regex.hashCode());
    return result;

  }

  public CPReply clone() {
    CPReply clone = new CPReply(this.positionInPage, this.replyGroup);
    clone.setNextPage(this.nextPage);
    clone.setNormalStartTime(this.normalStartTime);
    clone.setNormalEndTime(this.normalEndTime);
    clone.setSlowStartTime(this.slowStartTime);
    clone.setSlowEndTime(this.slowEndTime);
    clone.setEnglishPhrase(this.englishPhrase);
    clone.setSpanishPhrase(this.spanishPhrase);
    clone.setWFWEnglish(this.wfwEnglish);
    clone.setWFWSpanish(this.wfwSpanish);
    clone.setEngExplanation(this.engExplanation);
    clone.setSpnExplanation(this.spnExplanation);
    clone.setRegex(this.regex);
    return clone;
  }

  public ContentValues getContentValues (int sceneId, int pageRowId) {
    ContentValues values = new ContentValues();
    values.put(LinesCP.scene_id, sceneId);
    values.put(LinesCP.page_id, pageRowId);
    values.put(LinesCP.pos_id, this.getPositionInPage());
    values.put(LinesCP.reply_group_id, this.getReplyGroup());
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
    values.put(LinesCP.regex, this.getRegex());
    return values;
  }

  public static CPReply extractCPReply(Cursor cursor) {
    Integer position = cursor.getInt(cursor.getColumnIndex(LinesCP.pos_id));
    Integer replyGroup = cursor.getInt(cursor.getColumnIndex(LinesCP.reply_group_id));
    CPReply cpReply = new CPReply(position, replyGroup);
    cpReply.setNextPage(cursor.getInt(cursor.getColumnIndex(LinesCP.next_page_name)));
    cpReply.setTimes(cursor.getInt(cursor.getColumnIndex(LinesCP.normal_start_time)),
      cursor.getInt(cursor.getColumnIndex(LinesCP.normal_end_time)),
      cursor.getInt(cursor.getColumnIndex(LinesCP.slow_start_time)),
      cursor.getInt(cursor.getColumnIndex(LinesCP.slow_end_time)));
    cpReply.setEnglishPhrase(cursor.getString(cursor.getColumnIndex(LinesCP.english_phrase)));
    cpReply.setSpanishPhrase(cursor.getString(cursor.getColumnIndex(LinesCP.spanish_phrase)));
    cpReply.setWFWEnglish(cursor.getString(cursor.getColumnIndex(LinesCP.wfw_english)));
    cpReply.setWFWSpanish(cursor.getString(cursor.getColumnIndex(LinesCP.wfw_spanish)));
    cpReply.setEngExplanation(cursor.getString(cursor.getColumnIndex(LinesCP.eng_explanation)));
    cpReply.setSpnExplanation(cursor.getString(cursor.getColumnIndex(LinesCP.spn_explanation)));
    cpReply.setRegex(cursor.getString(cursor.getColumnIndex(LinesCP.regex)));
    return cpReply;
  }
}
