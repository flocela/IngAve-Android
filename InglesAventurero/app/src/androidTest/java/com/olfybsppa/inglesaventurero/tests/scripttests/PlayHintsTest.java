package com.olfybsppa.inglesaventurero.tests.scripttests;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.ViewPager;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.stageactivity.StageActivity;
import com.olfybsppa.inglesaventurero.start.SettingsActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.checkHintPlaying;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.checkHintTitleMatches;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickOk;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickOnReply;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickScrollGroupID;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.waitToStart;

@RunWith(AndroidJUnit4.class)
public class PlayHintsTest {
  private ViewPagerIdlingResource idlingResource;
  Intent intent;
  SharedPreferences.Editor prefEditor;

  @Rule
  public IntentsTestRule<StageActivity> mActivityRule =
    new IntentsTestRule(StageActivity.class, true, false);

  @Before
  public void setUp() {
    intent = new Intent();
    Context context = getInstrumentation().getTargetContext();
    prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    prefEditor.putBoolean(SettingsActivity.OPTIONS_PROMPT, false);
    prefEditor.putInt(SettingsActivity.WAIT_TIMES, -1);
    prefEditor.commit();
  }

  @After
  public void tearDownIdlingResource () {
    unregisterIdlingResources(idlingResource);
  }

  @Test
  public void playsHintAtPosition0 () {
    Activity activity = startActivity();

    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    checkHintPlaying("10 for: 1");
    clickOk();

    checkHintTitleMatches("Page:0 GroupID:0");
  }

  @Test
  public void playsHintsAtPosition0And1() {
    Activity activity = startActivity();

    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    clickOk();
    checkHintTitleMatches("Page:0 GroupID:0");
    clickScrollGroupID("Page:0 GroupID:1 Position:1");
    clickOnReply("Page:0 GroupID:1 Position:2");

    clickOk();
    checkHintTitleMatches("Page:1 GroupID:0");
    clickScrollGroupID("Page:1 GroupID:1 Position:1");
    clickOnReply("Page:1 GroupID:1 Position:2");

    clickOk();
    checkHintTitleMatches("Page:2 GroupID:0");
    clickOnReply("Page:2 GroupID:1 Position:1");

    //Click correct reply to get to Page 11.
    clickOk();
    onView(isRoot()).perform(swipeUp());
    clickScrollGroupID("Page:2 GroupID:3 Position:4");
    clickOnReply("Page:2 GroupID:3 Position:5");

    //Playing Hint at Pos_0
    checkHintPlaying("270 for: 1");
    clickOk();

    //Playing Hint at Pos_1
    waitToStart(1000); //Wait for dialog to dismiss.
    checkHintPlaying("280 for: 1");
    clickOk();
    onView(isRoot()).perform(swipeDown());
    checkHintTitleMatches("Page:11 GroupID:0");
  }
  
  public void waitForDialog(long millis) {
    final long startTime = System.currentTimeMillis();
    final long endTime = startTime + millis;
    do {}
    while (System.currentTimeMillis() < endTime);
  }

  private StageActivity startActivity() {
    Intent intent = new Intent();
    intent.putExtra(StageActivity.STORY, StageActivity.ESPRESSO_STORY);
    return mActivityRule.launchActivity(intent);
  }
}