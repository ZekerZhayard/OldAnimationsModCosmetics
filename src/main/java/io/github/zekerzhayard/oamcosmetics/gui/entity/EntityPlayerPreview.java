package io.github.zekerzhayard.oamcosmetics.gui.entity;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityPlayerPreview extends AbstractClientPlayer {
    public EntityPlayerPreview(World worldIn, GameProfile playerProfile) {
        super(worldIn, playerProfile);
    }
    
    @Override()
    public ResourceLocation getLocationSkin() {
        return Minecraft.getMinecraft().thePlayer.getLocationSkin();
    }
}
