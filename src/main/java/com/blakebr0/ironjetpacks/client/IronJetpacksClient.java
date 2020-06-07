package com.blakebr0.ironjetpacks.client;

import com.blakebr0.ironjetpacks.IronJetpacks;
import com.blakebr0.ironjetpacks.config.ModConfigs;
import com.blakebr0.ironjetpacks.handler.ColorHandler;
import com.blakebr0.ironjetpacks.handler.HudHandler;
import com.blakebr0.ironjetpacks.handler.JetpackClientHandler;
import com.blakebr0.ironjetpacks.handler.KeyBindingsHandler;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class IronJetpacksClient {
    public static void onInitializeClient() {
        ClientTickCallback.EVENT.register(KeyBindingsHandler::onClientTick);
        HudRenderCallback.EVENT.register(HudHandler::onRenderGameOverlay);
        ClientTickCallback.EVENT.register(JetpackClientHandler::onClientTick);
        
        KeyBindingsHandler.onClientSetup();
        ColorHandler.onClientSetup();
        ModelHandler.onClientSetup();
        
        AutoConfig.register(ModConfigs.Client.class, JanksonConfigSerializer::new);
        
        Supplier<MinecraftServer> oldServerSupplier = IronJetpacks.serverSupplier;
        IronJetpacks.serverSupplier = () -> {
            IntegratedServer server = MinecraftClient.getInstance().getServer();
            if (server != null) return server;
            return oldServerSupplier.get();
        };
    }
}
