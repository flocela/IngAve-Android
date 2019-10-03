package com.olfybsppa.inglesaventurero.start;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.olfybsppa.inglesaventurero.R;


public class SettingsActivity extends Activity {
  public static String DOWNLOAD_FIRST_SCENE_PROMPT = "download_first_scene_prompt";
  public static String DOWNLOAD_INSTRUCTIONS = "download_instructions_prompt";
  public static String START_SCENE_INSTRUCTIONS = "start_scene_instructions";
  public static String OPTIONS_PROMPT = "options_prompt";
  public static String WEB_ACTIVITY_TOAST = "web_activity_difficulty_toast";
  public static String SPN_SHOWN_H = "lines_shown_h";
  public static String SPN_SHOWN_R = "lines_shown_r";
  public static String WAIT_TIMES = "wait_times_key";
  public static String NUM_OF_ACTIVE_SCENES = "num_of_active_scenes_key";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getFragmentManager().beginTransaction()
      .replace(android.R.id.content, new SettingsFragment())
      .commit();
  }

  public static class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.preferences);
    }

  }
}
