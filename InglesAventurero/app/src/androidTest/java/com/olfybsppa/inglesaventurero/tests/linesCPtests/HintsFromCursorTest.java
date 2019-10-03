package com.olfybsppa.inglesaventurero.tests.linesCPtests;

import android.content.ContentValues;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.Hint;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPHint;

import java.util.ArrayList;

public class HintsFromCursorTest extends ProviderTestCase2<LinesCP> {

  private MockContentResolver mMockResolver;

  public HintsFromCursorTest() {
    super(LinesCP.class, LinesCP.AUTHORITY);
  }
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    mMockResolver = getMockContentResolver();
  }
  
  public void testExtractHints () {
    int sceneId = 777;
    int pageId = 3;

    CPHint cpHint1 = new CPHint(1, 10);
    cpHint1.setNextPage(12);
    cpHint1.setNormalStartTime(1001);
    cpHint1.setNormalEndTime(1010);
    cpHint1.setSlowStartTime(1002);
    cpHint1.setSlowEndTime(1012);
    cpHint1.setEnglishPhrase("english phrase 1");
    cpHint1.setSpanishPhrase("spanish phrase 1");
    cpHint1.setWFWEnglish("wfw english 1");
    cpHint1.setWFWSpanish("wfw spanish 1");
    cpHint1.setEngExplanation("eng explanation 1");
    cpHint1.setSpnExplanation("spn explanation 1");

    CPHint cpHint2 = new CPHint(2, 20);
    cpHint2.setNextPage(22);
    cpHint2.setNormalStartTime(2002);
    cpHint2.setNormalEndTime(2020);
    cpHint2.setSlowStartTime(2002);
    cpHint2.setSlowEndTime(2022);
    cpHint2.setEnglishPhrase("english phrase 2");
    cpHint2.setSpanishPhrase("spanish phrase 2");
    cpHint2.setWFWEnglish("wfw english 2");
    cpHint2.setWFWSpanish("wfw spanish 2");
    cpHint2.setEngExplanation("eng explanation 2");
    cpHint2.setSpnExplanation("spn explanation 2");

    Hint hint1 = new Hint(1, 10);
    hint1.setFollowingPage(12);
    hint1.setNormalStartTime(1001);
    hint1.setNormalEndTime(1010);
    hint1.setSlowStartTime(1002);
    hint1.setSlowEndTime(1012);
    hint1.setEngPhrase("english phrase 1");
    hint1.setWFWEng("wfw english 1");
    hint1.setWFWSpn("wfw spanish 1");
    hint1.setSpnPhrase("spanish phrase 1");
    hint1.setEngExplanation("eng explanation 1");
    hint1.setSpnExplanation("spn explanation 1");

    Hint hint2 = new Hint(2, 20);
    hint2.setFollowingPage(22);
    hint2.setNormalStartTime(2002);
    hint2.setNormalEndTime(2020);
    hint2.setSlowStartTime(2002);
    hint2.setSlowEndTime(2022);
    hint2.setEngPhrase("english phrase 2");
    hint2.setWFWEng("wfw english 2");
    hint2.setWFWSpn("wfw spanish 2");
    hint2.setSpnPhrase("spanish phrase 2");
    hint2.setEngExplanation("eng explanation 2");
    hint2.setSpnExplanation("spn explanation 2");

    ContentValues cv1 = cpHint1.getContentValues(sceneId, pageId);
    ContentValues cv2 = cpHint2.getContentValues(sceneId, pageId);

    ContentValues[] contentValuesArray = new ContentValues[2];
    contentValuesArray[0] = cv1;
    contentValuesArray[1] = cv2;

    mMockResolver.bulkInsert(LinesCP.hintTableUri, contentValuesArray);

    ArrayList<Hint> extractedHints = Hint.extractHints(mMockResolver, pageId);
    assertTrue(extractedHints.contains(hint1));
    assertTrue(extractedHints.contains(hint2));
    assertEquals(2, extractedHints.size());
  }

}
