package io.ebean.typequery.agent;

import org.junit.Test;

import static org.junit.Assert.*;

public class IgnoreClassHelperTest {


  @Test
  public void test() {

    IgnoreClassHelper ignoreClassHelper = new IgnoreClassHelper(new String[0]);

    assertTrue(ignoreClassHelper.isIgnoreClass("java/lang/Boolean"));
    assertTrue(ignoreClassHelper.isIgnoreClass("org/joda/LocalDate"));
    assertFalse(ignoreClassHelper.isIgnoreClass("org/koda/Foo"));
    assertFalse(ignoreClassHelper.isIgnoreClass("foo/Foo"));
    assertFalse(ignoreClassHelper.isIgnoreClass("bar/pixie/Foo"));
    assertFalse(ignoreClassHelper.isIgnoreClass("bar/poo/Foo"));
  }

  @Test
  public void testWithPackages() {

    IgnoreClassHelper ignoreClassHelper = new IgnoreClassHelper(new String[]{"foo","bar/pixie"});

    assertTrue(ignoreClassHelper.isIgnoreClass("java/lang/Boolean"));
    assertTrue(ignoreClassHelper.isIgnoreClass("org/joda/LocalDate"));
    assertTrue(ignoreClassHelper.isIgnoreClass("org/koda/Foo"));
    assertFalse(ignoreClassHelper.isIgnoreClass("foo/Foo"));
    assertFalse(ignoreClassHelper.isIgnoreClass("bar/pixie/Foo"));
    assertTrue(ignoreClassHelper.isIgnoreClass("bar/poo/Foo"));
  }
}
