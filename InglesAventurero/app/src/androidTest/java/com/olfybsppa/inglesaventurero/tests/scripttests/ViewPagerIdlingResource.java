package com.olfybsppa.inglesaventurero.tests.scripttests;

// Heavily copied from vaughandroid
// http://stackoverflow.com/questions/31056918/wait-for-view-pager-animations-with-espresso

import android.support.test.espresso.IdlingResource;
import android.support.v4.view.ViewPager;

public class ViewPagerIdlingResource implements IdlingResource {

  private final String mName;

  private boolean mIdle = true; // Default to idle since we can't query the scroll state.

  private ResourceCallback mResourceCallback;

  private boolean anyPageScrolledHappened = false;

  public ViewPagerIdlingResource(ViewPager viewPager, String name) {
    //Log.i("ATAG", "ViewPagerIdlingResource constructor");
    viewPager.addOnPageChangeListener(new ViewPagerListener());
    mName = name;
  }

  @Override
  public String getName() {
    return mName;
  }

  @Override
  public boolean isIdleNow() {
    //Log.i("ATAG", "isIdleNow: "+mIdle);
    return mIdle;
  }

  @Override
  public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
    //Log.i("ATAG", "ViewPagerIdlingResource registerIdleTransitionCallback");
    mIdle = true;
    mResourceCallback = resourceCallback;
  }

  public void setIdle (boolean idle) {
    //Log.i("ATAG", "setIdle: false and anyPageScrolledHappened = "+anyPageScrolledHappened);
    if (!anyPageScrolledHappened && false == idle) {
      mIdle = false;
    }
  }

  private class ViewPagerListener extends ViewPager.SimpleOnPageChangeListener {

    @Override
    public void onPageScrollStateChanged(int state) {
      anyPageScrolledHappened = true;
      //Log.i("ATAG", "ViewPager OnPageScrollStateChanged: ScrollState: " + state);
      mIdle = (state == ViewPager.SCROLL_STATE_IDLE
        // Treat dragging as idle, or Espresso will block itself when swiping.
        || state == ViewPager.SCROLL_STATE_DRAGGING);
      if (mIdle && mResourceCallback != null) {
        //Log.i("ATAG", "OnTransitionToIdle");
        mResourceCallback.onTransitionToIdle();
      }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      //Log.i("ATAG", "ViewPager OnPageScrolled:           ScrollState: " + "none");
    }

    @Override
    public void onPageSelected(int position) {
      //Log.i("ATAG", "ViewPager OnPageSelected:           ScrollState: " + "none");
    }
  }
}
