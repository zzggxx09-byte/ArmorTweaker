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
    this.itemIcon = IconHelper.register(iconRegister, "bowtweaks", "arrow_wooden", "minecraft:arrow");
    }
}
