package com.olfybsppa.inglesaventurero.tests.utilstest;

import android.os.Bundle;

import com.olfybsppa.inglesaventurero.collectors.SceneInfo;
import com.olfybsppa.inglesaventurero.utils.Ez;
import com.olfybsppa.inglesaventurero.utils.MaP;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class EzTest {

  /**
   * Bundle should contain ENGLISH_TITLE, SPANISH_TITLE, FILENAME AND SCENE_WEB_ID from
   * passed in SceneInfo.
   */
  @Test
  public void testConvertingSceneInfoToBundle() {
    SceneInfo sceneInfo = new SceneInfo();
    sceneInfo.setEnglishTitle("EnglishT").
      setSpanishTitle("SpanishT").
      setFilename("fileN").
      setWebId(99);
    Bundle bundle = Ez.convertToBundle(sceneInfo);
    assertEquals("EnglishT", bundle.getString(MaP.ENGLISH_TITLE));
    assertEquals("SpanishT", bundle.getString(MaP.SPANISH_TITLE));
    assertEquals("fileN", bundle.getString(MaP.FILENAME));
    assertEquals(99, bundle.getInt(MaP.SCENE_WEB_ID));
  }

}