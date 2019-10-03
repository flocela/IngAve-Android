package com.olfybsppa.inglesaventurero.webscenelistactivityutest.collectors_basics;

import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPPage;

import org.junit.Before;

public class CPPageIsSameUTest {
  private CPPage page;
  private CPPage comparisonPage;
  /*
  Hint hintC0;
  Hint hintC3;

  Reply replyC1;
  Reply replyC2;
  Reply replyC4;
  Reply replyC5;*/
  
  @Before
  protected  void setUp() throws Exception {
    page = new CPPage();
    page.setPageName(3);
/*
    hintC0 = new Hint(3, 0);
    hintC0.setNormalStartTime(30);
    hintC0.setNormalEndTime(31);
    hintC0.setSlowStartTime(32);
    hintC0.setSlowEndTime(33);
    hintC0.setEngPhrase("cat 0");
    hintC0.setSpnPhrase("next -1");
    hintC0.setWFWEng("cat 0");
    hintC0.setWFWSpn("position cat 0");
    hintC0.setExplanation("explanation cat 0");

    hintC3 = new Hint(3, 3);
    hintC3.setNormalStartTime(33);
    hintC3.setNormalEndTime(34);
    hintC3.setSlowStartTime(35);
    hintC3.setSlowEndTime(36);
    hintC3.setEngPhrase("cat 3");
    hintC3.setSpnPhrase("next -1");
    hintC3.setWFWEng("cat 3");
    hintC3.setWFWSpn("position cat 3");
    hintC3.setExplanation("explanation cat 3");

    replyC1 = new Reply(3, 1, 0);
    replyC1.setFollowingPage(22);
    replyC1.setNormalStartTime(31);
    replyC1.setNormalEndTime(32);
    replyC1.setSlowStartTime(33);
    replyC1.setSlowEndTime(34);
    replyC1.setEngPhrase("cat 1");
    replyC1.setSpnPhrase("next 22");
    replyC1.setWFWEng("cat 1");
    replyC1.setWFWSpn("position cat 1");
    replyC1.setExplanation("explanation cat 1");
    replyC1.setRegex("regex cat 1");

    replyC2 = new Reply(3, 2, 0);
    replyC2.setFollowingPage(4);
    replyC2.setNormalStartTime(32);
    replyC2.setNormalEndTime(33);
    replyC2.setSlowStartTime(34);
    replyC2.setSlowEndTime(35);
    replyC2.setEngPhrase("cat 2");
    replyC2.setSpnPhrase("next 4");
    replyC2.setWFWEng("cat 2");
    replyC2.setWFWSpn("position cat 2");
    replyC2.setExplanation("explanation cat 2");
    replyC2.setRegex("regex cat 2");

    replyC4 = new Reply(3, 4, 1);
    replyC4.setFollowingPage(23);
    replyC4.setNormalStartTime(34);
    replyC4.setNormalEndTime(35);
    replyC4.setSlowStartTime(36);
    replyC4.setSlowEndTime(37);
    replyC4.setEngPhrase("cat 4");
    replyC4.setSpnPhrase("next 23");
    replyC4.setWFWEng("cat 4");
    replyC4.setWFWSpn("position cat 4");
    replyC4.setExplanation("explanation cat 4");
    replyC4.setRegex("regex cat 4");

    replyC5 = new Reply(3, 5, 1);
    replyC5.setFollowingPage(4);
    replyC5.setNormalStartTime(35);
    replyC5.setNormalEndTime(36);
    replyC5.setSlowStartTime(37);
    replyC5.setSlowEndTime(38);
    replyC5.setEngPhrase("cat 5");
    replyC5.setSpnPhrase("next 4");
    replyC5.setWFWEng("cat 5");
    replyC5.setWFWSpn("position cat 5");
    replyC5.setExplanation("explanation cat 5");
    replyC5.setRegex("regex cat 5");

    page.addHint(hintC0);
    page.addHint(hintC3);
    page.addReply(replyC1);
    page.addReply(replyC2);
    page.addReply(replyC4);
    page.addReply(replyC5);

    Hint cloneHintC0  = hintC0.clone();
    Hint cloneHintC3  = hintC3.clone();
    Reply cloneReplyC1 = replyC1.clone();
    Reply cloneReplyC2 = replyC2.clone();
    Reply cloneReplyC4 = replyC4.clone();
    Reply cloneReplyC5 = replyC5.clone();
*/
    comparisonPage = new CPPage();
    comparisonPage.setPageName(3);
  /*  comparisonPage.addHint(cloneHintC0);
    comparisonPage.addHint(cloneHintC3);
    comparisonPage.addReply(cloneReplyC1);
    comparisonPage.addReply(cloneReplyC2);
    comparisonPage.addReply(cloneReplyC4);
    comparisonPage.addReply(cloneReplyC5);*/

  }

/*
  @Test
  public void testHintPositionsSwitched () {
    Hint switchC0 = new Hint(3, 3); //<-- used to be Hint(3,0)
    switchC0.setNormalStartTime(30);
    switchC0.setNormalEndTime(31);
    switchC0.setSlowStartTime(32);
    switchC0.setSlowEndTime(33);
    switchC0.setEngPhrase("cat 0");
    switchC0.setSpnPhrase("next -1");
    switchC0.setWFWEng("cat 0");
    switchC0.setWFWSpn("position cat 0");
    switchC0.setExplanation("explanation cat 0");

    Hint switchC3 = new Hint(3,0); // <-- used to be Hint(3, 3)
    switchC3.setNormalStartTime(33);
    switchC3.setNormalEndTime(34);
    switchC3.setSlowStartTime(35);
    switchC3.setSlowEndTime(36);
    switchC3.setEngPhrase("cat 3");
    switchC3.setSpnPhrase("position cat 3");
    switchC3.setWFWEng("cat 3");
    switchC3.setWFWSpn("position cat 3");
    switchC3.setExplanation("explanation cat 3");

    Reply cloneReplyC1 = replyC1.clone();
    Reply cloneReplyC2 = replyC2.clone();
    Reply cloneReplyC4 = replyC4.clone();
    Reply cloneReplyC5 = replyC5.clone();

    comparisonPage = new CPPage();
    comparisonPage.setPageName(3);
    comparisonPage.addHint(switchC0);
    comparisonPage.addHint(switchC3);
    comparisonPage.addReply(cloneReplyC1);
    comparisonPage.addReply(cloneReplyC2);
    comparisonPage.addReply(cloneReplyC4);
    comparisonPage.addReply(cloneReplyC5);

    assertFalse(page.isSame(comparisonPage));
  }*/

}
