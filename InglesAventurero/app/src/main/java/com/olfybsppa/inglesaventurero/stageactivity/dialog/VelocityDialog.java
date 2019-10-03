package com.olfybsppa.inglesaventurero.stageactivity.dialog;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.start.SettingsActivity;

public class VelocityDialog extends DialogFragment {

  public static String CURR_VELOCITY = "CURR_VELOCITY";
	private static String IN_ENGLISH = "IN_ENGLISH_BOOLEAN";
	private boolean inEnglish = false;	

  private TextView    pageTitle;
  private TextView    velocity0View;
  private TextView    velocity1View;
  private TextView    velocity2View;
  private TextView    velocity3View;
  private TextView    velocity4View;
  private TextView    velocity5View;
  private Button      languageButton;
  private int currVelocity = 1;

  public static VelocityDialog newInstance (Bundle bundle) {
    VelocityDialog bD = new VelocityDialog();
    bD.setArguments(bundle);
    return bD;
  }

  @Override
  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setStyle(DialogFragment.STYLE_NO_TITLE, getTheme());
    Bundle bundle = (savedInstanceState == null) ? getArguments() : savedInstanceState;
    if (bundle.containsKey(IN_ENGLISH)) {
      inEnglish = bundle.getBoolean(IN_ENGLISH);
    }
    currVelocity = bundle.getInt(CURR_VELOCITY);
    if (currVelocity < 0)
      currVelocity = 2;
  }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_velocity, container, false);
    pageTitle = getTextView(v, R.id.title);
    velocity0View = getTextView(v, R.id.velocity_0);
    velocity1View = getTextView(v, R.id.velocity_1);
    velocity2View = getTextView(v, R.id.velocity_2);
    velocity3View = getTextView(v, R.id.velocity_3);
    velocity4View = getTextView(v, R.id.velocity_4);
    velocity5View = getTextView(v, R.id.velocity_5);
    final RadioGroup radioGroup = (RadioGroup)v.findViewById(R.id.radio_group_velocity);
    radioGroup.check(radioGroup.getChildAt(currVelocity).getId());
    Button okButton = (Button)v.findViewById(R.id.btn_ok);
    okButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int idx = radioGroup.indexOfChild(radioButton);
        SharedPreferences sharedPreferences =
          PreferenceManager.getDefaultSharedPreferences(VelocityDialog.this.getActivity());
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putInt(SettingsActivity.WAIT_TIMES, idx);
        prefEditor.commit();
        VelocityDialog.this.dismiss();
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
    outState.putInt(CURR_VELOCITY, this.currVelocity);
	}

	private TextView getTextView (View v, int RId) {
    return (TextView)v.findViewById(RId);
  }

  private void setText() {
    if (inEnglish)
      setEnglishText();
    else
      setSpanishText();
  }
  
  private void setEnglishText () {
    pageTitle.setText(getResources().getString(R.string.velocity_title_eng));
    velocity0View.setText(getResources().getString(R.string.velocity_0_eng));
    velocity1View.setText(getResources().getString(R.string.velocity_1_eng));
    velocity2View.setText(getResources().getString(R.string.velocity_2_eng));
    velocity3View.setText(getResources().getString(R.string.velocity_3_eng));
    velocity4View.setText(getResources().getString(R.string.velocity_4_eng));
    velocity5View.setText(getResources().getString(R.string.velocity_5_eng));
    languageButton.setText(getResources().getString(R.string.enEspanol));
  }
  
  private void setSpanishText() {
    pageTitle.setText(getResources().getString(R.string.velocity_title_spn));
    velocity0View.setText(getResources().getString(R.string.velocity_0_spn));
    velocity1View.setText(getResources().getString(R.string.velocity_1_spn));
    velocity2View.setText(getResources().getString(R.string.velocity_2_spn));
    velocity3View.setText(getResources().getString(R.string.velocity_3_spn));
    velocity4View.setText(getResources().getString(R.string.velocity_4_spn));
    velocity5View.setText(getResources().getString(R.string.velocity_5_spn));
    languageButton.setText(getResources().getString(R.string.inEnglish));
  }
}