package com.olfybsppa.inglesaventurero.collectors;

import java.util.Comparator;

public class SceneInfo {
  private String englishTitle;
  private String spanishTitle;
  private String filename;
  private Integer webId;
  private String  difficulty;
  private String englishDescription;
  private String spanishDescription;
  private String sceneName;
  private boolean completed;
  private boolean downloaded;

  public SceneInfo () {}

  public boolean getCompleted() {
    return completed;
  }

  public String getDifficulty() {
    return difficulty;
  }

  public boolean getDownloaded() {
    return downloaded;
  }

  public String getEnglishDescription() {
    return englishDescription;
  }

  public String getEnglishTitle () {
    return this.englishTitle;
  }

  public String getFilename () {
    return this.filename;
  }

  public String getSceneName() {
    return sceneName;
  }

  public String getSpanishDescription() {
    return spanishDescription;
  }

  public String getSpanishTitle () {
    return this.spanishTitle;
  }

  public Integer getWebId () {
    return this.webId;
  }

  public void setCompleted (boolean completed) {
    this.completed = completed;
  }

  public void setDifficulty(String difficulty) {
    this.difficulty = difficulty;
  }

  public void setDownloaded (boolean downloaded) {
    this.downloaded = downloaded;
  }

  public void setEnglishDescription(String englishDescription) {
    this.englishDescription = englishDescription;
  }

  public void setSceneName(String sceneName) {
    this.sceneName = sceneName;
  }

  public void setSpanishDescription(String spanishDescription) {
    this.spanishDescription = spanishDescription;
  }

  public SceneInfo setEnglishTitle (String englishTitle) {
    this.englishTitle = englishTitle;
    return this;
  }

  public SceneInfo setSpanishTitle (String spanishTitle) {
    this.spanishTitle = spanishTitle;
    return this;
  }

  public SceneInfo setFilename (String filename) {
    this.filename = filename;
    return this;
  }

  public SceneInfo setWebId (Integer webId) {
    this.webId = webId;
    return this;
  }

  public static class SpanishTitleComparator implements Comparator<SceneInfo> {

    @Override
    public int compare(SceneInfo o1, SceneInfo o2) {
      if (o1.difficulty != o2.difficulty)
        return (Integer.parseInt(o1.difficulty) > Integer.parseInt(o2.difficulty))? 1 : -1;
      return o1.getSceneName().compareTo(o2.getSceneName());
    }
  }

}
