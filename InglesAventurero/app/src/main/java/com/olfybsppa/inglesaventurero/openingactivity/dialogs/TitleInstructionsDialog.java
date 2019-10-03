package com.olfybsppa.inglesaventurero.openingactivity.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.dialogs.SimpleDialog;


public class TitleInstructionsDialog extends SimpleDialog {

  public static String INSTRUCTIONS_SPANISH = "INSTRUCTIONS_SPANISH";
  public static String INSTRUCTIONS_ENGLISH = "INSTRUCTIONS_ENGLISH";
  private TextView instructionsEnglishTextView;
  private TextView instructionsSpanishTextView;

  public static TitleInstructionsDialog newInstance (Bundle startingBundle) {
    TitleInstructionsDialog cD = new TitleInstructionsDialog();
    cD.setArguments(startingBundle);
    return cD;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.dialog_short_instruction, container, false);

    instructionsEnglishTextView = (TextView)v.findViewById(R.id.instructions_english);
    instructionsSpanishTextView = (TextView)v.findViewById(R.id.dialog_title);

    Bundle args = this.getArguments();
    instructionsEnglishTextView.setText(args.getString(INSTRUCTIONS_ENGLISH));
    instructionsSpanishTextView.setText(args.getString(INSTRUCTIONS_SPANISH));

    Button okButton = (Button)v.findViewById(R.id.btn_ok);

    okButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        onOkayButton();
        TitleInstructionsDialog.this.dismiss();
      }
    });

    return v;
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(INSTRUCTIONS_ENGLISH, instructionsEnglishTextView.getText().toString());
    outState.putString(INSTRUCTIONS_SPANISH, instructionsSpanishTextView.getText().toString());
  }

  public void onOkayButton() {}
  public void onCancelButton() {}
}
