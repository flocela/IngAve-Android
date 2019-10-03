package com.olfybsppa.inglesaventurero.utils;

/**
 * Created by flo on 1/8/15.
 */
public class MaP {
  public static final int DIALOG_NOTIFICATION_ID = 1003;
  public static Integer NOTIFICATION_ID = 3000;

  public static String EXCEPTION_NOTIFICATION_TAG = "EXCEPTION_NOTIFICATION";

  public static String MESSAGE = "message";
  public static String NO_INTERNET_TAG = "no internet";
  public static String REQUEST_CODE = "request_code";
  public static String TITLE = "title";

  public static String ENGLISH_TITLE = "ENGLISH_TITLE";
  public static String SPANISH_TITLE = "SPANISH_TITLE";
  public static String FILENAME = "FILENAME";
  public static String SCENE_WEB_ID = "SCENE_WEB_ID";

  public static String BACKGROUNDS_MP3_ZIP_URL = "backgrounds_mp3_zip_url";
  public static String SCENE_INFO_URL = "scene_info_url";

  public static String WEBSITE = "http://www.appsbyflo.com/ingles_aventurero";

  public static String background_id = "background_id";
  public static String picture_info_id = "picture_info_id";
  public static String id = "id";
  public static String image_name = "image_name";
  public static String photographer = "photographer";
  public static String file = "file";
  public static String url_name = "url_name";

  public static String ZIP_FILE_DOWNLOADER = "zip_file_downloader";
  public static String FRAGMENT_TAG = "fragment_tag";

  public static String TYPE = "type";
  public static String INSTRUCTIONS_SPANISH = "INSTRUCTIONS_SPANISH";
  public static String INSTRUCTIONS_ENGLISH = "INSTRUCTIONS_ENGLISH";


  public static String getSceneXMLURL (int sceneId) {
    StringBuilder stringBuilder = new StringBuilder(WEBSITE);
    stringBuilder.append("/scenes/").append(sceneId).append(".xml");
    return stringBuilder.toString();
  }

  public static String getBackgroundsZipUrl (int sceneId) {
    StringBuilder stringBuilder = new StringBuilder(WEBSITE);
    stringBuilder.append("/scenes/get_zip/").append(sceneId);
    return stringBuilder.toString();
  }

}
