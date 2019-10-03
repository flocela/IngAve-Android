package com.olfybsppa.inglesaventurero.tests.linesCPtests;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.utils.Ez;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPPage;

public class ProviderPagesTest extends ProviderTestCase2<LinesCP> {

  private MockContentResolver mMockResolver;

  private CPPage page1;
  private CPPage page2;
  private ContentValues page1CV;
  private ContentValues page2CV;

  public ProviderPagesTest() {
    super(LinesCP.class, LinesCP.AUTHORITY);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    mMockResolver = getMockContentResolver();

    page1 = new CPPage();
    page1.setPageName(1);
    page1.setAsFirst(true);
    page1CV = page1.getContentValues(100, 10);

    page2 = new CPPage();
    page2.setPageName(2);
    page2CV = page2.getContentValues(100, 20);
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testInsertPage () {
    mMockResolver.insert(LinesCP.pageTableUri, page1CV);
    Cursor cursor = mMockResolver.query(LinesCP.pageTableUri, null, null, null, null);
    assertEquals(1, cursor.getCount());
    cursor.moveToFirst();
    assertEquals(100, cursor.getInt(cursor.getColumnIndex(LinesCP.scene_id)));
    assertEquals(1, cursor.getInt(cursor.getColumnIndex(LinesCP.page_name)));
    assertEquals(10, cursor.getInt(cursor.getColumnIndex(LinesCP.background_id)));
    assertEquals(1, cursor.getInt(cursor.getColumnIndex(LinesCP.is_first)));
    cursor.close();
  }

  public void testInsertTwoPagesDeleteOnePage () {
    mMockResolver.insert(LinesCP.pageTableUri, page1CV);
    mMockResolver.insert(LinesCP.pageTableUri, page2CV);
    Cursor cursor1 = mMockResolver.query(LinesCP.pageTableUri, null, null, null, null);
    assertEquals(2, cursor1.getCount());
    cursor1.moveToFirst();
    int pageId = cursor1.getInt(cursor1.getColumnIndex(BaseColumns._ID));
    cursor1.close();

    mMockResolver.delete(LinesCP.pageTableUri, Ez.where(BaseColumns._ID, "" + pageId), null);
    Cursor cursor2 = mMockResolver.query(LinesCP.pageTableUri, null, null, null, null);
    cursor2.moveToFirst();
    assertEquals(1, cursor2.getCount());
    assertFalse(pageId == cursor2.getInt(cursor2.getColumnIndex(BaseColumns._ID)));
    cursor2.close();
  }

  public void testFailureIfPageWithSameSceneAndPageNameIsAdded () {
    mMockResolver.insert(LinesCP.pageTableUri, page1CV);
    page2.setPageName(page1.getPageName());
    page2CV = page2.getContentValues(100, 20);
    boolean failed = false;
    try {
      mMockResolver.insert(LinesCP.pageTableUri, page2CV);
    }
    catch (Exception e) {
      failed = true;
    }
    assertTrue(failed);
  }

}