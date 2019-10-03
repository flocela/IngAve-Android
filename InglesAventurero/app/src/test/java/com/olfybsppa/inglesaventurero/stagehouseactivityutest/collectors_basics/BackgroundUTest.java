package com.olfybsppa.inglesaventurero.stagehouseactivityutest.collectors_basics;

import com.olfybsppa.inglesaventurero.collectors.Background;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class BackgroundUTest{
  private Background background  = new Background("one", "one.jpg");
  private Background backgroundB = new Background("two", "one.jpg");
  private Background backgroundC = new Background("one", "two.jpg");
  private Background copy =        new Background("one", "one.jpg");


  @Test
  public void testBackgroundsAreEqual () {
    assertTrue(background.equals(copy));
  }

  @Test
  public void testEqualsWhenNamesAreDifferent () {
    assertFalse(background.equals(backgroundB));
  }

  @Test
  public void testEqualsFilenamesAreDifferent () {
    assertFalse(background.equals(backgroundC));
  }

  @Test
  public void testHashcode () {
    assertTrue(background.hashCode() == (copy.hashCode()));
  }

  @Test
  public void testHashCodeWhenNamesAreDifferent () {
    assertFalse(background.hashCode() == backgroundB.hashCode());
  }

  @Test
  public void testHashCodeWhenFilenamesAreDifferent () {
    assertFalse(background.hashCode() == backgroundC.hashCode());
  }

}
