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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.checkHintTitleMatches;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickOnReply;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickOnWrongReply;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickScrollGroupID;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.performClick;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class IncorrectOnMatchedPageTest {
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
   * Match page 0. Scroll back to page 0, click on wrong response. Should result in
   * dialog showing “Incorrect response, but you already matched this page, so just
   * scroll to next page.”
   */
  @Test
  public void receivedIncorrectResponsesOnAlreadyMatchedPage_ShowYouAlreadyAnsweredThisDialog () {
    Activity activity = startActivity();

    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    //Correctly match Page:0.
    checkHintTitleMatches("Page:0 GroupID:0");
    clickOnReply("Page:0 GroupID:1 Position:1");

    checkHintTitleMatches("Page:8 GroupID:0");
    waitToStart(1000);
    onView(isRoot()).perform(click());
    onView(isRoot()).perform(swipeRight());

    //Incorrect answer on Page:0 GroupID:1 Position:1,
    // which is the reply that has already been answered correctly.
    checkHintTitleMatches("Page:0 GroupID:0");
    clickOnWrongReply("Page:0 GroupID:1 Position:1");

    //Show PageMatchedDialog text
    onView(isRoot()).perform(TestHelper.match(allOf(withId(R.id.incorrect_response),
                                         withText("Page:0 GroupID:1 Position:1 wrong"),
                                         isCompletelyDisplayed()),
                                   1000));
    onView(isRoot()).perform(TestHelper.match(allOf(withText(R.string.already_completed_page_spn),
                                         isCompletelyDisplayed()),
                                    1000));

    onView(isRoot()).perform(performClick(allOf(withText("Cerrar"),
                                                isCompletelyDisplayed()),
                                          1000));
    waitToStart(500);

    //Incorrect answer on Page:0 GroupID:1 Position:2. GroupID:1 has already been answered
    // correctly using Position:1 above.
    checkHintTitleMatches("Page:0 GroupID:0");
    clickScrollGroupID("Page:0 GroupID:1 Position:1");
    clickOnWrongReply("Page:0 GroupID:1 Position:2");

    //Show PageMatchedDialog text
    onView(isRoot()).perform(TestHelper.match(allOf(withId(R.id.incorrect_response),
                                         withText("Page:0 GroupID:1 Position:2 wrong"),
                                         isCompletelyDisplayed()),
                                   1000));
    onView(isRoot()).perform(TestHelper.match(allOf(withText(R.string.already_completed_page_spn),
                                         isCompletelyDisplayed()),
                                   1000));
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