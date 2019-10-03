package com.olfybsppa.inglesaventurero.stageactivity.collectors;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import com.olfybsppa.inglesaventurero.utils.Pair;

import com.olfybsppa.inglesaventurero.start.LinesCP;

public class ImageCredit {
  
  public String imageInfoName;
  public String artist;
  public String linkToLicense;
  public String nameOfLicense;
  public String artistImageName;
  public String imageUrl;
  public String imageUrlName;
  public String artistFilename; //same as artist's artistFilename.
  
  public ImageCredit (String imageInfoName) {
    this.imageInfoName = imageInfoName;
  }

  // SETTERS //

  public ImageCredit setImageInfoName (String imageInfoName) {
    this.imageInfoName = imageInfoName;
    return this;
  }


  public ImageCredit setArtist(String artist) {
    this.artist = artist;
    return this;
  }

  public ImageCredit setLinkToLicense(String linkToLicense) {
    this.linkToLicense = linkToLicense;
    return this;
  }

  public ImageCredit setNameOfLicense(String nameOfLicense) {
    this.nameOfLicense = nameOfLicense;
    return this;
  }

  public ImageCredit setArtistImageName(String artistImageName) {
    this.artistImageName = artistImageName;
    return this;
  }

  public ImageCredit setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  public ImageCredit setImageUrlName(String imageUrlName) {
    this.imageUrlName = imageUrlName;
    return this;
  }

  public ImageCredit setArtistFilename(String artistFilename) {
    this.artistFilename = artistFilename;
    return this;
  }

  // GETTERS //

  public String getImageInfoName () {
    return imageInfoName;
  }

  public ContentValues getContentValues() {
    ContentValues cV = new ContentValues();
    cV.put(LinesCP.image_info_name, this.imageInfoName);
    cV.put(LinesCP.artist, this.artist);
    cV.put(LinesCP.link_to_license, this.linkToLicense);
    cV.put(LinesCP.name_of_license, this.nameOfLicense);
    cV.put(LinesCP.artist_image_name, this.artistImageName);
    cV.put(LinesCP.image_url_name, this.imageUrlName);
    cV.put(LinesCP.image_url, this.imageUrl);
    cV.put(LinesCP.artist_filename, this.artistFilename);
    return cV;
  }

  public static Pair<Integer, ImageCredit> extractImageCredit (Cursor cursor) {
    if (cursor == null) return new Pair<>(-1, null);
    ImageCredit imageCredit =
      new ImageCredit(cursor.getString(cursor.getColumnIndex(LinesCP.image_info_name)));
    imageCredit.
      setLinkToLicense(cursor.getString(cursor.getColumnIndex(LinesCP.link_to_license)));
    imageCredit.
      setNameOfLicense(cursor.getString(cursor.getColumnIndex(LinesCP.name_of_license)));
    imageCredit.
      setArtist(cursor.getString(cursor.getColumnIndex(LinesCP.artist)));
    imageCredit.
      setArtistImageName(cursor.getString(cursor.getColumnIndex(LinesCP.artist_image_name)));
    imageCredit.
      setImageUrl(cursor.getString(cursor.getColumnIndex(LinesCP.image_url)));
    imageCredit.
      setImageUrlName(cursor.getString(cursor.getColumnIndex(LinesCP.image_url_name)));
    imageCredit.
      setArtistFilename(cursor.getString(cursor.getColumnIndex(LinesCP.artist_filename)));
    int rowNum = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
    return new Pair<> (rowNum, imageCredit);
  }

  public ImageCredit clone () {
    ImageCredit ic = new ImageCredit(this.imageInfoName);
    ic.setArtist(this.artist)
      .setLinkToLicense(this.linkToLicense)
      .setNameOfLicense(this.nameOfLicense)
      .setArtistImageName(this.artistImageName)
      .setImageUrlName(this.imageUrlName)
      .setImageUrl(this.imageUrl)
      .setArtistFilename(this.artistFilename);
    return ic;
  }

  @Override
  public boolean equals (Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    ImageCredit ic = (ImageCredit)obj;
    return ( (imageInfoName == ic.imageInfoName || 
             (imageInfoName != null && imageInfoName.equals(ic.imageInfoName))) && 
             (artist == ic.artist ||
               (artist != null && artist.equals(ic.artist))) &&
             (linkToLicense == ic.linkToLicense ||
               (linkToLicense != null && linkToLicense.equals(ic.linkToLicense))) &&
             (nameOfLicense == ic.nameOfLicense ||
               (nameOfLicense != null && nameOfLicense.equals(ic.nameOfLicense))) &&
             (artistImageName == ic.artistImageName ||
               (artistImageName != null && artistImageName.equals(ic.artistImageName))) &&
             (imageUrl == ic.imageUrl ||
               (imageUrl != null && imageUrl.equals(ic.imageUrl))) &&
             (imageUrlName == ic.imageUrlName ||
               (imageUrlName != null && imageUrlName.equals(ic.imageUrlName))) &&
             (artistFilename == ic.artistFilename ||
               (artistFilename != null && artistFilename.equals(ic.artistFilename))));
  }

  @Override
  public int hashCode () {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((imageInfoName == null) ? 0 : imageInfoName.hashCode());
    result = prime * result + ((artist == null) ? 0 : artist.hashCode());
    result = prime * result + ((linkToLicense == null) ? 0 : linkToLicense.hashCode());
    result = prime * result + ((nameOfLicense == null) ? 0 : nameOfLicense.hashCode());
    result = prime * result + ((artistImageName == null) ? 0 : artistImageName.hashCode());
    result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
    result = prime * result + ((imageUrlName == null) ? 0 : imageUrlName.hashCode());
    result = prime * result + ((artistFilename == null) ? 0 : artistFilename.hashCode());
    return result;

  }

}
