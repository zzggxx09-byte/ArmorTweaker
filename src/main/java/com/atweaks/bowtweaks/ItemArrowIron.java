package com.atweaks.bowtweaks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemArrowIron extends Item {

    public ItemArrowIron() {
        this.setMaxStackSize(64);
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.setUnlocalizedName("bowtweaks_arrow_iron");
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("minecraft:arrow");
    }
}
