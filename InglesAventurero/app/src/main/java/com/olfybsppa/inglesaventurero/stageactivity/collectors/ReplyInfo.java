package com.olfybsppa.inglesaventurero.stageactivity.collectors;

import android.os.Parcel;
import android.os.Parcelable;

public class ReplyInfo implements Parcelable{
  public int replyGroupID;
  public int positionID;
  public boolean matched;

  public ReplyInfo (int replyGroupID, int posId, boolean matched) {
    this.replyGroupID = replyGroupID;
    this.positionID = posId;
    this.matched = matched;
  }

  public void mergeReply(Reply reply) {
    reply.setMatched(matched);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeByte((byte) (matched ? 1 : 0));
    dest.writeInt(replyGroupID);
    dest.writeInt(positionID);
  }

  public final static Parcelable.Creator<ReplyInfo> CREATOR =
    new Parcelable.Creator<ReplyInfo>() {
      public ReplyInfo createFromParcel(Parcel in) {
        return new ReplyInfo(in);
      }

      public ReplyInfo[] newArray(int size) {
        return new ReplyInfo[size];
      }
    };

  private ReplyInfo(Parcel in) {
    matched = in.readByte() != 0;
    replyGroupID = in.readInt();
    positionID = in.readInt();
  }
}
