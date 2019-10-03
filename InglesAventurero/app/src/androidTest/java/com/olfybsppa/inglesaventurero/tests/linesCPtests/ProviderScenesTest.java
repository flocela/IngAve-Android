package com.olfybsppa.inglesaventurero.tests.linesCPtests;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.utils.Ez;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPScene;

public class ProviderScenesTest extends ProviderTestCase2<LinesCP> {

  private MockContentResolver mMockResolver;
  private SQLiteDatabase mDb;

  public ProviderScenesTest() {
    super(LinesCP.class, LinesCP.AUTHORITY);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    mMockResolver = getMockContentResolver();
    mDb = getProvider().getOpenHelperForTest().getWritableDatabase();
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testThereAreNoScenes () {
    String[] columns = {BaseColumns._ID};

    Cursor cursor = mMockResolver.query(LinesCP.sceneTableUri, columns, null, null, null);
    assertNotNull(cursor);
    assertEquals(0, cursor.getCount());
    cursor.close();
  }

  public void testInsertOneScene () {
    CPScene scene = new CPScene ("firstScene", "primerTitulo", "firstTitle", "0", "0");
    mMockResolver.insert(LinesCP.sceneTableUri, scene.getTitleTableContentValues());
    Cursor cursor = mMockResolver.query(LinesCP.sceneTableUri, null, null, null, null);
    assertNotNull(cursor);
    assertEquals(1, cursor.getCount());
    if (cursor != null) cursor.moveToFirst();
    assertEquals("firstScene", cursor.getString(cursor.getColumnIndex(LinesCP.scene_name)));
    assertEquals("primerTitulo", cursor.getString(cursor.getColumnIndex(LinesCP.english_title)));
    assertEquals("firstTitle", cursor.getString(cursor.getColumnIndex(LinesCP.spanish_title)));
    cursor.close();
  }

  public void testInsertTwoScenesDeleteOneScene () {
    CPScene scene1 = new CPScene ("firstScene", "primerTitulo", "firstTitle", "0", "0");
    CPScene scene2 = new CPScene ("secondScene", "segundoTitulo", "secondTitle", "0", "0");
    mMockResolver.insert(LinesCP.sceneTableUri, scene1.getTitleTableContentValues());
    mMockResolver.insert(LinesCP.sceneTableUri, scene2.getTitleTableContentValues());
    Cursor cursor1 = mMockResolver.query(LinesCP.sceneTableUri, null, null, null, null);
    assertNotNull(cursor1);
    assertEquals(2, cursor1.getCount());
    cursor1.close();

    Cursor cursor2 = mMockResolver.query(LinesCP.sceneTableUri, new String[] {BaseColumns._ID}, Ez.where(LinesCP.scene_name, "firstScene"), null, null);
    if (cursor2 !=null) cursor2.moveToFirst();
    int scene1Id = cursor2.getInt(cursor1.getColumnIndex(BaseColumns._ID));
    cursor2.close();
    mMockResolver.delete(LinesCP.sceneTableUri, Ez.where(BaseColumns._ID, "" + scene1Id), null);

    Cursor cursor3 = mMockResolver.query(LinesCP.sceneTableUri, null, null, null, null);
    assertNotNull(cursor3);
    cursor3.moveToFirst();
    assertEquals(1, cursor3.getCount());
    cursor3.close();
  }

  public void testFailureIfSceneHasSameNameAsExistingScene () {
    CPScene scene1 = new CPScene ("firstScene", "primerTitulo", "firstTitle", "0", "0");
    CPScene scene2 = new CPScene ("secondScene", "primerTitulo", "firstTitle", "0", "0");
    mMockResolver.insert(LinesCP.sceneTableUri, scene1.getTitleTableContentValues());

    boolean failed = false;
    try {
      mMockResolver.insert(LinesCP.sceneTableUri, scene2.getTitleTableContentValues());
    }catch (Exception e) {
      failed = true;
    }
    assertTrue(failed);
  }


}