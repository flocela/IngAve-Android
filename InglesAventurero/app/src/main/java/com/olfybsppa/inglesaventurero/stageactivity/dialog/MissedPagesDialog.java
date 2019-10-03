package com.olfybsppa.inglesaventurero.stageactivity.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;

public class MissedPagesDialog extends DialogFragment {

	private static String IN_ENGLISH = "IN_ENGLISH_BOOLEAN";
	private boolean  inEnglish = false;
	private Button   okButton;
  private Button   languageButton;
  private TextView titleView;
  private TextView missedPageView;

  public static MissedPagesDialog newInstance (Bundle bundle) {
    if (bundle == null)
      bundle = new Bundle();
    MissedPagesDialog dialog = new MissedPagesDialog();
    dialog.setArguments(bundle);
    return dialog;
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
		View v = inflater.inflate(R.layout.dialog_short_message, container, false);
    titleView = (TextView)v.findViewById(R.id.title);
    missedPageView = (TextView)v.findViewById(R.id.short_message);
    okButton = (Button)v.findViewById(R.id.btn_ok);
    okButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        MissedPagesDialog.this.dismiss();
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
	}

  private void setEnglishText () {
    titleView.setText(getResources().getString(R.string.missed_pages_title_eng));
    missedPageView.setText(getResources().getString(R.string.missed_pages_eng));
    languageButton.setText(getResources().getString(R.string.enEspanol));
  }

  private void setSpanishText () {
    titleView.setText(getResources().getString(R.string.missed_pages_title_spn));
    missedPageView.setText(getResources().getString(R.string.missed_pages_spn));
    languageButton.setText(getResources().getString(R.string.inEnglish));
  }

  private void setText() {
    if (inEnglish)
      setEnglishText();
    else
      setSpanishText();
  }
}
