package com.olfybsppa.inglesaventurero.tests.linesCPtests;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.utils.Ez;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPReply;

public class ProviderRepliesTest extends ProviderTestCase2<LinesCP> {

  private MockContentResolver mMockResolver;

  private CPReply reply1;
  private CPReply reply2;

  public ProviderRepliesTest() {
    super(LinesCP.class, LinesCP.AUTHORITY);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    mMockResolver = getMockContentResolver();

    reply1 = new CPReply(1, 60);
    reply1.setNextPage(12);
    reply1.setNormalStartTime(1001);
    reply1.setNormalEndTime(1010);
    reply1.setSlowStartTime(1002);
    reply1.setSlowEndTime(1012);
    reply1.setEnglishPhrase("english phrase 1");
    reply1.setSpanishPhrase("spanish phrase 1");
    reply1.setWFWEnglish("wfw english 1");
    reply1.setWFWSpanish("wfw spanish 1");
    reply1.setEngExplanation("eng explanation 1");
    reply1.setSpnExplanation("spn explanation 1");

    reply2 = new CPReply(2, 60);
    reply2.setNextPage(22);
    reply2.setNormalStartTime(2001);
    reply2.setNormalEndTime(2010);
    reply2.setSlowStartTime(2002);
    reply2.setSlowEndTime(2012);
    reply2.setEnglishPhrase("english phrase 2");
    reply2.setSpanishPhrase("spanish phrase 2");
    reply2.setWFWEnglish("wfw english 2");
    reply2.setWFWSpanish("wfw spanish 2");
    reply2.setEngExplanation("eng explanation 2");
    reply2.setSpnExplanation("spn explanation 2");
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testInsertReply () {
    ContentValues cv = reply1.getContentValues(777, 111);
    mMockResolver.insert(LinesCP.replyTableUri, cv);
    Cursor cursor = mMockResolver.query(LinesCP.replyTableUri, null, null, null, null);
    assertEquals(1, cursor.getCount());
    cursor.moveToFirst();
    CPReply fromCP = extractReply(cursor);
    assertTrue(fromCP.equals(reply1));
    cursor.close();
  }

  public void testInsertTwoReplysDeleteOne () {
    ContentValues cv1 = reply1.getContentValues(777, 111);

    ContentValues cv2 = reply2.getContentValues(777, 111);

    mMockResolver.insert(LinesCP.replyTableUri, cv1);
    mMockResolver.insert(LinesCP.replyTableUri, cv2);

    Cursor cursor1 = mMockResolver.query(LinesCP.replyTableUri, null, null, null, null);
    assertEquals(2, cursor1.getCount());
    cursor1.close();

    mMockResolver.delete(LinesCP.replyTableUri, Ez.where(BaseColumns._ID, "" + 1), null);
    Cursor cursor2 = mMockResolver.query(LinesCP.replyTableUri, null, null, null, null);
    assertEquals(1, cursor2.getCount());
    cursor2.close();
  }

  public void testCanNotHaveTwoReplysWithSamePageIDAndPosition () {
    ContentValues cv1 = reply1.getContentValues(777, 111);
    reply2.setPositionInPage(1);
    ContentValues cv2 = reply2.getContentValues(777, 111);
    mMockResolver.insert(LinesCP.replyTableUri, cv1);

    boolean failed = false;
    try {
      mMockResolver.insert(LinesCP.replyTableUri, cv2);
    } catch (Exception e) {
      failed = true;
    }
    assertTrue(failed);
  }

  public void testMustHavePageId () {
    ContentValues contentValues = reply1.getContentValues(777, 113);
    contentValues.remove(LinesCP.page_id);
    boolean failed = false;
    try {
      mMockResolver.insert(LinesCP.replyTableUri, contentValues);
    }catch (Exception e) {
      failed = true;
    }
    assertTrue(failed);
  }

  public void testMustHaveReplyGroup () {
    ContentValues contentValues = reply1.getContentValues(777, 111);
    contentValues.remove(LinesCP.reply_group_id);
    boolean failed = false;
    try {
      mMockResolver.insert(LinesCP.replyTableUri, contentValues);
    }catch (Exception e) {
     failed = true;
    }
    assertTrue(failed);
  }

  public void testMustHavePositionInPage() {
    ContentValues cv = reply1.getContentValues(777, 111);
    cv.remove(LinesCP.pos_id);
    boolean failed = false;
    try {
      mMockResolver.insert(LinesCP.replyTableUri, cv);
    }catch (Exception e) {
      failed = true;
    }
    assertTrue(failed);
  }

  private CPReply extractReply (Cursor replyCursor) {
    Integer position   = replyCursor.getInt(replyCursor.getColumnIndex(LinesCP.pos_id));
    Integer replyGroup = replyCursor.getInt(replyCursor.getColumnIndex(LinesCP.reply_group_id));
    CPReply reply = new CPReply(position, replyGroup);
    reply.setNextPage(replyCursor.getInt(replyCursor.getColumnIndex(LinesCP.next_page_name)));
    reply.setTimes(replyCursor.getInt(replyCursor.getColumnIndex(LinesCP.normal_start_time)),
      replyCursor.getInt(replyCursor.getColumnIndex(LinesCP.normal_end_time)),
      replyCursor.getInt(replyCursor.getColumnIndex(LinesCP.slow_start_time)),
      replyCursor.getInt(replyCursor.getColumnIndex(LinesCP.slow_end_time)));
    reply.setEnglishPhrase(replyCursor.getString(replyCursor.getColumnIndex(LinesCP.english_phrase)));
    reply.setSpanishPhrase(replyCursor.getString(replyCursor.getColumnIndex(LinesCP.spanish_phrase)));
    reply.setWFWEnglish(replyCursor.getString(replyCursor.getColumnIndex(LinesCP.wfw_english)));
    reply.setWFWSpanish(replyCursor.getString(replyCursor.getColumnIndex(LinesCP.wfw_spanish)));
    reply.setRegex(replyCursor.getString(replyCursor.getColumnIndex(LinesCP.regex)));
    reply.setEngExplanation(replyCursor.getString(replyCursor.getColumnIndex(LinesCP.eng_explanation)));
    reply.setSpnExplanation(replyCursor.getString(replyCursor.getColumnIndex(LinesCP.spn_explanation)));
    return reply;
  }


}