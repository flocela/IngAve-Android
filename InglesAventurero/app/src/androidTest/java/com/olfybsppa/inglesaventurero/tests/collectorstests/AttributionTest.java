package com.olfybsppa.inglesaventurero.tests.collectorstests;

import android.content.ContentValues;
import android.os.Bundle;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.start.LinesCP;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class AttributionTest {

  @Test
  public void testGetContentValues () {
    Attribution attribution1A = new Attribution("image_info_name_1_a");
    attribution1A.setChangesMadeEnglish("changes_english_1_a");
    attribution1A.setChangesMadeSpanish("changes_spanish_1_a");

    ContentValues cV = attribution1A.getContentValues();
    assertEquals("changes_english_1_a", cV.getAsString(LinesCP.changes_english));
    assertEquals("changes_spanish_1_a", cV.getAsString(LinesCP.changes_spanish));
  }

  @Test
  public void testMakeBundleFromAttribution () {
    Attribution attribution1A = new Attribution("image_info_name_1_a");
    attribution1A.setArtist("artist_1_a");
    attribution1A.setLinkToLicense("link_to_license_1_a");
    attribution1A.setNameOfLicense("name_of_license_1_a");
    attribution1A.setArtistImageName("artist_image_name_1_a");
    attribution1A.setImageUrlName("imge_url_name_1_a");
    attribution1A.setImageUrl("image_url_1_a");
    attribution1A.setFilename("artist_filename_1_a");
    attribution1A.setChangesMadeEnglish("changes_english_1_a");
    attribution1A.setChangesMadeSpanish("changes_spanish_1_a");
    Bundle origBundle = attribution1A.getPictureInfoBundle();
    Attribution backAttribution = Attribution.fromBundle(origBundle);
    assertTrue(backAttribution.equals(attribution1A));
  }

}