package com.olfybsppa.inglesaventurero.stageactivity;


import android.content.ContentResolver;
import android.os.Bundle;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.nonUIFragments.WorkerFragment;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.PageResolver;

import java.util.ArrayList;

public class ImageInfoWorkerF extends WorkerFragment {

  public static String STORY_ID = "STORY_ID";
  public static String PAGE_NAME  = "PAGE_NAME";

  private boolean      workDone = false;
  private int storyId  = -1;
  private int pageName = -1;
  private ContentResolver cr;
  private ArrayList<Attribution> attributions = new ArrayList<>();

  public static ImageInfoWorkerF newInstance (Bundle bundle) {
    ImageInfoWorkerF imageInfoWorkerF = new ImageInfoWorkerF();
    imageInfoWorkerF.setArguments(bundle);
    return imageInfoWorkerF;
  }

  @Override
  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle args = (savedInstanceState != null)? savedInstanceState : getArguments();
    this.storyId  = args.getInt(STORY_ID);
    this.pageName = args.getInt(PAGE_NAME);
  }

  @Override // Overrides WorkerFragment
  protected boolean work () {
    if (storyId != -1 && pageName != -1 && cr != null) {
      PageResolver pageResolver = new PageResolver(new ResolverWrapper(cr));
      attributions = pageResolver.retrievePictureInfo(storyId, pageName);
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

  public ArrayList<Attribution> getAttributions () {
    return attributions;
  }
}
