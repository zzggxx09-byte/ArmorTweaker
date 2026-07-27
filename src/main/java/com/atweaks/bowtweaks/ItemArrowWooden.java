package com.atweaks.bowtweaks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemArrowWooden extends Item {

    public ItemArrowWooden() {
        this.setMaxStackSize(64);
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.setUnlocalizedName("bowtweaks_arrow_wooden");
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        // Немає власної текстури — беремо ванільну текстуру стріли
        this.itemIcon = iconRegister.registerIcon("minecraft:arrow");
    }
}
