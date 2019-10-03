package com.olfybsppa.inglesaventurero.tests.linesCPtests;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.utils.Ez;

public class ProviderImageAttributionTest extends ProviderTestCase2<LinesCP> {

  private MockContentResolver mMockResolver;

  private Attribution attribution1;
  private Attribution attribution2;

  public ProviderImageAttributionTest() {
    super(LinesCP.class, LinesCP.AUTHORITY);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    mMockResolver = getMockContentResolver();

    attribution1 = new Attribution("doesnotmatter");
    attribution1.setChangesMadeEnglish("changesMadeInEnglish1");
    attribution1.setChangesMadeSpanish("changesMadeInSpanish1");

    attribution2 = new Attribution("doesnotmatter");
    attribution2.setChangesMadeEnglish("changesMadeInEnglish2");
    attribution2.setChangesMadeSpanish("changesMadeInSpanish2");

  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testInsertImageCredit () {
    ContentValues cv1 = attribution1.getContentValues();
    cv1.put(LinesCP.img_credit_id, 10);
    cv1.put(LinesCP.background_id, 100);
    mMockResolver.insert(LinesCP.attributionTableUri, cv1);

    ContentValues cv2 = attribution2.getContentValues();
    cv2.put(LinesCP.img_credit_id, 20);
    cv2.put(LinesCP.background_id, 200);
    mMockResolver.insert(LinesCP.attributionTableUri, cv2);

    Cursor cursor = mMockResolver.query(LinesCP.attributionTableUri,
                                        null,
                                        null,
                                        null,
                                        null);
    assertNotNull(cursor);
    cursor.moveToFirst();
    assertEquals(2, cursor.getCount());
    cursor.close();
  }

  public void testDoesNotAllowNullCreditId () {
    ContentValues cv1 = attribution1.getContentValues();
    cv1.put(LinesCP.background_id, 100);
    boolean failed = false;
    try {
      mMockResolver.insert(LinesCP.attributionTableUri, cv1);
    }
    catch (Exception e) {
      failed = true;
    }
    assertTrue(failed);
  }

  public void testDoesNotAllowNullBackgroundId () {
    ContentValues cv1 = attribution1.getContentValues();
    cv1.put(LinesCP.img_credit_id, 10);
    boolean failed = false;
    try {
      mMockResolver.insert(LinesCP.attributionTableUri, cv1);
    }
    catch (Exception e) {
      failed = true;
    }
    assertTrue(failed);
  }

  public void testDeleteAnAttribute (){
    ContentValues cv1 = attribution1.getContentValues();
    cv1.put(LinesCP.img_credit_id, 10);
    cv1.put(LinesCP.background_id, 100);
    mMockResolver.insert(LinesCP.attributionTableUri, cv1);

    ContentValues cv2 = attribution2.getContentValues();
    cv2.put(LinesCP.img_credit_id, 20);
    cv2.put(LinesCP.background_id, 200);
    mMockResolver.insert(LinesCP.attributionTableUri, cv2);

    Cursor cursor1 = mMockResolver.query(LinesCP.attributionTableUri,
                                         null,
                                         null,
                                         null,
                                         null);
    cursor1.moveToFirst();
    assertEquals(2, cursor1.getCount());
    cursor1.moveToFirst();
    cursor1.close();

    Cursor cursor2 = mMockResolver.query(LinesCP.attributionTableUri,
                                         new String[] {BaseColumns._ID},
                                         Ez.where(LinesCP.img_credit_id, ""+10),
                                         null,
                                         null);
    cursor2.moveToFirst();
    int attrId1 = cursor2.getInt(cursor2.getColumnIndex(BaseColumns._ID));
    cursor2.close();

    mMockResolver.delete(LinesCP.attributionTableUri,
                         Ez.where(BaseColumns._ID, "" + attrId1),
                         null);

    Cursor cursor3 = mMockResolver.query(LinesCP.attributionTableUri,
                                         null,
                                         null,
                                         null,
                                         null);
    cursor3.moveToFirst();
    assertEquals(1, cursor3.getCount());
    assertEquals("changesMadeInEnglish2",
                 cursor3.getString(cursor1.getColumnIndex(LinesCP.changes_english)));
    cursor3.close();
  }
}