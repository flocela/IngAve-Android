package com.olfybsppa.inglesaventurero.tests.resolvertests;

import android.database.Cursor;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.collectors.Background;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPPage;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPScene;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.SceneResolver;

/**
 * SceneResolver is created with a ResolverWrapper which holds a MockContentResolver.
 * SceneResolver.insertCPScene(origCPScene) is called.
 * Get Pages out of ContentProvider using MockContentResolver and they match Pages
 * in origCPScene.
 */
public class SceneResolver_AttributionsTest extends ProviderTestCase2<LinesCP> {

  private MockContentResolver mMockResolver;
  private CPScene origCPScene;
  public SceneResolver_AttributionsTest() {
    super(LinesCP.class, LinesCP.AUTHORITY);
  }
  private CPScene scene1a;
  private CPScene scene1aCopy;
  private CPScene scene1b;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    mMockResolver = getMockContentResolver();

    scene1a = new CPScene("AL-01a", "Alphabet_1a", "Alphabeto_1a", "0", "0");
    CPPage page1a  = new CPPage().setPageName(1).setAsFirst(true);
    scene1a.addBackground(1,  new Background("alphabet_1", "alphabet_1.jpg"));
    Attribution attribution1A = new Attribution("image_info_name_1_a");
    attribution1A.setArtist("artist_1_a");
    attribution1A.setLinkToLicense("link_to_license_1_a");
    attribution1A.setNameOfLicense("name_of_license_1_a");
    attribution1A.setArtistImageName("artist_image_name_1_a");
    attribution1A.setImageUrlName("image_url_name_1_a");
    attribution1A.setImageUrl("image_url_1_a");
    attribution1A.setFilename("artist_filename_1_a");
    attribution1A.setChangesMadeEnglish("changes_english_1_a");
    attribution1A.setChangesMadeSpanish("changes_spanish_1_a");
    scene1a.addAttribution("alphabet_1", attribution1A);
    scene1a.addPage(page1a);

    scene1aCopy = new CPScene("AL-01aCopy", "Alphabet_1aCopy", "Alphabeto_1aCopy", "0", "0");
    CPPage page1aCopy  = new CPPage().setPageName(1).setAsFirst(true);
    scene1aCopy.addBackground(1,  new Background("alphabet_1", "alphabet_1.jpg"));
    scene1aCopy.addAttribution("alphabet_1", attribution1A);
    scene1aCopy.addPage(page1aCopy);

    scene1b = new CPScene("AL-01b", "Alphabet_1b", "Alphabeto_1b", "0", "0");
    CPPage page1b  = new CPPage().setPageName(1).setAsFirst(true);
    scene1b.addBackground(1,  new Background("alphabet_1", "alphabet_1.jpg"));
    Attribution attribution1B = new Attribution("image_info_name_1_a");
    attribution1B.setArtist("artist_1_a");
    attribution1B.setLinkToLicense("link_to_license_1_a");
    attribution1B.setNameOfLicense("name_of_license_1_a");
    attribution1B.setArtistImageName("artist_image_name_1_a");
    attribution1B.setImageUrlName("image_url_name_1_a");
    attribution1B.setImageUrl("image_url_1_a");
    attribution1B.setFilename("artist_filename_1_a");
    attribution1B.setChangesMadeEnglish("changes_english_1_changed");
    attribution1B.setChangesMadeSpanish("changes_spanish_1_a");
    scene1b.addAttribution("alphabet_1", attribution1B);
    scene1b.addPage(page1b);

  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testInsertSceneWithSameAttributionThenDeleteFirstScene () {
    ResolverWrapper rW = new ResolverWrapper(mMockResolver);
    SceneResolver sceneResolver = new SceneResolver(rW);
    sceneResolver.insertCPScene(scene1a);
    sceneResolver.insertCPScene(scene1aCopy);

    Cursor sceneCursor1 = mMockResolver.query(LinesCP.sceneTableUri, null, null, null, null);
    sceneCursor1.moveToFirst();
    assertEquals(2, sceneCursor1.getCount());
    sceneCursor1.close();

    Cursor backgroundsCursor = mMockResolver.query(LinesCP.backgroundTableUri, null, null, null, null);
    backgroundsCursor.moveToFirst();
    assertEquals(1, backgroundsCursor.getCount());
    backgroundsCursor.close();

    Cursor atttibutionsCursor = mMockResolver.query(LinesCP.attributionTableUri, null, null, null, null);
    atttibutionsCursor.moveToFirst();
    assertEquals(1, atttibutionsCursor.getCount());
    atttibutionsCursor.close();

    Cursor imageCreditCursor = mMockResolver.query(LinesCP.attributionTableUri, null, null, null, null);
    imageCreditCursor.moveToFirst();
    assertEquals(1, imageCreditCursor.getCount());
    imageCreditCursor.close();

    sceneResolver.clearScene(scene1a.getSceneName());

    Cursor sceneCursor2 = mMockResolver.query(LinesCP.sceneTableUri, null, null, null, null);
    sceneCursor2.moveToFirst();
    assertEquals(2, sceneCursor2.getCount());
    sceneCursor2.close();

    Cursor backgroundsCursor2 = mMockResolver.query(LinesCP.backgroundTableUri, null, null, null, null);
    backgroundsCursor2.moveToFirst();
    assertEquals(1, backgroundsCursor2.getCount());
    backgroundsCursor2.close();

    Cursor atttibutionsCursor2 = mMockResolver.query(LinesCP.attributionTableUri, null, null, null, null);
    atttibutionsCursor2.moveToFirst();
    assertEquals(1, atttibutionsCursor2.getCount());
    atttibutionsCursor2.close();

    Cursor imageCreditCursor2 = mMockResolver.query(LinesCP.imgCreditUri, null, null, null, null);
    imageCreditCursor2.moveToFirst();
    assertEquals(1, imageCreditCursor2.getCount());
    imageCreditCursor2.close();
  }

  public void testInsertSceneWithSameAttributionThenInsertSceneWithUpdatedAttributionShouldUpdate () {
    ResolverWrapper rW = new ResolverWrapper(mMockResolver);
    SceneResolver sceneResolver = new SceneResolver(rW);
    sceneResolver.insertCPScene(scene1a);
    sceneResolver.insertCPScene(scene1aCopy);
    sceneResolver.insertCPScene(scene1b);

    Cursor sceneCursor1 = mMockResolver.query(LinesCP.sceneTableUri, null, null, null, null);
    sceneCursor1.moveToFirst();
    assertEquals(3, sceneCursor1.getCount());
    sceneCursor1.close();

    Cursor backgroundsCursor = mMockResolver.query(LinesCP.backgroundTableUri, null, null, null, null);
    backgroundsCursor.moveToFirst();
    assertEquals(1, backgroundsCursor.getCount());
    backgroundsCursor.close();

    Cursor atttibutionsCursor = mMockResolver.query(LinesCP.attributionTableUri, null, null, null, null);
    atttibutionsCursor.moveToFirst();
    assertEquals(1, atttibutionsCursor.getCount());
    String changesEnglish = atttibutionsCursor.getString(atttibutionsCursor.getColumnIndex(LinesCP.changes_english));
    assertEquals("changes_english_1_changed", changesEnglish);
    atttibutionsCursor.close();

    Cursor imageCreditCursor = mMockResolver.query(LinesCP.imgCreditUri, null, null, null, null);
    imageCreditCursor.moveToFirst();
    assertEquals(1, imageCreditCursor.getCount());
    imageCreditCursor.close();
  }
}