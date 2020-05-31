package com.blakebr0.ironjetpacks.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ArmorTickable {
    void tickArmor(ItemStack stack, World world, PlayerEntity player);
}
