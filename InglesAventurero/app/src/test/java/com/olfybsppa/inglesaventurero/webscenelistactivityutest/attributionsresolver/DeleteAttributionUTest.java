package com.olfybsppa.inglesaventurero.webscenelistactivityutest.attributionsresolver;

import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.AttributionsResolver;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.ImageCreditResolver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AttributionsResolver.class)
public class DeleteAttributionUTest {

  int attrID = 10;
  int imageCreditID = 100;
  @Mock ResolverWrapper mRW;
  @Mock ImageCreditResolver mImagecreditResolver;


  @Before
  public  void setUp() throws Exception {
    whenNew(ImageCreditResolver.class).withAnyArguments()
      .thenReturn(mImagecreditResolver);
    when(mRW.retrieveImageCreditRowId(attrID)).thenReturn(imageCreditID);
  }

  /**
   * testing AttributionResolver's deleteAttribution(int attrRowID)
   * this is a basic test,
   * test that ResolverWrapper's deleteAttribution(attRowID) is called
   */
  @Test
  public void pageExistsReferencingBackground () throws Exception {
    AttributionsResolver attrResolver = new AttributionsResolver(mRW);
    attrResolver.deleteAttribution(attrID);

    verify(mRW).deleteAttribution(attrID);
  }

  /**
   * testing AttributionResolver's deleteAttribution(int attrRowID)
   * test that ImageCreditResolver's clearImageCredit is called on
   * imagecredit referenced by attribution.
   */
  @Test
  public void imagecreditCleared () throws Exception {
    AttributionsResolver attrResolver = new AttributionsResolver(mRW);
    attrResolver.deleteAttribution(attrID);

    verify(mImagecreditResolver).clearImageCredit(imageCreditID);
  }

}
