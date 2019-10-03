package com.olfybsppa.inglesaventurero.stageactivity.dialog;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
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
import android.widget.RadioGroup;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.start.SettingsActivity;

public class LinesShownDialog extends DialogFragment {

  public static String CURR_SPN_SHOWN_H = "CURR_SPANISH_SHOWN_H";
  public static String CURR_SPN_SHOWN_R = "CURR_SPANISH_SHOWN_R";
	private static String IN_ENGLISH = "IN_ENGLISH_BOOLEAN";
	private boolean inEnglish = false;	

  private TextView pageTitle;
  private TextView choose0HView;
  private TextView choose1HView;
  private TextView choose2HView;
  private TextView choose3HView;
  private TextView choose0RView;
  private TextView choose1RView;
  private TextView choose2RView;
  private TextView choose3RView;
  private TextView linesShownTitleH;
  private TextView linesShownTitleR;
  private Button   languageButton;
  private int      currSpanShownH = 3;
  private int      currSpanShownR = 3;

  public static LinesShownDialog newInstance (Bundle bundle) {
    LinesShownDialog bD = new LinesShownDialog();
    bD.setArguments(bundle);
    return bD;
  }

  @Override
  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setStyle(DialogFragment.STYLE_NO_TITLE, getTheme());
    Bundle bundle = (savedInstanceState == null) ? getArguments() : savedInstanceState;
    inEnglish = bundle.getBoolean(IN_ENGLISH);
    currSpanShownH = bundle.getInt(CURR_SPN_SHOWN_H);
    currSpanShownR = bundle.getInt(CURR_SPN_SHOWN_R);
  }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_lines_shown, container, false);
    pageTitle = getTextView(v, R.id.title);
    choose0HView = getTextView(v, R.id.spn_shown_h_0);
    choose1HView = getTextView(v, R.id.spn_shown_h_1);
    choose2HView = getTextView(v, R.id.spn_shown_h_2);
    choose3HView = getTextView(v, R.id.spn_shown_h_3);
    choose0RView = getTextView(v, R.id.spn_shown_r_0);
    choose1RView = getTextView(v, R.id.spn_shown_r_1);
    choose2RView = getTextView(v, R.id.spn_shown_r_2);
    choose3RView = getTextView(v, R.id.spn_shown_r_3);
    linesShownTitleH = getTextView(v, R.id.lines_shown_h_title);
    linesShownTitleR = getTextView(v, R.id.lines_shown_r_title);

    final RadioGroup radioGroupH = (RadioGroup)v.findViewById(R.id.radio_group_spn_shown_h);
    radioGroupH.check(radioGroupH.getChildAt(currSpanShownH).getId());
    radioGroupH.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        View radioButtonH = radioGroupH.findViewById(checkedId);
        currSpanShownH = radioGroupH.indexOfChild(radioButtonH);
      }
    });

    final RadioGroup radioGroupR = (RadioGroup)v.findViewById(R.id.radio_group_spn_shown_r);
    radioGroupR.check(radioGroupR.getChildAt(currSpanShownR).getId());
    radioGroupR.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        View radioButtonR = radioGroupR.findViewById(checkedId);
        currSpanShownR = radioGroupR.indexOfChild(radioButtonR);
      }
    });

    Button okButton = (Button)v.findViewById(R.id.btn_ok);
    okButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        SharedPreferences sharedPreferences =
          PreferenceManager.getDefaultSharedPreferences(LinesShownDialog.this.getActivity());
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putInt(SettingsActivity.SPN_SHOWN_H, currSpanShownH);
        prefEditor.putInt(SettingsActivity.SPN_SHOWN_R, currSpanShownR);
        prefEditor.commit();
        LinesShownDialog.this.dismiss();
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
    outState.putInt(CURR_SPN_SHOWN_R, this.currSpanShownR);
	}

	private TextView getTextView (View v, int RId) {
    return (TextView)v.findViewById(RId);
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
    pageTitle.setText(getResources().getString(R.string.lines_shown_eng));
    choose0HView.setText(getResources().getString(R.string.lines_shown_h_0_eng));
    choose1HView.setText(getResources().getString(R.string.lines_shown_h_1_eng));
    choose2HView.setText(getResources().getString(R.string.lines_shown_h_2_eng));
    choose3HView.setText(getResources().getString(R.string.lines_shown_h_3_eng));
    choose0RView.setText(getResources().getString(R.string.lines_shown_r_0_eng));
    choose1RView.setText(getResources().getString(R.string.lines_shown_r_1_eng));
    choose2RView.setText(getResources().getString(R.string.lines_shown_r_2_eng));
    choose3RView.setText(getResources().getString(R.string.lines_shown_r_3_eng));
    String linesShownHString = getResources().getString(R.string.lines_shown_h_title_eng);
    SpannableString ssH = new SpannableString(linesShownHString);
    setColor(ssH, 37, 44, R.color.hint_text_eng);
    setBold(ssH, 37, 44);
    linesShownTitleH.setText(ssH);
    String linesShownRString = getResources().getString(R.string.lines_shown_r_title_eng);
    SpannableString ssR = new SpannableString(linesShownRString);
    setColor(ssR, 31, 44, R.color.reply_text_eng);
    setBold(ssR, 31, 44);
    linesShownTitleR.setText(ssR);
    languageButton.setText(getResources().getString(R.string.enEspanol));
  }

  private void setSpanishText() {
    pageTitle.setText(getResources().getString(R.string.lines_shown_spn));
    choose0HView.setText(getResources().getString(R.string.lines_shown_h_0_spn));
    choose1HView.setText(getResources().getString(R.string.lines_shown_h_1_spn));
    choose2HView.setText(getResources().getString(R.string.lines_shown_h_2_spn));
    choose3HView.setText(getResources().getString(R.string.lines_shown_h_3_spn));
    choose0RView.setText(getResources().getString(R.string.lines_shown_r_0_spn));
    choose1RView.setText(getResources().getString(R.string.lines_shown_r_1_spn));
    choose2RView.setText(getResources().getString(R.string.lines_shown_r_2_spn));
    choose3RView.setText(getResources().getString(R.string.lines_shown_r_3_spn));
    String linesShownHString = getResources().getString(R.string.lines_shown_h_title_spn);
    SpannableString ssH = new SpannableString(linesShownHString);
    setColor(ssH, 48, 72, R.color.hint_text_eng);
    setBold(ssH, 48, 72);
    linesShownTitleH.setText(ssH);
    String linesShownRString = getResources().getString(R.string.lines_shown_r_title_spn);
    SpannableString ssR = new SpannableString(linesShownRString);
    setColor(ssR, 37, 69, R.color.reply_text_eng);
    setBold(ssR, 37, 69);
    linesShownTitleR.setText(ssR);
    languageButton.setText(getResources().getString(R.string.inEnglish));
  }

  private void setText() {
    if (inEnglish)
      setEnglishText();
    else
      setSpanishText();
  }
}