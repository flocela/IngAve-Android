package com.olfybsppa.inglesaventurero.webscenelistactivity.collectors;


import android.content.ContentValues;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.collectors.Background;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.ImageCredit;
import com.olfybsppa.inglesaventurero.start.LinesCP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class CPScene {
  /* These attributes combined make up a complete scene.
     All Strings are necessary, although not required in the constructor.
     All attributes of a collection type are created and can be empty, they still
     must exist. In theory a scene may have no replies, but it wouldn't make much sense.
     So all attributes must exist and almost always must have data in them.
   */
  public String sceneName;
  public String englishTitle;
  public String spanishTitle;
  public String type1;
  public String type2;
  public String englishDescription;
  public String spanishDescription;
  public String englishVoiceInfo;
  public String spanishVoiceInfo;
  public ArrayList<CPPage> pages = new ArrayList<>();
  public HashMap<Integer, Background> backgroundsByPageName = new HashMap<>();
  public HashMap<String, HashSet<Attribution>> attributionsByBgName = new HashMap<String, HashSet<Attribution>>();
  public HashMap<Integer, ArrayList<CPHint>>   hintsByPageName = new HashMap<Integer, ArrayList<CPHint>>();
  public HashMap<Integer, ArrayList<CPReply>>  repliesByPageName = new HashMap<Integer, ArrayList<CPReply>>();
  public CPScene(String sceneName, String englishTitle, String spanishTitle, String type1, String type2) {
    this.sceneName = sceneName;
    this.englishTitle = englishTitle;
    this.spanishTitle = spanishTitle;
    this.type1 = type1;
    this.type2 = type2;
  }

  public void addPage(CPPage page) {
    pages.add(page);
  }

  public void addBackground(Integer pageName, Background background) {
    this.backgroundsByPageName.put(pageName, background);
  }

  public void addAttribution(String bgname, Attribution attr) {
    if (attributionsByBgName.containsKey(bgname)) {
      HashSet<Attribution> attributions = attributionsByBgName.get(bgname);
      if (!attributions.contains(attr)) {
        attributions.add(attr);
      }
    }
    else {
      HashSet<Attribution> attributions = new HashSet<>();
      attributions.add(attr);
      attributionsByBgName.put(bgname, attributions);
    }
  }

  public void addHint (Integer pageName, CPHint hint) {
    if (hintsByPageName.containsKey(pageName)) {
      ArrayList<CPHint> hints = hintsByPageName.get(pageName);
      hints.add(hint);
    }
    else {
      ArrayList<CPHint> hints = new ArrayList<CPHint>();
      hints.add(hint);
      hintsByPageName.put(pageName, hints);
    }
  }

  public void addReply (Integer pageName, CPReply reply) {
    if (repliesByPageName.containsKey(pageName)) {
      ArrayList<CPReply> replies = repliesByPageName.get(pageName);
      replies.add(reply);
    }
    else {
      ArrayList<CPReply> replies = new ArrayList<CPReply>();
      replies.add(reply);
      repliesByPageName.put(pageName, replies);
    }
  }

  // GETTERS

  public HashMap<String, HashSet<Attribution>> getAttrsByBkgdName() {
    return attributionsByBgName;
  }

  public String getEnglishVoiceInfo() {
    return englishVoiceInfo;
  }

  public String getEnglishTitle () {
    return this.englishTitle;
  }

  public HashMap<Integer, Background> getBkgdsByPageName() {
    return this.backgroundsByPageName;
  }

  public Collection<String> getBkgdFilenames () {
    ArrayList<String> filenames = new ArrayList<>();
    Iterator<Background> backgroundIterator = this.backgroundsByPageName.values().iterator();
    while (backgroundIterator.hasNext()) {
      filenames.add(backgroundIterator.next().getBackgroundFilename());
    }
    return filenames;
  }
  public String getEnglishDescription() {
    return englishDescription;
  }

  public HashMap<Integer, ArrayList<CPHint>> getHintsByPageName () {
    return this.hintsByPageName;
  }
  public HashSet<ImageCredit> getImageCredits () {
    Collection<HashSet<Attribution>> attributions = attributionsByBgName.values();
    Iterator<HashSet<Attribution>> iterator = attributions.iterator();
    HashSet<ImageCredit> imageCredits = new HashSet<>();
    while (iterator.hasNext()) {
      HashSet<Attribution> set = iterator.next();
      ArrayList<Attribution> attributionSet = new ArrayList<>(set);
      for (Attribution attr : attributionSet) {
        imageCredits.add(attr.constructImageCredit());
      }
    }
    return imageCredits;
  }

  public ArrayList<CPPage> getPages () {
    return this.pages;
  }

  public HashMap<Integer, ArrayList<CPReply>> getRepliesByPageName () {
    return this.repliesByPageName;
  }

  public String getSceneName () {
    return this.sceneName;
  }

  public String getSpanishDescription() {
    return spanishDescription;
  }
  public String getSpanishVoiceInfo() {
    return spanishVoiceInfo;
  }

  public String getSpanishTitle () {
    return this.spanishTitle;
  }

  public ContentValues getTitleTableContentValues() {
    ContentValues cV = new ContentValues();
    cV.put(LinesCP.scene_name, sceneName);
    cV.put(LinesCP.english_title, englishTitle);
    cV.put(LinesCP.spanish_title, spanishTitle);
    cV.put(LinesCP.type_1, type1);
    cV.put(LinesCP.type_2, type2);
    cV.put(LinesCP.english_description, englishDescription);
    cV.put(LinesCP.spanish_description, spanishDescription);
    cV.put(LinesCP.voice_attr_spn, spanishVoiceInfo);
    cV.put(LinesCP.voice_attr_eng, englishVoiceInfo);
    return cV;
  }

  public void setEnglishDescription(String englishDescription) {
    this.englishDescription = englishDescription;
  }

  public void setEnglishVoiceInfo(String englishVoiceInfo) {
    this.englishVoiceInfo = englishVoiceInfo;
  }

  public void setSpanishDescription(String spanishDescription) {
    this.spanishDescription = spanishDescription;
  }

  public void setSpanishVoiceInfo(String spanishVoiceInfo) {
    this.spanishVoiceInfo = spanishVoiceInfo;
  }
}
