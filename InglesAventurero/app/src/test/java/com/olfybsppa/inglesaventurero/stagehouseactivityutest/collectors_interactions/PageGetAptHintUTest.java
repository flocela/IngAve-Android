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
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class PageGetAptHintUTest {
  @Mock
  Hint mHintH0; //heard
  @Mock Hint   mHintU0; //unheard
  @Mock Hint   mHintH1; //heard
  @Mock Hint   mHintU1; //unheard
  @Mock Hint   mHintU2; //unheard

  @Mock
  Matchable mMatchedM0;
  @Mock Matchable  mMatchedM1;
  @Mock Matchable  mMatchedM2;
  @Mock Matchable  mNotMatchedM0;
  @Mock Matchable  mNotMatchedM1;
  @Mock Matchable  mNotMatchedM2;
  @Mock Matchable  mNotMatchedM3;

  ArrayList<Leader>    origLeaders =      new ArrayList<>();
  ArrayList<Matchable> origMatchables = new ArrayList<>();
  ArrayList<String>    responses =      new ArrayList<>();
  int pageID = 111;

  @Before
  public  void setUp() throws Exception {
    when(mHintH0.wasHeard()).thenReturn(true);
    when(mHintH0.getGroupId()).thenReturn(0);
    when(mHintH0.getPosition()).thenReturn(0);

    when(mHintU0.wasHeard()).thenReturn(false);
    when(mHintU0.getGroupId()).thenReturn(0);
    when(mHintU0.getPosition()).thenReturn(0);

    when(mHintH1.wasHeard()).thenReturn(true);
    when(mHintH1.getGroupId()).thenReturn(1);
    when(mHintH1.getPosition()).thenReturn(1);

    when(mHintU1.wasHeard()).thenReturn(false);
    when(mHintU1.getGroupId()).thenReturn(1);
    when(mHintU1.getPosition()).thenReturn(1);

    when(mHintU2.wasHeard()).thenReturn(false);
    when(mHintU2.getGroupId()).thenReturn(2);
    when(mHintU2.getPosition()).thenReturn(2);

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

    when(mNotMatchedM3.isMatchable()).thenReturn(true);
    when(mNotMatchedM3.getPosition()).thenReturn(3);
    when(mNotMatchedM3.getGroupId()).thenReturn(3);
    when(mNotMatchedM3.isMatched()).thenReturn(false);
  }

  // Cases:
  // 1.  unheard   hint,    unmatched reply
  // 2.  heard     hint,    heard hint,    unmatched reply
  // 3.  unheard   hint,    matched reply
  // 4.  heard     hint,    matched reply
  // 5.  matched   reply,   unheard hint
  // 6.  matched   reply,   heard hint
  // 7.  unheard   hint,    unheard hint,  unmatched reply
  // 8.  heard     hint,    unheard hint,  unmatched reply
  // 9.  unmatched reply,   unheard hint
  // 10. unmatched reply,   heard hint
  // 11. heard     hint,    unmatched reply
  // 12. heard     hint
  // 13. unheard   hint
  // 14. unmatched reply
  // 15. unheard   hint,    unmatched reply, unheard hint
  // 16. unheard   hint,    matched   reply, unheard hint //case never happens in project
  // 17. matched   reply,   unheard hint, unheard hint
  // 18. matched   reply,   heard   hint, unheard hint
  // 19. unheard   hint,    unmatched reply, unheard hint, unmatched reply
  // 20. heard     hint,    unmatched reply, unheard hint, unmatched reply
  // 20. heard     hint,    matched reply,   unheard hint, unmatched reply

  @Test
  public void unheardHint_unmatchedReply () {
    origLeaders.add(mHintU0);
    origMatchables.add(mNotMatchedM1);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(mHintU0, page.getAptLeader());
  }

  @Test
  public void heardHint_heardHint_unmatchedReply () {
    origLeaders.add(mHintH0);
    origLeaders.add(mHintH1);
    origMatchables.add(mNotMatchedM2);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(null, page.getAptLeader());
  }

  @Test
  public void unheardHint_matchedReply () {
    origLeaders.add(mHintU0);
    origMatchables.add(mMatchedM1);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(null, page.getAptLeader());
  }

  @Test
  public void heardHint_matchedReply () {
    origLeaders.add(mHintH0);
    origMatchables.add(mMatchedM1);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(null, page.getAptLeader());
  }

  @Test
  public void matchedReply_UnheardHint () {
    origLeaders.add(mHintU1);
    origMatchables.add(mMatchedM0);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(mHintU1, page.getAptLeader());
  }

  @Test
  public void matchedReply_heardHint () {
    origLeaders.add(mHintH1);
    origMatchables.add(mMatchedM0);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(null, page.getAptLeader());
  }

  @Test
  public void unheardHint_UnheardHint_UnmatchedReply () {
    origLeaders.add(mHintU0);
    origLeaders.add(mHintU1);
    origMatchables.add(mNotMatchedM2);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(mHintU0, page.getAptLeader());
  }

  @Test
  public void heardHint_UnheardHint_UnmatchedReply () {
    origLeaders.add(mHintH0);
    origLeaders.add(mHintU1);
    origMatchables.add(mNotMatchedM2);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(mHintU1, page.getAptLeader());
  }

  @Test
  public void unmatchedReply_unheardHint () {
    origLeaders.add(mHintU1);
    origMatchables.add(mNotMatchedM0);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(null, page.getAptLeader());
  }

  @Test
  public void unmatchedReply_heardHint () {
    origLeaders.add(mHintH1);
    origMatchables.add(mNotMatchedM0);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(null, page.getAptLeader());
  }

  @Test
  public void heardHint_unmatchedReply () {
    origLeaders.add(mHintH0);
    origMatchables.add(mNotMatchedM1);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(null, page.getAptLeader());
  }

  @Test
  public void heardHint () {
    origLeaders.add(mHintH0);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(null, page.getAptLeader());
  }

  @Test
  public void unheardHint () {
    origLeaders.add(mHintU0);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(mHintU0, page.getAptLeader());
  }

  @Test
  public void unmatchedReply () {
    origMatchables.add(mNotMatchedM1);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(null, page.getAptLeader());
  }

  @Test
  public void unheardHint_unmatchedReply_unheardHint () {
    origLeaders.add(mHintU0);
    origMatchables.add(mNotMatchedM1);
    origLeaders.add(mHintU2);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(mHintU0, page.getAptLeader());
  }

  @Test
  public void unheardHint_matchedReply_unheardHint () {
    origLeaders.add(mHintU0);
    origMatchables.add(mMatchedM1);
    origLeaders.add(mHintU2);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(mHintU0, page.getAptLeader());
  }

  @Test
  public void matchedReply_unheardHint_unheardHint () {
    origMatchables.add(mMatchedM0);
    origLeaders.add(mHintU1);
    origLeaders.add(mHintU2);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(mHintU1, page.getAptLeader());
  }

  @Test
  public void matchedReply_unheardHint_heardHint () {
    origMatchables.add(mMatchedM0);
    origLeaders.add(mHintH1);
    origLeaders.add(mHintU2);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(mHintU2, page.getAptLeader());
  }

  @Test
  public void unheardHint_unmatchedReply_unheardHint_unmatchedReply () {
    origLeaders.add(mHintU0);
    origMatchables.add(mNotMatchedM1);
    origLeaders.add(mHintU2);
    origMatchables.add(mNotMatchedM2);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(mHintU0, page.getAptLeader());
  }

  @Test
  public void heardHint_unmatchedReply_unheardHint_unmatchedReply () {
    origLeaders.add(mHintH0);
    origMatchables.add(mNotMatchedM1);
    origLeaders.add(mHintU2);
    origMatchables.add(mNotMatchedM2);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(null, page.getAptLeader());
  }

  @Test
  public void heardHint_matchedReply_unheardHint_unmatchedReply () {
    origLeaders.add(mHintH0);
    origMatchables.add(mMatchedM1);
    origLeaders.add(mHintU2);
    origMatchables.add(mNotMatchedM2);

    Page page = new Page(pageID, origLeaders, origMatchables);
    assertEquals(null, page.getAptLeader());
  }
}
