package org.avaje.ebean.typequery.agent;

import org.avaje.ebean.typequery.agent.asm.ClassVisitor;
import org.avaje.ebean.typequery.agent.asm.Label;
import org.avaje.ebean.typequery.agent.asm.Opcodes;
import org.avaje.ebean.typequery.agent.asm.Type;

/**
 * Changes the existing constructor to remove all the field initialisation as these are going to be
 * initialised lazily by calls to our generated methods.
 */
public class TypeQueryConstructorAdapter extends BaseConstructorAdapter implements Opcodes, Constants {

  private static final String TQROOT_BEAN = "org/avaje/ebean/typequery/TQRootBean";

  private final ClassInfo classInfo;

  private final String domainClass;

  private final ClassVisitor cv;

  private final String desc;

  private final String signature;

  /**
   * Construct for a query bean class given its associated entity bean domain class and a class visitor.
   */
  public TypeQueryConstructorAdapter(ClassInfo classInfo, String domainClass, ClassVisitor cv, String desc, String signature) {
    super();
    this.cv = cv;
    this.classInfo = classInfo;
    this.domainClass = domainClass;
    this.desc = desc;
    this.signature = signature;
  }

  @Override
  public void visitCode() {

    boolean withEbeanServer = WITH_EBEANSERVER_ARGUMENT.equals(desc);

    mv = cv.visitMethod(ACC_PUBLIC, "<init>", desc, signature, null);
    mv.visitCode();
    Label l0 = new Label();
    mv.visitLabel(l0);
    mv.visitLineNumber(1, l0);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitLdcInsn(Type.getType("L" + domainClass + ";"));
    if (withEbeanServer) {
      mv.visitVarInsn(ALOAD, 1);
      mv.visitMethodInsn(INVOKESPECIAL, TQROOT_BEAN, "<init>", "(Ljava/lang/Class;Lcom/avaje/ebean/EbeanServer;)V", false);
    } else {
      mv.visitMethodInsn(INVOKESPECIAL, TQROOT_BEAN, "<init>", "(Ljava/lang/Class;)V", false);
    }

    Label l1 = new Label();
    mv.visitLabel(l1);
    mv.visitLineNumber(2, l1);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitMethodInsn(INVOKEVIRTUAL, classInfo.getClassName(), "setRoot", "(Ljava/lang/Object;)V", false);
    Label l2 = new Label();
    mv.visitLabel(l2);
    mv.visitLineNumber(3, l2);
    mv.visitInsn(RETURN);
    Label l3 = new Label();
    mv.visitLabel(l3);
    mv.visitLocalVariable("this", "L" + classInfo.getClassName() + ";", null, l0, l3, 0);
    if (withEbeanServer) {
      mv.visitLocalVariable("server", "Lcom/avaje/ebean/EbeanServer;", null, l0, l3, 1);
      mv.visitMaxs(3, 2);
    } else {
      mv.visitMaxs(2, 1);
    }
    mv.visitEnd();
  }

}
