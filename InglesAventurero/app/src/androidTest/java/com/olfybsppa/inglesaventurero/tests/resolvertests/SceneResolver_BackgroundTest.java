package com.olfybsppa.inglesaventurero.tests.resolvertests;

import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.collectors.Background;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.utils.Pair;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPPage;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPScene;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.SceneResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * SceneResolver is created with a ResolverWrapper which holds a MockContentResolver.
 * SceneResolver.insertCPScene(origCPScene) is called.
 * Get Pages out of ContentProvider using MockContentResolver and they match Pages
 * in origCPScene.
 */
public class SceneResolver_BackgroundTest extends ProviderTestCase2<LinesCP> {

  private MockContentResolver mMockResolver;
  private CPScene origCPScene;
  public SceneResolver_BackgroundTest() {
    super(LinesCP.class, LinesCP.AUTHORITY);
  }
  private CPScene scene1a;
  private CPScene scene1aCopy;
  private CPScene scene1b;
  private CPScene scene1c;
  Attribution attribution1BDiff;
  
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
    scene1b.addBackground(1,  new Background("alphabet_1", "alphabet_changed.jpg"));
    attribution1BDiff = new Attribution("image_info_name_1_b_diff");
    attribution1BDiff.setArtist("artist_1_b_diff");
    attribution1BDiff.setLinkToLicense("link_to_license_1_b_diff");
    attribution1BDiff.setNameOfLicense("name_of_license_1_b_diff");
    attribution1BDiff.setArtistImageName("artist_image_name_1_b_diff");
    attribution1BDiff.setImageUrlName("image_url_name_1_b_diff");
    attribution1BDiff.setImageUrl("image_url_1_b_diff");
    attribution1BDiff.setFilename("artist_filename_1_b_diff");
    attribution1BDiff.setChangesMadeEnglish("changes_english_1_b_diff");
    attribution1BDiff.setChangesMadeSpanish("changes_spanish_1_b_diff");
    scene1b.addAttribution("alphabet_1", attribution1BDiff);
    scene1b.addPage(page1b);

    scene1c = new CPScene("AL-01c", "Alphabet_1c", "Alphabeto_1c", "0", "0");
    CPPage page1c  = new CPPage().setPageName(1).setAsFirst(true);
    scene1c.addBackground(1,  new Background("alphabet_1C", "alphabet_changed_C.jpg"));
    Attribution attribution1C = new Attribution("image_info_name_1_C");
    attribution1C.setArtist("artist_1_c");
    attribution1C.setLinkToLicense("link_to_license_1_c");
    attribution1C.setNameOfLicense("name_of_license_1_c");
    attribution1C.setArtistImageName("artist_image_name_1_c");
    attribution1C.setImageUrlName("image_url_name_1_c");
    attribution1C.setImageUrl("image_url_1_c");
    attribution1C.setFilename("artist_filename_1_c");
    attribution1C.setChangesMadeEnglish("changes_english_1_c");
    attribution1C.setChangesMadeSpanish("changes_spanish_1_c");
    scene1c.addAttribution("alphabet_1C", attribution1C);
    scene1c.addPage(page1c);
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testInsertDiffSceneWithSameBackgroundThenDeleteFirstScene () {
    ResolverWrapper rW = new ResolverWrapper(mMockResolver);
    SceneResolver sceneResolver = new SceneResolver(rW);
    sceneResolver.insertCPScene(scene1a);
    sceneResolver.insertCPScene(scene1aCopy);

    assertEquals(2, getNumOfXXX(rW, LinesCP.sceneTableUri));
    assertEquals(1, getNumOfXXX(rW, LinesCP.backgroundTableUri));
    assertEquals(1, getNumOfXXX(rW, LinesCP.attributionTableUri));
    assertEquals(1, getNumOfXXX(rW, LinesCP.imgCreditUri));

    sceneResolver.clearScene(scene1a.getSceneName());

    assertEquals(2, getNumOfXXX(rW, LinesCP.sceneTableUri));
    assertEquals(1, getNumOfXXX(rW, LinesCP.backgroundTableUri));
    assertEquals(1, getNumOfXXX(rW, LinesCP.attributionTableUri));
    assertEquals(1, getNumOfXXX(rW, LinesCP.imgCreditUri));
  }

  public void testInsertSceneWithSameBackgroundThenInsertSceneWithUpdatedBackgroundShouldUpdate () {
    ResolverWrapper rW = new ResolverWrapper(mMockResolver);
    SceneResolver sceneResolver = new SceneResolver(rW);
    
    sceneResolver.insertCPScene(scene1a);
    sceneResolver.insertCPScene(scene1aCopy);    
    sceneResolver.insertCPScene(scene1b);

    assertEquals(3, getNumOfXXX(rW, LinesCP.sceneTableUri));

    // all three pages each in their own scene point to the same background.
    Cursor pageCursor1 = mMockResolver.query(LinesCP.pageTableUri, null, null, null,null);
    pageCursor1.moveToFirst();
    int bkgdIdFromFirstScene = getInt(pageCursor1, LinesCP.background_id);
    pageCursor1.moveToNext();
    assertEquals(bkgdIdFromFirstScene, getInt(pageCursor1, LinesCP.background_id));
    pageCursor1.moveToNext();
    assertEquals(bkgdIdFromFirstScene, getInt(pageCursor1, LinesCP.background_id));
    pageCursor1.close();

    ArrayList<Integer> bgIDS = new ArrayList<>();
    bgIDS.add(bkgdIdFromFirstScene);
    HashMap<Integer, String> filenames = rW.retrieveFilenames(bgIDS);
    assertEquals("alphabet_changed.jpg", filenames.values().iterator().next());

    LinkedList<Pair<Integer, Attribution>> attributions = rW.retrieveAttributions(bkgdIdFromFirstScene);
    assertEquals(attribution1BDiff, attributions.get(0).second);

    assertEquals(1, getNumOfXXX(rW, LinesCP.backgroundTableUri));
    assertEquals(1, getNumOfXXX(rW, LinesCP.attributionTableUri));
    assertEquals(1, getNumOfXXX(rW, LinesCP.imgCreditUri));
  }

  public void testInsertDiffSceneWithDiffBackground () {
    ResolverWrapper rW = new ResolverWrapper(mMockResolver);
    SceneResolver sceneResolver = new SceneResolver(rW);
    sceneResolver.insertCPScene(scene1a);
    sceneResolver.insertCPScene(scene1c);

    assertEquals(2, getNumOfXXX(rW, LinesCP.sceneTableUri));
    assertEquals(2, getNumOfXXX(rW, LinesCP.backgroundTableUri));
    assertEquals(2, getNumOfXXX(rW, LinesCP.attributionTableUri));
    assertEquals(2, getNumOfXXX(rW, LinesCP.imgCreditUri));
  }

  private int getInt (Cursor cursor, String columnName) {
    return cursor.getInt(cursor.getColumnIndex(columnName));
  }

  private int getNumOfXXX (ResolverWrapper rW, Uri uri) {
    int numOfXXX = -1;
    Cursor cursor = rW.shortQuery(uri, null, null);
    if (cursor != null) {
      numOfXXX = cursor.getCount();
      cursor.close();
    }
    return numOfXXX;
  }
}