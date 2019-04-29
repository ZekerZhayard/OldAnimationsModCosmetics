package io.github.zekerzhayard.oamcosmetics.asm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.spiderfrog.oldanimations.cosmetic.Cosmetic;

import io.github.zekerzhayard.oamcosmetics.config.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class ByteCodeHook {
    public static ArrayList<Cosmetic> getSelfCosmetics(HashMap<UUID, ArrayList<Cosmetic>> cosmetics, Entity entityIn) {
        Entity player = Minecraft.getMinecraft().thePlayer;
        if (player != null && player.equals(entityIn)) {
            return Lists.newArrayList(Configs.cosmetics);
        }
        return cosmetics.get(entityIn.getUniqueID());
    }
}
