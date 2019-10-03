package com.olfybsppa.inglesaventurero.tests.scripttests;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.stageactivity.StageActivity;
import com.olfybsppa.inglesaventurero.start.SettingsActivity;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickOnReply;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickScrollGroupID;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class BasicSwipingTest {
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
  public void threeSwipes () {
    Activity activity = startActivity();

    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    onView(allOf(withId(R.id.h_engl_phrase),withText("Page:0 GroupID:0"))).
      check(matches(isCompletelyDisplayed()));

    onView(isRoot()).perform(swipeLeft());

    onView(allOf(withId(R.id.h_engl_phrase),withText("Page:8 GroupID:0"))).
      check(matches(isCompletelyDisplayed()));

    onView(isRoot()).perform(swipeLeft());

    onView(allOf(withId(R.id.h_engl_phrase),withText("Page:9 GroupID:0"))).
      check(matches(isCompletelyDisplayed()));

    onView(isRoot()).perform(swipeLeft());

    onView(allOf(withId(R.id.h_engl_phrase),withText("Page:10 GroupID:0"))).
      check(matches(isCompletelyDisplayed()));
  }

  @Test
  public void swipePastEndAndBeginningOfScript_GoesNoWhere() {
    Activity activity = startActivity();
    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    checkHintTitleMatches("Page:0 GroupID:0");
    clickOnReply("Page:0 GroupID:1 Position:1");
    checkHintTitleMatches("Page:8 GroupID:0");
    onView(isRoot()).perform(swipeRight());
    clickScrollGroupID("Page:0 GroupID:1 Position:1");
    onView(isRoot()).perform(swipeUp());
    clickOnReply("Page:0 GroupID:1 Position:2");
    checkHintTitleMatches("Page:1 GroupID:0");

    onView(isRoot()).perform(swipeLeft());
    onView(isRoot()).perform(swipeLeft());
    onView(isRoot()).perform(swipeLeft());

    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeRight());

    onView(isRoot()).perform(swipeLeft());
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

  private void checkHintTitleMatches(String title) {
    onView(isRoot()).perform(match(allOf(isCompletelyDisplayed(),
      withId(R.id.h_span_phrase),
      withText(title)), 2000));
  }

  private void checkReplyTitleMatches(String title) {
    onView(isRoot()).perform(match(allOf(isCompletelyDisplayed(),
      withId(R.id.r_span_phrase),
      withText(title)), 1000));
  }

  //an example of match()
  //onView(isRoot())
  //.perform(match(allOf(isDisplayed(), withText("apple 0b")),1500));
  public static ViewAction match(final Matcher<View> matcher, final long millis) {
    return new ViewAction() {
      @Override
      public Matcher<View> getConstraints() {
        return isRoot(); // I only work on the root view!
      }

      @Override
      public String getDescription() {
        return "wait for view with id <" + matcher.toString() + "> during " + millis + " millis.";
      }

      @Override
      public void perform(final UiController uiController, final View view) {
        uiController.loopMainThreadUntilIdle();
        final long startTime = System.currentTimeMillis();
        final long endTime = startTime + millis;
        do {
          for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
            if (matcher.matches(child)) {
              return;
            }
          }
          uiController.loopMainThreadForAtLeast(500);
        }
        while (System.currentTimeMillis() < endTime);

        throw new PerformException.Builder() // timeout happens
          .withActionDescription(this.getDescription())
          .withViewDescription(HumanReadables.describe(view))
          .withCause(new TimeoutException())
          .build();
      }
    };
  }
}