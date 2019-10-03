package com.olfybsppa.inglesaventurero.tests.linesCPtests;

import android.database.sqlite.SQLiteDatabase;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPScene;

public class ResolverWrapperTest extends ProviderTestCase2<LinesCP> {

  private MockContentResolver mMockResolver;
  private SQLiteDatabase mDb;

  public ResolverWrapperTest() {
    super(LinesCP.class, LinesCP.AUTHORITY);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    mMockResolver = getMockContentResolver();
  }

  public void testContainSceneReturnsTrue () {
    CPScene scene = new CPScene ("firstScene", "primerTitulo", "firstTitle", "0", "0");
    mMockResolver.insert(LinesCP.sceneTableUri, scene.getTitleTableContentValues());

    ResolverWrapper resolverWrapper = new ResolverWrapper(mMockResolver);
    assertTrue(resolverWrapper.containsScene(scene.sceneName));
  }

}