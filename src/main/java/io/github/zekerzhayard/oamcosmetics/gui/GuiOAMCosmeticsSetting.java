package io.github.zekerzhayard.oamcosmetics.gui;

import java.util.List;

import com.spiderfrog.oldanimations.cosmetic.Cosmetic;
import com.spiderfrog.oldanimations.cosmetic.EnumCosmetic;

import io.github.zekerzhayard.oamcosmetics.config.ConfigLoaderOAM;
import io.github.zekerzhayard.oamcosmetics.gui.utils.EnumRGB;
import io.github.zekerzhayard.oamcosmetics.gui.utils.GuiButtonWithPrefix;
import io.github.zekerzhayard.oamcosmetics.gui.utils.RGBGuiSlider;
import io.github.zekerzhayard.oamcosmetics.utils.CosmeticUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class GuiOAMCosmeticsSetting extends GuiOAMBase {
    private GuiScreen parent;
    private EnumCosmetic cosmetic;
    private Cosmetic cosmeticInstance;
    
    public GuiOAMCosmeticsSetting(EnumCosmetic cosmetic, GuiScreen parent) {
        this.parent = parent;
        this.cosmetic = cosmetic;
        this.cosmeticInstance = CosmeticUtils.enumCosmeticToCosmetic(cosmetic, CosmeticUtils.upperNameToData(this.cosmetic.name()), "");
    }
    
    @Override()
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.BOLD + CosmeticUtils.upperNameToCommonName(this.cosmetic.name()), this.width * 3 / 4, this.height / 2 - 80, 0x00FFFFFF);
    }
    
    @Override()
    public void initGui() {
        GuiButton rgbController;
        this.buttonList.add(rgbController = new GuiButtonWithPrefix(0, this.width * 3 / 4 - 75, this.height / 2 - 40, CosmeticUtils.upperNameToData(this.cosmetic.name()), "Data") {
            @Override()
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                boolean isMousePressed = super.mousePressed(mc, mouseX, mouseY);
                if (isMousePressed) {
                    List<String> datas = CosmeticUtils.upperNameToDataList(GuiOAMCosmeticsSetting.this.cosmetic.name());
                    int i = datas.indexOf(this.displayString) + 1;
                    if (i >= datas.size()) {
                        i = GuiOAMCosmeticsSetting.this.cosmetic.isRGBAllowed() ? -1 : 0;
                    }
                    String rgbStr = CosmeticUtils.cosmeticRGBStr(GuiOAMCosmeticsSetting.this.cosmeticInstance);
                    this.displayString = i < 0 ? rgbStr : datas.get(i);
                    ConfigLoaderOAM.removeCosmetic(GuiOAMCosmeticsSetting.this.cosmetic);
                    GuiOAMCosmeticsSetting.this.drawRGBGuiSlider(this);
                    if (!this.displayString.equals("off")) {
                        ConfigLoaderOAM.cosmetics.add(GuiOAMCosmeticsSetting.this.cosmeticInstance);
                    }
                }
                return isMousePressed;
            }
        });
        this.drawRGBGuiSlider(rgbController);
        this.buttonList.add(new GuiButtonWithPrefix(2, this.width * 3 / 4 - 75, this.height / 2 + 60, "Back", "") {
            @Override()
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                boolean isMousePressed = super.mousePressed(mc, mouseX, mouseY);
                if (isMousePressed) {
                    mc.displayGuiScreen(GuiOAMCosmeticsSetting.this.parent);
                }
                return isMousePressed;
            }
        });
        super.initGui();
    }
    
    @Override()
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        ConfigLoaderOAM.update(this.cosmeticInstance);
    }
    
    private void drawRGBGuiSlider(GuiButton controller) {
        String rgbStr = CosmeticUtils.cosmeticRGBStr(this.cosmeticInstance);
        if (controller.displayString.contains(",")) {
            CosmeticUtils.setCosmeticData(this.cosmeticInstance, rgbStr);
            for (int j = 1; j < 4; j++) {
                EnumRGB rgb = EnumRGB.values()[j - 1];
                String rgbValue = this.cosmeticInstance.data.split(",")[j - 1];
                this.buttonList.add(new RGBGuiSlider(j + 100, controller.xPosition, controller.yPosition + j * 25, rgb.getCommonName(), Double.valueOf(rgbValue), controller, this.cosmeticInstance, rgb));
            }
        } else {
            CosmeticUtils.setCosmeticData(this.cosmeticInstance, controller.displayString);
            for (int j = this.buttonList.size() - 1; j >= 0; j--) {
                if (this.buttonList.get(j).id > 100) {
                    this.buttonList.remove(j);
                }
            }
        }
    }
}
