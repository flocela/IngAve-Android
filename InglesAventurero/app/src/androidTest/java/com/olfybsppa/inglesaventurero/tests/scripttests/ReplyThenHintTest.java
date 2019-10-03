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
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.checkHintPlaying;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.checkHintTitleMatches;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.checkReplyTitleMatches;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickOk;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickScrollGroupID;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.waitToStart;

@RunWith(AndroidJUnit4.class)
public class ReplyThenHintTest {
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
  public void pageDoesNotPlayHintWhenHintNotAtPosition0Test() {

    Activity activity = startActivity();

    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    TestHelper.waitToStart(1000);
    clickOk();
    checkHintTitleMatches("Page:0 GroupID:0");
    TestHelper.clickOnReply("Page:0 GroupID:1 Position:1");
    clickOk();
    checkHintTitleMatches("Page:8 GroupID:0");
    TestHelper.clickOnReply("Page:8 GroupID:1 Position:1");
    clickOk();
    checkHintTitleMatches("Page:9 GroupID:0");
    TestHelper.clickOnReply("Page:9 GroupID:1 Position:1");
    clickOk();
    checkHintTitleMatches("Page:10 GroupID:0");
    onView(isRoot()).perform(swipeUp());
    TestHelper.clickOnReply("Page:10 GroupID:1 Position:1");

    //Page 20 begins with a Reply in pos 0, and so hint does not play when it is started.
    onView(withText("114 for: 1")).check(doesNotExist());
    checkReplyTitleMatches("Page:20 GroupID:0 Position:0");
  }

  @Test
  public void hintsOnPagePlayAfterCorrectResponseOnSamePageThenLeadsToNextPageTest() {
    Activity activity = startActivity();

    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    waitToStart(1000);
    clickOk();

    checkHintTitleMatches("Page:0 GroupID:0");
    TestHelper.clickOnReply("Page:0 GroupID:1 Position:1");

    clickOk();
    checkHintTitleMatches("Page:8 GroupID:0");
    TestHelper.clickOnReply("Page:8 GroupID:1 Position:1");

    clickOk();
    checkHintTitleMatches("Page:9 GroupID:0");
    TestHelper.clickOnReply("Page:9 GroupID:1 Position:1");

    clickOk();
    onView(isRoot()).perform(swipeUp());
    checkHintTitleMatches("Page:10 GroupID:0");
    TestHelper.clickOnReply("Page:10 GroupID:1 Position:1");

    //Page 20's pos_0 reply is clicked, then it's pos_1 and pos_2 hints play.
    checkReplyTitleMatches("Page:20 GroupID:0 Position:0");
    TestHelper.clickOnReply("Page:20 GroupID:0 Position:0");
    checkHintPlaying("114 for: 1");
    clickOk();
    checkHintPlaying("122 for: 1");
    clickOk();

    //Page 20 is complete when hints play, then Page 22 is automatically started and its
    //hint at pos_0 plays. When hint is done playing, Page 22 is done and so is script.
    //FinishedScript Dialog shows.
    clickOk(); //Page22's hint.
    onView(isRoot()).perform(TestHelper.match(withText("Muy Bien!"), 1000)); //this is the end of page
    clickOk();
    checkHintTitleMatches("Page:22 GroupID:0");
  }


  @Test
  public void hintsPlayAfterCorrectResponseWhenAlreadyMatchedTest () {
    Activity activity = startActivity();

    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    waitToStart(1000);
    clickOk();

    checkHintTitleMatches("Page:0 GroupID:0");
    TestHelper.clickOnReply("Page:0 GroupID:1 Position:1");

    clickOk();
    checkHintTitleMatches("Page:8 GroupID:0");
    TestHelper.clickOnReply("Page:8 GroupID:1 Position:1");

    clickOk();
    checkHintTitleMatches("Page:9 GroupID:0");
    TestHelper.clickOnReply("Page:9 GroupID:1 Position:1");

    clickOk();
    onView(isRoot()).perform(swipeUp());
    checkHintTitleMatches("Page:10 GroupID:0");
    TestHelper.clickOnReply("Page:10 GroupID:1 Position:1");

    //Page 20's pos_0 reply is clicked, then it's pos_1 and pos_2 hints play.
    checkReplyTitleMatches("Page:20 GroupID:0 Position:0");
    TestHelper.clickOnReply("Page:20 GroupID:0 Position:0");
    checkHintPlaying("114 for: 1");
    clickOk();
    checkHintPlaying("122 for: 1");
    clickOk();

    //Page 20 is complete when hints play, then Page 22 is automatically started and its
    //hint at pos_0 plays. When hint is done playing, Page 22 is done and so is script.
    //FinishedScript Dialog shows.
    clickOk(); //Page22's hint.
    onView(isRoot()).perform(TestHelper.match(withText("Muy Bien!"), 1000)); //this is the end of page
    clickOk();
    checkHintTitleMatches("Page:22 GroupID:0");

    //Scroll back to Page 20. Click Position:0 Reply, which is already matched.
    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeDown());
    checkReplyTitleMatches("Page:20 GroupID:0 Position:0");
    TestHelper.clickOnReply("Page:20 GroupID:0 Position:0");
    checkHintPlaying("114 for: 1");
    clickOk();
    checkHintPlaying("122 for: 1");
    clickOk();

  }

  @Test
  public void hintsPlayAfterCorrectResponseOnPage4() {
    Activity activity = startActivity();

    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    clickOk();
    checkHintTitleMatches("Page:0 GroupID:0");
    clickScrollGroupID("Page:0 GroupID:1 Position:1");
    clickScrollGroupID("Page:0 GroupID:1 Position:2");
    TestHelper.clickOnReply("Page:0 GroupID:1 Position:3");

    onView(withText("180 for: 1")).check(doesNotExist());
    checkReplyTitleMatches("Page:4 GroupID:0 Position:0");

    //Click on Page:4 Position:0 (Page 4 has Reply then Hint)
    TestHelper.clickOnReply("Page:4 GroupID:0 Position:0");

    //Final hint playing before going to next page.
    checkHintPlaying("180 for: 1");
    clickOk();

    //Goes to Page:6
    checkReplyTitleMatches("Page:6 GroupID:0 Position:0");
  }

  private StageActivity startActivity() {
    intent.putExtra(StageActivity.STORY, StageActivity.ESPRESSO_STORY);
    return mActivityRule.launchActivity(intent);
  }
}