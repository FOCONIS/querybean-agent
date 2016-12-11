package io.ebean.typequery.agent;


import io.ebean.typequery.agent.offline.MainTransform;

public abstract class BaseTest {

  static String[] transformArgs = {"target/test-classes", "prototype/**","packages=org.example;debug=9;"};

  static {
    MainTransform.main(transformArgs);
  }

}
