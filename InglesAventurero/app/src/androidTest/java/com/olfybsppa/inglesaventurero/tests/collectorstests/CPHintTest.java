package com.olfybsppa.inglesaventurero.tests.collectorstests;

import android.content.ContentValues;

import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPHint;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CPHintTest {

  @Test
  public void testGetContentValues () {
    CPHint cpHint = new CPHint(1, 22);
    cpHint.setNextPage(12);
    cpHint.setNormalStartTime(1001);
    cpHint.setNormalEndTime(1010);
    cpHint.setSlowStartTime(1002);
    cpHint.setSlowEndTime(1012);
    cpHint.setEnglishPhrase("english phrase 1");
    cpHint.setSpanishPhrase("spanish phrase 1");
    cpHint.setWFWEnglish("wfw english 1");
    cpHint.setWFWSpanish("wfw spanish 1");
    cpHint.setSpnExplanation("spanish explanation 1");
    cpHint.setEngExplanation("english explanation 1");

    ContentValues cV = cpHint.getContentValues(777, 111); //adds scenId and pageId to content values.
    assertEquals(777, cV.getAsInteger(LinesCP.scene_id).intValue());
    assertEquals(111, cV.getAsInteger(LinesCP.page_id).intValue());
    assertEquals(22, cV.getAsInteger(LinesCP.hint_group_id).intValue());
    assertEquals(1,  cV.getAsInteger(LinesCP.pos_id).intValue());
    assertEquals(12, cV.getAsInteger(LinesCP.next_page_name).intValue());
    assertEquals(1001l, cV.getAsLong(LinesCP.normal_start_time).longValue());
    assertEquals(1010l, cV.getAsLong(LinesCP.normal_end_time).longValue());
    assertEquals(1002l, cV.getAsLong(LinesCP.slow_start_time).longValue());
    assertEquals(1012l, cV.getAsLong(LinesCP.slow_end_time).longValue());
    assertEquals("english phrase 1", cV.getAsString(LinesCP.english_phrase));
    assertEquals("spanish phrase 1", cV.getAsString(LinesCP.spanish_phrase));
    assertEquals("wfw english 1", cV.getAsString(LinesCP.wfw_english));
    assertEquals("wfw spanish 1", cV.getAsString(LinesCP.wfw_spanish));
    assertEquals("english explanation 1", cV.getAsString(LinesCP.eng_explanation));
    assertEquals("spanish explanation 1", cV.getAsString(LinesCP.spn_explanation));
  }

}