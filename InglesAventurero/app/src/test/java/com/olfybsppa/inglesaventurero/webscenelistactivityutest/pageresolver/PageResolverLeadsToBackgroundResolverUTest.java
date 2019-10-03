package com.olfybsppa.inglesaventurero.webscenelistactivityutest.pageresolver;

import android.content.ContentValues;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.collectors.Background;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPPage;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.BackgroundResolver;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.PageResolver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashSet;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PageResolver.class)
public class PageResolverLeadsToBackgroundResolverUTest {

  @Mock ResolverWrapper    mockRW;
  @Mock BackgroundResolver mockBackgroundResolver;
  @Mock CPPage             mockCPPage;
  @Mock ContentValues      mockCV;
  @Mock ContentValues      mockBadCV;
  int                      sceneID     = 10;
  Background               background1 = new Background("bk1", "bk1.jpg");
  Background               background2 = new Background("bk2", "bk2.jpg");
  HashSet<Attribution>     attrHash1   = new HashSet<>();

  @Before
  public  void setUp() throws Exception {
    whenNew(BackgroundResolver.class).withAnyArguments().thenReturn(mockBackgroundResolver);
  }

  @Test
  public void testBackgroundResolversInsertMethodCalledWhenInsertPageMethodCalled() {

    PageResolver pageResolver = new PageResolver(mockRW);
    pageResolver.insertPage(mockCPPage, sceneID, background1, attrHash1);

    verify(mockBackgroundResolver).insertBkgdWithAttributions(background1, attrHash1);
  }
}
