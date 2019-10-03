package com.olfybsppa.inglesaventurero.openingactivity.dialogs;


import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.dialogs.SimpleDialog;
import com.olfybsppa.inglesaventurero.start.SettingsActivity;

public class DownloadSecondSceneInstructionsDialog extends SimpleDialog {

  public static DownloadSecondSceneInstructionsDialog newInstance () {
    Bundle startingBundle = new Bundle();
    DownloadSecondSceneInstructionsDialog gD = new DownloadSecondSceneInstructionsDialog();
    gD.setArguments(startingBundle);
    return gD;
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.dialog_short_instruction, container, false);

    Drawable drawableWhite = ContextCompat.getDrawable(getContext(), R.drawable.ic_file_download_white_24dp);
    drawableWhite.setBounds(0, 0, drawableWhite.getIntrinsicWidth(), drawableWhite.getIntrinsicHeight());
    ImageSpan imgSpanWhite = new ImageSpan(drawableWhite, ImageSpan.ALIGN_BASELINE);

    Drawable drawableBlack = ContextCompat.getDrawable(getContext(), R.drawable.ic_file_download_black_24dp);
    drawableBlack.setBounds(0, 0, drawableBlack.getIntrinsicWidth(), drawableBlack.getIntrinsicHeight());
    ImageSpan imgSpanBlack = new ImageSpan(drawableBlack, ImageSpan.ALIGN_BASELINE);

    TextView title = (TextView)v.findViewById(R.id.dialog_title);
    SpannableString instructionsSpanishSpannableString = new SpannableString("Oprima         arriba, para descargar mas escenas.");
    instructionsSpanishSpannableString.setSpan(imgSpanWhite, 7, 14, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    title.setText(instructionsSpanishSpannableString);

    TextView englishInstructions = (TextView)v.findViewById(R.id.instructions_english);
    SpannableString instructionsEnglishSpannableString = new SpannableString("Press             at top, to download another scene.");
    instructionsEnglishSpannableString.setSpan(imgSpanBlack, 5, 18, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    englishInstructions.setText(instructionsEnglishSpannableString);

    Button okButton = (Button)v.findViewById(R.id.btn_ok);
    okButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        setGeneralDownloadInstructionsToFalse();
        DownloadSecondSceneInstructionsDialog.this.dismiss();
      }
    });

    return v;
  }

  private void setGeneralDownloadInstructionsToFalse() {
    SharedPreferences sharedPreferences =
      PreferenceManager.getDefaultSharedPreferences(DownloadSecondSceneInstructionsDialog.this.getActivity());
    SharedPreferences.Editor prefEditor = sharedPreferences.edit();
    prefEditor.putBoolean(SettingsActivity.DOWNLOAD_INSTRUCTIONS, false);
    prefEditor.commit();
  }
}
