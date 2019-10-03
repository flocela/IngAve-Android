package com.olfybsppa.inglesaventurero.instruction_prompter_opening_activity;

import android.support.v4.app.DialogFragment;

import com.olfybsppa.inglesaventurero.openingactivity.dialogs.DownloadFirstSceneDialog;
import com.olfybsppa.inglesaventurero.openingactivity.dialogs.DownloadFirstSceneInstructionsDialog;
import com.olfybsppa.inglesaventurero.openingactivity.dialogs.DownloadSecondSceneInstructionsDialog;
import com.olfybsppa.inglesaventurero.openingactivity.dialogs.StartSceneTitleInstructionsDialog;
import com.olfybsppa.inglesaventurero.openingactivity.PromptCoordinator;
import com.olfybsppa.inglesaventurero.start.SettingsActivity;

import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertTrue;

public class PromptCoordinatorUTest {

  /**
   * should be DownloadFirstSceneDialog when
   * num of rows is 0 and
   * downloadFirstScenPrompt is true.
   */
  @Test
  public void testDownloadFirstScenePrompt () {
    HashMap<String, Boolean> map = new HashMap<>();
    map.put(SettingsActivity.DOWNLOAD_FIRST_SCENE_PROMPT, true);
    map.put(SettingsActivity.DOWNLOAD_INSTRUCTIONS,       true);
    map.put(SettingsActivity.START_SCENE_INSTRUCTIONS,    true);
    int numRows = 0;

    PromptCoordinator coordinator = new PromptCoordinator(map, numRows);
    DialogFragment dialog = coordinator.getCurrPromptDialog();
    assertTrue(dialog instanceof DownloadFirstSceneDialog);
  }

  /**
   * should be StartSceneTitleInstructionsDialog when
   * num of rows is 1 and
   * downloadFirstScenPrompt is false.
   */
  @Test
  public void testStartSceneInstructionsDialog () {
    HashMap<String, Boolean> map = new HashMap<>();
    map.put(SettingsActivity.DOWNLOAD_FIRST_SCENE_PROMPT, false);
    map.put(SettingsActivity.DOWNLOAD_INSTRUCTIONS,       true);
    map.put(SettingsActivity.START_SCENE_INSTRUCTIONS,    true);
    int numRows = 1;

    PromptCoordinator coordinator = new PromptCoordinator(map, numRows);
    DialogFragment dialog = coordinator.getCurrPromptDialog();
    assertTrue(dialog instanceof StartSceneTitleInstructionsDialog);
  }

  /**
   * should be StartSceneTitleInstructionsDialog when
   * num of rows is 1 and
   * downloadFirstScenPrompt is false.
   */
  @Test
  public void testGeneralDownloadFirstSceneInstructionsDialog () {
    HashMap<String, Boolean> map = new HashMap<>();
    map.put(SettingsActivity.DOWNLOAD_FIRST_SCENE_PROMPT, false);
    map.put(SettingsActivity.DOWNLOAD_INSTRUCTIONS,       true);
    map.put(SettingsActivity.START_SCENE_INSTRUCTIONS,    true);
    int numRows = 0;

    PromptCoordinator coordinator = new PromptCoordinator(map, numRows);
    DialogFragment dialog = coordinator.getCurrPromptDialog();
    assertTrue(dialog instanceof DownloadFirstSceneInstructionsDialog);
  }

  /**
   * should be StartSceneTitleInstructionsDialog when
   * num of rows is 1 and
   * downloadFirstScenPrompt is false.
   */
  @Test
  public void testGeneralDownloadSecondSceneInstructionsDialog () {
    HashMap<String, Boolean> map = new HashMap<>();
    map.put(SettingsActivity.DOWNLOAD_FIRST_SCENE_PROMPT, false);
    map.put(SettingsActivity.DOWNLOAD_INSTRUCTIONS,       true);
    map.put(SettingsActivity.START_SCENE_INSTRUCTIONS,    false);
    int numRows = 1;

    PromptCoordinator coordinator = new PromptCoordinator(map, numRows);
    DialogFragment dialog = coordinator.getCurrPromptDialog();
    assertTrue(dialog instanceof DownloadSecondSceneInstructionsDialog);
  }

}