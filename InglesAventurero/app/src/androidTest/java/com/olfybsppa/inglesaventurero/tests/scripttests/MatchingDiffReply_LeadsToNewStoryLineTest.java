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
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.checkHintTitleMatches;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickOnReply;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickScrollGroupID;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.waitToStart;

@RunWith(AndroidJUnit4.class)
public class MatchingDiffReply_LeadsToNewStoryLineTest {
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
  public void matchingDiffReplyInSameSetMovesToNewStoryLine () {
    Activity activity = startActivity();
    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    //Correctly matching GroupID:1 with Position 1.
    checkHintTitleMatches("Page:0 GroupID:0");
    clickOnReply("Page:0 GroupID:1 Position:1");

    checkHintTitleMatches("Page:8 GroupID:0");
    onView(isRoot()).perform(swipeRight());

    //Correctly matching GroupID:1 again, this time with Position: 2
    clickScrollGroupID("Page:0 GroupID:1 Position:1");
    onView(isRoot()).perform(swipeUp());
    clickOnReply("Page:0 GroupID:1 Position:2");

    //Moves to correct page.
    checkHintTitleMatches("Page:1 GroupID:0");
    //Stays on new page.
    waitToStart(2000);
    checkHintTitleMatches("Page:1 GroupID:0");

    onView(isRoot()).perform(swipeLeft());
    onView(isRoot()).perform(swipeLeft());
    onView(isRoot()).perform(swipeLeft());

    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeLeft());

    //Stays on new path
    checkHintTitleMatches("Page:1 GroupID:0");
  }

  private StageActivity startActivity() {
    Intent intent = new Intent();
    intent.putExtra(StageActivity.STORY, StageActivity.SILENT_STORY);
    return mActivityRule.launchActivity(intent);
  }

  public void waitForIdle(long millis) {
    final long startTime = System.currentTimeMillis();
    final long endTime = startTime + millis;
    do {}
    while (System.currentTimeMillis() < endTime);
  }
}