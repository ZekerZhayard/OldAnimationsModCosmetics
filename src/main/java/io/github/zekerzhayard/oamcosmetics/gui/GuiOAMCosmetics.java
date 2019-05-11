package io.github.zekerzhayard.oamcosmetics.gui;

import java.io.IOException;

import org.lwjgl.input.Mouse;

import com.spiderfrog.oldanimations.cosmetic.EnumCosmetic;

import io.github.zekerzhayard.oamcosmetics.gui.utils.GuiButtonWithPreview;
import io.github.zekerzhayard.oamcosmetics.utils.CosmeticUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiOAMCosmetics extends GuiOAMBase {
    private int base = 0;
    
    @Override()
    public void initGui() {
        int xPreViewCount = Math.max(this.width / 300, 1);
        int j = 0;
        for (int i = 1; i < EnumCosmetic.values().length; i++) {
            int x = this.width - (xPreViewCount - j) * 150;
            int y = ((i - 1) / xPreViewCount) * 140 + this.base + 30;
            String cosmeticName = EnumCosmetic.values()[i].name();
            this.buttonList.add(new GuiButtonWithPreview(i, x, y, CosmeticUtils.upperNameToData(cosmeticName), CosmeticUtils.upperNameToCommonName(cosmeticName), EnumCosmetic.values()[i], this) {
                @Override()
                public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                    boolean isMousePressed = super.mousePressed(mc, mouseX, mouseY);
                    if (isMousePressed) {
                        mc.displayGuiScreen(new GuiOAMCosmeticsSetting(EnumCosmetic.values()[this.id], GuiOAMCosmetics.this));
                    }
                    return isMousePressed;
                }
            });
            j++;
            if (j >= xPreViewCount) {
                j = 0;
            }
        }
        super.initGui();
    }
    
    @Override()
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int distance = Mouse.getDWheel() / 10;
        int maxButtonYPosition = 0;
        for (GuiButton button : this.buttonList) {
            if (maxButtonYPosition < button.yPosition) {
                maxButtonYPosition = button.yPosition;
            }
        }
        if ((maxButtonYPosition + 170 < this.height && distance < 0) || (this.base > 0 && distance > 0)) {
            return;
        }
        if (distance != 0) {
            this.base += distance;
            for (GuiButton button : this.buttonList) {
                button.yPosition += distance;
            }
        }
    }
}
