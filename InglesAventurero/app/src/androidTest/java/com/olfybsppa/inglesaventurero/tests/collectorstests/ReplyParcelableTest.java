package com.olfybsppa.inglesaventurero.tests.collectorstests;

import android.os.Parcel;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.Reply;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class ReplyParcelableTest {

  @Test
  public void testParcelable () {
    Reply defaultReply = new Reply(11, 1);
    defaultReply.setFollowingPage(12);
    defaultReply.setNormalStartTime(1001);
    defaultReply.setNormalEndTime(1010);
    defaultReply.setSlowStartTime(1002);
    defaultReply.setSlowEndTime(1012);
    defaultReply.setEngPhrase("english phrase 1");
    defaultReply.setSpnPhrase("spanish phrase 1");
    defaultReply.setWFWEng("wfw english 1");
    defaultReply.setWFWSpn("wfw spanish 1");
    defaultReply.setEngExplanation("eng explanation 1");
    defaultReply.setSpnExplanation("spn explanation 1");
    defaultReply.setRegex("regex\\d*");

    ArrayList<String> responses = new ArrayList<>();
    responses.add("regex1");
    defaultReply.match(responses);
    assertEquals(true, defaultReply.isMatched());

    Parcel parcel = Parcel.obtain();
    defaultReply.writeToParcel(parcel, 0);
    parcel.setDataPosition(0);
    Reply createdFromParcel = Reply.CREATOR.createFromParcel(parcel);
    assertEquals(defaultReply, createdFromParcel);
  }

}