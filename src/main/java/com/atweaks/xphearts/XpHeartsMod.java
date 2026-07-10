package com.atweaks.xphearts;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = XpHeartsMod.MODID, name = "XP Hearts", version = "1.0")
public class XpHeartsMod {

    public static final String MODID = "xphearts";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        XpConfigHandler.init(event.getSuggestedConfigurationFile());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new XpHeartsEventHandler());
    }
}
