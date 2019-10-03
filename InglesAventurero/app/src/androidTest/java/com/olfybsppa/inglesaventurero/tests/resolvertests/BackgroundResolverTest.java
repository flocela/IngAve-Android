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
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.BackgroundResolver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class BackgroundResolverTest extends ProviderTestCase2<LinesCP> {

  private MockContentResolver mCR;

  private Attribution origAttr1;
  private Attribution origAttr1B;
  private Attribution origAttr2;
  private Attribution newAttr3;
  private ImageCredit origImCr1;
  private ImageCredit origImCr2;
  private ImageCredit newImCr3;
  private ImageCredit badImCr;
  private Background background1;
  private Background background2;
  private Background background3;
  public BackgroundResolverTest() {
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
    background2 = new Background("BG2", "BG2.jpg");
    background3 = new Background("BG3", "BG3.jpg");
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Testing insertBkgdWithAttributions(Background bg, HashSet<Attribution> allBGAttributions)
   * Insert a background with two attributions.
   * Result in dB: 2 attributions
   *               1 background
   *               2 image credits
   * Add same background with 1 different attribution.
   * Results in dB: 1 attribution (diff from previous)
   *                1 background
   *                1 image credit (diff from prev) (assuming diff from previous image credits)
   */
  public void testInsertBackgroundWithDiffAttribution () {
    HashSet<Attribution> hashA = new HashSet<>();
    hashA.add(origAttr1);
    hashA.add(origAttr2);

    ResolverWrapper rW = new ResolverWrapper(mCR);
    BackgroundResolver bgResolver = new BackgroundResolver(rW);
    bgResolver.insertBkgdWithAttributions(background1, hashA);

    assertEquals(2, getNumOfXXX(rW, LinesCP.attributionTableUri));
    assertEquals(1, getNumOfXXX(rW, LinesCP.backgroundTableUri));
    assertEquals(2, getNumOfXXX(rW, LinesCP.imgCreditUri));

    HashSet<Attribution>hashB = new HashSet<>();
    hashB.add(newAttr3);
    bgResolver.insertBkgdWithAttributions(background1, hashB);

    assertEquals(1, getNumOfXXX(rW, LinesCP.attributionTableUri));
    assertEquals(1, getNumOfXXX(rW, LinesCP.backgroundTableUri));
    assertEquals(1, getNumOfXXX(rW, LinesCP.imgCreditUri));

    Pair<Integer, Background> backgroundPair = rW.retrieveBackground(background1.getBackgroundName());
    Pair<Integer, Attribution> attrPair      = rW.retrieveAttributions(backgroundPair.first).get(0);
    Pair<Integer, ImageCredit> imageCreditPair = rW.retrieveImageCreditWithID(newImCr3.imageInfoName);

    assertEquals(newAttr3, attrPair.second);
    assertEquals(newImCr3, imageCreditPair.second);
  }

  /**
   * Testing insertBkgdWithAttributions(Background bg, HashSet<Attribution> allBGAttributions)
   * Insert a background with two attributions.
   * Result in dB: 2 attributions
   *               1 background
   *               2 image credits
   * Add same background.
   * Result in dB are same: 2 attributions
   *                       1 background
   *                       2 image credits
   */
  public void testInsertingSameBackground () {
    HashSet<Attribution> hashA = new HashSet<>();
    hashA.add(origAttr1);
    hashA.add(origAttr2);

    ResolverWrapper rW = new ResolverWrapper(mCR);
    BackgroundResolver bgResolver = new BackgroundResolver(rW);
    bgResolver.insertBkgdWithAttributions(background1, hashA);

    assertEquals(2, getNumOfXXX(rW, LinesCP.attributionTableUri));
    assertEquals(1, getNumOfXXX(rW, LinesCP.backgroundTableUri));
    assertEquals(2, getNumOfXXX(rW, LinesCP.imgCreditUri));

    bgResolver.insertBkgdWithAttributions(background1, hashA);

    assertEquals(2, getNumOfXXX(rW, LinesCP.attributionTableUri));
    assertEquals(1, getNumOfXXX(rW, LinesCP.backgroundTableUri));
    assertEquals(2, getNumOfXXX(rW, LinesCP.imgCreditUri));
  }

  /**
   * Testing insertBkgdWithAttributions(Background bg, HashSet<Attribution> allBGAttributions)
   * Insert a background with two attributions.
   * Result in dB: 2 attributions
   *               1 background
   *               2 image credits
   * Add same background with 1 of the previous attributions.
   * Result in dB are same: 1 attributions
   *                       1 background
   *                       1 image credits
   */
  public void testInsertingOneAttributionThatIsTheSameAsPrevious () {
    HashSet<Attribution> hashA = new HashSet<>();
    hashA.add(origAttr1);
    hashA.add(origAttr2);

    ResolverWrapper rW = new ResolverWrapper(mCR);
    BackgroundResolver bgResolver = new BackgroundResolver(rW);
    bgResolver.insertBkgdWithAttributions(background1, hashA);

    assertEquals(2, getNumOfXXX(rW, LinesCP.attributionTableUri));
    assertEquals(1, getNumOfXXX(rW, LinesCP.backgroundTableUri));
    assertEquals(2, getNumOfXXX(rW, LinesCP.imgCreditUri));

    HashSet<Attribution>hashB = new HashSet<>();
    hashB.add(origAttr2);
    bgResolver.insertBkgdWithAttributions(background1, hashB);

    assertEquals(1, getNumOfXXX(rW, LinesCP.attributionTableUri));
    assertEquals(1, getNumOfXXX(rW, LinesCP.backgroundTableUri));
    assertEquals(1, getNumOfXXX(rW, LinesCP.imgCreditUri));

    Pair<Integer, Background> backgroundPair = rW.retrieveBackground(background1.getBackgroundName());
    Pair<Integer, Attribution> attrPair      = rW.retrieveAttributions(backgroundPair.first).get(0);
    Pair<Integer, ImageCredit> imageCreditPair = rW.retrieveImageCreditWithID(origImCr2.imageInfoName);

    assertEquals(origAttr2, attrPair.second);
    assertEquals(origImCr2, imageCreditPair.second);
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