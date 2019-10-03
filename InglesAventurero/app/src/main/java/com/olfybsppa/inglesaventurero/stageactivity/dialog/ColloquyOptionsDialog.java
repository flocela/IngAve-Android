package com.olfybsppa.inglesaventurero.stageactivity.dialog;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.dialogs.DialogDoneListener;
import com.olfybsppa.inglesaventurero.start.SettingsActivity;

public class ColloquyOptionsDialog extends DialogFragment {

  public static int REQUEST_CODE = 2000;

	private static String IN_ENGLISH = "IN_ENGLISH_BOOLEAN";
	private boolean inEnglish = false;	

  private TextView pageTitle;
  private TextView pageInstrIntro;
  private TextView pageOptions;
  private TextView pagePlay;
  private TextView pageMic;
  private TextView pageTurtle;
  private TextView pageExplanation;
  private TextView pageScrollGroup;
  private TextView checkBoxTextView;
  private Button   languageButton;
  private CheckBox checkBox;
  private String SHOW_AGAIN = "SHOW_AGAIN";

  /*
    Needs an ArrayList<String> with key CorrectionDialog.CORRECT_RESPONSES
    a String with key CorrectionDialog.INCORRECT_RESPONSE
  */
  public static ColloquyOptionsDialog newInstance (Bundle bundle) {
    ColloquyOptionsDialog bD = new ColloquyOptionsDialog();
    bD.setArguments(bundle);
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
	public View onCreateView(LayoutInflater inflater, ViewGroup contr, Bundle savedState) {
    View v = inflater.inflate(R.layout.dialog_colloquy_options, contr, false);
    pageTitle = getTextView(v, R.id.title);
    pageInstrIntro = getTextView(v, R.id.page_instruction_intro);
    pageOptions = getTextView(v, R.id.page_options);
    pagePlay = getTextView(v, R.id.page_play);
    pageMic = getTextView(v, R.id.page_mic);
    pageTurtle = getTextView(v, R.id.page_turtle);
    pageExplanation = getTextView(v, R.id.page_explanation);
    pageScrollGroup = getTextView(v, R.id.page_scroll_group);
    checkBoxTextView = getTextView(v, R.id.show_again_cb_text);
    checkBox = (CheckBox)v.findViewById(R.id.show_again_cb);
    if (savedState != null && savedState.containsKey(SHOW_AGAIN))
      checkBox.setChecked(savedState.getBoolean(SHOW_AGAIN));

    Button okButton = (Button)v.findViewById(R.id.btn_ok);
    okButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        if (checkBox.isChecked()) {
          SharedPreferences sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(ColloquyOptionsDialog.this.getActivity());
          SharedPreferences.Editor prefEditor = sharedPreferences.edit();
          prefEditor.putBoolean(SettingsActivity.OPTIONS_PROMPT, false);
          prefEditor.commit();
        }
        ((DialogDoneListener)getActivity()).notifyDialogDone(REQUEST_CODE, false, new Bundle());
        ColloquyOptionsDialog.this.dismiss();
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
    outState.putBoolean(SHOW_AGAIN, checkBox.isChecked());
	}

	private TextView getTextView (View v, int RId) {
    return (TextView)v.findViewById(RId);
  }

  private SpannableString newSpannableString(int id) {
    return new SpannableString(getResources().getString(id));
  }

  private void setBold (SpannableString ss, int startIndex, int endIndex) {
    ss.setSpan(new StyleSpan(Typeface.BOLD), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
  }

  private void setColor (SpannableString ss, int startIndex, int endIndex, int colorId) {
    ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this.getContext(),
      colorId)),
      startIndex,
      endIndex,
      Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
  }

  private void setEnglishText () {
    pageTitle.setText(getResources().getString(R.string.pageInstructionTitleEng));
    SpannableString ss = newSpannableString(R.string.pageInstructionIntroEng);
    setColor(ss, 23, 28, R.color.hint_text_eng);
    setBold(ss, 23, 28);
    setColor(ss, 87, 98, R.color.reply_text_eng);
    setBold(ss, 87, 98);
    pageInstrIntro.setText(ss);
    pageOptions.setText(getResources().getString(R.string.pageOptionTextEng));
    pagePlay.setText(getResources().getString(R.string.pagePlayTextEng));
    pageMic.setText(getResources().getString(R.string.pageMicTextEng));
    pageTurtle.setText(getResources().getString(R.string.pageTurtleTextEng));
    pageExplanation.setText(getResources().getString(R.string.pageExplanationTextEng));
    pageScrollGroup.setText(getResources().getString(R.string.pageScrollGroupEng));
    checkBoxTextView.setText(getResources().getString(R.string.pageInstructionLastShowEng));
    languageButton.setText(getResources().getString(R.string.enEspanol));
  }

  private void setSpanishText() {
    pageTitle.setText(getResources().getString(R.string.pageInstructionTitleSpn));
    SpannableString ss = newSpannableString(R.string.pageInstructionIntroSpn);
    setColor(ss, 25, 30, R.color.hint_text_eng);
    setBold(ss, 25, 30);
    setColor(ss, 99, 112, R.color.reply_text_eng);
    setBold(ss, 99, 112);
    pageInstrIntro.setText(ss);
    pageOptions.setText(getResources().getString(R.string.pageOptionTextSpn));
    pagePlay.setText(getResources().getString(R.string.pagePlayTextSpn));
    pageMic.setText(getResources().getString(R.string.pageMicTextSpn));
    pageTurtle.setText(getResources().getString(R.string.pageTurtleTextSpn));
    pageExplanation.setText(getResources().getString(R.string.pageExplanationTextSpn));
    pageScrollGroup.setText(getResources().getString(R.string.pageScrollGroupSpn));
    checkBoxTextView.setText(getResources().getString(R.string.pageInstructionLastShowSpn));
    languageButton.setText(getResources().getString(R.string.inEnglish));
  }

  private void setText() {
    if (inEnglish)
      setEnglishText();
    else
      setSpanishText();
  }
}
