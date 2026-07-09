package com.atweaks.armortweaks;

import java.util.UUID;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ArmorEventHandler {

    private static final UUID LEATHER_SPEED_UUID = UUID.fromString("b1f7a1a0-1111-4a2a-9c1a-000000000001");
    private static final UUID IRON_SPEED_UUID = UUID.fromString("b1f7a1a0-1111-4a2a-9c1a-000000000002");

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {
        if (!(event.entity instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) event.entity;

        IAttributeInstance speedAttribute = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

        double leatherWeight = ConfigHandler.leatherEnabled ? weightOf(player, true) : 0.0;
        double ironWeight = ConfigHandler.ironEnabled ? weightOf(player, false) : 0.0;

        updateModifier(speedAttribute, LEATHER_SPEED_UUID, "leatherSpeedBonus", leatherWeight > 0, ConfigHandler.leatherWalkSpeedBonus * leatherWeight);
        updateModifier(speedAttribute, IRON_SPEED_UUID, "ironSpeedPenalty", ironWeight > 0, -ConfigHandler.ironWalkSpeedPenalty * ironWeight);

        if (player.isInWater()) {
            if (leatherWeight > 0 && ConfigHandler.leatherBetterSwim) {
                double push = ConfigHandler.leatherSwimBonus * leatherWeight;
                if (player.motionY < 0.0D) {
                    player.motionY += push;
                } else {
                    player.motionY += push * 0.5D;
                }
            }
            if (ironWeight > 0 && ConfigHandler.ironWorseSwim) {
                player.motionY -= ConfigHandler.ironSwimPenalty * ironWeight;
            }
        }
    }

    private void updateModifier(IAttributeInstance attribute, UUID uuid, String name, boolean shouldHave, double amount) {
        AttributeModifier existing = attribute.getModifier(uuid);
        if (existing != null) {
            attribute.removeModifier(existing);
        }
        if (shouldHave) {
            attribute.applyModifier(new AttributeModifier(uuid, name, amount, 2));
        }
    }

    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event) {
        if (!(event.entity instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) event.entity;

        double leatherWeight = ConfigHandler.leatherEnabled ? weightOf(player, true) : 0.0;
        double ironWeight = ConfigHandler.ironEnabled ? weightOf(player, false) : 0.0;

        float multiplier = 1.0f;
        if (leatherWeight > 0) {
            multiplier -= (float) (ConfigHandler.leatherFallDamageReduction * leatherWeight);
        }
        if (ironWeight > 0) {
            multiplier += (float) (ConfigHandler.ironFallDamageIncrease * ironWeight);
        }
        if (multiplier < 0) multiplier = 0;

        event.distance *= multiplier;
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (!(event.entity instanceof EntityPlayer)) return;
        if (!ConfigHandler.leatherEnabled) return;

        EntityPlayer player = (EntityPlayer) event.entity;
        DamageSource source = event.source;
        boolean isProjectile = source.isProjectile();
        boolean isExplosion = source.isExplosion();
        if (!isProjectile && !isExplosion) return;

        double leatherWeight = weightOf(player, true);
        if (leatherWeight <= 0) return;

        float penaltyFraction = (float) (leatherWeight * ConfigHandler.leatherArrowExplosionProtectionPenalty * 0.25);
        event.ammount += event.ammount * penaltyFraction;

        int extraDamage = Math.max(1, (int) Math.round(ConfigHandler.leatherArrowExplosionDurabilityMultiplier));
        for (int slot = 0; slot < 4; slot++) {
            ItemStack piece = player.inventory.armorInventory[slot];
            if (isLeather(piece)) {
                piece.damageItem(extraDamage, player);
            }
        }
    }

    private double weightOf(EntityPlayer player, boolean leather) {
        double weight = 0.0;
        if (matches(player.inventory.armorInventory[3], leather)) weight += ConfigHandler.weightHelmet;
        if (matches(player.inventory.armorInventory[2], leather)) weight += ConfigHandler.weightChest;
        if (matches(player.inventory.armorInventory[1], leather)) weight += ConfigHandler.weightLegs;
        if (matches(player.inventory.armorInventory[0], leather)) weight += ConfigHandler.weightBoots;
        return weight;
    }

    private boolean matches(ItemStack stack, boolean leather) {
        return leather ? isLeather(stack) : isIron(stack);
    }

    private boolean isLeather(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof ItemArmor)) return false;
        ItemArmor armor = (ItemArmor) stack.getItem();
        return armor.getArmorMaterial() == ItemArmor.ArmorMaterial.CLOTH;
    }

    private boolean isIron(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof ItemArmor)) return false;
        ItemArmor armor = (ItemArmor) stack.getItem();
        return armor.getArmorMaterial() == ItemArmor.ArmorMaterial.IRON;
    }
}
