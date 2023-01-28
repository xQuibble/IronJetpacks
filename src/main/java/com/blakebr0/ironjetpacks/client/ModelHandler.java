package com.blakebr0.ironjetpacks.client;

import com.blakebr0.ironjetpacks.IronJetpacks;
import com.blakebr0.ironjetpacks.registry.JetpackRegistry;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class ModelHandler {
    private static final Logger LOGGER = LogManager.getLogger(IronJetpacks.NAME);
    
    public static void onClientSetup() {
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            out.accept(new ModelResourceLocation(new ResourceLocation(IronJetpacks.MOD_ID, "cell"), "inventory"));
            out.accept(new ModelResourceLocation(new ResourceLocation(IronJetpacks.MOD_ID, "capacitor"), "inventory"));
            out.accept(new ModelResourceLocation(new ResourceLocation(IronJetpacks.MOD_ID, "thruster"), "inventory"));
            out.accept(new ModelResourceLocation(new ResourceLocation(IronJetpacks.MOD_ID, "jetpack"), "inventory"));
        });
        ResourceLocation cell = new ResourceLocation(IronJetpacks.MOD_ID, "item/cell");
        ResourceLocation capacitor = new ResourceLocation(IronJetpacks.MOD_ID, "item/capacitor");
        ResourceLocation thruster = new ResourceLocation(IronJetpacks.MOD_ID, "item/thruster");
        ResourceLocation jetpack = new ResourceLocation(IronJetpacks.MOD_ID, "item/jetpack");
        Map<ModelResourceLocation, UnbakedModel> modelMap = Maps.newHashMap();
        JetpackRegistry.getInstance().getAllJetpacks().forEach(pack -> {
            ResourceLocation cellLocation = BuiltInRegistries.ITEM.getKey(pack.cell);
            if (cellLocation != null) {
                ModelResourceLocation location = new ModelResourceLocation(cellLocation, "inventory");
                provideModel(modelMap, location, cell);
            }
            
            ResourceLocation capacitorLocation = (BuiltInRegistries.ITEM).getKey(pack.capacitor);
            if (capacitorLocation != null) {
                ModelResourceLocation location = new ModelResourceLocation(capacitorLocation, "inventory");
                provideModel(modelMap, location, capacitor);
            }
            
            ResourceLocation thrusterLocation = (BuiltInRegistries.ITEM).getKey(pack.thruster);
            if (thrusterLocation != null) {
                ModelResourceLocation location = new ModelResourceLocation(thrusterLocation, "inventory");
                provideModel(modelMap, location, thruster);
            }
            
            ResourceLocation jetpackLocation = (BuiltInRegistries.ITEM).getKey(pack.item.get());
            if (jetpackLocation != null) {
                ModelResourceLocation location = new ModelResourceLocation(jetpackLocation, "inventory");
                provideModel(modelMap, location, jetpack);
            }
        });
        ModelLoadingRegistry.INSTANCE.registerVariantProvider(resourceManager -> (modelIdentifier, modelProviderContext) -> {
            return modelMap.get(modelIdentifier);
        });
    }
    
    private static void provideModel(Map<ModelResourceLocation, UnbakedModel> modelMap, ModelResourceLocation modelIdentifier, ResourceLocation redirectedId) {
        modelMap.put(modelIdentifier, new UnbakedModel() {
            @Override
            public Collection<ResourceLocation> getDependencies() {
                return Collections.emptyList();
            }

            @Override
            public void resolveParents(Function<ResourceLocation, UnbakedModel> function) {

            }

            @Nullable
            @Override
            public BakedModel bake(ModelBaker modelBaker, Function<Material, TextureAtlasSprite> function, ModelState modelState, ResourceLocation resourceLocation) {
                return modelBaker.bake(redirectedId, modelState);
            }


        });
    }
}
