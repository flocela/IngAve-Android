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
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.checkHintTitleMatches;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.checkReplyTitleMatches;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickOnReply;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickScrollGroupID;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class EndOfScriptTest {
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
   * Correct response on last page, when all pages have been matched, shows “You're finished message.”
   */
  @Test
  public void lastCorrectResponse_ShowEndStoryWithDialog() {
    Activity activity = startActivity();

    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    //Get to Page:17, which is a last page in script.
    checkHintTitleMatches("Page:0 GroupID:0");
    clickScrollGroupID("Page:0 GroupID:1 Position:1");
    clickOnReply("Page:0 GroupID:1 Position:2");
    checkHintTitleMatches("Page:1 GroupID:0");
    clickScrollGroupID("Page:1 GroupID:1 Position:1");
    clickOnReply("Page:1 GroupID:1 Position:2");
    checkHintTitleMatches("Page:2 GroupID:0");
    clickOnReply("Page:2 GroupID:1 Position:1");
    onView(isRoot()).perform(swipeUp());
    clickScrollGroupID("Page:2 GroupID:3 Position:4");
    clickOnReply("Page:2 GroupID:3 Position:5");
    checkHintTitleMatches("Page:11 GroupID:0");
    onView(isRoot()).perform(swipeUp());
    clickOnReply("Page:11 GroupID:2 Position:2");

    //On Page:17 click on reply
    checkHintTitleMatches("Page:17 GroupID:0");
    clickOnReply("Page:17 GroupID:1 Position:1");

    //Show End of Script dialog
    onView(isRoot()).perform(TestHelper.match(withText("Hay terminado la aventura!"), 1000)); //this is the end of page
  }

  /**
   * Page 18 group 0, position 0 is matched which leads to page 24. Scroll back to
   * page 18 and horizontal scroll to match group 0, position 1, which is the end of
   * the script. End of script dialog should show.
   */
  @Test
  public void matchingReplyEndingStoryInAReplySetThatIsAlreadyMatched_ShowEndStoryWithDialog() {
    Activity activity = startActivity();

    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    checkHintTitleMatches("Page:0 GroupID:0");
    clickScrollGroupID("Page:0 GroupID:1 Position:1");
    clickScrollGroupID("Page:0 GroupID:1 Position:2");
    clickScrollGroupID("Page:0 GroupID:1 Position:3");
    clickOnReply("Page:0 GroupID:1 Position:4");

    //Match Page:18 GroupID:0 Position:0.
    checkReplyTitleMatches("Page:18 GroupID:0 Position:0");
    clickOnReply("Page:18 GroupID:0 Position:0");

    checkHintTitleMatches("Page:24 GroupID:0");
    onView(isRoot()).perform(click());
    onView(isRoot()).perform(swipeRight());

    //Match Page:18 GroupID:0 Position:1. Same GroupID, different position.
    clickScrollGroupID("Page:18 GroupID:0 Position:0");
    clickOnReply("Page:18 GroupID:0 Position:1");

    //Moves script forward, which is end of script.
    onView(isRoot()).
      perform(TestHelper.match(allOf(withText("Hay terminado la aventura!"), isCompletelyDisplayed()), 1000));
  }

  private StageActivity startActivity() {
    intent.putExtra(StageActivity.STORY, StageActivity.SILENT_STORY);
    return mActivityRule.launchActivity(intent);
  }
}