package com.olfybsppa.inglesaventurero.webscenelistactivityutest.resolverwrapper.rwpages;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.utils.Ez;
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
public class RWPageNumPagesForBackgroundUTest {

  @Mock Uri mPagesUri;
  @Mock ContentResolver mCR;
  @Mock ContentValues mCV;
  @Mock Cursor mCursor;
  @Mock ContentValues badContentValues;//used to fail tests
  @Mock Uri badUri;
  int backgroundID = 10;

  @Before
  public  void setUp() throws Exception {
    mockStatic(Uri.class);
    when(Uri.parse(LinesCP.CP_URI + LinesCP.page_table)).thenReturn(mPagesUri);
  }

  /**
   * returns the number of pages that contain the backgroundID.
   */
  @Test
  public void retrievesNumOfPagesThatContainBkgdID () throws Exception {
    when(mCR.query(LinesCP.pageTableUri,
                   new String[] {BaseColumns._ID},
                   Ez.where(LinesCP.background_id, "" + backgroundID),
                   null,
                   null)).thenReturn(mCursor);
    when(mCursor.getCount()).thenReturn(22);

    ResolverWrapper rW = new ResolverWrapper(mCR);
    int numOfPages = rW.retrieveNumOfPagesUsingBackground(backgroundID);
    assertEquals(22, numOfPages);
  }

}
