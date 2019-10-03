package com.olfybsppa.inglesaventurero.stagehouseactivityutest.collectors_basics;


import com.olfybsppa.inglesaventurero.stageactivity.collectors.Matchable;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.NonMatchedLimited;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Reply;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.ReplyLineSet;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class ReplyLineSetUTest {

  Reply zeroReply;
  Reply oneReply;
  Reply twoReply;
  ArrayList<String> responses = new ArrayList<>();
  ArrayList<Reply>  inputReplies = new ArrayList<>();

  @Before
  public void setUp() throws Exception {
    zeroReply = new Reply(10, 1);
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

    oneReply = new Reply(11, 1);
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

    twoReply = new Reply(12, 1);
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

    inputReplies.add(zeroReply);
    inputReplies.add(oneReply);
    inputReplies.add(twoReply);

    responses.add("not a match");
    responses.add("regexA");
    responses.add("oneReply1");


  }

  @Test
  public void testConstructor () {
    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);

    Reply[] outputReplies = lineSet.getRepliesAsArray();
    List<Reply> outputRepliesList = Arrays.asList(outputReplies);
    assertTrue(inputReplies.containsAll(outputRepliesList));
    assertTrue(inputReplies.size() == outputRepliesList.size());

    ArrayList<Reply> outputReplies2 = lineSet.getReplies();
    assertTrue(inputReplies.containsAll(outputReplies2));
    assertTrue(inputReplies.size() == outputReplies2.size());
  }

  @Test
  public void testGetPositionInPage () {
    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);
    assertEquals(10, lineSet.getPosition());
  }

  @Test
  public void testGetGroupID () {
    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);
    assertEquals(1, lineSet.getGroupId());
  }

  @Test
  public void testFollowingPage () {
    oneReply.setFollowingPage(-1);

    ArrayList<String> responsesZero = new ArrayList<>();
    responsesZero.add("xeroReply0");
    ArrayList<String> responsesOne = new ArrayList<>();
    responsesOne.add("oneReply1");
    ArrayList<String> responsesTwo = new ArrayList<>();
    responsesTwo.add("twoReply2");

    Reply zeroClone = zeroReply.clone();
    zeroClone.match(responsesZero);
    Reply oneClone  = oneReply.clone();
    Reply twoClone  = twoReply.clone();
    ArrayList<Reply> clones = new ArrayList<>();
    clones.add(zeroClone);
    clones.add(oneClone);
    clones.add(twoClone);

    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);
    ReplyLineSet cloneLineSet = new ReplyLineSet(clones);

    assertEquals(11, lineSet.getFollowingPage());

    lineSet.match(responsesZero);
    assertEquals(11, lineSet.getFollowingPage());
    assertFalse(lineSet.equals(cloneLineSet));
    assertFalse(lineSet.hashCode() == cloneLineSet.hashCode());

    lineSet.match(responsesOne);
    assertEquals(-1, lineSet.getFollowingPage());

    lineSet.match(responsesTwo);
    assertEquals(13, lineSet.getFollowingPage());
  }

  @Test
  public void testNullResponses () {
    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);
    Matchable.Limited nullMatch = lineSet.match(null);
    assertTrue(nullMatch instanceof NonMatchedLimited);
    assertTrue(nullMatch.getResponses().size() == 0);
    assertFalse(lineSet.isMatched());
  }

  @Test
  public void testMatchEmptyResponses () {
    ArrayList<String> emptyResponses = new ArrayList<>();
    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);
    Matchable.Limited emptyMatch = lineSet.match(emptyResponses);
    assertTrue(emptyMatch instanceof NonMatchedLimited);
    assertTrue(emptyMatch.getResponses().size() == 0);
    assertFalse(lineSet.isMatched());
  }

  @Test
  public void testNonMatch () {
    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);
    ArrayList<String> incorrectResponses = new ArrayList<>();
    incorrectResponses.add("incorrect");
    incorrectResponses.add("so wrong");
    Matchable.Limited noMatch = lineSet.match(incorrectResponses);
    assertTrue(noMatch instanceof NonMatchedLimited);
    assertEquals(11, lineSet.getFollowingPage());
    assertEquals(-1, noMatch.getFollowingPage());
    assertTrue(noMatch.getResponses().equals(incorrectResponses));
    assertFalse(lineSet.isMatched());
  }

  @Test
  public void testMatch () {
    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);
    Matchable.Limited replyL = lineSet.match(responses);
    assertTrue(replyL.isMatched());
    assertEquals("oneReply1", replyL.getMatchedWith());
    assertEquals(12, replyL.getFollowingPage());
    assertEquals(12, lineSet.getFollowingPage());
    assertTrue(responses.equals(replyL.getResponses()));
    assertEquals(twoReply.getGroupId(), replyL.getGroupID());
    assertTrue(lineSet.isMatched());
  }

  @Test
  public void testMatchWhenReplyMatchSecondReply () {
    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);

    responses.clear();
    responses.add("xeroReply10");
    Matchable.Limited replyL = lineSet.match(responses);
    assertTrue(replyL.isMatched());
    assertTrue(zeroReply.isMatched());
    assertTrue(lineSet.isMatched());

    responses.clear();
    responses.add("twoReply02");
    replyL = lineSet.match(responses);
    assertTrue(replyL.isMatched());
    assertTrue(twoReply.isMatched());
    assertTrue(lineSet.isMatched());
  }

  @Test
  public void testClearMatches () {
    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);
    Matchable.Limited replyL = lineSet.match(responses);
    assertTrue(replyL.isMatched());

    lineSet.clearMatch();
    assertFalse(replyL.isMatched());
    assertFalse(lineSet.isMatched());
  }

  @Test
  public void testCompareToWithLargerGroupID () {
    Reply threeReply = new Reply(13, 1);
    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);
    assertTrue(lineSet.compareTo(threeReply) < 0);
  }

  @Test
  public void testCompareToWithSameGroupID () {
    Reply threeReply = new Reply(10, 1);
    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);
    assertTrue(lineSet.compareTo(threeReply) == 0);
    assertTrue(lineSet.compareTo(oneReply) < 0);

    Reply fourReply = new Reply(0, 1);
    assertTrue(lineSet.compareTo(fourReply) > 0);
  }

  @Test
  public void testCompareToSmallerGroupID () {
    Reply threeReply = new Reply(20, 0);
    ArrayList<Reply> threeReplyList = new ArrayList<>();
    threeReplyList.add(threeReply);
    ReplyLineSet threeReplyLineSet = new ReplyLineSet(threeReplyList);
    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);
    assertTrue(threeReplyLineSet.compareTo(lineSet) < 0);
  }

  @Test
  public void testGetNormalStartTime () {
    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);
    lineSet.noticeGroupScroll();
    assertEquals(1101, lineSet.getNormalStartTime());
  }

  @Test
  public void testGetNormalEndTime () {
    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);
    lineSet.noticeGroupScroll();
    assertEquals(1102, lineSet.getNormalEndTime());
  }

  @Test
  public void testGetMatchables () {
    ArrayList<String> compareStrings = new ArrayList<>();
    compareStrings.add("english phrase 10");
    compareStrings.add("english phrase 11");
    compareStrings.add("english phrase 12");

    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);
    List<Matchable> returnMatchables = lineSet.getMatchables();

    assertEquals(3, returnMatchables.size());

    ArrayList<String> returned = new ArrayList<>();
    returned.add(returnMatchables.get(0).getStringLines().get(0));
    returned.add(returnMatchables.get(1).getStringLines().get(0));
    returned.add(returnMatchables.get(2).getStringLines().get(0));

    assertTrue(returned.containsAll(compareStrings));
  }

  @Test
  public void testStringLines () {
    ArrayList<String> compareStrings = new ArrayList<>();
    compareStrings.add("english phrase 10");
    compareStrings.add("english phrase 11");
    compareStrings.add("english phrase 12");

    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);
    List<String> replyStrings = lineSet.getStringLines();
    assertEquals(3, replyStrings.size());
    assertTrue(compareStrings.containsAll(replyStrings));
  }

  @Test
  public void testFalseEqualsAndHashBecauseOfDifferentReplies () {
    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);

    ArrayList<Reply> smallerReplies = new ArrayList<>();
    smallerReplies.add(zeroReply);
    smallerReplies.add(oneReply);
    ReplyLineSet smallerReplyLineSet = new ReplyLineSet(smallerReplies);

    assertFalse(lineSet.equals(smallerReplyLineSet));
    assertFalse(lineSet.hashCode() == smallerReplyLineSet.hashCode());
  }

  @Test
  public void testFalseEqualsAndHashBecauseOfModifiedReply () {
    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);

    ArrayList<Reply> hasDiffReply = new ArrayList<>();
    hasDiffReply.add(zeroReply);
    hasDiffReply.add(oneReply);
    Reply diffReply = twoReply.clone();
    hasDiffReply.add(diffReply);
    ReplyLineSet diffLineSet = new ReplyLineSet(hasDiffReply);
    assertTrue(lineSet.equals(diffLineSet));
    assertTrue(lineSet.hashCode() == diffLineSet.hashCode());

    diffReply.setEngPhrase("diff english phrase");
    assertFalse(lineSet.equals(diffLineSet));
    assertFalse(lineSet.hashCode() == diffLineSet.hashCode());
  }

  @Test
  public void testFalseEqualsAndHashBecauseOfDifferentLastViewed () {
    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);
    ReplyLineSet diffSet = new ReplyLineSet(inputReplies);
    diffSet.noticeGroupScroll();

    assertFalse(lineSet.equals(diffSet));
    assertFalse(lineSet.hashCode() == diffSet.hashCode());
  }

  @Test
  public void testEqualsAndHash () {
    ReplyLineSet lineSet = new ReplyLineSet(inputReplies);
    ReplyLineSet cloneSet = new ReplyLineSet(inputReplies);
    assertTrue(lineSet.equals(cloneSet));
    assertTrue(lineSet.hashCode() == cloneSet.hashCode());
  }

}
