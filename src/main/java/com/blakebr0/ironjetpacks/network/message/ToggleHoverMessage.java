package com.blakebr0.ironjetpacks.network.message;

import com.blakebr0.ironjetpacks.item.JetpackItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class ToggleHoverMessage {
    public static ToggleHoverMessage read(PacketByteBuf buffer) {
        return new ToggleHoverMessage();
    }
    
    public static void write(ToggleHoverMessage message, PacketByteBuf buffer) {
        
    }
    
    public static void onMessage(ToggleHoverMessage message, MinecraftServer server, ServerPlayerEntity player) {
        server.execute(() -> {
            if (player != null) {
                ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);
                Item item = stack.getItem();
                if (item instanceof JetpackItem jetpack) {
                    jetpack.toggleHover(stack);
                }
            }
        });
    }
}
