package com.atweaks.armortweaks;

import java.util.UUID;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ArmorEventHandler {

    private static final UUID LEATHER_SPEED_UUID = UUID.fromString("b1f7a1a0-1111-4a2a-9c1a-000000000001");
    private static final UUID IRON_SPEED_UUID = UUID.fromString("b1f7a1a0-1111-4a2a-9c1a-000000000002");

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {
        if (!(event.entity instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) event.entity;

        IAttributeInstance speedAttribute = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

        boolean hasLeatherSet = ConfigHandler.leatherEnabled && isLeather(player.inventory.armorInventory[2]) && isLeather(player.inventory.armorInventory[1]);
        boolean hasIronSet = ConfigHandler.ironEnabled && isIron(player.inventory.armorInventory[2]) && isIron(player.inventory.armorInventory[1]);

        updateModifier(speedAttribute, LEATHER_SPEED_UUID, "leatherSpeedBonus", hasLeatherSet, ConfigHandler.leatherWalkSpeedBonus);
        updateModifier(speedAttribute, IRON_SPEED_UUID, "ironSpeedPenalty", hasIronSet, -ConfigHandler.ironWalkSpeedPenalty);

        if (player.isInWater()) {
            if (hasLeatherSet && ConfigHandler.leatherBetterSwim) {
                if (player.motionY < 0.0D) {
                    player.motionY += ConfigHandler.leatherSwimBonus;
                } else {
                    player.motionY += ConfigHandler.leatherSwimBonus * 0.5D;
                }
            }
            if (hasIronSet && ConfigHandler.ironWorseSwim) {
                player.motionY -= ConfigHandler.ironSwimPenalty;
            }
        }
    }

    private void updateModifier(IAttributeInstance attribute, UUID uuid, String name, boolean shouldHave, double amount) {
        AttributeModifier existing = attribute.getModifier(uuid);
        if (shouldHave) {
            if (existing == null) {
                attribute.applyModifier(new AttributeModifier(uuid, name, amount, 2));
            }
        } else {
            if (existing != null) {
                attribute.removeModifier(existing);
            }
        }
    }

    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event) {
        if (!(event.entity instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) event.entity;

        boolean hasLeatherSet = ConfigHandler.leatherEnabled && isLeather(player.inventory.armorInventory[2]) && isLeather(player.inventory.armorInventory[1]);
        boolean hasIronSet = ConfigHandler.ironEnabled && isIron(player.inventory.armorInventory[2]) && isIron(player.inventory.armorInventory[1]);

        if (hasLeatherSet) {
            event.distance = (float) (event.distance * ConfigHandler.leatherFallDamageMultiplier);
        }
        if (hasIronSet) {
            event.distance = (float) (event.distance * ConfigHandler.ironFallDamageMultiplier);
        }
    }

    private boolean isLeather(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof ItemArmor)) return false;
        ItemArmor armor = (ItemArmor) stack.getItem();
        return armor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER;
    }

    private boolean isIron(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof ItemArmor)) return false;
        ItemArmor armor = (ItemArmor) stack.getItem();
        return armor.getArmorMaterial() == ItemArmor.ArmorMaterial.IRON;
    }
}
