package com.olfybsppa.inglesaventurero.stageactivity;


import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.start.SettingsActivity;

import java.util.Map;

public class Producer {

  Tracker         tracker;
  String          type;
  FragmentManager fragmentManager;
  ViewPager       viewPager;
  PlayerInterface player;
  DialogPresenter notifier;
  SceneCatalogue  sceneCatalogue;
  Map             map;
  Resources       resources;

  public Producer (Tracker tracker,
                   String type,
                   FragmentManager fragmentManager,
                   ViewPager viewPager,
                   PlayerInterface player,
                   DialogPresenter notifier,
                   SceneCatalogue sceneCatalogue,
                   Map prefMap,
                   Resources resources) {
    this.tracker = tracker;
    this.type = type;
    this.fragmentManager = fragmentManager;
    this.viewPager = viewPager;
    this.player = player;
    this.notifier = notifier;
    this.sceneCatalogue = sceneCatalogue;
    this.map            = prefMap;
    this.resources      = resources;
  }

  //Adds adapter to view pager and that will cause ColloquyFragments to be committed with allowingStateLoss.
  public DirectorI getDirector() {
    Repository repository = new Repository(tracker);
    StageAdapter stageAdapter = new StageAdapter(fragmentManager, repository);
    viewPager.setAdapter(stageAdapter);
    ColloquyFrGetter colloquyFrGetter = stageAdapter.getColloquyFragmentGetter();
    CollViewUpdater collViewUpdater = new CollFragUpdater(colloquyFrGetter);
    Integer velocity = resources.getInteger(R.integer.wait_times_default);
    Object value = map.get(SettingsActivity.WAIT_TIMES);
    if (!(value instanceof String)){
      velocity = (Integer)value;
    }
    int[] velocityArr = null;
    switch (velocity) {
      case -1:
        velocityArr = resources.getIntArray(R.array.testing_times);
        break;
      case 0:
        velocityArr = resources.getIntArray(R.array.wait_times_0);
        break;
      case 1:
        velocityArr = resources.getIntArray(R.array.wait_times_1);
        break;
      case 2:
        velocityArr = resources.getIntArray(R.array.wait_times_2);
        break;
      case 3:
        velocityArr = resources.getIntArray(R.array.wait_times_3);
        break;
      case 4:
        velocityArr = resources.getIntArray(R.array.wait_times_4);
        break;
      case 5:
        velocityArr = resources.getIntArray(R.array.wait_times_5);
        break;
    }
    Director director = new Director(repository,
                                     collViewUpdater,
                                     viewPager,
                                     player,
                                     notifier,
                                     sceneCatalogue,
                                     velocityArr);
    if (!(Boolean)map.get(SettingsActivity.OPTIONS_PROMPT))
      director.setDoneWithInstructions(true);
    return director;
  }
}
