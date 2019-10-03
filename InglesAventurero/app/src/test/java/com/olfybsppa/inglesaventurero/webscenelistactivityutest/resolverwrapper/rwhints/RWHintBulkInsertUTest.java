package com.olfybsppa.inglesaventurero.webscenelistactivityutest.resolverwrapper.rwhints;

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

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Uri.class, UriDeterminer.class})
public class RWHintBulkInsertUTest {

  @Mock Uri             mockHintsUri;
  @Mock ContentResolver mockCR;
  @Mock ContentValues   mockContentValues;
  @Mock Uri             badUri;

  @Before
  public  void setUp() throws Exception {
    mockStatic(Uri.class);
    when(Uri.parse(LinesCP.CP_URI + LinesCP.hint_table)).thenReturn(mockHintsUri);
  }

  @Test
  public void callsBulkInsertHintOnContentResolver () throws Exception {
    ContentValues[] arrayCV = new ContentValues[5];

    ResolverWrapper rW = new ResolverWrapper(mockCR);
    rW.bulkInsertHints(arrayCV);

    verify(mockCR).bulkInsert(mockHintsUri, arrayCV);
  }

}
