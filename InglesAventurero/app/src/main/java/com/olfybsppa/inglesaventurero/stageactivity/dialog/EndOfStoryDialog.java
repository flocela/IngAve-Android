package com.olfybsppa.inglesaventurero.stageactivity.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;

public class EndOfStoryDialog extends DialogFragment {

	private static String inEnglishBoolean = "IN_ENGLISH_BOOLEAN";
	private boolean inEnglish = false;	
	private Button okButton;
  private Button languageButton;
  private TextView titleView;
  private TextView goodJobView;

  /*
    Needs an ArrayList<String> with key CorrectionDialog.CORRECT_RESPONSES
    a String with key CorrectionDialog.INCORRECT_RESPONSE
  */
  public static EndOfStoryDialog newInstance (Bundle bundle) {
    EndOfStoryDialog bD = new EndOfStoryDialog();
    bD.setArguments(bundle);
    return bD;
  }

  @Override
  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setStyle(DialogFragment.STYLE_NO_TITLE, getTheme());
  }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
    if (savedInstanceState != null && savedInstanceState.containsKey(inEnglishBoolean)) {
      inEnglish = savedInstanceState.getBoolean(inEnglishBoolean);
    }
		View v = inflater.inflate(R.layout.dialog_short_message, container, false);
    titleView = (TextView)v.findViewById(R.id.title);
    goodJobView = (TextView)v.findViewById(R.id.short_message);
    okButton = (Button)v.findViewById(R.id.btn_ok);
    okButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        EndOfStoryDialog.this.dismiss();
      }
    });

    languageButton = (Button)v.findViewById(R.id.btn_language);
    languageButton.setOnClickListener(new View.OnClickListener() {
      @Override
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
	  outState.putBoolean(inEnglishBoolean, this.inEnglish);

	}

	private void setEnglishText() {
    titleView.setText(getResources().getString(R.string.good_job_english));
    goodJobView.setText(getResources().getString(R.string.finished_english));
    languageButton.setText(getResources().getString(R.string.enEspanol));
  }

  private void setSpanishText() {
    titleView.setText(getResources().getString(R.string.good_job_spanish));
    goodJobView.setText(getResources().getString(R.string.finished_spanish));
    languageButton.setText(getResources().getString(R.string.inEnglish));
  }

  private void setText() {
    if (inEnglish)
      setEnglishText();
    else
      setSpanishText();
  }
}
