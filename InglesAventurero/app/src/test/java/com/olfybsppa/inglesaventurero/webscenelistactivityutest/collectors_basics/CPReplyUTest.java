package com.olfybsppa.inglesaventurero.webscenelistactivityutest.collectors_basics;

import android.database.Cursor;

import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPReply;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
public class CPReplyUTest {

  CPReply cpReply;
  CPReply secondCPReply;

  @Before
  public void setUp() throws Exception {
    cpReply = new CPReply(1, 11);
    cpReply.setNextPage(12);
    cpReply.setNormalStartTime(1001);
    cpReply.setNormalEndTime(1010);
    cpReply.setSlowStartTime(1002);
    cpReply.setSlowEndTime(1012);
    cpReply.setEnglishPhrase("english phrase 1");
    cpReply.setSpanishPhrase("spanish phrase 1");
    cpReply.setWFWEnglish("wfw english 1");
    cpReply.setWFWSpanish("wfw spanish 1");
    cpReply.setEngExplanation("eng explanation 1");
    cpReply.setSpnExplanation("spn explanation 1");
    cpReply.setRegex("regex 1");

    secondCPReply = new CPReply(1, 11);
    secondCPReply.setNextPage(12);
    secondCPReply.setNormalStartTime(1001);
    secondCPReply.setNormalEndTime(1010);
    secondCPReply.setSlowStartTime(1002);
    secondCPReply.setSlowEndTime(1012);
    secondCPReply.setEnglishPhrase("english phrase 1");
    secondCPReply.setSpanishPhrase("spanish phrase 1");
    secondCPReply.setWFWEnglish("wfw english 1");
    secondCPReply.setWFWSpanish("wfw spanish 1");
    secondCPReply.setEngExplanation("eng explanation 1");
    secondCPReply.setSpnExplanation("spn explanation 1");
    secondCPReply.setRegex("regex 1");
  }

  @Test
  public void testPositionGetter () {
    assertEquals(1, cpReply.getPositionInPage());
  }

  @Test
  public void testGroupIDGetter () {
    assertEquals(new Integer(11), cpReply.getReplyGroup());
  }

  @Test
  public void testNextPageGetter () {
    assertEquals(new Integer(12), cpReply.getNextPage());
  }

  @Test
  public void testNormalStartTimeGetter () {
    assertEquals(1001, cpReply.getNormalStartTime());
  }

  @Test
  public void testNormalEndTimeGetter () {
    assertEquals(1010, cpReply.getNormalEndTime());
  }

  @Test
  public void testSlowStartTimeGetter () {
    assertEquals(1002, cpReply.getSlowStartTime());
  }

  @Test
  public void testSlowEndTimeGetter () {
    assertEquals(1012, cpReply.getSlowEndTime());
  }

  @Test
  public void testEnglishPhraseGetter () {
    assertEquals("english phrase 1", cpReply.getEnglishPhrase());
  }

  @Test
  public void testSpanishPhraseGetter () {
    assertEquals("spanish phrase 1", cpReply.getSpanishPhrase());
  }

  @Test
  public void testWFWEnglishGetter () {
    assertEquals("wfw english 1", cpReply.getWfwEnglish());
  }

  @Test
  public void testWFWSpanishGetter () {
    assertEquals("wfw spanish 1", cpReply.getWfwSpanish());
  }

  @Test
  public void testExplanationGetter () {
    assertEquals("eng explanation 1", cpReply.getEngExplanation());
    assertEquals("spn explanation 1", cpReply.getSpnExplanation());
  }

  @Test
  public void testRegexGetter () {
    assertEquals("regex 1", cpReply.getRegex());
  }

  @Test
  public void testSetTimes () {
    cpReply.setTimes(47, 48, 49, 50);
    assertEquals(47, cpReply.getNormalStartTime());
    assertEquals(48, cpReply.getNormalEndTime());
    assertEquals(49, cpReply.getSlowStartTime());
    assertEquals(50, cpReply.getSlowEndTime());
  }


  @Test
  public void testReplysAreEqual () {
    assertTrue(cpReply.equals(secondCPReply));
  }

  @Test
  public void testPositionsAreDifferent () {
    secondCPReply.setPositionInPage(2);
    assertFalse(cpReply.equals(secondCPReply));
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode());
  }

  @Test
  public void testGroupIdsAreDifferent () {
    secondCPReply.setGroupId(12);
    assertFalse(cpReply.equals(secondCPReply));
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode());
  }

  @Test
  public void testNextPagesAreDifferent () {
    secondCPReply.setNextPage(13);
    assertFalse(cpReply.equals(secondCPReply));
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode());
  }

  @Test
  public void testNormalStartTimesAreDifferent () {
    secondCPReply.setNormalStartTime(2001);
    assertFalse(cpReply.equals(secondCPReply));
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode());
  }

  @Test
  public void testNormalEndTimesAreDifferent () {
    secondCPReply.setNormalEndTime(2010);
    assertFalse(cpReply.equals(secondCPReply));
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode());
  }

  @Test
  public void testSlowStartTimesAreDifferent () {
    secondCPReply.setSlowStartTime(2002);
    assertFalse(cpReply.equals(secondCPReply));
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode());
  }

  @Test
  public void testSlowEndTimesAreDifferent () {
    secondCPReply.setSlowEndTime(2012);
    assertFalse(cpReply.equals(secondCPReply));
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode());
  }

  @Test
  public void testEnglishPhrasesAreDifferent () {
    secondCPReply.setEnglishPhrase(null);
    assertFalse(cpReply.equals(secondCPReply));
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode());

    secondCPReply.setEnglishPhrase("english phrase 2");
    assertFalse(cpReply.equals(secondCPReply));
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode());
  }

  @Test
  public void testSpanishPhrasesAreDifferent () {
    secondCPReply.setSpanishPhrase(null);
    assertFalse(cpReply.equals(secondCPReply));
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode());

    secondCPReply.setSpanishPhrase("spanish phrase 2");
    assertFalse(cpReply.equals(secondCPReply));
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode());
  }

  @Test
  public void testWFWEnglishAreDifferent () {
    secondCPReply.setWFWEnglish(null);
    assertFalse(cpReply.equals(secondCPReply));
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode());

    secondCPReply.setWFWEnglish("wfw english 2");
    assertFalse(cpReply.equals(secondCPReply));
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode());
  }

  @Test
  public void testWFWSpanishAreDifferent () {
    secondCPReply.setWFWSpanish(null);
    assertFalse(cpReply.equals(secondCPReply));
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode());

    secondCPReply.setWFWSpanish("wfw spanish 2");
    assertFalse(cpReply.equals(secondCPReply));
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode());
  }

  @Test
  public void testEngExplanationsAreDifferent () {
    secondCPReply.setEngExplanation(null);
    assertFalse(cpReply.equals(secondCPReply));
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode());

    secondCPReply.setEngExplanation("eng explanation 2");
    assertFalse(cpReply.equals(secondCPReply));
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode());
  }

  @Test
  public void testSpnExplanationsAreDifferent () {
    secondCPReply.setSpnExplanation(null);
    assertFalse(cpReply.equals(secondCPReply));
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode());

    secondCPReply.setSpnExplanation("spn explanation 2");
    assertFalse(cpReply.equals(secondCPReply));
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode());
  }

  @Test
  public void testRegexAreDifferent () {
    secondCPReply.setRegex("different");
    assertFalse(cpReply.equals(secondCPReply));
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode());
  }

  @Test
  public void testHashCode () {
    assertEquals(cpReply.hashCode(), secondCPReply.hashCode());
    secondCPReply.setEngExplanation("different");
    assertFalse(cpReply.hashCode() == secondCPReply.hashCode()); //hashcode doesn't
    // actually have to be different.
  }

  @Test
  public void testCloneMethod () {
    secondCPReply = cpReply.clone();
    assertTrue(cpReply.equals(secondCPReply));
  }

  @Test
  public void testExtractHint () {
    Cursor mockCursor = mock(Cursor.class);
    when(mockCursor.getColumnIndex(LinesCP.pos_id)).thenReturn(1);
    when(mockCursor.getInt(1)).thenReturn(1);

    when(mockCursor.getColumnIndex(LinesCP.reply_group_id)).thenReturn(111);
    PowerMockito.when(mockCursor.getInt(111)).thenReturn(11);

    when(mockCursor.getColumnIndex(LinesCP.next_page_name)).thenReturn(2);
    when(mockCursor.getInt(2)).thenReturn(12);

    when(mockCursor.getColumnIndex(LinesCP.normal_start_time)).thenReturn(3);
    when(mockCursor.getInt(3)).thenReturn(1001);

    when(mockCursor.getColumnIndex(LinesCP.normal_end_time)).thenReturn(4);
    when(mockCursor.getInt(4)).thenReturn(1010);

    when(mockCursor.getColumnIndex(LinesCP.slow_start_time)).thenReturn(5);
    when(mockCursor.getInt(5)).thenReturn(1002);

    when(mockCursor.getColumnIndex(LinesCP.slow_end_time)).thenReturn(6);
    when(mockCursor.getInt(6)).thenReturn(1012);

    when(mockCursor.getColumnIndex(LinesCP.english_phrase)).thenReturn(7);
    when(mockCursor.getString(7)).thenReturn("english phrase 1");

    when(mockCursor.getColumnIndex(LinesCP.spanish_phrase)).thenReturn(8);
    when(mockCursor.getString(8)).thenReturn("spanish phrase 1");

    when(mockCursor.getColumnIndex(LinesCP.wfw_english)).thenReturn(9);
    when(mockCursor.getString(9)).thenReturn("wfw english 1");

    when(mockCursor.getColumnIndex(LinesCP.wfw_spanish)).thenReturn(10);
    when(mockCursor.getString(10)).thenReturn("wfw spanish 1");

    when(mockCursor.getColumnIndex(LinesCP.eng_explanation)).thenReturn(11);
    when(mockCursor.getString(11)).thenReturn("eng explanation 1");

    when(mockCursor.getColumnIndex(LinesCP.spn_explanation)).thenReturn(12);
    when(mockCursor.getString(12)).thenReturn("spn explanation 1");

    when(mockCursor.getColumnIndex(LinesCP.regex)).thenReturn(13);
    when(mockCursor.getString(13)).thenReturn("regex 1");

    CPReply replyFromCursor = CPReply.extractCPReply(mockCursor);
    assertTrue(replyFromCursor.equals(cpReply));

  }
}