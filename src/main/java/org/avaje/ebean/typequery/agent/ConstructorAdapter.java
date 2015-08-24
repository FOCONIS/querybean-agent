package org.avaje.ebean.typequery.agent;

import org.avaje.ebean.typequery.agent.asm.AnnotationVisitor;
import org.avaje.ebean.typequery.agent.asm.Attribute;
import org.avaje.ebean.typequery.agent.asm.ClassVisitor;
import org.avaje.ebean.typequery.agent.asm.Handle;
import org.avaje.ebean.typequery.agent.asm.Label;
import org.avaje.ebean.typequery.agent.asm.MethodVisitor;
import org.avaje.ebean.typequery.agent.asm.Opcodes;
import org.avaje.ebean.typequery.agent.asm.Type;
import org.avaje.ebean.typequery.agent.asm.TypePath;

/**
 * Changes the existing constructor to remove all the field initialisation as these are going to be
 * initialised lazily by calls to our generated methods.
 */
public class ConstructorAdapter extends MethodVisitor implements Opcodes {

  final String className;

  final String domainClass;

  final ClassVisitor cv;

  /**
   * Construct for a query bean class given its associated entity bean domain class and a class visitor.
   */
  public ConstructorAdapter(String className, String domainClass, ClassVisitor cv) {
    super(Opcodes.ASM5, null);
    this.cv = cv;
    this.className = className;
    this.domainClass = domainClass;
  }

  @Override
  public void visitCode() {

    mv = cv.visitMethod(ACC_PUBLIC, "<init>", "(I)V", null, null);
    mv.visitCode();
    Label l0 = new Label();
    mv.visitLabel(l0);
    mv.visitLineNumber(1, l0);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitLdcInsn(Type.getType("L"+domainClass+";"));
    mv.visitMethodInsn(INVOKESPECIAL, "org/avaje/ebean/typequery/TQRootBean", "<init>", "(Ljava/lang/Class;)V", false);
    Label l1 = new Label();
    mv.visitLabel(l1);
    mv.visitLineNumber(2, l1);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitMethodInsn(INVOKEVIRTUAL, className, "setRoot", "(Ljava/lang/Object;)V", false);
    Label l2 = new Label();
    mv.visitLabel(l2);
    mv.visitLineNumber(3, l2);
    mv.visitInsn(RETURN);
    Label l3 = new Label();
    mv.visitLabel(l3);
    mv.visitLocalVariable("this", "L"+className+";", null, l0, l3, 0);
    mv.visitLocalVariable("maxDepth", "I", null, l0, l3, 1);
    mv.visitMaxs(2, 2);
    mv.visitEnd();
  }

  @Override
  public void visitParameter(String name, int access) {
    // do nothing / consume existing
  }

  @Override
  public AnnotationVisitor visitAnnotationDefault() {
    // do nothing / consume existing
    return  null;
  }

  @Override
  public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
    // do nothing / consume existing
    return null;
  }

  @Override
  public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
    // do nothing / consume existing
    return null;
  }

  @Override
  public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
    // do nothing / consume existing
    return null;
  }

  @Override
  public void visitAttribute(Attribute attr) {
    // do nothing / consume existing
  }

  @Override
  public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
    // do nothing / consume existing
  }

  @Override
  public void visitInsn(int opcode) {
    // do nothing / consume existing
  }

  @Override
  public void visitIntInsn(int opcode, int operand) {
    // do nothing / consume existing
  }

  @Override
  public void visitVarInsn(int opcode, int var) {
    // do nothing / consume existing
  }

  @Override
  public void visitTypeInsn(int opcode, String type) {
    // do nothing / consume existing
  }

  @Override
  public void visitFieldInsn(int opcode, String owner, String name, String desc) {
    // do nothing / consume existing
  }

  @Override
  public void visitMethodInsn(int opcode, String owner, String name, String desc) {
    // do nothing / consume existing
  }

  @Override
  public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
    // do nothing / consume existing
  }

  @Override
  public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
    // do nothing / consume existing
  }

  @Override
  public void visitJumpInsn(int opcode, Label label) {
    // do nothing / consume existing
  }

  @Override
  public void visitLabel(Label label) {
    // do nothing / consume existing
  }

  @Override
  public void visitLdcInsn(Object cst) {
    // do nothing / consume existing
  }

  @Override
  public void visitIincInsn(int var, int increment) {
    // do nothing / consume existing
  }

  @Override
  public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
    // do nothing / consume existing
  }

  @Override
  public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
    // do nothing / consume existing
  }

  @Override
  public void visitMultiANewArrayInsn(String desc, int dims) {
    // do nothing / consume existing
  }

  @Override
  public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
    // do nothing / consume existing
    return null;
  }

  @Override
  public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
    // do nothing / consume existing
  }

  @Override
  public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
    // do nothing / consume existing
    return null;
  }

  @Override
  public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
    // do nothing / consume existing
  }

  @Override
  public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
    // do nothing / consume existing
    return null;
  }

  @Override
  public void visitLineNumber(int line, Label start) {
    // do nothing / consume existing
  }

  @Override
  public void visitMaxs(int maxStack, int maxLocals) {
    // do nothing / consume existing
  }

  @Override
  public void visitEnd() {
    // do nothing / consume existing
  }
}
