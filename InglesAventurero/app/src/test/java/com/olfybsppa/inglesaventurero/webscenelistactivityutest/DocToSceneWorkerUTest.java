package com.olfybsppa.inglesaventurero.webscenelistactivityutest;

import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.collectors.Background;
import com.olfybsppa.inglesaventurero.webscenelistactivity.DocToSceneWorker;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPHint;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPPage;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPReply;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPScene;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class DocToSceneWorkerUTest {
  private Document doc; // doc is going to populate scene.

  // comparing values in scene to correct values below.
  private ArrayList<CPPage> correctPages = new ArrayList<>();
  private HashMap<Integer, ArrayList<CPHint>> correctHints = new HashMap<Integer, ArrayList<CPHint>> ();
  private HashMap<Integer, ArrayList<CPReply>> correctReplies = new HashMap<Integer, ArrayList<CPReply>>();
  private HashMap<Integer, Background> correctBackgrounds = new HashMap<Integer, Background>();
  private HashMap<String, HashSet<Attribution>> correctAttributions = new HashMap<String, HashSet<Attribution>>();

  @Before
  public  void setUp() throws Exception {
    CPPage page1  = new CPPage().setPageName(1).setAsFirst(true);
    CPPage page2  = new CPPage().setPageName(2);
    CPPage page3  = new CPPage().setPageName(3);
    CPPage page4  = new CPPage().setPageName(4);
    CPPage page9  = new CPPage().setPageName(9);
    CPPage page10 = new CPPage().setPageName(10);
    CPPage page11 = new CPPage().setPageName(11);
    CPPage page15 = new CPPage().setPageName(15);
    CPPage page16 = new CPPage().setPageName(16);
    CPPage page17 = new CPPage().setPageName(17);
    CPPage page21 = new CPPage().setPageName(21);
    CPPage page23 = new CPPage().setPageName(23);

    correctPages.add(page1);
    correctPages.add(page2);
    correctPages.add(page3);
    correctPages.add(page4);
    correctPages.add(page9);
    correctPages.add(page10);
    correctPages.add(page11);
    correctPages.add(page15);
    correctPages.add(page16);
    correctPages.add(page17);
    correctPages.add(page21);
    correctPages.add(page23);

    CPHint hintApple0 = new CPHint(0, 0); //1
    hintApple0.setTimes(10, 11, 12, 13);
    hintApple0.setEnglishPhrase("apple 0");
    hintApple0.setSpanishPhrase("next -1");
    hintApple0.setWFWEnglish("apple 0");
    hintApple0.setWFWSpanish("position apple 0");
    hintApple0.setEngExplanation("eng explanation apple 0");
    hintApple0.setSpnExplanation("spn explanation apple 0");
    CPHint hintBanana0 = new CPHint(0, 0); //2
    hintBanana0.setTimes(20, 21, 22, 23);
    hintBanana0.setEnglishPhrase("banana 0");
    hintBanana0.setSpanishPhrase("next -1");
    hintBanana0.setWFWEnglish("banana 0");
    hintBanana0.setWFWSpanish("position banana 0");
    hintBanana0.setEngExplanation("eng explanation banana 0");
    hintBanana0.setSpnExplanation("spn explanation banana 0");
    CPHint hintCat0 = new CPHint(0, 0);//3
    hintCat0.setTimes(30, 31, 32, 33);
    hintCat0.setEnglishPhrase("cat 0");
    hintCat0.setSpanishPhrase("next -1");
    hintCat0.setWFWEnglish("cat 0");
    hintCat0.setWFWSpanish("position cat 0");
    hintCat0.setEngExplanation("eng explanation cat 0");
    hintCat0.setSpnExplanation("spn explanation cat 0");
    CPHint hintCat3 = new CPHint(3, 2);//3
    hintCat3.setTimes(33, 34, 35, 36);
    hintCat3.setEnglishPhrase("cat 3");
    hintCat3.setSpanishPhrase("next -1");
    hintCat3.setWFWEnglish("cat 3");
    hintCat3.setWFWSpanish("position cat 3");
    hintCat3.setEngExplanation("eng explanation cat 3");
    hintCat3.setSpnExplanation("spn explanation cat 3");
    CPHint hintDog0 = new CPHint(0, 0);//4
    hintDog0.setTimes(40, 41, 42, 43);
    hintDog0.setEnglishPhrase("dog 0");
    hintDog0.setSpanishPhrase("next -1");
    hintDog0.setWFWEnglish("dog 0");
    hintDog0.setWFWSpanish("position dog 0");
    hintDog0.setEngExplanation("eng explanation dog 0");
    hintDog0.setSpnExplanation("spn explanation dog 0");
    CPHint hintInk0 = new CPHint(0, 0);//9
    hintInk0.setTimes(90, 91, 92, 93);
    hintInk0.setEnglishPhrase("ink 0");
    hintInk0.setSpanishPhrase("next -1");
    hintInk0.setWFWEnglish("ink 0");
    hintInk0.setWFWSpanish("position ink 0");
    hintInk0.setEngExplanation("eng explanation ink 0");
    hintInk0.setSpnExplanation("spn explanation ink 0");
    CPHint hintJam0 = new CPHint(0, 0);//10
    hintJam0.setTimes(100, 101, 102, 103);
    hintJam0.setEnglishPhrase("jam 0");
    hintJam0.setSpanishPhrase("next -1");
    hintJam0.setWFWEnglish("jam 0");
    hintJam0.setWFWSpanish("position jam 0");
    hintJam0.setEngExplanation("eng explanation jam 0");
    hintJam0.setSpnExplanation("spn explanation jam 0");
    CPHint hintKettle0 = new CPHint(0, 0);//11
    hintKettle0.setTimes(110, 111, 112, 113);
    hintKettle0.setEnglishPhrase("kettle 0");
    hintKettle0.setSpanishPhrase("next -1");
    hintKettle0.setWFWEnglish("kettle 0");
    hintKettle0.setWFWSpanish("position kettle 0");
    hintKettle0.setEngExplanation("eng explanation kettle 0");
    hintKettle0.setSpnExplanation("spn explanation kettle 0");
    CPHint hintOcean1 = new CPHint(1, 1);//15
    hintOcean1.setTimes(151, 152, 153, 154);
    hintOcean1.setNextPage(16);
    hintOcean1.setEnglishPhrase("ocean 1");
    hintOcean1.setSpanishPhrase("next 16");
    hintOcean1.setWFWEnglish("ocean 1");
    hintOcean1.setWFWSpanish("position ocean 1");
    hintOcean1.setEngExplanation("eng explanation ocean 1");
    hintOcean1.setSpnExplanation("spn explanation ocean 1");
    CPHint hintPan1 = new CPHint(1, 1);//16
    hintPan1.setTimes(161, 162, 163, 164);
    hintPan1.setEnglishPhrase("pan 1");
    hintPan1.setSpanishPhrase("next -1");
    hintPan1.setWFWEnglish("pan 1");
    hintPan1.setWFWSpanish("position pan 1");
    hintPan1.setEngExplanation("eng explanation pan 1");
    hintPan1.setSpnExplanation("spn explanation pan 1");
    CPHint hintQuote0 = new CPHint(0, 0);//17
    hintQuote0.setTimes(170, 171, 172, 173);
    hintQuote0.setEnglishPhrase("quote 0");
    hintQuote0.setSpanishPhrase("next -1");
    hintQuote0.setWFWEnglish("quote 0");
    hintQuote0.setWFWSpanish("position quote 0");
    hintQuote0.setEngExplanation("eng explanation quote 0");
    hintQuote0.setSpnExplanation("spn explanation quote 0");
    CPHint hintUmbro0 = new CPHint(0, 0);//21
    hintUmbro0.setTimes(210, 211, 212, 213);
    hintUmbro0.setEnglishPhrase("umbro 0");
    hintUmbro0.setSpanishPhrase("next -1");
    hintUmbro0.setWFWEnglish("umbro 0");
    hintUmbro0.setWFWSpanish("position umbro 0");
    hintUmbro0.setEngExplanation("eng explanation umbro 0");
    hintUmbro0.setSpnExplanation("spn explanation umbro 0");
    CPHint hintWater0 = new CPHint(0, 0);//23
    hintWater0.setTimes(230, 231, 232, 233);
    hintWater0.setEnglishPhrase("water 0");
    hintWater0.setSpanishPhrase("next -1");
    hintWater0.setWFWEnglish("water 0");
    hintWater0.setWFWSpanish("position water 0");
    hintWater0.setEngExplanation("eng explanation water 0");
    hintWater0.setSpnExplanation("spn explanation water 0");

    correctHints.put(1, constructAL(hintApple0));
    correctHints.put(2, constructAL(hintBanana0));
    correctHints.put(3, constructAL(hintCat0, hintCat3));
    correctHints.put(4, constructAL(hintDog0));
    correctHints.put(9, constructAL(hintInk0));
    correctHints.put(10, constructAL(hintJam0));
    correctHints.put(11, constructAL(hintKettle0));
    correctHints.put(15, constructAL(hintOcean1));
    correctHints.put(16, constructAL(hintPan1));
    correctHints.put(17, constructAL(hintQuote0));
    correctHints.put(21, constructAL(hintUmbro0));
    correctHints.put(23, constructAL(hintWater0));

    CPReply replyApple1 = new CPReply(1, 1);//1
    replyApple1.setNextPage(9);
    replyApple1.setTimes(11, 12, 13, 14);
    replyApple1.setEnglishPhrase("apple 1");
    replyApple1.setSpanishPhrase("next 9");
    replyApple1.setWFWEnglish("apple 1");
    replyApple1.setWFWSpanish("position apple 1");
    replyApple1.setRegex("regex apple 1");
    replyApple1.setEngExplanation("eng explanation apple 1");
    replyApple1.setSpnExplanation("spn explanation apple 1");
    CPReply replyApple2 = new CPReply(2, 1);//1
    replyApple2.setNextPage(2);
    replyApple2.setTimes(12, 13, 14, 15);
    replyApple2.setEnglishPhrase("apple 2");
    replyApple2.setSpanishPhrase("next 2");
    replyApple2.setWFWEnglish("apple 2");
    replyApple2.setWFWSpanish("position apple 2");
    replyApple2.setRegex("regex apple 2");
    replyApple2.setEngExplanation("eng explanation apple 2");
    replyApple2.setSpnExplanation("spn explanation apple 2");
    CPReply replyBanana1 = new CPReply(1, 1);//2
    replyBanana1.setNextPage(15);
    replyBanana1.setTimes(21, 22, 23, 24);
    replyBanana1.setEnglishPhrase("banana 1");
    replyBanana1.setSpanishPhrase("next 15");
    replyBanana1.setWFWEnglish("banana 1");
    replyBanana1.setWFWSpanish("position banana 1");
    replyBanana1.setRegex("regex banana 1");
    replyBanana1.setEngExplanation("eng explanation banana 1");
    replyBanana1.setSpnExplanation("spn explanation banana 1");
    CPReply replyBanana2 = new CPReply(2, 1);//2
    replyBanana2.setNextPage(3);
    replyBanana2.setTimes(22, 23, 24, 25);
    replyBanana2.setEnglishPhrase("banana 2");
    replyBanana2.setSpanishPhrase("next 3");
    replyBanana2.setWFWEnglish("banana 2");
    replyBanana2.setWFWSpanish("position banana 2");
    replyBanana2.setRegex("regex banana 2");
    replyBanana2.setEngExplanation("eng explanation banana 2");
    replyBanana2.setSpnExplanation("spn explanation banana 2");
    CPReply replyCat1 = new CPReply(1, 1);//3
    replyCat1.setNextPage(21);
    replyCat1.setTimes(31, 32, 33, 34);
    replyCat1.setEnglishPhrase("cat 1");
    replyCat1.setSpanishPhrase("next 21");
    replyCat1.setWFWEnglish("cat 1");
    replyCat1.setWFWSpanish("position cat 1");
    replyCat1.setRegex("regex cat 1");
    replyCat1.setEngExplanation("eng explanation cat 1");
    replyCat1.setSpnExplanation("spn explanation cat 1");
    CPReply replyCat2 = new CPReply(2, 1);//3
    replyCat2.setTimes(32, 33, 34, 35);
    replyCat2.setEnglishPhrase("cat 2");
    replyCat2.setSpanishPhrase("next -1");
    replyCat2.setWFWEnglish("cat 2");
    replyCat2.setWFWSpanish("position cat 2");
    replyCat2.setRegex("regex cat 2");
    replyCat2.setEngExplanation("eng explanation cat 2");
    replyCat2.setSpnExplanation("spn explanation cat 2");
    CPReply replyCat4 = new CPReply(4, 3);//3
    replyCat4.setNextPage(23);
    replyCat4.setTimes(34, 35, 36, 37);
    replyCat4.setEnglishPhrase("cat 4");
    replyCat4.setSpanishPhrase("next 23");
    replyCat4.setWFWEnglish("cat 4");
    replyCat4.setWFWSpanish("position cat 4");
    replyCat4.setRegex("regex cat 4");
    replyCat4.setEngExplanation("eng explanation cat 4");
    replyCat4.setSpnExplanation("spn explanation cat 4");
    CPReply replyCat5 = new CPReply(5, 3);//3
    replyCat5.setNextPage(3);
    replyCat5.setTimes(35, 36, 37, 38);
    replyCat5.setEnglishPhrase("cat 5");
    replyCat5.setSpanishPhrase("next 4");
    replyCat5.setWFWEnglish("cat 5");
    replyCat5.setWFWSpanish("position cat 5");
    replyCat5.setRegex("regex cat 5");
    replyCat5.setEngExplanation("eng explanation cat 5");
    replyCat5.setSpnExplanation("spn explanation cat 5");
    CPReply replyInk1 = new CPReply(1, 1);//9
    replyInk1.setNextPage(10);
    replyInk1.setTimes(91, 92, 93, 94);
    replyInk1.setEnglishPhrase("ink 1");
    replyInk1.setSpanishPhrase("next 10");
    replyInk1.setWFWEnglish("ink 1");
    replyInk1.setWFWSpanish("position ink 1");
    replyInk1.setRegex("regex ink 1");
    replyInk1.setEngExplanation("eng explanation ink 1");
    replyInk1.setSpnExplanation("spn explanation ink 1");
    CPReply replyJam1 = new CPReply(1, 1);//10
    replyJam1.setNextPage(11);
    replyJam1.setTimes(111, 112, 113, 114);
    replyJam1.setEnglishPhrase("jam 1");
    replyJam1.setSpanishPhrase("next 11");
    replyJam1.setWFWEnglish("jam 1");
    replyJam1.setWFWSpanish("position jam 1");
    replyJam1.setRegex("regex jam 1");
    replyJam1.setEngExplanation("eng explanation jam 1");
    replyJam1.setSpnExplanation("spn explanation jam 1");
    CPReply replyOcean0 = new CPReply(0, 0);//15
    replyOcean0.setTimes(150, 151, 152, 153);
    replyOcean0.setEnglishPhrase("ocean 0");
    replyOcean0.setSpanishPhrase("next -1");
    replyOcean0.setWFWEnglish("ocean 0");
    replyOcean0.setWFWSpanish("position ocean 0");
    replyOcean0.setRegex("regex ocean 0");
    replyOcean0.setEngExplanation("eng explanation ocean 0");
    replyOcean0.setSpnExplanation("spn explanation ocean 0");
    CPReply replyPan0 = new CPReply(0, 0);//16
    replyPan0.setTimes(160, 161, 162, 163);
    replyPan0.setEnglishPhrase("pan 0");
    replyPan0.setSpanishPhrase("next -1");
    replyPan0.setWFWEnglish("pan 0");
    replyPan0.setWFWSpanish("position pan 0");
    replyPan0.setRegex("regex pan 0");
    replyPan0.setEngExplanation("eng explanation pan 0");
    replyPan0.setSpnExplanation("spn explanation pan 0");
    CPReply replyPan2 = new CPReply(2, 2);//16
    replyPan2.setNextPage(17);
    replyPan2.setTimes(162, 163, 164, 165);
    replyPan2.setEnglishPhrase("pan 2");
    replyPan2.setSpanishPhrase("next 17");
    replyPan2.setWFWEnglish("pan 2");
    replyPan2.setWFWSpanish("position pan 2");
    replyPan2.setRegex("regex pan 2");
    replyPan2.setEngExplanation("eng explanation pan 2");
    replyPan2.setSpnExplanation("spn explanation pan 2");

    correctReplies.put(1, constructAL(replyApple1, replyApple2));
    correctReplies.put(2, constructAL(replyBanana1, replyBanana2));
    correctReplies.put(3, constructAL(replyCat1, replyCat2, replyCat4, replyCat5));
    correctReplies.put(9, constructAL(replyInk1));
    correctReplies.put(10, constructAL(replyJam1));
    correctReplies.put(15, constructAL(replyOcean0));
    correctReplies.put(16, constructAL(replyPan0, replyPan2));

    correctBackgrounds.put(1, new Background("alphabet_1", "alphabet_1.jpg"));
    correctBackgrounds.put(2, new Background("alphabet_2", "alphabet_2.jpg"));
    correctBackgrounds.put(3, new Background("alphabet_3", "alphabet_3.jpg"));
    correctBackgrounds.put(4, new Background("alphabet_4", "alphabet_4.jpg"));
    correctBackgrounds.put(9, new Background("alphabet_9", "alphabet_9.jpg"));
    correctBackgrounds.put(10, new Background("alphabet_10", "alphabet_10.jpg"));
    correctBackgrounds.put(11, new Background("alphabet_11", "alphabet_11.jpg"));
    correctBackgrounds.put(15, new Background("alphabet_15", "alphabet_15.jpg"));
    correctBackgrounds.put(16, new Background("alphabet_16", "alphabet_16.jpg"));
    correctBackgrounds.put(17, new Background("alphabet_17", "alphabet_17.jpg"));
    correctBackgrounds.put(21, new Background("alphabet_21", "alphabet_21.jpg"));
    correctBackgrounds.put(23, new Background("alphabet_23", "alphabet_23.jpg"));

    Attribution attribution1A = new Attribution("image_info_name_1_a");
    attribution1A.setArtist("artist_1_a");
    attribution1A.setLinkToLicense("link_to_license_1_a");
    attribution1A.setNameOfLicense("name_of_license_1_a");
    attribution1A.setArtistImageName("artist_image_name_1_a");
    attribution1A.setImageUrlName("image_url_name_1_a");
    attribution1A.setImageUrl("image_url_1_a");
    attribution1A.setFilename("artist_filename_1_a");
    attribution1A.setChangesMadeEnglish("changes_english_1_a");
    attribution1A.setChangesMadeSpanish("changes_spanish_1_a");

    Attribution attribution1B = new Attribution("image_info_name_1_b");
    attribution1B.setArtist("artist_1_b");
    attribution1B.setLinkToLicense("link_to_license_1_b");
    attribution1B.setNameOfLicense("name_of_license_1_b");
    attribution1B.setArtistImageName("artist_image_name_1_b");
    attribution1B.setImageUrlName("image_url_name_1_b");
    attribution1B.setImageUrl("image_url_1_b");
    attribution1B.setFilename("artist_filename_1_b");
    attribution1B.setChangesMadeEnglish("changes_english_1_b");
    attribution1B.setChangesMadeSpanish("changes_spanish_1_b");

    Attribution attribution2A = new Attribution("image_info_name_2_a");
    attribution2A.setArtist("artist_2_a");
    attribution2A.setLinkToLicense("link_to_license_2_a");
    attribution2A.setNameOfLicense("name_of_license_2_a");
    attribution2A.setArtistImageName("artist_image_name_2_a");
    attribution2A.setImageUrlName("image_url_name_2_a");
    attribution2A.setImageUrl("image_url_2_a");
    attribution2A.setFilename("artist_filename_2_a");
    attribution2A.setChangesMadeEnglish("changes_english_2_a");
    attribution2A.setChangesMadeSpanish("changes_spanish_2_a");

    Attribution attribution2B = new Attribution("image_info_name_2_b");
    attribution2B.setArtist("artist_2_b");
    attribution2B.setLinkToLicense("link_to_license_2_b");
    attribution2B.setNameOfLicense("name_of_license_2_b");
    attribution2B.setArtistImageName("artist_image_name_2_b");
    attribution2B.setImageUrlName("image_url_name_2_b");
    attribution2B.setImageUrl("image_url_2_b");
    attribution2B.setFilename("artist_filename_2_b");
    attribution2B.setChangesMadeEnglish("changes_english_2_b");
    attribution2B.setChangesMadeSpanish("changes_spanish_2_b");

    Attribution attribution3A = new Attribution("image_info_name_3_a");
    attribution3A.setArtist("artist_3_a");
    attribution3A.setLinkToLicense("link_to_license_3_a");
    attribution3A.setNameOfLicense("name_of_license_3_a");
    attribution3A.setArtistImageName("artist_image_name_3_a");
    attribution3A.setImageUrlName("image_url_name_3_a");
    attribution3A.setImageUrl("image_url_3_a");
    attribution3A.setFilename("artist_filename_3_a");
    attribution3A.setChangesMadeEnglish("changes_english_3_a");
    attribution3A.setChangesMadeSpanish("changes_spanish_3_a");

    Attribution attribution4A = new Attribution("image_info_name_4_a");
    attribution4A.setArtist("artist_4_a");
    attribution4A.setLinkToLicense("link_to_license_4_a");
    attribution4A.setNameOfLicense("name_of_license_4_a");
    attribution4A.setArtistImageName("artist_image_name_4_a");
    attribution4A.setImageUrlName("image_url_name_4_a");
    attribution4A.setImageUrl("image_url_4_a");
    attribution4A.setFilename("artist_filename_4_a");
    attribution4A.setChangesMadeEnglish("changes_english_4_a");
    attribution4A.setChangesMadeSpanish("changes_spanish_4_a");

    Attribution attribution9A = new Attribution("image_info_name_9_a");
    attribution9A.setArtist("artist_9_a");
    attribution9A.setLinkToLicense("link_to_license_9_a");
    attribution9A.setNameOfLicense("name_of_license_9_a");
    attribution9A.setArtistImageName("artist_image_name_9_a");
    attribution9A.setImageUrlName("image_url_name_9_a");
    attribution9A.setImageUrl("image_url_9_a");
    attribution9A.setFilename("artist_filename_9_a");
    attribution9A.setChangesMadeEnglish("changes_english_9_a");
    attribution9A.setChangesMadeSpanish("changes_spanish_9_a");

    Attribution attribution10A = new Attribution("image_info_name_10_a");
    attribution10A.setArtist("artist_10_a");
    attribution10A.setLinkToLicense("link_to_license_10_a");
    attribution10A.setNameOfLicense("name_of_license_10_a");
    attribution10A.setArtistImageName("artist_image_name_10_a");
    attribution10A.setImageUrlName("image_url_name_10_a");
    attribution10A.setImageUrl("image_url_10_a");
    attribution10A.setFilename("artist_filename_10_a");
    attribution10A.setChangesMadeEnglish("changes_english_10_a");
    attribution10A.setChangesMadeSpanish("changes_spanish_10_a");

    Attribution attribution11A = new Attribution("image_info_name_11_a");
    attribution11A.setArtist("artist_11_a");
    attribution11A.setLinkToLicense("link_to_license_11_a");
    attribution11A.setNameOfLicense("name_of_license_11_a");
    attribution11A.setArtistImageName("artist_image_name_11_a");
    attribution11A.setImageUrlName("image_url_name_11_a");
    attribution11A.setImageUrl("image_url_11_a");
    attribution11A.setFilename("artist_filename_11_a");
    attribution11A.setChangesMadeEnglish("changes_english_11_a");
    attribution11A.setChangesMadeSpanish("changes_spanish_11_a");

    Attribution attribution15A = new Attribution("image_info_name_15_a");
    attribution15A.setArtist("artist_15_a");
    attribution15A.setLinkToLicense("link_to_license_15_a");
    attribution15A.setNameOfLicense("name_of_license_15_a");
    attribution15A.setArtistImageName("artist_image_name_15_a");
    attribution15A.setImageUrlName("image_url_name_15_a");
    attribution15A.setImageUrl("image_url_15_a");
    attribution15A.setFilename("artist_filename_15_a");
    attribution15A.setChangesMadeEnglish("changes_english_15_a");
    attribution15A.setChangesMadeSpanish("changes_spanish_15_a");

    Attribution attribution16A = new Attribution("image_info_name_16_a");
    attribution16A.setArtist("artist_16_a");
    attribution16A.setLinkToLicense("link_to_license_16_a");
    attribution16A.setNameOfLicense("name_of_license_16_a");
    attribution16A.setArtistImageName("artist_image_name_16_a");
    attribution16A.setImageUrlName("image_url_name_16_a");
    attribution16A.setImageUrl("image_url_16_a");
    attribution16A.setFilename("artist_filename_16_a");
    attribution16A.setChangesMadeEnglish("changes_english_16_a");
    attribution16A.setChangesMadeSpanish("changes_spanish_16_a");

    Attribution attribution17A = new Attribution("image_info_name_17_a");
    attribution17A.setArtist("artist_17_a");
    attribution17A.setLinkToLicense("link_to_license_17_a");
    attribution17A.setNameOfLicense("name_of_license_17_a");
    attribution17A.setArtistImageName("artist_image_name_17_a");
    attribution17A.setImageUrlName("image_url_name_17_a");
    attribution17A.setImageUrl("image_url_17_a");
    attribution17A.setFilename("artist_filename_17_a");
    attribution17A.setChangesMadeEnglish("changes_english_17_a");
    attribution17A.setChangesMadeSpanish("changes_spanish_17_a");

    Attribution attribution21A = new Attribution("image_info_name_21_a");
    attribution21A.setArtist("artist_21_a");
    attribution21A.setLinkToLicense("link_to_license_21_a");
    attribution21A.setNameOfLicense("name_of_license_21_a");
    attribution21A.setArtistImageName("artist_image_name_21_a");
    attribution21A.setImageUrlName("image_url_name_21_a");
    attribution21A.setImageUrl("image_url_21_a");
    attribution21A.setFilename("artist_filename_21_a");
    attribution21A.setChangesMadeEnglish("changes_english_21_a");
    attribution21A.setChangesMadeSpanish("changes_spanish_21_a");

    Attribution attribution23A = new Attribution("image_info_name_23_a");
    attribution23A.setArtist("artist_23_a");
    attribution23A.setLinkToLicense("link_to_license_23_a");
    attribution23A.setNameOfLicense("name_of_license_23_a");
    attribution23A.setArtistImageName("artist_image_name_23_a");
    attribution23A.setImageUrlName("image_url_name_23_a");
    attribution23A.setImageUrl("image_url_23_a");
    attribution23A.setFilename("artist_filename_23_a");
    attribution23A.setChangesMadeEnglish("changes_english_23_a");
    attribution23A.setChangesMadeSpanish("changes_spanish_23_a");

    correctAttributions.put("alphabet_1", constructSet(attribution1A, attribution1B));
    correctAttributions.put("alphabet_2", constructSet(attribution2A, attribution2B));
    correctAttributions.put("alphabet_3", constructSet(attribution3A));
    correctAttributions.put("alphabet_4", constructSet(attribution4A));
    correctAttributions.put("alphabet_9", constructSet(attribution9A));
    correctAttributions.put("alphabet_10", constructSet(attribution10A));
    correctAttributions.put("alphabet_11", constructSet(attribution11A));
    correctAttributions.put("alphabet_15", constructSet(attribution15A));
    correctAttributions.put("alphabet_16", constructSet(attribution16A));
    correctAttributions.put("alphabet_17", constructSet(attribution17A));
    correctAttributions.put("alphabet_21", constructSet(attribution21A));
    correctAttributions.put("alphabet_23", constructSet(attribution23A));
    
    InputStream in = null;
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    File testFile = getFileFromPath(this, "alphabet_doc_1.txt");
    try {
      in = new BufferedInputStream(new FileInputStream(testFile));
      DocumentBuilder builder = factory.newDocumentBuilder();
      doc = builder.parse(in);
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      if (in != null) {
        in.close();
      }
    }
  }

  private static File getFileFromPath(Object obj, String fileName) {
    ClassLoader classLoader = obj.getClass().getClassLoader();
    URL resource = classLoader.getResource(fileName);
    return new File(resource.getPath());
  }

  private ArrayList<CPHint> constructAL (CPHint... args) {
    ArrayList<CPHint> list = new ArrayList<>();
    for (CPHint hint : args) {
      list.add(hint);
    }
    return list;
  }

  private ArrayList<CPReply> constructAL (CPReply... args) {
    ArrayList<CPReply> list = new ArrayList<>();
    for (CPReply reply : args) {
      list.add(reply);
    }
    return list;
  }
  
  private HashSet<Attribution> constructSet (Attribution... args) {
    HashSet<Attribution> set = new HashSet<>();
    for (Attribution attribution : args) {
      set.add(attribution);
    }
    return set;
  }

  @Test
  public void testSceneFromDoc() { // the scene from xml doc contains all the pages and such.
    DocToSceneWorker sceneMaker = new DocToSceneWorker(doc); //doc is initialized in setup.
    sceneMaker.work();
    CPScene scene = sceneMaker.getScene();
    assertEquals("AL-01", scene.sceneName);
    assertEquals("Alphabet_1", scene.englishTitle);
    assertEquals("Alphabeto_1", scene.spanishTitle);

    ArrayList<CPPage> docPages = scene.pages;
    assertTrue(pagesAccountedFor(docPages, correctPages));

    HashMap<Integer, Background> docBackgrounds = scene.backgroundsByPageName;
    assertTrue(backgroundsAccountedFor(docBackgrounds));

    HashMap<Integer, ArrayList<CPHint>> docHints = scene.hintsByPageName;
    assertTrue(hintsAccountedFor(correctHints, docHints));
    
    HashMap<Integer, ArrayList<CPReply>> docReplies = scene.repliesByPageName;
    assertTrue(repliesAccountedFor(correctReplies, docReplies));

    HashMap<String, HashSet<Attribution>> docPictInfos = scene.attributionsByBgName;
    assertTrue(attributionsAccountedFor(correctAttributions, docPictInfos));
  }

  private boolean attributionsAccountedFor (HashMap<String, HashSet<Attribution>> corrAttrs,
                                            HashMap<String, HashSet<Attribution>> docAttrs) {
    int corrNumOfBackgrounds = corrAttrs.size();
    int docNumOfBackgrounds = docAttrs.size();
    if (corrNumOfBackgrounds != docNumOfBackgrounds) return false;
    for (Map.Entry<String, HashSet<Attribution>> entry : corrAttrs.entrySet()) {
      String corrBackgroundName = entry.getKey();
      HashSet<Attribution> corrSet = entry.getValue();
      HashSet<Attribution> docSet = docAttrs.get(corrBackgroundName);
      if (!corrSet.equals(docSet)) return false;
    }
    return true;
  }

  private boolean repliesAccountedFor (HashMap<Integer, ArrayList<CPReply>> correctRepliesHash,
                                       HashMap<Integer, ArrayList<CPReply>> docRepliesHash) {

    int docReplyCount = 0;
    Iterator<ArrayList<CPReply>> dReplyLists = docRepliesHash.values().iterator();
    while (dReplyLists.hasNext()) {
      docReplyCount = docReplyCount + dReplyLists.next().size();
    }

    int corrReplyCount = 0;
    Iterator<ArrayList<CPReply>> cReplyLists = correctRepliesHash.values().iterator();
    while (cReplyLists.hasNext()) {
      corrReplyCount = corrReplyCount + cReplyLists.next().size();
    }

    if (docReplyCount != corrReplyCount) return false;


    for (Map.Entry<Integer, ArrayList<CPReply>> entry : correctRepliesHash.entrySet()) {
      Integer corrPageName = entry.getKey();
      ArrayList<CPReply> corrReplys = entry.getValue();
      ArrayList<CPReply> docReplys = docRepliesHash.get(corrPageName);
      if (docReplys == null) return false;

      for (CPReply corrReply: corrReplys) {
        boolean matchFound = false;
        for (CPReply docReply: docReplys) {
          if (corrReply.equals(docReply)) {
            matchFound = true;
            break;
          }
        }
        if (!matchFound) return false;
      }
    }
    return true;
  }

  private boolean hintsAccountedFor (HashMap<Integer, ArrayList<CPHint>> correctHintsHash,
                                     HashMap<Integer, ArrayList<CPHint>> docHintsHash) {

    int docHintCount = 0;
    Iterator<ArrayList<CPHint>> dHintLists = docHintsHash.values().iterator();
    while (dHintLists.hasNext()) { //Total num of hints is sum of hints in each page, or in this case sum of hints in each list.
      docHintCount = docHintCount + dHintLists.next().size();
    }

    int corrHintCount = 0;
    Iterator<ArrayList<CPHint>> cHintLists = correctHintsHash.values().iterator();
    while (cHintLists.hasNext()) { //Total num of hints is sum of hints in each page, or in this case sum of hints in each list.
      corrHintCount = corrHintCount + cHintLists.next().size();
    }

    if (docHintCount != corrHintCount) return false;

    for (Map.Entry<Integer, ArrayList<CPHint>> entry : correctHintsHash.entrySet()) {
      Integer corrPageName = entry.getKey();
      ArrayList<CPHint> corrHints = entry.getValue();
      ArrayList<CPHint> docHints = docHintsHash.get(corrPageName);
      if (docHints == null) return false;

      for (CPHint corrHint: corrHints) {
        boolean matchFound = false;
        for (CPHint docHint: docHints) {
          if (corrHint.equals(docHint)) {
            matchFound = true;
            break;
          }
        }
        if (!matchFound) return false;
      }
    }
    return true;
  }

  private boolean backgroundsAccountedFor (HashMap<Integer, Background> backgroundsFromDoc) {
    if (correctBackgrounds.size() != backgroundsFromDoc.size()) return false;
    for (Integer corrPageName : correctBackgrounds.keySet()) {
      Background corrBackground = correctBackgrounds.get(corrPageName);
      Background docBackground = backgroundsFromDoc.get(corrPageName);
      if (!corrBackground.equals(docBackground)) return false;
    }
    return true;
  }

  private boolean pagesAccountedFor (ArrayList<CPPage> firstList,
                                     ArrayList<CPPage> secondList) {
    if (firstList.size() != secondList.size()) return false;
    for (CPPage page: firstList) {
      if (!secondList.contains(page)) return false;
    }
    return true;
  }

}
