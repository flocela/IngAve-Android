package com.olfybsppa.inglesaventurero.stagehouseactivityutest.collectors_interactions;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.Matchable;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Reply;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ReplyLimitedUTest {

  Reply defaultReply;
  Reply otherReply;
  ArrayList<String> responses = new ArrayList<>();

  @Before
  public  void setUp() throws Exception {
    defaultReply = new Reply(11, 1);
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

    // oneReply starts as same as zeroReply.
    otherReply = new Reply(11, 1);
    otherReply.setFollowingPage(12);
    otherReply.setNormalStartTime(1001);
    otherReply.setNormalEndTime(1010);
    otherReply.setSlowStartTime(1002);
    otherReply.setSlowEndTime(1012);
    otherReply.setEngPhrase("english phrase 1");
    otherReply.setSpnPhrase("spanish phrase 1");
    otherReply.setWFWEng("wfw english 1");
    otherReply.setWFWSpn("wfw spanish 1");
    otherReply.setEngExplanation("eng explanation 1");
    otherReply.setSpnExplanation("spn explanation 1");
    otherReply.setRegex("regex\\d*");

    responses.add("not a match");
    responses.add("regexA");
    responses.add("regex1");
  }

  @Test
  public void testConstructor () {
    Matchable.Limited replyL = otherReply.match(responses);
    assertTrue(replyL.isMatched());
    assertEquals(12, replyL.getFollowingPage());
    assertTrue(responses.containsAll(replyL.getResponses()));
    assertEquals(responses.size(), replyL.getResponses().size());
    assertEquals("regex1", replyL.getMatchedWith());
    assertEquals(otherReply.getGroupId(), replyL.getGroupID());
    assertEquals(11, replyL.getLinePosition());
  }

}
