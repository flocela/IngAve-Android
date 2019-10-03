package com.olfybsppa.inglesaventurero.stageactivity.collectors;

import android.os.Parcel;
import android.os.Parcelable;

public class ReplySetInfo implements Parcelable{
  public int replyGroupID;
  public int lastViewed;

  public ReplySetInfo (int groupId, int lastViewed) { //lastViewed is position.
    this.lastViewed = lastViewed;
    this.replyGroupID = groupId;
  }

  public void mergeReplySet (ReplyLineSet replySet) {
    replySet.setLastViewed(lastViewed);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(replyGroupID);
    dest.writeInt(lastViewed);
  }

  public static final Parcelable.Creator<ReplySetInfo> CREATOR =
    new Parcelable.Creator<ReplySetInfo>() {
      public ReplySetInfo createFromParcel(Parcel in) {
        return new ReplySetInfo(in);
      }

      public ReplySetInfo[] newArray(int size) {
        return new ReplySetInfo[size];
      }
    };

  private ReplySetInfo(Parcel in) {
    replyGroupID = in.readInt();
    lastViewed = in.readInt();
  }
}
