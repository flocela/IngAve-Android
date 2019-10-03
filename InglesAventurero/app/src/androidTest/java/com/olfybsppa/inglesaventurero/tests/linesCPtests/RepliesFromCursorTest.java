package com.olfybsppa.inglesaventurero.tests.linesCPtests;

import android.content.ContentValues;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.Reply;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPReply;

import java.util.ArrayList;

public class RepliesFromCursorTest extends ProviderTestCase2<LinesCP> {

  private MockContentResolver mMockResolver;

  public RepliesFromCursorTest() {
    super(LinesCP.class, LinesCP.AUTHORITY);
  }
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    mMockResolver = getMockContentResolver();
  }
  
  public void testExtractReplies () {
    int sceneId = 777;
    int pageId = 3;

    // Create replies that will be inserted into ContentProvider.
    CPReply cpReply1 = new CPReply(1, 7);
    cpReply1.setNextPage(12);
    cpReply1.setNormalStartTime(1001);
    cpReply1.setNormalEndTime(1010);
    cpReply1.setSlowStartTime(1002);
    cpReply1.setSlowEndTime(1012);
    cpReply1.setEnglishPhrase("english phrase 1");
    cpReply1.setSpanishPhrase("spanish phrase 1");
    cpReply1.setWFWEnglish("wfw english 1");
    cpReply1.setWFWSpanish("wfw spanish 1");
    cpReply1.setEngExplanation("eng explanation 1");
    cpReply1.setSpnExplanation("spn explanation 1");
    cpReply1.setRegex("regex 1");

    CPReply cpReply2 = new CPReply(2, 8);
    cpReply2.setNextPage(22);
    cpReply2.setNormalStartTime(2002);
    cpReply2.setNormalEndTime(2020);
    cpReply2.setSlowStartTime(2002);
    cpReply2.setSlowEndTime(2022);
    cpReply2.setEnglishPhrase("english phrase 2");
    cpReply2.setSpanishPhrase("spanish phrase 2");
    cpReply2.setWFWEnglish("wfw english 2");
    cpReply2.setWFWSpanish("wfw spanish 2");
    cpReply2.setEngExplanation("eng explanation 2");
    cpReply2.setSpnExplanation("spn explanation 2");
    cpReply2.setRegex("regex 2");

    ContentValues[] contentValuesArray = new ContentValues[2];
    ContentValues cv1 = cpReply1.getContentValues(sceneId, pageId);
    ContentValues cv2 = cpReply2.getContentValues(sceneId, pageId);
    contentValuesArray[0] = cv1;
    contentValuesArray[1] = cv2;

    // 1. Insert CPReplies into ContentProvider.
    mMockResolver.bulkInsert(LinesCP.replyTableUri, contentValuesArray);

    // 2. Extract replies from ContentProvider.
    ArrayList<Reply> extractedReplies = Reply.extractReplies(mMockResolver, pageId);

    // Create replies to compare with extracted Replies. Note matched and matchedWith
    // attributes are ignored because ContentProvider doesn't save these attribute.
    Reply reply1 = new Reply(1, 7);
    reply1.setFollowingPage(12);
    reply1.setNormalStartTime(1001);
    reply1.setNormalEndTime(1010);
    reply1.setSlowStartTime(1002);
    reply1.setSlowEndTime(1012);
    reply1.setEngPhrase("english phrase 1");
    reply1.setWFWEng("wfw english 1");
    reply1.setWFWSpn("wfw spanish 1");
    reply1.setSpnPhrase("spanish phrase 1");
    reply1.setEngExplanation("eng explanation 1");
    reply1.setSpnExplanation("spn explanation 1");
    reply1.setRegex("regex 1");

    Reply reply2 = new Reply(2, 8);
    reply2.setFollowingPage(22);
    reply2.setNormalStartTime(2002);
    reply2.setNormalEndTime(2020);
    reply2.setSlowStartTime(2002);
    reply2.setSlowEndTime(2022);
    reply2.setEngPhrase("english phrase 2");
    reply2.setWFWEng("wfw english 2");
    reply2.setWFWSpn("wfw spanish 2");
    reply2.setSpnPhrase("spanish phrase 2");
    reply2.setEngExplanation("eng explanation 2");
    reply2.setSpnExplanation("spn explanation 2");
    reply2.setRegex("regex 2");

    // 3. Compare extracted replies to expected Replies.
    assertTrue(extractedReplies.contains(reply1));
    assertTrue(extractedReplies.contains(reply2));
    assertEquals(2, extractedReplies.size());

  }

}
