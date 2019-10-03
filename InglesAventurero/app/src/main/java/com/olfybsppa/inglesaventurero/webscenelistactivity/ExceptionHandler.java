package com.olfybsppa.inglesaventurero.webscenelistactivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.olfybsppa.inglesaventurero.dialogs.ExceptionDialog;

public class ExceptionHandler extends Handler {

  private Listener listener;
  private String TAG = "tag";
  public static String EXCEPTION_OCCURRED = "EXCEPTION_OCCURRED";
  public static String IO_EXCEPTION_OCCURRED = "IO_EXCEPTION_OCCURRED";

  public ExceptionHandler (Listener listener) {
    this.listener = listener;
  }

  @Override
  public void handleMessage (Message msg) {
    Bundle msgBundle = msg.getData();
    if (msgBundle.containsKey(IO_EXCEPTION_OCCURRED)) {
      listener.showIOExceptionDialog(msgBundle);
    }
    else {
      listener.showExceptionDialog(msgBundle);
    }
  }

  public boolean sendExceptionMessage (Bundle errorBundle) {
    Message message = this.obtainMessage();
    message.setData(errorBundle);
    return this.sendMessage(message);
  }

  public boolean exceptionOccurred (String exceptionMessage) {
    Message message = this.obtainMessage();
    Bundle bundle = new Bundle();
    bundle.putString(ExceptionDialog.MESSAGE_ENG, exceptionMessage);
    bundle.putString(ExceptionDialog.MESSAGE_SPN, exceptionMessage);
    if (exceptionMessage.contains("IOException")) {
      bundle.putBoolean(IO_EXCEPTION_OCCURRED, true);
    }
    message.setData(bundle);
    sendMessage(message);
    return true;
  }

  public interface Listener {
    void showExceptionDialog(Bundle errorBundle);
    void showIOExceptionDialog(Bundle errorBundle);
  }

}
