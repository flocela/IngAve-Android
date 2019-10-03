package com.olfybsppa.inglesaventurero.tests.scripttests;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.view.ViewPager;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.stageactivity.StageActivity;
import com.olfybsppa.inglesaventurero.start.SettingsActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickEnglish;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickEspanol;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickOk;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickScrollGroupID;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.match;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.performClick;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.waitToStart;
import static org.hamcrest.core.AllOf.allOf;

public class LineButtonsTest {
  private ViewPagerIdlingResource idlingResource;
  Intent intent;
  SharedPreferences.Editor prefEditor;
  @Rule
  public ActivityTestRule<StageActivity> mActivityRule
    = new IntentsTestRule(StageActivity.class, true, false);

  @Before
  public void setup() {
    IdlingPolicies.setMasterPolicyTimeout(60, TimeUnit.SECONDS);
    IdlingPolicies.setIdlingResourceTimeout(26, TimeUnit.SECONDS);
    intent = new Intent();
    Context context = getInstrumentation().getTargetContext();
    prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    prefEditor.putBoolean(SettingsActivity.OPTIONS_PROMPT, false);
    prefEditor.putInt(SettingsActivity.WAIT_TIMES, -1);
    prefEditor.commit();
  }

  @Test
  public void pressExplantionButtonForHintAndDialogShows() {
    Activity activity = startActivity();
    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    waitToStart(1000);
    clickOk(); //Close hint is playing dialog

    waitToStart(500);
    TestHelper.checkHintTitleMatches("Page:0 GroupID:0");
    onView(isRoot()).
      perform(performClick(allOf(isDescendantOfA(withChild(withText("Page:0 GroupID:0"))),
        withId(R.id.h_explanation)), 1000));
    onView(isRoot()).perform(match(allOf(withText(R.string.lineExplanationTitleSpn),
      withId(R.id.title),
      isCompletelyDisplayed()), 1000));
    onView(isRoot()).perform(match(allOf(withText("spn_explanation apple 0d"),
      withId(R.id.explanation)), 1000));
    waitToStart(500);

    clickEnglish();
    onView(isRoot()).perform(match(allOf(withText(R.string.lineExplanationTitleEng),
      withId(R.id.title),
      isCompletelyDisplayed()), 1000));
    onView(isRoot()).perform(match(allOf(withText("eng_explanation apple 0d"),
      withId(R.id.explanation)), 1000));

    clickEspanol();
    onView(isRoot()).perform(match(allOf(withText(R.string.lineExplanationTitleSpn),
      withId(R.id.title),
      isCompletelyDisplayed()), 1000));
    onView(isRoot()).perform(match(allOf(withText("spn_explanation apple 0d"),
      withId(R.id.explanation)), 1000));
  }

  @Test
  public void pressPlaySlowOnHintAndSlowHintPlays() {
    Activity activity = startActivity();
    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    waitToStart(1000);
    clickOk(); //Close hint is playing dialog

    onView(isRoot()).
      perform(performClick(allOf(isDescendantOfA(withChild(withText("Page:0 GroupID:0"))),
                                                           withId(R.id.slow_play)), 1000));
    TestHelper.checkHintPlaying("12 for: 1");
  }

  @Test
  public void pressReplayOnHintAndHintReplays() {
    Activity activity = startActivity();
    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    waitToStart(1000);
    clickOk(); //Close hint is playing dialog

    onView(isRoot()).
      perform(performClick(allOf(isDescendantOfA(withChild(withText("Page:0 GroupID:0"))),
                                 withId(R.id.replay)), 1000));
    TestHelper.checkHintPlaying("10 for: 1");
  }

  @Test
  public void pressExplanationButtonOnReplyAndExplanationDialogShows() {
    Activity activity = startActivity();
    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    waitToStart(1000);
    clickOk(); //Close hint is playing dialog

    waitToStart(500);
    TestHelper.checkHintTitleMatches("Page:0 GroupID:0");
    clickScrollGroupID("Page:0 GroupID:1 Position:1");
    onView(isRoot()).
      perform(performClick(allOf(isDescendantOfA(withChild(withText("Page:0 GroupID:1 Position:2"))),
                                 withId(R.id.r_explanation)), 1000));
    onView(isRoot()).perform(match(allOf(withText(R.string.lineExplanationTitleSpn),
                                         withId(R.id.title),
                                         isCompletelyDisplayed()), 1000));
    onView(isRoot()).perform(match(allOf(withText("spn_explanation apple 2"),
                                         withId(R.id.explanation)), 1000));
    waitToStart(500);

    clickEnglish();
    onView(isRoot()).perform(match(allOf(withText(R.string.lineExplanationTitleEng),
                                         withId(R.id.title),
                                         isCompletelyDisplayed()), 1000));
    onView(isRoot()).perform(match(allOf(withText("eng_explanation apple 2"),
                                         withId(R.id.explanation)), 1000));
  }

  @Test
  public void pressPlaySlowOnReplyAndSlowReplyPlays() {
    Activity activity = startActivity();
    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    waitToStart(1000);
    clickOk(); //Close hint is playing dialog

    TestHelper.checkHintTitleMatches("Page:0 GroupID:0");
    clickScrollGroupID("Page:0 GroupID:1 Position:1");
    onView(isRoot()).
      perform(match(allOf(isDescendantOfA(withChild(withText("Page:0 GroupID:1 Position:2"))),
                          withId(R.id.r_play_slow)), 1000));
    onView(isRoot()).
      perform(performClick(allOf(isDescendantOfA(withChild(withText("Page:0 GroupID:1 Position:2"))),
                                 withId(R.id.r_play_slow)), 1000));
    TestHelper.checkHintPlaying("20 for: 1");
  }

  @Test
  public void pressReplayOnReplyReplaysReply() {
    Activity activity = startActivity();
    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    waitToStart(1000);
    clickOk(); //Close hint is playing dialog

    TestHelper.checkHintTitleMatches("Page:0 GroupID:0");
    clickScrollGroupID("Page:0 GroupID:1 Position:1");
    onView(isRoot()).
      perform(performClick(allOf(isDescendantOfA(withChild(withText("Page:0 GroupID:1 Position:2"))),
        withId(R.id.r_play_again)), 1000));
    TestHelper.checkHintPlaying("18 for: 1");
  }

  private StageActivity startActivity() {
    intent.putExtra(StageActivity.STORY, StageActivity.ESPRESSO_STORY);
    return mActivityRule.launchActivity(intent);
  }

}
