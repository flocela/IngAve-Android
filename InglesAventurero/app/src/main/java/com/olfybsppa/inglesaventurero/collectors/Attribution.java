package com.olfybsppa.inglesaventurero.collectors;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.ImageCredit;
import com.olfybsppa.inglesaventurero.start.LinesCP;

public class Attribution implements Parcelable {

  private String changesMadeEng;
  private String changesMadeSpan;
  private ImageCredit imageCredit;

  public Attribution(String imageInfoName) {
    this.imageCredit = new ImageCredit(imageInfoName);
  }

  public Attribution (String changesMadeEng,
                      String changesMadeSpan,
                      ImageCredit ic) {
    this.changesMadeEng = changesMadeEng;
    this.changesMadeSpan = changesMadeSpan;
    this.imageCredit = ic.clone();
  }

  public Attribution setChangesMadeEnglish(String changesMadeEnglish) {
    this.changesMadeEng = changesMadeEnglish;
    return this;
  }

  public Attribution setChangesMadeSpanish(String changesMadeSpanish) {
    this.changesMadeSpan = changesMadeSpanish;
    return this;
  }

  public Attribution setArtist(String artist) {
    this.imageCredit.artist = artist;
    return this;
  }

  public Attribution setLinkToLicense(String linkToLicense) {
    this.imageCredit.linkToLicense = linkToLicense;
    return this;
  }

  public Attribution setNameOfLicense(String nameOfLicense) {
    this.imageCredit.nameOfLicense = nameOfLicense;
    return this;
  }

  public Attribution setArtistImageName(String artistImageName) {
    this.imageCredit.artistImageName = artistImageName;
    return this;
  }

  public Attribution setImageUrl(String imageUrl) {
    this.imageCredit.imageUrl = imageUrl;
    return this;
  }

  public Attribution setImageUrlName(String imageUrlName) {
    this.imageCredit.imageUrlName = imageUrlName;
    return this;
  }

  public Attribution setFilename(String artistFilename) {
    this.imageCredit.artistFilename = artistFilename;
    return this;
  }

  public String getImageInfoName () {
    return this.imageCredit.imageInfoName;
  }

  public ImageCredit constructImageCredit() {
    return imageCredit.clone();
  }

  public static Attribution fromBundle(Bundle bundle) {
    Attribution attribution = new Attribution(bundle.getString(LinesCP.image_info_name));

    if (bundle.containsKey(LinesCP.artist_image_name)) {
      attribution.setArtistImageName(bundle.getString(LinesCP.artist_image_name));
    }
    if (bundle.containsKey(LinesCP.artist)) {
      attribution.setArtist(bundle.getString(LinesCP.artist));
    }
    if (bundle.containsKey(LinesCP.name_of_license)) {
      attribution.setNameOfLicense(bundle.getString(LinesCP.name_of_license));
    }
    if (bundle.containsKey(LinesCP.link_to_license)) {
      attribution.setLinkToLicense(bundle.getString(LinesCP.link_to_license));
    }
    if(bundle.containsKey(LinesCP.artist_filename)) {
      attribution.setFilename(bundle.getString(LinesCP.artist_filename));
    }
    if(bundle.containsKey(LinesCP.image_url_name)) {
      attribution.setImageUrlName(bundle.getString(LinesCP.image_url_name));
    }
    if(bundle.containsKey(LinesCP.image_url)) {
      attribution.setImageUrl(bundle.getString(LinesCP.image_url));
    }

    if (bundle.containsKey(LinesCP.changes_english)) {
      attribution.setChangesMadeEnglish(bundle.getString(LinesCP.changes_english));
    }
    if(bundle.containsKey(LinesCP.changes_spanish)) {
      attribution.setChangesMadeSpanish(bundle.getString(LinesCP.changes_spanish));
    }
    return attribution;
  }

  public Bundle getPictureInfoBundle() {
    Bundle bundle = new Bundle();
    bundle.putString(LinesCP.artist_image_name, this.imageCredit.artistImageName);
    bundle.putString(LinesCP.artist, this.imageCredit.artist);
    bundle.putString(LinesCP.name_of_license, this.imageCredit.nameOfLicense);
    bundle.putString(LinesCP.link_to_license, this.imageCredit.linkToLicense);
    bundle.putString(LinesCP.changes_english, this.changesMadeEng);
    bundle.putString(LinesCP.changes_spanish, this.changesMadeSpan);
    bundle.putString(LinesCP.artist_filename, this.imageCredit.artistFilename);
    bundle.putString(LinesCP.image_url_name, this.imageCredit.imageUrlName);
    bundle.putString(LinesCP.image_url, this.imageCredit.imageUrl);
    bundle.putString(LinesCP.image_info_name, this.imageCredit.imageInfoName);
    return bundle;
  }


  public Attribution clone() {
    return new Attribution(this.changesMadeEng, this.changesMadeSpan, this.imageCredit);
  }

  public ContentValues getContentValues () {
    ContentValues cV = new ContentValues();
    cV.put(LinesCP.changes_english, this.changesMadeEng);
    cV.put(LinesCP.changes_spanish, this.changesMadeSpan);
    return cV;
  }

  @Override
  public boolean equals (Object object) {
    if (object == this) return true;
    if (object == null || object.getClass() != this.getClass()) return false;
    Attribution obj = (Attribution)object;
    return (
      (changesMadeEng == obj.changesMadeEng ||
        (changesMadeEng != null && changesMadeEng.equals(obj.changesMadeEng))) &&
      (changesMadeSpan == obj.changesMadeSpan ||
        (changesMadeSpan != null && changesMadeSpan.equals(obj.changesMadeSpan))) &&
      (imageCredit == obj.imageCredit ||
        (imageCredit != null && imageCredit.equals(obj.imageCredit)))
    );
  }

  @Override
  public int hashCode () {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((changesMadeEng == null) ? 0 : changesMadeEng.hashCode());
    result = prime * result + ((changesMadeSpan == null) ? 0 : changesMadeSpan.hashCode());
    result = prime * result + ((imageCredit == null) ? 0 : imageCredit.hashCode());
    return result;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(changesMadeEng);
    dest.writeString(changesMadeSpan);
    dest.writeString(getImageInfoName());
    dest.writeString(imageCredit.artistImageName);
    dest.writeString(imageCredit.artist);
    dest.writeString(imageCredit.nameOfLicense);
    dest.writeString(imageCredit.linkToLicense);
    dest.writeString(imageCredit.artistFilename);
    dest.writeString(imageCredit.imageUrlName);
    dest.writeString(imageCredit.imageUrl);
  }

  public static final Parcelable.Creator<Attribution> CREATOR
    = new Parcelable.Creator<Attribution>() {
    public Attribution createFromParcel(Parcel in) {
      return new Attribution(in);
    }

    public Attribution[] newArray(int size) {
      return new Attribution[size];
    }
  };

  private Attribution (Parcel in) {
    changesMadeEng = in.readString();
    changesMadeSpan = in.readString();
    imageCredit = new ImageCredit(in.readString());
    setArtistImageName(in.readString());
    setArtist(in.readString());
    setNameOfLicense(in.readString());
    setLinkToLicense(in.readString());
    setFilename(in.readString());
    setImageUrlName(in.readString());
    setImageUrl(in.readString());
  }
}
