package com.olfybsppa.inglesaventurero.stageactivity;


import com.olfybsppa.inglesaventurero.stageactivity.collectors.Hint;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Leader;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Matchable;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Page;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Reply;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Used for Script Tests. Called from TrackerMakerFragment work().
 * Pages are copied into AlphabetTracker-java.odt to be better able to see them.
 */
public class AlphabeticalTracker {
   public static Tracker getTracker() {
     HashMap<Integer, Page> pages = new HashMap<>();
     addPages(pages);
     return new Tracker(pages);
   }

  private static void addPages (HashMap<Integer, Page> pages) {
    Hint hintApple0 = new Hint(0, 0); //0
    hintApple0.setEngPhrase("Page:0 GroupID:0");
    hintApple0.setWFWEng("Time plays: 10 for 1");
    hintApple0.setWFWSpn("Next page: -1 _ _");
    hintApple0.setEngExplanation("eng_explanation apple 0d");
    hintApple0.setSpnExplanation("spn_explanation apple 0d");
    hintApple0.setTimes(10, 11, 12, 13);
    hintApple0.setSpnPhrase("Page:0 GroupID:0");

    Hint hintBanana0 = new Hint(0, 0); //1
    hintBanana0.setEngPhrase("Page:1 GroupID:0");
    hintBanana0.setWFWEng("Time plays: 22 for 1");
    hintBanana0.setWFWSpn("Next  page: -1 _ _");
    hintBanana0.setEngExplanation("eng_explanation banana 0d");
    hintBanana0.setSpnExplanation("spn_explanation banana 0d");
    hintBanana0.setTimes(22, 23, 24, 25);
    hintBanana0.setSpnPhrase("Page:1 GroupID:0");

    Hint hintCat0 = new Hint(0, 0);//2
    hintCat0.setEngPhrase("Page:2 GroupID:0");
    hintCat0.setWFWEng("Time plays: 34 for 1");
    hintCat0.setWFWSpn("Next  page: -1 _ _");
    hintCat0.setEngExplanation("eng_explanation cat 0d");
    hintCat0.setSpnExplanation("spn_explanation cat 0d");
    hintCat0.setTimes(34, 35, 36, 37);
    hintCat0.setSpnPhrase("Page:2 GroupID:0");

    Hint hintCat3 = new Hint(3, 2);//2
    hintCat3.setEngPhrase("Page:2 GroupID:2");
    hintCat3.setWFWEng("Time plays: 46 for 1");
    hintCat3.setWFWSpn("Next  page: -1 _ _");
    hintCat3.setEngExplanation("eng_explanation cat 3d");
    hintCat3.setSpnExplanation("spn_explanation cat 3d");
    hintCat3.setTimes(46, 47, 48, 49);
    hintCat3.setSpnPhrase("Page:2 GroupID:2");

    Hint hintDog0 = new Hint(0, 0);//3
    hintDog0.setEngPhrase("Page:3 GroupID:0");
    hintDog0.setWFWEng("Time plays: 58 for 1");
    hintDog0.setWFWSpn("Next  page: 12 _ _");
    hintDog0.setEngExplanation("eng_explanation dog 0d");
    hintDog0.setSpnExplanation("spn_explanation dog 0d");
    hintDog0.setTimes(58, 59, 60, 61);
    hintDog0.setSpnPhrase("Page:3 GroupID:0");
    hintDog0.setFollowingPage(12);

    Hint hintElephant1 = new Hint(1, 1);//4
    hintElephant1.setEngPhrase("Page:4 GroupID:1");
    hintElephant1.setWFWEng("Time plays: 180 for 1");
    hintElephant1.setWFWSpn("Next  page: -1 _ _");
    hintElephant1.setEngExplanation("eng_explanation dog 0d");
    hintElephant1.setSpnExplanation("spn_explanation dog 0d");
    hintElephant1.setTimes(180, 181, 182, 183);
    hintElephant1.setSpnPhrase("Page:4 GroupID:1");
    hintElephant1.setFollowingPage(6);

    Hint hintFlower0 = new Hint(0,0);//5
    hintFlower0.setEngPhrase("Page:5 GroupID:0");
    hintFlower0.setWFWEng("Time plays: 420 for 1");
    hintFlower0.setWFWSpn("Next  page: 25 _ _");
    hintFlower0.setEngExplanation("eng_explanation flower 0a");
    hintFlower0.setSpnExplanation("spn_explanation flower 0a");
    hintFlower0.setTimes(420, 421, 422, 423);
    hintFlower0.setSpnPhrase("Page:5 GroupID:0");
    hintFlower0.setFollowingPage(25);

    Hint hintFlower1 = new Hint(1, 1);//5
    hintFlower1.setEngPhrase("Page:5 GroupID:1");
    hintFlower1.setWFWEng("Time plays: 430 for 1");
    hintFlower1.setWFWSpn("Next  page: 25 _ _");
    hintFlower1.setEngExplanation("eng_explanation flower 1a");
    hintFlower1.setSpnExplanation("spn_explanation flower 1a");
    hintFlower1.setTimes(430, 431, 432, 433);
    hintFlower1.setSpnPhrase("Page:5 GroupID:1");
    hintFlower1.setFollowingPage(25);

    Hint hintFlower3 = new Hint(3, 3);//5
    hintFlower3.setEngPhrase("Page:5 GroupID:3");
    hintFlower3.setWFWEng("Time plays: 440 for 1");
    hintFlower3.setWFWSpn("Next  page: 25 _ _");
    hintFlower3.setEngExplanation("eng_explanation flower 3a");
    hintFlower3.setSpnExplanation("spn_explanation flower 3a");
    hintFlower3.setTimes(440, 441, 442, 443);
    hintFlower3.setSpnPhrase("Page:5 GroupID:3");
    hintFlower3.setFollowingPage(25);

    Hint hintFlower4 = new Hint(4, 4);//5
    hintFlower4.setEngPhrase("Page:5 GroupID:4");
    hintFlower4.setWFWEng("Time plays: 450 for 1");
    hintFlower4.setWFWSpn("Next  page: 25 _ _");
    hintFlower4.setEngExplanation("eng_explanation flower 4a");
    hintFlower4.setSpnExplanation("spn_explanation flower 4a");
    hintFlower4.setTimes(450, 451, 452, 453);
    hintFlower4.setSpnPhrase("Page:5 GroupID:4");
    hintFlower4.setFollowingPage(25);

    Hint hintGiraffe1 = new Hint(1, 1);//6
    hintGiraffe1.setEngPhrase("Page:6 GroupID:1");
    hintGiraffe1.setWFWEng("Time plays: 400 for 1");
    hintGiraffe1.setWFWSpn("Next  page: -1 _ _");
    hintGiraffe1.setEngExplanation("eng_explanation giraffe 0d");
    hintGiraffe1.setSpnExplanation("spn_explanation giraffe 0d");
    hintGiraffe1.setTimes(400, 401, 402, 403);
    hintGiraffe1.setSpnPhrase("Page:6 GroupID:1");
    hintGiraffe1.setFollowingPage(-1);

    Hint hintInk0 = new Hint(0, 0);//8
    hintInk0.setEngPhrase("Page:8 GroupID:0");
    hintInk0.setWFWEng("Time plays: 62 for 1");
    hintInk0.setWFWSpn("Next  page: -1 _ _");
    hintInk0.setEngExplanation("eng_explanation ink 0d");
    hintInk0.setSpnExplanation("spn_explanation ink 0d");
    hintInk0.setTimes(62, 63, 64, 65);
    hintInk0.setSpnPhrase("Page:8 GroupID:0");

    Hint hintJam0 = new Hint(0, 0);//9
    hintJam0.setEngPhrase("Page:9 GroupID:0");
    hintJam0.setWFWEng("Time plays: 70 for 1");
    hintJam0.setWFWSpn("Next  page: -1 _ _");
    hintJam0.setEngExplanation("eng_explanation jam 0d");
    hintJam0.setSpnExplanation("spn_explanation jam 0d");
    hintJam0.setTimes(70, 71, 72,73);
    hintJam0.setSpnPhrase("Page:9 GroupID:0");

    Hint hintKettle0 = new Hint(0, 0);//10
    hintKettle0.setEngPhrase("Page:10 GroupID:0");
    hintKettle0.setWFWEng("Time plays: 78 for 1");
    hintKettle0.setWFWSpn("Next  page: -1 _ _");
    hintKettle0.setEngExplanation("eng_explanation kettle 0d");
    hintKettle0.setSpnExplanation("spn_explanation kettle 0d");
    hintKettle0.setTimes(78, 79, 80, 81);
    hintKettle0.setFollowingPage(-1);
    hintKettle0.setSpnPhrase("Page:10 GroupID:0");

    Hint hintLlama0 = new Hint(0, 0);//11
    hintLlama0.setEngPhrase("Page:11 GroupID:0");
    hintLlama0.setWFWEng("Time plays: 270 for 1");
    hintLlama0.setWFWSpn("Next  page: -1 _ _");
    hintLlama0.setEngExplanation("eng_explanation Llama 0a");
    hintLlama0.setSpnExplanation("spn_explanation Llama 0a");
    hintLlama0.setTimes(270, 271, 272, 273);
    hintLlama0.setSpnPhrase("Page:11 GroupID:0");

    Hint hintLlama1 = new Hint(1, 1);//11
    hintLlama1.setEngPhrase("Page:11 GroupID:1");
    hintLlama1.setWFWEng("Time plays: 280 for 1");
    hintLlama1.setWFWSpn("Next  page: -1 _ _");
    hintLlama1.setEngExplanation("eng_explanation Llama 0b");
    hintLlama1.setSpnExplanation("spn_explanation Llama 0b");
    hintLlama1.setTimes(280, 281, 282, 283);
    hintLlama1.setSpnPhrase("Page:11 GroupID:1");
    
    Hint hintMaple0 = new Hint(0, 0);//12
    hintMaple0.setEngPhrase("Page:12 GroupID:0");
    hintMaple0.setWFWEng("Time plays: 130 for 1");
    hintMaple0.setWFWSpn("Next  page: -1 _ _");
    hintMaple0.setEngExplanation("eng_explanation Maple 0a");
    hintMaple0.setSpnExplanation("spn_explanation Maple 0a");
    hintMaple0.setTimes(130, 131, 132, 133);
    hintMaple0.setSpnPhrase("Page:12 GroupID:0");

    Hint hintMaple1 = new Hint(1, 1);//12
    hintMaple1.setEngPhrase("Page:12 GroupID:1");
    hintMaple1.setWFWEng("Time plays: 140 for 1");
    hintMaple1.setWFWSpn("Next  page: -1 _ _");
    hintMaple1.setEngExplanation("eng_explanation maple 0b");
    hintMaple1.setSpnExplanation("spn_explanation maple 0b");
    hintMaple1.setTimes(140, 141, 142, 143);
    hintMaple1.setSpnPhrase("Page:12 GroupID:1");

    Hint hintOcean1 = new Hint(0, 0);//14
    hintOcean1.setEngPhrase("Page:14 GroupID:0");
    hintOcean1.setWFWEng("Time plays: 86 for 1");
    hintOcean1.setWFWSpn("Next  page: -1 _ _");
    hintOcean1.setEngExplanation("eng_explanation ocean 1d");
    hintOcean1.setSpnExplanation("spn_explanation ocean 1d");
    hintOcean1.setTimes(86, 87, 88, 89);
    hintOcean1.setFollowingPage(-1);
    hintOcean1.setSpnPhrase("Page:14 GroupID:0");

    Hint hintPan1 = new Hint(0,0);//15
    hintPan1.setEngPhrase("Page:15 GroupID:0");
    hintPan1.setWFWEng("Time plays: 94 for 1");
    hintPan1.setWFWSpn("Next  page: -1 _ _");
    hintPan1.setEngExplanation("eng_explanation pan 1d");
    hintPan1.setSpnExplanation("spn_explanation pan 1d");
    hintPan1.setTimes(94, 95, 96, 97);
    hintPan1.setSpnPhrase("Page:15 GroupID:0");

    Hint hintQuote0 = new Hint(0, 0);//16
    hintQuote0.setEngPhrase("Page:16 GroupID:0");
    hintQuote0.setWFWEng("Time plays: 102 for 1");
    hintQuote0.setWFWSpn("Next  page: -1 _ _");
    hintQuote0.setEngExplanation("eng_explanation quote 0d");
    hintQuote0.setSpnExplanation("spn_explanation quote 0d");
    hintQuote0.setTimes(102, 103, 104, 105);
    hintQuote0.setSpnPhrase("Page:16 GroupID:0");

    Hint hintQuote1 = new Hint(1, 1);//16
    hintQuote1.setEngPhrase("Page:16 GroupID:1");
    hintQuote1.setWFWEng("Time plays: 118 for 1");
    hintQuote1.setWFWSpn("Next  page: -1 _ _");
    hintQuote1.setEngExplanation("eng_explanation quote 1d");
    hintQuote1.setSpnExplanation("spn_explanation quote 1d");
    hintQuote1.setTimes(118, 119, 120, 121);
    hintQuote1.setSpnPhrase("Page:16 GroupID:1");

    Hint hintParty1 = new Hint(0, 0);//17
    hintParty1.setEngPhrase("Page:17 GroupID:0");
    hintParty1.setWFWEng("Time plays: 240 for 1");
    hintParty1.setWFWSpn("Next  page: -1 _ _");
    hintParty1.setEngExplanation("eng_explanation party 1d");
    hintParty1.setSpnExplanation("spn_explanation party 1d");
    hintParty1.setTimes(240, 241, 242, 243);
    hintParty1.setFollowingPage(-1);
    hintParty1.setSpnPhrase("Page:17 GroupID:0");

    Hint hintTiger1 = new Hint(1, 1);//19
    hintTiger1.setEngPhrase("Page:19 GroupID:1");
    hintTiger1.setWFWEng("Time plays: 190 for 1");
    hintTiger1.setWFWSpn("Next  page: -1 _ _");
    hintTiger1.setEngExplanation("eng_explanation tiger 1d");
    hintTiger1.setSpnExplanation("spn_explanation tiger 1d");
    hintTiger1.setTimes(190, 191, 192, 193);
    hintTiger1.setSpnPhrase("Page:19 GroupID:1");

    Hint hintTiger2 = new Hint(2, 2);//19
    hintTiger2.setEngPhrase("Page:19 GroupID:2");
    hintTiger2.setWFWEng("Time plays: 200 for 1");
    hintTiger2.setWFWSpn("Next  page: -1 _ _");
    hintTiger2.setEngExplanation("eng_explanation tiger 2d");
    hintTiger2.setSpnExplanation("spn_explanation tiger 2d");
    hintTiger2.setTimes(200, 201, 202, 203);
    hintTiger2.setSpnPhrase("Page:19 GroupID:2");

    Hint hintUmbro1 = new Hint(1, 1);//20
    hintUmbro1.setEngPhrase("Page:20 GroupID:1");
    hintUmbro1.setWFWEng("Time plays: 114 for 1");
    hintUmbro1.setWFWSpn("Next  page: -1 _ _");
    hintUmbro1.setEngExplanation("eng_explanation umbro 1d");
    hintUmbro1.setSpnExplanation("spn_explanation umbro 1d");
    hintUmbro1.setTimes(114, 115, 116, 117);
    hintUmbro1.setSpnPhrase("Page:20 GroupID:1");

    Hint hintUmbro2 = new Hint(2, 2);//20
    hintUmbro2.setEngPhrase("Page:20 GroupID:2");
    hintUmbro2.setWFWEng("Time plays: 122 for 1");
    hintUmbro2.setWFWSpn("Next  page: -1 _ _");
    hintUmbro2.setEngExplanation("eng_explanation umbro 2d");
    hintUmbro2.setSpnExplanation("spn_explanation umbro 2d");
    hintUmbro2.setTimes(122, 123, 124, 125);
    hintUmbro2.setSpnPhrase("Page:20 GroupID:2");

    Hint hintViolet0 = new Hint(0, 0);//21
    hintViolet0.setFollowingPage(-1);
    hintViolet0.setEngPhrase("Page:21 GroupID:0");
    hintViolet0.setWFWEng("Time plays: 220 for 1");
    hintViolet0.setWFWSpn("Next  page: -1 _ _");
    hintViolet0.setEngExplanation("eng_explanation violet 0d");
    hintViolet0.setSpnExplanation("spn_explanation violet 0d");
    hintViolet0.setTimes(220, 221, 222, 223);
    hintViolet0.setSpnPhrase("Page:21 GroupID:0");

    Hint hintWater0 = new Hint(0, 0);//22
    hintWater0.setFollowingPage(-1);
    hintWater0.setEngPhrase("Page:22 GroupID:0");
    hintWater0.setWFWEng("Time plays: 110 for 1");
    hintWater0.setWFWSpn("Next  page: -1 _ _");
    hintWater0.setEngExplanation("eng_explanation water 0d");
    hintWater0.setSpnExplanation("spn_explanation water 0d");
    hintWater0.setTimes(110, 111, 112, 113);
    hintWater0.setSpnPhrase("Page:22 GroupID:0");

    Hint hintXenon0 = new Hint(0, 0);//23
    hintXenon0.setFollowingPage(-1);
    hintXenon0.setEngPhrase("Page:23 GroupID:0");
    hintXenon0.setWFWEng("Time plays: 230 for 1");
    hintXenon0.setWFWSpn("Next  page: -1 _ _");
    hintXenon0.setEngExplanation("eng_explanation xenon 0d");
    hintXenon0.setSpnExplanation("spn_explanation xenon 0d");
    hintXenon0.setTimes(290, 291, 292, 293);
    hintXenon0.setSpnPhrase("Page:23 GroupID:0");

    Hint hintYellow0 = new Hint(0, 0);//24
    hintYellow0.setFollowingPage(-1);
    hintYellow0.setEngPhrase("Page:24 GroupID:0");
    hintYellow0.setWFWEng("Time plays: 290 for 1");
    hintYellow0.setWFWSpn("Next  page: -1 _ _");
    hintYellow0.setEngExplanation("eng_explanation yellow 0d");
    hintYellow0.setSpnExplanation("spn_explanation yellow 0d");
    hintYellow0.setTimes(230, 231, 232, 233);
    hintYellow0.setSpnPhrase("Page:24 GroupID:0");

    Hint hintZebra0 = new Hint(0, 0);//25
    hintZebra0.setFollowingPage(-1);
    hintZebra0.setEngPhrase("Page:25 GroupID:0");
    hintZebra0.setWFWEng("Time plays: 340 for 1");
    hintZebra0.setWFWSpn("Next  page: -1 _ _");
    hintZebra0.setEngExplanation("eng_explanation zebra 0a");
    hintZebra0.setSpnExplanation("spn_explanation zebra 0a");
    hintZebra0.setTimes(340, 341, 342, 343);
    hintZebra0.setSpnPhrase("Page:25 GroupID:0");

    Reply replyApple1 = new Reply(1, 1);//0
    replyApple1.setEngPhrase("Page:0 GroupID:1 Position:1");
    replyApple1.setWFWEng("Time plays: 14 for 1");
    replyApple1.setWFWSpn("Next  page: 8 _ _");
    replyApple1.setRegex("Page:0 GroupID:1 Position:1");
    replyApple1.setEngExplanation("eng_explanation apple 1");
    replyApple1.setTimes(14, 15, 16, 17);
    replyApple1.setFollowingPage(8);
    replyApple1.setSpnPhrase("Page:0 GroupID:1 Position:1");

    Reply replyApple2 = new Reply(2, 1);//0
    replyApple2.setEngPhrase("Page:0 GroupID:1 Position:2");
    replyApple2.setWFWEng("Time plays: 18 for 1");
    replyApple2.setWFWSpn("Next  page: 1 _ _");
    replyApple2.setRegex("Page:0 GroupID:1 Position:2");
    replyApple2.setEngExplanation("eng_explanation apple 2");
    replyApple2.setSpnExplanation("spn_explanation apple 2");
    replyApple2.setTimes(18, 19, 20, 21);
    replyApple2.setFollowingPage(1);
    replyApple2.setSpnPhrase("Page:0 GroupID:1 Position:2");

    Reply replyApple3 = new Reply(3, 1);//0
    replyApple3.setEngPhrase("Page:0 GroupID:1 Position:3");
    replyApple3.setWFWEng("Time plays: 160 for 1");
    replyApple3.setWFWSpn("Next  page: 4 _ _");
    replyApple3.setRegex("Page:0 GroupID:1 Position:3");
    replyApple3.setEngExplanation("eng_explanation apple 3");
    replyApple3.setTimes(160, 161, 162, 163);
    replyApple3.setFollowingPage(4);
    replyApple3.setSpnPhrase("Page:0 GroupID:1 Position:3");

    Reply replyApple4 = new Reply(3, 1);//0
    replyApple4.setEngPhrase("Page:0 GroupID:1 Position:4");
    replyApple4.setWFWEng("Time plays: 320 for 1");
    replyApple4.setWFWSpn("Next  page: 18 _ _");
    replyApple4.setRegex("Page:0 GroupID:1 Position:4");
    replyApple4.setEngExplanation("eng_explanation apple 4");
    replyApple4.setTimes(320 , 321, 322, 323);
    replyApple4.setFollowingPage(18);
    replyApple4.setSpnPhrase("Page:0 GroupID:1 Position:4");

    Reply replyBanana1 = new Reply(1, 1);//1
    replyBanana1.setEngPhrase("Page:1 GroupID:1 Position:1");
    replyBanana1.setWFWEng("Time plays: 26 for 1");
    replyBanana1.setWFWSpn("Next  page: 25 _ _");
    replyBanana1.setRegex("Page:1 GroupID:1 Position:1");
    replyBanana1.setEngExplanation("eng_explanation banana 1e");
    replyBanana1.setTimes(26, 27, 28, 29);
    replyBanana1.setFollowingPage(25);
    replyBanana1.setSpnPhrase("Page:1 GroupID:1 Position:1");

    Reply replyBanana2 = new Reply(2, 1);//1
    replyBanana2.setEngPhrase("Page:1 GroupID:1 Position:2");
    replyBanana2.setWFWEng("Time plays: 30 for 1");
    replyBanana2.setWFWSpn("Next  page: 2 _ _");
    replyBanana2.setRegex("Page:1 GroupID:1 Position:2");
    replyBanana2.setEngExplanation("eng_explanation banana 2e");
    replyBanana2.setTimes(30, 31, 32, 33);
    replyBanana2.setFollowingPage(2);
    replyBanana2.setSpnPhrase("Page:1 GroupID:1 Position:2");

    Reply replyBanana3 = new Reply(3, 1);//1
    replyBanana3.setEngPhrase("Page:1 GroupID:1 Position:3");
    replyBanana3.setWFWEng("Time plays: 470 for 1");
    replyBanana3.setWFWSpn("Next page: 5 _ _");
    replyBanana3.setRegex("Page:1 GroupID:1 Position:3");
    replyBanana3.setEngExplanation("eng_explanation banana 3e");
    replyBanana3.setTimes(470, 471, 472, 473);
    replyBanana3.setFollowingPage(5);
    replyBanana3.setSpnPhrase("Page:1 GroupID:1 Position:3");

    Reply replyCat1 = new Reply(1, 1);//2
    replyCat1.setEngPhrase("Page:2 GroupID:1 Position:1");
    replyCat1.setWFWEng("Time plays: 38 for 1");
    replyCat1.setWFWSpn("Next  page: -1 _ _");
    replyCat1.setRegex("Page:2 GroupID:1 Position:1");
    replyCat1.setEngExplanation("eng_explanation cat 1e");
    replyCat1.setTimes(38, 39, 40, 41);
    replyCat1.setSpnPhrase("Page:2 GroupID:1 Position:1");

    Reply replyCat2 = new Reply(2, 1);//2
    replyCat2.setEngPhrase("Page:2 GroupID:1 Position:2");
    replyCat2.setWFWEng("Time plays: 42 for 1");
    replyCat2.setWFWSpn("Next  page: -1 _ _");
    replyCat2.setRegex("Page:2 GroupID:1 Position:2");
    replyCat2.setEngExplanation("eng_explanation cat 2e");
    replyCat2.setTimes(42, 43, 44, 45);
    replyCat2.setSpnPhrase("Page:2 GroupID:1 Position:2");

    Reply replyCat4 = new Reply(4, 3);//2
    replyCat4.setEngPhrase("Page:2 GroupID:3 Position:4");
    replyCat4.setWFWEng("Time plays: 50 for 1");
    replyCat4.setWFWSpn("Next  page: -1 _ _");
    replyCat4.setRegex("Page:2 GroupID:3 Position:4");
    replyCat4.setEngExplanation("eng_explanation cat 4e");
    replyCat4.setTimes(50, 51, 52, 53);
    replyCat4.setFollowingPage(23);
    replyCat4.setSpnPhrase("Page:2 GroupID:3 Position:4");

    Reply replyCat5 = new Reply(5, 3);//2
    replyCat5.setEngPhrase("Page:2 GroupID:3 Position:5");
    replyCat5.setWFWEng("Time plays: 54 for 1");
    replyCat5.setWFWSpn("Next  page: 11 _ _");
    replyCat5.setRegex("Page:2 GroupID:3 Position:5");
    replyCat5.setEngExplanation("eng_explanation cat 5e");
    replyCat5.setTimes(54, 55, 56, 57);
    replyCat5.setFollowingPage(11);
    replyCat5.setSpnPhrase("Page:2 GroupID:3 Position:5");

    Reply replyCat6 = new Reply(6, 3);//2
    replyCat6.setEngPhrase("Page:2 GroupID:3 Position:6");
    replyCat6.setWFWEng("Time plays: 360 for 1");
    replyCat6.setWFWSpn("Next  page: 3 _ _");
    replyCat6.setRegex("Page:2 GroupID:3 Position:6");
    replyCat6.setEngExplanation("eng_explanation cat 6e");
    replyCat6.setTimes(360, 361, 362, 363);
    replyCat6.setFollowingPage(3);
    replyCat6.setSpnPhrase("Page:2 GroupID:3 Position:6");

    Reply replyElephant0 = new Reply(0, 0);//4
    replyElephant0.setEngPhrase("Page:4 GroupID:0 Position:0");
    replyElephant0.setWFWEng("Time plays: 170 for 1");
    replyElephant0.setWFWSpn("Next  page: -1 _ _");
    replyElephant0.setRegex("Page:4 GroupID:0 Position:0");
    replyElephant0.setEngExplanation("eng_explanation cat 5e");
    replyElephant0.setTimes(170, 171, 172, 173);
    replyElephant0.setFollowingPage(6);
    replyElephant0.setSpnPhrase("Page:4 GroupID:0 Position:0");

    Reply replyFlower2 = new Reply(2, 2);//5
    replyFlower2.setEngPhrase("Page:5 GroupID:2 Position:2");
    replyFlower2.setWFWEng("Time plays: 460 for 1");
    replyFlower2.setWFWSpn("Next  page: 25 _ _");
    replyFlower2.setRegex("Page:5 GroupID:2 Position:2");
    replyFlower2.setEngExplanation("eng_explanation flower 2a");
    replyFlower2.setTimes(460, 461, 462, 463);
    replyFlower2.setFollowingPage(25);
    replyFlower2.setSpnPhrase("Page:5 GroupID:2 Position:2");

    Reply replyGiraffe0 = new Reply(0, 0);//6
    replyGiraffe0.setEngPhrase("Page:6 GroupID:0 Position:0");
    replyGiraffe0.setWFWEng("Time plays: 410 for 1");
    replyGiraffe0.setWFWSpn("Next  page: -1 _ _");
    replyGiraffe0.setRegex("Page:6 GroupID:0 Position:0");
    replyGiraffe0.setEngExplanation("eng_explanation giraffe 5e");
    replyGiraffe0.setTimes(410, 411, 412, 413);
    replyGiraffe0.setFollowingPage(-1);
    replyGiraffe0.setSpnPhrase("Page:6 GroupID:0 Position:0");

    Reply replyInk1 = new Reply(1, 1);//8
    replyInk1.setEngPhrase("Page:8 GroupID:1 Position:1");
    replyInk1.setWFWEng("Time plays: 66 for 1");
    replyInk1.setWFWSpn("Next  page: 9 _ _");
    replyInk1.setRegex("Page:8 GroupID:1 Position:1");
    replyInk1.setEngExplanation("eng_explanation ink 1e");
    replyInk1.setTimes(66, 67, 68, 69);
    replyInk1.setFollowingPage(9);
    replyInk1.setSpnPhrase("Page:8 GroupID:1 Position:1");

    Reply replyJam1 = new Reply(1, 1);//9
    replyJam1.setEngPhrase("Page:9 GroupID:1 Position:1");
    replyJam1.setWFWEng("Time plays: 74 for 1");
    replyJam1.setWFWSpn("Next  page: 10 _ _");
    replyJam1.setRegex("Page:9 GroupID:1 Position:1");
    replyJam1.setEngExplanation("eng_explanation jam 1e");
    replyJam1.setTimes(74, 75, 76, 77);
    replyJam1.setFollowingPage(10);
    replyJam1.setSpnPhrase("Page:9 GroupID:1 Position:1");

    Reply replyKettle1 = new Reply(1, 1); //10
    replyKettle1.setEngPhrase("Page:10 GroupID:1 Position:1");
    replyKettle1.setWFWEng("Time plays: 150 for 1");
    replyKettle1.setWFWSpn("Next  page: 20 _ _");
    replyKettle1.setRegex("Page:10 GroupID:1 Position:1");
    replyKettle1.setEngExplanation("eng_explanation jam 1e");
    replyKettle1.setTimes(150, 151, 152, 153);
    replyKettle1.setFollowingPage(20);
    replyKettle1.setSpnPhrase("Page:10 GroupID:1 Position:1");

    Reply replyLlama1 = new Reply(2, 2); //11
    replyLlama1.setEngPhrase("Page:11 GroupID:2 Position:2");
    replyLlama1.setWFWEng("Time plays: 260 for 1");
    replyLlama1.setWFWSpn("Next  page: 17 _ _");
    replyLlama1.setRegex("Page:11 GroupID:2 Position:2");
    replyLlama1.setEngExplanation("eng_explanation llama 1e");
    replyLlama1.setTimes(260, 261, 262, 263);
    replyLlama1.setFollowingPage(17);
    replyLlama1.setSpnPhrase("Page:11 GroupID:2 Position:2");

    Reply replyMaple0 = new Reply(2, 2);//12
    replyMaple0.setEngPhrase("Page:12 GroupID:2 Position:2");
    replyMaple0.setWFWEng("Time plays: 150 for 1");
    replyMaple0.setWFWSpn("Next  page: 14 _ _");
    replyMaple0.setRegex("Page:12 GroupID:2 Position:2");
    replyMaple0.setEngExplanation("eng_explanation maple 0c");
    replyMaple0.setTimes(150, 151, 152, 153);
    replyMaple0.setSpnPhrase("Page:12 GroupID:2 Position:2");
    replyMaple0.setFollowingPage(14);

    Reply replyOcean0 = new Reply(1, 1);//14
    replyOcean0.setEngPhrase("Page:14 GroupID:1 Position:1");
    replyOcean0.setWFWEng("Time plays: 82 for 1");
    replyOcean0.setWFWSpn("Next  page: -1 _ _");
    replyOcean0.setRegex("Page:14 GroupID:1 Position:1");
    replyOcean0.setEngExplanation("eng_explanation ocean 0e");
    replyOcean0.setTimes(82, 83, 84, 85);
    replyOcean0.setSpnPhrase("Page:14 GroupID:1 Position:1");
    replyOcean0.setFollowingPage(-1);

    Reply replyPan0 = new Reply(1, 1);//15
    replyPan0.setEngPhrase("Page:15 GroupID:1 Position:1");
    replyPan0.setWFWEng("Time plays: 90 for 1");
    replyPan0.setWFWSpn("Next  page: 19 _ _");
    replyPan0.setRegex("Page:15 GroupID:1 Position:1");
    replyPan0.setEngExplanation("eng_explanation pan 0e");
    replyPan0.setTimes(90, 91, 92, 93);
    replyPan0.setSpnPhrase("Page:15 GroupID:1 Position:1");
    replyPan0.setFollowingPage(19);

    Reply replyPan2 = new Reply(2, 1);//15
    replyPan2.setEngPhrase("Page:15 GroupID:1 Position:2");
    replyPan2.setWFWEng("Time plays: 98 for 1");
    replyPan2.setWFWSpn("Next  page: 16 _ _");
    replyPan2.setRegex("Page:15 GroupID:1 Position:2");
    replyPan2.setEngExplanation("eng_explanation pan 2e");
    replyPan2.setTimes(98, 99, 100, 101);
    replyPan2.setFollowingPage(16);
    replyPan2.setSpnPhrase("Page:15 GroupID:1 Position:2");

    Reply replyParty0 = new Reply(1, 1);//17
    replyParty0.setEngPhrase("Page:17 GroupID:1 Position:1");
    replyParty0.setWFWEng("Time plays: 250 for 1");
    replyParty0.setWFWSpn("Next  page: -1 _ _");
    replyParty0.setRegex("Page:17 GroupID:1 Position:1");
    replyParty0.setEngExplanation("eng_explanation party 0e");
    replyParty0.setTimes(250, 251, 252, 253);
    replyParty0.setSpnPhrase("Page:17 GroupID:1 Position:1");
    replyParty0.setFollowingPage(-1);

    Reply replyQuark0 = new Reply(0, 0);//18
    replyQuark0.setEngPhrase("Page:18 GroupID:0 Position:0");
    replyQuark0.setWFWEng("Time plays: 300 for 1");
    replyQuark0.setWFWSpn("Next  page: 24 _ _");
    replyQuark0.setRegex("Page:18 GroupID:0 Position:0");
    replyQuark0.setEngExplanation("eng_explanation Quark 0e");
    replyQuark0.setTimes(300, 301, 302, 303);
    replyQuark0.setSpnPhrase("Page:18 GroupID:0 Position:0");
    replyQuark0.setFollowingPage(24);

    Reply replyQuark1 = new Reply(1, 0);//18
    replyQuark1.setEngPhrase("Page:18 GroupID:0 Position:1");
    replyQuark1.setWFWEng("Time plays: 310 for 1");
    replyQuark1.setWFWSpn("Next  page: -1 _ _");
    replyQuark1.setRegex("Page:18 GroupID:0 Position:1");
    replyQuark1.setEngExplanation("eng_explanation Quark 0e");
    replyQuark1.setTimes(310, 311, 312, 313);
    replyQuark1.setSpnPhrase("Page:18 GroupID:0 Position:1");
    replyQuark1.setFollowingPage(-1);
    
    Reply replyTiger0 = new Reply(0, 0);//19
    replyTiger0.setEngPhrase("Page:19 GroupID:0 Position:0");
    replyTiger0.setWFWEng("Time plays: 210 for 1");
    replyTiger0.setWFWSpn("Next  page: 21 _ _");
    replyTiger0.setRegex("Page:20 GroupID:0 Position:0");
    replyTiger0.setEngExplanation("eng_explanation tiger 0e");
    replyTiger0.setTimes(210, 211, 212, 213);
    replyTiger0.setFollowingPage(21);
    replyTiger0.setSpnPhrase("Page:19 GroupID:0 Position:0");

    Reply replyUmbro0 = new Reply(0, 0);//20
    replyUmbro0.setEngPhrase("Page:20 GroupID:0 Position:0");
    replyUmbro0.setWFWEng("Time plays: 126 for 1");
    replyUmbro0.setWFWSpn("Next  page: 22 _ _");
    replyUmbro0.setRegex("Page:20 GroupID:0 Position:0");
    replyUmbro0.setEngExplanation("eng_explanation umbro 0e");
    replyUmbro0.setTimes(126, 127, 128, 129);
    replyUmbro0.setFollowingPage(22);
    replyUmbro0.setSpnPhrase("Page:20 GroupID:0 Position:0");

    Reply replyYellow0 = new Reply(1, 1);//24
    replyYellow0.setEngPhrase("Page:24 GroupID:1 Position:1");
    replyYellow0.setWFWEng("Time plays: 330 for 1");
    replyYellow0.setWFWSpn("Next  page: -1 _ _");
    replyYellow0.setRegex("Page:24 GroupID:1 Position:1");
    replyYellow0.setEngExplanation("eng_explanation yellow 0e");
    replyYellow0.setTimes(330, 331, 332, 333);
    replyYellow0.setFollowingPage(-1);
    replyYellow0.setSpnPhrase("Page:24 GroupID:1 Position:1");

    Reply replyZebra0 = new Reply(1, 1);//25
    replyZebra0.setEngPhrase("Page:25 GroupID:1 Position:1");
    replyZebra0.setWFWEng("Time plays: 350 for 1");
    replyZebra0.setWFWSpn("Next  page: -1 _ _");
    replyZebra0.setRegex("Page:25 GroupID:1 Position:1");
    replyZebra0.setEngExplanation("eng_explanation zebra 0e");
    replyZebra0.setTimes(350, 351, 352, 353);
    replyZebra0.setFollowingPage(-1);
    replyZebra0.setSpnPhrase("Page:25 GroupID:1 Position:1");

    ArrayList<Matchable> matchables0 = new ArrayList<>();
    ArrayList<Leader> hints0 = new ArrayList<>();
    hints0.add(hintApple0);
    matchables0.add(replyApple1);
    matchables0.add(replyApple2);
    matchables0.add(replyApple3);
    matchables0.add(replyApple4);
    ArrayList<Matchable> matchables1 = new ArrayList<>();
    ArrayList<Leader> hints1 = new ArrayList<>();
    hints1.add(hintBanana0);
    matchables1.add(replyBanana1);
    matchables1.add(replyBanana2);
    matchables1.add(replyBanana3);
    ArrayList<Matchable> matchables2 = new ArrayList<>();
    ArrayList<Leader> hints2 = new ArrayList<>();
    hints2.add(hintCat0);
    hints2.add(hintCat3);
    matchables2.add(replyCat1);
    matchables2.add(replyCat2);
    matchables2.add(replyCat4);
    matchables2.add(replyCat5);
    matchables2.add(replyCat6);
    ArrayList<Leader> hints3 = new ArrayList<>();;
    hints3.add(hintDog0);
    ArrayList<Leader> hints4 = new ArrayList<>();
    ArrayList<Matchable> matchables4 = new ArrayList<>();
    hints4.add(hintElephant1);
    matchables4.add(replyElephant0);
    ArrayList<Matchable> matchables5 = new ArrayList<>();
    ArrayList<Leader> hints5 = new ArrayList<>();
    matchables5.add(replyFlower2);
    hints5.add(hintFlower0);
    hints5.add(hintFlower1);
    hints5.add(hintFlower3);
    hints5.add(hintFlower4);
    ArrayList<Leader> hints6 = new ArrayList<>();
    ArrayList<Matchable> matchables6 = new ArrayList<>();
    hints6.add(hintGiraffe1);
    matchables6.add(replyGiraffe0);
    ArrayList<Matchable> matchables8 = new ArrayList<>();
    ArrayList<Leader> hints8 = new ArrayList<>();
    hints8.add(hintInk0);
    matchables8.add(replyInk1);
    ArrayList<Matchable> matchables9 = new ArrayList<>();
    ArrayList<Leader> hints9 = new ArrayList<>();
    hints9.add(hintJam0);
    matchables9.add(replyJam1);
    ArrayList<Leader> hints10 = new ArrayList<>();
    hints10.add(hintKettle0);
    ArrayList<Matchable>matchables10 = new ArrayList<>();
    matchables10.add(replyKettle1);
    ArrayList<Leader> hints11 = new ArrayList<>();
    hints11.add(hintLlama0);
    hints11.add(hintLlama1);
    ArrayList<Matchable>matchables11 = new ArrayList<>();
    matchables11.add(replyLlama1);
    ArrayList<Matchable> matchables12 = new ArrayList<>();
    ArrayList<Leader> hints12 = new ArrayList<>();
    matchables12.add(replyMaple0);
    hints12.add(hintMaple0);
    hints12.add(hintMaple1);
    ArrayList<Matchable> matchables14 = new ArrayList<>();
    ArrayList<Leader> hints14 = new ArrayList<>();
    hints14.add(hintOcean1);
    matchables14.add(replyOcean0);
    ArrayList<Matchable> matchables15 = new ArrayList<>();
    ArrayList<Leader> hints15 = new ArrayList<>();
    hints15.add(hintPan1);
    matchables15.add(replyPan0);
    matchables15.add(replyPan2);
    ArrayList<Leader> hints16 = new ArrayList<>();
    hints16.add(hintQuote0);
    hints16.add(hintQuote1);
    ArrayList<Matchable> matchables17 = new ArrayList<>();
    ArrayList<Leader> hints17 = new ArrayList<>();
    hints17.add(hintParty1);
    matchables17.add(replyParty0);
    ArrayList<Matchable> matchables18 = new ArrayList<>();
    matchables18.add(replyQuark0);
    matchables18.add(replyQuark1);
    ArrayList<Matchable> matchables19 = new ArrayList<>();
    ArrayList<Leader> hints19 = new ArrayList<>();
    hints19.add(hintTiger1);
    hints19.add(hintTiger2);
    matchables19.add(replyTiger0);
    ArrayList<Matchable> matchables20 = new ArrayList<>();
    ArrayList<Leader> hints20 = new ArrayList<>();
    hints20.add(hintUmbro1);
    hints20.add(hintUmbro2);
    matchables20.add(replyUmbro0);
    ArrayList<Leader> hints21 = new ArrayList<>();
    hints21.add(hintViolet0);
    ArrayList<Leader> hints22 = new ArrayList<>();
    hints22.add(hintWater0);
    ArrayList<Leader> hints23 = new ArrayList<>();
    hints23.add(hintXenon0);
    ArrayList<Leader> hints24 = new ArrayList<>();
    ArrayList<Matchable> matchables24 = new ArrayList<>();
    hints24.add(hintYellow0);
    matchables24.add(replyYellow0);
    ArrayList<Leader> hints25 = new ArrayList<>();
    ArrayList<Matchable> matchables25 = new ArrayList<>();
    hints25.add(hintZebra0);
    matchables25.add(replyZebra0);
    
    Page page0  = new Page(0, hints0, matchables0);
    page0.setAsFirstPage(true);
    Page page1  = new Page(1, hints1, matchables1);
    Page page2  = new Page(2, hints2, matchables2);
    Page page3  = new Page(3, hints3, new ArrayList<Matchable>());
    Page page4  = new Page(4, hints4, matchables4);
    Page page5  = new Page(5, hints5, matchables5);
    Page page6  = new Page(6, hints6, matchables6);
    Page page8  = new Page(8, hints8, matchables8);
    Page page9  = new Page(9, hints9, matchables9);
    Page page10 = new Page(10, hints10, matchables10);
    Page page11 = new Page(11, hints11, matchables11);
    Page page12 = new Page(12, hints12, matchables12);
    Page page14 = new Page(14, hints14, matchables14);
    Page page15 = new Page(15, hints15, matchables15);
    Page page16 = new Page(16, hints16, new ArrayList<Matchable>());
    Page page17 = new Page(17, hints17, matchables17);
    Page page18 = new Page(18, new ArrayList<Leader>(), matchables18);
    Page page19 = new Page(19, hints19, matchables19);
    Page page20 = new Page(20, hints20, matchables20);
    Page page21 = new Page(21, hints21, new ArrayList<Matchable>());
    Page page22 = new Page(22, hints22, new ArrayList<Matchable>());
    Page page23 = new Page(23, hints23, new ArrayList<Matchable>());
    Page page24 = new Page(24, hints24, matchables24);
    Page page25 = new Page(25, hints25, matchables25);
    
    pages.put(page0.getName(), page0);
    pages.put(page1.getName(), page1);
    pages.put(page2.getName(), page2);
    pages.put(page3.getName(), page3);
    pages.put(page4.getName(), page4);
    pages.put(page5.getName(), page5);
    pages.put(page6.getName(), page6);
    pages.put(page8.getName(), page8);
    pages.put(page9.getName(), page9);
    pages.put(page10.getName(), page10);
    pages.put(page11.getName(), page11);
    pages.put(page12.getName(), page12);
    pages.put(page14.getName(), page14);
    pages.put(page15.getName(), page15);
    pages.put(page16.getName(), page16);
    pages.put(page17.getName(), page17);
    pages.put(page18.getName(), page18);
    pages.put(page19.getName(), page19);
    pages.put(page20.getName(), page20);
    pages.put(page21.getName(), page21);
    pages.put(page22.getName(), page22);
    pages.put(page23.getName(), page23);
    pages.put(page24.getName(), page24);
    pages.put(page25.getName(), page25);
  }
}
