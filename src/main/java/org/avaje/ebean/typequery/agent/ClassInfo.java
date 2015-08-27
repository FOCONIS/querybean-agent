package org.avaje.ebean.typequery.agent;

import org.avaje.ebean.typequery.agent.asm.ClassVisitor;
import org.avaje.ebean.typequery.agent.asm.FieldVisitor;
import org.avaje.ebean.typequery.agent.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds meta information for a class.
 */
public class ClassInfo implements Constants {


  private final EnhanceContext enhanceContext;

  private final String className;

  private boolean addedMarkerAnnotation;

  private boolean typeQueryBean;

  private boolean typeQueryUser;

  private boolean alreadyEnhanced;

  private List<FieldInfo> fields;

  private boolean hasBasicConstructor;

  private boolean hasMainConstructor;

  public ClassInfo(EnhanceContext enhanceContext, String className) {
    this.enhanceContext = enhanceContext;
    this.className = className;
  }

  /**
   * Return the className.
   */
  public String getClassName() {
    return className;
  }

  /**
   * Return true if the bean is already enhanced.
   */
  public boolean isAlreadyEnhanced() {
    return alreadyEnhanced;
  }

  public boolean addMarkerAnnotation() {
    if (addedMarkerAnnotation) {
      return false;
    }
    addedMarkerAnnotation = true;
    return true;
  }

  /**
   * Return true if the bean is a type query bean.
   */
  public boolean isTypeQueryBean() {
    return typeQueryBean;
  }

  /**
   * Return true if the class is explicitly annotated with TypeQueryUser annotation.
   */
  public boolean isTypeQueryUser() {
    return typeQueryUser;
  }

  /**
   * Check for the type query bean and type query user annotations.
   */
  public void checkTypeQueryAnnotation(String desc) {
    if (isTypeQueryBeanAnnotation(desc)) {
      typeQueryBean = true;
    } else if (isAlreadyEnhancedAnnotation(desc)) {
      alreadyEnhanced = true;
    }
  }

  /**
   * Add the type query bean field. We will create a 'property access' method for each field.
   */
  public void addField(int access, String name, String desc, String signature) {

    if (((access & Opcodes.ACC_PUBLIC) != 0)) {
      if (fields == null) {
        fields = new ArrayList<FieldInfo>();
      }
      fields.add(new FieldInfo(this, name, desc, signature));
    }
  }

  /**
   * Return true if the annotation is the TypeQueryBean annotation.
   */
  private boolean isAlreadyEnhancedAnnotation(String desc) {
    return ANNOTATION_ALREADY_ENHANCED_MARKER.equals(desc);
  }

  /**
   * Return true if the annotation is the TypeQueryBean annotation.
   */
  private boolean isTypeQueryBeanAnnotation(String desc) {
    return ANNOTATION_TYPE_QUERY_BEAN.equals(desc);
  }

  /**
   * Return the fields for a type query bean.
   */
  public List<FieldInfo> getFields() {
    return fields;
  }

  /**
   * Note that a GETFIELD call has been replaced to method call.
   */
  public void addGetFieldIntercept(String owner, String name) {

    if (isLog(4)) {
      log("change getfield " + owner + " name:" + name);
    }
    typeQueryUser = true;
  }

  public boolean isLog(int level) {
    return enhanceContext.isLog(level);
  }

  public void log(String msg) {
    enhanceContext.log(className, msg);
  }

  /**
   * There is a basic constructor on the assoc bean which is being overwritten (so don't need to add later).
   */
  public void setHasBasicConstructor() {
    if (isLog(3)) {
      log("replace assoc bean basic constructor");
    }
    hasBasicConstructor = true;
  }

  /**
   * There is a main constructor on the assoc bean which is being overwritten (so don't need to add later).
   */
  public void setHasMainConstructor() {
    if (isLog(3)) {
      log("replace assoc bean main constructor");
    }
    hasMainConstructor = true;
  }

  /**
   * Add fields and constructors to assoc type query beans as necessary.
   */
  public void addAssocBeanExtras(ClassVisitor cv) {

    if (isLog(3)) {
      String msg = "... add fields";
      if (!hasBasicConstructor) {
        msg += ", basic constructor";
      }
      if (!hasMainConstructor) {
        msg += ", main constructor";
      }
      log(msg);
    }

    if (!hasBasicConstructor) {
      // add the assoc bean basic constructor
      new TypeQueryAssocBasicConstructor(this, cv, ASSOC_BEAN_BASIC_CONSTRUCTOR_DESC, ASSOC_BEAN_BASIC_SIG).visitCode();
    }
    if (!hasMainConstructor) {
      // add the assoc bean main constructor
      new TypeQueryAssocMainConstructor(this, cv, ASSOC_BEAN_MAIN_CONSTRUCTOR_DESC, ASSOC_BEAN_MAIN_SIG).visitCode();
    }

  }

}
