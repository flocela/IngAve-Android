package com.olfybsppa.inglesaventurero.start;


import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


public abstract class SuperFragmentActivity extends AppCompatActivity {

  private DialogFragment dialogFragment;
  public static String DIALOG_FRAGMENT_TAG = "DIALOG_FRAGMENT_TAG";

  protected abstract void initStartingVariables(Bundle args);
  protected abstract void initView();

  protected String getRString (int stringID) {
    return getResources().getString(stringID);
  }

  /**
   * returns true if fragment can be commited, then commits it.
   */
  public int showDialogFragmentASL(DialogFragment fragment, String tag) {
    FragmentManager fm = this.getSupportFragmentManager();
    Fragment foundFragment = fm.findFragmentByTag(tag);
    FragmentTransaction ft = fm.beginTransaction();
    if (null != fragment && null == foundFragment) {
      ft.add(fragment, tag);
      return ft.commitAllowingStateLoss();
    }
    return -1;
  }

  @Override
  protected void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initView();
    Bundle args = savedInstanceState;
    if (args == null) {
      args = getIntent().getExtras();
    }
    if (args == null) {
      try {
        ApplicationInfo ai = getPackageManager().
          getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
        args = ai.metaData;
      } catch (PackageManager.NameNotFoundException e) {
      } catch (NullPointerException e) {
      }
    }
    initStartingVariables(args);
  }

  public void onResume () {
    super.onResume();
    if (dialogFragment != null) {
      showDialogFragmentASL(dialogFragment);
    }
    dialogFragment = null;
  }

  protected void showDialogFragmentASL(DialogFragment newFragment) {
    FragmentManager fm = this.getSupportFragmentManager();
    Fragment foundFragment = fm.findFragmentByTag(DIALOG_FRAGMENT_TAG);
    FragmentTransaction ft = fm.beginTransaction();
    try {
      if (null == foundFragment) {
        ft.add(newFragment, DIALOG_FRAGMENT_TAG);
        ft.commitAllowingStateLoss();
      }
      else if (null != foundFragment) {
        ft.remove(foundFragment);
        ft.add(newFragment, DIALOG_FRAGMENT_TAG);
        ft.commitAllowingStateLoss();
      }
    }
    catch (IllegalStateException e) {}
  }

  protected void addFragmentRemovePrevious (Fragment fragment,
                                            String tag) {
    FragmentManager fm = this.getSupportFragmentManager();
    Fragment foundFragment = fm.findFragmentByTag(tag);
    if (null != foundFragment) {
      fm.beginTransaction().remove(foundFragment).commitAllowingStateLoss();
    }
    fm.beginTransaction().add(fragment, tag).commitAllowingStateLoss();
  }

  //ASL stands for AllowingStateLoss
  protected Fragment addFragmentIfNotPresentASL (Fragment fragment,
                                                String tag) {
    FragmentManager fm = this.getSupportFragmentManager();
    Fragment foundFragment = fm.findFragmentByTag(tag);
    if (null == foundFragment) {
      fm.beginTransaction().add(fragment, tag).commitAllowingStateLoss();
      return fragment;
    }
    return foundFragment;
  }

  protected Fragment findFragByTag(String tag) {
    FragmentManager fm = this.getSupportFragmentManager();
    return fm.findFragmentByTag(tag);
  }

  protected FragmentTransaction beginTransaction() {
    FragmentManager fm = this.getSupportFragmentManager();
    return fm.beginTransaction();
  }

  protected void addFragmentIfNotAddedASL(Fragment fragment,
                                          String tag,
                                          int containerViewId) {
    FragmentManager fm = this.getSupportFragmentManager();
    Fragment foundFragment = fm.findFragmentByTag(tag);
    if (null == foundFragment) {
      fm.beginTransaction().add(containerViewId, fragment, tag).commitAllowingStateLoss();
    }
  }

  protected void removeFragmentASL (String tag) {
    FragmentManager fm = this.getSupportFragmentManager();
    Fragment fragment = findFragByTag(tag);
    if (fragment != null) {
      fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
    }
  }
}