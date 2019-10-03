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
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.checkHintPlaying;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickOk;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickOnReply;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickScrollGroupID;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class MatchPageLeadsToAlreadyMatchedPage_Test {
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

  /**
   * Match page 0 which leads to page 1. Match page 1. Go back to page 0 and match it
   * again to lead to page 1. Page 1 hint should play again.
   */
  @Test
  public void matchLeadsToAlreadyMatchedPageThatStartsWithHint_HintPlays() {
    Activity activity = startActivity();

    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    //Match Page:0 Position:2
    clickOk();
    checkHintTitleMatches("Page:0 GroupID:0");
    clickScrollGroupID("Page:0 GroupID:1 Position:1");
    clickOnReply("Page:0 GroupID:1 Position:2");

    //Match Page:1
    clickOk();
    clickOnReply("Page:1 GroupID:1 Position:1");

    //Move back to Page:0
    clickOk();
    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeRight());

    //Click to match Page:0, Page:1 should play hint.
    clickOnReply("Page:0 GroupID:1 Position:2");
    checkHintPlaying("22 for: 1");
    clickOk();
    checkHintTitleMatches("Page:1 GroupID:0");
  }

  /**
   * Match page 0 which leads to page 4, which starts with a reply. Match page 4. Scroll
   * back to page 0. Match page 0 again to lead to already matched page 4. Hint does
   * not play.
   */
  @Test
  public void matchLeadsToAlreadyMatchedPageStartingWithReply_HintDoesNotPlay() {
    Activity activity = startActivity();

    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    //Match Page:0 Position:3
    clickOk();
    checkHintTitleMatches("Page:0 GroupID:0");
    clickScrollGroupID("Page:0 GroupID:1 Position:1");
    clickScrollGroupID("Page:0 GroupID:1 Position:2");
    clickOnReply("Page:0 GroupID:1 Position:3");

    //Match Page:4
    clickOnReply("Page:4 GroupID:0 Position:0");

    //Move back to Page:0
    clickOk();
    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeRight());

    //Click Position:3 which will lead to Page 4 again. I know hint is not playing
    //because ReplyTitleMatches (Reply groupID:0 is not hidden), which means the hint
    //dialog is not in the way, which means hint is not playing.
    onView(isRoot()).perform(swipeUp());
    clickOnReply("Page:0 GroupID:1 Position:3");
    checkReplyTitleMatches("Page:4 GroupID:0 Position:0");
  }

  /**
   * Match from page 0 to page 1 to page 5. Page 5 has two hints, then 1 reply, then
   * two hints.
   */
  @Test
  public void matchLeadsToAlreadyMatchedPage_TwoHintsPlayInARow () {
    Activity activity = startActivity();

    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    //Match Page:0 Position:2
    clickOk();
    checkHintTitleMatches("Page:0 GroupID:0");
    onView(isRoot()).perform(swipeUp());
    clickScrollGroupID("Page:0 GroupID:1 Position:1");
    clickOnReply("Page:0 GroupID:1 Position:2");

    //Match Page:1 Position:3
    clickOk();
    checkHintTitleMatches("Page:1 GroupID:0");
    clickScrollGroupID("Page:1 GroupID:1 Position:1");
    clickScrollGroupID("Page:1 GroupID:1 Position:2");
    clickOnReply("Page:1 GroupID:1 Position:3");

    //Match Page 5.
    clickOk();
    clickOk();
    onView(isRoot()).perform(swipeUp());
    clickOnReply("Page:5 GroupID:2 Position:2");

    clickOk();
    clickOk();
    clickOk(); //This is on Page 25

    //Move back to Page 1.
    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeRight());

    //Now click on Page:1. To move to Page:5. I'll be checking if Page 5 Hints play.
    onView(isRoot()).perform(swipeUp());
    clickOnReply("Page:1 GroupID:1 Position:3");

    //Check Hint:0 and Hint:1 play on Page 5.
    checkHintPlaying("420 for: 1");
    clickOk();
    checkHintPlaying("430 for: 1");
    clickOk();

    //Click on Reply:2
    clickOnReply("Page:5 GroupID:2 Position:2");

    //Check Hint:3 and Hint:4 play on Page 5.
    checkHintPlaying("440 for: 1");
    clickOk();
    checkHintPlaying("450 for: 1");
    clickOk();

    //Check moves to next page, Page:25
    clickOk();
    checkHintTitleMatches("Page:25 GroupID:0");
  }

  private StageActivity startActivity() {
    Intent intent = new Intent();
    intent.putExtra(StageActivity.STORY, StageActivity.ESPRESSO_STORY);
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