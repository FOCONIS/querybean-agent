package org.avaje.ebean.typequery.agent.asm;

/**
 * ClassWriter that uses a specific ClassLoader.
 * <p>
 * The ClassLoader is used specifically to assist the inherited getCommonSuperClass() method.
 */
public class CLAwareClassWriter extends ClassWriter {

  private final ClassLoader classLoader;

  /**
   * Construct with flags and a ClassLoader which is used for supporting the getCommonSuperClass() method.
   */
  public CLAwareClassWriter(int flags, ClassLoader classLoader) {
    super(flags);
    this.classLoader = classLoader;
  }

  @Override
  protected String getCommonSuperClass(String type1, String type2) {
    try {
      return getCommonSuperClassBase(type1, type2);
    } catch (Throwable e) {
      throw new RuntimeException("Error getting common superClass for " + type1 + " and " + type2 + " - " + e.getMessage(), e);
    }
  }

  /**
   * Return the class using the supplied ClassLoader.
   */
  @Override
  protected Class<?> classForName(String name) throws ClassNotFoundException {
    return Class.forName(name, false, classLoader);
  }

}
