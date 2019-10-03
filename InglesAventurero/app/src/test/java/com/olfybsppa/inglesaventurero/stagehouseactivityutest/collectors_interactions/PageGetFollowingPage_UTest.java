package com.olfybsppa.inglesaventurero.stagehouseactivityutest.collectors_interactions;


import com.olfybsppa.inglesaventurero.stageactivity.collectors.Hint;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Leader;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Matchable;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Page;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class PageGetFollowingPage_UTest {
  @Mock
  Hint mHint0;
  @Mock Hint       mHint1;
  @Mock Hint       mHint2;
  @Mock Hint       mHint3;
  @Mock Hint       mHint4;
  @Mock
  Matchable mMatchedM1;
  @Mock Matchable  mMatchedM2;
  @Mock Matchable  mNotMatchedM0;
  @Mock Matchable  mNotMatchedM1;
  @Mock Matchable  mNotMatchedM2;
  @Mock Matchable  mNotMatchedM3;
  @Mock Matchable.Limited mMatchL0;
  @Mock Matchable.Limited mMatchL1;
  @Mock Matchable.Limited mMatchL2;
  @Mock Matchable.Limited mNoMatchL;

  ArrayList<Leader>    origHints =      new ArrayList<>();
  ArrayList<Matchable> origMatchables = new ArrayList<>();
  int pageID = 111;

  @Before
  public  void setUp() throws Exception {
    when(mHint0.getFollowingPage()).thenReturn(0);
    when(mHint1.getFollowingPage()).thenReturn(1);
    when(mHint3.getFollowingPage()).thenReturn(3);
    when(mHint4.getFollowingPage()).thenReturn(4);

    when(mMatchedM1.isMatchable()).thenReturn(true);
    when(mMatchedM1.getPosition()).thenReturn(1);
    when(mMatchedM1.getGroupId()).thenReturn(1);
    when(mMatchedM1.isMatched()).thenReturn(true);
    when(mMatchedM1.getFollowingPage()).thenReturn(1);

    when(mMatchedM2.isMatchable()).thenReturn(true);
    when(mMatchedM2.getPosition()).thenReturn(2);
    when(mMatchedM2.getGroupId()).thenReturn(2);
    when(mMatchedM2.isMatched()).thenReturn(true);
    when(mMatchedM2.getFollowingPage()).thenReturn(2);

    when(mNotMatchedM0.isMatchable()).thenReturn(true);
    when(mNotMatchedM0.getPosition()).thenReturn(0);
    when(mNotMatchedM0.getGroupId()).thenReturn(0);
    when(mNotMatchedM0.isMatched()).thenReturn(false);

    when(mNotMatchedM1.isMatchable()).thenReturn(true);
    when(mNotMatchedM1.getPosition()).thenReturn(1);
    when(mNotMatchedM1.getGroupId()).thenReturn(1);
    when(mNotMatchedM1.isMatched()).thenReturn(false);
    when(mNotMatchedM1.getFollowingPage()).thenReturn(1);

    when(mNotMatchedM2.isMatchable()).thenReturn(true);
    when(mNotMatchedM2.getPosition()).thenReturn(2);
    when(mNotMatchedM2.getGroupId()).thenReturn(2);
    when(mNotMatchedM2.getFollowingPage()).thenReturn(2);
  }

  // Cases:
  // 1. no matchables.
  // 2. all matchables are matched.
  // 3. matched and unmatched matchables.
  // 4. matched and unmatched matchables followed by hints.

  // When there are no matchables in the page, should
  // result in last hint's following page.
  @Test
  public void noMatchables () {
    origHints.add(mHint0);
    origHints.add(mHint1);

    Page page = new Page(pageID, origHints, origMatchables);
    assertEquals(1, page.getFollowingPage());
    assertFalse(page.isLast());
  }

  // When all matchables are matched.
  @Test
  public void allMatched () {
    origHints.add(mHint0);
    origMatchables.add(mMatchedM1);
    origMatchables.add(mMatchedM2);

    Page page = new Page(pageID, origHints, origMatchables);
    assertEquals(2, page.getFollowingPage());
    assertFalse(page.isLast());
  }

  // When contains matched and unmatched Matchables.
  // FollowingPage is from last matchable regardless if it has been matched or not.
  @Test
  public void matchedAndUnmatched () {
    origHints.add(mHint0);
    origMatchables.add(mMatchedM1);
    origMatchables.add(mNotMatchedM2);

    Page page = new Page(pageID, origHints, origMatchables);
    assertEquals(2, page.getFollowingPage());
    assertFalse(page.isLast());
  }

  // When contains matched and unmatched matchables followed by hints.
  // FollowingPage is from the last matchable regardless if it has been matched or not.
  @Test
  public void matchesAndUnmatchedFollowedByHint () {
    origHints.add(mHint0);
    origHints.add(mHint3);
    origHints.add(mHint4);
    origMatchables.add(mMatchedM1);
    origMatchables.add(mNotMatchedM2);

    Page page = new Page(pageID, origHints, origMatchables);
    assertEquals(2, page.getFollowingPage());
    assertFalse(page.isLast());
  }
}
