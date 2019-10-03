package com.olfybsppa.inglesaventurero.stagehouseactivityutest.collectors_basics;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.Hint;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Leader;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Matchable;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Page;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Reply;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class PageEqualsAndHashUTest {

  private Page origPage;
  private Page copyPage;

  private Hint hint00;
  private Hint hint22;
  private Hint hint54;
  private Hint hClone00;
  private Hint hClone22;
  private Hint hClone54;
  private Reply reply11;
  private Reply reply33;
  private Reply reply43;
  private Reply reply65;
  private Reply rClone11;
  private Reply rClone33;
  private Reply rClone43;
  private Reply rClone65;

  ArrayList<Leader>    leaders         = new ArrayList<>();
  ArrayList<Matchable> matchables      = new ArrayList<>();
  ArrayList<Leader>    leadersClone    = new ArrayList<>();
  ArrayList<Matchable> matchablesClone = new ArrayList<>();

  @Before
  public  void setUp() throws Exception {
    hint00  = new Hint (0, 0);
    hint22  = new Hint (2, 2);
    hint54  = new Hint (5, 4);
    hClone00 = hint00.clone();
    hClone22 = hint22.clone();
    hClone54 = hint54.clone();
    reply11 = new Reply(1, 1);
    reply11.setRegex("hello");
    reply33 = new Reply(3, 3);
    reply43 = new Reply(4, 3);
    reply65 = new Reply(6, 5);
    rClone11 = reply11.clone();
    rClone33 = reply33.clone();
    rClone43 = reply43.clone();
    rClone65 = reply65.clone();
    leaders.add(hint00);
    leaders.add(hint22);
    matchables.add(reply11);
    matchables.add(reply33);
    matchables.add(reply43);
    leadersClone.add(hClone00);
    leadersClone.add(hClone22);
    matchablesClone.add(rClone11);
    matchablesClone.add(rClone33);
    matchablesClone.add(rClone43);

    origPage = new Page(1, leaders, matchables);
    origPage.setBackgroundFilename("one.jpg");

    copyPage = new Page(1, leadersClone, matchablesClone);
    copyPage.setBackgroundFilename("one.jpg");

  }

  // testing .equals().
  // test page with empty lines.
  @Test
  public void equalsWithEmptyLines () {
    Page pageOrig = new Page(0, new ArrayList<Leader>(), new ArrayList<Matchable>());
    Page pageSame = new Page(0, new ArrayList<Leader>(), new ArrayList<Matchable>());
    assertEquals(pageOrig, pageSame);
    assertEquals(pageOrig.hashCode(), pageSame.hashCode());
  }

  // testing .equals().
  // test page with multiple hints and replies.
  @Test
  public void equalsWithMultipleHintsAndReplies () {
    assertTrue(origPage.equals(copyPage));
    assertTrue(origPage.hashCode() == copyPage.hashCode());
  }

  // testing .equals().
  // case where page id is different.
  @Test
  public void equalsWithDiffPageIDs () {
    Page diffPage = new Page(1, leaders, matchables);
    assertFalse(origPage.equals(diffPage));
    assertFalse(origPage.hashCode() == diffPage.hashCode());
  }

  // testing .equals().
  // setAsFirstPage has been changed.
  @Test
  public void differentSetAsFirst () {
    copyPage.setAsFirstPage(true);
    assertFalse(origPage.equals(copyPage));
    assertFalse(origPage.hashCode() == copyPage.hashCode());
  }

  // testing .equals().
  // setBackgroundFileName has been changed.
  @Test
  public void differentBackgroundName () {
    copyPage.setBackgroundFilename("two.jpg");
    assertFalse(origPage.equals(copyPage));
    assertFalse(origPage.hashCode() == copyPage.hashCode());
  }

  // testing .equals().
  // Matchable Line has been matched.
  @Test
  public void pageOneHasMatchedAReply () {
    ArrayList<String> responses = new ArrayList<>();
    responses.add("hello");

    Page clonePage = new Page(1, leadersClone, matchablesClone);
    clonePage.setBackgroundFilename("one.jpg");
    assertTrue(origPage.equals(clonePage));
    assertTrue(origPage.hashCode() == clonePage.hashCode());
    origPage.acceptResponses(responses);

    assertFalse(origPage.equals(clonePage));
    assertFalse(origPage.hashCode() == clonePage.hashCode());
  }

  // testing .equals().
  // Matchable Line has not been matched.
  @Test
  public void notMatchingPageDoesntChangePage () {
    ArrayList<String> responses = new ArrayList<>();
    responses.add("goodbye");

    Page clonePage = new Page(1, leadersClone, matchablesClone);
    clonePage.setBackgroundFilename("one.jpg");
    assertTrue(origPage.equals(clonePage));
    assertTrue(origPage.hashCode() == clonePage.hashCode());
    origPage.acceptResponses(responses);
    assertTrue(origPage.equals(clonePage));
    assertTrue(origPage.hashCode() == clonePage.hashCode());
  }

  // testing. equals().
  // Matchable Line has been matched, then reset() is called.
  @Test
  public void matchingPageThenClearGroups () {
    ArrayList<String> responses = new ArrayList<>();
    responses.add("hello");

    Page clonePage = new Page(1, leadersClone, matchablesClone);
    clonePage.setBackgroundFilename("one.jpg");
    assertTrue(origPage.equals(clonePage));
    assertTrue(origPage.hashCode() == clonePage.hashCode());
    origPage.acceptResponses(responses);
    assertFalse(origPage.equals(clonePage));
    assertFalse(origPage.hashCode() == clonePage.hashCode());

    origPage.reset();
    assertTrue(origPage.equals(clonePage));
    assertTrue(origPage.hashCode() == clonePage.hashCode());
  }
}
