package org.avaje.ebean.typequery.agent;

import org.avaje.ebean.typequery.agent.asm.ClassVisitor;
import org.avaje.ebean.typequery.agent.asm.Opcodes;

import java.util.List;

/**
 * Add generated 'property access' methods for the fields.
 */
public class AddTypeQueryBeanMethods implements Opcodes {


  /**
   * Add the generated 'property access' methods.
   */
  public static void add(ClassVisitor  cw, ClassInfo classInfo) {

    List<FieldInfo> fields = classInfo.getFields();

    for (FieldInfo field : fields) {
      field.writeMethod(cw);
    }
  }

}
