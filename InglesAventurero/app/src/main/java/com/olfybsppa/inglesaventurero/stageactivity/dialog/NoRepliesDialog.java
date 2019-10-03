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

public class NoRepliesDialog extends DialogFragment {

	private static String inEnglishBoolean = "IN_ENGLISH_BOOLEAN";
	private boolean inEnglish = false;	
	private Button okButton;

  private TextView titleView;
  private TextView messageTV;

  /*
    Needs an ArrayList<String> with key CorrectionDialog.CORRECT_RESPONSES
    a String with key CorrectionDialog.INCORRECT_RESPONSE
  */
  public static NoRepliesDialog newInstance (Bundle bundle) {
    NoRepliesDialog bD = new NoRepliesDialog();
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
		View v = inflater.inflate(R.layout.dialog_short_message, container, false);
    this.titleView = (TextView)v.findViewById(R.id.title);
    this.messageTV = (TextView)v.findViewById(R.id.short_message);
    setTitlesAndLabelViews();

		okButton = (Button)v.findViewById(R.id.btn_ok);
		okButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				NoRepliesDialog.this.dismiss();
			}
		});
		
		Button languageButton = (Button)v.findViewById(R.id.btn_language);    
		if (NoRepliesDialog.this.inEnglish) {
		  languageButton.setText("Español");
		  okButton.setText("Close");
    }
    else {
      languageButton.setText("English");
      okButton.setText(getString(R.string.close_spn));
    }
		languageButton.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        NoRepliesDialog.this.inEnglish = !NoRepliesDialog.this.inEnglish;
        if (NoRepliesDialog.this.inEnglish) {
          ((Button)v).setText("Español");
          NoRepliesDialog.this.okButton.setText("Close");
        }
        else {
          ((Button)v).setText("English");
          NoRepliesDialog.this.okButton.setText(getString(R.string.close_spn));
        }
        setTitlesAndLabelViews();
      }
    });

		return v;
	}

  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBoolean(inEnglishBoolean, this.inEnglish);
  }

  private void setTitlesAndLabelViews() {
    if (inEnglish) {
      this.titleView.setText(getResources().getString(R.string.message_eng));
      this.messageTV.setText(getResources().getString(R.string.no_reply_necessary_eng));
    }
    else {
      this.titleView.setText(getResources().getString(R.string.message_spn));
      this.messageTV.setText(getResources().getString(R.string.no_reply_necessary_spn));
    }
  }
}
