package com.olfybsppa.inglesaventurero.utils;

import android.net.Uri;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UriDeterminer {
	
  public static final Pattern contentProviderPattern
  = Pattern.compile("(content://com\\.olfybsppa\\.inglesaventurero\\.start\\.LinesCP/(\\w+))");

  public static final Pattern sceneTablePattern
    = Pattern.compile("(content://com\\.olfybsppa\\.inglesaventurero\\.start\\.LinesCP/\\w+/(\\w+))");

  public static final Pattern scenesPattern
    = Pattern.compile("http:..www.appsbyflo.com.ingles_aventurero.(.*)");

  public static String getTableName (Uri uri) {
    Matcher m = contentProviderPattern.matcher(uri.toString());
    m.matches();
    try {
      return m.group(2);
    }
    catch (IllegalStateException e) {
      return null;
    }	
  }

  public static Integer getLastId (Uri uri) {
    Matcher m = sceneTablePattern.matcher(uri.toString());
    m.matches();
    try {
      return Integer.parseInt(m.group(2));
    }
    catch (IllegalStateException e) {
      return null;
    }
  }

  public static String getScenId (String url) {
    Matcher m = scenesPattern.matcher(url);
    m.matches();
    try {
      return m.group(1);
    }
    catch (IllegalStateException e) {
      return null;
    }
  }
}
