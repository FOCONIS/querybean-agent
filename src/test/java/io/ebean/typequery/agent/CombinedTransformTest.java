package io.ebean.typequery.agent;

import org.junit.Test;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import static org.junit.Assert.*;

public class CombinedTransformTest {

  private final byte[] bytes0 = new byte[10];
  private final byte[] bytes1 = new byte[10];

  @Test
  public void transform_when_both() throws Exception {

    CombinedTransform combined = new CombinedTransform(new En0(), new En1());
    CombinedTransform.Response response = combined.transform(null, null, null, null, null);

    assertTrue(response.isEnhanced());
    assertTrue(response.isFirst());
    assertTrue(response.isSecond());
    assertSame(response.getClassBytes(), bytes1);
  }

  @Test
  public void transform_when_first() throws Exception {

    CombinedTransform combined = new CombinedTransform(new En0(), new NoEnhancement());
    CombinedTransform.Response response = combined.transform(null, null, null, null, null);

    assertTrue(response.isEnhanced());
    assertTrue(response.isFirst());
    assertFalse(response.isSecond());
    assertSame(response.getClassBytes(), bytes0);
  }

  @Test
  public void transform_when_second() throws Exception {

    CombinedTransform combined = new CombinedTransform(new NoEnhancement(), new En1());
    CombinedTransform.Response response = combined.transform(null, null, null, null, null);

    assertTrue(response.isEnhanced());
    assertFalse(response.isFirst());
    assertTrue(response.isSecond());
    assertSame(response.getClassBytes(), bytes1);
  }

  @Test
  public void transform_when_neither() throws Exception {

    CombinedTransform combined = new CombinedTransform(new NoEnhancement(), new NoEnhancement());
    CombinedTransform.Response response = combined.transform(null, null, null, null, null);

    assertFalse(response.isEnhanced());
    assertFalse(response.isFirst());
    assertFalse(response.isSecond());
    assertNull(response.getClassBytes());
  }

  private class NoEnhancement implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
      return null;
    }
  }

  private class En0 implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
      return bytes0;
    }
  }

  private class En1 implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
      return bytes1;
    }
  }

}