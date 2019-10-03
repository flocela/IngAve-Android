package com.olfybsppa.inglesaventurero.dialogs;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * SimpleDialog means there is no title,
 * the theme is not specified
 * Also note, no requestCode is saved.
 */
public class SimpleDialog extends DialogFragment {
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);	
		this.setStyle(DialogFragment.STYLE_NO_TITLE, getTheme());		
	}
}