package com.atweaks.bowtweaks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderArrow;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        // Використовуємо стандартний ванільний рендер стріли (вигляд як у звичайної стріли)
        RenderingRegistry.registerEntityRenderingHandler(EntityArrowWooden.class,
                new RenderArrow(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityArrowIron.class,
                new RenderArrow(Minecraft.getMinecraft().getRenderManager()));
    }
}
