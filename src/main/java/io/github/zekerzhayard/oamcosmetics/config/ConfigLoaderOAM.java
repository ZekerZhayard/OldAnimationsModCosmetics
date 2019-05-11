package io.github.zekerzhayard.oamcosmetics.config;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.spiderfrog.oldanimations.cosmetic.Cosmetic;
import com.spiderfrog.oldanimations.cosmetic.EnumCosmetic;
import com.spiderfrog.oldanimations.utils.JsonConfig;

import io.github.zekerzhayard.oamcosmetics.OAMCosmetics;
import io.github.zekerzhayard.oamcosmetics.utils.CosmeticUtils;
import net.minecraftforge.common.config.Configuration;

public class ConfigLoaderOAM {
    public static Configuration config;
    public static CopyOnWriteArrayList<Cosmetic> cosmetics = new CopyOnWriteArrayList<Cosmetic>();
    public static boolean showOthersCosmetics = true;
    
    public static void init() {
        ConfigLoaderOAM.config = new Configuration(new File(new JsonConfig().path, OAMCosmetics.NAME + ".cfg"));
        ConfigLoaderOAM.config.load();
        for (String cosmeticNameAndData : ConfigLoaderOAM.config.get(OAMCosmetics.MODID, "cosmetics", new String[0]).getStringList()) {
            try {
                String cosmeticName = cosmeticNameAndData.split("#")[0];
                String data = cosmeticNameAndData.split("#")[1];
                ConfigLoaderOAM.cosmetics.add(new Cosmetic(CosmeticUtils.upperNameToEnumCosmeitcOrdinal(cosmeticName), data, ""));
            } catch (ArrayIndexOutOfBoundsException e) {
                
            }
        }
        ConfigLoaderOAM.showOthersCosmetics = ConfigLoaderOAM.config.get(OAMCosmetics.MODID, "showOthersCosmetics", true).getBoolean();
        ConfigLoaderOAM.config.save();
    }
    
    public static boolean containsCosmetic(EnumCosmetic enumCosmeticIn) {
        for (Cosmetic cosmetic : ConfigLoaderOAM.cosmetics) {
            if (cosmetic.getType().name().equals(enumCosmeticIn.name())) {
                return true;
            }
        }
        return false;
    }
    
    public static void removeCosmetic(EnumCosmetic enumCosmeticIn) {
        for (Cosmetic cosmetic : ConfigLoaderOAM.cosmetics) {
            if (cosmetic.getType().name().equals(enumCosmeticIn.name())) {
                ConfigLoaderOAM.cosmetics.remove(cosmetic);
            }
        }
    }
    
    public static void update(Cosmetic cosmeticIn) {
        boolean isExist = ConfigLoaderOAM.containsCosmetic(cosmeticIn.getType());
        ConfigLoaderOAM.removeCosmetic(cosmeticIn.getType());
        if (isExist) {
            ConfigLoaderOAM.cosmetics.add(cosmeticIn);
        }
    }
    
    public static void save() {
        ArrayList<String> cosmeticStrings = new ArrayList<String>();
        for (Cosmetic cosmetic : ConfigLoaderOAM.cosmetics) {
            cosmeticStrings.add(cosmetic.getType().name() + "#" + cosmetic.data);
        }
        ConfigLoaderOAM.config.get(OAMCosmetics.MODID, "cosmetics", new String[0]).set(cosmeticStrings.toArray(new String[0]));
        ConfigLoaderOAM.config.get(OAMCosmetics.MODID, "showOthersCosmetics", true).set(ConfigLoaderOAM.showOthersCosmetics);
        ConfigLoaderOAM.config.save();
    }
}
