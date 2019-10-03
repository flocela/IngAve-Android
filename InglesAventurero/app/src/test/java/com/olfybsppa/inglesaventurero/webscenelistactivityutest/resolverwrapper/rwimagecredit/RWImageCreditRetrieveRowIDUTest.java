package com.olfybsppa.inglesaventurero.webscenelistactivityutest.resolverwrapper.rwimagecredit;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.ImageCredit;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.utils.Ez;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Uri.class, ImageCredit.class})
public class RWImageCreditRetrieveRowIDUTest {

  @Mock Uri mAttributionsURI;
  @Mock ContentResolver mCR;
  @Mock Cursor mCursor;
  @Mock Uri badUri;//used to fail tests to make sure test is running at all.
  @Mock ImageCredit badImageCredit;
  int imageCreditRowID = 10;
  int attributionRowID = 20;

  @Before
  public void setUp() {
    mockStatic(Uri.class);
    when(Uri.parse(LinesCP.CP_URI + LinesCP.attributions_table))
      .thenReturn(mAttributionsURI);
  }

  /**
   * testing rw.retrieveImageCreditRowID when content provider has attribution with
   * attributionRowID.
   */
  @Test
  public void retrievesCursorAndReturnsCursorImageCreditID () throws Exception {
    when(mCR.query(mAttributionsURI,
                   new String[] {LinesCP.img_credit_id},
                   Ez.where(BaseColumns._ID, ""+attributionRowID),
                   null,
                   null)).thenReturn(mCursor);
    when(mCursor.moveToFirst()).thenReturn(true);
    when(mCursor.getColumnIndex(LinesCP.img_credit_id)).thenReturn(15);
    when(mCursor.getInt(15)).thenReturn(imageCreditRowID);

    ResolverWrapper rW = new ResolverWrapper(mCR);
    int returnedICID = rW.retrieveImageCreditRowId(attributionRowID);
    assertEquals(imageCreditRowID, returnedICID);
  }

  /**
   * testing rw.retrieveImageCreditRowID when content provider does not have attribution with
   * attributionRowID.
   */
  @Test
  public void returnsNegativeOneWhenRetrievedCursorIsEmpty () throws Exception {
    when(mCR.query(mAttributionsURI,
      new String[] {LinesCP.img_credit_id},
      Ez.where(BaseColumns._ID, ""+attributionRowID),
      null,
      null)).thenReturn(mCursor);
    when(mCursor.moveToFirst()).thenReturn(false);

    ResolverWrapper rW = new ResolverWrapper(mCR);
    int returnedICID = rW.retrieveImageCreditRowId(attributionRowID);
    assertEquals(-1, returnedICID);
  }
}
