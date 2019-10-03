package com.olfybsppa.inglesaventurero.tests.resolvertests;

import android.database.Cursor;
import android.provider.BaseColumns;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.collectors.Background;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.ImageCredit;
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
public class SceneResolver_ImageCreditTest extends ProviderTestCase2<LinesCP> {

  private MockContentResolver mMockResolver;
  private CPScene origCPScene;
  public SceneResolver_ImageCreditTest() {
    super(LinesCP.class, LinesCP.AUTHORITY);
  }
  private CPScene scene1a;
  private CPScene scene1ACopy;
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

    scene1ACopy = new CPScene("AL-01aCopy", "Alphabet_1aCopy", "Alphabeto_1aCopy", "0", "0");
    CPPage page1aCopy  = new CPPage().setPageName(1).setAsFirst(true);
    scene1ACopy.addBackground(1,  new Background("alphabet_1", "alphabet_1.jpg"));
    scene1ACopy.addAttribution("alphabet_1", attribution1A);
    scene1ACopy.addPage(page1aCopy);

    scene1b = new CPScene("AL-01b", "Alphabet_1b", "Alphabeto_1b", "0", "0");
    CPPage page1b  = new CPPage().setPageName(1).setAsFirst(true);
    scene1b.addBackground(1,  new Background("alphabet_1", "alphabet_1.jpg"));
    Attribution attribution1b = new Attribution("image_info_name_1_a");
    attribution1b.setArtist("artist_1_a_CHANGED");
    attribution1b.setLinkToLicense("link_to_license_1_b");
    attribution1b.setNameOfLicense("name_of_license_1_b");
    attribution1b.setArtistImageName("artist_image_name_1_b");
    attribution1b.setImageUrlName("image_url_name_1_b");
    attribution1b.setImageUrl("image_url_1_b");
    attribution1b.setFilename("artist_filename_1_b");
    attribution1b.setChangesMadeEnglish("changes_english_1_a");
    attribution1b.setChangesMadeSpanish("changes_spanish_1_a");
    scene1b.addAttribution("alphabet_1", attribution1b);
    scene1b.addPage(page1b);
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testInsertScenesWithSameImageCreditsThenInsertSceneWithDiffImageCreditsShouldUpdate () {
    ResolverWrapper rW = new ResolverWrapper(mMockResolver);
    SceneResolver sceneResolver = new SceneResolver(rW);
    sceneResolver.insertCPScene(scene1a);
    sceneResolver.insertCPScene(scene1ACopy);
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
    int imCreditIDFromAttr = atttibutionsCursor.getInt(atttibutionsCursor.getColumnIndex(LinesCP.img_credit_id));
    assertEquals(1, atttibutionsCursor.getCount());
    atttibutionsCursor.close();

    Cursor imageCreditCursor = mMockResolver.query(LinesCP.imgCreditUri, null, null, null, null);
    imageCreditCursor.moveToFirst();
    assertEquals(1, imageCreditCursor.getCount());
    ImageCredit im = ImageCredit.extractImageCredit(imageCreditCursor).second;
    assertEquals(im.getImageInfoName(), "image_info_name_1_a");
    assertEquals(im.artist, "artist_1_a_CHANGED");
    int imCreditId = imageCreditCursor.getInt(imageCreditCursor.getColumnIndex(BaseColumns._ID));
    assertEquals(imCreditId, imCreditIDFromAttr);
    imageCreditCursor.close();
  }
}