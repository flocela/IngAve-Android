package com.olfybsppa.inglesaventurero.utilsutest;

import android.support.v4.util.Pair;

import com.olfybsppa.inglesaventurero.utils.TextFormatter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TextFormatterUTest {

  @Test
  public void testWithData1 () {
    String english = "Do you still need players for Saturday?";
    String spanish = "Haces tú todavía necesitar jugadores para sábado?";
    TextFormatter textFormatter = new TextFormatter();
    Pair<String, String> output_eng_spn = textFormatter.lineUpWords(english, spanish);
    assertEquals("Do*** you still** need***** players** for* Saturday?", output_eng_spn.first);
    assertEquals("Haces tú* todavía necesitar jugadores para sábado**?", output_eng_spn.second);
  }

  @Test
  public void testWithData2 () {
    String english = "Do you speak Spanish today?";
    String spanish = "Haces tú hablar español hoy?";
    TextFormatter textFormatter = new TextFormatter();
    Pair<String, String> output_eng_spn = textFormatter.lineUpWords(english, spanish);
    assertEquals("Do*** you speak* Spanish today?", output_eng_spn.first);
    assertEquals("Haces tú* hablar español hoy**?", output_eng_spn.second);
  }

  @Test
  public void testWithData3 () {
    String english = "Yeah, sure.";
    String spanish = "Sí, seguro.";
    TextFormatter textFormatter = new TextFormatter();
    Pair<String, String> output_eng_spn = textFormatter.lineUpWords(english, spanish);
    assertEquals("Yeah, sure**.", output_eng_spn.first);
    assertEquals("Sí**, seguro.", output_eng_spn.second);
  }

  @Test
  public void testWithData4 () {
    String english = "What should we get?";
    String spanish = "¿Qué debemos nosotros conseguir?";
    TextFormatter textFormatter = new TextFormatter();
    Pair<String, String> output_eng_spn = textFormatter.lineUpWords(english, spanish);
    assertEquals("What should* we****** get******?", output_eng_spn.first);
    assertEquals("¿Qué debemos nosotros conseguir?", output_eng_spn.second);
  }

}