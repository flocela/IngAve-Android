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

import java.util.ArrayList;

public class CorrectionDialog extends DialogFragment {
	private static String inEnglishBoolean = "IN_ENGLISH_BOOLEAN";
	private boolean inEnglish = false;	
	private Button okButton;

  private TextView titleView;
  private TextView incorrectResponseLabelView;
  private TextView incorrectResponseView;
  private TextView correctResponsesLabelView;
  private TextView correctResponsesView;

  private String incorrectResponse;
  private ArrayList<String> correctResponses;
  private String correctResponsesString = new String();

  public static String CORRECT_RESPONSES = "CORRECT_RESPONSES";
  public static String INCORRECT_RESPONSE = "INCORRECT_RESPONSE";

  /*
    Needs an ArrayList<String> with key CorrectionDialog.CORRECT_RESPONSES
    a String with key CorrectionDialog.INCORRECT_RESPONSE
  */
  public static CorrectionDialog newInstance (Bundle bundle) {
    CorrectionDialog bD = new CorrectionDialog();
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
    Bundle args = this.getArguments();
    incorrectResponse = args.getString(INCORRECT_RESPONSE);
    correctResponses = args.getStringArrayList(CORRECT_RESPONSES);
    StringBuilder stringBuilder = new StringBuilder(correctResponses.get(0));
    for (int ii=1; ii<correctResponses.size(); ii++) {
      stringBuilder.append("\n\n");
      stringBuilder.append(correctResponses.get(ii));
    }
    this.correctResponsesString = stringBuilder.toString();
	  if (null != savedInstanceState && savedInstanceState.containsKey(inEnglishBoolean)) {
	    inEnglish = savedInstanceState.getBoolean(inEnglishBoolean);
	  }
	  
		View v = inflater.inflate(R.layout.dialog_corrections, container, false);
    this.titleView = (TextView)v.findViewById(R.id.title);
    this.incorrectResponseLabelView = (TextView)v.findViewById(R.id.incorrect_response_label);
    this.incorrectResponseView = (TextView)v.findViewById(R.id.incorrect_response);
    this.correctResponsesLabelView = (TextView)v.findViewById(R.id.correct_responses_label);
    this.correctResponsesView = (TextView)v.findViewById(R.id.correct_responses);
    this.incorrectResponseView.setText(this.incorrectResponse);
    this.correctResponsesView.setText(this.correctResponsesString);
    setTitlesAndLabelViews();

		okButton = (Button)v.findViewById(R.id.btn_ok);
		okButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CorrectionDialog.this.dismiss();
			}
		});
		
		Button languageButton = (Button)v.findViewById(R.id.btn_language);    
		if (CorrectionDialog.this.inEnglish) {
		  languageButton.setText("Español");
		  okButton.setText("Close");
    }
    else {
      languageButton.setText("English");
      okButton.setText(getString(R.string.close_spn));
    }
		languageButton.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        CorrectionDialog.this.inEnglish = !CorrectionDialog.this.inEnglish;
        if (CorrectionDialog.this.inEnglish) {
          ((Button)v).setText("Español");
          CorrectionDialog.this.okButton.setText("Close");
        }
        else {
          ((Button)v).setText("English");
          CorrectionDialog.this.okButton.setText(getString(R.string.close_spn));
        }
        setTitlesAndLabelViews();
      }
    });

		return v;
	}

  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBoolean(inEnglishBoolean, this.inEnglish);
    outState.putString(INCORRECT_RESPONSE, this.incorrectResponse);
    outState.putStringArrayList(CORRECT_RESPONSES, this.correctResponses);
  }

  private void setTitlesAndLabelViews() {
    if (inEnglish) {
      this.titleView.setText("Not Quite Right");
      this.incorrectResponseLabelView.setText("I understood:");
      this.correctResponsesLabelView.setText("These are the correct responses:");
    }
    else {
      this.titleView.setText("No Exactamente Correcto");
      this.incorrectResponseLabelView.setText("Entendí:");
      this.correctResponsesLabelView.setText("Estas son las respuestas correctas:");
    }
  }
}
