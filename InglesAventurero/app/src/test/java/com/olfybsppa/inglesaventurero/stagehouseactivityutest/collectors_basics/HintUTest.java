package com.olfybsppa.inglesaventurero.stagehouseactivityutest.collectors_basics;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.Hint;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

public class HintUTest {

  Hint defaultHint;
  Hint otherHint;

  @Before
  public  void setUp() throws Exception {
    defaultHint = new Hint(1, 22);
    defaultHint.setFollowingPage(12);
    defaultHint.setNormalStartTime(1001);
    defaultHint.setNormalEndTime(1010);
    defaultHint.setSlowStartTime(1002);
    defaultHint.setSlowEndTime(1012);
    defaultHint.setEngPhrase("english phrase 1");
    defaultHint.setSpnPhrase("spanish phrase 1");
    defaultHint.setWFWEng("wfw english 1");
    defaultHint.setWFWSpn("wfw spanish 1");
    defaultHint.setEngExplanation("eng explanation 1");
    defaultHint.setSpnExplanation("spn explanation 1");

    // otherHint starts off as the same as defaultHint.
    otherHint = new Hint(1, 22);
    otherHint.setFollowingPage(12);
    otherHint.setNormalStartTime(1001);
    otherHint.setNormalEndTime(1010);
    otherHint.setSlowStartTime(1002);
    otherHint.setSlowEndTime(1012);
    otherHint.setEngPhrase("english phrase 1");
    otherHint.setSpnPhrase("spanish phrase 1");
    otherHint.setWFWEng("wfw english 1");
    otherHint.setWFWSpn("wfw spanish 1");
    otherHint.setEngExplanation("eng explanation 1");
    otherHint.setSpnExplanation("spn explanation 1");
  }

  @Test
  public void testConstructor () {
    assertEquals(1, defaultHint.getPosition());
    assertEquals(22, defaultHint.getGroupId());
  }

  @Test
  public void testPositionsAreDifferentWhenSetInConstructor () {
    defaultHint = new Hint(1, 0);
    otherHint = new Hint(2, 0);
    assertFalse(defaultHint.equals(otherHint));
    assertFalse(otherHint.hashCode() == defaultHint.hashCode());
  }

  @Test
  public void testSetAndGetFollowingPages () {
    otherHint.setFollowingPage(13);
    assertEquals(13, otherHint.getFollowingPage());
    assertFalse(defaultHint.equals(otherHint));
    assertFalse(otherHint.hashCode() == defaultHint.hashCode());
  }

  @Test
  public void testSetAndGetHeard () {
    assertFalse(defaultHint.wasHeard());
    otherHint.setHeard(true);
    assertTrue(otherHint.wasHeard());
    assertFalse(defaultHint.equals(otherHint));
    assertFalse(otherHint.hashCode() == defaultHint.hashCode());
  }

  @Test
  public void testSetAndGetNormalStartTimes () {
    otherHint.setNormalStartTime(2001);
    assertEquals(2001, otherHint.getNormalStartTime());
    assertFalse(defaultHint.equals(otherHint));
    assertFalse(otherHint.hashCode() == defaultHint.hashCode());
  }

  @Test
  public void testSetAndGetNormalEndTimes () {
    otherHint.setNormalEndTime(2010);
    assertEquals(2010, otherHint.getNormalEndTime());
    assertFalse(defaultHint.equals(otherHint));
    assertFalse(otherHint.hashCode() == defaultHint.hashCode());
  }

  @Test
  public void testSetAndGetSlowStartTimes () {
    otherHint.setSlowStartTime(2002);
    assertEquals(2002, otherHint.getSlowStartTime());
    assertFalse(defaultHint.equals(otherHint));
    assertFalse(otherHint.hashCode() == defaultHint.hashCode());
  }

  @Test
  public void testSetAndGetSlowEndTimes () {
    otherHint.setSlowEndTime(2012);
    assertEquals(2012, otherHint.getSlowEndTime());
    assertFalse(defaultHint.equals(otherHint));
    assertFalse(otherHint.hashCode() == defaultHint.hashCode());
  }

  @Test
  public void testSetTimes () {
    defaultHint.setTimes(45, 46, 47, 48);
    assertEquals(45, defaultHint.getNormalStartTime());
    assertEquals(46, defaultHint.getNormalEndTime());
    assertEquals(47, defaultHint.getSlowStartTime());
    assertEquals(48, defaultHint.getSlowEndTime());
    assertFalse(defaultHint.equals(otherHint));
    assertFalse(otherHint.hashCode() == defaultHint.hashCode());
  }

  @Test
  public void testSetAndGetEnglishPhrases () {
    otherHint.setEngPhrase(null);
    assertNull(otherHint.getEnglishPhrase());
    assertFalse(defaultHint.equals(otherHint));
    assertFalse(otherHint.hashCode() == defaultHint.hashCode());

    otherHint.setEngPhrase("english phrase 2");
    assertEquals("english phrase 2", otherHint.getEnglishPhrase());
    assertFalse(defaultHint.equals(otherHint));
    assertFalse(otherHint.hashCode() == defaultHint.hashCode());
  }

  @Test
  public void testSetAndGetSpanishPhrases () {
    otherHint.setSpnPhrase(null);
    assertNull(otherHint.getSpanishPhrase());
    assertFalse(defaultHint.equals(otherHint));
    assertFalse(otherHint.hashCode() == defaultHint.hashCode());

    otherHint.setSpnPhrase("spanish phrase 2");
    assertEquals("spanish phrase 2", otherHint.getSpanishPhrase());
    assertFalse(defaultHint.equals(otherHint));
    assertFalse(otherHint.hashCode() == defaultHint.hashCode());
  }

  @Test
  public void testSetAndGetWFWEnglish () {
    otherHint.setWFWEng(null);
    assertNull(otherHint.getWFWEnglish());
    assertFalse(defaultHint.equals(otherHint));
    assertFalse(otherHint.hashCode() == defaultHint.hashCode());

    otherHint.setWFWEng("wfw english 2");
    assertEquals("wfw english 2", otherHint.getWFWEnglish());
    assertFalse(defaultHint.equals(otherHint));
    assertFalse(otherHint.hashCode() == defaultHint.hashCode());
  }

  @Test
  public void testSetAndGetWFWSpanish () {
    otherHint.setWFWSpn(null);
    assertNull(otherHint.getWFWSpanish());
    assertFalse(defaultHint.equals(otherHint));
    assertFalse(otherHint.hashCode() == defaultHint.hashCode());

    otherHint.setWFWSpn("wfw spanish 2");
    assertEquals("wfw spanish 2", otherHint.getWFWSpanish());
    assertFalse(defaultHint.equals(otherHint));
    assertFalse(otherHint.hashCode() == defaultHint.hashCode());
  }

  @Test
  public void testSetAndGetExplanations () {
    otherHint.setEngExplanation(null);
    otherHint.setSpnExplanation(null);
    assertNull(otherHint.getEngExplanation());
    assertNull(otherHint.getSpnExplanation());
    assertFalse(defaultHint.equals(otherHint));
    assertFalse(otherHint.hashCode() == defaultHint.hashCode());

    otherHint.setEngExplanation("eng explanation 2");
    otherHint.setSpnExplanation("spn explanation 2");
    assertEquals("eng explanation 2", otherHint.getEngExplanation());
    assertEquals("spn explanation 2", otherHint.getSpnExplanation());
    assertFalse(defaultHint.equals(otherHint));
    assertFalse(otherHint.hashCode() == defaultHint.hashCode());
  }

  @Test
  public void testCloneMethod () {
    otherHint = defaultHint.clone();
    assertTrue(defaultHint.equals(otherHint));
    assertTrue(otherHint.hashCode() == defaultHint.hashCode());

    defaultHint.setHeard(true);
    otherHint = defaultHint.clone();
    assertTrue(defaultHint.equals(otherHint));
    assertTrue(otherHint.hashCode() == defaultHint.hashCode());
  }

  @Test
  public void testHintsAreEqual () {
    assertTrue(defaultHint.equals(otherHint));
  }

  @Test
  public void testEqualsMethodWithArrayList () {
    ArrayList<Hint> list = new ArrayList<>();
    list.add(defaultHint);
    assertTrue(list.contains(otherHint));
  }

  @Test
  public void testEqualsReflexive () {
    assertTrue(defaultHint.equals(defaultHint));
  }

  @Test
  public void testEqualsSymmetric () {
    assertTrue(defaultHint.equals(otherHint));
    assertTrue(otherHint.equals(defaultHint));
  }

  @Test
  public void testEqualsTransitive () {
    Hint third = defaultHint.clone();
    assertTrue(defaultHint.equals(otherHint));
    assertTrue(otherHint.equals(third));
    assertTrue(defaultHint.equals(third));
  }

  @Test
  public void testEqualsNull () {
    assertFalse(defaultHint.equals(null));
  }

  @Test 
  public void testCompareToSameGroupID () {
    Hint thirdHint = new Hint(1, 22);
    Hint fourthHint = new Hint(2, 22);
    Hint fifthHint  = new Hint(4, 22);
    assertTrue(defaultHint.compareTo(thirdHint) == 0);
    assertTrue(defaultHint.compareTo(fourthHint)<0);
    assertTrue(fifthHint.compareTo(defaultHint)>0);
  }

  @Test 
  public void testCompareToDiffGroupID () {
    Hint thirdHint = new Hint(1, 21);
    Hint fourthHint = new Hint(1, 22);
    Hint fifthHint  = new Hint(1, 23);
    assertTrue(defaultHint.compareTo(thirdHint) > 0);
    assertTrue(defaultHint.compareTo(fourthHint) == 0);
    assertTrue(defaultHint.compareTo(fifthHint)< 0);
  }

}
