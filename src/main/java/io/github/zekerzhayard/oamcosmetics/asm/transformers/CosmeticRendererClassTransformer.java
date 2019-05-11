package io.github.zekerzhayard.oamcosmetics.asm.transformers;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

public class CosmeticRendererClassTransformer extends AbstractClassTransformer {

    @Override
    public boolean isTargetClassName(String className) {
        return className.equals("com.spiderfrog.oldanimations.cosmetic.CosmeticRenderer");
    }

    @Override
    public AbstractMethodTransformer[] getMethodTransformers() {
        return new AbstractMethodTransformer[] { new AbstractMethodTransformer() {
            @Override()
            public boolean isTargetMethod(String methodName, String methodDesc) {
                return methodName.equals("func_78088_a") && methodDesc.equals("(Lnet/minecraft/entity/Entity;FFFFFF)V");
            }

            @Override()
            public AbstractInsnTransformer[] getInsnTransformers() {
                return new AbstractInsnTransformer[] { new AbstractInsnTransformer() {
                    @Override()
                    public boolean isTargetInsn(AbstractInsnNode ain) {
                        return ain instanceof TypeInsnNode && ain.getOpcode() == Opcodes.CHECKCAST && ((TypeInsnNode) ain).desc.equals("com/spiderfrog/oldanimations/cosmetic/Cosmetic");
                    }

                    @Override()
                    public void transform(MethodNode mn, AbstractInsnNode ain) {
                        mn.instructions.insert(ain, new MethodInsnNode(Opcodes.INVOKESTATIC, "OldAnimationsModHook", "resetColor", "(Lcom/spiderfrog/oldanimations/cosmetic/Cosmetic;)Lcom/spiderfrog/oldanimations/cosmetic/Cosmetic;", false));
                    }
                } };
            }
        } };
    }

}
