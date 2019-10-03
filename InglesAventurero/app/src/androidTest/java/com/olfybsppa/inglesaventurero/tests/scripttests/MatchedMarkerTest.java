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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.checkHintTitleMatches;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.checkMarkerIsThere;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickOnReply;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickScrollGroupID;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.performClick;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class MatchedMarkerTest {
  private ViewPagerIdlingResource idlingResource;
  Intent intent;
  SharedPreferences.Editor prefEditor;

  // launch flag should be false so that it is not lazily instantiated. Instead I
  // launch the activity in startActivity().
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

  /**
   * Correctly reply to Position:1 and Position:2 in GroupID:1. Do not reply correctly
   * to Position:3. Scroll away, scroll back. Position:1 and Position:2 are marked
   * as having been matched, Position:3 is not marked.
   */
  @Test
  public void matchedMarkersInLineSet () {
    Activity activity = startActivity();

    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    checkHintTitleMatches("Page:0 GroupID:0");
    clickScrollGroupID("Page:0 GroupID:1 Position:1");
    clickOnReply("Page:0 GroupID:1 Position:2");

    onView(isRoot()).perform(swipeRight());
    clickScrollGroupID("Page:0 GroupID:1 Position:2");
    clickScrollGroupID("Page:0 GroupID:1 Position:3");
    clickScrollGroupID("Page:0 GroupID:1 Position:4");
    clickOnReply("Page:0 GroupID:1 Position:1");

    checkHintTitleMatches("Page:8 GroupID:0");
    onView(isRoot()).perform(swipeLeft());
    onView(isRoot()).perform(swipeLeft());
    onView(isRoot()).perform(swipeLeft());
    onView(isRoot()).perform(swipeLeft());
    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeRight());

    //Page:0 GroupID:1 Position:1 is marked. Page:0 GroupID:1 Position:2 is marked.
    checkMarkerIsThere("Page:0 GroupID:1 Position:1");
    clickScrollGroupID("Page:0 GroupID:1 Position:1");
    checkMarkerIsThere("Page:0 GroupID:1 Position:2");
    clickScrollGroupID("Page:0 GroupID:1 Position:2");

    //Page:0 GroupID:1 Position:3 is not marked.
    onView(isRoot()).
      perform(TestHelper.match(allOf(withId(R.id.r_matchedmarker),
                                     hasSibling(withChild(withText("Page:0 GroupID:1 Position:3"))),
                                     not(isDisplayed())),
                               1000));
  }

  /**
   * Match reply, scroll away and scroll back, reply is still marked as matched.
   */
  @Test
  public void matchedMarkerOnReply () {
    Activity activity = startActivity();

    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    checkHintTitleMatches("Page:0 GroupID:0");
    onView(isRoot()).perform(swipeLeft());
    checkHintTitleMatches("Page:8 GroupID:0");
    onView(isRoot()).
      perform(performClick(allOf(isDescendantOfA(withChild(withText("Page:8 GroupID:1 Position:1"))),
        withId(R.id.r_correct_response)), 1000));

    onView(isRoot()).perform(swipeLeft());
    onView(isRoot()).perform(swipeLeft());
    onView(isRoot()).perform(swipeLeft());
    onView(isRoot()).perform(swipeLeft());

    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeRight());

    //Page:8 GroupID:1 Position:1 is marked.
    onView(allOf(withId(R.id.r_matchedmarker),
      hasSibling(withChild(withText("Page:8 GroupID:1 Position:1"))))).check(matches(isCompletelyDisplayed()));
  }


  private StageActivity startActivity() {
    Intent intent = new Intent();
    intent.putExtra(StageActivity.STORY, StageActivity.SILENT_STORY);
    return mActivityRule.launchActivity(intent);
  }

  public void waitToStart(long millis) {
    final long startTime = System.currentTimeMillis();
    final long endTime = startTime + millis;
    do {}
    while (System.currentTimeMillis() < endTime);
  }
}