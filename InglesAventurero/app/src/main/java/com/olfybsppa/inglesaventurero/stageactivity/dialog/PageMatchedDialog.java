package com.olfybsppa.inglesaventurero.stageactivity.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;

public class PageMatchedDialog extends DialogFragment {

	private static String IN_ENGLISH = "IN_ENGLISH_BOOLEAN";
	private boolean inEnglish = false;	
	private Button okButton;
  private Button languageButton;

  private TextView titleView;
  private TextView incorrectResponseLabelView;
  private TextView incorrectResponseView;
  private TextView alreadyMatchedView;

  private String incorrectResponse;

  public static String INCORRECT_RESPONSE = "INCORRECT_RESPONSE";

  /*
    Needs an ArrayList<String> with key CorrectionDialog.CORRECT_RESPONSES
    a String with key CorrectionDialog.INCORRECT_RESPONSE
  */
  public static PageMatchedDialog newInstance (Bundle bundle) {
    PageMatchedDialog bD = new PageMatchedDialog();
    bD.setArguments(bundle);
    return bD;
  }

  @Override
  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setStyle(DialogFragment.STYLE_NO_TITLE, getTheme());
    Bundle args = (savedInstanceState != null)? savedInstanceState : getArguments();
    inEnglish = args.getBoolean(IN_ENGLISH);
  }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
    Bundle args = this.getArguments();
    incorrectResponse = args.getString(INCORRECT_RESPONSE);
	  
		View v = inflater.inflate(R.layout.dialog_all_matched, container, false);
    this.titleView = (TextView)v.findViewById(R.id.title);
    this.incorrectResponseLabelView = (TextView)v.findViewById(R.id.incorrect_response_label);
    this.incorrectResponseView = (TextView)v.findViewById(R.id.incorrect_response);
    this.incorrectResponseView.setText(this.incorrectResponse);
    this.alreadyMatchedView = (TextView)v.findViewById(R.id.already_matched);
		okButton = (Button)v.findViewById(R.id.btn_ok);
		okButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				PageMatchedDialog.this.dismiss();
			}
		});
		
		languageButton = (Button)v.findViewById(R.id.btn_language);
		languageButton.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        PageMatchedDialog.this.inEnglish = !PageMatchedDialog.this.inEnglish;
        setTitlesAndLabelViews();
      }
    });
    setTitlesAndLabelViews();
		return v;
	}

  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBoolean(IN_ENGLISH, this.inEnglish);
    outState.putString(INCORRECT_RESPONSE, incorrectResponseView.getText().toString());
  }

  private void setTitlesAndLabelViews() {
    if (inEnglish) {
      this.titleView.setText(getResources().getString(R.string.scroll_to_next_page_eng));
      this.incorrectResponseLabelView.setText(getResources().getString(R.string.understood_eng));
      this.languageButton.setText(getResources().getString(R.string.enEspanol));
      okButton.setText(getResources().getString(R.string.close_eng));
      alreadyMatchedView.setText(getResources().getString(R.string.already_completed_page_eng));
    }
    else {
      this.titleView.setText(getResources().getString(R.string.scroll_to_next_page_spn));
      this.incorrectResponseLabelView.setText(getResources().getString(R.string.understood_spn));
      languageButton.setText(getResources().getString(R.string.inEnglish));
      okButton.setText(getResources().getString(R.string.close_spn));
      alreadyMatchedView.setText(getResources().getString(R.string.already_completed_page_spn));
    }
  }
}
