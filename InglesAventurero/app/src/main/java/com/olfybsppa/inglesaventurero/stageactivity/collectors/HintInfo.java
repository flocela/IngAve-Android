package com.olfybsppa.inglesaventurero.stageactivity.collectors;

import android.os.Parcel;
import android.os.Parcelable;

public class HintInfo implements Parcelable {
  public int hintGroupID;
  public int posId;
  public boolean heard;

  public HintInfo (int groupId, int posId, boolean heard) {
    this.hintGroupID = groupId;
    this.posId = posId;
    this.heard = heard;
  }

  public void mergeHint(Hint hint) {
    hint.setHeard(heard);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeByte((byte) (heard ? 1 : 0));
    dest.writeInt(hintGroupID);
    dest.writeInt(posId);
  }

  public final static Parcelable.Creator<HintInfo> CREATOR =
    new Parcelable.Creator<HintInfo>() {
      public HintInfo createFromParcel(Parcel in) {
        return new HintInfo(in);
      }

      public HintInfo[] newArray(int size) {
        return new HintInfo[size];
      }
    };

  private HintInfo(Parcel in) {
    heard = in.readByte() != 0;
    hintGroupID = in.readInt();
    posId = in.readInt();
  }
}