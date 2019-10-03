package com.olfybsppa.inglesaventurero.stagehouseactivityutest.collectors_basics;

import android.os.Bundle;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.ImageCredit;
import com.olfybsppa.inglesaventurero.start.LinesCP;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class AttributionUTest {
  private Attribution attribution1A = new Attribution("image_info_name_1_a");
  private Attribution attribution1B = new Attribution("image_info_name_1_a");
  private Attribution attribution1C = new Attribution("image_info_name_1_c");

  @Before
  public  void setUp() throws Exception {
    attribution1A.setArtist("artist_1_a");
    attribution1A.setLinkToLicense("link_to_license_1_a");
    attribution1A.setNameOfLicense("name_of_license_1_a");
    attribution1A.setArtistImageName("artist_image_name_1_a");
    attribution1A.setImageUrlName("imge_url_name_1_a");
    attribution1A.setImageUrl("image_url_1_a");
    attribution1A.setFilename("artist_filename_1_a");
    attribution1A.setChangesMadeEnglish("changes_english_1_a");
    attribution1A.setChangesMadeSpanish("changes_spanish_1_a");

    attribution1B.setArtist("artist_1_a");
    attribution1B.setLinkToLicense("link_to_license_1_a");
    attribution1B.setNameOfLicense("name_of_license_1_a");
    attribution1B.setArtistImageName("artist_image_name_1_a");
    attribution1B.setImageUrlName("imge_url_name_1_a");
    attribution1B.setImageUrl("image_url_1_a");
    attribution1B.setFilename("artist_filename_1_a");
    attribution1B.setChangesMadeEnglish("changes_english_1_a");
    attribution1B.setChangesMadeSpanish("changes_spanish_1_a");

    attribution1C.setArtist("artist_1_a");// credit1C infoname is different!
    attribution1C.setLinkToLicense("link_to_license_1_a");
    attribution1C.setNameOfLicense("name_of_license_1_a");
    attribution1C.setArtistImageName("artist_image_name_1_a");
    attribution1C.setImageUrlName("imge_url_name_1_a");
    attribution1C.setImageUrl("image_url_1_a");
    attribution1C.setFilename("artist_filename_1_a");
    attribution1C.setChangesMadeEnglish("changes_english_1_a");
    attribution1C.setChangesMadeSpanish("changes_spanish_1_a");
  }

  @Test
  public void testEquals () {
    assertTrue(attribution1A.equals(attribution1B));
  }

  @Test
  public void tetGetImageInfoName () {
    assertTrue(attribution1A.getImageInfoName().equals("image_info_name_1_a"));
  }

  @Test
  public void testEqualsChangesMadeEnglish () {
    attribution1B.setChangesMadeEnglish("changes_english_b");
    assertFalse(attribution1A.equals(attribution1B));
  }

  @Test
  public void testEqualsChangesMadeSpanish () {
    attribution1B.setChangesMadeSpanish("changes_spanish_1_b");
    assertFalse(attribution1A.equals(attribution1B));
  }

  @Test
  public void testEqualsSetArtist () {
    attribution1B.setArtist("artist_1_b");
    assertFalse(attribution1A.equals(attribution1B));
  }

  @Test
  public void testEqualsSetLinkToLicense () {
    attribution1B.setLinkToLicense("link_to_license_1_b");
    assertFalse(attribution1A.equals(attribution1B));
  }

  @Test
  public void testEqualsSetNameOfLicense () {
    attribution1B.setNameOfLicense("name_of_license_1_b");
    assertFalse(attribution1A.equals(attribution1B));
  }

  @Test
  public void testEqualsSetArtistImageName () {
    attribution1B.setArtistImageName("artist_image_name_1_b");
    assertFalse(attribution1A.equals(attribution1B));
  }

  @Test
  public void testEqualsSetImageUrlName () {
    attribution1B.setImageUrlName("image_url_name_1_b");
    assertFalse(attribution1A.equals(attribution1B));
  }

  @Test
  public void testEqualsSetImageUrl () {
    attribution1B.setImageUrl("image_url_1_b");
    assertFalse(attribution1A.equals(attribution1B));
  }

  @Test
  public void testEqualsSetFilename () {
    attribution1B.setFilename("artist_filename_1_b");
    assertFalse(attribution1A.equals(attribution1B));
  }

  @Test
  public void testConstructImageCredit () {
    ImageCredit imageCredit = attribution1A.constructImageCredit();
    ImageCredit ic = new ImageCredit("image_info_name_1_a");
    ic.setArtist("artist_1_a");
    ic.setLinkToLicense("link_to_license_1_a");
    ic.setNameOfLicense("name_of_license_1_a");
    ic.setArtistImageName("artist_image_name_1_a");
    ic.setImageUrlName("imge_url_name_1_a");
    ic.setImageUrl("image_url_1_a");
    ic.setArtistFilename("artist_filename_1_a");
    assertTrue(ic.equals(imageCredit));
  }

  @Test
  public void testFromBundle() {
    Bundle mockBundle = mock(Bundle.class);
    when(mockBundle.getString(LinesCP.image_info_name)).thenReturn("image_info_name_1_a");
    when(mockBundle.getString(LinesCP.artist_image_name)).thenReturn("artist_image_name_1_a");
    when(mockBundle.getString(LinesCP.artist)).thenReturn("artist_1_a");
    when(mockBundle.getString(LinesCP.name_of_license)).thenReturn("name_of_license_1_a");
    when(mockBundle.getString(LinesCP.link_to_license)).thenReturn("link_to_license_1_a");
    when(mockBundle.getString(LinesCP.artist_filename)).thenReturn("artist_filename_1_a");
    when(mockBundle.getString(LinesCP.image_url_name)).thenReturn("imge_url_name_1_a");
    when(mockBundle.getString(LinesCP.image_url)).thenReturn("image_url_1_a");
    when(mockBundle.getString(LinesCP.changes_english)).thenReturn("changes_english_1_a");
    when(mockBundle.getString(LinesCP.changes_spanish)).thenReturn("changes_spanish_1_a");
    when(mockBundle.containsKey(anyString())).thenReturn(true);
    Attribution fromBundle = Attribution.fromBundle(mockBundle);
    assertTrue(fromBundle.equals(attribution1A));
  }

  @Test
  public void testClone() {
    Attribution clone = attribution1A.clone();
    assertTrue(clone.equals(attribution1A));
  }

  @Test
  public void testReflexiveEquals () {
    assertTrue(attribution1A.equals(attribution1A));
  }

  @Test
  public void testSymmetricEquals () {
    assertTrue(attribution1A.equals(attribution1B));
    assertTrue(attribution1B.equals(attribution1A));
  }

  @Test
  public void testTransitiveEquals () {
    Attribution clone = attribution1B.clone();
    assertTrue(attribution1A.equals(attribution1B));
    assertTrue(attribution1B.equals(clone));
    assertTrue(attribution1A.equals(clone));
  }

  @Test
  public void testNullEquals () {
    assertFalse(attribution1A.equals(null));
  }

  @Test
  public void testHashCode () {
    assertTrue(attribution1A.hashCode() == attribution1B.hashCode());
  }

  @Test
  public void testHashCodeChangesMadeEnglish () {
    attribution1B.setChangesMadeEnglish("changes_english_b");
    assertFalse(attribution1A.hashCode() == attribution1B.hashCode());
  }

  @Test
  public void testHashCodeChangesMadeSpanish () {
    attribution1B.setChangesMadeSpanish("changes_spanish_1_b");
    assertFalse(attribution1A.hashCode() == attribution1B.hashCode());
  }

  @Test
  public void testHashCodeSetArtist () {
    attribution1B.setArtist("artist_1_b");
    assertFalse(attribution1A.hashCode() == attribution1B.hashCode());
  }

  @Test
  public void testHashCodeSetLinkToLicense () {
    attribution1B.setLinkToLicense("link_to_license_1_b");
    assertFalse(attribution1A.hashCode() == attribution1B.hashCode());
  }

  @Test
  public void testHashCodeSetNameOfLicense () {
    attribution1B.setNameOfLicense("name_of_license_1_b");
    assertFalse(attribution1A.hashCode() == attribution1B.hashCode());
  }

  @Test
  public void testHashCodeSetArtistImageName () {
    attribution1B.setArtistImageName("artist_image_name_1_b");
    assertFalse(attribution1A.hashCode() == attribution1B.hashCode());
  }

  @Test
  public void testHashCodeSetImageUrlName () {
    attribution1B.setImageUrlName("image_url_name_1_b");
    assertFalse(attribution1A.hashCode() == attribution1B.hashCode());
  }

  @Test
  public void testHashCodeSetImageUrl () {
    attribution1B.setImageUrl("image_url_1_b");
    assertFalse(attribution1A.hashCode() == attribution1B.hashCode());
  }

  @Test
  public void testHashCodeSetFilename () {
    attribution1B.setFilename("artist_filename_1_b");
    assertFalse(attribution1A.hashCode() == attribution1B.hashCode());
  }

}
