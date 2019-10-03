package com.olfybsppa.inglesaventurero.tests.scripttests;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.espresso.IdlingPolicies;
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

import java.util.concurrent.TimeUnit;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.olfybsppa.inglesaventurero.tests.scripttests.TestHelper.clickOnWrongReply;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class CorrectionDialogTest {
  private ViewPagerIdlingResource idlingResource;
  private SharedPreferences.Editor prefEditor;
  Intent intent;

  @Rule
  public IntentsTestRule<StageActivity> mActivityRule =
    new IntentsTestRule(StageActivity.class, true, false);

  @Before
  public void setUp() {
    IdlingPolicies.setMasterPolicyTimeout(60, TimeUnit.SECONDS);
    IdlingPolicies.setIdlingResourceTimeout(26, TimeUnit.SECONDS);
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
  public void receiveIncorrectResponses_ShowCorrectionDialog () {
    Activity activity = startActivity();

    idlingResource = new ViewPagerIdlingResource((ViewPager)activity.
      findViewById(R.id.pager), "VPIR_0");
    registerIdlingResources(idlingResource);

    TestHelper.checkHintTitleMatches("Page:0 GroupID:0");
    clickOnWrongReply("Page:0 GroupID:1 Position:1");

    //CorrectionDialog title is shown.
    onView(isRoot()).
      perform(TestHelper.match(allOf(withId(R.id.incorrect_response),
                                     withText("Page:0 GroupID:1 Position:1 wrong"),
                                     isCompletelyDisplayed()), 1000));

    //CorrectionDialog text is shown.
    String text = "Page:0 GroupID:1 Position:1\n" +
                  "\n" +
                  "Page:0 GroupID:1 Position:2\n" +
                  "\n" +
                  "Page:0 GroupID:1 Position:3\n" +
                  "\n" +
                  "Page:0 GroupID:1 Position:4";
    onView(isRoot()).
      perform(TestHelper.match(allOf(withId(R.id.correct_responses),
                                     withText(text),
                                     isCompletelyDisplayed()), 1000));
  }

  private StageActivity startActivity() {
    intent.putExtra(StageActivity.STORY, StageActivity.SILENT_STORY);
    return mActivityRule.launchActivity(intent);
  }

  public void waitForToasts(long millis) {
    final long startTime = System.currentTimeMillis();
    final long endTime = startTime + millis;
    do {}
    while (System.currentTimeMillis() < endTime);
  }
}