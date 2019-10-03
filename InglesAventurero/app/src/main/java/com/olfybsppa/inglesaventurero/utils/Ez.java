package com.olfybsppa.inglesaventurero.utils;


import android.content.Context;
import android.os.Bundle;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.collectors.SceneInfo;

import java.io.File;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ez {

  public static String where(String where, String equals) {
    return new StringBuilder(where).append("='")
      .append(equals)
      .append("'").toString();
  }

  public static String where(String where1, String equals1, String where2, String equals2) {
    return new StringBuilder(where1).append("='")
      .append(equals1)
      .append("'")
      .append(" AND ")
      .append(where2).append("='")
      .append(equals2)
      .append("'").toString();
  }

  public static String where(String where1, String equals1,
                             String where2, String equals2,
                             String where3, String equals3) {
    return new StringBuilder(where1).append("='")
      .append(equals1)
      .append("'")
      .append(" AND ")
      .append(where2).append("='")
      .append(equals2)
      .append("'")
      .append(" AND ")
      .append(where3).append("='")
      .append(equals3)
      .append("'").toString();
  }

  public static String orWhere(String where, String equals1, String equals2, String equals3) {
    return new StringBuilder(where).append("='")
      .append(equals1)
      .append("'")
      .append(" OR ")
      .append(where).append("='")
      .append(equals2)
      .append("'")
      .append(" OR ")
      .append(where).append("='")
      .append(equals3)
      .append("'").toString();
  }

  public static String orWhere(String where, Iterable<Integer> equals) {
    Iterator<Integer> iterator = equals.iterator();
    if (!iterator.hasNext())
      return "";
    StringBuilder builder = new StringBuilder(where).append("='");
    builder.append(iterator.next()).append("'");
    while (iterator.hasNext()) {
      builder.append(" OR ")
        .append(where).append("='")
        .append(""+iterator.next())
        .append("'");
    }
    return builder.toString();
  }

  public static Bundle convertToBundle (SceneInfo sceneInfo) {
    Bundle bundle = new Bundle();
    bundle.putString(MaP.ENGLISH_TITLE, sceneInfo.getEnglishTitle());
    bundle.putString(MaP.SPANISH_TITLE, sceneInfo.getSpanishTitle());
    bundle.putString(MaP.FILENAME, sceneInfo.getFilename());
    bundle.putInt(MaP.SCENE_WEB_ID, sceneInfo.getWebId());
    return bundle;
  }

  public static String formatSceneTitle (String sceneName) {
    Pattern zeroPattern = Pattern.compile(".*(\\d+)$");
    Matcher mm = zeroPattern.matcher(sceneName);
    if (mm.matches()) {
      String number = mm.group(1);
      if (Integer.parseInt(number) < 10) {
        return sceneName.replace("- number", "- 0"+number);
      }
      else {
        return sceneName;
      }
    }
    else
      return sceneName;
  }

  public static String getPath (String fileName) {
    String trimmedName = fileName.trim();
    int lastSlashIdx = trimmedName.lastIndexOf('/');
    if (-1 == lastSlashIdx)
      return "";
    else
      return trimmedName.substring(0, lastSlashIdx+1);
  }

  public static String generateRandomWord()
  {
    Random random = new Random();
      int overThreeLetters = random.nextInt(4);
      char[] word = new char[overThreeLetters+3]; // words of length 3 through 6. (1 and 2 letter words are boring.)
      for(int j = 0; j < word.length; j++) {
        word[j] = (char) ('a' + random.nextInt(26));
      }
    return new String(word);
  }

  //TODO need to add test, difficult because Context .getMatchedWith is final.
  public static File getPrivateDirectory (Context context) {
    String dirName = context.getString(R.string.private_directory);
    return context.getDir(dirName, Context.MODE_PRIVATE);
  }

  public static String formatStoryToMP3FileName (String story) {
    return story.toLowerCase()+".m4a";
  }

  //TODO need to add test, difficult because Context .getMatchedWith is final.
  public static String storyToFullMP3Name(Context context, String story) {
    String dirName = getPrivateDirectory(context).getAbsolutePath();
    String fileName = formatStoryToMP3FileName(story);
    return dirName + "/" + fileName;
  }

  public static Bundle oneIntBundle(String tag, int num) {
    Bundle bundle = new Bundle();
    bundle.putInt(tag, num);
    return bundle;
  }

  public static Bundle oneStringBundle(String tag, String text) {
    Bundle bundle = new Bundle();
    bundle.putString(tag, text);
    return bundle;
  }
}
