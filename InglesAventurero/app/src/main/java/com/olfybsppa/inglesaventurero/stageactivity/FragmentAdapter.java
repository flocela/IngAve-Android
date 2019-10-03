package com.olfybsppa.inglesaventurero.stageactivity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class FragmentAdapter extends PagerAdapter {
  private static final String TAG = "FSPA";
  private static final boolean DEBUG = false;

  private final FragmentManager mFragmentManager;
  private FragmentTransaction mCurTransaction = null;

  private ArrayList<Fragment.SavedState> mSavedState = new ArrayList<Fragment.SavedState>();
  protected ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
  private Fragment mCurrentPrimaryItem = null;

  public FragmentAdapter(FragmentManager fm) {
    mFragmentManager = fm;
  }

  /**
   * Return the Fragment associated with a specified position.
   */
  public abstract Fragment getItem(int position);

  @Override
  public void startUpdate(ViewGroup container) {
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    // If we already have this item instantiated, there is nothing
    // to do.  This can happen when we are restoring the entire pager
    // from its saved state, where the fragment manager has already
    // taken care of restoring the fragments we previously had instantiated.
    if (mFragments.size() > position) {
      Fragment f = mFragments.get(position);
      if (f != null) {
        return f;
      }
    }

    if (mCurTransaction == null) {
      mCurTransaction = mFragmentManager.beginTransaction();
    }

    Fragment fragment = getItem(position);
    if (DEBUG) Log.v(TAG, "Adding item #" + position + ": f=" + fragment);
    if (mSavedState.size() > position) {
      Fragment.SavedState fss = mSavedState.get(position);
      if (fss != null) {
        fragment.setInitialSavedState(fss);
      }
    }
    while (mFragments.size() <= position) {
      mFragments.add(null);
    }
    fragment.setMenuVisibility(false);
    fragment.setUserVisibleHint(false);
    mFragments.set(position, fragment);
    mCurTransaction.add(container.getId(), fragment);

    return fragment;
  }

  @Override
  public void setPrimaryItem(ViewGroup container, int position, Object object) {
    Fragment fragment = (Fragment)object;
    if (fragment != mCurrentPrimaryItem) {
      if (mCurrentPrimaryItem != null) {
        mCurrentPrimaryItem.setMenuVisibility(false);
        mCurrentPrimaryItem.setUserVisibleHint(false);
      }
      if (fragment != null) {
        fragment.setMenuVisibility(true);
        fragment.setUserVisibleHint(true);
      }
      mCurrentPrimaryItem = fragment;
    }
  }

  @Override
  public void finishUpdate(ViewGroup container) {
    if (mCurTransaction != null) {
      mCurTransaction.commitAllowingStateLoss();
      mCurTransaction = null;
      mFragmentManager.executePendingTransactions();
    }
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return ((Fragment)object).getView() == view;
  }

  @Override
  public Parcelable saveState() {
    Bundle state = null;
    if (mSavedState.size() > 0) {
      state = new Bundle();
      Fragment.SavedState[] fss = new Fragment.SavedState[mSavedState.size()];
      mSavedState.toArray(fss);
      state.putParcelableArray("states", fss);
    }
    for (int i=0; i<mFragments.size(); i++) {
      Fragment f = mFragments.get(i);
      if (f != null && f.isAdded()) {
        if (state == null) {
          state = new Bundle();
        }
        String key = "f" + i;
        mFragmentManager.putFragment(state, key, f);
      }
    }
    return state;
  }

  @Override
  public void restoreState(Parcelable state, ClassLoader loader) {
    if (state != null) {
      Bundle bundle = (Bundle)state;
      bundle.setClassLoader(loader);
      Parcelable[] fss = bundle.getParcelableArray("states");
      mSavedState.clear();
      mFragments.clear();
      if (fss != null) {
        for (int i=0; i<fss.length; i++) {
          mSavedState.add((Fragment.SavedState)fss[i]);
        }
      }
      Iterable<String> keys = bundle.keySet();
      for (String key: keys) {
        if (key.startsWith("f")) {
          int index = Integer.parseInt(key.substring(1));
          Fragment f = mFragmentManager.getFragment(bundle, key);
          if (f != null) {
            while (mFragments.size() <= index) {
              mFragments.add(null);
            }
            f.setMenuVisibility(false);
            mFragments.set(index, f);
          } else {
            Log.w(TAG, "Bad fragment at key " + key);
          }
        }
      }
    }
  }

  @Override
  public void notifyDataSetChanged() {
    Fragment currF = mCurrentPrimaryItem;
    List<Integer> toRemove = new LinkedList<>();
    if (currF != null) {
      int currPosition = (findFragment(currF));
      if (currPosition != -1) {
        for (int ii=currPosition+1; ii<mFragments.size(); ii++) {
          Fragment existingFragment = mFragments.get(ii);
          if (existingFragment == null)
            toRemove.add(ii);
          else if(getItemPosition(existingFragment) == POSITION_NONE)
            toRemove.add(ii);
        }
        for (Integer toGo : toRemove) {
          removeFragment(toGo);
        }
      }
    }
    super.notifyDataSetChanged();
  }

  private int findFragment (Fragment fragment) {
    for (int ii=0; ii< mFragments.size(); ii++) {
      if (fragment.equals(mFragments.get(ii)))
        return ii;
    }
    return -1;
  }

  private void removeFragment (int position) {
    if (mFragments.size() <= position)
      return;
    if (mCurTransaction == null) {
      mCurTransaction = mFragmentManager.beginTransaction();
    }
    Fragment wrongFragment = mFragments.get(position);
    if (wrongFragment != null) {
      mCurTransaction.remove(wrongFragment);
      mFragments.set(position, null);
    }
    if (mSavedState.size() > position) {
      mSavedState.set(position, null);
    }
  }


  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    Fragment fragment = (Fragment) object;

    if (mCurTransaction == null) {
      mCurTransaction = mFragmentManager.beginTransaction();
    }
    if (DEBUG) Log.v(TAG, "Removing item #" + position + ": f=" + object
      + " v=" + ((Fragment)object).getView());
    while (mSavedState.size() <= position) {
      mSavedState.add(null);
    }
    int isInLineFragment = getItemPosition(object);
    if (isInLineFragment != PagerAdapter.POSITION_NONE) {
      mSavedState.set(position, fragment.isAdded()
        ? mFragmentManager.saveFragmentInstanceState(fragment) : null);
      mFragments.set(position, null);
    }
    else {
      mSavedState.set(position, null);
    }

    mCurTransaction.remove(fragment);
  }

  protected Fragment getCurrentPrimaryItem () {
    return this.mCurrentPrimaryItem;
  }


}