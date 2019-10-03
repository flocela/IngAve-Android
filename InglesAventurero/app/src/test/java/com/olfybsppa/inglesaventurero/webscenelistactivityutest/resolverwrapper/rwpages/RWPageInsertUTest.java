package com.olfybsppa.inglesaventurero.webscenelistactivityutest.resolverwrapper.rwpages;

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
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Uri.class, UriDeterminer.class})
public class RWPageInsertUTest {

  @Mock Uri mockPagesUri;
  @Mock ContentResolver mockCR;
  @Mock ContentValues mockCV;
  @Mock ContentValues badContentValues;//used to fail tests
  @Mock Uri badUri;

  @Before
  public  void setUp() throws Exception {
    mockStatic(Uri.class);
    when(Uri.parse(LinesCP.CP_URI + LinesCP.page_table)).thenReturn(mockPagesUri);
    mockStatic(UriDeterminer.class);
  }

  @Test
  public void callsInsertsPageOnContentResolver () throws Exception {
    when(mockCR.insert(LinesCP.pageTableUri, mockCV)).thenReturn(mockPagesUri);
    when(UriDeterminer.getLastId(mockPagesUri)).thenReturn(77);
    ResolverWrapper rW = new ResolverWrapper(mockCR);

    int rowId = rW.insertPage(mockCV);
    assertEquals(77, rowId);
  }


}
