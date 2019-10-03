package com.olfybsppa.inglesaventurero.webscenelistactivityutest.imagecreditresolver;

import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.ImageCreditResolver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ImageCreditResolver.class)
public class ClearImageCreditUTest {

  int imagecreditID = 10;
  @Mock ResolverWrapper mRW;

  @Before
  public  void setUp() throws Exception {
  }

  /**
   * test ImageCreditResolver's clearImageCredit(imagecreditRowID)
   * test that if there exists an attribution that references
   * imagecreditRowID, then don't delete the imagecredit.
   */
  @Test
  public void attributionExistsThatReferencingImageCreditThenDontDeleteIC () throws Exception {
    when(mRW.retrieveNumOfAttributionsUsingImageCredit(imagecreditID)).thenReturn(1);

    ImageCreditResolver imcrResolver = new ImageCreditResolver(mRW);
    imcrResolver.clearImageCredit(imagecreditID);

    verify(mRW,times(0)).deleteImageCredit(imagecreditID);
  }


  /**
   * test ImageCreditResolver's clearImageCredit(imagecreditRowID)
   * test that if no attributions exist that reference the imagecredit,
   * then delete the imagecredit.
   */
  @Test
  public void noAttrExistThatReferenceImageCreditThenDeleteIC () throws Exception {
    when(mRW.retrieveNumOfAttributionsUsingImageCredit(imagecreditID)).thenReturn(0);

    ImageCreditResolver imcrResolver = new ImageCreditResolver(mRW);
    imcrResolver.clearImageCredit(imagecreditID);

    verify(mRW,times(1)).deleteImageCredit(imagecreditID);
  }

}
