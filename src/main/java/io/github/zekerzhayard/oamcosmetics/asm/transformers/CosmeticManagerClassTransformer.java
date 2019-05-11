package io.github.zekerzhayard.oamcosmetics.asm.transformers;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class CosmeticManagerClassTransformer extends AbstractClassTransformer {
    @Override()
    public boolean isTargetClassName(String className) {
        return className.equals("com.spiderfrog.oldanimations.cosmetic.CosmeticManager");
    }

    @Override()
    public AbstractMethodTransformer[] getMethodTransformers() {
        return new AbstractMethodTransformer[] { new AbstractMethodTransformer() {
            @Override()
            public boolean isTargetMethod(String methodName, String methodDesc) {
                return methodName.equals("getCosmetics") && methodDesc.equals("(Lnet/minecraft/entity/Entity;)Ljava/util/ArrayList;");
            }

            @Override()
            public AbstractInsnTransformer[] getInsnTransformers() {
                return new AbstractInsnTransformer[] { new AbstractInsnTransformer() {
                    @Override()
                    public boolean isTargetInsn(AbstractInsnNode ain) {
                        return ain instanceof MethodInsnNode && ain.getPrevious() instanceof VarInsnNode && ain.getPrevious().getPrevious() instanceof FieldInsnNode;
                    }

                    @Override()
                    public void transform(MethodNode mn, AbstractInsnNode ain) {
                        mn.instructions.insertBefore(ain, new MethodInsnNode(Opcodes.INVOKESTATIC, "OldAnimationsModHook", "getSelfCosmetics", "(Ljava/util/HashMap;Lnet/minecraft/entity/Entity;)Ljava/util/ArrayList;", false));
                        mn.instructions.insertBefore(ain, new InsnNode(Opcodes.ARETURN));
                    }
                } };
            }
        } };
    }
}
