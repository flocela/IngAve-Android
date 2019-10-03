package com.olfybsppa.inglesaventurero.webscenelistactivityutest.collectors_basics;

import android.database.Cursor;

import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPHint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CPHintUTest {

  CPHint hint;
  CPHint secondHint;

  @Mock
  Cursor mockCursor;

  @Before
  public void setUp() throws Exception {

    hint = new CPHint(1, 2);
    hint.setNextPage(12);
    hint.setNormalStartTime(1001);
    hint.setNormalEndTime(1010);
    hint.setSlowStartTime(1002);
    hint.setSlowEndTime(1012);
    hint.setEnglishPhrase("english phrase 1");
    hint.setSpanishPhrase("spanish phrase 1");
    hint.setWFWEnglish("wfw english 1");
    hint.setWFWSpanish("wfw spanish 1");
    hint.setEngExplanation("eng explanation 1");
    hint.setSpnExplanation("spn explanation 1");

    secondHint = new CPHint(1, 2);
    secondHint.setNextPage(12);
    secondHint.setNormalStartTime(1001);
    secondHint.setNormalEndTime(1010);
    secondHint.setSlowStartTime(1002);
    secondHint.setSlowEndTime(1012);
    secondHint.setEnglishPhrase("english phrase 1");
    secondHint.setSpanishPhrase("spanish phrase 1");
    secondHint.setWFWEnglish("wfw english 1");
    secondHint.setWFWSpanish("wfw spanish 1");
    secondHint.setEngExplanation("eng explanation 1");
    secondHint.setSpnExplanation("spn explanation 1");
  }

  @Test
  public void positionGetter () {
    assertEquals(1, hint.getPositionInPage());
  }

  @Test
  public void groupGetter () {
    assertEquals(2, hint.getGroupId());
  }
  @Test
  public void nextPageGetter () {
    assertEquals(new Integer(12), hint.getNextPage());
  }

  @Test
  public void normalStartTimeGetter () {
    assertEquals(1001, hint.getNormalStartTime());
  }

  @Test
  public void normalEndTimeGetter () {
    assertEquals(1010, hint.getNormalEndTime());
  }

  @Test
  public void slowStartTimeGetter () {
    assertEquals(1002, hint.getSlowStartTime());
  }

  @Test
  public void slowEndTimeGetter () {
    assertEquals(1012,hint.getSlowEndTime());
  }

  @Test
  public void englishPhraseGetter () {
    assertEquals("english phrase 1", hint.getEnglishPhrase());
  }

  @Test
  public void spanishPhraseGetter () {
    assertEquals("spanish phrase 1", hint.getSpanishPhrase());
  }

  @Test
  public void wfwEnglishGetter () {
    assertEquals("wfw english 1", hint.getWfwEnglish());
  }

  @Test
  public void wfwSpanishGetter () {
    assertEquals("wfw spanish 1", hint.getWfwSpanish());
  }

  @Test
  public void explanationGetter () {
    assertEquals("eng explanation 1", hint.getEngExplanation());
    assertEquals("spn explanation 1", hint.getSpnExplanation());
  }

  @Test
  public void testHintsAreEqual () {
    assertTrue(hint.equals(secondHint));
  }

  @Test
  public void testPositionsAreDifferent () {
    secondHint.setPositionInPage(2);
    assertFalse(hint.equals(secondHint));
    assertFalse(hint.hashCode() == secondHint.hashCode());
  }

  @Test
  public void testNextPagesAreDifferent () {
    secondHint.setNextPage(13);
    assertFalse(hint.equals(secondHint));
    assertFalse(hint.hashCode() == secondHint.hashCode());
  }

  @Test
  public void testNormalStartTimesAreDifferent () {
    secondHint.setNormalStartTime(2001);
    assertFalse(hint.equals(secondHint));
    assertFalse(hint.hashCode() == secondHint.hashCode());
  }

  @Test
  public void testNormalEndTimesAreDifferent () {
    secondHint.setNormalEndTime(2010);
    assertFalse(hint.equals(secondHint));
    assertFalse(hint.hashCode() == secondHint.hashCode());
  }

  @Test
  public void testSlowStartTimesAreDifferent () {
    secondHint.setSlowStartTime(2002);
    assertFalse(hint.equals(secondHint));
    assertFalse(hint.hashCode() == secondHint.hashCode());
  }

  @Test
  public void testSlowEndTimesAreDifferent () {
    secondHint.setSlowEndTime(2012);
    assertFalse(hint.equals(secondHint));
    assertFalse(hint.hashCode() == secondHint.hashCode());
  }

  @Test
  public void testEnglishPhrasesAreDifferent () {
    secondHint.setEnglishPhrase(null);
    assertFalse(hint.equals(secondHint));
    assertFalse(hint.hashCode() == secondHint.hashCode());
    secondHint.setEnglishPhrase("english phrase 2");
    assertFalse(hint.equals(secondHint));
    assertFalse(hint.hashCode() == secondHint.hashCode());
  }

  @Test
  public void testSpanishPhrasesAreDifferent () {
    secondHint.setSpanishPhrase(null);
    assertFalse(hint.equals(secondHint));
    assertFalse(hint.hashCode() == secondHint.hashCode());
    secondHint.setSpanishPhrase("spanish phrase 2");
    assertFalse(hint.equals(secondHint));
    assertFalse(hint.hashCode() == secondHint.hashCode());
  }

  @Test
  public void testWFWEnglishAreDifferent () {
    secondHint.setWFWEnglish(null);
    assertFalse(hint.equals(secondHint));
    assertFalse(hint.hashCode() == secondHint.hashCode());
    secondHint.setWFWEnglish("wfw english 2");
    assertFalse(hint.equals(secondHint));
    assertFalse(hint.hashCode() == secondHint.hashCode());
  }

  @Test
  public void testWFWSpanishAreDifferent () {
    secondHint.setWFWSpanish(null);
    assertFalse(hint.equals(secondHint));
    assertFalse(hint.hashCode() == secondHint.hashCode());
    secondHint.setWFWSpanish("wfw spanish 2");
    assertFalse(hint.equals(secondHint));
    assertFalse(hint.hashCode() == secondHint.hashCode());
  }

  @Test
  public void testExplanationsAreDifferent () {
    secondHint.setEngExplanation(null);
    secondHint.setSpnExplanation(null);
    assertFalse(hint.equals(secondHint));
    assertFalse(hint.hashCode() == secondHint.hashCode());
    secondHint.setEngExplanation("explanation 2");
    secondHint.setSpnExplanation("explanation 2");
    assertFalse(hint.equals(secondHint));
    assertFalse(hint.hashCode() == secondHint.hashCode());
  }

  @Test
  public void testCloneMethod () {
    secondHint = hint.clone();
    assertTrue(hint.equals(secondHint));
  }

  @Test
  public void testHashCodeMethod () {
    assertEquals(hint.hashCode(), secondHint.hashCode());
  }

  @Test
  public void testExtractHint () {
    when(mockCursor.getColumnIndex(LinesCP.pos_id)).thenReturn(1);
    when(mockCursor.getInt(1)).thenReturn(1);

    when(mockCursor.getColumnIndex(LinesCP.hint_group_id)).thenReturn(12);
    when(mockCursor.getInt(12)).thenReturn(2);

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

    CPHint hintFromCursor = CPHint.extractCPHint(mockCursor);
    assertTrue(hintFromCursor.equals(hint));
  }

}
