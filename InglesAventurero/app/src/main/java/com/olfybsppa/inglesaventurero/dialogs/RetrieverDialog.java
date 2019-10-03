package com.olfybsppa.inglesaventurero.dialogs;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.olfybsppa.inglesaventurero.utils.MaP;

/**
 * Takes care of remembering the requestcode through onSaveInstance().
 * Takes care of setting the DialogDoneListener if a TargetFragment was
 * supplied or if this dialog was started with an Activity.
 * Also specifies STYLE as STYLE_NO_TITLE
 * and theme as getTheme().
 * // TODO I don't use getArguments the bundle is empty. Maybe shouldn't be a RetrieverDialog.
 */
public abstract class RetrieverDialog extends DialogFragment {
	 protected int requestCode; 	
	 protected DialogDoneListener listener;
	 
	 protected void includeInOnSaveInstanceState(Bundle outState) {}
	 
	 @Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);		
			this.setStyle(DialogFragment.STYLE_NO_TITLE, getTheme());
			
			Bundle args = (null != savedInstanceState)? savedInstanceState : getArguments(); 
			
			requestCode = this.getTargetRequestCode();		
			if (0 == requestCode) {
			   requestCode = args.getInt(MaP.REQUEST_CODE);
			}			
		}
	 
	 protected void setDialogDoneListener() {		 
			Fragment fragment = getTargetFragment();
			if ( null == fragment ) {
				listener = (DialogDoneListener)RetrieverDialog.this.getActivity();
			}
			else {
				listener = (DialogDoneListener)fragment;
			}
	 }	
	 
	 @Override
		public void onSaveInstanceState (Bundle outState) {
			super.onSaveInstanceState(outState);
			outState.putInt(MaP.REQUEST_CODE, this.requestCode);
			includeInOnSaveInstanceState(outState);
		}	 

}
