package com.olfybsppa.inglesaventurero.tests.linesCPtests;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.ImageCredit;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.utils.Ez;
import com.olfybsppa.inglesaventurero.utils.Pair;

public class ProviderImageCreditTest extends ProviderTestCase2<LinesCP> {

  private MockContentResolver mMockResolver;
  private SQLiteDatabase mDb;

  private ImageCredit firstIC;
  private ImageCredit secondIC;

  public ProviderImageCreditTest() {
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

  public void testInsertImageCredit () {
    mMockResolver.insert(LinesCP.imgCreditUri, firstIC.getContentValues());
    Cursor cursor1 = mMockResolver.query(LinesCP.imgCreditUri, null, null, null, null);
    assertNotNull(cursor1);
    assertEquals(1, cursor1.getCount());
    cursor1.moveToFirst();
    Pair<Integer, ImageCredit> imageCreditPair = ImageCredit.extractImageCredit(cursor1);
    cursor1.close();
    assertTrue(imageCreditPair.second.equals(firstIC));
    firstIC.setArtistFilename("wrongFirstArtistFilename");
    assertFalse(imageCreditPair.second.equals(firstIC));
  }

  public void testInsertTwoImageCreditsDeleteOneCredit () {
    mMockResolver.insert(LinesCP.imgCreditUri, firstIC.getContentValues());
    mMockResolver.insert(LinesCP.imgCreditUri, secondIC.getContentValues());
    Cursor cursor1 = mMockResolver.query(LinesCP.imgCreditUri, null, null, null, null);
    assertEquals(2, cursor1.getCount());
    cursor1.close();

    Cursor cursor2 = mMockResolver.query(LinesCP.imgCreditUri,
      new String[]{BaseColumns._ID},
      Ez.where(LinesCP.image_info_name, "firstImageCredit"),
      null,
      null);
    cursor2.moveToFirst();
    int creditId = cursor2.getInt(cursor2.getColumnIndex(BaseColumns._ID));
    cursor2.close();

    mMockResolver.delete(LinesCP.imgCreditUri, Ez.where(BaseColumns._ID, "" + creditId), null);

    Cursor cursor3 = mMockResolver.query(LinesCP.imgCreditUri, null, null, null, null);
    cursor3.moveToFirst();
    assertEquals(1, cursor3.getCount());
    cursor3.close();
  }

  public void testUpdateImageCredit () {
    mMockResolver.insert(LinesCP.imgCreditUri, firstIC.getContentValues());
    mMockResolver.insert(LinesCP.imgCreditUri, secondIC.getContentValues());
    Cursor cursor1 = mMockResolver.query(LinesCP.imgCreditUri,
                                         new String[] {BaseColumns._ID},
                                         Ez.where(LinesCP.image_info_name, "firstImageCredit"),
                                         null,
                                         null);
    cursor1.moveToFirst();
    int creditId1 = cursor1.getInt(cursor1.getColumnIndex(BaseColumns._ID));
    cursor1.close();

    ImageCredit changedIC = firstIC.clone();
    changedIC.setArtist("Changed Artist");
    mMockResolver.update(LinesCP.imgCreditUri,
                         changedIC.getContentValues(),
                         Ez.where(BaseColumns._ID, "" + creditId1),
                         null);

    Cursor cursor2 = mMockResolver.query(LinesCP.imgCreditUri, null, null, null, null);
    cursor2.moveToFirst();
    assertEquals(2, cursor2.getCount());
    while (!cursor2.isAfterLast()) {
      boolean matched = false;
      Pair<Integer, ImageCredit> imageCreditPair = ImageCredit.extractImageCredit(cursor2);
      assertFalse(imageCreditPair.second.equals(firstIC));
      if (imageCreditPair.second.equals(changedIC) || imageCreditPair.second.equals(secondIC)) {
        matched = true;
      }
      assertTrue(matched);
      cursor2.moveToNext();
    }
    cursor2.close();
  }

  public void testBulkInsertCredits () {
    ContentValues[] values = new ContentValues[2];
    values[0] = firstIC.getContentValues();
    values[1] = secondIC.getContentValues();
    mMockResolver.bulkInsert(LinesCP.imgCreditUri, values);
    Cursor cursor1 = mMockResolver.query(LinesCP.imgCreditUri, null, null, null, null);
    assertEquals(2, cursor1.getCount());
    cursor1.close();
  }
}