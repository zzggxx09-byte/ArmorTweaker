package com.atweaks.bowtweaks;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class IconHelper {

    /**
     * Реєструє іконку з assets/bowtweaks/textures/items/<name>.png.
     * Якщо такого файлу немає в ресурсах (ти не поклав свою текстуру) —
     * реєструє ванільну текстуру замість неї.
     */
    public static IIcon register(IIconRegister iconRegister, String modId, String name, String vanillaFallback) {
        ResourceLocation location = new ResourceLocation(modId, "textures/items/" + name + ".png");
        boolean exists = true;
        try {
            Minecraft.getMinecraft().getResourceManager().getResource(location);
        } catch (IOException e) {
            exists = false;
        }

        if (exists) {
            return iconRegister.registerIcon(modId + ":" + name);
        } else {
            return iconRegister.registerIcon(vanillaFallback);
        }
    }
}
