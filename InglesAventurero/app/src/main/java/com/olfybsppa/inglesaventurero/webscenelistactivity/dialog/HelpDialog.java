package com.olfybsppa.inglesaventurero.webscenelistactivity.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;

public class HelpDialog extends DialogFragment {


	
	private static String IN_ENGLISH = "IN_ENGLISH_BOOLEAN";
	private boolean inEnglish = false;	

  private TextView helpTitle;
  private TextView pressRowInfo;
  private TextView difficultyInfo;
  private Button   languageButton;

  public static HelpDialog newInstance () {
    HelpDialog bD = new HelpDialog();
    return bD;
  }

  @Override
  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setStyle(DialogFragment.STYLE_NO_TITLE, getTheme());
    if (savedInstanceState != null && savedInstanceState.containsKey(IN_ENGLISH)) {
      if (savedInstanceState.getBoolean(IN_ENGLISH))
        inEnglish = true;
      else
        inEnglish = false;
    }
  }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_web_help, container, false);
    helpTitle      = getTextView(v, R.id.title);
    pressRowInfo   = getTextView(v, R.id.press_row_info);
    difficultyInfo = getTextView(v, R.id.difficulty_info);

    Button okButton = (Button)v.findViewById(R.id.btn_ok);
    okButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        HelpDialog.this.dismiss();
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
	
	public void onSaveInstanceState(Bundle outState) {
	  super.onSaveInstanceState(outState);
	  outState.putBoolean(IN_ENGLISH, this.inEnglish);
	}

	private TextView getTextView (View v, int RId) {
    return (TextView)v.findViewById(RId);
  }

  private void setEnglishText () {
    helpTitle.setText(getResources().getString(R.string.download_title_eng));
    pressRowInfo.setText(getResources().getString(R.string.download_instructions_eng));
    difficultyInfo.setText(getResources().getString(R.string.difficulty_instructions_eng));
    languageButton.setText(getResources().getString(R.string.enEspanol));
  }

  private void setSpanishText() {
    helpTitle.setText(getResources().getString(R.string.download_title_spn));
    pressRowInfo.setText(getResources().getString(R.string.download_instructions_spn));
    difficultyInfo.setText(getResources().getString(R.string.difficulty_instructions_spn));
    languageButton.setText(getResources().getString(R.string.inEnglish));
  }

  private void setText() {
    if (inEnglish)
      setEnglishText();
    else
      setSpanishText();
  }
}
