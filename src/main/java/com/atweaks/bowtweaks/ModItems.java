package com.atweaks.bowtweaks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ModItems {

    public static Item itemSmallBow;
    public static Item itemProfessionalBow;
    public static Item itemArrowWooden;
    public static Item itemArrowIron;

    public static void init() {
        itemSmallBow = new ItemSmallBow();
        itemProfessionalBow = new ItemProfessionalBow();
        itemArrowWooden = new ItemArrowWooden();
        itemArrowIron = new ItemArrowIron();

        GameRegistry.registerItem(itemSmallBow, "small_bow");
        GameRegistry.registerItem(itemProfessionalBow, "professional_bow");
        GameRegistry.registerItem(itemArrowWooden, "arrow_wooden");
        GameRegistry.registerItem(itemArrowIron, "arrow_iron");
    }
}
