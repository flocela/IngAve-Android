package com.olfybsppa.inglesaventurero.stageactivity;

import java.util.List;

public interface ColloquyFrGetter {
  ColloquyFragment       getColloquyFragment (int pageName);
  List<ColloquyFragment> getColloquyFragmentsGreaterThan(int pos);
  ColloquyFragment       getCurrFragment ();
}
