package io.ebean.typequery.agent;

import java.util.Arrays;

/**
 * Detects if a class is a descriptor bean.
 * <p>
 * Used by enhancement to detect when GETFIELD access on query beans should be replaced by
 * appropriate method calls.
 * </p>
 */
class DetectDescriptorBean {

	private final String[] packages;

	DetectDescriptorBean(String[] packages) {
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
	 * Descriptor beans are expected to be in a descriptor sub-package.
	 */
	String[] getPackages() {
		return packages;
	}

	/**
	 * Check that the class is in an expected package (sub package of a package containing entity beans).
	 */
	private boolean isDescriptorBeanPackage(String domainPackage) {
		for (int i = 0; i < packages.length; i++) {
			if (domainPackage.startsWith(packages[i])) {
				return true;
			}
		}
		return false;
	}


	/**
	 * Return true if this class is a descriptor bean using naming conventions for descriptor beans.
	 */
	boolean isDescriptorBean(String owner) {
		
		int subPackagePos = owner.lastIndexOf("/descriptor/");
		if (subPackagePos > -1) {
			String suffix = owner.substring(subPackagePos);
			if (isDescriptorBeanSuffix(suffix)) {
				String domainPackage = owner.substring(0, subPackagePos + 1);
				return isDescriptorBeanPackage(domainPackage);
			}
		}
		return false;
	}

	/**
	 * Check that the class follows descriptor bean naming convention.
	 */
	private boolean isDescriptorBeanSuffix(String suffix) {
		return (suffix.startsWith("/descriptor/D") || suffix.startsWith("/descriptor/assoc/D"));
	}

}
