package com.olfybsppa.inglesaventurero.webscenelistactivityutest.collectors_basics;

import android.database.Cursor;
import android.provider.BaseColumns;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.ImageCredit;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.utils.Pair;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static junit.framework.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ImageCreditUTest {
  
  ImageCredit ic1a, ic1b, ic2;
  @Mock Cursor mockCursor;
  
  @Before
  public  void setUp() throws Exception {
    ic1a = new ImageCredit("1");
    ic1a.setArtist("1");
    ic1a.setLinkToLicense("1");
    ic1a.setNameOfLicense("1");
    ic1a.setArtistImageName("1");
    ic1a.setImageUrl("1");
    ic1a.setImageUrlName("1");
    ic1a.setArtistFilename("1");
    ic1b = new ImageCredit("1");
    ic1b.setArtist("1");
    ic1b.setLinkToLicense("1");
    ic1b.setNameOfLicense("1");
    ic1b.setArtistImageName("1");
    ic1b.setImageUrl("1");
    ic1b.setImageUrlName("1");
    ic1b.setArtistFilename("1");
    ic2 = new ImageCredit("2");
    ic2.setImageInfoName("2");
    ic2.setArtist("2");
    ic2.setLinkToLicense("2");
    ic2.setNameOfLicense("2");
    ic2.setArtistImageName("2");
    ic2.setImageUrl("2");
    ic2.setImageUrlName("2");
    ic2.setArtistFilename("2");
  }
  
  @Test
  public void testEquals() {
    assertTrue(ic1a.equals(ic1b));
    assertFalse(ic1a.equals(ic2));
  }

  @Test
  public void testEqualsWithDifferentImageInfoName () {
    ic1b.setImageInfoName("3");
    assertFalse(ic1a.equals(ic1b));
  }

  @Test
  public void testEqualsWithDifferentArtist () {
    ic1b.setArtist("3");
    assertFalse(ic1a.equals(ic1b));
  }

  @Test
  public void testEqualsWithDifferentLinkToLicense () {
     ic1b.setLinkToLicense("3");
    assertFalse(ic1a.equals(ic1b));
  }

  @Test
  public void testEqualsWithDifferentNameOfLicense () {
     ic1b.setNameOfLicense("3");
    assertFalse(ic1a.equals(ic1b));
  }

  @Test
  public void testEqualsWithDifferentArtistImageName () {
     ic1b.setArtistImageName("3");
    assertFalse(ic1a.equals(ic1b));
  }


  @Test
  public void testEqualsWithDifferentImageUrl () {
     ic1b.setImageUrl("3");
    assertFalse(ic1a.equals(ic1b));
  }

  @Test
  public void testEqualsWithDifferentImageUrlName () {
     ic1b.setImageUrlName("3");
    assertFalse(ic1a.equals(ic1b));
  }

  @Test
  public void testEqualsWithDifferentFileName () {
     ic1b.setArtistFilename("3");
    assertFalse(ic1a.equals(ic1b));
  }

  @Test
  public void testReflexiveEquals () {
    assertTrue(ic1a.equals(ic1a));
  }

  @Test
  public void testSymmetricEquals () {
    assertTrue(ic1a.equals(ic1b));
    assertTrue(ic1b.equals(ic1a));
  }

  @Test
  public void testTransitiveEquals () {
    ImageCredit ic1c = ic1a.clone();
    assertTrue(ic1a.equals(ic1b));
    assertTrue(ic1b.equals(ic1c));
    assertTrue(ic1a.equals(ic1c));
  }

  @Test
  public void testNullEquals () {
    assertFalse(ic1a.equals(null));
  }

  @Test
  public void testClone () {
    ImageCredit ic1c = ic1a.clone();
    assertTrue(ic1a.equals(ic1c));
  }

  @Test
  public void testExtractImageCredit () {
    ImageCredit ic = new ImageCredit("image_info_name");
    ic.setArtist("artist");
    ic.setLinkToLicense("link_to_license");
    ic.setNameOfLicense("name_of_license");
    ic.setArtistImageName("artist_image_name");
    ic.setImageUrl("image_url");
    ic.setImageUrlName("image_url_name");
    ic.setArtistFilename("artist_filename");
    when(mockCursor.getColumnIndex(LinesCP.image_info_name)).thenReturn(1);
    when(mockCursor.getString(1)).thenReturn("image_info_name");
    when(mockCursor.getColumnIndex(LinesCP.link_to_license)).thenReturn(2);
    when(mockCursor.getString(2)).thenReturn("link_to_license");
    when(mockCursor.getColumnIndex(LinesCP.name_of_license)).thenReturn(3);
    when(mockCursor.getString(3)).thenReturn("name_of_license");
    when(mockCursor.getColumnIndex(LinesCP.artist)).thenReturn(4);
    when(mockCursor.getString(4)).thenReturn("artist");
    when(mockCursor.getColumnIndex(LinesCP.artist_image_name)).thenReturn(5);
    when(mockCursor.getString(5)).thenReturn("artist_image_name");
    when(mockCursor.getColumnIndex(LinesCP.image_url)).thenReturn(6);
    when(mockCursor.getString(6)).thenReturn("image_url");
    when(mockCursor.getColumnIndex(LinesCP.image_url_name)).thenReturn(7);
    when(mockCursor.getString(7)).thenReturn("image_url_name");
    when(mockCursor.getColumnIndex(LinesCP.artist_filename)).thenReturn(8);
    when(mockCursor.getString(8)).thenReturn("artist_filename");
    when(mockCursor.getColumnIndex(BaseColumns._ID)).thenReturn(22);
    when(mockCursor.getInt(22)).thenReturn(23);

    Pair<Integer, ImageCredit> pair = ImageCredit.extractImageCredit(mockCursor);
    assertEquals(new Integer(23), pair.first);
    assertTrue(ic.equals(pair.second));
  }

  @Test
  public void testHashCode() {
    assertEquals(ic1a.hashCode(), ic1b.hashCode());
    assertFalse(ic1a.hashCode() == ic2.hashCode());
  }

  @Test
  public void testHashCodeWithDifferentImageInfoName () {
    ic1b.setImageInfoName("3");
    assertFalse(ic1a.hashCode() == ic1b.hashCode());
  }

  @Test
  public void testHashCodeWithDifferentArtist () {
    ic1b.setArtist("3");
    assertFalse(ic1a.hashCode() == ic1b.hashCode());
  }

  @Test
  public void testHashCodeWithDifferentLinkToLicense () {
    ic1b.setLinkToLicense("3");
    assertFalse(ic1a.hashCode() == ic1b.hashCode());
  }

  @Test
  public void testHashCodeWithDifferentNameOfLicense () {
    ic1b.setNameOfLicense("3");
    assertFalse(ic1a.hashCode() == ic1b.hashCode());
  }

  @Test
  public void testHashCodeWithDifferentArtistImageName () {
    ic1b.setArtistImageName("3");
    assertFalse(ic1a.hashCode() == ic1b.hashCode());
  }


  @Test
  public void testHashCodeWithDifferentImageUrl () {
    ic1b.setImageUrl("3");
    assertFalse(ic1a.hashCode() == ic1b.hashCode());
  }

  @Test
  public void testHashCodeWithDifferentImageUrlName () {
    ic1b.setImageUrlName("3");
    assertFalse(ic1a.hashCode() == ic1b.hashCode());
  }

  @Test
  public void testHashCodeWithDifferentFileName () {
    ic1b.setArtistFilename("3");
    assertFalse(ic1a.hashCode() == ic1b.hashCode());
  }
}
