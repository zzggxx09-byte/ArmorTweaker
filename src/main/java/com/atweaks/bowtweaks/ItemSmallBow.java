package com.atweaks.bowtweaks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ItemSmallBow extends ItemBow {

    private IIcon[] pullIcons;

    public ItemSmallBow() {
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.setUnlocalizedName("bowtweaks_small_bow");
        this.setMaxDamage(ConfigHandler.smallBowDurability);
        this.setFull3D();
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = IconHelper.register(iconRegister, "bowtweaks", "small_bow_standby", "minecraft:bow_standby");

        pullIcons = new IIcon[3];
        pullIcons[0] = IconHelper.register(iconRegister, "bowtweaks", "small_bow_pulling_0", "minecraft:bow_pulling_0");
        pullIcons[1] = IconHelper.register(iconRegister, "bowtweaks", "small_bow_pulling_1", "minecraft:bow_pulling_1");
        pullIcons[2] = IconHelper.register(iconRegister, "bowtweaks", "small_bow_pulling_2", "minecraft:bow_pulling_2");
    }

    // Саме цей метод відповідає за анімацію натягу від першої особи
    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        if (usingItem == null) {
            return this.itemIcon;
        }

        int ticksInUse = this.getMaxItemUseDuration(stack) - useRemaining;
        int drawTicks = ConfigHandler.smallBowDrawTicks;

        if (ticksInUse >= drawTicks) {
            return pullIcons[2];
        } else if (ticksInUse > drawTicks * 0.66F) {
            return pullIcons[1];
        } else if (ticksInUse > 0) {
            return pullIcons[0];
        }
        return this.itemIcon;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        boolean hasAmmo = player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.itemArrowWooden);

        ArrowNockEvent event = new ArrowNockEvent(player, stack);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            return event.result;
        }

        if (hasAmmo) {
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
            world.playSoundAtEntity(player, "bowtweaks:small_bow_draw", 0.5F, 1.0F);
        }
        return stack;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useRemaining) {
        boolean creative = player.capabilities.isCreativeMode;
        boolean hasAmmo = creative || player.inventory.hasItem(ModItems.itemArrowWooden);
        if (!hasAmmo) {
            return;
        }

        int ticksUsed = this.getMaxItemUseDuration(stack) - useRemaining;

        float charge = (float) ticksUsed / (float) ConfigHandler.smallBowDrawTicks;
        charge = (charge * charge + charge * 2.0F) / 3.0F;
        if (charge > 1.0F) charge = 1.0F;
        if (charge < 0.1F) return;

        float velocity = charge * 2.0F * (float) ConfigHandler.smallBowVelocityMultiplier;

        EntityArrowWooden arrow = new EntityArrowWooden(world, player, velocity);
        if (charge == 1.0F) {
            arrow.setIsCritical(true);
        }

        stack.damageItem(1, player);
        world.playSoundAtEntity(player, "bowtweaks:small_bow_shoot", 1.0F,
                1.0F / (this.itemRand.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);

        if (creative) {
            arrow.canBePickedUp = 2;
        } else {
            player.inventory.consumeInventoryItem(ModItems.itemArrowWooden);
            arrow.canBePickedUp = 1;
        }

        if (!world.isRemote) {
            world.spawnEntityInWorld(arrow);
        }
    }
}
