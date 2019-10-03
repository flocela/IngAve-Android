package com.olfybsppa.inglesaventurero.stageactivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.Leader;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Matchable;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Page;
import com.olfybsppa.inglesaventurero.stageactivity.dialog.ColloquyOptionsDialog;
import com.olfybsppa.inglesaventurero.stageactivity.dialog.CorrectionDialog;
import com.olfybsppa.inglesaventurero.stageactivity.dialog.EndOfStoryDialog;
import com.olfybsppa.inglesaventurero.stageactivity.dialog.MissedPagesDialog;
import com.olfybsppa.inglesaventurero.stageactivity.dialog.NoRepliesDialog;
import com.olfybsppa.inglesaventurero.stageactivity.dialog.PageMatchedDialog;
import com.olfybsppa.inglesaventurero.start.SettingsActivity;

import java.util.ArrayList;

/**
 * Note: Replies are the predetermined sentences that I want the user to say.
 *       Responses are the responses that the user says and are compared to the Replies.
 */
public class Director implements DirectorI, ViewPager.OnPageChangeListener  {
  private Repository repository;
  private CollViewUpdater collViewUpdater;
  private ViewPager            viewPager;
  private PlayerInterface      player;
  private DialogPresenter      notifier;
  private Waiter               waiter;
  private SceneCatalogue       sceneCatalogue;
  private boolean              pageZeroStarted;
  private boolean              shownInstructions;
  private boolean              doneWithInstructions;
  private boolean              waitingForPageTurn;
  private boolean              waitingForEndOfScene;
  private boolean              waitingForMatchableFlash;
  private int waitAfterMatch      = 1400;
  private int waitEndIsMatchable  = 0;
  private int waitEndIsLeader     = 2000;
  private int waitAtTopOfPage     = 500;
  private int waitBeforeFirstHint = 1000;
  private int waitBeforeFlash     = 400;
  private int waitScroll          = 300;
  private int waitPlayer          = 300;

  public static int CONTINUE_PAGE_INT    = -2;
  public static int PLAY_AGAIN_INT       = -3;
  public static int PAUSE_ON_PAGE_INT    = -4;
  public static String CONT_PAGE         = "CONT_PAGE";
  public static String PAUSE_ON_PAGE     = "PAUSE_ON_PAGE";

  //Only call from UI thread. I'm creating the waiterHandler here.
  public Director(Repository repository,
                  CollViewUpdater collViewUpdater,
                  ViewPager viewPager,
                  PlayerInterface player,
                  DialogPresenter notifier,
                  SceneCatalogue sceneCatalogue,
                  int[] timeArray) {
    waiter = new Waiter();
    this.repository = repository;
    this.collViewUpdater = collViewUpdater;
    this.viewPager = viewPager;
    viewPager.addOnPageChangeListener(this);
    this.player = player;
    this.notifier = notifier;
    this.sceneCatalogue = sceneCatalogue;
    changeVelocity(timeArray);
  }

  @Override
  public void changeVelocity(int[] times) {
    if (times != null && times.length == 8) {
      waitAfterMatch      = times[0];
      waitEndIsMatchable  = times[1];
      waitEndIsLeader     = times[2];
      waitAtTopOfPage     = times[3];
      waitBeforeFirstHint = times[4];
      waitBeforeFlash     = times[5];
      waitScroll          = times[6];
      waitPlayer          = times[7];
    }
    else {
      changeVelocity(new int[] {1400, 0, 200, 500, 1000, 400, 300, 300});
    }
  }

  @Override
  public void clearEndOfStory() {
    int currPosition = viewPager.getCurrentItem();
    collViewUpdater.clearMatches(repository.getPage(currPosition).getName());
    repository.clearEndOfStory(currPosition);
  }

  // only call on UI Thread
  public void close() {
    if (waiter != null) {
      waiter.removeCallbacksAndMessages(null);
      waiter = null;
    }
  }

  // only call on UI Thread
  public void open() {
    if (waiter == null) {
      waiter = new Waiter();
    }
  }

  //Only call from UI thread.
  @Override
  public void continueWithPage(int groupID) {
    if (waiter == null)
      return;
    Page currPage                      = repository.getPage(viewPager.getCurrentItem());
    int currPageName                   = currPage.getName();
    Leader aptLead                     = currPage.getAptLeader();
    Leader nextLead                    = currPage.getNextLeaderAt(groupID);
    ArrayList<Matchable> aptMatchables = currPage.getAptMatchables();
    if (null != aptLead) { //Play hint.
      playHint(currPageName, aptLead, CONTINUE_PAGE_INT);
    }
    else if (null != nextLead) {
      playHint(currPageName, nextLead, CONTINUE_PAGE_INT);
    }
    else if (aptMatchables.isEmpty()) { //End of page.
      if (!atLastPage()) {
        if (waitingForPageTurn) {
          setWaitsToFalse();
          viewPager.setCurrentItem(viewPager.getCurrentItem() + 1); //Turn to next page.
          Page newCurrPage = repository.getPage(viewPager.getCurrentItem());
          scrollToAptLine(newCurrPage, 100);
          waiter.waitOnPauseOnPage(waitAtTopOfPage, 0);
        }
        else {
          setWaitForPageToTurn();
          if (currPage.endsWithLeader()) {
            waiter.waitOnContPage(waitEndIsLeader, groupID);
          }
          else {
            waiter.waitOnContPage(waitEndIsMatchable, groupID);
          }
        }
      }
      else if (repository.isSceneCompleted()) {
        sceneCatalogue.sceneFinished();
        if (waitingForEndOfScene) {
          setWaitsToFalse();
          notifier.showDialog(EndOfStoryDialog.newInstance(null));
        }
        else {
          setWaitForEndOfScene();
          if (currPage.endsWithLeader()) {
            waiter.waitOnContPage(waitEndIsLeader, groupID);
          }
          else {
            waiter.waitOnContPage(waitEndIsMatchable, groupID);
          }
        }
      }
      else if (!repository.isSceneCompleted()) {
        notifier.showDialog(MissedPagesDialog.newInstance(null));
      }
    }
    else if (!aptMatchables.isEmpty()) { //Prompt for Response.
      if (currPageName == 0 && !doneWithInstructions && !shownInstructions) {
        notifier.possiblyShow(ColloquyOptionsDialog.newInstance(null),
          SettingsActivity.OPTIONS_PROMPT);
        // If I go forward in scene and then come back to page 0, instructions will not
        // show because shownInstructions is set to true. However, if
        // there is a configuration change, then Director is made new again, and
        // instructions will show. (Doesn't happen often enough, Leave it.)
        shownInstructions = true;
      }
      else {
        if (waitingForMatchableFlash) {
          setWaitsToFalse();
          collViewUpdater.flash(aptMatchables.get(0).getGroupId(), waitScroll);
        }
        else {
          setWaitingForMatchableFlash();
          waiter.waitOnContPage(waitBeforeFlash, groupID);
        }
      }
    }
  }

  @Override
  public int getCurrPageName() {
    int currPosition = viewPager.getCurrentItem();
    Page currPage = repository.getPage(currPosition);
    return currPage.getName();
  }

  // Only call from UI thread.
  @Override
  public void notifyStoppedPlaying (int pageName, int position, int shadow) {
    Page pageHeard = repository.getPageFromName(pageName);
    if (pageHeard != null) {
      pageHeard.haveHeard(position);
    }
    Page currPage = repository.getPage(viewPager.getCurrentItem());
    collViewUpdater.clearHilights(pageName, position);
    if (currPage.getName() == pageName) {
      if (shadow == PAUSE_ON_PAGE_INT) {
        pauseOnPage(currPage.getGroupIDOfPosition(position) + 1);
      }
      else if (shadow == CONTINUE_PAGE_INT) {
        continueWithPage(currPage.getGroupIDOfPosition(position) + 1);
      }
      else if (shadow == StageActivity.PLAYER_DIDNT_FINISH) {
        //Do nothing
      }
    }
  }

  @Override
  public void receiveUserInput(ArrayList<String> lines) {
    int currPosition = viewPager.getCurrentItem();

    //applyLines() will change the respository
    Answer ans = repository.applyLines(lines, currPosition);

    //Now change the views.
    boolean repliesExist         = ans.matchablesExist();
    boolean pageAlreadyMatched   = ans.pagePreviouslyMatched();
    ArrayList<String> aptReplies = ans.getAptMatchableStrings();
    if (!repliesExist) {
      notifier.showDialog(NoRepliesDialog.newInstance(null));
    }
    else if (!ans.isMatched() && pageAlreadyMatched) {
      Bundle bundle = new Bundle();
      bundle.putString(CorrectionDialog.INCORRECT_RESPONSE, ans.getFirstResponse());
      notifier.showDialog(PageMatchedDialog.newInstance(bundle));
    }
    else if (!ans.isMatched() && !aptReplies.isEmpty()) {
      Bundle bundle = new Bundle();
      bundle.putStringArrayList(CorrectionDialog.CORRECT_RESPONSES, aptReplies);
      bundle.putString(CorrectionDialog.INCORRECT_RESPONSE, ans.getFirstResponse());
      notifier.showDialog(CorrectionDialog.newInstance(bundle));
    }
    else if (ans.isMatched()) {
      Page currPage = repository.getPage(currPosition);
      int lastMatchableGroupID = currPage.getLastMatchableGroupID();
      collViewUpdater.match(currPage, lines);
      //if (ans.getGroupID() == lastMatchbleGroupID) {
      //  If matchable is the last matchable go to next page or
      //  play final aptHint, then go to next page.
      //}
      //else if (ans.getGroupID() != lastMatchableGroupID) {
      //  User could be matching a non-matched or matched reply in a higher lineset,
      //  and I do not want the page to turn even if the last matchable
      //  has already been matched, so I call pauseOnPage.
      //  I do not care if lineset was already matched or not.
      //  I am waiting for user to match the lastMatchableGroupID before moving to
      //  next page.
      //  Pause at this page after playing next apt hint.
      //}
      //Log.i("ATAG", "receiveUserInputArray: waiter is not null: " + (waiter != null));
      if (ans.getGroupID() == lastMatchableGroupID)
        waiter.waitOnContPage(waitAfterMatch,  ans.getGroupID() + 1);
      else if (ans.getGroupID() != lastMatchableGroupID)
        waiter.waitOnPauseOnPage(waitAfterMatch, ans.getGroupID() +1);
    }
  }

  @Override
  public void setDoneWithInstructions(boolean done) {
    this.doneWithInstructions = done;
  }
  boolean userScroll       = false;
  boolean pageTurned       = false;
  int currState            = -1;
  float origOffset         = -1;
  float finalOffset        = -1;
  float currPositionOffset = -1;
  int origPosition         = -1;
  int finalPosition        = -1;
  int currPosition         = -1;

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    if (origOffset == -1){
      origOffset = positionOffset;
    }
    if (origOffset > 0.97 && positionOffset < 0.03) {
      finalOffset = positionOffset;
    }
    else if (origOffset != -1 && origOffset < 0.03 && positionOffset > 0.97) {
      finalOffset = positionOffset;
    }
    if (origPosition == -1){
      origPosition = position;
    }
    if (origPosition != -1 && position != origPosition) {
      finalPosition = position;
    }
    currPosition = position;
    currPositionOffset = positionOffset;
    if (!pageZeroStarted && position == 0) {
      if (waiter != null) {
        waiter.waitOnContPage(waitBeforeFirstHint, 0);
        pageZeroStarted = true;
      }
    }
  }

  @Override
  public void onPageScrollStateChanged(int state) {
    currState = state;
    if (currState == 1) {
      userScroll = true;
    }
    if (currState == 0 && origOffset != -1 && finalOffset != -1 && Math.abs(finalOffset - origOffset) > 0.93) {
      pageTurned = true;
    }
    if (currState == 0 && origPosition != -1 && finalPosition != -1 && Math.abs(finalPosition - origPosition) > 0) {
      pageTurned = true;
    }

    if (currState == 0) {
      if (pageTurned) {
        if (userScroll && waiter != null) {
          waiter.removeCallbacksAndMessages(null);
        }
        Page page = repository.getPage(finalPosition);
        if (page != null)
          player.requestPause(page.getName());
      }
      origOffset   = currPositionOffset;
      origPosition = currPosition;
      finalOffset    = -1;
      finalPosition = -1;
      userScroll = false;
      pageTurned = false;
    }
  }

  @Override
  public void onPageSelected(int position) {
    ////Log.i("ATAG", "onPageSelected: position: " + position);
  }

  //
  private class Waiter extends Handler {
    private String GROUP_ID = "GROUP_ID";

    @Override
    public void handleMessage (Message inputMessage) {
      if (inputMessage.getData().containsKey(CONT_PAGE)) {
        continueWithPage(inputMessage.getData().getInt(GROUP_ID));
      }
      else if (inputMessage.getData().containsKey(PAUSE_ON_PAGE)) {
        pauseOnPage(inputMessage.getData().getInt(GROUP_ID));
      }
    }

    public void waitOnPauseOnPage (int waitTime, int groupID) {
      removeCallbacksAndMessages(null);
      Message message = obtainMessage();
      Bundle bundle = new Bundle();
      bundle.putBoolean(PAUSE_ON_PAGE, true);
      bundle.putInt(GROUP_ID, groupID);
      message.setData(bundle);
      sendMessageDelayed(message, waitTime);
    }

    public void waitOnContPage (int waitTime, int groupID) {
      removeCallbacksAndMessages(null);
      Message message = obtainMessage();
      Bundle bundle = new Bundle();
      bundle.putBoolean(CONT_PAGE, true);
      bundle.putInt(GROUP_ID, groupID);
      message.setData(bundle);
      sendMessageDelayed(message, waitTime);
    }
  }

  private boolean atLastPage () {
    Page currPage = repository.getPage(viewPager.getCurrentItem());
    return (-1 == currPage.getFollowingPage());
  }

  //Does not allow turn to next page or end of scene unless this page only has hints.
  private void pauseOnPage (int groupID) {
    if (null == waiter)
      return;
    int currPosition = viewPager.getCurrentItem();
    Page currPage = repository.getPage(currPosition);
    int currName = currPage.getName();
    Leader aptLead = currPage.getAptLeader();
    Leader nextLead = currPage.getNextLeaderAt(groupID);
    ArrayList<Matchable> aptMatchables = currPage.getAptMatchables();
    boolean onlyHasHints = currPage.onlyHasHints();

    if (null != aptLead) { //Play hint.
      playHint(currName, aptLead, PAUSE_ON_PAGE_INT);
    }
    else if (null != nextLead) {
      playHint(currName, nextLead, PAUSE_ON_PAGE_INT);
    }
    else if (!aptMatchables.isEmpty()) { //Prompt user for response.
      if (waitingForMatchableFlash) {
        setWaitsToFalse();
        collViewUpdater.flash(aptMatchables.get(0).getGroupId(), waitScroll);
      }
      else {
        setWaitingForMatchableFlash();
        waiter.waitOnContPage(waitBeforeFlash, groupID);
      }
    }
    else if (onlyHasHints) { //All hints have been heard. Will lead to page turn.
      continueWithPage(groupID);
    }
    //Prompt user for response although all responses have been matched (scroll, no flash)
    else if (currPage.isNextMatchableAt(groupID)) {
      collViewUpdater.scrollTo(currPage.getName(), groupID, waitScroll);
    }
  }

  private void playHint (int pageName, Leader lead, int shadow) {
    collViewUpdater.hilightWords(pageName, lead.getGroupId(), lead.getPosition());
    requestStart(pageName, lead, shadow);
  }

  private void requestStart (int pageName, Leader lead, int shadow) {
    player.requestStart(lead.getNormalStartTime(),
                        lead.getNormalEndTime() - lead.getNormalStartTime(),
                        pageName,
                        lead.getPosition(),
                        shadow,
                        waitPlayer);
  }

  private void scrollToAptLine (Page page, int scrollTime) {
    int newPageName = page.getName();
    Leader newAptLead = page.getAptLeader();
    ArrayList<Matchable> newAptMatchables = page.getAptMatchables();
    if (newAptLead != null)
      collViewUpdater.scrollTo(page.getName(), newAptLead.getGroupId(), 100);
    else if (!newAptMatchables.isEmpty())
      collViewUpdater.scrollTo(newPageName, newAptMatchables.get(0).getGroupId(), 100);
  }

  private void setWaitForEndOfScene () {
    waitingForPageTurn = false;
    waitingForMatchableFlash = false;
    waitingForEndOfScene = true;
  }

  private void setWaitingForMatchableFlash () {
    waitingForPageTurn = false;
    waitingForEndOfScene = false;
    waitingForMatchableFlash = true;
  }

  private void setWaitForPageToTurn () {
    waitingForEndOfScene = false;
    waitingForMatchableFlash = false;
    waitingForPageTurn = true;
  }

  private void setWaitsToFalse() {
    waitingForPageTurn = false;
    waitingForEndOfScene = false;
    waitingForMatchableFlash = false;
  }
}