package com.olfybsppa.inglesaventurero.webscenelistactivityutest.resolverwrapper.rwimagecredit;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.ImageCredit;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.utils.Ez;
import com.olfybsppa.inglesaventurero.utils.Pair;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Uri.class, ImageCredit.class})
public class RWImageCreditRetrieveExistsUTest {

  @Mock Uri mockImageCreditsUri;
  @Mock ContentResolver mockCR;
  @Mock Cursor mockCursor;
  @Mock ImageCredit mockImageCredit;
  String imageInfoName = "imageInfoName";
  @Mock Uri badUri;//used to fail tests to make sure test is running at all.
  @Mock ImageCredit badImageCredit;

  @Before
  public void setUp() {
    mockStatic(Uri.class);
    when(Uri.parse(LinesCP.CP_URI + LinesCP.img_credits_table))
      .thenReturn(mockImageCreditsUri);
    mockStatic(ImageCredit.class);
  }

  @Test
  public void returnsImageCreditPairGivenImageInfoName () throws Exception {
    when(mockCR.query(mockImageCreditsUri,
                      null,
                      Ez.where(LinesCP.image_info_name, imageInfoName),
                      null,
                      null)).thenReturn(mockCursor);

    when(ImageCredit.extractImageCredit(mockCursor))
      .thenReturn(new Pair<>(12, mockImageCredit));
    when(mockCursor.moveToFirst()).thenReturn(true);

    ResolverWrapper rW = new ResolverWrapper(mockCR);
    Pair<Integer, ImageCredit> imageCreditPair = rW.retrieveImageCreditWithID(imageInfoName);
    assertEquals(imageCreditPair.first, new Integer(12));
    assertEquals(imageCreditPair.second, mockImageCredit);
  }
}
