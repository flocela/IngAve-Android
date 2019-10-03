package com.olfybsppa.inglesaventurero.webscenelistactivity;


import com.olfybsppa.inglesaventurero.collectors.Attribution;
import com.olfybsppa.inglesaventurero.collectors.Background;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.ImageCredit;
import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPHint;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPPage;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPReply;
import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPScene;
import com.olfybsppa.inglesaventurero.worker.Worker;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;

public class DocToSceneWorker extends Worker {

  private Document doc;
  private CPScene scene;

  public DocToSceneWorker(Document doc) {
    this.doc = doc;
  }

  public void work () {
    Element sceneNode = getSceneElement(doc);
    attachSceneInfo (sceneNode);
    attachPages(sceneNode); // note pages don't have backgroundfilenames attached to them.
    attachHints(sceneNode);
    attachReplies(sceneNode);
    workDone = true;
  }

  public CPScene getScene () {
    return scene;
  }

  private Element getSceneElement (Document doc) {
    NodeList nodeList = doc.getElementsByTagName("scene");
    return (Element)nodeList.item(0);
  }

  private void attachSceneInfo (Element sceneNode) {
    scene = new CPScene(getTextFrom(sceneNode, LinesCP.scene_name),
                        getTextFrom(sceneNode, LinesCP.english_title),
                        getTextFrom(sceneNode, LinesCP.spanish_title),
                        getTextFrom(sceneNode, LinesCP.type_1),
                        getTextFrom(sceneNode, LinesCP.type_2));
    scene.setEnglishDescription(getTextFrom(sceneNode, LinesCP.english_description));
    scene.setSpanishDescription(getTextFrom(sceneNode, LinesCP.spanish_description));
    scene.setEnglishVoiceInfo(getTextFrom(sceneNode, LinesCP.voice_attr_eng));
    scene.setSpanishVoiceInfo(getTextFrom(sceneNode, LinesCP.voice_attr_spn));
  }

  private void attachPages (Element sceneNode) {
    NodeList pages = sceneNode.getElementsByTagName("page");
    for (int pp=0; pp<pages.getLength(); pp++) {
      Element pageElement = (Element)pages.item(pp);
      CPPage page = extractPage(pageElement);
      scene.addPage(page);
      Element backgroundEl = getSubElement(pageElement, "background");
      Background background = extractBackground(backgroundEl);
      scene.addBackground(page.getPageName(), background);
      attachAttributions(background.getBackgroundName(), backgroundEl);
    }
  }

  private void attachHints (Element sceneNode) {
    NodeList hints = sceneNode.getElementsByTagName("hint");
    for (int hh=0; hh<hints.getLength(); hh++) {
      Element hintElement = (Element)hints.item(hh);
      HashMap<String, Object> pair = extractPageAndHint(hintElement);
      scene.addHint((Integer)pair.get("pageName"), (CPHint)pair.get("hint"));
      // can not get Pair class to work properly, using Hashmap instead???
      // It's an android class.
    }
  }

  private void attachReplies (Element sceneNode) {
    NodeList replies = sceneNode.getElementsByTagName("reply");
    for (int rr=0; rr<replies.getLength(); rr++) {
      Element replyElement = (Element)replies.item(rr);
      HashMap<String, Object> pair = extractPageAndReply(replyElement);
      scene.addReply((Integer)pair.get("pageName"), (CPReply)pair.get("reply"));
    }
  }

  private void attachAttributions(String backgroundName, Element backgroundElement) {
    NodeList attributions = backgroundElement.getElementsByTagName("attribution");
    for (int pi=0; pi<attributions.getLength(); pi++) {
      Element attributionEl = (Element)attributions.item(pi);
      Attribution attribution = extractAttribution(attributionEl);
      scene.addAttribution(backgroundName, attribution);
    }
  }

  private Attribution extractAttribution (Element attributionEl) {
    ImageCredit credit = extractImageCredit(getSubElement(attributionEl, "image_credit"));
    return new Attribution(getTextFrom(attributionEl, LinesCP.changes_english),
                           getTextFrom(attributionEl, LinesCP.changes_spanish),
                           credit);
  }

  private ImageCredit extractImageCredit (Element creditEl) {
    ImageCredit credit = new ImageCredit(getTextFrom(creditEl, LinesCP.image_info_name));
    credit.setArtist(getTextFrom(creditEl, LinesCP.artist));
    credit.setLinkToLicense(getTextFrom(creditEl, LinesCP.link_to_license));
    credit.setNameOfLicense(getTextFrom(creditEl, LinesCP.name_of_license));
    credit.setArtistImageName(getTextFrom(creditEl, LinesCP.artist_image_name));
    credit.setImageUrl(getTextFrom(creditEl, LinesCP.image_url));
    credit.setImageUrlName(getTextFrom(creditEl, LinesCP.image_url_name));
    credit.setArtistFilename(getTextFrom(creditEl, LinesCP.artist_filename));
    return credit;
  }

  private HashMap<String, Object> extractPageAndHint (Element hintNode) {
    Integer pageName = getIntegerFrom(hintNode, LinesCP.page_name);
    Integer position = getIntegerFrom(hintNode, LinesCP.pos_id);
    Integer groupID  = getIntegerFrom(hintNode, LinesCP.hint_group_id);
    CPHint hint = new CPHint(position, groupID);
    hint.setNextPage(getIntegerFrom(hintNode, LinesCP.next_page_name));
    hint.setTimes(getIntegerFrom(hintNode, LinesCP.normal_start_time),
      getIntegerFrom(hintNode, LinesCP.normal_end_time),
      getIntegerFrom(hintNode, LinesCP.slow_start_time),
      getIntegerFrom(hintNode, LinesCP.slow_end_time));
    hint.setEnglishPhrase(getTextFrom(hintNode, LinesCP.english_phrase));
    hint.setSpanishPhrase(getTextFrom(hintNode, LinesCP.spanish_phrase));
    hint.setWFWEnglish(getTextFrom(hintNode, LinesCP.wfw_english));
    hint.setWFWSpanish(getTextFrom(hintNode, LinesCP.wfw_spanish));
    hint.setEngExplanation(getTextFrom(hintNode, LinesCP.eng_explanation));
    hint.setSpnExplanation(getTextFrom(hintNode, LinesCP.spn_explanation));
    HashMap<String, Object> pair = new HashMap<>();
    pair.put("pageName", pageName);
    pair.put("hint", hint);
    return pair;
  }
  
  private HashMap<String, Object> extractPageAndReply (Element replyNode) {
    Integer pagename = getIntegerFrom(replyNode, LinesCP.page_name);
    Integer position = getIntegerFrom(replyNode, LinesCP.pos_id);
    Integer replyGroup = getIntegerFrom(replyNode, LinesCP.reply_group_id);
    CPReply reply = new CPReply(position, replyGroup);
    reply.setNextPage(getIntegerFrom(replyNode, LinesCP.next_page_name));
    reply.setTimes(getIntegerFrom(replyNode, LinesCP.normal_start_time),
      getIntegerFrom(replyNode, LinesCP.normal_end_time),
      getIntegerFrom(replyNode, LinesCP.slow_start_time),
      getIntegerFrom(replyNode, LinesCP.slow_end_time));
    reply.setEnglishPhrase(getTextFrom(replyNode, LinesCP.english_phrase));
    reply.setSpanishPhrase(getTextFrom(replyNode, LinesCP.spanish_phrase));
    reply.setWFWEnglish(getTextFrom(replyNode, LinesCP.wfw_english));
    reply.setWFWSpanish(getTextFrom(replyNode, LinesCP.wfw_spanish));
    reply.setEngExplanation(getTextFrom(replyNode, LinesCP.eng_explanation));
    reply.setSpnExplanation(getTextFrom(replyNode, LinesCP.spn_explanation));
    reply.setRegex(getTextFrom(replyNode, LinesCP.regex));
    HashMap<String, Object> pair = new HashMap<>();
    pair.put("pageName", pagename);
    pair.put("reply", reply);
    return pair;
  }

  private CPPage extractPage (Element pageNode) {
    CPPage page = new CPPage(getIntegerFrom(pageNode, LinesCP.page_name));
    if (1 == getIntegerFrom(pageNode, LinesCP.is_first))
      page.setAsFirst(true);
    return page;
  }

  private Background extractBackground(Element backgroundE) {
    Background background = new Background();
    background.addFilename(getTextFrom(backgroundE, "background_filename"));
    background.addName(getTextFrom(backgroundE, LinesCP.background_name));
    return background;
  }

  private String getTextFrom (Element element, String xmlTagString) {
    NodeList nodeList = element.getElementsByTagName(xmlTagString);
    if ( 0 < nodeList.getLength() ) {
      Element subElement = (Element)nodeList.item(0);
      NodeList subNodeList = subElement.getChildNodes();
      Node tagNode = subNodeList.item(0);
      if (null != tagNode) {
        return tagNode.getNodeValue().trim();
      }
    }
    return "";
  }

  private Integer getIntegerFrom (Element element, String xmlTagString) {
    String text = getTextFrom(element, xmlTagString);
    if (text.isEmpty()) return null;
    return Integer.valueOf(text);
  }
  private Element getSubElement (Element element, String tagName) {
    NodeList nodeList = element.getElementsByTagName(tagName);
    if (0 < nodeList.getLength()) {
      return (Element)nodeList.item(0);
    }
    return null;
  }



}
