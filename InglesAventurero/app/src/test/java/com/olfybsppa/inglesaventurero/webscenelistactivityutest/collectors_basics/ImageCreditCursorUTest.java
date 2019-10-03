package com.olfybsppa.inglesaventurero.webscenelistactivityutest.collectors_basics;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import com.olfybsppa.inglesaventurero.utils.Pair;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.ImageCredit;
import com.olfybsppa.inglesaventurero.start.LinesCP;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Uri.class})
public class ImageCreditCursorUTest {
  @Mock Cursor mockCursor;
  @Test
  public void getsRowIdAndImageCreditFromCursor () throws Exception {
    when(mockCursor.getColumnIndex(LinesCP.image_info_name)).thenReturn(0);
    when(mockCursor.getColumnIndex(LinesCP.link_to_license)).thenReturn(1);
    when(mockCursor.getColumnIndex(LinesCP.name_of_license)).thenReturn(2);
    when(mockCursor.getColumnIndex(LinesCP.artist)).thenReturn(3);
    when(mockCursor.getColumnIndex(LinesCP.artist_image_name)).thenReturn(4);
    when(mockCursor.getColumnIndex(LinesCP.image_url)).thenReturn(5);
    when(mockCursor.getColumnIndex(LinesCP.image_url_name)).thenReturn(6);
    when(mockCursor.getColumnIndex(LinesCP.artist_filename)).thenReturn(7);
    when(mockCursor.getColumnIndex(BaseColumns._ID)).thenReturn(8);
    
    when(mockCursor.getString(0)).thenReturn("a_image_info_name");
    when(mockCursor.getString(1)).thenReturn("a_link_to_license");
    when(mockCursor.getString(2)).thenReturn("a_name_of_license");
    when(mockCursor.getString(3)).thenReturn("a_artist");
    when(mockCursor.getString(4)).thenReturn("a_artist_image_name");
    when(mockCursor.getString(5)).thenReturn("a_image_url");
    when(mockCursor.getString(6)).thenReturn("a_image_url_name");
    when(mockCursor.getString(7)).thenReturn("a_artist_filename");
    when(mockCursor.getInt(8)).thenReturn(12);

    ImageCredit compareCredit = new ImageCredit("a_image_info_name");
    compareCredit.setLinkToLicense("a_link_to_license")
                 .setNameOfLicense("a_name_of_license")
                 .setArtist("a_artist")
                 .setArtistImageName("a_artist_image_name")
                 .setImageUrl("a_image_url")
                 .setImageUrlName("a_image_url_name")
                 .setArtistFilename("a_artist_filename");

    Pair<Integer, ImageCredit> result = ImageCredit.extractImageCredit(mockCursor);
    assertTrue(result.second.equals(compareCredit));
    assertEquals(new Integer(12), result.first);
  }


}
