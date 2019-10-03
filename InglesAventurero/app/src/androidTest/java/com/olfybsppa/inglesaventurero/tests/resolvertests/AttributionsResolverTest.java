package com.olfybsppa.inglesaventurero.tests.resolvertests;

import android.database.Cursor;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.ImageCredit;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.utils.Pair;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.AttributionsResolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class AttributionsResolverTest extends ProviderTestCase2<LinesCP> {

  private MockContentResolver mCR;

  private Attribution origAttr1;
  private Attribution origAttr1B;
  private Attribution origAttr2;
  private Attribution newAttr3;
  private ImageCredit origImCr1;
  private ImageCredit origImCr2;
  private ImageCredit newImCr3;
  private ImageCredit badImCr;

  public AttributionsResolverTest() {
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

    badImCr = new ImageCredit("badImCr");
    badImCr.setArtist("badArtist_")
      .setLinkToLicense("badLinkToLicense_")
      .setNameOfLicense("badNameOfLicense_")
      .setArtistImageName("badArtistImageName_")
      .setImageUrl("badImageUrl_")
      .setImageUrlName("badImageUrlName_")
      .setArtistFilename("badArtistFilename_");
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test setAttributions(bgId, Attributions newSet)
   * Insert attributions when no previous imagecredits or attributions exist in the
   * content provider.
   */
  public void testSetAttributionsSimple () {
    int bgId = 12;
    HashSet<Attribution> origSet = new HashSet<>();
    origSet.add(origAttr1);
    origSet.add(origAttr2);

    ResolverWrapper rW = new ResolverWrapper(mCR);
    AttributionsResolver attributionsResolver = new AttributionsResolver(rW);
    attributionsResolver.setAttributions(bgId, origSet);

    LinkedList<Pair<Integer, Attribution>> retrievedAttrPairs = rW.retrieveAttributions(bgId);

    assertEquals(2, retrievedAttrPairs.size());
    for (Attribution orig : origSet) {
      boolean origHasMatchInOrigSet = false;
      for (int ii=0; ii<retrievedAttrPairs.size(); ii++) {
        if (orig.equals(retrievedAttrPairs.get(ii).second)) {
          retrievedAttrPairs.remove(ii);
          origHasMatchInOrigSet = true;
          break;
        }
      }
      assertTrue(origHasMatchInOrigSet);
    }
  }

  /**
   * Test setAttributions(bgId, Attributions newSet)
   * Insert attributions when the same attributions already exist in the
   * content provider. Only one set of attributions will be in content provider, originals
   * were deleted.
   */
  public void testSetAttributionsWhenTheyAlreadyExist () {
    int bgId = 12;
    HashSet<Attribution> correctHash = new HashSet<>();
    correctHash.add(origAttr1);
    correctHash.add(origAttr2);

    ResolverWrapper rW = new ResolverWrapper(mCR);
    AttributionsResolver attributionsResolver = new AttributionsResolver(rW);
    attributionsResolver.setAttributions(bgId, correctHash); // inserting first time.
    attributionsResolver.setAttributions(bgId, correctHash); // inserting second time.

    HashSet<Attribution> cpHash = teaseOutAttributions(rW.retrieveAttributions(bgId));
    assertEquals(correctHash, cpHash);
  }

  /**
   * Test setAttributions(bgId, Attributions newSet)
   * Case where original set of attributions pointing to background are added to content
   * provider, then new set of attributions are added to content provider. Attributions
   * contained in orig set that are not in new set should be deleted. The content provider
   * should only carry the new set of attributions.
   */
  public void testExistingAttributionsPointingToBGNotIncludedInNewHash_AreDeleted() {
    int bgId = 12;
    ResolverWrapper rW = new ResolverWrapper(mCR);
    HashSet<Attribution> origHash = new HashSet<>();
    origHash.add(origAttr1);
    origHash.add(origAttr2);
    origHash.add(origAttr1B);
    AttributionsResolver attributionsResolver = new AttributionsResolver(rW);
    attributionsResolver.setAttributions(bgId, origHash);

    Attribution newAttribution_1 = origAttr1.clone();
    newAttribution_1.setChangesMadeEnglish("newEnglishChanges_1");
    HashSet<Attribution> newSet = new HashSet<>();
    newSet.add(newAttribution_1);
    newSet.add(newAttr3);
    newSet.add(origAttr1B);
    attributionsResolver.setAttributions(bgId, newSet);

    HashSet<Attribution> cpSet = teaseOutAttributions(rW.retrieveAttributions(bgId));
    assertEquals(newSet, cpSet);
  }

  /**
   * Testing setAttributions(Integer bkgdID, HashSet<Attribution> newAttributionsHash)
   * First verify that origAttr1 and origAttr1B, refer to the same imagecredit, then
   * origAttr1 is updated to newAttr_1 but still keeps the same imagecredit, newAttr_1
   * and origAttr1B will refer to the same imagecredit as before.
   */
  public void testUpdatedAttributionRefersToSameIC () {
    int bgId = 12;
    ResolverWrapper rW = new ResolverWrapper(mCR);
    HashSet<Attribution> origHash = new HashSet<>();
    origHash.add(origAttr1);
    origHash.add(origAttr1B);
    origHash.add(origAttr2);
    AttributionsResolver attributionsResolver = new AttributionsResolver(rW);
    attributionsResolver.setAttributions(bgId, origHash);

    LinkedList<Pair<Integer, ImageCredit>> origImageCredits = retrieveAllImageCredits(rW);
    Integer ic1RowId = getRowIdForImageCredit(origImCr1, origImageCredits);

    Attribution newAttr_1 = origAttr1.clone();
    newAttr_1.setChangesMadeEnglish("newEnglishChanges_1");
    HashSet<Attribution> newSet = new HashSet<>();
    newSet.add(newAttr_1);
    newSet.add(origAttr1B);
    attributionsResolver.setAttributions(bgId, newSet);

    HashSet<Attribution> cpSet = teaseOutAttributions(rW.retrieveAttributions(bgId));
    assertEquals(newSet, cpSet);

    HashMap<Integer, ImageCredit> finalImageCredits
      = getImageCredits(retrieveAllImageCredits(rW));
    assertEquals(origImCr1, finalImageCredits.get(ic1RowId));
    assertTrue(finalImageCredits.size() == 1);
  }

  /**
   * Testing setAttributions(Integer bkgdID, HashSet<Attribution> newAttributionsHash)
   * AttributionA and attributionB refer to the same imagecredit and belong
   * to different backgrounds. attributionA is replaced by attribution3 which
   * refers to another imagecredit.
   * attributionB should still refer to the same imagecredit it started with.
   */
  public void testInsertTwoAttributionsWithSameICButDiffBGID_ThenChangeOneToHaveDifferentIC () {
    int bgId_A = 12;
    HashSet<Attribution> origHashA = new HashSet<>();
    origHashA.add(origAttr1);
    ResolverWrapper rW = new ResolverWrapper(mCR);
    AttributionsResolver attributionsResolver1A = new AttributionsResolver(rW);
    attributionsResolver1A.setAttributions(bgId_A, origHashA);

    int bgId_B = 13;
    HashSet<Attribution> origHashB = new HashSet<>();
    Attribution origAttribution1b = origAttr1.clone();
    origAttribution1b.setChangesMadeEnglish("changesMadeEnglish_1B");
    origHashB.add(origAttribution1b);
    AttributionsResolver attributionsResolver1B = new AttributionsResolver(rW);
    attributionsResolver1B.setAttributions(bgId_B, origHashB);

    LinkedList<Pair<Integer, ImageCredit>> origImageCredits = retrieveAllImageCredits(rW);
    Integer ic1RowId = getRowIdForImageCredit(origImCr1, origImageCredits);

    HashSet<Attribution> newHashA = new HashSet<>();
    newHashA.add(newAttr3);
    attributionsResolver1A.setAttributions(bgId_A, newHashA);

    LinkedList<Pair<Integer, Attribution>> attrPairsA = rW.retrieveAttributions(bgId_A);
    assertEquals(teaseOutAttributions(attrPairsA), newHashA);

    LinkedList<Pair<Integer, Attribution>> attrPairsB = rW.retrieveAttributions(bgId_B);
    assertEquals(teaseOutAttributions(attrPairsB), origHashB);

    LinkedList<Pair<Integer, ImageCredit>> finalImageCreditPairs
      = retrieveAllImageCredits(rW);
    HashSet<ImageCredit> finalImageCredits = onlyImageCredits(finalImageCreditPairs);
    HashMap<Integer, ImageCredit> finalImageCreditsHashMap = getImageCredits(finalImageCreditPairs);
    HashSet<ImageCredit> correctFinalImageCredits = new HashSet<>();
    correctFinalImageCredits.add(origImCr1);
    correctFinalImageCredits.add(newImCr3);
    assertEquals(correctFinalImageCredits, finalImageCredits);

    assertEquals(origImCr1, finalImageCreditsHashMap.get(ic1RowId));
  }

  /**
   * Testing setAttributions(Integer bkgdID, HashSet<Attribution> newAttributionsHash)
   * BG with two orig attributes with their own separate IC's are inserted. Then
   * attribution on original BG are set to a new BG with a new IC. The BG will be updated
   * with the new Attribution. And the original IC's will be deleted leaving only the
   * last IC.
   */
  public void testTwoAttributesAreReplacedByNewAttribute () {
    int bgId = 12;
    HashSet<Attribution> origHash = new HashSet<>();
    origHash.add(origAttr1);
    origHash.add(origAttr2);

    ResolverWrapper rW = new ResolverWrapper(mCR);
    AttributionsResolver attributionsResolver = new AttributionsResolver(rW);
    attributionsResolver.setAttributions(bgId, origHash);

    HashSet<Attribution> newHash = new HashSet<>();
    newHash.add(newAttr3);
    attributionsResolver.setAttributions(bgId, newHash);

    LinkedList<Pair<Integer, Attribution>> attrPairs = rW.retrieveAttributions(bgId);
    assertEquals(newHash, teaseOutAttributions(attrPairs));

    LinkedList<Pair<Integer, ImageCredit>> imCreditsFromCP = retrieveAllImageCredits(rW);
    assertEquals(1, imCreditsFromCP.size());
    assertEquals(newImCr3, imCreditsFromCP.get(0).second);
  }

  /**
   * Testing setAttributions(Integer bkgdID, HashSet<Attribution> newAttributionsHash)
   */
  public void testOneAttributionReplacedByOneAttribution () {
    int bgId = 12;
    HashSet<Attribution> origHash = new HashSet<>();
    origHash.add(origAttr1);

    ResolverWrapper rW = new ResolverWrapper(mCR);
    AttributionsResolver attributionsResolver = new AttributionsResolver(rW);
    attributionsResolver.setAttributions(bgId, origHash);

    HashSet<Attribution> newHash = new HashSet<>();
    newHash.add(newAttr3);
    attributionsResolver.setAttributions(bgId, newHash);

    assertEquals(newHash, teaseOutAttributions(rW.retrieveAttributions(bgId)));

    LinkedList<Pair<Integer, ImageCredit>> imCreditsFromCP = retrieveAllImageCredits(rW);
    assertEquals(1, imCreditsFromCP.size());
    assertEquals(newImCr3, imCreditsFromCP.get(0).second);
  }

  /**
   * Testing setAttributions(Integer bkgdID, HashSet<Attribution> newAttributionsHash)
   * Result is two updated attributes, one points to orig IC, the other points to new IC.
   */
  public void testOrigAttrsShareIC_ReplaceWithSameAttrWithSharedICAndAttributeWithDifferenceIC () {
    int bgId = 12;
    Attribution origAttribution1B = origAttr1.clone();
    origAttribution1B.setChangesMadeEnglish("changesMadeEnglishB");
    HashSet<Attribution> origHash = new HashSet<>();
    origHash.add(origAttr1);
    origHash.add(origAttribution1B);

    ResolverWrapper rW = new ResolverWrapper(mCR);
    AttributionsResolver attributionsResolver = new AttributionsResolver(rW);
    attributionsResolver.setAttributions(bgId, origHash);

    HashSet<Attribution> newHash = new HashSet<>();
    newHash.add(origAttr1);
    newHash.add(newAttr3);
    attributionsResolver.setAttributions(bgId, newHash);

    LinkedList<Pair<Integer, Attribution>> attrPairs = rW.retrieveAttributions(bgId);

    assertEquals(2, attrPairs.size());
    for (Attribution hashAttr : newHash) {//Attrs. in newHash should of replaced Attrs. in origHash.
      boolean hasMatch = false;
      for (int ii=0; ii<attrPairs.size(); ii++) {
        if (hashAttr.equals(attrPairs.get(ii).second)) {
          attrPairs.remove(ii);
          hasMatch = true;
          break;
        }
      }
      assertTrue(hasMatch);
    }

    LinkedList<Pair<Integer, ImageCredit>> imCreditsFromCP = retrieveAllImageCredits(rW);
    LinkedList<ImageCredit> newImageCredits = new LinkedList<>();
    newImageCredits.add(origImCr1);
    newImageCredits.add(newImCr3);
    assertEquals(2, imCreditsFromCP.size());
    for (ImageCredit newImageCredit : newImageCredits) {
      boolean hasMatch = false;
      for (int jj=0; jj<imCreditsFromCP.size(); jj++) {
        if (newImageCredit.equals(imCreditsFromCP.get(jj).second)) {
          imCreditsFromCP.remove(jj);
          hasMatch = true;
          break;
        }
      }
      assertTrue(hasMatch);
    }
  }

  /**
   * Testing deleteAttribution (int attributionRowId)
   * Simple test. Test imagecredit is deleted when only one attribution points to it.
   */
  public void testDeletingAttributionDeletesIC () {
    int bgId = 12;
    ArrayList<Integer> bgIds = new ArrayList<>();
    bgIds.add(bgId);
    HashSet<Attribution> origHash = new HashSet<>();
    origHash.add(origAttr1);
    ResolverWrapper rW = new ResolverWrapper(mCR);
    AttributionsResolver attributionsResolver = new AttributionsResolver(rW);
    attributionsResolver.setAttributions(bgId, origHash);

    int numOfImageCredits = -1;
    Cursor cursor = rW.shortQuery(LinesCP.imgCreditUri, null, null);
    if (cursor != null) {
      if (cursor.moveToFirst())
        numOfImageCredits = cursor.getCount();
      cursor.close();
    }
    assertEquals(1, numOfImageCredits);

    Collection<Integer> attrRowIDs = rW.retreiveAttributionRowIds(bgIds);
    attributionsResolver.deleteAttribution(attrRowIDs.iterator().next());

    int newNumOfImageCredits = -1;
    Cursor icCursor = rW.shortQuery(LinesCP.imgCreditUri, null, null);
    if (icCursor != null) {
      newNumOfImageCredits = icCursor.getCount();
      icCursor.close();
    }
    assertEquals(0, newNumOfImageCredits); //No remaining imagecredits.
  }

  /**
   * Testing deleteAttribution (int attributionRowId)
   * origAttr1 and origAttr1B both share origImCr1. Deleting origAttr1, does not delete
   * origImCr1.
   */
  public void testAttributionThatShareICWithAnotherAttribution () {
    int bgId = 12;
    ArrayList<Integer> bgIds = new ArrayList<>();
    bgIds.add(bgId);
    HashSet<Attribution> origHash = new HashSet<>();
    origHash.add(origAttr1);
    origHash.add(origAttr1B);
    ResolverWrapper rW = new ResolverWrapper(mCR);
    AttributionsResolver attributionsResolver = new AttributionsResolver(rW);
    attributionsResolver.setAttributions(bgId, origHash);

    int numOfImageCredits = -1;
    Cursor cursor = rW.shortQuery(LinesCP.imgCreditUri, null, null);
    if (cursor != null) {
      if (cursor.moveToFirst())
        numOfImageCredits = cursor.getCount();
      cursor.close();
    }
    assertEquals(1, numOfImageCredits);

    Collection<Integer> attrRowIDs = rW.retreiveAttributionRowIds(bgIds);
    attributionsResolver.deleteAttribution(attrRowIDs.iterator().next());

    int newNumOfImageCredits = -1;
    Cursor icCursor = rW.shortQuery(LinesCP.imgCreditUri, null, null);
    if (icCursor != null) {
      if (icCursor.moveToFirst())
        newNumOfImageCredits = icCursor.getCount();
      icCursor.close();
    }
    assertEquals(1, newNumOfImageCredits); //Still one imagecredit.
  }

  /**
   * Testing deleteAttributions (Collection<Integer> attributionIDs)
   * bgId1 has origAttr1 and origAttr1B,
   * bgId2 has origAttr2
   * bgId3 has newAttr3.
   * Add all bgds to ContentProvider.
   * Retrieve attribution row Ids for origAttr1B, origAttr2, and newAttr3, leaving out origAttr1.
   * delete attributions except for origAttr1.
   * Should still be left with origImCr1 because I didn't delete origAttr1.
   */
  public void testImageCreditsAreNotDeletedWhenStillUsedByAnotherAttribution () {
    int bgId1 = 12;
    int bgId2 = 13;
    int bgId3 = 14;
    HashSet<Attribution> origHash1 = new HashSet<>();
    origHash1.add(origAttr1);
    origHash1.add(origAttr1B);
    HashSet<Attribution> origHash2 = new HashSet<>();
    origHash2.add(origAttr2);
    HashSet<Attribution> origHash3 = new HashSet<>();
    origHash3.add(newAttr3);

    ResolverWrapper rW = new ResolverWrapper(mCR);
    AttributionsResolver attributionsResolver = new AttributionsResolver(rW);
    attributionsResolver.setAttributions(bgId1, origHash1);
    attributionsResolver.setAttributions(bgId2, origHash2);
    attributionsResolver.setAttributions(bgId3, origHash3);

    int numOfImageCredits = -1;
    Cursor cursor = rW.shortQuery(LinesCP.imgCreditUri, null, null);
    if (cursor != null) {
      if (cursor.moveToFirst())
        numOfImageCredits = cursor.getCount();
      cursor.close();
    }
    assertEquals(3, numOfImageCredits);

    HashSet<Integer> bg1 = new HashSet<>();
    bg1.add(bgId1);
    Collection<Integer> attrRowIDs1 = rW.retreiveAttributionRowIds(bg1);
    Iterator<Integer> iterator = attrRowIDs1.iterator();
    int origAttr2RowId = iterator.next();
    HashSet<Integer> bg2 = new HashSet<>();
    bg2.add(bgId2);
    Collection<Integer> attrRowIDs2 = rW.retreiveAttributionRowIds(bg2);
    HashSet<Integer> bg3 = new HashSet<>();
    bg3.add(bgId3);
    Collection<Integer> attrRowIDs3 = rW.retreiveAttributionRowIds(bg3);

    HashSet<Integer> attributionIDsAllButFirst = new HashSet<>();
    attributionIDsAllButFirst.add(origAttr2RowId);
    attributionIDsAllButFirst.add(attrRowIDs2.iterator().next());
    attributionIDsAllButFirst.add(attrRowIDs3.iterator().next());

    attributionsResolver.deleteAttributions(attributionIDsAllButFirst);

    int newNumOfImageCredits = -1;
    Cursor icCursor = rW.shortQuery(LinesCP.imgCreditUri, null, null);
    if (icCursor != null) {
      if (icCursor.moveToFirst())
        newNumOfImageCredits = icCursor.getCount();
      icCursor.close();
    }
    assertEquals(1, newNumOfImageCredits); //Still one imagecredit.

    int numOfAttr = -1;
    Cursor attrCursor = rW.shortQuery(LinesCP.imgCreditUri, null, null);
    if (attrCursor != null) {
      if (attrCursor.moveToFirst())
        numOfAttr = attrCursor.getCount();
      attrCursor.close();
    }
    assertEquals(1, numOfAttr);
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
}