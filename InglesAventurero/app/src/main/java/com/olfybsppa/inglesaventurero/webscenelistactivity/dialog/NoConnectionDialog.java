package com.olfybsppa.inglesaventurero.webscenelistactivity.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.dialogs.DialogDoneListener;

//TODO add english text to this dialog.
public class NoConnectionDialog extends DialogFragment {
  protected int requestCode;
  protected DialogDoneListener listener;
  private static String REQUEST_CODE = "REQUEST_CODE";
  private static String IN_ENGLISH   = "IN_ENGLISH";
  private boolean  inEnglish = false;
  private TextView titleView;
  private TextView messageView;
  private Button   languageButton;

  public static NoConnectionDialog newInstance (Bundle bundle) {
    NoConnectionDialog ncD = new NoConnectionDialog();
    if (bundle == null) {
      ncD.setArguments(new Bundle());
    }
    else {
      ncD.setArguments(bundle);
    }
    return ncD;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, getTheme());

    Bundle args = (null != savedInstanceState)? savedInstanceState : getArguments();
    if (args.containsKey(REQUEST_CODE))
      requestCode = args.getInt(REQUEST_CODE);
    else
      requestCode = this.getTargetRequestCode();
    inEnglish = args.getBoolean(IN_ENGLISH);
  }

  protected void setDialogDoneListener() {
    Fragment fragment = getTargetFragment();
    if ( null == fragment ) {
      listener = (DialogDoneListener)this.getActivity();
    }
    else {
      listener = (DialogDoneListener)fragment;
    }
  }

  @Override
  public View onCreateView (LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.dialog_notification, container, false);
    titleView   = (TextView)v.findViewById(R.id.title);
    messageView = (TextView)v.findViewById(R.id.notification_message);
    Button okButton = (Button)v.findViewById(R.id.btn_ok);
    okButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        setDialogDoneListener();
        listener.notifyDialogDone(requestCode, false, NoConnectionDialog.this.getArguments());
        NoConnectionDialog.this.dismiss();
      }
    });

    languageButton = (Button)v.findViewById(R.id.btn_language);
    languageButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        inEnglish = !inEnglish;
        setText();
      }
    });

    setText();
    return v;
  }

  @Override
  public void onCancel(DialogInterface dialog) {
    setDialogDoneListener();
    listener.notifyDialogDone(requestCode, true, NoConnectionDialog.this.getArguments());
  }

  @Override
  public void onSaveInstanceState (Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(REQUEST_CODE, this.requestCode);
    outState.putBoolean(IN_ENGLISH, inEnglish);
  }

  private void setEnglishText () {
    titleView.setText(getResources().getString(R.string.internet_down_eng));
    messageView.setText(getResources().getString(R.string.appsbyflo_error_eng));
    languageButton.setText(getResources().getString(R.string.enEspanol));
  }

  private void setSpanishText () {
    titleView.setText(getResources().getString(R.string.internet_down_spn));
    messageView.setText(getResources().getString(R.string.appsbyflo_error_spn));
    languageButton.setText(getResources().getString(R.string.inEnglish));
  }

  private void setText() {
    if (inEnglish)
      setEnglishText();
    else
      setSpanishText();
  }
}
