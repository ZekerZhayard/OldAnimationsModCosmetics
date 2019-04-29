package io.github.zekerzhayard.oamcosmetics.gui;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.spiderfrog.oldanimations.cosmetic.Cosmetic;
import com.spiderfrog.oldanimations.cosmetic.EnumCosmetic;

import io.github.zekerzhayard.oamcosmetics.OAMCosmetics;
import io.github.zekerzhayard.oamcosmetics.asm.ClassTransformer;
import io.github.zekerzhayard.oamcosmetics.config.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class GuiOAMCosmetics extends GuiScreen {
    private int base = 0;
    
    @Override()
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        int j = this.base;
        this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.BOLD + OAMCosmetics.NAME, this.width / 2 + 30, this.base + 44, 0x00FFFF55);
        this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.BOLD + "Cosmetics Settings", this.width / 2 + 30, this.base + 62, 0x00FFFFFF);
        this.drawString(this.fontRendererObj, EnumChatFormatting.DARK_AQUA + OAMCosmetics.NAME + " v" + OAMCosmetics.VERSION + " by ZekerZhayard", 0, this.height - 10, 0x00FFFFFF);
        for (int i = 1; i < EnumCosmetic.values().length; i++) {
            String text = ClassTransformer.cosmeticNames.get(EnumCosmetic.values()[i].name());
            this.drawString(this.fontRendererObj, text, this.width / 2 - 70 - this.fontRendererObj.getStringWidth(text), (j += 22) + 96, 0x00FFFFFF);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override()
    public void initGui() {
        for (int i = 1; i < EnumCosmetic.values().length; i++) {
            int x = this.width / 2 - 60;
            int y = i * 22 + this.base + 90;
            this.buttonList.add(new GuiButton(i, x, y, this.findCosmetic(EnumCosmetic.values()[i].name())) {
                @Override()
                public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                    if (super.mousePressed(mc, mouseX, mouseY)) {
                        ArrayList<String> datas = ClassTransformer.datas.get(EnumCosmetic.values()[this.id].name());
                        int i = datas.indexOf(this.displayString) + 1;
                        if (i >= datas.size()) {
                            i = 0;
                        }
                        this.displayString = datas.get(i);
                        for (Cosmetic cosmetic : Configs.cosmetics) {
                            if (cosmetic.getType().name().equals(EnumCosmetic.values()[this.id].name())) {
                                Configs.cosmetics.remove(cosmetic);
                            }
                        }
                        if (!this.displayString.equals("off")) {
                            Configs.cosmetics.add(new Cosmetic(this.id, this.displayString, ""));
                        }
                    }
                    return super.mousePressed(mc, mouseX, mouseY);
                }
            });
        }
        super.initGui();
    }
    
    @Override()
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int distance = Mouse.getDWheel() / 10;
        if ((this.base < 90 - (EnumCosmetic.values().length * 22) && distance < 0) || (this.base > 0 && distance > 0)) {
            return;
        }
        if (distance != 0) {
            this.base += distance;
            for (GuiButton button : this.buttonList) {
                button.yPosition += distance;
            }
        }
    }

    @Override()
    public void onGuiClosed() {
        Configs.save();
    }
    
    private String findCosmetic(String name) {
        for (Cosmetic cosmetic : Configs.cosmetics) {
            if (cosmetic.getType().name().equals(name)) {
                return cosmetic.data;
            }
        }
        return "off";
    }
}
