package com.olfybsppa.inglesaventurero.stagehouseactivityutest.tracker;

import com.olfybsppa.inglesaventurero.stageactivity.Tracker;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Page;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class TrackerUTest {

  private Page page00;
  private Page page01;
  private Page page02;
  private Page page03;
  private HashMap<Integer, Page> pages;
  private Tracker tracker;

  
  // Pages next default page is as follows:
  // 00 -> 01 -> 02 -> -1
  // Page 03 is not in default script, no pages lead to it.
  // 03 -> null
 
  @Before
  public void setUp() throws Exception {
    page00 = mock(Page.class);
    when(page00.getName()).thenReturn(0);
    when(page00.getFollowingPage()).thenReturn(1);
    when(page00.isFirst()).thenReturn(true);
    when(page00.isLast()).thenReturn(false);
    page01 = mock(Page.class);
    when(page01.getName()).thenReturn(1);
    when(page01.getFollowingPage()).thenReturn(2);
    when(page01.isLast()).thenReturn(false);
    page02 = mock(Page.class);
    when(page02.getName()).thenReturn(2);
    when(page02.getFollowingPage()).thenReturn(-1);
    when(page02.isLast()).thenReturn(true);
    page03 = mock(Page.class);
    when(page03.getName()).thenReturn(3);
    when(page03.getFollowingPage()).thenReturn(-1);
    when(page03.isLast()).thenReturn(true);

    pages = new HashMap<Integer, Page>();
    pages.put(0, page00);
    pages.put(1, page01);
    pages.put(2, page02);
    pages.put(3, page03);

    tracker = new Tracker(pages);
  }

  
  // Testing .getCount().
  // Script is 3 pages long.
  @Test
  public void getCountReturns3 () throws Exception {
    assertEquals(3, tracker.getCount());
  }
  
  // Testing .getPage(int position).
  // Retrieve page at index 2 will return third page from script, which is page02.
  @Test
  public void getMeasureReturnsPage02 () throws Exception {
    assertEquals(page02, tracker.getPage(2));
  }
  
  // Testing .getPage(int position).
  @Test
  public void getMeasureResultsInIllegalArgumentException () throws Exception {
    boolean isIllegaArgumentException = false;
    Page page = tracker.getPage(5);
    assertNull(page);
  }

  // Testing .getPositionOfMeasure (int measureNameId)
  // Notice current script is only the pages that have been asked for so far,
  // not the whole script.
  @Test
  public void getPositionOfMeasure () throws Exception {
    tracker.setCurrentPosition(2);
    tracker.getCurrMeasure();
    assertEquals(1, tracker.getPositionOfMeasure(1));
  }

  @Test
  public void positionExists () throws Exception {
    assertTrue(tracker.positionExists(2));
    assertFalse(tracker.positionExists(3));
  }

  // Testing .setCurrentPosition.
  // Move to index 2 in script, retrieve curr page, which is page02
  @Test
  public void setAndGetCurrentPosition () throws Exception {
    tracker.setCurrentPosition(2);
    assertEquals(page02, tracker.getCurrMeasure());
  }

  // Testing .setCurrentPosition.
  // Move to index 1, then get nextNameId, which is 2.
  @Test
  public void getNextMeasureIDWhenExists () {
    tracker.setCurrentPosition(1);
    assertEquals(2, tracker.getNextNameId());
  }

  
  // Testing .getNextNameId
  // Move to index 2, then get nextNameId, which is -1.
  @Test
  public void getNextMeasureIDWhenAlreadyAtLastMeasure () {
    tracker.setCurrentPosition(2);
    assertEquals(-1, tracker.getNextNameId());
  }

  // Testing .changeEnding() returns and IllegalArgumentException, when asked to add
  // a measure that doesn't exist in it's hash.
  @Test
  public void changeEndingToNonExistingMeasure () throws Exception {
    boolean exceptionHappened = false;
    try {
      tracker.changeEnding(4, -1);
    }
    catch (Exception e) {
      if (e instanceof IllegalArgumentException)
        exceptionHappened = true;
    }
    assertTrue(exceptionHappened);
  }


  // Testing .changeEnding() returns and IllegalArgumentException, when asked to add
  // a measure that doesn't exist in it's hash.
  @Test
  public void changeEndingAtPositionThatIsTooFarOut () throws Exception {
    boolean exceptionHappened = false;
    try {
      tracker.changeEnding(4, 3);
    }
    catch (Exception e) {
      if (e instanceof IllegalArgumentException)
        exceptionHappened = true;
    }
    assertTrue(exceptionHappened);
  }
  
  // Testing .changeEnding(int measurePosition, Integer measureNameID).
  // Testing .getCurrentMeasure().
  // Delete pages 01 and 02, add page03 after page00.
  @Test
  public void changeEnding () throws Exception {
    tracker.changeEnding(0, 3);
    tracker.setCurrentPosition(0);
    assertEquals(page00, tracker.getCurrMeasure());
    tracker.setCurrentPosition(1);
    assertEquals(page03, tracker.getCurrMeasure());
    assertEquals(2, tracker.getCount());
  }

  // Testing clearStoryFrom(int pos)
  // Move position from zero to two, making currStory go from 0 to 2.
  // Clear story from 1 onward.
  // Pages 1, 2, and 3 should be reset.
  // current position should be 1.
  @Test
  public void clearStory () throws Exception {
    tracker.setCurrentPosition(0);
    tracker.setCurrentPosition(1);
    tracker.setCurrentPosition(2);
    tracker.clearStoryFrom(1);
    assertEquals(page01, tracker.getCurrMeasure());
    verify(page00, times(0)).reset();
    verify(page01).reset();
    verify(page02).reset();
    verify(page03).reset();
    assertEquals(2, tracker.getNextNameId());
  }

  
  // Testing .changeEnding(int measurePosition, Integer measureNameID).
  // Testing .getCurrentPath().
  // get path returns a path that is 0-->1-->3
  @Test
  public void getPath013 () throws Exception {
    ArrayList<Integer> correctPath = new ArrayList<>();
    correctPath.add(0);
    correctPath.add(1);
    correctPath.add(3);

    tracker.changeEnding(1, 3);
    tracker.setCurrentPosition(2);
    ArrayList<Integer> currPath = tracker.getCurrentPath();

    assertEquals(correctPath, currPath);
  }

  
  // Testing .changeEnding(int measurePosition, Integer measureNameID).
  // Testing .getCurrentPath().
  // get path returns a path that is 0-->1
  @Test
  public void getPath01 () throws Exception {
    ArrayList<Integer> correctPath = new ArrayList<>();
    correctPath.add(0);
    correctPath.add(1);

    tracker.changeEnding(1, 3);
    tracker.setCurrentPosition(1);
    ArrayList<Integer> currPath = tracker.getCurrentPath();

    assertEquals(correctPath, currPath);
  }

  // Testing .changeEnding(int measurePosition, Integer measureNameID).
  // Testing .getCurrentPath().
  // case where initially at end of Path, then add to path.
  @Test
  public void getAddToEndOfPath () throws Exception {
    HashMap<Integer, Page> origPages = new HashMap<Integer, Page>();
    origPages.put(0, page00);
    origPages.put(1, page01);
    origPages.put(2, page02);
    origPages.put(3, page03);

    tracker = new Tracker(origPages);
    tracker.setCurrentPosition(2);
    tracker.changeEnding(2, 3);
  }

  
  // Testing .setCurrentStory(ArrayList<Integer> wantedStroy).
  @Test
  public void setCurrStory () throws Exception {
    Page page04 = mock(Page.class);
    when(page04.getName()).thenReturn(4);
    when(page04.getFollowingPage()).thenReturn(5);
    Page page05 = mock(Page.class);
    when(page05.getName()).thenReturn(5);
    when(page05.getFollowingPage()).thenReturn(-1);
    when(page05.isLast()).thenReturn(true);
    pages = new HashMap<Integer, Page>();
    pages.put(0, page00);
    pages.put(1, page01);
    pages.put(2, page02);
    pages.put(3, page03);
    pages.put(4, page04);
    pages.put(5, page05);
    tracker = new Tracker(pages);

    ArrayList<Integer> wantedStory = new ArrayList<>();
    wantedStory.add(page00.getName());
    wantedStory.add(page01.getName());
    wantedStory.add(page04.getName());
    tracker.setCurrentPosition(1);
    tracker.setCurrStory(wantedStory);
    ArrayList<Integer> resultantPath = tracker.getCurrentPath();

    assertEquals(wantedStory, resultantPath);
    assertEquals(2, tracker.getCurrPosition());
  }
}
