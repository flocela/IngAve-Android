package com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers;

import android.content.ContentValues;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.collectors.Background;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPHint;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPPage;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPReply;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPScene;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class SceneResolver {

  private ResolverWrapper rw;

  // Given a scene, moves scene into Content Provider.
  public SceneResolver(ResolverWrapper rw) {
    this.rw = rw;
  }

  public void insertCPScene(CPScene cpScene) {
    String                                sceneName   = cpScene.getSceneName();
    ContentValues                         sceneCV     = cpScene.getTitleTableContentValues();
    ArrayList<CPPage>                     pagesList   = cpScene.getPages();
    HashMap<Integer, Background>          bkgdsByPage = cpScene.getBkgdsByPageName();
    HashMap<String, HashSet<Attribution>> attrsByBkgd = cpScene.getAttrsByBkgdName();
    HashMap<Integer, ArrayList<CPHint>>   cphintsByPgName = cpScene.getHintsByPageName();
    HashMap<Integer, ArrayList<CPReply>>  cprepliesByPgName = cpScene.getRepliesByPageName();
    if (rw.containsScene(sceneName))
      clearScene(sceneName);
    int sceneId = rw.retrieveSceneRowId(sceneName);
    int tempSceneId = sceneId;
    if (sceneId == -1)
      sceneId = rw.insertScene(sceneCV);

    ArrayList<ContentValues> hintsContentValues = new ArrayList<>();
    ArrayList<ContentValues> repliesContentValues = new ArrayList<>();
    PageResolver pageResolver = new PageResolver(rw);
    for (CPPage page : pagesList) {
      Background background = bkgdsByPage.get(page.getPageName());
      HashSet<Attribution> attributions = attrsByBkgd.get(background.getBackgroundName());
      int pageRowId  = pageResolver.insertPage(page, sceneId, background, attributions);
      ArrayList<ContentValues> hintsCV
        = this.constructHintsCVs(sceneId, pageRowId, cphintsByPgName.get(page.getPageName()));
      ArrayList<ContentValues> repliesCV
        = this.constructRepliesCVs(sceneId, pageRowId, cprepliesByPgName.get(page.getPageName()));
      hintsContentValues.addAll(hintsCV);
      repliesContentValues.addAll(repliesCV);
    }
    rw.bulkInsertHints(convertToArray(hintsContentValues));
    rw.bulkInsertReplies(convertToArray(repliesContentValues));
    if (tempSceneId != -1)
      rw.updateSceneActiveness(sceneId, true);
  }

  public void clearScene(String sceneName) {
    int sceneID = rw.retrieveSceneRowId(sceneName);
    PageResolver pageResolver = new PageResolver(rw);
    pageResolver.deletePagesFromScene(sceneID);
    rw.updateSceneActiveness(sceneID, false);
  }

  public void clearScene(int sceneId) {
    PageResolver pageResolver = new PageResolver(rw);
    pageResolver.deletePagesFromScene(sceneId);
    rw.updateSceneActiveness(sceneId, false);
  }

  private ArrayList<ContentValues> constructHintsCVs(int sceneId,
                                                     int pageRowId,
                                                     ArrayList<CPHint> cphints) {
    ArrayList<ContentValues> hintsCVs = new ArrayList<>();
    if (cphints == null) return hintsCVs;
    for (CPHint cpHint : cphints) {
      ContentValues values = cpHint.getContentValues(sceneId, pageRowId);
      hintsCVs.add(values);
    }
    return hintsCVs;
  }

  private ArrayList<ContentValues> constructRepliesCVs(int sceneId,
                                                       int pageRowId,
                                                       ArrayList<CPReply> cpreplies) {
    ArrayList<ContentValues> repliesCVs = new ArrayList<>();
    if (cpreplies == null) return repliesCVs;
    for (CPReply cpReply : cpreplies) {
      ContentValues values = cpReply.getContentValues(sceneId, pageRowId);
      repliesCVs.add(values);
    }
    return repliesCVs;
  }

  private ContentValues[] convertToArray (ArrayList<ContentValues> cvList) {
    int size = cvList.size();
    ContentValues[] cvArray = new ContentValues[size];
    for (int ii=0; ii<size; ii++) {
      cvArray[ii] = cvList.get(ii);
    }
    return cvArray;
  }

  public HashMap<Integer, String> filenamesForScene (String sceneName) {
    int sceneRowID = rw.retrieveSceneRowId(sceneName);
    return filenamesForScene(sceneRowID);
  }

  public HashMap<Integer, String> filenamesForScene (int sceneId) {
    HashMap<CPPage, Integer> pagesWithBackgroundIDS =
      rw.retrievePagesWithBackroundIDs(sceneId);
    Collection<Integer> backgroundIDs = pagesWithBackgroundIDS.values();
    return rw.retrieveFilenames(backgroundIDs);
  }
}