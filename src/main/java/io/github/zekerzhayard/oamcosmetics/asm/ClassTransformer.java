package io.github.zekerzhayard.oamcosmetics.asm;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.google.common.collect.Lists;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

public class ClassTransformer implements IClassTransformer {
    public static ConcurrentHashMap<String, String> cosmeticNames = new ConcurrentHashMap<String, String>() {
        {
            this.put("SHARINGANEYES", "SharinganEyes");
        }
    };
    public static ConcurrentHashMap<String, ArrayList<String>> datas = new ConcurrentHashMap<String, ArrayList<String>>() {
        {
            this.put("SHARINGANEYES", Lists.newArrayList("off", "default", "sasuke", "itachi", "madara", "obito"));
        }
    };
    
    @Override()
    public byte[] transform(String className, String transformedName, byte[] basicClass) {
        if (className.contains("com.spiderfrog.oldanimations.cosmetic.cosmetics.Cosmetic")) {
            System.out.println("Found the class: " + className);
            
            // Get cosmetics names.
            String cosmeticName = className.replace("com.spiderfrog.oldanimations.cosmetic.cosmetics.Cosmetic", "");
            ClassTransformer.cosmeticNames.put(cosmeticName.toUpperCase(), cosmeticName);
            
            // Get cosmetics data.
            ArrayList<String> dataList = Lists.newArrayList("off", "default");
            ClassNode cn = new ClassNode();
            new ClassReader(basicClass).accept(cn, ClassReader.EXPAND_FRAMES);
            for (MethodNode mn : cn.methods) {
                if (mn.name.equals("render") && mn.desc.equals("(F)V")) {
                    System.out.println("Found the method: " + mn.name + mn.desc);
                    AbstractInsnNode[] ains = mn.instructions.toArray();
                    for (int i = 0; i < ains.length; i++) {
                        if (ains[i] instanceof LdcInsnNode && ains[i - 1] instanceof FieldInsnNode && ains[i + 1] instanceof MethodInsnNode && ((MethodInsnNode) ains[i + 1]).name.equals("equalsIgnoreCase")) {
                            String data = (String) ((LdcInsnNode) ains[i]).cst;
                            System.out.println("Found the data: " + data);
                            dataList.add(data);
                        }
                    }
                }
            }
            ClassTransformer.datas.put(cosmeticName.toUpperCase(), dataList);
        }
        
        if (className.equals("com.spiderfrog.oldanimations.cosmetic.CosmeticManager")) {
            System.out.println("Found the class: " + className);
            ClassNode cn = new ClassNode();
            new ClassReader(basicClass).accept(cn, ClassReader.EXPAND_FRAMES);
            for (MethodNode mn : cn.methods) {
                if (mn.name.equals("getCosmetics") && FMLDeobfuscatingRemapper.INSTANCE.mapMethodDesc(mn.desc).equals("(Lnet/minecraft/entity/Entity;)Ljava/util/ArrayList;")) {
                    System.out.println("Found the method: " + mn.name + mn.desc);
                    AbstractInsnNode[] ains = mn.instructions.toArray();
                    for (int i = 0; i < ains.length; i++) {
                        if (ains[i] instanceof MethodInsnNode && ains[i - 1] instanceof VarInsnNode && ains[i - 2] instanceof FieldInsnNode) {
                            System.out.println("Found the node: " + ains[i].getOpcode() + " " + ains[i].getType());
                            mn.instructions.insertBefore(ains[i], new MethodInsnNode(Opcodes.INVOKESTATIC, "io/github/zekerzhayard/oamcosmetics/asm/ByteCodeHook", "getSelfCosmetics", "(Ljava/util/HashMap;Lnet/minecraft/entity/Entity;)Ljava/util/ArrayList;", false));
                            mn.instructions.insertBefore(ains[i], new InsnNode(Opcodes.ARETURN));
                        }
                    }
                }
            }
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            cn.accept(cw);
            return cw.toByteArray();
        }
        
        return basicClass;
    }
}
