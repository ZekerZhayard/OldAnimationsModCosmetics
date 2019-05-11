package io.github.zekerzhayard.oamcosmetics.asm.transformers;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.google.common.collect.Lists;

public class CosmeticsClassTransformer extends AbstractClassTransformer {
    public static ConcurrentHashMap<String, String> cosmeticNames = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, List<String>> datas = new ConcurrentHashMap<>();
    private List<String> dataList = Lists.newArrayList();
    
    static {
        CosmeticsClassTransformer.cosmeticNames.put("SHARINGANEYES", "SharinganEyes");
        CosmeticsClassTransformer.datas.put("SHARINGANEYES", Lists.newArrayList("off", "default", "sasuke", "itachi", "madara", "obito"));
    }
    
    @Override()
    public boolean isTargetClassName(String className) {
        return className.contains("com.spiderfrog.oldanimations.cosmetic.cosmetics.Cosmetic");
    }

    @Override()
    public AbstractMethodTransformer[] getMethodTransformers() {
        return new AbstractMethodTransformer[] { new AbstractMethodTransformer() {
            @Override()
            public boolean isTargetMethod(String methodName, String methodDesc) {
                return methodName.equals("render") && methodDesc.equals("(F)V");
            }

            @Override()
            public AbstractInsnTransformer[] getInsnTransformers() {
                return new AbstractInsnTransformer[] { new AbstractInsnTransformer() {
                    @Override()
                    public boolean isTargetInsn(AbstractInsnNode ain) {
                        return ain instanceof LdcInsnNode && ain.getPrevious() instanceof FieldInsnNode && ain.getNext() instanceof MethodInsnNode && ((MethodInsnNode) ain.getNext()).name.equals("equalsIgnoreCase");
                    }

                    @Override()
                    public void transform(MethodNode mn, AbstractInsnNode ain) {
                        String data = (String) ((LdcInsnNode) ain).cst;
                        if (!CosmeticsClassTransformer.this.dataList.contains(data)) {
                            System.out.println("Found the data: " + data);
                            CosmeticsClassTransformer.this.dataList.add(data);
                        }
                    }
                } };
            }
        } };
    }
    
    @Override()
    public void transform(ClassNode cn) {
        super.transform(cn);
        String cosmeticName = cn.name.replace("com/spiderfrog/oldanimations/cosmetic/cosmetics/Cosmetic", "");
        CosmeticsClassTransformer.cosmeticNames.put(cosmeticName.toUpperCase(), cosmeticName);
        this.dataList.add(0, "off");
        this.dataList.add(1, "default");
        CosmeticsClassTransformer.datas.put(cosmeticName.toUpperCase(), Lists.newArrayList(this.dataList));
        this.dataList.clear();
    }
}
