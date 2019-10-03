package com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers;

import android.content.ContentValues;
import android.provider.BaseColumns;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.ImageCredit;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.utils.Ez;
import com.olfybsppa.inglesaventurero.utils.Pair;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

public class AttributionsResolver {
  private ResolverWrapper rw;

  public AttributionsResolver(ResolverWrapper rw) {
    this.rw = rw;
  }
  /**
   *   This will delete existing attributions associated
   * with this background that are not contained in newAttributionsHash.
   *   When an attribution is deleted, ImageCreditResolver.clearImageCredit() is called
   * on its image credit. clearImageCredit() only deletes the image credit if no other
   * attributes are pointing to it.
   */
  public void setAttributions (Integer bkgdID, HashSet<Attribution> newAttributionsHash) {
    LinkedList<Pair<Integer, Attribution>> existingAttrList = rw.retrieveAttributions(bkgdID);
    for(Pair<Integer,Attribution> existingAttrPair : existingAttrList) {
      Attribution existingAttr = existingAttrPair.second;
      if (!newAttributionsHash.contains(existingAttr)) {
        deleteAttribution(existingAttrPair.first);
      }
    }
    HashSet<Attribution> setOfExistingAttrs = teaseOutAttributions(existingAttrList);
    for (Attribution newAttribution : newAttributionsHash) {
      if (!setOfExistingAttrs.contains(newAttribution)) {
        insertAttributionIncludingImageCredit(newAttribution, bkgdID);
      }
    }
  }

  public void deleteAttribution (int attributionRowId) {
    int imageCreditID = rw.retrieveImageCreditRowId(attributionRowId);
    rw.deleteAttribution(attributionRowId);
    ImageCreditResolver imageCreditResolver = new ImageCreditResolver(rw);
    imageCreditResolver.clearImageCredit(imageCreditID);
  }

  public void deleteAttributions (Collection<Integer> attributionIDs) {
    Collection<Integer> imageCreditIds = rw.retreiveImageCreditRowIds(attributionIDs);
    rw.deleteAttributions(attributionIDs);
    ImageCreditResolver imResolver = new ImageCreditResolver(rw);
    for (Integer imID : imageCreditIds) {
      imResolver.clearImageCredit(imID);
    }
  }

  private HashSet<Attribution>
    teaseOutAttributions(LinkedList<Pair<Integer, Attribution>> pairs) {
      HashSet<Attribution> hash = new HashSet<>();
      for (Pair<Integer, Attribution> pair : pairs) {
        hash.add(pair.second);
      }
      return hash;
    }


  private int insertAttributionIncludingImageCredit(Attribution attribution, int backgroundId) {
    String imageInfoName = attribution.getImageInfoName();
    ImageCredit icFromAttr = attribution.constructImageCredit();
    Pair<Integer,ImageCredit> cpImageCreditPair = rw.retrieveImageCreditWithID(imageInfoName);
    int icRowId = cpImageCreditPair.first;
    if (icRowId == -1) { //image credit is not in ContentProvider
      icRowId = rw.insertImageCredit(icFromAttr.getContentValues());
    }
    else if (!icFromAttr.equals(cpImageCreditPair.second)) { //image credit needs to updated.
      rw.updateImageCredit(icFromAttr, Ez.where(BaseColumns._ID, "" + cpImageCreditPair.first));
    }
    ContentValues cV = attribution.getContentValues();
    cV.put(LinesCP.img_credit_id, icRowId);
    cV.put(LinesCP.background_id, backgroundId);
    return rw.insertAttributionSimple(cV);
  }
}
