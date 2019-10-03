package com.olfybsppa.inglesaventurero.nonUIFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.olfybsppa.inglesaventurero.listeners.WorkProgressListener;

public abstract class WorkerFragment extends Fragment {
  protected boolean mReady = false;
  protected boolean mQuitting = false;
  protected String info = null;
  protected String exceptionString;

  protected final Thread mThread = new Thread() {
    @Override
    public void run () {
      synchronized (this) {
        while(!mReady) {
          if (mQuitting) {
            return;
          }
          try {
            wait();
          }catch (InterruptedException e) {}
        }
      }
      work();
      if (workIsDone()) {
        synchronized (this) {
          if (mReady && !mQuitting) {
            ((WorkProgressListener)getActivity()).
              notifyProgress(getTag(), false, 100, info);
          }
        }
      }
    }
  };

  protected abstract boolean work();
  protected abstract boolean workIsDone();

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    toDoOnActivityCreated(savedInstanceState);
    synchronized (mThread) {
      mReady = true;
      mThread.notify();
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    mThread.start();
  }

  @Override
  public void onDestroy() {
    synchronized (mThread) {
      mReady = false;
      mQuitting = true;
      mThread.notify();
    }
    super.onDestroy();
  }

  @Override
  public void onDetach() {
    synchronized (mThread) {
      mReady = false;
      mThread.notify();
    }
    super.onDetach();
  }

  @Override
  public void onResume() {
    super.onResume();
    if (workIsDone()) {
      ((WorkProgressListener)getActivity()).
        notifyProgress(getTag(), false, 100, info);
    }
    else if (exceptionString != null) {
      ((WorkProgressListener)getActivity()).
        notifyProgress(getTag(), false, -1, exceptionString);
    }
  }

  protected abstract void toDoOnActivityCreated (Bundle savedInstanceState);

  protected void notifyWorkProgressListener (String tag,
                                             boolean isCancelled,
                                             int percentDone,
                                             String info) {
    synchronized (mThread) {
      if (mReady && !mQuitting) {
        ((WorkProgressListener)getActivity())
          .notifyProgress(tag, isCancelled, percentDone, info);
      }
    }
  }
}
