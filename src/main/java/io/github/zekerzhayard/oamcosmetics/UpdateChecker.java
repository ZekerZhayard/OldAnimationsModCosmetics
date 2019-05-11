package io.github.zekerzhayard.oamcosmetics;

import java.util.List;

import com.spiderfrog.oldanimations.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class UpdateChecker {
    public static void startCheckUpdate() {
        new UpdateChecker();
    }
    
    private String currentVersion = OAMCosmetics.VERSION;
    private String checkedVersion;
    private boolean firstLogin = true;
    
    private UpdateChecker() {
        new Thread() {
            @Override()
            public void run() {
                try {
                    System.out.println("Checking update...");
                    List<String> texts = Utils.getWebsite("https://raw.githubusercontent.com/ZekerZhayard/OldAnimationsModCosmetics/master/gradle.properties");
                    for (String text : texts) {
                        if (text.split("=")[0].equals("mod_version")) {
                            UpdateChecker.this.checkedVersion = text.split("=")[1];
                            UpdateChecker.this.checkUpdate();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    
    private void checkUpdate() {
        String[] currentVersionSplit = this.currentVersion.split("\\.");
        String[] checkedVersionSplit = this.checkedVersion.split("\\.");
        for (int i = 0; i < currentVersionSplit.length; i++) {
            if (Integer.valueOf(currentVersionSplit[i]) < Integer.valueOf(checkedVersionSplit[i])) {
                MinecraftForge.EVENT_BUS.register(this);
                System.out.println("New version found: " + this.checkedVersion);
                return;
            }
        }
    }
    
    @SubscribeEvent()
    public void sendUpdateMessage(GuiScreenEvent.InitGuiEvent.Post event) {
        if (!this.firstLogin || !(event.gui instanceof GuiDownloadTerrain)) {
            return;
        }
        this.firstLogin = false;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentTranslation("update.message", this.checkedVersion));
    }
}
