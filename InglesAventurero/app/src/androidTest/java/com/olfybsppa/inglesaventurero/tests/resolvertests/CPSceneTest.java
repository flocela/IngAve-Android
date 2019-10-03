package com.olfybsppa.inglesaventurero.tests.resolvertests;

import android.content.ContentValues;

import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPScene;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CPSceneTest{

  @Test
  public void testGetContentValues () {
    CPScene cpScene = new CPScene("Scene Name", "English Title", "Titulo en Espanol", "10", "11");
    ContentValues cV = cpScene.getTitleTableContentValues();
    assertEquals("Scene Name", cV.getAsString(LinesCP.scene_name));
    assertEquals("English Title", cV.getAsString(LinesCP.english_title));
    assertEquals("Titulo en Espanol", cV.getAsString(LinesCP.spanish_title));
    assertEquals("10", cV.getAsString(LinesCP.type_1));
    assertEquals("11", cV.getAsString(LinesCP.type_2));
  }
}