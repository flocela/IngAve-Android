package com.olfybsppa.inglesaventurero.webscenelistactivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.collectors.Background;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Hint;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.ImageCredit;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Leader;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Matchable;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Reply;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.utils.Ez;
import com.olfybsppa.inglesaventurero.utils.Pair;
import com.olfybsppa.inglesaventurero.utils.UriDeterminer;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPPage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class ResolverWrapper {

  private ContentResolver cr;

  /* deleteXXX() methods are not tested. Too simple. Just passes work to ContentResolver.
   * insertXXX() methods are not tested. Too simple. Just passes work to ContentResolver.
   * updateXXX() methods are not tested. Too simple. Just passes work to ContentResolver.
   *
   * NOTE: There is no updateAttribution. There is only updateImageCredit and updateBackground.
   * These are simple, they don't call other updates.
   */
  public ResolverWrapper (ContentResolver contentResolver) {
    this.cr = contentResolver;
  }

  //Checked by inspection.
  public boolean containsScene (String sceneName) {
    boolean containsScene = false;
    Cursor cursor = shortQuery(LinesCP.sceneTableUri,
                               new String[]{BaseColumns._ID},
                               Ez.where(LinesCP.scene_name, sceneName));
    if (cursor == null)
      return false;
    cursor.moveToFirst();
    if (cursor.getCount() > 0)
      containsScene = true;
    cursor.close();
    return containsScene;
  }

  public int deleteImageCredit (int rowID) {
    return shortDelete(LinesCP.imgCreditUri, rowID);
  }

  public int deleteAttribution (int rowID) {
    return shortDelete(LinesCP.attributionTableUri, rowID);
  }

  public int deleteAttributions (Iterable<Integer> rowIDs) {
    if (!rowIDs.iterator().hasNext())
      return 0;
    String whereClause = Ez.orWhere(BaseColumns._ID, rowIDs);
    return cr.delete(LinesCP.attributionTableUri, whereClause, null);
  }

  public int deletePages (Iterable<Integer> rowIDs) {
    if (!rowIDs.iterator().hasNext())
      return 0;
    String whereClause = Ez.orWhere(BaseColumns._ID, rowIDs);
    return cr.delete(LinesCP.pageTableUri, whereClause, null);
  }

  public int deleteBackgrounds (Iterable<Integer> bkgdRowIds) {
    if (!bkgdRowIds.iterator().hasNext())
      return 0;
    String whereClause = Ez.orWhere(BaseColumns._ID, bkgdRowIds);
    return cr.delete(LinesCP.backgroundTableUri, whereClause, null);
  }

  public int deleteHints (Iterable<Integer> hintRowIds) {
    if (!hintRowIds.iterator().hasNext())
      return 0;
    String whereClause = Ez.orWhere(BaseColumns._ID, hintRowIds);
    return cr.delete(LinesCP.hintTableUri, whereClause, null);
  }

  public int deleteReplies (Iterable<Integer> replyRowIds) {
    if (!replyRowIds.iterator().hasNext())
      return 0;
    String whereClause = Ez.orWhere(BaseColumns._ID, replyRowIds);
    return cr.delete(LinesCP.replyTableUri, whereClause, null);
  }

  public int insertScene (ContentValues sceneCV) {
    Uri uri = cr.insert(LinesCP.sceneTableUri, sceneCV);
    return UriDeterminer.getLastId(uri);
  }

  public int insertImageCredit (ContentValues imageCreditCV) {
    Uri uri = cr.insert(LinesCP.imgCreditUri, imageCreditCV);
    return UriDeterminer.getLastId(uri);
  }

  public int insertAttributionSimple (ContentValues attributionCV) {
    Uri uri = cr.insert(LinesCP.attributionTableUri, attributionCV);
    return UriDeterminer.getLastId(uri);
  }

  public Integer insertBackground(ContentValues cV) {
    Uri uri = cr.insert(LinesCP.backgroundTableUri, cV);
    return UriDeterminer.getLastId(uri);
  }

  public Integer insertPage(ContentValues cV) {
    Uri uri = cr.insert(LinesCP.pageTableUri, cV);
    return UriDeterminer.getLastId(uri);
  }

  public void bulkInsertHints (ContentValues[] values) {
    cr.bulkInsert(LinesCP.hintTableUri, values);
  }

  public void bulkInsertReplies(ContentValues[] values) {
    cr.bulkInsert(LinesCP.replyTableUri, values);
  }

  public int updateBackground(Background background, String whereStatement) {
    return cr.update(LinesCP.backgroundTableUri,
      background.getContentValues(),
      whereStatement,
      null);
  }

  public int updateImageCredit(ImageCredit imageCredit, String whereStatement) {
    return cr.update(LinesCP.imgCreditUri,
      imageCredit.getContentValues(),
      whereStatement,
      null);
  }

  public int updateSceneActiveness(int sceneId, boolean active) {
    ContentValues contentValues = new ContentValues();
    if (active == true)
      contentValues.put(LinesCP.active, 1);
    else
      contentValues.put(LinesCP.active, -1);
    return cr.update(LinesCP.sceneTableUri,
                     contentValues,
                     Ez.where(BaseColumns._ID, "" + sceneId),
                     null);
  }

  public int updateSceneCompleteness(int sceneId, boolean finished) {
    ContentValues contentValues = new ContentValues();
    if (finished == true)
      contentValues.put(LinesCP.finished, 1);
    else
      contentValues.put(LinesCP.finished, -1);
    return cr.update(LinesCP.sceneTableUri,
      contentValues,
      Ez.where(BaseColumns._ID, "" + sceneId),
      null);
  }

  public HashSet<Integer> retrievePageIDsForScene (int sceneRowID) {
    Cursor cursor = shortQuery(LinesCP.pageTableUri,
                               new String[] {BaseColumns._ID},
                               Ez.where(LinesCP.scene_id, "" + sceneRowID));
    return getIntegersFromColumn(cursor, BaseColumns._ID);
  }

  //Checked by inspection.
  public HashMap<CPPage, Integer> retrievePagesWithBackroundIDs (int sceneId) {
    HashMap<CPPage, Integer> pages = new HashMap<>();
    Cursor cr = shortQuery(LinesCP.pageTableUri, null, Ez.where(LinesCP.scene_id, "" + sceneId));
    if (cr != null) {
      if (cr.moveToFirst()) {
        while(!cr.isAfterLast()) {
          CPPage cppage = new CPPage(getInt(cr, LinesCP.page_name));
          cppage.setAsFirst(getInt(cr, LinesCP.is_first) == 1);
          pages.put(cppage, getInt(cr, LinesCP.background_id));
          cr.moveToNext();
        }
      }
      cr.close();
    }
    return pages;
  }

  //Checked by inspection.
  public HashMap<Integer, String> retrieveFilenames (Iterable<Integer> bgIds) {
    HashMap<Integer, String> filenames = new HashMap<>();
    Iterator<Integer> iterator = bgIds.iterator();
    if (!iterator.hasNext())
      return filenames;
    Cursor cu = shortQuery(LinesCP.backgroundTableUri,
                           new String[] {BaseColumns._ID, LinesCP.filename},
                           Ez.orWhere(BaseColumns._ID, bgIds));
    if (cu != null) {
      if (cu.moveToFirst()) {
        while(!cu.isAfterLast()) {
          filenames.put(getInt(cu, BaseColumns._ID), getString(cu, LinesCP.filename));
          cu.moveToNext();
        }
      }
      cu.close();
    }
    return filenames;
  }

  //Checked by inspection.
  public HashMap<Integer, Integer> retreiveRowIDsPerPageNames (int sceneId) {
    HashMap<Integer, Integer>  pages = new HashMap<>();
    Cursor cr = shortQuery(LinesCP.pageTableUri,
                           new String[] {BaseColumns._ID, LinesCP.page_name},
                           Ez.where(LinesCP.scene_id, "" + sceneId));
    if (cr != null) {
      if (cr.moveToFirst()) {
        while(!cr.isAfterLast()) {
          pages.put(getInt(cr, LinesCP.page_name), getInt(cr, BaseColumns._ID));
          cr.moveToNext();
        }
      }
      cr.close();
    }
    return pages;
  }

  //Checked by inspection.
  public Pair<Integer, Background> retrieveBackground (String backgroundName) {
    Cursor cursor = shortQuery(LinesCP.backgroundTableUri,
                               null,
                               Ez.where(LinesCP.background_name, backgroundName));
    int backgroundRow = -1;
    Background background = null;
    if (cursor != null) {
      if (cursor.moveToFirst()) {
        backgroundRow = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        background = new Background(cursor.getString(cursor.getColumnIndex(LinesCP.background_name)),
                                    cursor.getString(cursor.getColumnIndex(LinesCP.filename)));
      }
      cursor.close();
    }
    return new Pair<>(backgroundRow, background);
  }

  //Checked by inspection.
  public int retreiveBackgroundRowId (int sceneId, int pageName) {
    Cursor cursor = shortQuery(LinesCP.pageTableUri,
                             new String[] {LinesCP.background_id},
                             Ez.where(LinesCP.scene_id, ""+sceneId, LinesCP.page_name, "" + pageName));
    int backgroundRowId = -1;
    if (cursor != null) {
      if (cursor.moveToFirst()) {
        backgroundRowId = cursor.getInt(cursor.getColumnIndex(LinesCP.background_id));
      }
      cursor.close();
    }
    return backgroundRowId;
  }

  //Checked by inspection.
  public LinkedList<Pair<Integer, Attribution>> retrieveAttributions (int backgroundId) {
    LinkedList <Pair<Integer, Attribution>> attributions = new LinkedList<>();
    Cursor attrCursor = shortQuery(LinesCP.attributionTableUri,
                                   null,
                                   Ez.where(LinesCP.background_id, ""+backgroundId));
    if (attrCursor != null) {
      if (attrCursor.moveToFirst()) {
        while (!attrCursor.isAfterLast()) {
          int ic_id = attrCursor.getInt(attrCursor.getColumnIndex(LinesCP.img_credit_id));
          Cursor icCursor = queryForImageCreditCursor(ic_id);
          if (icCursor != null) {
            if (icCursor.moveToFirst()) {
              Pair<Integer, ImageCredit> imageCPair = ImageCredit.extractImageCredit(icCursor);
              ImageCredit imageC = imageCPair.second;
              String englishChanges = attrCursor.getString(attrCursor.getColumnIndex(LinesCP.changes_english));
              String spanishChanges = attrCursor.getString(attrCursor.getColumnIndex(LinesCP.changes_spanish));
              int attrRowId = attrCursor.getInt(attrCursor.getColumnIndex(BaseColumns._ID));
              Attribution attr = new Attribution(englishChanges, spanishChanges, imageC);
              attributions.add(new Pair<>(attrRowId, attr));
            }
            icCursor.close();
          }
          attrCursor.moveToNext();
        }
      }
      attrCursor.close();
    }
    return attributions;
  }

  //Checked by inspection.
  public Pair<Integer, ImageCredit> retrieveImageCreditWithID(String imageInfoName) {
    Pair<Integer, ImageCredit> creditFromCP = new Pair<>(-1, null);
    Cursor cursor = queryForImageCreditCursor(imageInfoName);
    if (cursor != null) {
      if (cursor.moveToFirst()) {
        creditFromCP = ImageCredit.extractImageCredit(cursor);
      }
      cursor.close();
    }
    return creditFromCP;
  }

  //Checked by inspection.
  public int retrieveImageCreditRowId (int attributionRowId) {
    Cursor attrCursor = shortQuery(LinesCP.attributionTableUri,
                                   new String[] {LinesCP.img_credit_id},
                                   Ez.where(BaseColumns._ID, "" + attributionRowId));
    int imageCreditId = -1;
    if (attrCursor != null && attrCursor.moveToFirst())
      imageCreditId = getInt(attrCursor,LinesCP.img_credit_id);
    if (attrCursor != null)
      attrCursor.close();
    return imageCreditId;
  }

  //Checked by inspection.
  public Collection<Integer> retreiveImageCreditRowIds (Iterable<Integer> attributionIds) {
    HashSet<Integer> imCreditIds = new HashSet<>();
    if (!attributionIds.iterator().hasNext())
      return imCreditIds;
    Cursor cursor = shortQuery(LinesCP.attributionTableUri,
                               new String[] {LinesCP.img_credit_id},
                               Ez.orWhere(BaseColumns._ID, attributionIds));
    if (cursor != null) {
      if (cursor.moveToFirst()) {
        while (!cursor.isAfterLast()) {
          imCreditIds.add(getInt(cursor, LinesCP.img_credit_id));
          cursor.moveToNext();
        }
      }
      cursor.close();
    }
    return imCreditIds;
  }

  //Checked by inspection.
  public Collection<Integer> retreiveAttributionRowIds (Iterable<Integer> bgIds) {
    HashSet<Integer> attrIds = new HashSet<>();
    if (!bgIds.iterator().hasNext())
      return attrIds;
    Cursor cursor = shortQuery(LinesCP.attributionTableUri,
                               new String[] {BaseColumns._ID},
                               Ez.orWhere(LinesCP.background_id, bgIds));
    if (cursor != null) {
      if (cursor.moveToFirst()) {
        while (!cursor.isAfterLast()) {
          attrIds.add(getInt(cursor, BaseColumns._ID));
          cursor.moveToNext();
        }
      }
      cursor.close();
    }
    return attrIds;
  }

  //Checked by inspection.
  public ArrayList<Attribution> retrieveAttributionsForBackgroundID (int backgroundRowId) {
    ArrayList<Attribution> attributions = new ArrayList<>();
    Cursor cursor = shortQuery(LinesCP.attributionTableUri,
                               null,
                               Ez.where(LinesCP.background_id, ""+backgroundRowId));
    if (cursor != null) {
      if (cursor.moveToFirst()) {
        while (!cursor.isAfterLast()) {
          int ic_id = cursor.getInt(cursor.getColumnIndex(LinesCP.img_credit_id));
          Cursor icCursor = queryForImageCreditCursor(ic_id);
          if (icCursor != null) {
            if (icCursor.moveToFirst()) {
              Pair<Integer, ImageCredit> imageCPair = ImageCredit.extractImageCredit(icCursor);
              ImageCredit imageC = imageCPair.second;
              String englishChanges = getString(cursor, LinesCP.changes_english);
              String spanishChanges = getString(cursor, LinesCP.changes_spanish);
              attributions.add(new Attribution(englishChanges, spanishChanges, imageC));
            }
            icCursor.close();
          }
          cursor.moveToNext();
        }
      }
      cursor.close();
    }
    return attributions;
  }

  //Checked by inspection.
  public int retrieveSceneRowId (String sceneName) {
    Cursor sceneCursor = shortQuery(LinesCP.sceneTableUri,
                                    new String[] {BaseColumns._ID},
                                    Ez.where(LinesCP.scene_name, sceneName));
    int sceneRowID = -1;
    if (sceneCursor != null && sceneCursor.moveToFirst())
      sceneRowID = getInt(sceneCursor, BaseColumns._ID);
    if (sceneCursor != null)
      sceneCursor.close();
    return sceneRowID;
  }

  //Checked by inspection.
  public HashMap<String, String> retrieveVoiceAttr (int sceneRowId) {
    Cursor sceneCursor = shortQuery(LinesCP.sceneTableUri,
                                    new String[] {LinesCP.voice_attr_eng, LinesCP.voice_attr_spn},
                                    Ez.where(BaseColumns._ID, ""+sceneRowId));
    HashMap<String, String> voiceAttrs = new HashMap<>();
    if (sceneCursor != null && sceneCursor.moveToFirst()) {
      voiceAttrs.put(LinesCP.voice_attr_eng, getString(sceneCursor, LinesCP.voice_attr_eng));
      voiceAttrs.put(LinesCP.voice_attr_spn, getString(sceneCursor, LinesCP.voice_attr_spn));
    }
    if (sceneCursor != null)
      sceneCursor.close();
    return voiceAttrs;
  }

  public HashMap<Integer, Integer> retrieveBGRowIDsAndNumCorrPagesFrScene(Iterable<Integer> pageIDs) {
    HashMap<Integer, Integer> backgroundIDsToNumOfPages = new HashMap<>();
    if (!pageIDs.iterator().hasNext())
      return backgroundIDsToNumOfPages;
    Cursor cursor = shortQuery(LinesCP.pageTableUri,
                               new String[] {LinesCP.background_id},
                               Ez.orWhere(BaseColumns._ID, pageIDs));
    if (cursor != null) {
      if (cursor.moveToFirst()) {
        while (!cursor.isAfterLast()) {
          int bgID = getInt(cursor, LinesCP.background_id);
          if (backgroundIDsToNumOfPages.containsKey(bgID)) {
            backgroundIDsToNumOfPages.put(bgID, (backgroundIDsToNumOfPages.get(bgID) + 1));
          }
          else {
            backgroundIDsToNumOfPages.put(bgID, 1);
          }
          cursor.moveToNext();
        }
      }
      cursor.close();
    }
    return backgroundIDsToNumOfPages;
  }

  //Checked by inspection.
  public Set<Integer> retrieveHintRowIDsFromPages (Iterable<Integer> pageIDs) {
    HashSet<Integer> hash = new HashSet<>();
    if (!pageIDs.iterator().hasNext())
      return hash;
    Cursor cursor = shortQuery(LinesCP.hintTableUri,
                               new String[] {BaseColumns._ID},
                               Ez.orWhere(LinesCP.page_id, pageIDs));
    if (cursor != null && cursor.moveToFirst()) {
      while(!cursor.isAfterLast()) {
        hash.add(getInt(cursor, BaseColumns._ID));
        cursor.moveToNext();
      }
    }
    if (cursor != null)
      cursor.close();
    return hash;
  }

  //Checked by inspection.
  public Set<Integer> retrieveReplyRowIDsFromPages (Iterable<Integer> pageIDs) {
    HashSet<Integer> hash = new HashSet<>();
    if (!pageIDs.iterator().hasNext())
      return hash;
    Cursor cursor = shortQuery(LinesCP.replyTableUri,
                               new String[] {BaseColumns._ID},
                               Ez.orWhere(LinesCP.page_id, pageIDs));
    if (cursor != null && cursor.moveToFirst()) {
      while(!cursor.isAfterLast()) {
        hash.add(getInt(cursor, BaseColumns._ID));
        cursor.moveToNext();
      }
    }
    if (cursor != null)
      cursor.close();
    return hash;
  }

  //Checked by inspection.
  public HashMap<Integer, ArrayList<Leader>> retrieveHintsFromSceneId (int sceneRowID) {
    HashMap<Integer, ArrayList<Leader>> hintsHash = new HashMap<Integer, ArrayList<Leader>>();
    Cursor cr = shortQuery(LinesCP.hintTableUri,
                           null,
                           Ez.where(LinesCP.scene_id, "" + sceneRowID));
    if (cr != null && cr.moveToFirst()) {
      while(!cr.isAfterLast()) {
        Hint hint = Hint.extractHint(cr);
        int pageId = getInt(cr, LinesCP.page_id);
        if (!hintsHash.containsKey(pageId)) {
          ArrayList<Leader> hints = new ArrayList<>();
          hints.add(hint);
          hintsHash.put(pageId, hints);
        }
        else {
          ArrayList<Leader> hints = hintsHash.get(pageId);
          hints.add(hint);
        }
        cr.moveToNext();
      }
    }
    if (cr != null)
      cr.close();
    return hintsHash;
  }

  //Checked by inspection.
  public HashMap<Integer, ArrayList<Matchable>> retrieveRepliesFromSceneId (int sceneRowID) {
    HashMap<Integer, ArrayList<Matchable>> repliesHash = new HashMap<>();
    Cursor cr = shortQuery(LinesCP.replyTableUri,
                           null,
                           Ez.where(LinesCP.scene_id, "" + sceneRowID));
    if (cr != null && cr.moveToFirst()) {
      while(!cr.isAfterLast()) {
        Reply reply = Reply.extractReply(cr);
        int pageId = getInt(cr, LinesCP.page_id);
        if (!repliesHash.containsKey(pageId)) {
          ArrayList<Matchable> replies = new ArrayList<>();
          replies.add(reply);
          repliesHash.put(pageId, replies);
        }
        else {
          ArrayList<Matchable> replies = repliesHash.get(pageId);
          replies.add(reply);
        }
        cr.moveToNext();
      }
    }
    if (cr != null)
      cr.close();
    return repliesHash;
  }

  //Checked by inspection.
  public int retrieveNumOfPagesUsingBackground (int backgroundRowID) {
    Cursor cursor = shortQuery(LinesCP.pageTableUri,
                               new String[] {BaseColumns._ID},
                               Ez.where(LinesCP.background_id, "" + backgroundRowID));
    int numOfPages = 0;
    if (cursor != null) {
      numOfPages = cursor.getCount();
      cursor.close();
    }
    return numOfPages;
  }

  //Checked by inspection.
  public int retrieveNumOfAttributionsUsingImageCredit (int imcreditRowID) {
    Cursor cursor = shortQuery(LinesCP.attributionTableUri,
                               new String[] {BaseColumns._ID},
                               Ez.where(LinesCP.img_credit_id, "" + imcreditRowID));
    int numOfAttrs = 0;
    if (cursor != null) {
      numOfAttrs = cursor.getCount();
      cursor.close();
    }
    return numOfAttrs;
  }

  //Checked by inspection.
  public Collection<String> retrieveCompletedScenes () {
    Cursor cursor = shortQuery(LinesCP.sceneTableUri,
                               new String[] {LinesCP.scene_name},
                               Ez.where(LinesCP.finished, ""+1));
    Collection<String> scenes = new HashSet<>();
    if (cursor != null && cursor.moveToFirst()) {
      while (!cursor.isAfterLast()) {
        scenes.add(getString(cursor, LinesCP.scene_name));
        cursor.moveToNext();
      }
    }
    if (cursor != null)
      cursor.close();
    return scenes;
  }

  //Checked by inspection.
  public Collection<String> retrieveActiveScenes () {
    Cursor cursor = shortQuery(LinesCP.sceneTableUri,
                               new String[] {LinesCP.scene_name},
                               Ez.where(LinesCP.active, ""+1));
    Collection<String> scenes = new HashSet<>();
    if (cursor != null && cursor.moveToFirst()) {
      while (!cursor.isAfterLast()) {
        scenes.add(getString(cursor,LinesCP.scene_name));
        cursor.moveToNext();
      }
    }
    if (cursor != null)
      cursor.close();
    return scenes;
  }

  //Checked by inspection.
  private Cursor queryForImageCreditCursor (String imageInfoName) {
    return shortQuery(LinesCP.imgCreditUri,
                      null,
                      Ez.where(LinesCP.image_info_name, imageInfoName));
  }

  //Checked by inspection.
  private Cursor queryForImageCreditCursor (int rowId) {
    return shortQuery(LinesCP.imgCreditUri,
                      null,
                      Ez.where(BaseColumns._ID, ""+rowId));
  }

  //Checked by inspection.
  private HashSet<Integer> getIntegersFromColumn (Cursor cursor, String columnName) {
    HashSet<Integer> integers = new HashSet<>();
    if (cursor != null) {
      if (cursor.moveToFirst()) {
        while (!cursor.isAfterLast()) {
          integers.add(getInt(cursor, columnName));
          cursor.moveToNext();
        }
      }
      cursor.close();
    }
    return integers;
  }

  // Made public to make Resolver Tests easier to read.
  public Cursor shortQuery (Uri uri, String[] columns, String selection) {
    return cr.query(uri, columns, selection, null, null);
  }

  private int shortDelete (Uri uri, int rowID) {
    String whereClause = Ez.where(BaseColumns._ID, "" + rowID);
    return cr.delete(uri, whereClause, null);
  }

  private int getInt (Cursor cursor, String columnName) {
    return cursor.getInt(cursor.getColumnIndex(columnName));
  }

  private String getString (Cursor cursor, String columnName) {
    return cursor.getString(cursor.getColumnIndex(columnName));
  }
}