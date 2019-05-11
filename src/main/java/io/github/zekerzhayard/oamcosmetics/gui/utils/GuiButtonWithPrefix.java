package io.github.zekerzhayard.oamcosmetics.gui.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonWithPrefix extends GuiButton {
    public String prefix;
    
    public GuiButtonWithPrefix(int buttonId, int x, int y, String buttonText, String prefix) {
        this(buttonId, x, y, 150, 20, buttonText, prefix);
    }
    
    public GuiButtonWithPrefix(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, String prefix) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.prefix = prefix;
    }
    
    @Override()
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int backColor = this.hovered ? 0xFF55FFFF : 0xFF153F3F;
            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, backColor);
            this.mouseDragged(mc, mouseX, mouseY);
            this.drawCenteredString(mc.fontRendererObj, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 0xFFFFFFFF);
            this.drawString(mc.fontRendererObj, this.prefix, this.xPosition - 10 - mc.fontRendererObj.getStringWidth(this.prefix), this.yPosition + 5, 0xFFFFFFFF);
        }
    }
}
