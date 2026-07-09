package com.atweaks.armortweaks;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;

public class ArmorStatsHandler {

    public static void applyOverrides() {
        try {
            overridePiece((ItemArmor) Items.leather_helmet, ConfigHandler.leatherHelmetArmor, ConfigHandler.leatherHelmetDurability);
            overridePiece((ItemArmor) Items.leather_chestplate, ConfigHandler.leatherChestArmor, ConfigHandler.leatherChestDurability);
            overridePiece((ItemArmor) Items.leather_leggings, ConfigHandler.leatherLegsArmor, ConfigHandler.leatherLegsDurability);
            overridePiece((ItemArmor) Items.leather_boots, ConfigHandler.leatherBootsArmor, ConfigHandler.leatherBootsDurability);

            overridePiece((ItemArmor) Items.iron_helmet, ConfigHandler.ironHelmetArmor, ConfigHandler.ironHelmetDurability);
            overridePiece((ItemArmor) Items.iron_chestplate, ConfigHandler.ironChestArmor, ConfigHandler.ironChestDurability);
            overridePiece((ItemArmor) Items.iron_leggings, ConfigHandler.ironLegsArmor, ConfigHandler.ironLegsDurability);
            overridePiece((ItemArmor) Items.iron_boots, ConfigHandler.ironBootsArmor, ConfigHandler.ironBootsDurability);
        } catch (Exception e) {
            System.err.println("[ArmorTweaks] Не вдалось застосувати перевизначення статів броні: " + e.getMessage());
        }
    }

    private static void overridePiece(ItemArmor armor, int newArmorPoints, int newDurability) throws Exception {
        ((Item) armor).setMaxDamage(newDurability);

        Field field = ItemArmor.class.getDeclaredField("damageReduceAmount");
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.setInt(armor, newArmorPoints);
    }
}
