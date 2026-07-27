package com.atweaks.bowtweaks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemProfessionalBow extends ItemBow {

    public ItemProfessionalBow() {
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.setUnlocalizedName("bowtweaks_professional_bow");
        this.setMaxDamage(ConfigHandler.proBowDurability);
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("minecraft:bow_standby");
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useRemaining) {
        boolean creative = player.capabilities.isCreativeMode;
        boolean hasAmmo = creative || player.inventory.hasItem(ModItems.itemArrowIron);
        if (!hasAmmo) {
            return;
        }

        int ticksUsed = this.getMaxItemUseDuration(stack) - useRemaining;

        float charge = (float) ticksUsed / (float) ConfigHandler.proBowDrawTicks;
        charge = (charge * charge + charge * 2.0F) / 3.0F;
        if (charge > 1.0F) charge = 1.0F;
        if (charge < 0.1F) return;

        float velocity = charge * 2.0F * (float) ConfigHandler.proBowVelocityMultiplier;

        EntityArrowIron arrow = new EntityArrowIron(world, player, velocity);
        if (charge == 1.0F) {
            arrow.setIsCritical(true);
        }

        stack.damageItem(1, player);
        world.playSoundAtEntity(player, "random.bow", 1.0F,
                1.0F / (this.itemRand.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);

        if (creative) {
            arrow.canBePickedUp = 2;
        } else {
            player.inventory.consumeInventoryItem(ModItems.itemArrowIron);
            arrow.canBePickedUp = 1;
        }

        if (!world.isRemote) {
            world.spawnEntityInWorld(arrow);
        }
    }
}
