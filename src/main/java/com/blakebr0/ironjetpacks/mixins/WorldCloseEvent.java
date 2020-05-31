package com.blakebr0.ironjetpacks.mixins;

import com.blakebr0.ironjetpacks.handler.InputHandler;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class WorldCloseEvent {
    @Inject(method = "stop", at = @At("RETURN"))
    private void onStop(boolean stopImmediately, CallbackInfo ci) {
        this.stop();
    }
    
    private void stop() {
        InputHandler.clear();
    }
}