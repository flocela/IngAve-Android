package com.olfybsppa.inglesaventurero.webscenelistactivityutest.resolverwrapper.rwattributions;

import android.content.ContentResolver;
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
public class RWAttributionDeleteUTest {
  @Mock Uri mockAttributionTableUri;
  @Mock ContentResolver mockCR;

  @Test
  public void callsDeleteOnContentResolver () throws Exception {
    int attributionRowId = 12;

    mockStatic(Uri.class);
    when(Uri.parse(LinesCP.CP_URI + LinesCP.attributions_table)).thenReturn(mockAttributionTableUri);

    ResolverWrapper rW = new ResolverWrapper(mockCR);
    rW.deleteAttribution(attributionRowId);
    verify(mockCR).delete(LinesCP.attributionTableUri, Ez.where(BaseColumns._ID, "" + attributionRowId), null);
  }
}
