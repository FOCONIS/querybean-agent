package io.ebean.typequery.agent;

import org.junit.Test;

import static org.junit.Assert.*;

public class DetectQueryBeanTest {

  @Test
  public void testToString_when_empty() throws Exception {
    assertEquals(new DetectQueryBean(new String[]{}).toString(),"[]");
  }

  @Test
  public void testToString() throws Exception {
    assertEquals(new DetectQueryBean(new String[]{"one", "two"}).toString(),"[one, two]");
  }

  @Test
  public void isEmpty_when_empty() throws Exception {
    assertTrue(new DetectQueryBean(new String[]{}).isEmpty());
  }

  @Test
  public void isEmpty() throws Exception {
    assertFalse(new DetectQueryBean(new String[]{"one", "two"}).isEmpty());
  }

  @Test
  public void getPackages_when_empty() throws Exception {

    String[] packages = new DetectQueryBean(new String[]{}).getPackages();
    assertEquals(packages.length, 0);
  }

  @Test
  public void getPackages() throws Exception {

    String[] packages = new DetectQueryBean(new String[]{"one", "two"}).getPackages();
    assertEquals(packages.length, 2);
    assertEquals(packages[0], "one");
    assertEquals(packages[1], "two");
  }

  @Test
  public void isQueryBean() throws Exception {

    DetectQueryBean detectQueryBean = new DetectQueryBean(new String[]{"org/z/domain", "org/z/dom/sub"});

    assertTrue(detectQueryBean.isQueryBean("org/z/domain/query/QCust"));
    assertTrue(detectQueryBean.isQueryBean("org/z/domain/query/assoc/QCust"));
    assertTrue(detectQueryBean.isQueryBean("org/z/domain/sub/query/QCust"));
    assertTrue(detectQueryBean.isQueryBean("org/z/domain/sub/query/assoc/QCust"));

    assertTrue(detectQueryBean.isQueryBean("org/z/dom/sub/query/QCust"));
    assertTrue(detectQueryBean.isQueryBean("org/z/dom/sub/query/assoc/QCust"));
    assertTrue(detectQueryBean.isQueryBean("org/z/dom/sub/sub/query/QCust"));
    assertTrue(detectQueryBean.isQueryBean("org/z/dom/sub/sub/query/assoc/QCust"));
  }

  @Test
  public void isQueryBean_when_notDomainPackage() throws Exception {

    DetectQueryBean detectQueryBean = new DetectQueryBean(new String[]{"org.z.domain", "org.z.dom.sub"});

    assertFalse(detectQueryBean.isQueryBean("org/z/other/query/QCust"));
    assertFalse(detectQueryBean.isQueryBean("org/z/other/query/assoc/QCust"));
  }

  @Test
  public void isQueryBean_when_notQuerySubpackage() throws Exception {

    DetectQueryBean detectQueryBean = new DetectQueryBean(new String[]{"org.z.domain", "org.z.dom.sub"});

    // incorrect package
    assertFalse(detectQueryBean.isQueryBean("org/z/domain/queryx/QCust"));
    assertFalse(detectQueryBean.isQueryBean("org/z/domain/queryx/assoc/QCust"));
    assertFalse(detectQueryBean.isQueryBean("org/z/domain/query/assocx/QCust"));

    // not starting with Q
    assertFalse(detectQueryBean.isQueryBean("org/z/domain/query/Cust"));
    assertFalse(detectQueryBean.isQueryBean("org/z/domain/query/assoc/Cust"));
  }

}