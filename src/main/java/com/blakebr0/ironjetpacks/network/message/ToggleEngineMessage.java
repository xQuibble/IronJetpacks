package com.blakebr0.ironjetpacks.network.message;

import com.blakebr0.ironjetpacks.item.JetpackItem;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;

public class ToggleEngineMessage {
    public static ToggleEngineMessage read(PacketByteBuf buffer) {
        return new ToggleEngineMessage();
    }
    
    public static void write(ToggleEngineMessage message, PacketByteBuf buffer) {
        
    }
    
    public static void onMessage(ToggleEngineMessage message, PacketContext context) {
        context.getTaskQueue().execute(() -> {
            ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();
            if (player != null) {
                ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);
                Item item = stack.getItem();
                if (item instanceof JetpackItem) {
                    JetpackItem jetpack = (JetpackItem) item;
                    jetpack.toggleEngine(stack);
                }
            }
        });
    }
}
