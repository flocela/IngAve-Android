package com.olfybsppa.inglesaventurero.tests.resolvertests;

import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.collectors.Background;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.ImageCredit;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.utils.Pair;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPHint;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPPage;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPReply;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPScene;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.PageResolver;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.SceneResolver;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class PageResolverTest extends ProviderTestCase2<LinesCP> {

  private MockContentResolver mCR;

  private Attribution origAttr1;
  private Attribution origAttr1B;
  private Attribution origAttr2;
  private Attribution newAttr3;
  private ImageCredit origImCr1;
  private ImageCredit origImCr2;
  private ImageCredit newImCr3;
  private ImageCredit badImCr;
  private CPScene sceneA;
  private CPScene sceneB;
  private Background background1;
  public PageResolverTest() {
    super(LinesCP.class, LinesCP.AUTHORITY);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    mCR = getMockContentResolver();

    origImCr1 = new ImageCredit ("origImageCredit_1");
    origImCr1.setArtist("origArtist_1")
      .setLinkToLicense("origLinkToLicense_1")
      .setNameOfLicense("origNameOfLicense_1")
      .setArtistImageName("origArtistImageName_1")
      .setImageUrl("origImageUrl_1")
      .setImageUrlName("origImageUrlName_1")
      .setArtistFilename("origArtistFilename_1");

    origImCr2 = new ImageCredit ("origImageCredit_2");
    origImCr2.setArtist("origArtist_2")
      .setLinkToLicense("origLinkToLicense_2")
      .setNameOfLicense("origNameOfLicense_2")
      .setArtistImageName("origArtistImageName_2")
      .setImageUrl("origImageUrl_2")
      .setImageUrlName("origImageUrlName_2")
      .setArtistFilename("origArtistFilename_2");

    newImCr3 = new ImageCredit ("newImageCredit_3");
    newImCr3.setArtist("newArtist_3")
      .setLinkToLicense("newLinkToLicense_3")
      .setNameOfLicense("newNameOfLicense_3")
      .setArtistImageName("newArtistImageName_3")
      .setImageUrl("newImageUrl_3")
      .setImageUrlName("newImageUrlName_3")
      .setArtistFilename("newArtistFilename_3");

    origAttr1 = new Attribution ("origEnglishChanges_1",  "origChangesSpanish_1",  origImCr1);
    origAttr1B = new Attribution("origEnglishChanges_1B", "origChangesSpanish_1B", origImCr1);
    origAttr2 = new Attribution ("origEnglishChanges_2",  "origChangesSpanish_2",  origImCr2);
    newAttr3 = new Attribution  ("newEnglishChanges_3",   "newEnglishChanges_3",   newImCr3);

    background1 = new Background("BG1", "BG1.jpg");
    Background background2 = new Background("BG2", "BG2.jpg");
    Background background3 = new Background("BG3", "BG3.jpg");

    CPHint hintA10 = new CPHint(0, 0);
    CPReply replyA11 = new CPReply(1, 1);
    CPPage pageA1 = new CPPage(1);

    CPHint hintA20 = new CPHint(0, 0);
    CPReply replyA21 = new CPReply(1, 1);
    CPPage pageA2 = new CPPage(2);

    sceneA = new CPScene("AA", "AAEng", "AASpn", "0", "0");
    sceneA.addPage(pageA1);
    sceneA.addHint(1, hintA10);
    sceneA.addReply(1, replyA11);
    sceneA.addPage(pageA2);
    sceneA.addHint(2, hintA20);
    sceneA.addReply(2, replyA21);
    sceneA.addBackground(1, background1);
    sceneA.addBackground(2, background2);
    sceneA.addAttribution("BG1", origAttr1);
    sceneA.addAttribution("BG2", origAttr2);

    CPHint hintB10 = new CPHint(0, 0);
    CPReply replyB11 = new CPReply(1, 1);
    CPPage pageB1 = new CPPage(1);

    CPHint hintB20 = new CPHint(0, 0);
    CPReply replyB21 = new CPReply(1, 1);
    CPPage pageB2 = new CPPage(2);

    sceneB = new CPScene("BB", "BBEng", "BBSpn", "0", "0");
    sceneB.addPage(pageB1);
    sceneB.addHint(1, hintB10);
    sceneB.addReply(1, replyB11);
    sceneB.addPage(pageB2);
    sceneB.addHint(2, hintB20);
    sceneB.addReply(2, replyB21);
    sceneB.addBackground(1, background1);
    sceneB.addBackground(2, background3);
    sceneB.addAttribution("BG1", origAttr1);
    sceneB.addAttribution("BG3", newAttr3);
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test deletePagesFromScene (int sceneIDRow)
   * Add two scenes, where first page of each scene share the same background.
   *   Num of pages should be 4.
   *   Num of Backgrounds should be 3.
   *   Num of Attributions should be 3.
   *   Num of Hints should be 4.
   *   Num of Replies should be 4.
   * Then delete one scene.
   *   Num of pages should be 2.
   *   Num of Backgrounds should be 2. (deleting scene only deletes one background, the one that is not shared in the other scene.)
   *   Num of Attributions should be 1.
   *   Num of Hints should be 2.
   *   Num of Replies should be 2.
   */
  public void testDeleteSceneThatSharesBGWithAnotherScene () {
    ResolverWrapper rW = new ResolverWrapper(mCR);
    SceneResolver sceneResolver = new SceneResolver(rW);
    sceneResolver.insertCPScene(sceneA);
    sceneResolver.insertCPScene(sceneB);

    assertEquals(4, getNumOfXXX(rW, LinesCP.pageTableUri));
    assertEquals(3, getNumOfXXX(rW, LinesCP.backgroundTableUri));
    assertEquals(3, getNumOfXXX(rW, LinesCP.attributionTableUri));
    assertEquals(4, getNumOfXXX(rW, LinesCP.hintTableUri));
    assertEquals(4, getNumOfXXX(rW, LinesCP.replyTableUri));

    int sceneARowID = rW.retrieveSceneRowId(sceneA.getSceneName());
    PageResolver pageResolver = new PageResolver(rW);
    pageResolver.deletePagesFromScene(sceneARowID);

    assertEquals(2, getNumOfXXX(rW, LinesCP.pageTableUri));
    assertEquals(2, getNumOfXXX(rW, LinesCP.backgroundTableUri));
    assertEquals(2, getNumOfXXX(rW, LinesCP.attributionTableUri));
    assertEquals(2, getNumOfXXX(rW, LinesCP.hintTableUri));
    assertEquals(2, getNumOfXXX(rW, LinesCP.replyTableUri));
  }

  /**
   * retrieveBackgroundFilesToDelete (int sceneIDRow)
   * Add two scenes, where first page of each scene share the same background.
   * Delete one scene should result in one BackgroundFile returned.
   */
  public void testRetreiveBackgroundFilesToDelete () {
    ResolverWrapper rW = new ResolverWrapper(mCR);
    SceneResolver sceneResolver = new SceneResolver(rW);
    sceneResolver.insertCPScene(sceneA);
    sceneResolver.insertCPScene(sceneB);

    int sceneARowID = rW.retrieveSceneRowId(sceneA.getSceneName());
    PageResolver pageResolver = new PageResolver(rW);
    Collection<String> bgFile = pageResolver.retrieveBackgroundFilesToDelete(sceneARowID);

    assertEquals("BG2.jpg", bgFile.iterator().next());
  }

  /**
   * retrieveBackgroundFilesToDelete (int sceneIDRow)
   * SceneA has two pages that use the same background1 and another page that uses background2.
   * SceneB has a page that also uses background1.
   * Add two scenes.
   * Delete sceneA should result in background2 returned.
   */
  public void testRetreiveBackroundFilesToDeleteWhenSceneUsesBackgroundTwice () {
    ResolverWrapper rW = new ResolverWrapper(mCR);
    SceneResolver sceneResolver = new SceneResolver(rW);
    CPPage page3 = new CPPage(3);
    sceneA.addPage(page3);
    sceneA.addBackground(3, background1);
    sceneResolver.insertCPScene(sceneA);
    sceneResolver.insertCPScene(sceneB);

    int sceneARowID = rW.retrieveSceneRowId(sceneA.getSceneName());
    PageResolver pageResolver = new PageResolver(rW);
    Collection<String> bgFile = pageResolver.retrieveBackgroundFilesToDelete(sceneARowID);

    assertEquals("BG2.jpg", bgFile.iterator().next());
  }

  private HashMap<Integer, ImageCredit> getImageCredits (LinkedList<Pair<Integer, ImageCredit>> pairs) {
    HashMap<Integer, ImageCredit> hash = new HashMap<>();
    for (Pair<Integer, ImageCredit> pair : pairs) {
      hash.put(pair.first, pair.second);
    }
    return hash;
  }

  private LinkedList<Pair<Integer, ImageCredit>> retrieveAllImageCredits (ResolverWrapper rW) {
    LinkedList<Pair<Integer, ImageCredit>> imageCredits = new LinkedList<>();
    Cursor icCursor = mCR.query(LinesCP.imgCreditUri, null, null, null, null);
    if (icCursor != null) {
      icCursor.moveToFirst();
      while(!icCursor.isAfterLast()) {
        Pair<Integer, ImageCredit> icPair = ImageCredit.extractImageCredit(icCursor);
        imageCredits.add(icPair);
        icCursor.moveToNext();
      }
      icCursor.close();
    }
    return imageCredits;
  }

  private HashSet<Attribution> teaseOutAttributions(LinkedList<Pair<Integer, Attribution>> pairs) {
    HashSet<Attribution> attributions = new HashSet<>();
    for (Pair<Integer, Attribution> pair : pairs) {
      attributions.add(pair.second);
    }
    return attributions;
  }

  private HashSet<ImageCredit> onlyImageCredits (LinkedList<Pair<Integer, ImageCredit>> pairs) {
    HashSet<ImageCredit> imageCredits = new HashSet<>();
    for (Pair<Integer, ImageCredit> pair : pairs) {
      imageCredits.add(pair.second);
    }
    return imageCredits;
  }

  private Integer getRowIdForImageCredit(ImageCredit imageCredit,
                                         LinkedList<Pair<Integer, ImageCredit>> pairs) {
    Integer imageCreditRowId = null;
    for (Pair<Integer, ImageCredit> pair : pairs) {
      ImageCredit currImageCredit = pair.second;
      if (currImageCredit.equals(imageCredit)) {
        imageCreditRowId = pair.first;
      }
    }
    return imageCreditRowId;
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