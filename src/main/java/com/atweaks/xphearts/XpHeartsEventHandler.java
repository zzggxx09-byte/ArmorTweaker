package com.atweaks.xphearts;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class XpHeartsEventHandler {

    private static final UUID XP_HEALTH_UUID = UUID.fromString("c2f8b2b1-2222-4b3b-8d2b-000000000099");
    private static final Map<String, Integer> knownHearts = new HashMap<String, Integer>();

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {
        if (!(event.entity instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) event.entity;
        if (player.worldObj.isRemote) return;

        IAttributeInstance maxHealthAttribute = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);

        double baseHealth = XpConfigHandler.baseHearts * 2.0D;
        if (maxHealthAttribute.getBaseValue() != baseHealth) {
            maxHealthAttribute.setBaseValue(baseHealth);
        }

        int earnedHearts = calcEarnedHearts(player.experienceTotal);

        AttributeModifier existing = maxHealthAttribute.getModifier(XP_HEALTH_UUID);
        double bonusHealth = earnedHearts * 2.0D;
        double existingAmount = existing != null ? existing.getAmount() : -1;
        if (existingAmount != bonusHealth) {
            if (existing != null) {
                maxHealthAttribute.removeModifier(existing);
            }
            if (bonusHealth > 0) {
                maxHealthAttribute.applyModifier(new AttributeModifier(XP_HEALTH_UUID, "xpHeartsBonus", bonusHealth, 0));
            }
        }

        if (player.getHealth() > player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
        }

        String key = player.getUniqueID().toString();
        Integer prev = knownHearts.get(key);
        if (prev == null) {
            knownHearts.put(key, earnedHearts);
        } else if (earnedHearts > prev) {
            int gained = earnedHearts - prev;
            player.heal(gained * 2.0F);
            knownHearts.put(key, earnedHearts);
            if (XpConfigHandler.announceInChat) {
                player.addChatMessage(new ChatComponentText(
                        EnumChatFormatting.RED + "+ " + gained + " нове серце за досвід! Усього сердець: "
                                + (XpConfigHandler.baseHearts + earnedHearts)));
            }
        } else if (earnedHearts < prev) {
            knownHearts.put(key, earnedHearts);
        }
    }

    private int calcEarnedHearts(int totalXp) {
        int maxExtra = XpConfigHandler.maxHearts - XpConfigHandler.baseHearts;
        if (maxExtra <= 0) return 0;

        int earned = 0;
        double neededCumulative = 0;
        double cost = XpConfigHandler.baseXpCost;

        while (earned < maxExtra) {
            neededCumulative += cost;
            if (totalXp < neededCumulative) break;
            earned++;
            cost *= XpConfigHandler.xpCostMultiplier;
        }
        return earned;
    }
}
