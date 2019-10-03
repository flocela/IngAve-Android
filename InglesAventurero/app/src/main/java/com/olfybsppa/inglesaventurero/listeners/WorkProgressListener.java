package com.olfybsppa.inglesaventurero.listeners;

/**
 * Created by flo on 11/26/14.
 */
public interface WorkProgressListener {

  public void notifyProgress
    (String tag, boolean wasCancelled, int percentDone, String info);
}
