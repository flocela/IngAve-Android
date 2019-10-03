package com.olfybsppa.inglesaventurero.webscenelistactivityutest.attributionsresolver;


import android.net.Uri;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.ImageCredit;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.utils.Pair;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.AttributionsResolver;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.ImageCreditResolver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashSet;
import java.util.LinkedList;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
@RunWith(PowerMockRunner.class)
@PrepareForTest({Uri.class, AttributionsResolver.class})
public class InsertAttribution2UTest {
  @Mock Uri mImCrURI;
  @Mock  ResolverWrapper mRW;
  @Mock ImageCreditResolver mImCrResolver;
  Integer bkgdID = 10;
  HashSet<Attribution> newAttrs = new HashSet<>();


  @Before
  public void setUp () throws Exception {
    whenNew(ImageCreditResolver.class).withArguments(mRW).thenReturn(mImCrResolver);
    when(mRW.retrieveImageCreditWithID(anyString())).thenReturn(null);
  }

  /**
   * Testing setAttributions(Integer bkgdID, HashSet<Attribution> newAttributionsHash)
   * setting the attributions with newAttrs argument means that previous attributions
   * that are not included in newAttrs are deleted.
   */
  @Test
  public void testOldAttributionsAreDeleted () {
    int bkgdID = 10;
    Attribution attr1 = mock(Attribution.class);
    Attribution attr2 = mock(Attribution.class);
    Attribution attr3 = mock(Attribution.class);

    ImageCredit imcr1 = mock(ImageCredit.class);
    ImageCredit imcr2 = mock(ImageCredit.class);
    ImageCredit imcr3 = mock(ImageCredit.class);
    when(attr1.constructImageCredit()).thenReturn(imcr1);
    when(attr2.constructImageCredit()).thenReturn(imcr2);
    when(attr3.constructImageCredit()).thenReturn(imcr3);
    
    LinkedList<Pair<Integer, Attribution>> oldAttributions = new LinkedList<>();
    oldAttributions.add(new Pair<Integer, Attribution>(1, attr1));
    oldAttributions.add(new Pair<Integer, Attribution>(2, attr2));
    oldAttributions.add(new Pair<Integer, Attribution>(3, attr3));

    newAttrs = new HashSet<>();
    newAttrs.add(attr1);

    when(mRW.retrieveAttributions(bkgdID)).thenReturn(oldAttributions);

    AttributionsResolver attrResolver = new AttributionsResolver(mRW);
    AttributionsResolver spy = spy(attrResolver);
    spy.setAttributions(bkgdID, newAttrs);

    verify(spy).deleteAttribution(2);
    verify(spy).deleteAttribution(3);
  }

}
