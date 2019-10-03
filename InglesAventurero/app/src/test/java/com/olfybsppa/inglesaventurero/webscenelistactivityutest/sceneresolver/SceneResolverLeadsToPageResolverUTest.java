package com.olfybsppa.inglesaventurero.webscenelistactivityutest.sceneresolver;

import android.content.ContentValues;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.collectors.Background;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPPage;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPScene;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.PageResolver;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.SceneResolver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SceneResolver.class)
public class SceneResolverLeadsToPageResolverUTest {

  @Mock ResolverWrapper mockRW;
  @Mock CPScene         mockCPScene;
  @Mock ContentValues   mockCV;
  @Mock ContentValues   mockBadCV;
  @Mock PageResolver    mockPageResolver;
  Background                            background1 = new Background("bk1", "bk1.jpg");
  HashSet<Attribution>                  attrHash1   = new HashSet<>();
  int                                   sceneID     = 10;
  CPPage                                page1       = new CPPage(1);
  ArrayList<CPPage>                     pages       = new ArrayList<>();
  HashMap<Integer, Background>          backgrounds = new HashMap<>();
  HashMap<String, HashSet<Attribution>> attrsByBkgd = new HashMap<>();
  int                                   pageID      = 1;

  @Before
  public  void setUp() throws Exception {
    when(mockCPScene.getTitleTableContentValues()).thenReturn(mockCV);
    mockStatic(PageResolver.class);
    whenNew(PageResolver.class).withAnyArguments().thenReturn(mockPageResolver);
    when(mockPageResolver.insertPage(page1, sceneID, background1, attrHash1)).thenReturn(pageID);

    pages.add(page1);
    backgrounds.put(1, background1);
    attrsByBkgd.put("bk1", attrHash1);

    when(mockCPScene.getPages()).thenReturn(pages);
    when(mockCPScene.getSceneName()).thenReturn("sceneName");
    when(mockRW.retrieveSceneRowId("sceneName")).thenReturn(-1);
    when(mockRW.insertScene(mockCV)).thenReturn(sceneID);
    when(mockCPScene.getBkgdsByPageName()).thenReturn(backgrounds);
    when(mockCPScene.getAttrsByBkgdName()).thenReturn(attrsByBkgd);
  }

  @Test
  public void testPageResolverInsertMethodCalledWhenInsertCPSceneMethodCalled() {

    SceneResolver sceneResolver = new SceneResolver(mockRW);
    sceneResolver.insertCPScene(mockCPScene);

    verify(mockPageResolver).insertPage(page1, sceneID, background1, attrHash1);
  }
}
