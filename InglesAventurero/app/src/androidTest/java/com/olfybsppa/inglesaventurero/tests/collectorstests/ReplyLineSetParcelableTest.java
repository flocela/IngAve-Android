package com.olfybsppa.inglesaventurero.tests.collectorstests;

import android.os.Parcel;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.Reply;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.ReplyLineSet;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class ReplyLineSetParcelableTest {

  @Test
  public void testParcelable () {
    Reply zeroReply = new Reply(10, 1);
    zeroReply.setFollowingPage(11);
    zeroReply.setNormalStartTime(1001);
    zeroReply.setNormalEndTime(1002);
    zeroReply.setSlowStartTime(1003);
    zeroReply.setSlowEndTime(1014);
    zeroReply.setEngPhrase("english phrase 10");
    zeroReply.setSpnPhrase("spanish phrase 10");
    zeroReply.setWFWEng("wfw english 10");
    zeroReply.setWFWSpn("wfw spanish 10");
    zeroReply.setEngExplanation("eng explanation 10");
    zeroReply.setSpnExplanation("spn explanation 10");
    zeroReply.setRegex("xeroReply\\d*");

    Reply oneReply = new Reply(11, 1);
    oneReply.setFollowingPage(12);
    oneReply.setNormalStartTime(1101);
    oneReply.setNormalEndTime(1102);
    oneReply.setSlowStartTime(1103);
    oneReply.setSlowEndTime(1104);
    oneReply.setEngPhrase("english phrase 11");
    oneReply.setSpnPhrase("spanish phrase 11");
    oneReply.setWFWEng("wfw english 11");
    oneReply.setWFWSpn("wfw spanish 11");
    oneReply.setEngExplanation("eng explanation 11");
    oneReply.setSpnExplanation("spn explanation 11");
    oneReply.setRegex("oneReply\\d*");

    Reply twoReply = new Reply(12, 1);
    twoReply.setFollowingPage(13);
    twoReply.setNormalStartTime(1201);
    twoReply.setNormalEndTime(1202);
    twoReply.setSlowStartTime(1203);
    twoReply.setSlowEndTime(1204);
    twoReply.setEngPhrase("english phrase 12");
    twoReply.setSpnPhrase("spanish phrase 12");
    twoReply.setWFWEng("wfw english 12");
    twoReply.setWFWSpn("wfw spanish 12");
    twoReply.setEngExplanation("eng explanation 12");
    twoReply.setSpnExplanation("spn explanation 12");
    twoReply.setRegex("twoReply\\d*");

    ArrayList<Reply> replies = new ArrayList<>();
    replies.add(zeroReply);
    replies.add(oneReply);
    replies.add(twoReply);

    ReplyLineSet lineSet = new ReplyLineSet(replies);

    ArrayList<String> responses = new ArrayList<>();
    responses.add("oneReply1");

    lineSet.match(responses);
    Parcel parcel = Parcel.obtain();
    lineSet.writeToParcel(parcel, 0);
    parcel.setDataPosition(0);
    ReplyLineSet createdFromParcel = ReplyLineSet.CREATOR.createFromParcel(parcel);
    assertEquals(lineSet, createdFromParcel);
  }

}