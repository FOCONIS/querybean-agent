package io.ebean.typequery.agent;

import io.ebean.typequery.agent.asm.MethodVisitor;
import io.ebean.typequery.agent.asm.Opcodes;

/**
 * Adapter that changes GETFIELD calls to type query beans to instead use the generated
 * 'property access' methods.
 */
public class MethodAdapter extends MethodVisitor implements Opcodes {

	private final EnhanceContext enhanceContext;

	private final ClassInfo classInfo;

	public MethodAdapter(MethodVisitor mv, EnhanceContext enhanceContext, ClassInfo classInfo) {
		super(ASM5, mv);
		this.enhanceContext = enhanceContext;
		this.classInfo = classInfo;
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		if (opcode == GETFIELD && enhanceContext.isQueryBean(owner)) {
			enhanceContext.log(1, "query bean enhancing: " + owner + " " + classInfo.getClassName() + " ", desc);
			
			classInfo.addGetFieldIntercept(owner, name);
			mv.visitMethodInsn(INVOKEVIRTUAL, owner, "_" + name, "()" + desc, false);
			
		} else if (opcode == GETFIELD && enhanceContext.isDescriptorBean(owner) && !enhanceContext.isDescriptorBean(classInfo.getClassName())) {
			enhanceContext.log(1, "desc enhancing: " + owner + " " + classInfo.getClassName() + " ", desc);
			
			classInfo.addGetFieldIntercept(owner, name);
			mv.visitMethodInsn(INVOKEVIRTUAL, owner, "_" + name, "()" + desc, false);
			
		} else {
			super.visitFieldInsn(opcode, owner, name, desc);
		}
	}

}
