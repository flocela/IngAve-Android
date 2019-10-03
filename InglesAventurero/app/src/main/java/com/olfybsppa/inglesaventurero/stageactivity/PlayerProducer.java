package com.olfybsppa.inglesaventurero.stageactivity;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.olfybsppa.inglesaventurero.R;

import java.io.File;

public class PlayerProducer {
  private AppCompatActivity activity;
  private String story;
  private DialogPresenter notifier;
  private String playerTag;

  public PlayerProducer (AppCompatActivity activity,
                         String playerTag,
                         String story,
                         DialogPresenter notifier) {
    this.activity = activity;
    this.playerTag = playerTag;
    this.story = story;
    this.notifier = notifier;
  }

  public static boolean hasFile (Activity activity, String story) {
    File dir = activity.getDir(activity.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
    String fullMP3Name = dir.getAbsolutePath() + "/" + story + ".mp4a";
    File file = new File(fullMP3Name);
    return file.exists();
  }

  PlayerInterface producePlayer () {
    if (story.equals(StageActivity.ESPRESSO_STORY)) {
      return new EspressoPlayer(notifier, playerTag);
    }
    else if (story.equals(StageActivity.TEST_STORY)) {
      return new TestPlayer(activity, 1000, playerTag);
    }
    else if (story.equals(StageActivity.SILENT_STORY)) {
      return new SilentPlayer(activity, 500, playerTag);
    }
    else {
      File dir = activity.getDir(activity.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
      String fullMP3Name = dir.getAbsolutePath() + "/" + story + ".m4a";
      File file = new File(fullMP3Name);
      if (!file.exists())
        return null;
      PlayerFragment playerFrag = PlayerFragment.newInstance(story);
      FragmentManager fm = activity.getSupportFragmentManager();
      PlayerFragment foundFragment = (PlayerFragment)fm.findFragmentByTag(playerTag);
      if (null == foundFragment) {
        fm.beginTransaction().add(playerFrag, playerTag).commitAllowingStateLoss();
        return new Player(playerFrag, playerTag);
      }
      return new Player(foundFragment, playerTag);
    }
  }
}