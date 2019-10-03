package com.olfybsppa.inglesaventurero.webscenelistactivityutest.resolverwrapper.rwbackgrounds;

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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Uri.class, UriDeterminer.class})
public class RWBackgroundInsertUTest {
  @Mock Uri mockBackgroundUri;
  @Mock ContentResolver mockCR;
  @Mock ContentValues mockContentValues;
  @Mock ContentValues badContentValues;//used to fail tests to make sure test is running at all.
  @Mock Uri badUri;

  @Before
  public  void setUp() throws Exception {
    mockStatic(Uri.class);
    when(Uri.parse(LinesCP.CP_URI + LinesCP.backgrounds_table)).thenReturn(mockBackgroundUri);
    mockStatic(UriDeterminer.class);
  }

  @Test
  public void callsInsertBackgroundOnContentResolver () throws Exception {
    when(mockCR.insert(LinesCP.backgroundTableUri, mockContentValues)).thenReturn(mockBackgroundUri);
    when(UriDeterminer.getLastId(mockBackgroundUri)).thenReturn(12);
    ResolverWrapper rW = new ResolverWrapper(mockCR);

    Integer rowId = rW.insertBackground(mockContentValues);
    verify(mockCR).insert(mockBackgroundUri, mockContentValues);
    assertEquals(new Integer(12), rowId);
  }
}
