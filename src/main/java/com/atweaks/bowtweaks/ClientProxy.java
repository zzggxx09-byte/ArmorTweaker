package com.atweaks.bowtweaks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.RenderArrow;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        // У 1.7.10 конструктор Render() без аргументів,
        // RenderManager підставляється автоматично при реєстрації
        RenderingRegistry.registerEntityRenderingHandler(EntityArrowWooden.class, new RenderArrow());
        RenderingRegistry.registerEntityRenderingHandler(EntityArrowIron.class, new RenderArrow());
    }
}
