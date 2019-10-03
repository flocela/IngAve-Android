package com.olfybsppa.inglesaventurero.openingactivity;

import android.os.Handler;
import android.os.Message;

import com.olfybsppa.inglesaventurero.utils.Ez;

public class DelayedPrompter extends Handler {
  private Listener listener;
  public static String NUM_OF_ROWS_IN_LIST = "NUM_OF_ROWS_IN_LIST";

  public DelayedPrompter(Listener listener) {
    this.listener = listener;
  }

  @Override
  public void handleMessage (Message msg) {
    int sentNum = msg.getData().getInt(NUM_OF_ROWS_IN_LIST);
    listener.listUpdated(sentNum);
  }

  public boolean sendPrompterMessage(int numOfRowsInList) {
    Message message = this.obtainMessage();
    message.setData(Ez.oneIntBundle(NUM_OF_ROWS_IN_LIST, numOfRowsInList));
    return this.sendMessageDelayed(message, 1000);
  }

  public interface Listener {
    void listUpdated(int sentNumOfRows);
  }
}
