package io.github.zekerzhayard.oamcosmetics;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import io.github.zekerzhayard.oamcosmetics.commands.CommandOAMCosmetics;
import io.github.zekerzhayard.oamcosmetics.config.ConfigLoaderOAM;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class OAMCosmetics extends DummyModContainer {
    public static final String MODID = "oldanimationsmodcosmetics";
    public static final String NAME = "OldAnimationsModCosmetics";
    public static final String VERSION = "@VERSION@";
    
    public OAMCosmetics() {
        super(new ModMetadata());
        ModMetadata md = this.getMetadata();
        md.modId = OAMCosmetics.MODID;
        md.name = OAMCosmetics.NAME;
        md.version = OAMCosmetics.VERSION;
        md.authorList = Arrays.asList("ZekerZhayard");
        md.description = "Unlock cosmetics by self in OldAnimationsMod.";
    }
    
    @Override()
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }
    
    @Subscribe()
    public void preInit(FMLPreInitializationEvent event) {
        ConfigLoaderOAM.init();
    }
    
    @Subscribe()
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new CommandOAMCosmetics());
        UpdateChecker.startCheckUpdate();
    }
}
