package com.blakebr0.ironjetpacks.client;

import com.blakebr0.ironjetpacks.IronJetpacks;
import com.blakebr0.ironjetpacks.registry.JetpackRegistry;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
            ResourceLocation cellLocation = Registry.ITEM.getKey(pack.cell);
            if (cellLocation != null) {
                ModelResourceLocation location = new ModelResourceLocation(cellLocation, "inventory");
                provideModel(modelMap, location, cell);
            }
            
            ResourceLocation capacitorLocation = Registry.ITEM.getKey(pack.capacitor);
            if (capacitorLocation != null) {
                ModelResourceLocation location = new ModelResourceLocation(capacitorLocation, "inventory");
                provideModel(modelMap, location, capacitor);
            }
            
            ResourceLocation thrusterLocation = Registry.ITEM.getKey(pack.thruster);
            if (thrusterLocation != null) {
                ModelResourceLocation location = new ModelResourceLocation(thrusterLocation, "inventory");
                provideModel(modelMap, location, thruster);
            }
            
            ResourceLocation jetpackLocation = Registry.ITEM.getKey(pack.item.get());
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
            public Collection<Material> getMaterials(Function<ResourceLocation, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
                return Collections.emptyList();
            }
            
            @Override
            public BakedModel bake(ModelBakery loader, Function<Material, TextureAtlasSprite> textureGetter, ModelState rotationContainer, ResourceLocation modelId) {
                return loader.bake(redirectedId, rotationContainer);
            }
        });
    }
}
