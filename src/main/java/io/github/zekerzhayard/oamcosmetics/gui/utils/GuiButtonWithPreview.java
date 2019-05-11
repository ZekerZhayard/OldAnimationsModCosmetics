package io.github.zekerzhayard.oamcosmetics.gui.utils;

import com.mojang.authlib.GameProfile;
import com.spiderfrog.oldanimations.cosmetic.EnumCosmetic;

import io.github.zekerzhayard.oamcosmetics.gui.entity.EntityPlayerPreview;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;

public class GuiButtonWithPreview extends GuiButton {
    public String textLine1;
    public EnumCosmetic enumCosmetic;
    public GuiScreen parent;
    
    public GuiButtonWithPreview(int buttonId, int x, int y, String buttonText, String textLine1, EnumCosmetic enumCosmetic, GuiScreen parent) {
        this(buttonId, x, y, 140, 40, buttonText, textLine1, enumCosmetic, parent);
    }
    
    public GuiButtonWithPreview(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, String textLine1, EnumCosmetic enumCosmetic, GuiScreen parent) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.textLine1 = textLine1;
        this.enumCosmetic = enumCosmetic;
        this.parent = parent;
    }
    
    @Override()
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible && this.isInScreen()) {
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int backColor = this.hovered ? 0xFF55FFFF : 0x70153F3F;
            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, backColor);
            this.mouseDragged(mc, mouseX, mouseY);
            this.drawCenteredString(mc.fontRendererObj, this.textLine1, this.xPosition + this.width / 2, this.yPosition - 4 + this.height / 4, 0xFFFFFFFF);
            this.drawCenteredString(mc.fontRendererObj, this.displayString, this.xPosition + this.width / 2, this.yPosition - 4 + this.height * 3 / 4, 0xFFFFFFFF);
            String entityName = this.enumCosmetic.name() + "#Cosmetic";
            GuiInventory.drawEntityOnScreen(this.xPosition + this.width / 2, this.yPosition + this.height + this.width * this.parent.height / this.parent.width, this.width * 30 / 128, 5, 4, new EntityPlayerPreview(mc.theWorld, new GameProfile(EntityPlayer.getOfflineUUID(entityName), entityName)));
        }
    }
    
    private boolean isInScreen() {
        return this.xPosition + this.width >= 0 && this.xPosition <= this.parent.width && this.yPosition + this.height >= 0 && this.yPosition <= this.parent.height;
    }
}
