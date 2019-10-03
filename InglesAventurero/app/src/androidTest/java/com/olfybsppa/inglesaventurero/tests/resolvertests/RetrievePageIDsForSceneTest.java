package com.olfybsppa.inglesaventurero.tests.resolvertests;

import android.database.Cursor;
import android.provider.BaseColumns;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.utils.Ez;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPPage;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPScene;

import java.util.HashSet;

public class RetrievePageIDsForSceneTest extends ProviderTestCase2<LinesCP>  {

  private CPPage cpPageA = new CPPage(1);
  private CPPage cpPageB = new CPPage(2);
  // sceneName, englishTitle and spanishTitle doesn't matter.
  private CPScene cpScene = new CPScene("zeroSceneName", "zeroEnglish", "zeroSpanish", "0", "0");

  private MockContentResolver mResolver;
  private ResolverWrapper rw;

  public RetrievePageIDsForSceneTest() {
    super(LinesCP.class, LinesCP.AUTHORITY);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    mResolver = getMockContentResolver();
    rw = new ResolverWrapper(mResolver);
  }

  public void testRetrieveHintsFromPageRowID () {
    int sceneRowID = rw.insertScene(cpScene.getTitleTableContentValues());
    rw.insertPage(cpPageA.getContentValues(sceneRowID, 0));// backgroundID doesn't matter.
    rw.insertPage(cpPageB.getContentValues(sceneRowID, 0));

    HashSet<Integer> pagesRowIDsHash = rw.retrievePageIDsForScene(sceneRowID);
    Integer[] pagesRowIDs = (Integer[]) pagesRowIDsHash.toArray(new Integer[pagesRowIDsHash.size()]);
    assertEquals(2, pagesRowIDs.length);

    CPPage cpPageAtRowZero = getCPPageAtRowID(pagesRowIDs[0]);
    CPPage cpPageAtRowOne  = getCPPageAtRowID(pagesRowIDs[1]);
    assertTrue(!cpPageAtRowZero.equals(cpPageAtRowOne));
    assertTrue(cpPageAtRowZero.equals(cpPageA) || cpPageAtRowZero.equals(cpPageB));
    assertTrue(cpPageAtRowOne.equals(cpPageA) || cpPageAtRowOne.equals(cpPageB));
  }

  public void testPageDoesntHaveHintsAndNoneWereReturned () {
    // sceneID and backgroundID don't matter
    int sceneRowID = rw.insertScene(cpScene.getTitleTableContentValues());
    rw.insertPage(cpPageA.getContentValues(sceneRowID + 2, 0));// backgroundID doesn't matter.
    rw.insertPage(cpPageB.getContentValues(sceneRowID + 2, 0));// pages don't contain sceneRowID, they
                                                               // contain sceneRowID+2.

    HashSet<Integer> pagesRowIDsHash = rw.retrievePageIDsForScene(sceneRowID);
    Integer[] pagesRowIDs = (Integer[]) pagesRowIDsHash.toArray(new Integer[pagesRowIDsHash.size()]);
    assertEquals(0, pagesRowIDs.length);
  }

  private CPPage getCPPageAtRowID (int rowID) {
    Cursor cursor = mResolver.query(LinesCP.pageTableUri,
                                     null,
                                     Ez.where(BaseColumns._ID, "" + rowID),
                                     null,
                                     null);
    CPPage cpPage = null;
    if (cursor != null && cursor.moveToFirst()) {
      int pageName = cursor.getInt(cursor.getColumnIndex(LinesCP.page_name));
      cpPage = new CPPage(pageName);
    }
    cursor.close();
    return cpPage;
  }

}
