package io.github.zekerzhayard.oamcosmetics.asm.transformers;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class OtherAnimationsTransformer extends AbstractClassTransformer {
    @Override()
    public boolean isTargetClassName(String className) {
        return className.equals("com.spiderfrog.oldanimations.gui.OtherAnimations");
    }

    @Override()
    public AbstractMethodTransformer[] getMethodTransformers() {
        return new AbstractMethodTransformer[] { new AbstractMethodTransformer() {
            @Override()
            public boolean isTargetMethod(String methodName, String methodDesc) {
                return methodName.equals("func_146284_a") && methodDesc.equals("(Lnet/minecraft/client/gui/GuiButton;)V");
            }

            @Override()
            public AbstractInsnTransformer[] getInsnTransformers() {
                return new AbstractInsnTransformer[] { new AbstractInsnTransformer() {
                    @Override()
                    public boolean isTargetInsn(AbstractInsnNode ain) {
                        return ain instanceof FieldInsnNode && ((FieldInsnNode) ain).getOpcode() == Opcodes.PUTSTATIC && ((FieldInsnNode) ain).name.equals("cosmetics");
                    }

                    @Override()
                    public void transform(MethodNode mn, AbstractInsnNode ain) {
                        mn.instructions.insert(ain, new MethodInsnNode(Opcodes.INVOKESTATIC, "OldAnimationsModHook", "setShowOthersCosmetics", "()V", false));
                    }
                } };
            }
        }, new AbstractMethodTransformer() {
            private boolean isCosmetic = false;
            
            @Override()
            public boolean isTargetMethod(String methodName, String methodDesc) {
                return methodName.equals("func_73866_w_") && methodDesc.equals("()V");
            }

            @Override()
            public AbstractInsnTransformer[] getInsnTransformers() {
                return new AbstractInsnTransformer[] { new AbstractInsnTransformer() {
                    @Override()
                    public boolean isTargetInsn(AbstractInsnNode ain) {
                        return ain instanceof FieldInsnNode && ((FieldInsnNode) ain).name.equals("cosmetics");
                    }

                    @Override()
                    public void transform(MethodNode mn, AbstractInsnNode ain) {
                        setCosmetic(true);
                    }
                }, new AbstractInsnTransformer() {
                    @Override()
                    public boolean isTargetInsn(AbstractInsnNode ain) {
                        return ain instanceof MethodInsnNode && ((MethodInsnNode) ain).owner.equals("net/minecraft/client/gui/GuiButton") && isCosmetic();
                    }

                    @Override()
                    public void transform(MethodNode mn, AbstractInsnNode ain) {
                        setCosmetic(false);
                        mn.instructions.insertBefore(ain, new MethodInsnNode(Opcodes.INVOKESTATIC, "OldAnimationsModHook", "getCosmeticsSettingString", "(Ljava/lang/String;)Ljava/lang/String;", false));
                    }
                } };
            }

            public boolean isCosmetic() {
                return this.isCosmetic;
            }

            public void setCosmetic(boolean isCosmetic) {
                this.isCosmetic = isCosmetic;
            }
        } };
    }
}
