package com.olfybsppa.inglesaventurero.webscenelistactivityutest.resolverwrapper.rwattributions;

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
public class RWAttrNumAttrForImageCreditUTest {
  @Mock Uri mAttrUri;
  @Mock ContentResolver mCR;
  @Mock ContentValues mCV;
  @Mock Cursor mCursor;
  int imagecreditID = 10;

  @Before
  public  void setUp() throws Exception {
    mockStatic(Uri.class);
    when(Uri.parse(LinesCP.CP_URI + LinesCP.attributions_table))
      .thenReturn(mAttrUri);
  }

  /**
   * returns the number of pages that contain the imagecreditID.
   */
  @Test
  public void retrievesNumOfAttrsThatContainImageCreditID () throws Exception {
    when(mCR.query(LinesCP.attributionTableUri,
                   new String[] {BaseColumns._ID},
                   Ez.where(LinesCP.img_credit_id, "" + imagecreditID),
                   null,
                   null)).thenReturn(mCursor);
    when(mCursor.getCount()).thenReturn(22);

    ResolverWrapper rW = new ResolverWrapper(mCR);
    int numOfPages = rW.retrieveNumOfAttributionsUsingImageCredit(imagecreditID);
    assertEquals(22, numOfPages);
  }

}
