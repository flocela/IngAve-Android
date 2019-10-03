package com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers;

import android.content.ContentValues;
import android.provider.BaseColumns;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.collectors.Background;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.utils.Ez;
import com.olfybsppa.inglesaventurero.utils.Pair;

import java.util.HashSet;

public class BackgroundResolver {
  private ResolverWrapper rw;

  public BackgroundResolver(ResolverWrapper rw) {
    this.rw = rw;
  }

  /*
   *   If the background with same name already exists in the CP, it will be updated with
   * content from bg (currently only content is filename).
   *   If background already exists it has its own Attributes. This method calls
   * AttributionsResolver.setAttributions() which deletes existing attributes that are
   * not contained in the new HashSet setOfAttributes. contains() is determined by
   * Attribute.equals(). It must have the same changesMade and imageCredit.
   */
  public int insertBkgdWithAttributions(Background bg,
                                        HashSet<Attribution> setOfAttributes) {
    if (bg == null || setOfAttributes == null || setOfAttributes.size() == 0) {
      return -1;
    }
    Pair<Integer, Background> backgroundPair = rw.retrieveBackground(bg.getBackgroundName());
    int bkgdID = backgroundPair.first;
    if (backgroundPair.first == -1) {
      bkgdID = importBackground(bg);
    }
    else if (!backgroundPair.second.equals(bg)){
      rw.updateBackground(bg, Ez.where(BaseColumns._ID, ""+bkgdID));
    }
    AttributionsResolver attributionsResolver = new AttributionsResolver(rw);
    attributionsResolver.setAttributions(bkgdID, setOfAttributes);
    return bkgdID;
  }

  public int retreiveNumOfPagesUsingBackground(int bkgdRowID) {
    return rw.retrieveNumOfPagesUsingBackground(bkgdRowID);
  }

  private int importBackground (Background background) {
    ContentValues backgroundCV = background.getContentValues();
    return rw.insertBackground(backgroundCV);
  }
}
