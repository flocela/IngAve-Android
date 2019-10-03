package com.olfybsppa.inglesaventurero.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;

import com.olfybsppa.inglesaventurero.webscenelistactivity.WebActivity;

public class ProgressDialogFragment extends DialogFragment {

  public static final String MESSAGE = "MESSAGE";
  private MessageProgressDialog progressDialog;
  private String message;

  public static ProgressDialogFragment newInstance(Bundle bundle) {
    ProgressDialogFragment fragment = new ProgressDialogFragment();
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Bundle args = null;
    if(savedInstanceState == null) {
      args = getArguments();
    }
    else {
      args = savedInstanceState;
    }
    message = (args.get(MESSAGE) == null)? "" : args.getString(MESSAGE);
    progressDialog = new MessageProgressDialog(getActivity(), message);
    progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
      @Override
      public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
          Activity activity = ProgressDialogFragment.this.getActivity();
          if (activity instanceof WebActivity) {
            ((WebActivity) activity).stopDownloadingScene();
            dismiss();
          }
          return true;
        }
        return false;
      }
    });
    return progressDialog;
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(MESSAGE, this.message);
  }

  public void setMessage (String message) {
    if (progressDialog != null) {
      progressDialog.setMessage(message);
      this.message = message;
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    progressDialog.dismiss();
  }
}
