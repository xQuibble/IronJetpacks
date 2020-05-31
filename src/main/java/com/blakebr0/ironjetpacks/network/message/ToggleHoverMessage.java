package com.blakebr0.ironjetpacks.network.message;

import com.blakebr0.ironjetpacks.item.JetpackItem;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;

public class ToggleHoverMessage {
    public static ToggleHoverMessage read(PacketByteBuf buffer) {
        return new ToggleHoverMessage();
    }
    
    public static void write(ToggleHoverMessage message, PacketByteBuf buffer) {
        
    }
    
    public static void onMessage(ToggleHoverMessage message, PacketContext context) {
        context.getTaskQueue().execute(() -> {
            ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();
            if (player != null) {
                ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);
                Item item = stack.getItem();
                if (item instanceof JetpackItem) {
                    JetpackItem jetpack = (JetpackItem) item;
                    jetpack.toggleHover(stack);
                }
            }
        });
    }
}
