package com.olfybsppa.inglesaventurero.webscenelistactivity.collectors;


import android.content.ContentValues;

import com.olfybsppa.inglesaventurero.start.LinesCP;

// Page used for importing into Content Provider
public class CPPage {
  // I make pageName up, is not ContentProvider row Id. It is the page number for
  // the page in a script.
  private int pageName = -1;
  private boolean isFirst = false;

  public CPPage(){}

  public CPPage (int pageName) {
    this.pageName = pageName;
  }

  public CPPage setPageName (int pageName) {
    this.pageName = pageName;
    return this;
  }

  public CPPage setAsFirst (boolean isFirst) {
    this.isFirst = isFirst;
    return this;
  }

  public boolean isFirst () {
    return isFirst;
  }

  public Integer getPageName() {
    return this.pageName;
  }

  public ContentValues getContentValues (Integer sceneId, Integer backgroundId) {
    ContentValues cV = new ContentValues();
    cV.put(LinesCP.page_name, this.pageName);
    cV.put(LinesCP.is_first, this.isFirst);
    cV.put(LinesCP.scene_id, sceneId);
    cV.put(LinesCP.background_id, backgroundId);
    return cV;
  }

  @Override
  public boolean equals (Object obj) {
    if (obj == this)
      return true;
    if (obj == null || obj.getClass() != this.getClass())
      return false;
    CPPage other = (CPPage)obj;
    return pageName == other.pageName && isFirst == other.isFirst;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + pageName;
    int booleanIsFirst = (isFirst)? 1 : 0;
    result = prime * result + booleanIsFirst;
    return result;
  }

}
