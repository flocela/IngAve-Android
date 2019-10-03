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

import static junit.framework.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PageResolver.class)
public class InsertPageUTest {

  @Mock ResolverWrapper mockRW;
  @Mock Background mockBackground;
  @Mock HashSet<Attribution> mockAttributions;
  @Mock BackgroundResolver mBkgdResolver;
  @Mock CPPage mockPage;
  @Mock ContentValues mockCV;

  Integer sceneId = 2;
  Integer backgroundId = 3;
  Integer pageRowIdNum = 5;

  @Before
  public  void setUp() throws Exception {
    whenNew(BackgroundResolver.class)
      .withArguments(mockRW).thenReturn(mBkgdResolver);
    when(mBkgdResolver.insertBkgdWithAttributions(mockBackground, mockAttributions))
      .thenReturn(backgroundId);
    when(mockPage.getContentValues(sceneId, backgroundId))
      .thenReturn(mockCV);
    when(mockRW.insertPage(mockCV)).thenReturn(pageRowIdNum);
  }

  @Test
  public void testInsertPage() throws Exception{
    PageResolver pageResolver = new PageResolver(mockRW);
    Integer rowId = pageResolver.insertPage(mockPage,
                                        sceneId,
                                        mockBackground,
                                        mockAttributions);
    assertEquals(new Integer(pageRowIdNum), rowId);
  }


}
