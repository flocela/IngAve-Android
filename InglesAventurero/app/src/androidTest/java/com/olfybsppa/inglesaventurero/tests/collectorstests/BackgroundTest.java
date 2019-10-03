package com.olfybsppa.inglesaventurero.tests.collectorstests;

import android.content.ContentValues;

import com.olfybsppa.inglesaventurero.collectors.Background;
import com.olfybsppa.inglesaventurero.start.LinesCP;

import junit.framework.TestCase;

import org.junit.Test;

public class BackgroundTest extends TestCase {

  @Test
  public void testGetContentValues () {
    Background background = new Background("one", "one.jpg");

    ContentValues cV = background.getContentValues();
    assertEquals("one", cV.getAsString(LinesCP.background_name));
  }


}