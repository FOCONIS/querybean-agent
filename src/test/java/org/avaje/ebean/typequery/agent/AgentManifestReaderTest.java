package org.avaje.ebean.typequery.agent;

import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class AgentManifestReaderTest {

  @Test
  public void testRead() throws Exception {

    Set<String> packages = AgentManifestReader.readQueryMf(AgentManifestReaderTest.class.getClassLoader(), null);

    assertThat(packages).contains("prototype.domain","one.foo","two.bar","three.baz");
  }

  @Test
  public void testAddRaw() {

    AgentManifestReader manifestReader = new AgentManifestReader();
    manifestReader.addRaw("packages: prototype.domain one.foo");
    Set<String> packages = manifestReader.getPackages();

    assertThat(packages).contains("prototype.domain","one.foo");
  }

  @Test
  public void testCombine() {

    AgentManifestReader manifestReader = new AgentManifestReader();
    manifestReader.addRaw("packages: other.one");
    Set<String> packages = manifestReader.getPackages();
    assertThat(packages).contains("other.one");

    Set<String> allPackages = AgentManifestReader.readQueryMf(AgentManifestReaderTest.class.getClassLoader(), packages);

    assertThat(allPackages).contains("prototype.domain","one.foo","two.bar","three.baz","other.one");
  }
}