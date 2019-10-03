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
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickEnglish;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.waitToStart;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class FinishedButMissedPageTest {
  private ViewPagerIdlingResource idlingResource;
  private SharedPreferences.Editor prefEditor;
  Intent intent;
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
   * Click on correct answer on last page, without finishing all the script's pages.
   * MissedPagesDialog pops up with correct text.
   */
  @Test
  public void onlyFinishedLastPage_ShowMissedPagesDialog () {
    Activity activity = startActivity();

    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    TestHelper.checkHintTitleMatches("Page:0 GroupID:0");

    onView(isRoot()).perform(swipeLeft());
    onView(isRoot()).perform(swipeLeft());
    onView(isRoot()).perform(swipeLeft());
    onView(isRoot()).perform(swipeLeft());
    TestHelper.clickOnReply("Page:20 GroupID:0 Position:0");
    waitToStart(2000);

    //MissedPagesDialog title is shown.
    onView(isRoot()).
      perform(TestHelper.match(allOf(withId(R.id.title),
                                     withText("Páginas Omitidas"),
                                     isCompletelyDisplayed()), 2000));

    clickEnglish();
    onView(isRoot()).
      perform(TestHelper.match(allOf(withId(R.id.title),
        withText(R.string.missed_pages_title_eng),
        isCompletelyDisplayed()), 1000));
  }

  private StageActivity startActivity() {
    intent.putExtra(StageActivity.STORY, StageActivity.SILENT_STORY);
    return mActivityRule.launchActivity(intent);
  }

  public void waitForViewPagerResponse(long millis) {
    final long startTime = System.currentTimeMillis();
    final long endTime = startTime + millis;
    do {}
    while (System.currentTimeMillis() < endTime);
  }
}