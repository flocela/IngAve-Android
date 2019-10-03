package com.olfybsppa.inglesaventurero.tests.resolvertests;

import android.database.Cursor;
import android.provider.BaseColumns;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.collectors.Background;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.utils.Ez;
import com.olfybsppa.inglesaventurero.utils.Pair;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.SceneResolver;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPHint;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPPage;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPReply;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPScene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * SceneResolver is created with a ResolverWrapper which holds a MockContentResolver.
 * SceneResolver.insertCPScene(origCPScene) is called.
 * Get Pages out of ContentProvider using MockContentResolver and they match Pages
 * in origCPScene.
 */
public class SceneResolver_Insert_SceneTest extends ProviderTestCase2<LinesCP> {

  private MockContentResolver mMockResolver;
  private CPScene origCPScene;
  public SceneResolver_Insert_SceneTest() {
    super(LinesCP.class, LinesCP.AUTHORITY);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    mMockResolver = getMockContentResolver();
    TestSceneFactoryBasic cpSceneFactory = new TestSceneFactoryBasic();
    origCPScene = cpSceneFactory.getScene();
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testInsertCPSceneInfo () {
    ResolverWrapper rW = new ResolverWrapper(mMockResolver);
    SceneResolver sceneResolver = new SceneResolver(rW);
    sceneResolver.insertCPScene(origCPScene);

    // test scene in content provider has same scene name.
    Cursor cursor1 = mMockResolver.query(LinesCP.sceneTableUri,
                                         null,
                                         null, null, null);
    cursor1.moveToFirst();
    assertEquals(1, cursor1.getCount());
    int sceneID = cursor1.getInt(cursor1.getColumnIndex(BaseColumns._ID));
    CPScene resultCPScene = extractScene(cursor1);
    cursor1.close();
    assertTrue(origCPScene.getSceneName().equals(resultCPScene.getSceneName()));


    Cursor cursor2 = mMockResolver.query(LinesCP.pageTableUri,
                                         null,
                                         Ez.where(LinesCP.scene_id, ""+sceneID),
                                         null, null);
    cursor2.moveToFirst();
    ArrayList<Pair<Integer, CPPage>> cppagesPerBgId = extractPages(cursor2);
    cursor2.moveToFirst();
    HashMap<Integer, CPPage> cppagesPerPageRowId = getPagesPerPageRowId(cursor2);
    cursor2.close();

    // test all pages were entered into content provider
    ArrayList<CPPage> pages = getPages(cppagesPerBgId);
    assertTrue(pagesAccountedFor(origCPScene.getPages(), pages));

    // test pages reference the correct backgrounds.
    HashMap<Integer, Background> bgsByPageName= getBackgroundsByPageName(cppagesPerBgId);
    assertTrue(bgsAccountedFor(origCPScene.getBkgdsByPageName(), bgsByPageName));

    Cursor bgCursor = mMockResolver.query(LinesCP.backgroundTableUri, null, null, null, null, null);
    bgCursor.moveToFirst();
    assertEquals(12, bgCursor.getCount());

    // test attributions reference the correct backgrounds.
    HashMap<String, HashSet<Attribution>> attributionSetsPerBgName =
      getAttributionsPerBgName(cppagesPerBgId, bgsByPageName);
    assertTrue(attrsAccountedFor(attributionSetsPerBgName,
      origCPScene.getAttrsByBkgdName()));

    // test hints match up to page names
    HashMap<Integer, ArrayList<CPHint>> cphintsPerPageName =
      getHintsPerPageName(cppagesPerPageRowId);
    assertTrue(cphintsAccountedFor(origCPScene.getHintsByPageName(),
                                   cphintsPerPageName));

    // test replies match up to page names
    HashMap<Integer, ArrayList<CPReply>> cprepliesPerPageName =
      getRepliesPerPageName(cppagesPerPageRowId);
    assertTrue(cprepliesAccountedFor(origCPScene.getRepliesByPageName(),
      cprepliesPerPageName));
  }

  public void testInsertCPSceneInfoTwice () {
    ResolverWrapper rW = new ResolverWrapper(mMockResolver);
    SceneResolver sceneResolver = new SceneResolver(rW);
    sceneResolver.insertCPScene(origCPScene);
    sceneResolver.insertCPScene(origCPScene);

    Cursor cursor1 = mMockResolver.query(LinesCP.sceneTableUri,
                                         null,
                                         null, null, null);
    cursor1.moveToFirst();
    assertEquals(1, cursor1.getCount());
    int sceneID = cursor1.getInt(cursor1.getColumnIndex(BaseColumns._ID));
    CPScene resultCPScene = extractScene(cursor1);
    cursor1.close();
    assertTrue(origCPScene.getSceneName().equals(resultCPScene.getSceneName()));


    Cursor cursor2 = mMockResolver.query(LinesCP.pageTableUri,
                                         null,
                                         Ez.where(LinesCP.scene_id, ""+sceneID),
                                         null, null);
    cursor2.moveToFirst();
    ArrayList<Pair<Integer, CPPage>> cppagesPerBgId = extractPages(cursor2);
    cursor2.moveToFirst();
    HashMap<Integer, CPPage> cppagesPerPageRowId = getPagesPerPageRowId(cursor2);
    cursor2.close();

    // test all pages were entered into content provider
    ArrayList<CPPage> pages = getPages(cppagesPerBgId);
    assertTrue(pagesAccountedFor(origCPScene.getPages(), pages));

    // test pages reference the correct backgrounds.
    HashMap<Integer, Background> bgsByPageName= getBackgroundsByPageName(cppagesPerBgId);
    assertTrue(bgsAccountedFor(origCPScene.getBkgdsByPageName(), bgsByPageName));

    // test attributions reference the correct backgrounds.
    HashMap<String, HashSet<Attribution>> attributionSetsPerBgName =
      getAttributionsPerBgName(cppagesPerBgId, bgsByPageName);
    assertTrue(attrsAccountedFor(attributionSetsPerBgName,
      origCPScene.getAttrsByBkgdName()));

    // test hints match up to page names
    HashMap<Integer, ArrayList<CPHint>> cphintsPerPageName =
      getHintsPerPageName(cppagesPerPageRowId);
    assertTrue(cphintsAccountedFor(origCPScene.getHintsByPageName(),
      cphintsPerPageName));

    // test replies match up to page names
    HashMap<Integer, ArrayList<CPReply>> cprepliesPerPageName =
      getRepliesPerPageName(cppagesPerPageRowId);
    assertTrue(cprepliesAccountedFor(origCPScene.getRepliesByPageName(),
      cprepliesPerPageName));

    // Test pages, hints, replies, attributions, backgrounds, image credits only in content provider once.
    Cursor pagesCursor = mMockResolver.query(LinesCP.pageTableUri, null, null, null, null);
    pagesCursor.moveToFirst();
    assertEquals(12, pagesCursor.getCount());

    Cursor backgroundsCursor = mMockResolver.query(LinesCP.backgroundTableUri, null, null, null, null);
    backgroundsCursor.moveToFirst();
    assertEquals(12, backgroundsCursor.getCount());

    Cursor hintsCursor = mMockResolver.query(LinesCP.hintTableUri, null, null, null, null);
    hintsCursor.moveToFirst();
    assertEquals(13, hintsCursor.getCount());

    Cursor repliesCursor = mMockResolver.query(LinesCP.replyTableUri, null, null, null, null);
    repliesCursor.moveToFirst();
    assertEquals(13, repliesCursor.getCount());

    Cursor atttibutionsCursor = mMockResolver.query(LinesCP.attributionTableUri, null, null, null, null);
    atttibutionsCursor.moveToFirst();
    assertEquals(14, atttibutionsCursor.getCount());

    Cursor imageCreditCursor = mMockResolver.query(LinesCP.imgCreditUri, null, null, null, null);
    imageCreditCursor.moveToFirst();
    assertEquals(14, imageCreditCursor.getCount());

  }


  private HashMap<Integer, ArrayList<CPReply>> getRepliesPerPageName
    (HashMap<Integer, CPPage> cppagesPerPageRowId) {
    HashMap<Integer, ArrayList<CPReply>> cpreplies = new HashMap<>();
    ResolverWrapper rW = new ResolverWrapper(mMockResolver);
    for (Integer pageRowId : cppagesPerPageRowId.keySet()) {
      Integer pageName = cppagesPerPageRowId.get(pageRowId).getPageName();
      Cursor cursor = mMockResolver.query(LinesCP.replyTableUri,
        null,
        Ez.where(LinesCP.page_id, ""+pageRowId),
        null,
        null);
      cursor.moveToFirst();
      ArrayList<CPReply> cpRepliesList = new ArrayList<>();
      while (!cursor.isAfterLast()) {
        CPReply cpReply = CPReply.extractCPReply(cursor);
        cpRepliesList.add(cpReply);
        cursor.moveToNext();
      }
      cursor.close();
      cpreplies.put(pageName, cpRepliesList);
    }
    return cpreplies;
  }

  private HashMap<Integer, ArrayList<CPHint>> getHintsPerPageName
    (HashMap<Integer, CPPage> cppagesPerPageRowId) {
    HashMap<Integer, ArrayList<CPHint>> cphints = new HashMap<>();
    for (Integer pageRowId : cppagesPerPageRowId.keySet()) {
      Integer pageName = cppagesPerPageRowId.get(pageRowId).getPageName();
      Cursor cursor = mMockResolver.query(LinesCP.hintTableUri,
                                          null,
                                          Ez.where(LinesCP.page_id, ""+pageRowId),
                                          null,
                                          null);
      cursor.moveToFirst();
      ArrayList<CPHint> cphintsList = new ArrayList<>();
      while (!cursor.isAfterLast()) {
        CPHint cphint = CPHint.extractCPHint(cursor);
        cphintsList.add(cphint);
        cursor.moveToNext();
      }
      cursor.close();
      cphints.put(pageName, cphintsList);
    }
    return cphints;
  }

  private HashMap<Integer, CPPage> getPagesPerPageRowId (Cursor cursor) {
    HashMap<Integer, CPPage> pagesPerRowId = new HashMap<>();
    while (!cursor.isAfterLast()) {
      Integer rowId = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
      CPPage cppage = extractPage(cursor);
      pagesPerRowId.put(rowId, cppage);
      cursor.moveToNext();
    }
    return pagesPerRowId;
  }

  private HashMap<String, HashSet<Attribution>> getAttributionsPerBgName
    (ArrayList<Pair<Integer, CPPage>> cppagesPerBgId,
     HashMap<Integer, Background> bgsByPageName) {
    ResolverWrapper rW = new ResolverWrapper(mMockResolver);
    HashMap<String, HashSet<Attribution>> attributionsPerBgName = new HashMap<>();
    for (Pair<Integer, CPPage> pair : cppagesPerBgId) {
      Integer bkgdID = pair.first;
      Integer pageName = pair.second.getPageName();
      String backgroundName = bgsByPageName.get(pageName).getBackgroundName();
      HashSet<Attribution> hashSet = getHashSet(rW.retrieveAttributions(bkgdID));
      attributionsPerBgName.put(backgroundName, hashSet);
    }
    return attributionsPerBgName;
  }

  private HashSet<Attribution> getHashSet (LinkedList<Pair<Integer, Attribution>> attributions) {
    HashSet<Attribution> attributionHash = new HashSet<>();
    for (Pair<Integer, Attribution> pair: attributions) {
      attributionHash.add(pair.second);
    }
    return attributionHash;
  }

  //Pair is Background Row Id and corresponding CPPage
  private ArrayList<Pair<Integer, CPPage>> extractPages (Cursor cursor) {
    ArrayList<Pair<Integer, CPPage>> cppages = new ArrayList<>();
    while (!cursor.isAfterLast()) {
      int backgroundRowId = cursor.getInt(cursor.getColumnIndex(LinesCP.background_id));
      CPPage cppage = extractPage(cursor);
      cppages.add(new Pair<Integer, CPPage>(backgroundRowId, cppage));
      cursor.moveToNext();
    }
    return cppages;
  }

  private CPPage extractPage (Cursor cursor) {
    Integer pageName = cursor.getInt(cursor.getColumnIndex(LinesCP.page_name));
    int isFirstInt = cursor.getInt(cursor.getColumnIndex(LinesCP.is_first));

    boolean isFirst = false;
    if (isFirstInt == 1)  isFirst = true;

    CPPage cppage = new CPPage();
    cppage.setPageName(pageName);
    cppage.setAsFirst(isFirst);
    return cppage;
  }

  private ArrayList<CPPage> getPages (ArrayList<Pair<Integer, CPPage>> pairs) {
    ArrayList<CPPage> pages = new ArrayList<>();
    for (Pair<Integer, CPPage> pair : pairs) {
      pages.add(pair.second);
    }
    return pages;
  }

  // Returned Pairs are PageName and corresonding Backgrounds
  // Argument are Pairs of Background Row Id and corresponding CPPage
  private HashMap<Integer, Background>
              getBackgroundsByPageName (ArrayList<Pair<Integer, CPPage>> pairs) {

    HashMap<Integer, Background> backgroundsByPageName = new HashMap<>();
    for (Pair<Integer, CPPage> pair : pairs) {
      Integer pageName = pair.second.getPageName();
      Background background = Background.extractBackground(mMockResolver, pair.first);
      backgroundsByPageName.put(pageName, background);
    }
    return backgroundsByPageName;
  }


  private boolean pagesAccountedFor (ArrayList<CPPage> correctPages,
                                     ArrayList<CPPage> otherPages) {
    if (correctPages.size() != otherPages.size()) return false;
    for (CPPage correctPage : correctPages) {
      if (!otherPages.contains(correctPage))return false;
    }
    return true;
  }

  private boolean bgsAccountedFor (HashMap<Integer, Background> correctBackgrounds,
                                   HashMap<Integer, Background> otherBackgrounds) {
    if (correctBackgrounds.size() != otherBackgrounds.size()) return false;
    Set<Integer> correctPageNames = correctBackgrounds.keySet();
    for (Integer pageName : correctPageNames) {
      if (!correctBackgrounds.get(pageName).equals(otherBackgrounds.get(pageName))) {
        return false;
      }
    }
    return true;
  }

  private boolean attrsAccountedFor (HashMap<String, HashSet<Attribution>> correctAttrs,
                                     HashMap<String, HashSet<Attribution>> otherAttrs) {

    int correctSize = 0;
    for (HashSet<Attribution> attrs : correctAttrs.values()) {
      correctSize = correctSize + attrs.size();
    }
    int otherSize = 0;
    for (HashSet<Attribution> attrs : otherAttrs.values()) {
      otherSize = otherSize + attrs.size();
    }
    if (correctSize != otherSize) return false;

    for (String corrBgName : correctAttrs.keySet()) {
      HashSet<Attribution> corrAttrsHash = correctAttrs.get(corrBgName);
      HashSet<Attribution> otherAttrsHash = otherAttrs.get(corrBgName);
      if (!corrAttrsHash.equals(otherAttrsHash)) return false;
    }
    return true;
  }

  private boolean cphintsAccountedFor (HashMap<Integer, ArrayList<CPHint>> correctCPHints,
                                       HashMap<Integer, ArrayList<CPHint>> otherCPHints) {
    int correctSize = 0;
    for (ArrayList<CPHint> cphintsList : correctCPHints.values()) {
      correctSize = correctSize + cphintsList.size();
    }
    int otherSize = 0;
    for (ArrayList<CPHint> cphintsList : otherCPHints.values()) {
      otherSize = otherSize + cphintsList.size();
    }
    if (otherSize != correctSize) return false;

    for (Integer corrPageName : correctCPHints.keySet()) {
      ArrayList<CPHint> corrCPHintsList = correctCPHints.get(corrPageName);
      ArrayList<CPHint> otherCPHintsList = otherCPHints.get(corrPageName);
      if (corrCPHintsList.size() != otherCPHintsList.size()) return false;
      if (!corrCPHintsList.containsAll(otherCPHintsList))
        return false;
    }
    return true;
  }

  private boolean cprepliesAccountedFor
                    (HashMap<Integer, ArrayList<CPReply>> correctCPReplies,
                     HashMap<Integer, ArrayList<CPReply>> otherCPReplies) {
    int correctSize = 0;
    for (ArrayList<CPReply>cprepliesList : correctCPReplies.values()) {
      correctSize = correctSize + cprepliesList.size();
    }
    int otherSize = 0;
    for (ArrayList<CPReply> cprepliesList : otherCPReplies.values()) {
      otherSize = otherSize + cprepliesList.size();
    }
    if (otherSize != correctSize) return false;

    for (Integer corrPageName : correctCPReplies.keySet()) {
      ArrayList<CPReply> corrCPRepliesList = correctCPReplies.get(corrPageName);
      ArrayList<CPReply> otherCPRepliesList = otherCPReplies.get(corrPageName);
      if (corrCPRepliesList.size() != otherCPRepliesList.size()) return false;
      if (!corrCPRepliesList.containsAll(otherCPRepliesList)) return false;
    }
    return true;
  }

  private CPScene extractScene (Cursor cursor) {
    String sceneName = cursor.getString(cursor.getColumnIndex(LinesCP.scene_name));
    String englishTitle = cursor.getString(cursor.getColumnIndex(LinesCP.english_title));
    String spanishTitle = cursor.getString(cursor.getColumnIndex(LinesCP.spanish_title));
    return new CPScene(sceneName, englishTitle, spanishTitle, "0", "0");
  }
}