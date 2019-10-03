package com.olfybsppa.inglesaventurero.tests.collectorstests;

import android.content.ContentValues;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.ImageCredit;
import com.olfybsppa.inglesaventurero.start.LinesCP;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ImageCreditTest {

  ImageCredit ic;
  ContentValues cV;

  @Before
  public void setUp() {
    ic = new ImageCredit("image_info_name");
    ic.setArtist("artist");
    ic.setLinkToLicense("link_to_license");
    ic.setNameOfLicense("name_of_license");
    ic.setArtistImageName("artist_image_name");
    ic.setImageUrl("image_url");
    ic.setImageUrlName("image_url_name");
    ic.setArtistFilename("artist_filename");

    cV = ic.getContentValues();
  }

  @Test
  public void testGetContentValuesImageInfoName () {
    assertEquals("image_info_name", cV.getAsString(LinesCP.image_info_name));
  }

  @Test
  public void testGetContentValuesArtist () {
    assertEquals("artist", cV.getAsString(LinesCP.artist));
  }

  @Test
  public void testGetContentValuesLinkToLicense () {
    assertEquals("link_to_license", cV.getAsString(LinesCP.link_to_license));
  }

  @Test
  public void testGetContentValuesNameOfLicense () {
    assertEquals("name_of_license", cV.getAsString(LinesCP.name_of_license));
  }

  @Test
  public void testGetContentValuesArtistImageName () {
    assertEquals("artist_image_name", cV.getAsString(LinesCP.artist_image_name));
  }

  @Test
  public void testGetContentValuesImageURLName () {
    assertEquals("image_url_name", cV.getAsString(LinesCP.image_url_name));
  }
  @Test
  public void testGetContentValuesImageURL () {
    assertEquals("image_url", cV.getAsString(LinesCP.image_url));
  }
  @Test
  public void testGetContentValuesArtistFilename () {
    assertEquals("artist_filename", cV.getAsString(LinesCP.artist_filename));
  }

}