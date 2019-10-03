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
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.checkHintPlaying;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.checkHintTitleMatches;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickOk;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickOnReply;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickScrollGroupID;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.waitToStart;

@RunWith(AndroidJUnit4.class)
public class LeadToAlreadyMatchedPage_MatchedPageHintsPlayAndStaysThere {
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
   * Match to page with only hints will play hints.
   * Match to page with only hints → Hints will play and then go to next page. If page
   * is already matched, then still play hints and stay there.
   * Match page 0, match page 2, page 3 only has one hint, match page 12. For test:
   * start on page 2, should lead to page 3 playing hint, should lead to page 12
   * playing hints and should stay on page 12, although page 12 is already matched.
   */
  @Test
  public void matchReplyLeadsToPageWOnlyHintsAndLeadsToAlreadyMatchedPage_StaysThere() {
    Activity activity = startActivity();
    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    //Match Page:0 and Page:1 to get to Page:2.
    clickOk();
    checkHintTitleMatches("Page:0 GroupID:0");
    clickScrollGroupID("Page:0 GroupID:1 Position:1");
    clickOnReply("Page:0 GroupID:1 Position:2");
    clickOk();
    checkHintTitleMatches("Page:1 GroupID:0");
    clickScrollGroupID("Page:1 GroupID:1 Position:1");
    clickOnReply("Page:1 GroupID:1 Position:2");

    //At Page:2, match Position:6 to get to Page 3. (Page 2 has H, R, H, R)
    checkHintPlaying("34 for: 1");
    clickOk();
    checkHintTitleMatches("Page:2 GroupID:0");
    clickOnReply("Page:2 GroupID:1 Position:1");
    checkHintPlaying("46 for: 1");
    clickOk();
    onView(isRoot()).perform(swipeUp());
    clickScrollGroupID("Page:2 GroupID:3 Position:4");
    clickScrollGroupID("Page:2 GroupID:3 Position:5");
    //match Position 6.
    clickOnReply("Page:2 GroupID:3 Position:6");

    //Script continues to Page 3, which has one hint. (Page 3 has H)
    clickOk();//Hint playing dialog

    //Script continues to Page 12 after hint has played. (Page 12 has H, H, R)
    checkHintPlaying("130 for: 1");
    clickOk();
    checkHintPlaying("140 for: 1");
    clickOk();
    onView(isRoot()).perform(swipeDown());
    checkHintTitleMatches("Page:12 GroupID:0");
    onView(isRoot()).perform(swipeUp());
    clickOnReply("Page:12 GroupID:2 Position:2");

    //Script continues to Page 14. Not clicking on responses on Page 14. 14 remains clear.
    checkHintPlaying("86 for: 1");
    clickOk();
    checkHintTitleMatches("Page:14 GroupID:0");

    //Scroll back to Page 2.
    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeRight());
    onView(isRoot()).perform(swipeDown());
    checkHintTitleMatches("Page:2 GroupID:0");

    //Page:2 click on reply at GroupID: 3. Should lead to Page 3, play hint. Should lead
    // to page 12 and should stay there.
    onView(isRoot()).perform(swipeUp());
    clickOnReply("Page:2 GroupID:3 Position:6"); //click on Reply should lead to Page 3.
    checkHintPlaying("58 for: 1");
    clickOk();
    checkHintPlaying("130 for: 1");
    clickOk();
    checkHintPlaying("140 for: 1");
    clickOk();
    waitToStart(2000);
    onView(isRoot()).perform(swipeDown());
    checkHintTitleMatches("Page:12 GroupID:0");

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
}