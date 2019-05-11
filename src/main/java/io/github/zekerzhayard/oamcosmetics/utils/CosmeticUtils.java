package io.github.zekerzhayard.oamcosmetics.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.spiderfrog.oldanimations.cosmetic.Cosmetic;
import com.spiderfrog.oldanimations.cosmetic.EnumCosmetic;

import io.github.zekerzhayard.oamcosmetics.asm.transformers.CosmeticsClassTransformer;
import io.github.zekerzhayard.oamcosmetics.config.ConfigLoaderOAM;

public class CosmeticUtils {
    public static String upperNameToCommonName(String upperName) {
        return CosmeticsClassTransformer.cosmeticNames.get(upperName);
    }
    
    public static String commonNameToUpperName(String commonName) {
        return commonName.toUpperCase();
    }
    
    public static String upperNameToData(String upperName) {
        for (Cosmetic cosmetic : ConfigLoaderOAM.cosmetics) {
            if (cosmetic.getType().name().equals(upperName)) {
                return cosmetic.data;
            }
        }
        return "off";
    }
    
    public static List<String> upperNameToDataList(String upperName) {
        return CosmeticsClassTransformer.datas.get(upperName);
    }
    
    public static int upperNameToEnumCosmeitcOrdinal(String upperName) {
        return Arrays.binarySearch(EnumCosmetic.values(), EnumCosmetic.valueOf(upperName));
    }
    
    public static EnumCosmetic cosmeticToEnumCosmetic(Cosmetic cosmetic) {
        return cosmetic.getType();
    }
    
    public static Cosmetic enumCosmeticToCosmetic(EnumCosmetic enumCosmetic, String data, String extra) {
        for (Cosmetic cosmetic : ConfigLoaderOAM.cosmetics) {
            if (cosmetic.getType().name().equals(enumCosmetic.name())) {
                CosmeticUtils.cloneCosmetic(cosmetic, new Cosmetic(enumCosmetic.ordinal(), data, extra));
                return cosmetic;
            }
        }
        return new Cosmetic(enumCosmetic.ordinal(), data, extra);
    }
    
    public static String cosmeticRGBStr(Cosmetic cosmetic) {
        return String.valueOf((int) cosmetic.r) + "," + String.valueOf((int) cosmetic.g) + "," + String.valueOf((int) cosmetic.b);
    }
    
    public static void cloneCosmetic(Cosmetic cosmeticIn, Cosmetic cosmeticCloned) {
        for (Field field : Cosmetic.class.getFields()) {
            field.setAccessible(true);
            try {
                if (!"rgb".contains(field.getName()) || ("rgb".contains(field.getName()) && field.getDouble(cosmeticCloned) != 0.0D)) {
                    field.set(cosmeticIn, field.get(cosmeticCloned));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void setCosmeticData(Cosmetic cosmetic, String data) {
        String extra = null;
        try {
            extra = (String) FieldUtils.readDeclaredField(cosmetic, "extra", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CosmeticUtils.cloneCosmetic(cosmetic, new Cosmetic(cosmetic.getType().ordinal(), data, extra));
    }
}
