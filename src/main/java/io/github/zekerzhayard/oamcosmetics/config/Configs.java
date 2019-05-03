package io.github.zekerzhayard.oamcosmetics.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

import com.spiderfrog.oldanimations.cosmetic.Cosmetic;
import com.spiderfrog.oldanimations.cosmetic.EnumCosmetic;
import com.spiderfrog.oldanimations.utils.JsonConfig;

import io.github.zekerzhayard.oamcosmetics.OAMCosmetics;
import net.minecraftforge.common.config.Configuration;

public class Configs {
    public static Configuration config;
    public static CopyOnWriteArrayList<Cosmetic> cosmetics = new CopyOnWriteArrayList<Cosmetic>();
    public static boolean showOthersCosmetics = true;
    
    public static void init() {
        Configs.config = new Configuration(new File(new JsonConfig().path, OAMCosmetics.NAME + ".cfg"));
        Configs.config.load();
        for (String cosmeticNameAndData : Configs.config.get(OAMCosmetics.MODID, "cosmetics", new String[0]).getStringList()) {
            try {
                String cosmeticName = cosmeticNameAndData.split(",")[0];
                String data = cosmeticNameAndData.split(",")[1];
                Configs.cosmetics.add(new Cosmetic(Arrays.binarySearch(EnumCosmetic.values(), EnumCosmetic.valueOf(cosmeticName)), data, ""));
            } catch (ArrayIndexOutOfBoundsException e) {
                
            }
        }
        Configs.showOthersCosmetics = Configs.config.get(OAMCosmetics.MODID, "showOthersCosmetics", true).getBoolean();
        Configs.config.save();
    }
    
    public static void save() {
        ArrayList<String> cosmeticStrings = new ArrayList<String>();
        for (Cosmetic cosmetic : Configs.cosmetics) {
            cosmeticStrings.add(cosmetic.getType().name() + "," + cosmetic.data);
        }
        Configs.config.get(OAMCosmetics.MODID, "cosmetics", new String[0]).set(cosmeticStrings.toArray(new String[0]));
        Configs.config.get(OAMCosmetics.MODID, "showOthersCosmetics", true).set(Configs.showOthersCosmetics);
        Configs.config.save();
    }
}
