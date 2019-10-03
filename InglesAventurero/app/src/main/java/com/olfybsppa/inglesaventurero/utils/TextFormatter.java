package com.olfybsppa.inglesaventurero.utils;

import android.support.v4.util.Pair;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.text.Normalizer;

public class TextFormatter {

  public static Pair<String, String> lineUpWords (String english,
                                                  String spanish) {
    english = english.trim();
    spanish = spanish.trim();
    english = english.replaceAll(" +", " ");
    spanish = spanish.replaceAll(" +", " ");
    String[] englishWords = english.split(" ");
    String[] spanishWords = spanish.split(" ");
    int[] englishNumOfChars = getNumOfChars(englishWords);
    int[] spanishNumOfChars = getNumOfChars(spanishWords);
    String[] starredEnglishWords = updateChars(englishWords, spanishNumOfChars);
    String[] starredSpanishWords = updateChars(spanishWords, englishNumOfChars);
    String englishString = concatenateWords(starredEnglishWords);
    String spanishString = concatenateWords(starredSpanishWords);
    return (new Pair<>(englishString, spanishString));
  }

  public static Spannable applyColor(String string,
                                            int regColor,
                                            int invisibleColor) {
    Spannable span = new SpannableStringBuilder(string);
    int startStarIndex  = string.indexOf('*');
    if (startStarIndex == -1) {
      span.setSpan(new ForegroundColorSpan(regColor), 0, string.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
      return span;
    }
    if (startStarIndex != 0) {
      span.setSpan(new ForegroundColorSpan(regColor), 0, startStarIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }
    while (startStarIndex != -1) {
      int followingStarIndex = startStarIndex;
      int endStarIndex = string.indexOf("*",followingStarIndex+1);

      while (endStarIndex - followingStarIndex ==1) {
        followingStarIndex = endStarIndex;
        endStarIndex = string.indexOf("*", followingStarIndex+1);
      }
      span.setSpan(new ForegroundColorSpan(invisibleColor), startStarIndex, followingStarIndex + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
      if (followingStarIndex == string.length()-1)
        break;
      if (endStarIndex != -1)
        span.setSpan(new ForegroundColorSpan(regColor), followingStarIndex+1, endStarIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
      else
        span.setSpan(new ForegroundColorSpan(regColor), followingStarIndex+1, string.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
      startStarIndex = endStarIndex;
    }

    return span;
  }


  private static int[] getNumOfChars (String[] words) {
    int[] numOfChar = new int[words.length];
    for (int ii=0; ii<words.length; ii++) {
      String noAccent = Normalizer.normalize(words[ii], Normalizer.Form.NFD);
      int count = 0;
      for (char c : noAccent.toCharArray()) {
        if (c == '\u00BF') //inverted question mark
          count++;
        else if (c <= '\u007F')
          count ++;
      }
      numOfChar[ii] = count;
    }
    return numOfChar;
  }

  public static String flattenToAscii(String string) {
    StringBuilder sb = new StringBuilder();
    string = Normalizer.normalize(string, Normalizer.Form.NFD);
    for (char c : string.toCharArray()) {
      if (c == '\u2E2E')
        sb.append(c);
      else if (c <= '\u007F')
        sb.append(c);
    }
    return sb.toString();
  }

  private static String[] updateChars (String[] accentedWords, int[] numOfChars) {
    String[] returnStrings = new String[accentedWords.length];

    int[] origNumOfLettersArray = getNumOfChars(accentedWords);
    for (int ii=0; ii<numOfChars.length; ii++) {
      int origNumOfLetters  = origNumOfLettersArray[ii];
      int finalNumOfLetters = numOfChars[ii];
      int diff = origNumOfLetters - finalNumOfLetters;
      if (diff < 0) {
        StringBuilder builder = new StringBuilder(accentedWords[ii]);
        for (int ss=0; ss<-diff; ss++) {
          builder.append('*');
        }
        returnStrings[ii] = builder.toString();
      }
      else
        returnStrings[ii] = accentedWords[ii];
    }
    for (int jj=0; jj<returnStrings.length; jj++) {
      String word = returnStrings[jj];
      if (-1 != word.indexOf("?*")) {
          word = word.replace("?", "");
          word = word + "?";
      }
      else if (-1 != word.indexOf(".*")) {
        word = word.replace(".", "");
        word = word + ".";
      }
      else if (-1 != word.indexOf(",*")) {
        word = word.replace(",", "");
        word = word + ",";
      }
      else if (-1 != word.indexOf("!*")) {
        word = word.replace("!", "");
        word = word + "!";
      }
      returnStrings[jj] = word;
    }
    return returnStrings;
  }

  private static String concatenateWords (String[] words) {
    if (words.length ==0)
      return "";
    StringBuilder builder = new StringBuilder(words[0]);
    for (int ii=1; ii<words.length; ii++) {
      builder.append(" ");
      builder.append(words[ii]);
    }
    return builder.toString();
  }



}
