package com.olfybsppa.inglesaventurero.stageactivity;


import android.content.ContentResolver;
import android.os.Bundle;

import com.olfybsppa.inglesaventurero.nonUIFragments.WorkerFragment;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;

import java.util.HashMap;

public class VoiceInfoWorkerF extends WorkerFragment {

  public static String STORY_ID = "STORY_ID";

  private boolean      workDone = false;
  private int storyId  = -1;
  private ContentResolver cr;
  private HashMap<String, String> voiceAttrs = new HashMap<>();

  public static VoiceInfoWorkerF newInstance (Bundle bundle) {
    VoiceInfoWorkerF imageInfoWFragment = new VoiceInfoWorkerF();
    imageInfoWFragment.setArguments(bundle);
    return imageInfoWFragment;
  }

  @Override
  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle args = (savedInstanceState != null)? savedInstanceState : getArguments();
    this.storyId  = args.getInt(STORY_ID);
  }

  @Override // Overrides WorkerFragment
  protected boolean work () {
    if (storyId != -1 && cr != null) {
      ResolverWrapper rw = new ResolverWrapper(cr);
      voiceAttrs = rw.retrieveVoiceAttr(storyId);
      synchronized (mThread) {
        if (mReady)
          notifyWorkProgressListener(getTag(), true, 100, null);
      }
    }
    return true;
  }

  @Override
  protected void toDoOnActivityCreated (Bundle savedInstanceState) {
    this.cr = getActivity().getContentResolver();
  }

  @Override
  protected boolean workIsDone() {
    return workDone;
  }

  public HashMap<String, String> getAttributions () {
    return voiceAttrs;
  }
}
