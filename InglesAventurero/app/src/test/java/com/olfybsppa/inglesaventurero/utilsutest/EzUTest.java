package com.olfybsppa.inglesaventurero.utilsutest;


import com.olfybsppa.inglesaventurero.utils.Ez;

import org.junit.Test;

import java.util.HashSet;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class EzUTest {

  @Test
  public void testOneColumnWhereClause () {
    String whereClause = Ez.where("col1", "some_name");
    assertEquals(whereClause, "col1='some_name'");
  }

  @Test
  public void testTwoColumnWhereClause () {
    String whereClause = Ez.where("col1", "one_name", "col2", "another_name");
    assertEquals(whereClause, "col1='one_name' AND col2='another_name'");
  }

  @Test
  public void testThreeColumnWhereClause () {
    String whereClause = Ez.where("col1", "one_name",
      "col2", "another_name",
      "col3", "last_name");
    assertEquals(whereClause, "col1='one_name' AND col2='another_name' AND col3='last_name'");
  }

  @Test
  public void testOrWhereClause () {
    String whereClause = Ez.orWhere("col1", "one_name", "two_name", "three_name");
    assertEquals(whereClause, "col1='one_name' OR col1='two_name' OR col1='three_name'");
  }

  @Test
  public void testShouldReturnPathWithLastSlashWithoutFileName () {
    String fullFileName = "dirAAA/dirAA/dirA/fileA.txt";
    String ezFilePath = Ez.getPath(fullFileName);
    assertEquals("dirAAA/dirAA/dirA/", ezFilePath);
  }

  @Test
  public void testEZFilePathEmpty () {
    String emptyFileName = "";
    assertTrue(Ez.getPath(emptyFileName).isEmpty());
  }

  @Test
  public void testGenerateRandomWord () {
    HashSet<String> hash = new HashSet<String>();
    for (int ii=0; ii<100; ii++) {
      String word = Ez.generateRandomWord();
      assertTrue(hash.add(word));
    }
  }

}
