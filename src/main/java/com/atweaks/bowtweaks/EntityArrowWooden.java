package com.atweaks.bowtweaks;

import java.lang.reflect.Field;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityArrowWooden extends EntityArrow {

    public EntityArrowWooden(World world) {
        super(world);
    }

    public EntityArrowWooden(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityArrowWooden(World world, EntityLivingBase shooter, float velocity) {
        super(world, shooter, velocity);
        this.setDamage(ConfigHandler.woodenArrowDamage);
    }

    // inGround приватне в EntityArrow - читаємо рефлексією
    private boolean isInGroundSafe() {
        try {
            Field field = EntityArrow.class.getDeclaredField("inGround");
            field.setAccessible(true);
            return field.getBoolean(this);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {
        if (!this.worldObj.isRemote && isInGroundSafe() && this.arrowShake <= 0) {
            boolean canPickup = this.canBePickedUp == 1
                    || (this.canBePickedUp == 2 && player.capabilities.isCreativeMode);

            if (this.canBePickedUp == 1
                    && !player.inventory.addItemStackToInventory(new ItemStack(ModItems.itemArrowWooden, 1))) {
                canPickup = false;
            }

            if (canPickup) {
                this.playSound("random.pop", 0.2F,
                        ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                player.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }
}
