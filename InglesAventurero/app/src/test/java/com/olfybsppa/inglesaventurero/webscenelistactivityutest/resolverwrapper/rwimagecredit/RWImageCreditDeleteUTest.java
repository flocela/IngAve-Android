package com.olfybsppa.inglesaventurero.webscenelistactivityutest.resolverwrapper.rwimagecredit;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.BaseColumns;

import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.utils.Ez;
import com.olfybsppa.inglesaventurero.utils.UriDeterminer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Uri.class, UriDeterminer.class})
public class RWImageCreditDeleteUTest {
  @Mock Uri mockImageCreditsUri;
  @Mock ContentResolver mockCR;
  @Mock ContentValues mockContentValues;
  @Mock ContentValues badContentValues;//used to fail tests to make sure test is running at all.
  @Mock Uri badUri;

  @Test
  public void callsDeleteImageCreditOnContentResolver () throws Exception {
    int imageCreditRowId = 10;
    mockStatic(Uri.class);
    when(Uri.parse(LinesCP.CP_URI + LinesCP.img_credits_table)).thenReturn(mockImageCreditsUri);

    ResolverWrapper rW = new ResolverWrapper(mockCR);
    rW.deleteImageCredit(imageCreditRowId);
    verify(mockCR).delete(mockImageCreditsUri, Ez.where(BaseColumns._ID, ""+imageCreditRowId), null);
  }
}
