package com.olfybsppa.inglesaventurero.stageactivity;


import com.olfybsppa.inglesaventurero.stageactivity.collectors.Page;

import java.util.ArrayList;
import java.util.List;

public class CollFragUpdater implements CollViewUpdater {

  private ColloquyFrGetter frGetter;

  public CollFragUpdater(ColloquyFrGetter getter) {
    this.frGetter = getter;
  }

  @Override
  public void match(Page page, ArrayList<String> lines) {
    ColloquyFragment colloquyFragment = frGetter.getColloquyFragment(page.getName());
    ColloquyFragment currFragment     = frGetter.getCurrFragment();
    if (null != colloquyFragment && colloquyFragment == currFragment)
      colloquyFragment.match(lines);
  }

  @Override
  public void hilightWords(int pageName, int groupId, int position) {
    ColloquyFragment colloquyFragment = frGetter.getColloquyFragment(pageName);
    if (null != colloquyFragment) {
      colloquyFragment.scrollAndHilightWords(groupId, position, true);
    }
  }

  @Override
  public void clearHilights(int pageName) {
    ColloquyFragment colloquyFragment = frGetter.getColloquyFragment(pageName);
    if (colloquyFragment != null)
      colloquyFragment.clearHilights();
  }

  @Override
  public void clearHilights(int pageName, int position) {
    ColloquyFragment colloquyFragment = frGetter.getColloquyFragment(pageName);
    if (colloquyFragment != null) {
      colloquyFragment.clearHilights(pageName, position);
    }
  }

  @Override
  public void flash(int groupId, int waitTime) {
    ColloquyFragment colloquyFragment = frGetter.getCurrFragment();
    if (null != colloquyFragment)
      colloquyFragment.scrollAndFlashNextReplies(groupId, waitTime, true);
  }

  @Override
  public void clearMatches(int pageName) {
    ColloquyFragment colloquyFragment = frGetter.getColloquyFragment(pageName);
    if (null != colloquyFragment) {
      colloquyFragment.clearMatchesForInUse();
      List<ColloquyFragment> fragments =
        frGetter.getColloquyFragmentsGreaterThan(colloquyFragment.getCurrPosition());
      for (ColloquyFragment fragment :fragments) {
        fragment.clearMatches();
      }
    }
  }

  @Override
  public void scrollTo(int pageName, int groupID, int scrollTime) {
    ColloquyFragment colloquyFragment = frGetter.getColloquyFragment(pageName);
    ColloquyFragment currFragment     = frGetter.getCurrFragment();
    if (null != colloquyFragment && colloquyFragment == currFragment)
      colloquyFragment.scrollTo(groupID, scrollTime);
  }
}
