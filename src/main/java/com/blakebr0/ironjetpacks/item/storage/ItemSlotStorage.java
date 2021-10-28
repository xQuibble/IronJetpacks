package com.blakebr0.ironjetpacks.item.storage;

import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class ItemSlotStorage extends SingleStackStorage {
    private LivingEntity entity;
    private EquipmentSlot slot;
    
    public ItemSlotStorage(LivingEntity entity, EquipmentSlot slot) {
        this.entity = entity;
        this.slot = slot;
    }
    
    @Override
    public ItemStack getStack() {
        return entity.getEquippedStack(slot);
    }
    
    @Override
    protected void setStack(ItemStack stack) {
        entity.equipStack(slot, stack);
    }
}
