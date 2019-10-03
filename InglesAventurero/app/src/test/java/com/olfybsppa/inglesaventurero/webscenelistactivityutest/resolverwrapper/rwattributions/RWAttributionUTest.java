package com.olfybsppa.inglesaventurero.webscenelistactivityutest.resolverwrapper.rwattributions;

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

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Uri.class, UriDeterminer.class})
public class RWAttributionUTest {
  @Mock Uri mockAttributionTableUri;
  @Mock ContentResolver mockCR;
  @Mock ContentValues mockContentValues;
  @Mock ContentValues badContentValues;//used to fail tests to make sure test is running at all.
  @Mock Uri badUri;

  @Before
  public  void setUp() throws Exception {
    mockStatic(Uri.class);
    when(Uri.parse(LinesCP.CP_URI + LinesCP.attributions_table)).thenReturn(mockAttributionTableUri);
  }

  @Test
  public void callsInsertAttributionOnContentResolver () throws Exception {
    when(mockCR.insert(mockAttributionTableUri, mockContentValues)).thenReturn(mockAttributionTableUri);
    mockStatic(UriDeterminer.class);
    when(UriDeterminer.getLastId(mockAttributionTableUri)).thenReturn(12);

    ResolverWrapper rW = new ResolverWrapper(mockCR);
    Integer rowId = rW.insertAttributionSimple(mockContentValues);

    verify(mockCR).insert(mockAttributionTableUri, mockContentValues);
    assertEquals(new Integer(12), rowId);
  }
}
