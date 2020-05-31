package com.blakebr0.ironjetpacks.network;

import com.blakebr0.ironjetpacks.IronJetpacks;
import com.blakebr0.ironjetpacks.network.message.ToggleEngineMessage;
import com.blakebr0.ironjetpacks.network.message.ToggleHoverMessage;
import com.blakebr0.ironjetpacks.network.message.UpdateInputMessage;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

public class NetworkHandler {
    public static final Identifier PACKET_ID = new Identifier(IronJetpacks.MOD_ID, IronJetpacks.MOD_ID);
    private static int id = 0;
    
    public static void onCommonSetup() {
        ServerSidePacketRegistry.INSTANCE.register(PACKET_ID, (packetContext, packetByteBuf) -> {
            int id = packetByteBuf.readInt();
            switch (id) {
                case 0: {
                    ToggleHoverMessage.onMessage(ToggleHoverMessage.read(packetByteBuf), packetContext);
                    break;
                }
                case 1: {
                    UpdateInputMessage.onMessage(UpdateInputMessage.read(packetByteBuf), packetContext);
                    break;
                }
                case 2: {
                    ToggleEngineMessage.onMessage(ToggleEngineMessage.read(packetByteBuf), packetContext);
                    break;
                }
            }
        });
    }
    
    @Environment(EnvType.CLIENT)
    public static void sendToServer(ToggleHoverMessage message) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(0);
        ToggleHoverMessage.write(message, buf);
        ClientSidePacketRegistry.INSTANCE.sendToServer(PACKET_ID, buf);
    }
    
    @Environment(EnvType.CLIENT)
    public static void sendToServer(UpdateInputMessage message) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(1);
        UpdateInputMessage.write(message, buf);
        ClientSidePacketRegistry.INSTANCE.sendToServer(PACKET_ID, buf);
    }
    
    @Environment(EnvType.CLIENT)
    public static void sendToServer(ToggleEngineMessage message) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(2);
        ToggleEngineMessage.write(message, buf);
        ClientSidePacketRegistry.INSTANCE.sendToServer(PACKET_ID, buf);
    }
}
