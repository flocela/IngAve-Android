package com.olfybsppa.inglesaventurero.openingactivity.dialogs;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.dialogs.RetrieverDialog;
import com.olfybsppa.inglesaventurero.start.SettingsActivity;
import com.olfybsppa.inglesaventurero.utils.MaP;

public class DownloadFirstSceneDialog extends RetrieverDialog {
  /**
   * Requires: nothing.
   *
   * Returns: same bundle that was provided. Check notifyDialogDone for second
   * argument "isCancelled". If isCancelled = false, then user confirmed download.
   * If isCancelled = true, then user did not confirm download.
   */
  public static DownloadFirstSceneDialog newInstance (int requestCode) {
    DownloadFirstSceneDialog cD = new DownloadFirstSceneDialog();
    Bundle bundle = new Bundle();
    bundle.putInt(MaP.REQUEST_CODE, requestCode);
    cD.setArguments(bundle);
    return cD;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View v = inflater.inflate(R.layout.dialog_download_first_scene, container, false);

    ImageButton okButton = (ImageButton)v.findViewById(R.id.btn_ok);

    okButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        setGeneralDownloadInstructionsToFalse();
        setDialogDoneListener();
        listener.notifyDialogDone(requestCode, false, DownloadFirstSceneDialog.this.getArguments());
        DownloadFirstSceneDialog.this.dismiss();
      }
    });

    Button cancelButton = (Button)v.findViewById(R.id.btn_cancel);

    cancelButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        setGeneralDownloadInstructionsToFalse();
        setDialogDoneListener();
        listener.notifyDialogDone(requestCode, true, DownloadFirstSceneDialog.this.getArguments());
        DownloadFirstSceneDialog.this.dismiss();
      }
    });

    return v;
  }

  private void setGeneralDownloadInstructionsToFalse() {
    SharedPreferences sharedPreferences =
      PreferenceManager.getDefaultSharedPreferences(DownloadFirstSceneDialog.this.getActivity());
    SharedPreferences.Editor prefEditor = sharedPreferences.edit();
    prefEditor.putBoolean(SettingsActivity.DOWNLOAD_FIRST_SCENE_PROMPT, false);
    prefEditor.commit();
  }

  @Override
  public void onCancel(DialogInterface dialog) {
    setGeneralDownloadInstructionsToFalse();
    setDialogDoneListener();
    listener.notifyDialogDone(requestCode, true, DownloadFirstSceneDialog.this.getArguments());
  }


}
