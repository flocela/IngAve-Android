package com.olfybsppa.inglesaventurero.openingactivity;

import android.support.v4.app.DialogFragment;

import com.olfybsppa.inglesaventurero.openingactivity.dialogs.DownloadFirstSceneDialog;
import com.olfybsppa.inglesaventurero.openingactivity.dialogs.DownloadFirstSceneInstructionsDialog;
import com.olfybsppa.inglesaventurero.openingactivity.dialogs.DownloadSecondSceneInstructionsDialog;
import com.olfybsppa.inglesaventurero.openingactivity.dialogs.StartSceneTitleInstructionsDialog;
import com.olfybsppa.inglesaventurero.start.SettingsActivity;

import java.util.Map;

/**
 * Given a list view and SharedPreferences, will
 * decide which prompt should be showing right now. Bases prompt
 * on number of items in view and which prompts have already been
 * confirmed (based on SharedPreferences).
 * This class is very closely associated with MainActivity because
 * PromptDownloadFirstSongDialog calls requestShowDownloadList() on
 * MainActivity.
 *
 * Prompts that show based on the main list view are
 * 2. Start playing a song instruction.
 * 3. Download another song instruction.
 *
 */
public class PromptCoordinator {

  public Map preferences;
  public int numRows;

  public PromptCoordinator(final Map preferences, int numRows) {
    this.preferences = preferences;
    this.numRows = numRows;
  }

  public DialogFragment getCurrPromptDialog () {
    Boolean downloadFirstScenePrompt = (Boolean)preferences.get(SettingsActivity.DOWNLOAD_FIRST_SCENE_PROMPT);
    Boolean showDownloadInstructions = (Boolean)preferences.get(SettingsActivity.DOWNLOAD_INSTRUCTIONS);
    Boolean startSceneInstruction = (Boolean)preferences.get(SettingsActivity.START_SCENE_INSTRUCTIONS);
    if (downloadFirstScenePrompt && 0 == numRows) {
      return DownloadFirstSceneDialog.newInstance(OpeningActivity.REQUEST_DOWNLOAD_LIST);
    }
    else if (downloadFirstScenePrompt == false && startSceneInstruction && 1==numRows) {
      return StartSceneTitleInstructionsDialog.newInstance();
    }
    else if (numRows==0) {
      return DownloadFirstSceneInstructionsDialog.newInstance();
    }
    else if (showDownloadInstructions && numRows>0){
      return DownloadSecondSceneInstructionsDialog.newInstance();
    }
    return null;
  }
}
