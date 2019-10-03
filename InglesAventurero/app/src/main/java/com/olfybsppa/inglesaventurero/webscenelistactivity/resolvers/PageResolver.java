package com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers;


import android.content.ContentValues;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.collectors.Background;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Leader;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Matchable;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Page;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPPage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PageResolver {
  private ResolverWrapper rw;

  public PageResolver(ResolverWrapper rw) {
    this.rw = rw;
  }

  public int insertPage(CPPage page,
                        Integer sceneId,
                        Background background,
                        HashSet<Attribution> attributions) {
    BackgroundResolver backgroundResolver = new BackgroundResolver(rw);
    int backgroundId = backgroundResolver.insertBkgdWithAttributions(background,
                                                                   attributions);
    ContentValues pageCV = page.getContentValues(sceneId, backgroundId);
    return rw.insertPage(pageCV);
  }

  public void deletePagesFromScene (int sceneIDRow) {
    HashSet<Integer> pageRowIDs  = rw.retrievePageIDsForScene(sceneIDRow);
    Set<Integer> bkgdRowIDs  = rw.retrieveBGRowIDsAndNumCorrPagesFrScene(pageRowIDs).keySet();
    Set<Integer> hintRowIDs  = rw.retrieveHintRowIDsFromPages(pageRowIDs);
    Set<Integer> replyRowIDs = rw.retrieveReplyRowIDsFromPages(pageRowIDs);
    Set<Integer> deleteBkgdIDS = new HashSet<>();
    rw.deletePages(pageRowIDs);
    for (Integer bkgdRowID : bkgdRowIDs) {
      int numOfPagesUsingBkgd = rw.retrieveNumOfPagesUsingBackground(bkgdRowID);
      if (numOfPagesUsingBkgd == 0) {
        deleteBkgdIDS.add(bkgdRowID);
      }
    }
    rw.deleteBackgrounds(deleteBkgdIDS);
    AttributionsResolver attributionsResolver = new AttributionsResolver(rw);
    attributionsResolver.deleteAttributions(rw.retreiveAttributionRowIds(deleteBkgdIDS));
    rw.deleteHints(hintRowIDs);
    rw.deleteReplies(replyRowIDs);
  }

  public Collection<String> retrieveBackgroundFilesToDelete (int sceneIDRow) {
    HashSet<Integer> pageRowIDs      = rw.retrievePageIDsForScene(sceneIDRow);
    HashMap<Integer, Integer> bkgdRowIDsAndNumOfPages =
      rw.retrieveBGRowIDsAndNumCorrPagesFrScene(pageRowIDs);
    ArrayList<String> toDeleteJPGS    = new ArrayList<>();
    HashMap<Integer, String> bkgdHash = rw.retrieveFilenames(bkgdRowIDsAndNumOfPages.keySet());
    for (Integer bkgdRowID : bkgdRowIDsAndNumOfPages.keySet()) {
      int numOfPagesUsingBkgdForAllScenes = rw.retrieveNumOfPagesUsingBackground(bkgdRowID);
      if (numOfPagesUsingBkgdForAllScenes <= bkgdRowIDsAndNumOfPages.get(bkgdRowID)) {
        toDeleteJPGS.add(bkgdHash.get(bkgdRowID));
      }
    }
    return toDeleteJPGS;
  }

  public HashMap<Integer, Page> retrievePagesForScene (int sceneId) {
    HashMap<Integer, Page> pages = new HashMap<>();
    int id = sceneId;
    HashMap<Integer, ArrayList<Leader>> hints = rw.retrieveHintsFromSceneId(id);
    HashMap<Integer, ArrayList<Matchable>> replies = rw.retrieveRepliesFromSceneId(id);
    HashMap<CPPage, Integer> pagesAndBgIDs = rw.retrievePagesWithBackroundIDs(id);
    HashMap<Integer, Integer> rowIdPerPageNames = rw.retreiveRowIDsPerPageNames(id);
    HashMap<Integer, String> filenames = rw.retrieveFilenames(pagesAndBgIDs.values());
    for (CPPage cppage : pagesAndBgIDs.keySet()) {
      int rowId = rowIdPerPageNames.get(cppage.getPageName());
      Page page = new Page(cppage.getPageName(), hints.get(rowId), replies.get(rowId));
      page.setAsFirstPage(cppage.isFirst());
      page.setBackgroundFilename(filenames.get(pagesAndBgIDs.get(cppage)));
      pages.put(cppage.getPageName(), page);
    }
    return pages;
  }

  public HashMap<Integer, Page> retrievePagesForScene (String story) {
    int id = rw.retrieveSceneRowId(story);
    return retrievePagesForScene(id);
  }

  public ArrayList<Attribution> retrievePictureInfo (int sceneId, int pageId) {
    ArrayList<Attribution> attributions = new ArrayList<>();
    int backgroundRowId = rw.retreiveBackgroundRowId(sceneId, pageId);
    if (-1 == backgroundRowId)
      return attributions;
    return rw.retrieveAttributionsForBackgroundID(backgroundRowId);
  }
}