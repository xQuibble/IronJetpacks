package com.blakebr0.ironjetpacks.mixins;

import com.blakebr0.ironjetpacks.crafting.JetpackDynamicRecipeManager;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

@Mixin(RecipeManager.class)
public class MixinRecipeManager {
    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At(value = "INVOKE", target = "Ljava/util/Map;entrySet()Ljava/util/Set;", ordinal = 1),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void postApply(Map<ResourceLocation, JsonObject> map, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci, Map<RecipeType<?>, ImmutableMap.Builder<ResourceLocation, Recipe<?>>> map2) {
        ImmutableMap.Builder<ResourceLocation, Recipe<?>> builder = map2.computeIfAbsent(RecipeType.CRAFTING, recipeType -> ImmutableMap.builder());
        JetpackDynamicRecipeManager.appendRecipes(builder::put);
    }
}
