package com.blakebr0.ironjetpacks.mixins;

import com.blakebr0.ironjetpacks.handler.InputHandler;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class PlayerDimensionEvent {
    @Inject(method = "changeDimension", at = @At("HEAD"))
    private void changed(DimensionType newDimension, CallbackInfoReturnable<Entity> cir) {
        this.change((ServerPlayerEntity) (Object) this, newDimension);
    }
    
    public void change(ServerPlayerEntity playerEntity, DimensionType type) {
        InputHandler.onChangeDimension(playerEntity, type);
    }
}