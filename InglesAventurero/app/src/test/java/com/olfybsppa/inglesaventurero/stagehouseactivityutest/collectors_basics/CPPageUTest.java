package com.olfybsppa.inglesaventurero.stagehouseactivityutest.collectors_basics;

import com.olfybsppa.inglesaventurero.webscenelistactivity.collectors.CPPage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class CPPageUTest {

  private CPPage cppage1;
  private CPPage cppage2;

  @Before
  public  void setUp() throws Exception {
    cppage1 = new CPPage();
    cppage1.setPageName(1);
    cppage1.setAsFirst(true);

    cppage2 = new CPPage();
    cppage2.setPageName(2);
    cppage2.setAsFirst(false);
  }

  @Test
  public void testIsFirstGetter () {
    assertTrue(cppage1.isFirst());
  }

  @Test
  public void equalsWhenNull () {
    assertFalse(cppage1.equals(null));
  }

  @Test
  public void equalsSymmetric () {
    CPPage copy = new CPPage();
    copy.setPageName(1);
    copy.setAsFirst(true);

    assertTrue(cppage1.equals(copy));
    assertTrue(copy.equals(cppage1));
  }

  @Test
  public void equalsTransitive () {
    CPPage copyA = new CPPage();
    copyA.setPageName(1);
    copyA.setAsFirst(true);

    CPPage copyB = new CPPage();
    copyB.setPageName(1);
    copyB.setAsFirst(true);

    assertTrue(cppage1.equals(copyA));
    assertTrue(copyA.equals(copyB));
    assertTrue(cppage1.equals(copyB));
  }

  @Test
  public void equalsHashCodeOnDifferentObjects () {
    int hashCode1 = cppage1.hashCode();
    int hashCode2 = cppage2.hashCode();
    assertFalse(hashCode1 == hashCode2); // doesn't really have to be true, but testing
    // for now.
  }

  @Test
  public void equalsHashCodeOnSameObject () {
    int hashCode1a = cppage1.hashCode();
    int hashCode1b = cppage1.hashCode();
    assertTrue(hashCode1a == hashCode1b);
  }

  @Test
  public void equalsHashCodeOnEqualObjects () {
    CPPage copy = new CPPage();
    copy.setPageName(1);
    copy.setAsFirst(true);

    assertTrue(cppage1.hashCode() == copy.hashCode());
  }

  @Test
  public void testEqualsAndHashCodeInArrayListsContainsMethod () {
    ArrayList<CPPage> list = new ArrayList<>();
    list.add(cppage1);
    assertFalse(list.contains(cppage2));
    assertTrue(list.contains(cppage1));
  }
}
