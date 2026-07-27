package com.atweaks.bowtweaks;

import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

@Mod(modid = BowTweaksMod.MODID, name = "Bow Tweaks", version = "1.0")
public class BowTweaksMod {

    public static final String MODID = "bowtweaks";

    @SidedProxy(clientSide = "com.atweaks.bowtweaks.ClientProxy", serverSide = "com.atweaks.bowtweaks.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        ModItems.init();

        EntityRegistry.registerModEntity(EntityArrowWooden.class, "wooden_arrow", 1, this, 64, 5, true);
        EntityRegistry.registerModEntity(EntityArrowIron.class, "iron_arrow", 2, this, 64, 5, true);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        RecipeHandler.init();
        proxy.registerRenderers();

        if (ConfigHandler.removeVanillaBowAndArrow) {
            removeVanillaRecipes();
        }
    }

    @SuppressWarnings("unchecked")
    private void removeVanillaRecipes() {
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        Iterator<IRecipe> iterator = recipes.iterator();
        while (iterator.hasNext()) {
            IRecipe recipe = iterator.next();
            ItemStack output = recipe.getRecipeOutput();
            if (output != null && (output.getItem() == Items.bow || output.getItem() == Items.arrow)) {
                iterator.remove();
            }
        }
    }
}
