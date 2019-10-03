package com.olfybsppa.inglesaventurero.tests.linesCPtests;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.ImageCredit;
import com.olfybsppa.inglesaventurero.start.LinesCP;

public class ProviderBulkInsetTest extends ProviderTestCase2<LinesCP> {

  private MockContentResolver mMockResolver;
  private SQLiteDatabase mDb;

  private ImageCredit firstIC;
  private ImageCredit secondIC;

  public ProviderBulkInsetTest() {
    super(LinesCP.class, LinesCP.AUTHORITY);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    mMockResolver = getMockContentResolver();
    mDb = getProvider().getOpenHelperForTest().getWritableDatabase();

    firstIC = new ImageCredit ("firstImageCredit");
    firstIC.setArtist("firstArtist")
      .setLinkToLicense("firstLinkToLicense")
      .setNameOfLicense("firstNameOfLicense")
      .setArtistImageName("firstArtistImageName")
      .setImageUrl("firstImageUrl")
      .setImageUrlName("firstImageUrlName")
      .setArtistFilename("firstArtistFilename");

    secondIC = new ImageCredit ("secondImageCredit");
    secondIC.setArtist("secondArtist")
      .setLinkToLicense("secondLinkToLicense")
      .setNameOfLicense("secondNameOfLicense")
      .setArtistImageName("secondArtistImageName")
      .setImageUrl("secondImageUrl")
      .setImageUrlName("secondImageUrlName")
      .setArtistFilename("secondArtistFilename");


  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }


  public void testBulkInsertCreditsOneCreditUpdatesExistingCredit () {
    ImageCredit existingIC = new ImageCredit ("firstImageCredit");
    existingIC.setArtist("existingArtist")
      .setLinkToLicense("existingLinkToLicense")
      .setNameOfLicense("existingNameOfLicense")
      .setArtistImageName("existingArtistImageName")
      .setImageUrl("existingImageUrl")
      .setImageUrlName("existingImageUrlName")
      .setArtistFilename("existingArtistFilename");
    ContentValues[] values = new ContentValues[2];
    values[0] = firstIC.getContentValues();
    values[1] = secondIC.getContentValues();
    mMockResolver.bulkInsert(LinesCP.imgCreditUri, values);
    Cursor cursor1 = mMockResolver.query(LinesCP.imgCreditUri, null, null, null, null);
    assertEquals(2, cursor1.getCount());
    cursor1.close();
  }

}