package com.olfybsppa.inglesaventurero.stageactivity.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;

public class VoiceAttributionsDialog extends DialogFragment {
	private static String inEnglishBoolean = "IN_ENGLISH_BOOLEAN";
	private boolean inEnglish = false;	
	private Button okButton;
  private TextView titleView;
  private TextView voiceAttrView;
  private String voiceAttrEng;
  private String voiceAttrSpn;
  public static String VOICE_ATTR_ENG = "VOICE_ATTR_ENG";
  public static String VOICE_ATTR_SPN = "VOICE_ATTR_SPN";

  public static VoiceAttributionsDialog newInstance (Bundle bundle) {
    VoiceAttributionsDialog bD = new VoiceAttributionsDialog();
    bD.setArguments(bundle);
    return bD;
  }


  @Override
  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setStyle(DialogFragment.STYLE_NO_TITLE,getTheme());
    Bundle args = (savedInstanceState != null)? savedInstanceState : getArguments();
    this.voiceAttrEng = args.getString(VOICE_ATTR_ENG);
    this.voiceAttrSpn = args.getString(VOICE_ATTR_SPN);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    if (null != savedInstanceState && savedInstanceState.containsKey(inEnglishBoolean)) {
      inEnglish = savedInstanceState.getBoolean(inEnglishBoolean);
    }

    View v = inflater.inflate(R.layout.dialog_voice_attrs, container, false);
    this.titleView     = (TextView)v.findViewById(R.id.title);
    this.voiceAttrView = (TextView)v.findViewById(R.id.info);
    setTextViews();
    okButton = (Button)v.findViewById(R.id.btn_ok);
    okButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        VoiceAttributionsDialog.this.dismiss();
      }
    });

    Button languageButton = (Button)v.findViewById(R.id.btn_language);
    if (VoiceAttributionsDialog.this.inEnglish) {
      languageButton.setText("Español");
      okButton.setText("Close");
    }
    else {
      languageButton.setText("English");
      okButton.setText(getString(R.string.close_spn));
    }
    languageButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        VoiceAttributionsDialog.this.inEnglish = !VoiceAttributionsDialog.this.inEnglish;
        if (VoiceAttributionsDialog.this.inEnglish) {
          ((Button)v).setText("Español");
          VoiceAttributionsDialog.this.okButton.setText("Close");
        }
        else {
          ((Button)v).setText("English");
          VoiceAttributionsDialog.this.okButton.setText(getString(R.string.close_spn));
        }
        setTextViews();
      }
    });

    return v;
  }

  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBoolean(inEnglishBoolean, this.inEnglish);
    outState.putString(VOICE_ATTR_ENG, this.voiceAttrEng);
    outState.putString(VOICE_ATTR_SPN, this.voiceAttrSpn);
  }

  private void setTextViews() {
    if (inEnglish) {
      this.titleView.setText(getResources().getString(R.string.voice_attributions_title_eng));
      this.voiceAttrView.setText(voiceAttrEng);
    }
    else {
      this.titleView.setText(getResources().getString(R.string.voice_attributions_title_spn));
      this.voiceAttrView.setText(voiceAttrSpn);
    }
  }
}
