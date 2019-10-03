package com.olfybsppa.inglesaventurero.tests.collectorstests;

import android.content.ContentValues;

import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPPage;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class CPPageTest {

  @Test
  public void testGetContentValues () {
    CPPage page = new CPPage();
    page.setPageName(3);
    page.setAsFirst(true);

    ContentValues cV = page.getContentValues(10, 12);
    assertEquals(3, cV.getAsInteger(LinesCP.page_name).intValue());
    assertEquals(12, cV.getAsInteger(LinesCP.background_id).intValue());
    assertEquals(10, cV.getAsInteger(LinesCP.scene_id).intValue());
    assertTrue(cV.getAsBoolean(LinesCP.is_first));
  }

}