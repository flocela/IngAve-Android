package com.olfybsppa.inglesaventurero.webscenelistactivityutest.resolverwrapper.rwfiles;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.ImageCredit;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.utils.UriDeterminer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Uri.class, UriDeterminer.class})
public class RWFileUTest {
  @Mock Uri mockImageCreditsUri;

  @Mock ContentResolver mockCR;
  @Mock ResolverWrapper mockRW;
  @Mock ContentValues mockAttributeCV;
  @Mock ContentValues mockImageCV;
  @Mock Attribution mockAttribution;
  @Mock ImageCredit mockImageCredit;

  @Mock ContentValues mockCV;
  @Mock Uri mockFilesUri;

  @Mock ContentValues badContentValues;//used to fail tests to make sure test is running at all.
  @Mock Uri badUri;

  @Test
  public void insertsFileWithFilename () throws Exception {//TODO
    /*String filename = "filename.jpg";
    mockStatic(Uri.class);
    when(Uri.parse(LinesCP.CP_URI + LinesCP.bg_files_table)).thenReturn(mockFilesUri);
    mockStatic(UriDeterminer.class);
    when(UriDeterminer.getLastId(mockFilesUri)).thenReturn(12);

    whenNew(ContentValues.class).withNoArguments().thenReturn(mockCV);
    when(mockCR.insert(LinesCP.bgFileTableUri, mockCV)).thenReturn(mockFilesUri);
    ResolverWrapper rW = new ResolverWrapper(mockCR);
    Integer rowId = rW.insertFile(filename);
    Assert.assertEquals(new Integer(12), rowId);*/
  }


}
