package io.github.zekerzhayard.oamcosmetics.gui;

import io.github.zekerzhayard.oamcosmetics.OAMCosmetics;
import io.github.zekerzhayard.oamcosmetics.config.ConfigLoaderOAM;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.EnumChatFormatting;

public abstract class GuiOAMBase extends GuiScreen {
    @Override()
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        Gui.drawRect(this.width / 2, 0, this.width, this.height, 0x50000000);
        this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.BOLD + OAMCosmetics.NAME, this.width / 4, 44, 0x00FFFF55);
        this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.BOLD + "Cosmetics Settings", this.width / 4, 62, 0x00FFFFFF);
        this.drawString(this.fontRendererObj, EnumChatFormatting.DARK_AQUA + OAMCosmetics.NAME + " v" + OAMCosmetics.VERSION + " by ZekerZhayard", 0, this.height - 10, 0x00FFFFFF);
        GuiInventory.drawEntityOnScreen(this.width / 4, this.height / 2 + 60, 50, this.width / 4 - mouseX, this.height / 2 + 30 - 77 - mouseY, this.mc.thePlayer);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override()
    public void onGuiClosed() {
        ConfigLoaderOAM.save();
    }
}
