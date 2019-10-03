package com.olfybsppa.inglesaventurero.stagehouseactivityutest.collectors_basics;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.Matchable;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Reply;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

public class ReplyUTest {

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
    assertEquals(11, defaultReply.getPosition());
    assertEquals(1,  defaultReply.getGroupId());
  }

  @Test
  public void testPositionsAreDifferentWhenSetInConstructor () {
    defaultReply = new Reply(13, 1);
    otherReply = new Reply(12, 1);
    assertFalse(defaultReply.equals(otherReply));
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());
  }

  @Test
  public void testGroupIdsAreDifferent () {
    defaultReply = new Reply(1, 3);
    otherReply = new Reply(1, 2);
    assertFalse(defaultReply.equals(otherReply));
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());
  }

  @Test
  public void testSetAndGetPositionInPage () {
    otherReply.setPositionInPage(33);
    assertEquals(33, otherReply.getPosition());
    assertFalse(defaultReply.equals(otherReply));
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());
  }

  @Test
  public void testSetAndGetExplanations () {
    otherReply.setEngExplanation(null);
    otherReply.setSpnExplanation(null);
    assertNull(otherReply.getEngExplanation());
    assertNull(otherReply.getSpnExplanation());
    assertFalse(defaultReply.equals(otherReply));
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());

    otherReply.setEngExplanation("eng explanation 2");
    otherReply.setSpnExplanation("spn explanation 2");
    assertEquals("eng explanation 2", otherReply.getEngExplanation());
    assertEquals("spn explanation 2", otherReply.getSpnExplanation());
    assertFalse(defaultReply.equals(otherReply));
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());
  }

  @Test
  public void testSetAndGetEnglishPhrase () {
    otherReply.setEngPhrase(null);
    assertNull(otherReply.getEnglishPhrase());
    assertFalse(defaultReply.equals(otherReply));
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());

    otherReply.setEngPhrase("diff english phrase");
    assertEquals("diff english phrase", otherReply.getEnglishPhrase());
    assertFalse(defaultReply.equals(otherReply));
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());
  }

  @Test
  public void testSetAndGetFollowingPage () {
    otherReply.setFollowingPage(13);
    assertEquals(13, otherReply.getFollowingPage());
    assertFalse(defaultReply.equals(otherReply));
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());
  }

  @Test
  public void testGetMatchablesAndGetStringLines () {
    ArrayList<Matchable> matchables = defaultReply.getMatchables();
    assertEquals(1, matchables.size());
    ArrayList<String> strings = matchables.get(0).getStringLines();
    assertEquals(1, strings.size());
    assertEquals("english phrase 1", strings.get(0));
  }

  @Test
  public void testSetAndGetNormalEndTimes () {
    otherReply.setNormalEndTime(2010);
    assertEquals(2010, otherReply.getNormalEndTime());
    assertFalse(defaultReply.equals(otherReply));
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());
  }

  @Test
  public void testSetAndGetNormalStartTimes () {
    otherReply.setNormalStartTime(2001);
    assertEquals(2001, otherReply.getNormalStartTime());
    assertFalse(defaultReply.equals(otherReply));
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());
  }

  @Test
  public void testSetAndGetRegex () {
    otherReply.setRegex(null);
    assertNull(otherReply.getRegex());
    assertFalse(defaultReply.equals(otherReply));
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());

    otherReply.setRegex("regex 2");
    assertEquals("regex 2", otherReply.getRegex());
    assertFalse(defaultReply.equals(otherReply));
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());
  }

  @Test
  public void testGetReplyStrings () {
    ArrayList<Matchable> matchables = new ArrayList<>();
    matchables.add(defaultReply);
    assertEquals(defaultReply, defaultReply.getMatchables().get(0));
    assertEquals(1, defaultReply.getMatchables().size());
  }

  @Test
  public void testSetAndGetSlowStartTimes () {
    otherReply.setSlowStartTime(2002);
    assertEquals(2002, otherReply.getSlowStartTime());
    assertFalse(defaultReply.equals(otherReply));
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());
  }

  @Test
  public void testSetAndGetSlowEndTimesAreEquals () {
    otherReply.setSlowEndTime(2012);
    assertEquals(2012, otherReply.getSlowEndTime());
    assertFalse(defaultReply.equals(otherReply));
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());
  }

  @Test
  public void testSetTimes () {
    defaultReply.setTimes(45, 46, 47, 48);
    assertEquals(45, defaultReply.getNormalStartTime());
    assertEquals(46, defaultReply.getNormalEndTime());
    assertEquals(47, defaultReply.getSlowStartTime());
    assertEquals(48, defaultReply.getSlowEndTime());
  }

  @Test
  public void testSetAndGetSpanishPhrases () {
    otherReply.setSpnPhrase(null);
    assertNull(otherReply.getSpanishPhrase());
    assertFalse(defaultReply.equals(otherReply));
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());

    otherReply.setSpnPhrase("spanish phrase 2");
    assertEquals("spanish phrase 2", otherReply.getSpanishPhrase());
    assertFalse(defaultReply.equals(otherReply));
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());
  }

  @Test
  public void testSetAndGetWFWEnglish () {
    otherReply.setWFWEng(null);
    assertNull(otherReply.getWFWEnglish());
    assertFalse(defaultReply.equals(otherReply));
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());

    otherReply.setWFWEng("wfw english 2");
    assertEquals("wfw english 2", otherReply.getWFWEnglish());
    assertFalse(defaultReply.equals(otherReply));
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());
  }

  @Test
  public void testSetAndGetWFWSpanish () {
    otherReply.setWFWSpn(null);
    assertNull(otherReply.getWFWSpanish());
    assertFalse(defaultReply.equals(otherReply));
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());

    otherReply.setWFWSpn("wfw spanish 2");
    assertEquals("wfw spanish 2", otherReply.getWFWSpanish());
    assertFalse(defaultReply.equals(otherReply));
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());
  }


  @Test
  public void testClearMatch () {
    Matchable.Limited returnReplyL = otherReply.match(responses);
    assertTrue(returnReplyL.isMatched());

    otherReply.clearMatch();
    assertFalse(otherReply.isMatched());
    assertEquals(otherReply, defaultReply);
  }

  @Test
  public void testCompareToWithSameGroupID() {
    Reply thirdReply  = new Reply(11, 1);
    Reply fourthReply = new Reply(2, 1);
    assertTrue(defaultReply.compareTo(thirdReply) == 0);
    assertTrue(defaultReply.compareTo(fourthReply)>0);
    assertTrue(fourthReply.compareTo(defaultReply)<0);
  }

  @Test
  public void testCompareToDiffGroupID () {
    Reply thirdReply = new Reply(1, 11);
    Reply fourthReply = new Reply(1, 22);
    assertFalse(defaultReply.compareTo(thirdReply) == 0);
    assertTrue(defaultReply.compareTo(fourthReply) < 0);
    assertTrue(fourthReply.compareTo(defaultReply) > 0);
  }

  @Test
  public void testMatchNegative () {
    responses.clear();
    responses.add("NOT A MATCH");
    Matchable.Limited replyL = otherReply.match(responses);
    assertFalse(replyL.isMatched());
    assertNull(replyL.getMatchedWith());
    assertTrue(-1 == replyL.getFollowingPage());
    assertTrue(replyL.getResponses().equals(responses));
    assertEquals(-1, replyL.getGroupID());
    assertEquals(-1, replyL.getLinePosition());
    //otherReply should no longer be equal to defaultReply.
    assertTrue(defaultReply.hashCode() == otherReply.hashCode());
    assertTrue(defaultReply.equals(otherReply));
  }

  @Test
  public void testMatchPositive () {
    Matchable.Limited replyL = otherReply.match(responses);
    assertTrue(replyL.isMatched());
    assertEquals("regex1", replyL.getMatchedWith());
    assertTrue(12 == replyL.getFollowingPage());
    assertTrue(replyL.getResponses().equals(responses));
    assertEquals(replyL.getGroupID(), otherReply.getGroupId());
    assertEquals(11, replyL.getLinePosition());
    //otherReply should no longer be equal to defaultReply.
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());
    assertFalse(defaultReply.equals(otherReply));
  }

  @Test
  public void testHashCode () {
    assertEquals(defaultReply.hashCode(), otherReply.hashCode());
    otherReply.setWFWSpn("different");
    assertFalse(defaultReply.hashCode() == otherReply.hashCode());
    Reply thirdReply = defaultReply.clone();
    assertTrue(thirdReply.hashCode() == defaultReply.hashCode());
  }

  @Test
  public void testCloneMethod () {
    otherReply = defaultReply.clone();
    assertTrue(defaultReply.equals(otherReply));
  }

  @Test
  public void testRepliesAreEqual () {
    assertTrue(defaultReply.equals(otherReply));
    assertTrue(defaultReply.hashCode() == otherReply.hashCode());
  }

  @Test
  public void testEqualsMethodWithArrayList () {
    ArrayList<Reply> list = new ArrayList<>();
    list.add(defaultReply);
    assertTrue(list.contains(otherReply));
  }

  @Test
  public void testEqualsReflexive () {
    assertTrue(defaultReply.equals(defaultReply));
  }

  @Test
  public void testEqualsSymmetric () {
    assertTrue(defaultReply.equals(otherReply));
    assertTrue(otherReply.equals(defaultReply));
  }

  @Test
  public void testEqualsTransitive () {
    Reply third = defaultReply.clone();
    assertTrue(defaultReply.equals(otherReply));
    assertTrue(otherReply.equals(third));
    assertTrue(defaultReply.equals(third));
  }

  @Test
  public void testEqualsNull () {
    assertFalse(defaultReply.equals(null));
  }

}
