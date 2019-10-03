package com.olfybsppa.inglesaventurero.tests.scripttests;

import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.view.View;

import com.olfybsppa.inglesaventurero.R;

import org.hamcrest.Matcher;

import java.util.concurrent.TimeoutException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

public class TestHelper {

  //an example of match()
  //onView(isRoot()).perform(match(allOf(isDisplayed(), withText("apple 0b")),1500));
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

  public static ViewAction performClick(final Matcher<View> matcher, final long millis) {
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
        final long startTimeA = System.currentTimeMillis();
        final long endTimeA = startTimeA + millis;
        do {
          for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
            if (matcher.matches(child)) {
              child.performClick();
              return;
            }
          }
          uiController.loopMainThreadForAtLeast(500);
        }
        while (System.currentTimeMillis() < endTimeA);

        throw new PerformException.Builder() // timeout happens
          .withActionDescription(this.getDescription())
          .withViewDescription(HumanReadables.describe(view))
          .withCause(new TimeoutException())
          .build();
      }
    };
  }

  public static void clickOk () {
    onView(isRoot()).perform(performClick(allOf(withId(R.id.btn_ok), isCompletelyDisplayed()), 1000));
    waitToStart(500);
  }

  public static void clickEnglish () {
    onView(isRoot()).perform(performClick(allOf(withId(R.id.btn_language),
                                                withText(R.string.inEnglish),
                                                isCompletelyDisplayed()), 1000));
    waitToStart(500);
  }

  public static void clickEspanol () {
    onView(isRoot()).perform(performClick(allOf(withId(R.id.btn_language),
                                                withText(R.string.enEspanol),
                                                isCompletelyDisplayed()), 1000));
    waitToStart(500);
  }

  public static void clickOnReply(String text) {
    onView(isRoot()).
      perform(performClick(allOf(isDescendantOfA(withChild(withText(text))),
                                 withId(R.id.r_correct_response),
                                 isDisplayed()), 1000));
  }

  public static void clickOnWrongReply(String text) {
    onView(isRoot()).
      perform(performClick(allOf(isDescendantOfA(withChild(withText(text))),
        withId(R.id.r_wrong_response)), 1000));
  }

  public static void clickScrollGroupID (String text) {
    onView(isRoot()).
      perform(performClick(allOf(isDescendantOfA(withChild(withText(text))),
        withId(R.id.r_group_scroll)), 1000));
    waitToStart(500);
  }

  public static void checkHintTitleMatches(String title) {
    onView(isRoot()).perform(TestHelper.match(allOf(isDisplayed(),
      withId(R.id.h_span_phrase),
      withText(title)), 2000));
  }

  public static void checkReplyTitleMatches(String title) {
    onView(isRoot()).perform(TestHelper.match(allOf(isCompletelyDisplayed(),
      withId(R.id.r_engl_phrase),
      withText(title)), 2000));
  }

  public static void checkMarkerIsThere (String text) {
    onView(isRoot()).
      perform(TestHelper.match(allOf(withId(R.id.r_matchedmarker),
        hasSibling(withChild(withText(text))))
        ,1000));
  }

  public static void checkHintPlaying(String hint) {
    onView(isRoot()).perform(TestHelper.match(allOf(isCompletelyDisplayed(),
      withText(hint)), 1000));
  }

  public static void waitToStart(long millis) {
    final long startTime = System.currentTimeMillis();
    final long endTime = startTime + millis;
    do {}
    while (System.currentTimeMillis() < endTime);
  }
}
