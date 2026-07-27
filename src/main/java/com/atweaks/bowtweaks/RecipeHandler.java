package com.atweaks.bowtweaks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipeHandler {

    public static void init() {
        // Small Bow: 3 палки + 1 нитка
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemSmallBow),
                Items.stick, Items.stick, Items.stick, Items.string);

        // Professional Bow: 3 залізних злитки + 3 нитки
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemProfessionalBow),
                Items.iron_ingot, Items.iron_ingot, Items.iron_ingot,
                Items.string, Items.string, Items.string);

        // Дерев'яна стріла: перо + палка (кількість — з конфігу)
        GameRegistry.addShapelessRecipe(
                new ItemStack(ModItems.itemArrowWooden, ConfigHandler.woodenArrowCraftCount),
                Items.feather, Items.stick);

        // Залізна стріла: перо + палка + злиток заліза (кількість — з конфігу)
        GameRegistry.addShapelessRecipe(
                new ItemStack(ModItems.itemArrowIron, ConfigHandler.ironArrowCraftCount),
                Items.feather, Items.stick, Items.iron_ingot);
    }
}
