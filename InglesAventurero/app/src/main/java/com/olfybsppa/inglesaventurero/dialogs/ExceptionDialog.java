package com.olfybsppa.inglesaventurero.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;

public class ExceptionDialog extends DialogFragment {

	public static final String TITLE_ENG   = "TITLE_ENG";
	public static final String TITLE_SPN   = "TITLE_SPN";
	public static final String MESSAGE_ENG = "MESSAGE_ENG";
	public static final String MESSAGE_SPN = "MESSAGE_SPN";
	private static final String IN_ENGLISH = "IN_ENGLISH";

	private TextView titleView;
	private TextView messageView;
	private String englishTitle;
	private String spanishTitle;
	private String englishMessage;
	private String spanishMessage;
	private Button   languageButton;
	private boolean  inEnglish;

	public static ExceptionDialog newInstance (Bundle startingBundle) {
		ExceptionDialog cD = new ExceptionDialog();
		cD.setArguments(startingBundle);
		return cD;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setStyle(DialogFragment.STYLE_NO_TITLE, getTheme());
		Bundle args = (savedInstanceState != null)? savedInstanceState : getArguments();
		if (args.getBoolean(IN_ENGLISH))
			inEnglish = true;
		else
			inEnglish = false;
		englishTitle = args.getString(TITLE_ENG);
		spanishTitle = args.getString(TITLE_SPN);
		englishMessage = args.getString(MESSAGE_ENG);
		spanishMessage = args.getString(MESSAGE_SPN);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_notification, container, false);
		
		titleView = (TextView)v.findViewById(R.id.title);
		messageView = (TextView)v.findViewById(R.id.notification_message);

		Button okButton = (Button)v.findViewById(R.id.btn_ok);
		okButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ExceptionDialog.this.dismiss();
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

	private void setText() {
		if (inEnglish)
			setEnglishText();
		else
			setSpanishText();
	}

	private void setEnglishText () {
		if (englishTitle == null)
			titleView.setText(getResources().getString(R.string.error_occurred_eng));
		else
			titleView.setText(englishTitle);
		messageView.setText(englishMessage);
		languageButton.setText(getResources().getString(R.string.enEspanol));
	}

	private void setSpanishText () {
		if (spanishTitle == null)
			titleView.setText(getResources().getString(R.string.error_occurred_spn));
		else
			titleView.setText(spanishTitle);
		messageView.setText(spanishMessage);
		languageButton.setText(getResources().getString(R.string.inEnglish));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(IN_ENGLISH, inEnglish);
		outState.putString(MESSAGE_ENG, englishMessage);
		outState.putString(MESSAGE_SPN, spanishMessage);
	}
}