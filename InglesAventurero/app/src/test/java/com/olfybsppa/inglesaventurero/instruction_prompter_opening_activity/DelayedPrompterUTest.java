package com.olfybsppa.inglesaventurero.instruction_prompter_opening_activity;

import android.os.Bundle;
import android.os.Message;

import com.olfybsppa.inglesaventurero.openingactivity.DelayedPrompter;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DelayedPrompterUTest {

  /**
   * should call listUpdated with number of rows in list on listener when handleMessage
   * is called.
   */
  @Test
  public void testListenerIsNotified () {
    DelayedPrompter.Listener mockListener = mock(DelayedPrompter.Listener.class);
    Message mockMessage = mock(Message.class);
    Bundle  mockBundle  = mock(Bundle.class);
    when(mockMessage.getData()).thenReturn(mockBundle);
    when(mockBundle.getInt(DelayedPrompter.NUM_OF_ROWS_IN_LIST)).thenReturn(13);

    DelayedPrompter promptHandler = new DelayedPrompter(mockListener);

    promptHandler.handleMessage(mockMessage);

    verify(mockListener).listUpdated(13);
  }


}