package com.olfybsppa.inglesaventurero.tests.utilstest;

import android.net.Uri;

import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.utils.UriDeterminer;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class URIDeterminerTest {

  @Test
  public void testConvertingSceneInfoToBundle() {
    Uri sceneTableUri = LinesCP.sceneTableUri;
    assertEquals(LinesCP.scene_table, UriDeterminer.getTableName(sceneTableUri));
  }

  @Test
  public void testGetSceneId () {
    Uri sceneTableUri = LinesCP.sceneTableUri;
    Uri.Builder builder = sceneTableUri.buildUpon();
    builder.appendPath("13");
    Uri sceneWithId = builder.build();
    assertEquals(Integer.valueOf(13), UriDeterminer.getLastId(sceneWithId));
  }

}