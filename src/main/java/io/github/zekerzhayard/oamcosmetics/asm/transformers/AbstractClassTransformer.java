package io.github.zekerzhayard.oamcosmetics.asm.transformers;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

public abstract class AbstractClassTransformer {
    public abstract boolean isTargetClassName(String className);
    
    public abstract AbstractMethodTransformer[] getMethodTransformers();
    
    public void transform(ClassNode cn) {
        for (MethodNode mn : cn.methods) {
            for (AbstractMethodTransformer amt : this.getMethodTransformers()) {
                if (amt.isTargetMethod(FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(cn.name, mn.name, mn.desc), FMLDeobfuscatingRemapper.INSTANCE.mapMethodDesc(mn.desc))) {
                    System.out.println("Found the method: " + mn.name + mn.desc);
                    amt.transform(mn);
                }
            }
        }
    }
}

abstract class AbstractMethodTransformer {
    public abstract boolean isTargetMethod(String methodName, String methodDesc);
    
    public abstract AbstractInsnTransformer[] getInsnTransformers();
    
    public void transform(MethodNode mn) {
        for (AbstractInsnNode ain : mn.instructions.toArray()) {
            for (AbstractInsnTransformer ait : this.getInsnTransformers()) {
                if (ait.isTargetInsn(ain)) {
                    System.out.println("Found the node: " + ain.getOpcode() + " " + ain.getType());
                    ait.transform(mn, ain);
                }
            }
        }
    }

}

abstract class AbstractInsnTransformer {
    public abstract boolean isTargetInsn(AbstractInsnNode ain);
    
    public abstract void transform(MethodNode mn, AbstractInsnNode ain);
}
