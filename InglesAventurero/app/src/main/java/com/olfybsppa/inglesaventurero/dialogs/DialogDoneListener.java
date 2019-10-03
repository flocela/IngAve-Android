package com.olfybsppa.inglesaventurero.dialogs;

import android.os.Bundle;

public interface DialogDoneListener {

	public void notifyDialogDone(int requestCode,
                               boolean isCancelled,
                               Bundle returnedBundle);
	
}
