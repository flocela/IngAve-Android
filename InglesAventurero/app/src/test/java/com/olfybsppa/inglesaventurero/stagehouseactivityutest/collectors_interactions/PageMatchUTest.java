package com.olfybsppa.inglesaventurero.stagehouseactivityutest.collectors_interactions;


import com.olfybsppa.inglesaventurero.stageactivity.Answer;
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
import static junit.framework.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.mockito.Mockito.verify;
@RunWith(PowerMockRunner.class)
public class PageMatchUTest {
  @Mock
  Hint mHint0;
  @Mock Hint   mHint1;
  @Mock Hint   mHint2;
  @Mock Matchable mMatchedM0;
  @Mock Matchable  mMatchedM1;
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
  ArrayList<String>    responses =      new ArrayList<>();
  int pageID = 111;

  @Before
  public  void setUp() throws Exception {
    when(mMatchL0.isMatched()).thenReturn(true);
    when(mMatchL1.isMatched()).thenReturn(true);
    when(mMatchL2.isMatched()).thenReturn(true);
    when(mMatchL0.getLinePosition()).thenReturn(0);
    when(mMatchL1.getLinePosition()).thenReturn(1);
    when(mMatchL2.getLinePosition()).thenReturn(2);
    when(mNoMatchL.isMatched()).thenReturn(false);

    when(mMatchedM0.isMatchable()).thenReturn(true);
    when(mMatchedM0.getPosition()).thenReturn(0);
    when(mMatchedM0.getGroupId()).thenReturn(0);
    when(mMatchedM0.isMatched()).thenReturn(true);
    when(mMatchedM1.isMatchable()).thenReturn(true);
    when(mMatchedM1.getPosition()).thenReturn(1);
    when(mMatchedM1.getGroupId()).thenReturn(1);
    when(mMatchedM1.isMatched()).thenReturn(true);
    when(mMatchedM2.isMatchable()).thenReturn(true);
    when(mMatchedM2.getPosition()).thenReturn(2);
    when(mMatchedM2.getGroupId()).thenReturn(2);
    when(mMatchedM2.isMatched()).thenReturn(true);

    when(mNotMatchedM0.isMatchable()).thenReturn(true);
    when(mNotMatchedM0.getPosition()).thenReturn(0);
    when(mNotMatchedM0.getGroupId()).thenReturn(0);
    when(mNotMatchedM0.isMatched()).thenReturn(false);
    when(mNotMatchedM1.isMatchable()).thenReturn(true);
    when(mNotMatchedM1.getPosition()).thenReturn(1);
    when(mNotMatchedM1.getGroupId()).thenReturn(1);
    when(mNotMatchedM1.isMatched()).thenReturn(false);
    when(mNotMatchedM2.isMatchable()).thenReturn(true);
    when(mNotMatchedM2.getPosition()).thenReturn(2);
    when(mNotMatchedM2.getGroupId()).thenReturn(2);
    when(mNotMatchedM2.isMatched()).thenReturn(false);
  }

  // Cases:
  // 1. no matchables.
  // 2. all matchables are matched, no matches.
  // 3. all matchables are matched, is a match.
  // 4. matched and unmatched matchables and no matches.
  // 5. matched and unmatched matchables exist and matches unmatched matchable.
  // 6. matched and unmatched matchables exits and matches matched matchable.
  // 7. unmatched and unmatched matchables and matches second matchable.
  // 8. hint followed by matchable and matchable matched.

  // When there are no replies in the page, should
  // result in an Answer where method matchablesExist() return false;
  @Test
  public void noMatchables () {
    origHints.add(mHint0);
    origHints.add(mHint1);

    Page page = new Page(pageID, origHints, origMatchables);
    Answer answer = page.acceptResponses(responses);

    assertFalse(answer.matchablesExist());
  }

  // When all matchables are matched before call to acceptResponses(responses)
  // do not match
  @Test
  public void allAnswersWereAlreadyMatchedAndThereIsNoMatch () {
    origMatchables.add(mMatchedM0);
    origMatchables.add(mMatchedM2);
    when(mMatchedM0.match(responses)).thenReturn(mNoMatchL);
    when(mMatchedM2.match(responses)).thenReturn(mNoMatchL);
    Page page = new Page(pageID, origHints, origMatchables);
    Answer answer = page.acceptResponses(responses);

    assertFalse(answer.particularMatchablePreviouslyMatched());
    assertTrue(answer.matchablesExist());
    assertFalse(answer.isMatched());
    assertTrue(answer.pagePreviouslyMatched());
  }

  // When all matchables are matched before call to acceptResponses and responses
  // match mMatchedM12.
  @Test
  public void allAnswersWereAlreadyMatchedAndThereIsNowAMatch () {
    origMatchables.add(mMatchedM0);
    origMatchables.add(mMatchedM1);
    origMatchables.add(mMatchedM2);
    when(mMatchL1.isMatched()).thenReturn(true);
    when(mMatchL2.isMatched()).thenReturn(true);
    when(mMatchedM0.match(responses)).thenReturn(mNoMatchL);
    when(mMatchedM1.match(responses)).thenReturn(mMatchL1);
    when(mMatchedM2.match(responses)).thenReturn(mMatchL2);

    Page page = new Page(pageID, origHints, origMatchables);
    Answer answer = page.acceptResponses(responses);

    assertTrue(answer.matchablesExist());
    assertTrue(answer.isMatched());
    assertTrue(answer.particularMatchablePreviouslyMatched());
    assertEquals(1, answer.getLinePosition());
    assertTrue(answer.pagePreviouslyMatched());
  }

  // When contains matched and unmatched Matchables and there is no match with responses
  @Test
  public void matchedAndUnMatchedAndDoesNotMatch () {
    origMatchables.add(mMatchedM0);
    origMatchables.add(mNotMatchedM2);
    when(mMatchedM0.match(responses)).thenReturn(mNoMatchL);
    when(mNotMatchedM2.match(responses)).thenReturn(mNoMatchL);

    Page page = new Page(pageID, origHints, origMatchables);
    Answer answer = page.acceptResponses(responses);

    assertTrue(answer.matchablesExist());
    assertFalse(answer.particularMatchablePreviouslyMatched());
    assertFalse(answer.isMatched());
  }

  // When contains matched and unmatched Matchables and unmatched matchable
  // matches.
  @Test
  public void matchesNextUnmatchedMatchable () {
    origMatchables.add(mMatchedM0);
    origMatchables.add(mNotMatchedM1);
    origMatchables.add(mNotMatchedM2);
    when(mMatchedM0.match(responses)).thenReturn(mMatchL0);//shouldn't really match, but just proves that Page will check unmatched matchables first.
    when(mNotMatchedM1.match(responses)).thenReturn(mMatchL1);
    when(mNotMatchedM2.match(responses)).thenReturn(mMatchL2);

    Page page = new Page(pageID, origHints, origMatchables);
    Answer answer = page.acceptResponses(responses);

    assertTrue(answer.matchablesExist());
    assertEquals(1, answer.getLinePosition());
    assertTrue(answer.isMatched());
    assertFalse(answer.particularMatchablePreviouslyMatched());
  }

  // When contains matched and unmatched Matchables and responses match a
  // matchable that was previously matched.
  @Test
  public void matchesMatchableThatWasAlreadyMatched () {
    origMatchables.add(mMatchedM0);
    origMatchables.add(mMatchedM1);
    origMatchables.add(mNotMatchedM2);
    when(mMatchL0.isMatched()).thenReturn(true);
    when(mMatchL1.isMatched()).thenReturn(true);
    when(mMatchedM0.match(responses)).thenReturn(mMatchL0);
    when(mMatchedM1.match(responses)).thenReturn(mMatchL1);
    when(mNotMatchedM2.match(responses)).thenReturn(mNoMatchL);

    Page page = new Page(pageID, origHints, origMatchables);
    Answer answer = page.acceptResponses(responses);

    assertTrue(answer.matchablesExist());
    assertTrue(answer.particularMatchablePreviouslyMatched());
    assertEquals(0, answer.getLinePosition());
    assertTrue(answer.isMatched());
  }

  // When contains two unmatched matchables and responses match second matchable.
  // Should result in no match on the page.
  @Test
  public void matchesMatchableThatIsNotEarliestMatchable () {
    when(mHint0.getGroupId()).thenReturn(0);
    when(mHint0.getPosition()).thenReturn(0);
    origHints.add(mHint0);
    origMatchables.add(mNotMatchedM1);
    origMatchables.add(mNotMatchedM2);
    when(mNotMatchedM1.match(responses)).thenReturn(mNoMatchL);
    when(mNotMatchedM2.match(responses)).thenReturn(mMatchL1);

    Page page = new Page(pageID, origHints, origMatchables);
    Answer answer = page.acceptResponses(responses);

    assertTrue(answer.matchablesExist());
    assertFalse(answer.particularMatchablePreviouslyMatched());
    assertFalse(answer.isMatched());
  }

  // hint followed by matchable and matchable matched.
  @Test
  public void hintFollowedByMatchableAndMatchableMatched () {
    when(mHint0.getGroupId()).thenReturn(0);
    when(mHint0.getPosition()).thenReturn(0);
    when(mMatchL1.getGroupID()).thenReturn(1);
    origHints.add(mHint0);
    origMatchables.add(mNotMatchedM1);
    origMatchables.add(mNotMatchedM2);
    when(mNotMatchedM1.match(responses)).thenReturn(mMatchL1);
    when(mNotMatchedM2.match(responses)).thenReturn(mNoMatchL);

    Page page = new Page(pageID, origHints, origMatchables);
    Answer answer = page.acceptResponses(responses);

    assertTrue(answer.matchablesExist());
    assertFalse(answer.particularMatchablePreviouslyMatched());
    assertTrue(answer.isMatched());
    verify(mHint0).setHeard(true);
  }

}
