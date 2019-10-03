package com.olfybsppa.inglesaventurero.tests.linesCPtests;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.olfybsppa.inglesaventurero.collectors.Background;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.utils.Ez;

public class ProviderBackgroundsTest extends ProviderTestCase2<LinesCP> {

  private MockContentResolver mMockResolver;
  private Background background1;
  private Background background2;

  public ProviderBackgroundsTest() {
    super(LinesCP.class, LinesCP.AUTHORITY);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    mMockResolver = getMockContentResolver();
    background1 = new Background("background 1","background1.jpg");
    background1.addFilename("one");
    background2 = new Background("background 2", "background2.jpg");
    background2.addFilename("two");
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testInsertOneBackground () {
    ContentValues cV = background1.getContentValues();
    mMockResolver.insert(LinesCP.backgroundTableUri, cV);
    Cursor cursor = mMockResolver.query(LinesCP.backgroundTableUri, null, null, null, null);
    cursor.moveToFirst();
    assertEquals(1, cursor.getCount());
    Background backgroundReturned =
      new Background(cursor.getString(cursor.getColumnIndex(LinesCP.background_name)),
                     cursor.getString(cursor.getColumnIndex(LinesCP.filename)));
    assertTrue(backgroundReturned.equals(background1));
  }

  public void testInsertTwoBackgroundDeleteOne () {
    ContentValues cV1 = background1.getContentValues();
    ContentValues cV2 = background2.getContentValues();
    mMockResolver.insert(LinesCP.backgroundTableUri, cV1);
    mMockResolver.insert(LinesCP.backgroundTableUri, cV2);
    Cursor cursor1 = mMockResolver.query(LinesCP.backgroundTableUri, null, null, null, null);
    cursor1.moveToFirst();
    assertEquals(2, cursor1.getCount());
    cursor1.close();

    Cursor cursor2 = mMockResolver.query(LinesCP.backgroundTableUri, new String[]{BaseColumns._ID}, Ez.where(LinesCP.background_name, "background 1"), null, null);
    cursor2.moveToFirst();
    int background1Id = cursor2.getInt(cursor2.getColumnIndex(BaseColumns._ID));
    cursor2.close();

    mMockResolver.delete(LinesCP.backgroundTableUri, Ez.where(BaseColumns._ID, "" + background1Id), null);

    Cursor cursor3 = mMockResolver.query(LinesCP.backgroundTableUri, null, null, null, null);
    assertEquals(1, cursor3.getCount());
    cursor3.moveToFirst();
    Background backgroundNotDeleted =
      new Background(cursor3.getString(cursor3.getColumnIndex(LinesCP.background_name)),
           cursor3.getString(cursor3.getColumnIndex(LinesCP.filename)));
    assertTrue(backgroundNotDeleted.equals(background2));
    cursor3.close();
  }

  public void testDoesNotAllowTwoBackgroundsWithSameName () {
    ContentValues cV1 = background1.getContentValues();
    ContentValues cV2 = new ContentValues();
    cV2.put(LinesCP.background_name, "background 1");
    cV2.put(LinesCP.filename, "different");
    mMockResolver.insert(LinesCP.backgroundTableUri, cV1);
    boolean failed = false;
    try {
      mMockResolver.insert(LinesCP.backgroundTableUri, cV2);
    }
    catch (Exception e) {
      failed = true;
    }
    assertTrue(failed);

  }

  public void testDoesNotAllowsTwoBackgroundsWithSameFilename () {
    ContentValues cV1 = background1.getContentValues();
    ContentValues cV2 = new ContentValues();
    cV2.put(LinesCP.background_name, background2.getBackgroundName());
    cV2.put(LinesCP.filename, background1.getBackgroundFilename());
    mMockResolver.insert(LinesCP.backgroundTableUri, cV1);
    boolean failed = false;
    try {
      mMockResolver.insert(LinesCP.backgroundTableUri, cV2);
    }
    catch (Exception e) {
      failed = true;
    }
    assertTrue(failed);

  }


}