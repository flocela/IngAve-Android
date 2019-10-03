package com.olfybsppa.inglesaventurero.tests.collectorstests;

import android.os.Parcel;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.Hint;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class HintParcelableTest {

  @Test
  public void testParcelable () {
    Hint hint = new Hint(1, 22);
    hint.setFollowingPage(12);
    hint.setHeard(true);
    hint.setNormalStartTime(1001);
    hint.setNormalEndTime(1010);
    hint.setSlowStartTime(1002);
    hint.setSlowEndTime(1012);
    hint.setEngPhrase("english phrase 1");
    hint.setSpnPhrase("spanish phrase 1");
    hint.setWFWEng("wfw english 1");
    hint.setWFWSpn("wfw spanish 1");
    hint.setEngExplanation("eng explanation 1");
    hint.setSpnExplanation("spn explanation 1");

    hint.setHeard(true);
    Parcel parcel = Parcel.obtain();
    hint.writeToParcel(parcel, 0);
    parcel.setDataPosition(0);
    Hint createdFromParcel = Hint.CREATOR.createFromParcel(parcel);
    assertEquals(hint, createdFromParcel);
  }

}