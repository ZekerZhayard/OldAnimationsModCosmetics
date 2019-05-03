package io.github.zekerzhayard.oamcosmetics.asm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.spiderfrog.oldanimations.OldAnimations;
import com.spiderfrog.oldanimations.Settings;
import com.spiderfrog.oldanimations.cosmetic.Cosmetic;

import io.github.zekerzhayard.oamcosmetics.config.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;

public class ByteCodeHook {
    public static ArrayList<Cosmetic> getSelfCosmetics(HashMap<UUID, ArrayList<Cosmetic>> cosmetics, Entity entityIn) {
        Entity player = Minecraft.getMinecraft().thePlayer;
        if (player != null && player.equals(entityIn)) {
            return Lists.newArrayList(Configs.cosmetics);
        }
        if (Configs.showOthersCosmetics) {
            return cosmetics.get(entityIn.getUniqueID());
        }
        return null;
    }
    
    public static String getCosmeticsSettingString(String str) {
        if (Settings.cosmetics) {
            if (Configs.showOthersCosmetics) {
                return "Cosmetics: " + OldAnimations.on;
            }
            return "Cosmetics: " + EnumChatFormatting.YELLOW + "OnlySelf";
        }
        return "Cosmetics: " + OldAnimations.off;
    }
    
    public static void setShowOthersCosmetics() {
        if (!Settings.cosmetics) {
            Settings.cosmetics = Configs.showOthersCosmetics;
            Configs.showOthersCosmetics = !Configs.showOthersCosmetics;
            Configs.save();
        }
    }
}
