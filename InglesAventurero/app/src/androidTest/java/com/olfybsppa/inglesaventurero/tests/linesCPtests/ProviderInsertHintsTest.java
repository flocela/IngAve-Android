package com.olfybsppa.inglesaventurero.tests.linesCPtests;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.utils.Ez;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPHint;

public class ProviderInsertHintsTest extends ProviderTestCase2<LinesCP> {

  private MockContentResolver mMockResolver;

  private CPHint hint1;
  private CPHint hint2;

  public ProviderInsertHintsTest() {
    super(LinesCP.class, LinesCP.AUTHORITY);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    mMockResolver = getMockContentResolver();

    hint1 = new CPHint(1, 11);
    hint1.setNextPage(12);
    hint1.setNormalStartTime(1001);
    hint1.setNormalEndTime(1010);
    hint1.setSlowStartTime(1002);
    hint1.setSlowEndTime(1012);
    hint1.setEnglishPhrase("english phrase 1");
    hint1.setSpanishPhrase("spanish phrase 1");
    hint1.setWFWEnglish("wfw english 1");
    hint1.setWFWSpanish("wfw spanish 1");
    hint1.setEngExplanation("eng explanation 1");
    hint1.setSpnExplanation("spn explanation 1");

    hint2 = new CPHint(2, 22);
    hint2.setNextPage(22);
    hint2.setNormalStartTime(2001);
    hint2.setNormalEndTime(2010);
    hint2.setSlowStartTime(2002);
    hint2.setSlowEndTime(2012);
    hint2.setEnglishPhrase("english phrase 2");
    hint2.setSpanishPhrase("spanish phrase 2");
    hint2.setWFWEnglish("wfw english 2");
    hint2.setWFWSpanish("wfw spanish 2");
    hint2.setEngExplanation("eng explanation 2");
    hint2.setSpnExplanation("spn explanation 2");
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testInsertHint () {
    ContentValues cv = hint1.getContentValues(777, 111);
    mMockResolver.insert(LinesCP.hintTableUri, cv);
    Cursor cursor = mMockResolver.query(LinesCP.hintTableUri, null, null, null, null);
    cursor.moveToFirst();
    assertEquals(1, cursor.getCount());
    int fromCPPageId = cursor.getInt(cursor.getColumnIndex(LinesCP.page_id));
    CPHint fromCP = CPHint.extractCPHint(cursor);
    cursor.close();

    assertTrue(fromCP.equals(hint1));
    assertEquals(111, fromCPPageId);
  }

  public void testInsertTwoHintsDeleteOne () {
    ContentValues cv1 = hint1.getContentValues(111, 777);
    ContentValues cv2 = hint2.getContentValues(111, 777);

    mMockResolver.insert(LinesCP.hintTableUri, cv1);
    mMockResolver.insert(LinesCP.hintTableUri, cv2);

    Cursor cursor1 = mMockResolver.query(LinesCP.hintTableUri, null, null, null, null);
    assertEquals(2, cursor1.getCount());
    cursor1.close();

    mMockResolver.delete(LinesCP.hintTableUri, Ez.where(BaseColumns._ID, "" + 1), null);
    Cursor cursor2 = mMockResolver.query(LinesCP.hintTableUri, null, null, null, null);
    assertEquals(1, cursor2.getCount());
    cursor2.close();
  }

  public void testCanNotHaveTwoHintsWithSamePageIDAndPosition () {
    ContentValues cv1 = hint1.getContentValues(777, 111);
    hint2.setPositionInPage(1);
    ContentValues cv2 = hint2.getContentValues(777, 111);
    mMockResolver.insert(LinesCP.hintTableUri, cv1);

    boolean failed = false;
    try {
      mMockResolver.insert(LinesCP.hintTableUri, cv2);
    } catch (Exception e) {
      failed = true;
    }
    assertTrue(failed);
  }

  public void testMustHavePositionInPage() {
    ContentValues cv = hint1.getContentValues(111, 777);
    cv.remove(LinesCP.pos_id);
    boolean failed = false;
    try {
      mMockResolver.insert(LinesCP.hintTableUri, cv);
    }catch (Exception e) {
      failed = true;
    }
    assertTrue(failed);
  }

}