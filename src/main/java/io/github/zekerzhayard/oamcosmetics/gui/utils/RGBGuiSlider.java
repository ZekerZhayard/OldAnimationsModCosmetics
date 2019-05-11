package io.github.zekerzhayard.oamcosmetics.gui.utils;

import com.spiderfrog.oldanimations.cosmetic.Cosmetic;
import com.spiderfrog.oldanimations.gui.utils.DefaultGuiSlider;

import io.github.zekerzhayard.oamcosmetics.utils.CosmeticUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

public class RGBGuiSlider extends DefaultGuiSlider {
    private Cosmetic cosmetic;
    private EnumRGB rgb;
    private GuiButton controller;
    private String prefix;
    
    public RGBGuiSlider(int id, int xPos, int yPos, String prefix, double currentVal, GuiButton controller, Cosmetic cosmetic, EnumRGB rgb) {
        super(id, xPos, yPos, 150, 20, "", "", 0.0D, 255.0D, currentVal, false, false);
        this.controller = controller;
        this.cosmetic = cosmetic;
        this.rgb = rgb;
        this.prefix = prefix;
    }
    
    @Override()
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            if (this.dragging) {
                this.sliderValue = (mouseX - (this.xPosition + 4)) / (float)(this.width - 8);
                this.updateSlider();
            }
        }
    }
    
    @Override
    public void updateSlider() {
        super.updateSlider();
        double value = this.sliderValue * (this.maxValue - this.minValue) + this.minValue;
        switch (this.rgb) {
            case RED: {
                this.cosmetic.r = value;
                break;
            } case GREEN: {
                this.cosmetic.g = value;
                break;
            } case BLUE: {
                this.cosmetic.b = value;
            }
        }
        CosmeticUtils.setCosmeticData(this.cosmetic, CosmeticUtils.cosmeticRGBStr(this.cosmetic));
        this.controller.displayString = this.cosmetic.data;
    }
    
    @Override()
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int backColor = this.hovered ? 0xFF55FFFF : 0xFF153F3F;
            Gui.drawRect(this.xPosition, this.yPosition + this.height / 2 - 1, this.xPosition + this.width, this.yPosition + this.height / 2 + 1, backColor);
            int k = this.xPosition + (int) ((this.width - 6) * this.sliderValue + 3);
            Gui.drawRect(k - 3, this.yPosition, k + 3, this.yPosition + this.height, backColor);
            this.mouseDragged(mc, mouseX, mouseY);
            this.drawString(mc.fontRendererObj, this.prefix, this.xPosition - 10 - mc.fontRendererObj.getStringWidth(this.prefix), this.yPosition + 5, 0xFFFFFF);
        }
    }
}
