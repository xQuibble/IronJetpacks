package com.blakebr0.ironjetpacks.network;

import com.blakebr0.ironjetpacks.IronJetpacks;
import com.blakebr0.ironjetpacks.network.message.ToggleEngineMessage;
import com.blakebr0.ironjetpacks.network.message.ToggleHoverMessage;
import com.blakebr0.ironjetpacks.network.message.UpdateInputMessage;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class NetworkHandler {
    public static final ResourceLocation PACKET_ID = new ResourceLocation(IronJetpacks.MOD_ID, IronJetpacks.MOD_ID);
    private static int id = 0;
    
    public static void onCommonSetup() {
        ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (server, player, handler, buf, responseSender) -> {
            int id = buf.readInt();
            switch (id) {
                case 0 -> ToggleHoverMessage.onMessage(ToggleHoverMessage.read(buf), server, player);
                case 1 -> UpdateInputMessage.onMessage(UpdateInputMessage.read(buf), server, player);
                case 2 -> ToggleEngineMessage.onMessage(ToggleEngineMessage.read(buf), server, player);
            }
        });
    }
    
    @Environment(EnvType.CLIENT)
    public static void sendToServer(ToggleHoverMessage message) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(0);
        ToggleHoverMessage.write(message, buf);
        ClientPlayNetworking.send(PACKET_ID, buf);
    }
    
    @Environment(EnvType.CLIENT)
    public static void sendToServer(UpdateInputMessage message) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(1);
        UpdateInputMessage.write(message, buf);
        ClientPlayNetworking.send(PACKET_ID, buf);
    }
    
    @Environment(EnvType.CLIENT)
    public static void sendToServer(ToggleEngineMessage message) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(2);
        ToggleEngineMessage.write(message, buf);
        ClientPlayNetworking.send(PACKET_ID, buf);
    }
}
