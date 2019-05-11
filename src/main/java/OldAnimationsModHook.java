import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.spiderfrog.oldanimations.OldAnimations;
import com.spiderfrog.oldanimations.Settings;
import com.spiderfrog.oldanimations.cosmetic.Cosmetic;

import io.github.zekerzhayard.oamcosmetics.config.ConfigLoaderOAM;
import io.github.zekerzhayard.oamcosmetics.gui.entity.EntityPlayerPreview;
import io.github.zekerzhayard.oamcosmetics.utils.CosmeticUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;

public class OldAnimationsModHook {
    public static ArrayList<Cosmetic> getSelfCosmetics(HashMap<UUID, ArrayList<Cosmetic>> cosmetics, final Entity entityIn) {
        Entity player = Minecraft.getMinecraft().thePlayer;
        if (player != null && player.equals(entityIn)) {
            return Lists.newArrayList(ConfigLoaderOAM.cosmetics);
        }
        if (entityIn instanceof EntityPlayerPreview) {
            entityIn.ticksExisted = player.ticksExisted;
            return Lists.newArrayList(new Cosmetic(CosmeticUtils.upperNameToEnumCosmeitcOrdinal(entityIn.getName().split("#")[0]), "", ""));
        }
        if (ConfigLoaderOAM.showOthersCosmetics) {
            return cosmetics.get(entityIn.getUniqueID());
        }
        return null;
    }
    
    public static Cosmetic resetColor(Cosmetic cosmetic) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        return cosmetic;
    }
    
    public static String getCosmeticsSettingString(String str) {
        if (Settings.cosmetics) {
            if (ConfigLoaderOAM.showOthersCosmetics) {
                return "Cosmetics: " + OldAnimations.on;
            }
            return "Cosmetics: " + EnumChatFormatting.YELLOW + "OnlySelf";
        }
        return "Cosmetics: " + OldAnimations.off;
    }
    
    public static void setShowOthersCosmetics() {
        if (!Settings.cosmetics) {
            Settings.cosmetics = ConfigLoaderOAM.showOthersCosmetics;
            ConfigLoaderOAM.showOthersCosmetics = !ConfigLoaderOAM.showOthersCosmetics;
            ConfigLoaderOAM.save();
        }
    }
}
