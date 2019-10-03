package com.olfybsppa.inglesaventurero.webscenelistactivityutest.pageresolver;

import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
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
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PageResolver.class)
public class DeletePagesUTest {

  int sceneID = 1;
  HashSet<Integer> pageRowIDs = new HashSet<>();
  HashSet<Integer> hintsFromPage10 = new HashSet<>();
  HashSet<Integer> hintsFromPage20 = new HashSet<>();
  HashSet<Integer> repliesFromPage10 = new HashSet<>();
  HashSet<Integer> repliesFromPage20 = new HashSet<>();
  HashSet<Integer> backgrounds       = new HashSet<>();
  int backgroundForPage10 = 10000;
  int backgroundForPage20 = 20000;
  @Mock ResolverWrapper mRW;
  @Mock BackgroundResolver mBkgdResolver;

  @Before
  public  void setUp() throws Exception {
    hintsFromPage10.add(100);
    hintsFromPage10.add(101);
    hintsFromPage20.add(200);
    hintsFromPage20.add(201);

    repliesFromPage10.add(1000);
    repliesFromPage10.add(1001);
    repliesFromPage20.add(2000);
    repliesFromPage20.add(2001);

    backgrounds.add(backgroundForPage10);
    backgrounds.add(backgroundForPage20);

    pageRowIDs.add(10);
    pageRowIDs.add(20);
    when(mRW.retrievePageIDsForScene(sceneID)).thenReturn(pageRowIDs);
  }

  /**
   * test PageResolver.deletePagesFromScene()
   * simple test.
   * verify that all pages having the sceneID are deleted by mRW.
   */
  @Test
  public void deletesPages () throws Exception {

    PageResolver pageResolver = new PageResolver(mRW);
    pageResolver.deletePagesFromScene(sceneID);

    verify(mRW).deletePages(pageRowIDs);
  }
}
