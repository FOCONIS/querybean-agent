package org.avaje.ebean.typequery.agent;

import java.util.Arrays;

/**
 * Detects if a class is a query bean.
 * <p>
 * Used by enhancement to detect when GETFIELD access on query beans should be replaced by
 * appropriate method calls.
 * </p>
 */
class DetectQueryBean {

  private final String[] packages;

  DetectQueryBean(String[] packages) {
    this.packages = packages;
  }

  public String toString() {
    return Arrays.toString(packages);
  }

  /**
   * Return true if there are no known packages.
   */
  boolean isEmpty() {
    return packages.length == 0;
  }

  /**
   * Return the packages that entity beans are expected.
   * Query beans are expected to be in a query sub-package.
   */
  String[] getPackages() {
    return packages;
  }

  /**
   * Return true if this class is a query bean using naming conventions for query beans.
   */
  boolean isQueryBean(String owner) {

    int subPackagePos = owner.lastIndexOf("/query/");
    if (subPackagePos > -1) {
      String suffix = owner.substring(subPackagePos);
      if (isQueryBeanSuffix(suffix)) {
        String domainPackage = owner.substring(0, subPackagePos + 1);
        return isQueryBeanPackage(domainPackage);
      }
    }
    return false;
  }

  /**
   * Check that the class is in an expected package (sub package of a package containing entity beans).
   */
  private boolean isQueryBeanPackage(String domainPackage) {
    for (int i = 0; i < packages.length; i++) {
      if (domainPackage.startsWith(packages[i])) {
        return true;
      }
    }
    return false;
  }

  /**
   * Check that the class follows query bean naming convention.
   */
  private boolean isQueryBeanSuffix(String suffix) {
    return (suffix.startsWith("/query/Q") || suffix.startsWith("/query/assoc/Q"));
  } 
}
