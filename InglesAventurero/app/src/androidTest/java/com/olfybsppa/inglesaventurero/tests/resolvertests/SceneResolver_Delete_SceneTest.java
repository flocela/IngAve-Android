package com.olfybsppa.inglesaventurero.tests.resolvertests;

import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPScene;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.SceneResolver;

public class SceneResolver_Delete_SceneTest extends ProviderTestCase2<LinesCP> {

  private MockContentResolver mMockResolver;
  private CPScene origCPScene;
  public SceneResolver_Delete_SceneTest() {
    super(LinesCP.class, LinesCP.AUTHORITY);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    mMockResolver = getMockContentResolver();
    TestSceneFactoryBasic cpSceneFactory = new TestSceneFactoryBasic();
    origCPScene = cpSceneFactory.getScene();
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testInsertThenDeleteScene () {
    ResolverWrapper rW = new ResolverWrapper(mMockResolver);
    SceneResolver sceneResolver = new SceneResolver(rW);
    sceneResolver.insertCPScene(origCPScene);
    sceneResolver.clearScene(origCPScene.getSceneName());

    // Test pages, hints, replies, attributions, backgrounds, image credits only in content provider once.
    assertEquals(1, getNumOfXXX(rW, LinesCP.sceneTableUri));
    assertEquals(0, getNumOfXXX(rW, LinesCP.pageTableUri));
    assertEquals(0, getNumOfXXX(rW, LinesCP.backgroundTableUri));
    assertEquals(0, getNumOfXXX(rW, LinesCP.hintTableUri));
    assertEquals(0, getNumOfXXX(rW, LinesCP.replyTableUri));
    assertEquals(0, getNumOfXXX(rW, LinesCP.attributionTableUri));
    assertEquals(0, getNumOfXXX(rW, LinesCP.imgCreditUri));
  }

  private int getNumOfXXX (ResolverWrapper rW, Uri uri) {
    int numOfXXX = -1;
    Cursor cursor = rW.shortQuery(uri, null, null);
    if (cursor != null) {
      numOfXXX = cursor.getCount();
      cursor.close();
    }
    return numOfXXX;
  }

}