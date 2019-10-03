package com.olfybsppa.inglesaventurero.openingactivity.dialogs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.olfybsppa.inglesaventurero.start.SettingsActivity;


public class StartSceneTitleInstructionsDialog extends TitleInstructionsDialog {

  public static StartSceneTitleInstructionsDialog newInstance () {
    StartSceneTitleInstructionsDialog ssD = new StartSceneTitleInstructionsDialog();
    Bundle bundle = new Bundle();
    bundle.putString(TitleInstructionsDialog.INSTRUCTIONS_SPANISH, "Oprima La Escena Para Empezar!");
    bundle.putString(TitleInstructionsDialog.INSTRUCTIONS_ENGLISH, "Select The Scene To Start!");
    ssD.setArguments(bundle);
    return ssD;
  }

  @Override
  public void onOkayButton() {
    setPreferenceSetting();
  }

  @Override
  public void onCancelButton() {
    setPreferenceSetting();
  }

  private void setPreferenceSetting() {
    SharedPreferences sharedPreferences =
      PreferenceManager.getDefaultSharedPreferences(StartSceneTitleInstructionsDialog.this.getActivity());
    SharedPreferences.Editor prefEditor = sharedPreferences.edit();
    prefEditor.putBoolean(SettingsActivity.START_SCENE_INSTRUCTIONS, false);
    prefEditor.commit();
  }
}
