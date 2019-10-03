package com.olfybsppa.inglesaventurero.webscenelistactivityutest.collectors_basics;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.collectors.Background;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.ImageCredit;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPHint;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPPage;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPReply;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPScene;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CPSceneUTest {

  CPScene cpScene;

  CPHint hintApple0 = new CPHint(0, 0); //0
  CPHint hintBanana0 = new CPHint(0, 0); //1
  CPHint hintCat0 = new CPHint(0, 0);//2
  CPHint hintCat3 = new CPHint(3, 2);//2
  CPHint hintDog0 = new CPHint(0, 0);//3
  CPHint hintInk0 = new CPHint(0, 0);//8
  CPHint hintJam0 = new CPHint(0, 0);//9
  CPHint hintKettle0 = new CPHint(0, 0);//10
  CPHint hintOcean1 = new CPHint(1, 1);//14
  CPHint hintPan1 = new CPHint(1, 1);//15
  CPHint hintQuote0 = new CPHint(0, 0);//16
  CPHint hintUmbro0 = new CPHint(0, 0);//20
  CPHint hintWater0 = new CPHint(0, 0);//22
  HashMap<Integer, ArrayList<CPHint>> correctHints = new HashMap<>();

  CPReply replyApple1 = new CPReply(1, 0);//0
  CPReply replyApple2 = new CPReply(2, 0);//0
  CPReply replyBanana1 = new CPReply(1, 0);//1
  CPReply replyBanana2 = new CPReply(2, 0);//1
  CPReply replyCat1 = new CPReply(1, 0);//2
  CPReply replyCat2 = new CPReply(2, 0);//2
  CPReply replyCat4 = new CPReply(4, 1);//2
  CPReply replyCat5 = new CPReply(5, 1);//2
  CPReply replyInk1 = new CPReply(1, 0);//8
  CPReply replyJam1 = new CPReply(1, 0);//9
  CPReply replyOcean0 = new CPReply(0, 0);//14
  CPReply replyPan0 = new CPReply(0, 0);//15
  HashMap<Integer, ArrayList<CPReply>> correctReplies = new HashMap<>();

  CPPage page0 = new CPPage(0);
  CPPage page1 = new CPPage(1);
  CPPage page2 = new CPPage(2);
  CPPage page3 = new CPPage(3);
  CPPage page8 = new CPPage(8);
  CPPage page9 = new CPPage(9);
  CPPage page10 = new CPPage();
  CPPage page14 = new CPPage();
  CPPage page15 = new CPPage();
  CPPage page16 = new CPPage();
  CPPage page20 = new CPPage();
  CPPage page22 = new CPPage();
  ArrayList<CPPage> correctPages = new ArrayList<>();

  Background background0 = new Background("background0", "background0.jpg");
  Background background1 = new Background("background1", "background1.jpg");
  Background background2 = new Background("background2", "background2.jpg");
  Background background3 = new Background("background3", "background3.jpg");
  Background background8 = new Background("background8", "background8.jpg");
  Background background9 = new Background("background9", "background9.jpg");
  Background background10 = new Background("background10", "background10.jpg");
  Background background14 = new Background("background14", "background14.jpg");
  Background background15 = new Background("background15", "background15.jpg");
  Background background16 = new Background("background16", "background16.jpg");
  Background background20 = new Background("background20", "background20.jpg");
  Background background22 = new Background("background22", "background22.jpg");
  HashMap<Integer, Background> correctBackgrounds = new HashMap<>();

  ImageCredit imageCredit0 = new ImageCredit("imageCredit0");
  ImageCredit imageCredit1 = new ImageCredit("imageCredit1");
  ImageCredit imageCredit2  = new ImageCredit("imageCredit2");
  ImageCredit imageCredit8  = new ImageCredit("imageCredit8");
  ImageCredit imageCredit9  = new ImageCredit("imageCredit9");
  ImageCredit imageCredit10  = new ImageCredit("imageCredit10");
  ImageCredit imageCredit14  = new ImageCredit("imageCredit14");
  ImageCredit imageCredit15  = new ImageCredit("imageCredit15");
  ImageCredit imageCredit16  = new ImageCredit("imageCredit16");
  ImageCredit imageCredit20  = new ImageCredit("imageCredit20");
  ImageCredit imageCredit22  = new ImageCredit("imageCredit22");
  HashSet<ImageCredit> correctImageCredits = new HashSet<>();

  Attribution attribution0a = new Attribution("chE0a", "chS0a", imageCredit0);
  Attribution attribution0b = new Attribution("chE0b", "chS0b", imageCredit0);
  Attribution attribution1a = new Attribution("chE1a", "chS1a", imageCredit1);
  Attribution attribution1b = new Attribution("chE1b", "chS1b", imageCredit1);
  Attribution attribution1c = new Attribution("chE1b", "chS1b", imageCredit1);
  Attribution attribution2 = new Attribution("chE2", "chS2", imageCredit2);
  Attribution attribution3 = new Attribution("chE3", "chS3", imageCredit2);
  Attribution attribution8 = new Attribution("chE8", "chS8", imageCredit8);
  Attribution attribution9 = new Attribution("chE9", "chS9", imageCredit9);
  Attribution attribution10 = new Attribution("chE10", "chS10", imageCredit10);
  Attribution attribution14 = new Attribution("chE14", "chS14", imageCredit14);
  Attribution attribution15 = new Attribution("chE15", "chS15", imageCredit15);
  Attribution attribution16 = new Attribution("chE16", "chS16", imageCredit16);
  Attribution attribution20 = new Attribution("chE20", "chS20", imageCredit20);
  Attribution attribution22 = new Attribution("chE22", "chS22", imageCredit22);
  HashMap<String, HashSet<Attribution>> correctAttributions = new HashMap<>();


  @Before
  public void setUp() throws Exception {
    cpScene = new CPScene("Scene Name", "English Title", "Titulo en Espanol", "0", "0");

    page10.setPageName(10);
    page14.setPageName(14);
    page15.setPageName(15);
    page16.setPageName(16);
    page20.setPageName(20);
    page22.setPageName(22);

    correctPages.add(page0);
    correctPages.add(page1);
    correctPages.add(page2);
    correctPages.add(page3);
    correctPages.add(page8);
    correctPages.add(page9);
    correctPages.add(page10);
    correctPages.add(page14);
    correctPages.add(page15);
    correctPages.add(page16);
    correctPages.add(page20);
    correctPages.add(page22);

    cpScene.addPage(page0);
    cpScene.addPage(page1);
    cpScene.addPage(page2);
    cpScene.addPage(page3);
    cpScene.addPage(page8);
    cpScene.addPage(page9);
    cpScene.addPage(page10);
    cpScene.addPage(page14);
    cpScene.addPage(page15);
    cpScene.addPage(page16);
    cpScene.addPage(page20);
    cpScene.addPage(page22);

    cpScene.addBackground(0, background0);
    cpScene.addBackground(1, background1);
    cpScene.addBackground(2, background2);
    cpScene.addBackground(3, background3);
    cpScene.addBackground(8, background8);
    cpScene.addBackground(9, background9);
    cpScene.addBackground(10, background10);
    cpScene.addBackground(14, background14);
    cpScene.addBackground(15, background15);
    cpScene.addBackground(16, background16);
    cpScene.addBackground(20, background20);
    cpScene.addBackground(22, background22);

    correctBackgrounds.put(0, background0);
    correctBackgrounds.put(1, background1);
    correctBackgrounds.put(2, background2);
    correctBackgrounds.put(3, background3);
    correctBackgrounds.put(8, background8);
    correctBackgrounds.put(9, background9);
    correctBackgrounds.put(10, background10);
    correctBackgrounds.put(14, background14);
    correctBackgrounds.put(15, background15);
    correctBackgrounds.put(16, background16);
    correctBackgrounds.put(20, background20);
    correctBackgrounds.put(22, background22);

    cpScene.addAttribution(background0.getBackgroundName(), attribution0a);
    cpScene.addAttribution(background0.getBackgroundName(), attribution0b);
    cpScene.addAttribution(background1.getBackgroundName(), attribution1a);
    cpScene.addAttribution(background1.getBackgroundName(), attribution1b);
    cpScene.addAttribution(background1.getBackgroundName(), attribution1c);
    cpScene.addAttribution(background2.getBackgroundName(), attribution2);
    cpScene.addAttribution(background3.getBackgroundName(), attribution3);
    cpScene.addAttribution(background8.getBackgroundName(), attribution8);
    cpScene.addAttribution(background9.getBackgroundName(), attribution9);
    cpScene.addAttribution(background10.getBackgroundName(), attribution10);
    cpScene.addAttribution(background14.getBackgroundName(), attribution14);
    cpScene.addAttribution(background15.getBackgroundName(), attribution15);
    cpScene.addAttribution(background16.getBackgroundName(), attribution16);
    cpScene.addAttribution(background20.getBackgroundName(), attribution20);
    cpScene.addAttribution(background22.getBackgroundName(), attribution22);

    HashSet<Attribution> hashset0 = new HashSet<>();
    hashset0.add(attribution0a);
    hashset0.add(attribution0b);
    correctAttributions.put(background0.getBackgroundName(), hashset0);
    HashSet<Attribution> hashset1 = new HashSet<>();
    hashset1.add(attribution1a);
    hashset1.add(attribution1b);
    hashset1.add(attribution1c);
    correctAttributions.put(background1.getBackgroundName(), hashset1);
    correctAttributions.put(background2.getBackgroundName(), attrHashSet(attribution2));
    correctAttributions.put(background3.getBackgroundName(), attrHashSet(attribution3));
    correctAttributions.put(background8.getBackgroundName(), attrHashSet(attribution8));
    correctAttributions.put(background9.getBackgroundName(), attrHashSet(attribution9));
    correctAttributions.put(background10.getBackgroundName(), attrHashSet(attribution10));
    correctAttributions.put(background14.getBackgroundName(), attrHashSet(attribution14));
    correctAttributions.put(background15.getBackgroundName(), attrHashSet(attribution15));
    correctAttributions.put(background16.getBackgroundName(), attrHashSet(attribution16));
    correctAttributions.put(background20.getBackgroundName(), attrHashSet(attribution20));
    correctAttributions.put(background22.getBackgroundName(), attrHashSet(attribution22));

    correctImageCredits.add(imageCredit0);
    correctImageCredits.add(imageCredit1);
    correctImageCredits.add(imageCredit2);
    correctImageCredits.add(imageCredit8);
    correctImageCredits.add(imageCredit9);
    correctImageCredits.add(imageCredit10);
    correctImageCredits.add(imageCredit14);
    correctImageCredits.add(imageCredit15);
    correctImageCredits.add(imageCredit16);
    correctImageCredits.add(imageCredit20);
    correctImageCredits.add(imageCredit22);

    hintApple0.setEnglishPhrase("apple 0");
    hintBanana0.setEnglishPhrase("banana 0");
    hintCat0.setEnglishPhrase("cat 0");
    hintCat3.setEnglishPhrase("cat 3");
    hintDog0.setEnglishPhrase("dog 0");
    hintInk0.setEnglishPhrase("ink 0");
    hintJam0.setEnglishPhrase("jam 0");
    hintKettle0.setEnglishPhrase("kettle 0");
    hintOcean1.setEnglishPhrase("ocean 1");
    hintPan1.setEnglishPhrase("pan 1");
    hintQuote0.setEnglishPhrase("quote 0");
    hintUmbro0.setEnglishPhrase("umbro 0");
    hintWater0.setEnglishPhrase("water 0");

    cpScene.addHint(0, hintApple0);
    cpScene.addHint(1, hintBanana0);
    cpScene.addHint(2, hintCat0);
    cpScene.addHint(2, hintCat3);
    cpScene.addHint(3, hintDog0);
    cpScene.addHint(8, hintInk0);
    cpScene.addHint(9, hintJam0);
    cpScene.addHint(10, hintKettle0);
    cpScene.addHint(14, hintOcean1);
    cpScene.addHint(15, hintPan1);
    cpScene.addHint(16, hintQuote0);
    cpScene.addHint(20, hintUmbro0);
    cpScene.addHint(22, hintWater0);

    correctHints.put(0, hintList(hintApple0));
    correctHints.put(1, hintList(hintBanana0));
    correctHints.put(2, hintList(hintCat0, hintCat3));
    correctHints.put(3, hintList(hintDog0));
    correctHints.put(8, hintList(hintInk0));
    correctHints.put(9, hintList(hintJam0));
    correctHints.put(10, hintList(hintKettle0));
    correctHints.put(14, hintList(hintOcean1));
    correctHints.put(15, hintList(hintPan1));
    correctHints.put(16, hintList(hintQuote0));
    correctHints.put(20, hintList(hintUmbro0));
    correctHints.put(22, hintList(hintWater0));

    replyApple1.setEnglishPhrase("apple 1");
    replyApple2.setEnglishPhrase("apple 2");
    replyBanana1.setEnglishPhrase("banana 1");
    replyBanana2.setEnglishPhrase("banana 2");
    replyCat1.setEnglishPhrase("cat 1");
    replyCat2.setEnglishPhrase("cat 2");
    replyCat4.setEnglishPhrase("cat 4");
    replyCat5.setEnglishPhrase("cat 5");
    replyInk1.setEnglishPhrase("ink 1");
    replyJam1.setEnglishPhrase("jam 1");
    replyOcean0.setEnglishPhrase("ocean 1");
    replyPan0.setEnglishPhrase("pan 0");

    cpScene.addReply(0, replyApple1);
    cpScene.addReply(0, replyApple2);
    cpScene.addReply(1, replyBanana1);
    cpScene.addReply(1, replyBanana2);
    cpScene.addReply(2, replyCat1);
    cpScene.addReply(2, replyCat2);
    cpScene.addReply(2, replyCat4);
    cpScene.addReply(2, replyCat5);
    cpScene.addReply(8, replyInk1);
    cpScene.addReply(9, replyJam1);
    cpScene.addReply(14, replyOcean0);
    cpScene.addReply(15, replyPan0);

    correctReplies.put(0, replyList(replyApple1, replyApple2));
    correctReplies.put(1, replyList(replyBanana1, replyBanana2));
    correctReplies.put(2, replyList(replyCat1, replyCat2, replyCat4, replyCat5));
    correctReplies.put(8, replyList(replyInk1));
    correctReplies.put(9, replyList(replyJam1));
    correctReplies.put(14, replyList(replyOcean0));
    correctReplies.put(15, replyList(replyPan0));
  }

  private ArrayList<CPReply> replyList (CPReply... replys) {
    ArrayList<CPReply> replies = new ArrayList<>();
    for (CPReply reply :replys) {
      replies.add(reply);
    }
    return replies;
  }

  private ArrayList<CPHint> hintList (CPHint... hints) {
    ArrayList<CPHint> list = new ArrayList<>();
    for (CPHint hint: hints) {
      list.add(hint);
    }
    return list;
  }

  private HashSet<Attribution> attrHashSet(Attribution attr) {
    HashSet<Attribution> hashSet = new HashSet<>();
    hashSet.add(attr);
    return hashSet;
  }

  @Test
  public void testConstructor() {
    assertEquals("Scene Name", cpScene.getSceneName());
    assertEquals("English Title", cpScene.getEnglishTitle());
    assertEquals("Titulo en Espanol", cpScene.getSpanishTitle());
  }

  @Test
  public void testAddPageAndGetPages() {
    assertEquals(correctPages, cpScene.getPages());
  }

  @Test
  public void testAddAndGetBackgrounds () {
    assertEquals(correctBackgrounds, cpScene.getBkgdsByPageName());
  }

  @Test
  public void testAddAndGetAttributions () {
    assertEquals(correctAttributions, cpScene.getAttrsByBkgdName());
  }

  @Test
  public void testGetImageCreditsFromAddedAttributions () {
    assertEquals(correctImageCredits, cpScene.getImageCredits());
  }

  @Test
  public void testAddAndGetHints () {
    assertEquals(correctHints, cpScene.getHintsByPageName());
  }

  @Test
  public void testAddRepiesAndGetHints () {
    assertEquals(correctReplies, cpScene.getRepliesByPageName());
  }

}
