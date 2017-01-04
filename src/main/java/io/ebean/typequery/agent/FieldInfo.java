package io.ebean.typequery.agent;

import io.ebean.typequery.agent.asm.ClassVisitor;
import io.ebean.typequery.agent.asm.Label;
import io.ebean.typequery.agent.asm.MethodVisitor;
import io.ebean.typequery.agent.asm.Opcodes;
import io.ebean.typequery.agent.asm.Type;

/**
 * Field information.
 */
public class FieldInfo implements Opcodes, Constants {

  private final ClassInfo classInfo;
  private final String name;
  private final String desc;
  private final String internalName;
  private final String signature;

  public FieldInfo(ClassInfo classInfo, String name, String desc, String signature) {
    this.classInfo = classInfo;
    this.name = name;
    this.desc = desc;
    this.internalName = Type.getType(desc).getInternalName();
    this.signature = signature;
  }

  public String toString() {
    return "name:" + name + " desc:" + desc + " sig:" + signature;
  }

  /**
   * Add the 'property access method' that callers should use (instead of get field).
   */
  public void writeMethod(ClassVisitor cw, boolean typeQueryRootBean) {

    // simple why to determine the property is an associated bean type
    boolean assocProperty = desc.contains("/QAssoc");

    if (classInfo.isLog(4)) {
      classInfo.log(" ... add method _" + name + " assocProperty:" + assocProperty + " rootBean:" + typeQueryRootBean);
    }

    MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "_"+name, "()"+desc, "()"+signature, null);
    mv.visitCode();
    Label l0 = new Label();
    mv.visitLabel(l0);
    mv.visitLineNumber(1, l0);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitFieldInsn(GETFIELD, classInfo.getClassName(), name, desc);
    Label l1 = new Label();
    mv.visitJumpInsn(IFNONNULL, l1);
    Label l2 = new Label();
    mv.visitLabel(l2);
    mv.visitLineNumber(2, l2);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitTypeInsn(NEW, internalName);
    mv.visitInsn(DUP);
    mv.visitLdcInsn(name);
    mv.visitVarInsn(ALOAD, 0);

    if (assocProperty) {
      if (typeQueryRootBean) {
        mv.visitInsn(ICONST_1);
        mv.visitMethodInsn(INVOKESPECIAL, internalName, "<init>", "(Ljava/lang/String;Ljava/lang/Object;I)V", false);
      } else {
        mv.visitFieldInsn(GETFIELD, classInfo.getClassName(), FIELD_ROOT, "Ljava/lang/Object;");
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, classInfo.getClassName(), FIELD_PATH, "Ljava/lang/String;");
        mv.visitInsn(ICONST_1);
        mv.visitMethodInsn(INVOKESPECIAL, internalName, "<init>", "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;I)V", false);
      }

    } else {
      if (typeQueryRootBean) {
        mv.visitMethodInsn(INVOKESPECIAL, internalName, "<init>", "(Ljava/lang/String;Ljava/lang/Object;)V", false);
      } else {
        mv.visitFieldInsn(GETFIELD, classInfo.getClassName(), FIELD_ROOT, "Ljava/lang/Object;");
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, classInfo.getClassName(), FIELD_PATH, "Ljava/lang/String;");
        mv.visitMethodInsn(INVOKESPECIAL, internalName, "<init>", "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;)V", false);
      }
    }

    mv.visitFieldInsn(PUTFIELD, classInfo.getClassName(), name, desc);
    mv.visitLabel(l1);
    mv.visitLineNumber(3, l1);
    mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitFieldInsn(GETFIELD, classInfo.getClassName(), name, desc);
    mv.visitInsn(ARETURN);
    Label l3 = new Label();
    mv.visitLabel(l3);
    mv.visitLocalVariable("this", "L" + classInfo.getClassName() + ";", null, l0, l3, 0);
    if (assocProperty) {
      if (typeQueryRootBean) {
        mv.visitMaxs(6, 1);
      } else {
        mv.visitMaxs(7, 1);
      }
    } else {
      if (typeQueryRootBean) {
        mv.visitMaxs(5, 1);
      } else {
        mv.visitMaxs(6, 1);
      }
    }
    mv.visitEnd();
  }

  /**
   * Initialise the field (used by 'Alias' constructor).
   */
  public void writeFieldInit(MethodVisitor mv) {
    Label l10 = new Label();
    mv.visitLabel(l10);
    mv.visitLineNumber(3, l10);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitTypeInsn(NEW, internalName);
    mv.visitInsn(DUP);
    mv.visitLdcInsn(name);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitMethodInsn(INVOKESPECIAL, internalName, "<init>", "(Ljava/lang/String;Ljava/lang/Object;)V", false);
    mv.visitFieldInsn(PUTFIELD, classInfo.getClassName(), name, desc);
  }
}
