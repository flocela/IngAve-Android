package com.olfybsppa.inglesaventurero.webscenelistactivityutest.resolverwrapper.rwimagecredit;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.ImageCredit;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
@RunWith(PowerMockRunner.class)
@PrepareForTest({Uri.class})
public class RWImageCreditUpdate1UTest {

  @Mock Uri mockImageCreditsUri;
  @Mock ContentResolver mockCR;
  @Mock ImageCredit mockImageCredit;
  @Mock ContentValues mockContentValues;
  @Mock ContentValues badContentValues;//used to fail tests to make sure test is running at all.
  @Mock Uri badUri;

  @Before
  public  void setUp() throws Exception {
    mockStatic(Uri.class);
    when(Uri.parse(LinesCP.CP_URI + LinesCP.img_credits_table)).thenReturn(mockImageCreditsUri);
    when(mockImageCredit.getContentValues()).thenReturn(mockContentValues);
  }

  @Test
  public void callsUpdateImageCreditOnContentResolver () {
    ResolverWrapper rW = new ResolverWrapper(mockCR);
    String whereStatement = "where statement";
    rW.updateImageCredit(mockImageCredit, whereStatement);

    verify(mockCR).update(mockImageCreditsUri, mockContentValues, whereStatement, null);
  }

}
