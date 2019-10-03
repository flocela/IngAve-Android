package com.olfybsppa.inglesaventurero.tests.collectorstests;

import android.content.ContentValues;

import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPReply;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CPReplyTest {

  @Test
  public void testGetContentValues () {
    CPReply cpReply = new CPReply(1, 2);
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

    ContentValues cV = cpReply.getContentValues(777, 111);
    assertEquals(777, cV.getAsInteger(LinesCP.scene_id).intValue());
    assertEquals(111, cV.getAsInteger(LinesCP.page_id).intValue());
    assertEquals(1, cV.getAsInteger(LinesCP.pos_id).intValue());
    assertEquals(2, cV.getAsInteger(LinesCP.reply_group_id).intValue());
    assertEquals(12, cV.getAsInteger(LinesCP.next_page_name).intValue());
    assertEquals(1001l, cV.getAsLong(LinesCP.normal_start_time).longValue());
    assertEquals(1010l, cV.getAsLong(LinesCP.normal_end_time).longValue());
    assertEquals(1002l, cV.getAsLong(LinesCP.slow_start_time).longValue());
    assertEquals(1012l, cV.getAsLong(LinesCP.slow_end_time).longValue());
    assertEquals("english phrase 1", cV.getAsString(LinesCP.english_phrase));
    assertEquals("spanish phrase 1", cV.getAsString(LinesCP.spanish_phrase));
    assertEquals("wfw english 1", cV.getAsString(LinesCP.wfw_english));
    assertEquals("wfw spanish 1", cV.getAsString(LinesCP.wfw_spanish));
    assertEquals("eng explanation 1", cV.getAsString(LinesCP.eng_explanation));
    assertEquals("spn explanation 1", cV.getAsString(LinesCP.spn_explanation));
    assertEquals("regex 1", cV.getAsString(LinesCP.regex));
  }

}