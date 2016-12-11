package io.ebean.typequery.agent;

import org.junit.Test;

import static org.junit.Assert.*;


public class EnhanceContextTest {

  @Test
  public void testInclude() {

    String args = "packages=org.example,org.example.domain.query";

    EnhanceContext context = new EnhanceContext(args, EnhanceContextTest.class.getClassLoader(), null);

    assertTrue(context.isQueryBean("org/example/domain/query/QProduct"));
    assertTrue(context.isQueryBean("org/example/domain/query/assoc/QAssocProduct"));
    assertFalse(context.isQueryBean("org/example/domain/foo/QAssocProduct"));

    assertFalse(context.isIgnoreClass("org/example/domain/query/QProduct"));
    assertFalse(context.isIgnoreClass("org/example/domain/query/assoc/QAssocProduct"));
    assertFalse(context.isIgnoreClass("org/example/finder/MyFinder"));
    assertFalse(context.isIgnoreClass("org/example/MyFinder"));
    assertTrue(context.isIgnoreClass("org/foo/MyFinder"));

  }

}