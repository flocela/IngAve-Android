package com.olfybsppa.inglesaventurero.webscenelistactivityutest.resolverwrapper.rwimagecredit;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.utils.UriDeterminer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
@RunWith(PowerMockRunner.class)
@PrepareForTest({Uri.class, UriDeterminer.class})
public class RWImageCreditInsertUTest {

  @Mock Uri mockImageCreditsUri;
  @Mock ContentResolver mockCR;
  @Mock ContentValues mockContentValues;
  @Mock ContentValues badContentValues;//used to fail tests
  @Mock Uri badUri;

  @Before
  public  void setUp() throws Exception {
    mockStatic(Uri.class);
    when(Uri.parse(LinesCP.CP_URI + LinesCP.img_credits_table))
      .thenReturn(mockImageCreditsUri);
    mockStatic(UriDeterminer.class);
  }

  @Test
  public void callsInsertImageCreditOnContentResolver () throws Exception {
    when(mockCR.insert(LinesCP.imgCreditUri, mockContentValues))
      .thenReturn(mockImageCreditsUri);
    when(UriDeterminer.getLastId(mockImageCreditsUri)).thenReturn(12);

    ResolverWrapper rW = new ResolverWrapper(mockCR);
    int rowId = rW.insertImageCredit(mockContentValues);

    verify(mockCR).insert(mockImageCreditsUri, mockContentValues);
    assertEquals(12, rowId);
  }
}
