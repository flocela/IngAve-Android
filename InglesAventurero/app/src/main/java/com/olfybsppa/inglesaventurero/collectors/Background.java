package com.olfybsppa.inglesaventurero.collectors;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.utils.Ez;

public class Background {
  private String backgroundName;
  private String backgroundFilename;

  public Background(){}

  public Background (String backgroundName, String backgroundFilename) {
    this.backgroundName = backgroundName;
    this.backgroundFilename = backgroundFilename;
  }

  //TODO doesn't look like this has been tested!!!
  public static Background extractBackground (ContentResolver cR, int bgRowId) {
    Cursor cursor = cR.query(LinesCP.backgroundTableUri,
                             new String[]{LinesCP.background_name,
                                          LinesCP.filename},
                             Ez.where(BaseColumns._ID, "" + bgRowId),
                             null,
                             null);
    if (cursor != null) cursor.moveToFirst();
    String bgname = cursor.getString(cursor.getColumnIndex(LinesCP.background_name));
    String filename = cursor.getString(cursor.getColumnIndex(LinesCP.filename));
    cursor.close();
    return new Background(bgname, filename);
  }

  public Background addName (String backgroundName) {
    this.backgroundName = backgroundName;
    return this;
  }

  public Background addFilename (String backgroundFilename) {
    this.backgroundFilename = backgroundFilename;
    return this;
  }


  public String getBackgroundName () {return this.backgroundName;}

  public String getBackgroundFilename () {return this.backgroundFilename;}

  public boolean equals (Background object) {
    if (object == this) return true;
    if (object == null || object.getClass() != this.getClass()) return false;

    Background obj = (Background)object;

    return (
      (backgroundName == obj.backgroundName ||
        (backgroundName != null && backgroundName.equals(obj.backgroundName))) &&
      (backgroundFilename == obj.backgroundFilename ||
        (backgroundFilename != null && backgroundFilename.equals(obj.backgroundFilename)))
    );
  }

  public ContentValues getContentValues () {
    ContentValues cV = new ContentValues();
    cV.put(LinesCP.background_name, this.backgroundName);
    cV.put(LinesCP.filename, this.backgroundFilename);
    return cV;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((backgroundName == null) ? 0 : backgroundName.hashCode());
    result = prime * result +
      ((backgroundFilename == null) ? 0 : backgroundFilename.hashCode());
    return result;
  }

}
