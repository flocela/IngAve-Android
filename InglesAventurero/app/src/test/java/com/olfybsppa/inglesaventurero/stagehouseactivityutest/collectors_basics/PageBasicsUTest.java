package com.olfybsppa.inglesaventurero.stagehouseactivityutest.collectors_basics;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.Hint;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Leader;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Line;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Matchable;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Page;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Reply;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.ReplyLineSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class PageBasicsUTest {

  private Hint dHint00;
  private Reply dReply01;
  private Hint  dHint02;
  private ArrayList<Line> emptyLines = new ArrayList<>();

  private Hint  hint00;
  private Hint  hint11;
  private Reply reply22;
  private Reply reply32;
  private Hint  hint43;
  private Reply reply54;
  private Reply reply64;
  private ArrayList<Leader> origLeaders       = new ArrayList<>();
  private ArrayList<Matchable> origMatchables = new ArrayList<>();

  @Before
  public  void setUp() throws Exception {
    dHint00 = mock(Hint.class);
    when(dHint00.getPosition()).thenReturn(0);

    dReply01 = mock(Reply.class);
    when(dReply01.getPosition()).thenReturn(1);

    dHint02 = mock(Hint.class);
    when(dHint02.getPosition()).thenReturn(2);

    hint00   = new Hint(0, 0);
    hint00.setFollowingPage(0);
    hint11   = new Hint(1, 1);
    hint11.setFollowingPage(1);
    reply22 = new Reply(2, 2);
    reply22.setFollowingPage(2);
    reply32 = new Reply(3, 2);
    reply32.setFollowingPage(3);
    hint43   = new Hint(4, 3);
    hint43.setFollowingPage(4);
    reply54 = new Reply(5, 4);
    reply54.setFollowingPage(5);
    reply64 = new Reply(6, 4);
    reply64.setFollowingPage(6);
    reply22.setRegex("reply22");
    reply32.setRegex("reply32");
    reply54.setRegex("reply54");
    reply64.setRegex("reply64");
    reply22.setEngPhrase("reply 22");
    reply32.setEngPhrase("reply 32");
    reply54.setEngPhrase("reply 54");
    reply64.setEngPhrase("reply 64");
    origLeaders.add(hint00);
    origLeaders.add(hint11);
    origLeaders.add(hint43);
    origMatchables.add(reply22);
    origMatchables.add(reply32);
    origMatchables.add(reply54);
    origMatchables.add(reply64);
  }

  @Test
  public void testConstructorWithNull () {
    Page page = new Page(0, null, null);
    assertEquals(null, page.getAptLeader());
    assertEquals(0, page.getAptMatchables().size());
    assertEquals(page.getFollowingPage(), -1);
    assertEquals(0, page.getLeads().size());
    assertEquals(0, page.getMatchables().size());
    assertEquals(0, page.getName());
    assertFalse(page.isFirst());
    assertFalse(page.isLast());
  }

  @Test
  public void testConstructorWithEmptyArrayList () {
    Page page = new Page(0, new ArrayList<Leader>(), new ArrayList<Matchable>());
    assertEquals(null, page.getAptLeader());
    assertEquals(0, page.getAptMatchables().size());
    assertEquals(page.getFollowingPage(), -1);
    assertEquals(0, page.getLeads().size());
    assertEquals(0, page.getMatchables().size());
    assertEquals(0, page.getName());
    assertFalse(page.isFirst());
    assertFalse(page.isLast());
  }

  @Test
  public void testConstructor () {
    Page page = new Page(1, origLeaders, origMatchables);
    assertEquals(1, page.getName());

    ArrayList<Leader> corrLeaders = new ArrayList<>();
    corrLeaders.add(hint00);
    corrLeaders.add(hint11);
    corrLeaders.add(hint43);

    ArrayList<Matchable> corrMatchables = new ArrayList<>();
    ArrayList<Reply> repliesTwo = new ArrayList<>();
    repliesTwo.add(reply22);
    repliesTwo.add(reply32);
    ReplyLineSet replyLineSetOne = new ReplyLineSet(repliesTwo);
    corrMatchables.add(replyLineSetOne);

    ArrayList<Reply> repliesFour = new ArrayList<>();
    repliesFour.add(reply54);
    repliesFour.add(reply64);
    ReplyLineSet replyLineSetFour = new ReplyLineSet(repliesFour);
    corrMatchables.add(replyLineSetFour);

    assertEquals(corrLeaders.size(), page.getLeads().size());
    assertTrue(corrLeaders.containsAll(page.getLeads()));
    assertEquals(corrMatchables.size(), page.getMatchables().size());
    assertTrue(corrMatchables.containsAll(page.getMatchables()));
  }

  @Test
  public void testSetPageName () {
    Page page = new Page(10, new ArrayList<Leader>(), new ArrayList<Matchable>());
    assertEquals(10, page.getName());
  }

  @Test
  public void testIsFirst () {
    Page page = new Page(10, new ArrayList<Leader>(), new ArrayList<Matchable>());
    assertFalse(page.isFirst());
    page.setAsFirstPage(true);
    assertTrue(page.isFirst());
  }

  @Test
  public void testSetBackgroundFilename () {
    Page page = new Page(10, new ArrayList<Leader>(), new ArrayList<Matchable>());
    page.setBackgroundFilename("background.jpg");
    assertEquals("background.jpg", page.getBackgroundFilename());
  }
}
