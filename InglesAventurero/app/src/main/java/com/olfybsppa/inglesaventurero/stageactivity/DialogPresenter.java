package com.olfybsppa.inglesaventurero.stageactivity;


import android.support.v4.app.DialogFragment;

public interface DialogPresenter {
  void possiblyShow(DialogFragment dialog, String settingsActivityPreference);
  void showDialog(DialogFragment dialog);
}
