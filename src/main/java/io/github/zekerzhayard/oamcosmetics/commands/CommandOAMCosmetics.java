package io.github.zekerzhayard.oamcosmetics.commands;

import io.github.zekerzhayard.oamcosmetics.gui.GuiOAMCosmetics;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CommandOAMCosmetics extends CommandBase {
    @Override()
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
    
    @Override()
    public String getCommandName() {
        return "oamc";
    }

    @Override()
    public String getCommandUsage(ICommandSender sender) {
        return "/oamc";
    }

    @Override()
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent()
    public void onClientTickEvent(TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(new GuiOAMCosmetics());
    }
}
