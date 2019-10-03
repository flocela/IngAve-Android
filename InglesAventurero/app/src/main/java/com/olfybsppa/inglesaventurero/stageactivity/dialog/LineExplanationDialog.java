package com.olfybsppa.inglesaventurero.stageactivity.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.utils.Ez;

public class LineExplanationDialog extends DialogFragment {

	private static String IN_ENGLISH = "IN_ENGLISH_BOOLEAN";
	private boolean inEnglish = false;
  private TextView title;
  private TextView explanation;
  private Button   languageButton;
  public static String LINE_EXPLANATION_ENG = "LINE_EXPLANATION_ENG";
  public static String LINE_EXPLANATION_SPN = "LINE_EXPLANATION_SPN";
  private String explanationEng;
  private String explanationSpn;

  public static LineExplanationDialog newInstance (String explanationEng, String explanationSpn) {
    LineExplanationDialog bD = new LineExplanationDialog();
    Bundle bundle = Ez.oneStringBundle(LINE_EXPLANATION_ENG, explanationEng);
    bundle.putString(LINE_EXPLANATION_SPN, explanationSpn);
    bD.setArguments(bundle);
    return bD;
  }

  @Override
  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setStyle(DialogFragment.STYLE_NO_TITLE, getTheme());
    Bundle args = (savedInstanceState == null)? getArguments() : savedInstanceState;
    inEnglish = args.getBoolean(IN_ENGLISH);
  }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
    Bundle args = (savedInstanceState == null)? getArguments() : savedInstanceState;
    explanationEng = args.getString(LINE_EXPLANATION_ENG);
    explanationSpn = args.getString(LINE_EXPLANATION_SPN);
		View v = inflater.inflate(R.layout.dialog_line_explanation, container, false);
    title = getTextView(v, R.id.title);
    explanation = getTextView(v, R.id.explanation);
    Button okButton = (Button)v.findViewById(R.id.btn_ok);
    okButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        LineExplanationDialog.this.dismiss();
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
    outState.putString(LINE_EXPLANATION_ENG, explanationEng);
    outState.putString(LINE_EXPLANATION_SPN, explanationSpn);
	}

	private TextView getTextView (View v, int RId) {
    return (TextView)v.findViewById(RId);
  }

  private void setEnglishText () {
    title.setText(getResources().getString(R.string.lineExplanationTitleEng));
    explanation.setText(explanationEng);
    languageButton.setText(getResources().getString(R.string.enEspanol));
  }

  private void setSpanishText() {
    title.setText(getResources().getString(R.string.lineExplanationTitleSpn));
    explanation.setText(explanationSpn);
    languageButton.setText(getResources().getString(R.string.inEnglish));
  }

  private void setText() {
    if (inEnglish)
      setEnglishText();
    else
      setSpanishText();
  }
}