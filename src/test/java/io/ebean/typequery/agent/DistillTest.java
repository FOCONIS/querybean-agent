package io.ebean.typequery.agent;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class DistillTest {

  @Test
  public void parse() throws Exception {

    String[] pkgs = Distill.parsePackages("com.foo, org.z");
    assertEquals(pkgs.length, 2);
    assertEquals(pkgs[0], "com/foo/");
    assertEquals(pkgs[1], "org/z/");
  }

  @Test
  public void merge() throws Exception {

    String[] pkgs = Distill.mergePackages(new String[]{"one","two"}, new String[]{"two","three"});

    assertEquals(pkgs.length, 4);
    assertEquals(pkgs[0], "one");
    assertEquals(pkgs[1], "two");
    assertEquals(pkgs[2], "two");
    assertEquals(pkgs[3], "three");
  }

  @Test
  public void convert() throws Exception {

    DetectQueryBean detectQueryBean = Distill.convertDetectQueryBean(Arrays.asList("com.foo.domain", "org.z"));
    String[] pkgs = detectQueryBean.getPackages();
    assertEquals(pkgs.length, 2);
    assertEquals(pkgs[0], "com/foo/domain/");
    assertEquals(pkgs[1], "org/z/");
  }

  @Test
  public void convert_when_querySuffix_expect_trim() throws Exception {

    DetectQueryBean detectQueryBean = Distill.convertDetectQueryBean(Arrays.asList("com.foo.domain.query", "org.z.query"));
    String[] pkgs = detectQueryBean.getPackages();
    assertEquals(pkgs.length, 2);
    assertEquals(pkgs[0], "com/foo/domain/");
    assertEquals(pkgs[1], "org/z/");
  }
}