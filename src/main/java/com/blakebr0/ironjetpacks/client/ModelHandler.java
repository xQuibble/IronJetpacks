package com.blakebr0.ironjetpacks.client;

import com.blakebr0.ironjetpacks.IronJetpacks;
import com.blakebr0.ironjetpacks.registry.JetpackRegistry;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
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
            out.accept(new ModelIdentifier(new Identifier(IronJetpacks.MOD_ID, "cell"), "inventory"));
            out.accept(new ModelIdentifier(new Identifier(IronJetpacks.MOD_ID, "capacitor"), "inventory"));
            out.accept(new ModelIdentifier(new Identifier(IronJetpacks.MOD_ID, "thruster"), "inventory"));
            out.accept(new ModelIdentifier(new Identifier(IronJetpacks.MOD_ID, "jetpack"), "inventory"));
        });
        Identifier cell = new Identifier(IronJetpacks.MOD_ID, "item/cell");
        Identifier capacitor = new Identifier(IronJetpacks.MOD_ID, "item/capacitor");
        Identifier thruster = new Identifier(IronJetpacks.MOD_ID, "item/thruster");
        Identifier jetpack = new Identifier(IronJetpacks.MOD_ID, "item/jetpack");
        Map<ModelIdentifier, UnbakedModel> modelMap = Maps.newHashMap();
        JetpackRegistry.getInstance().getAllJetpacks().forEach(pack -> {
            Identifier cellLocation = Registry.ITEM.getId(pack.cell);
            if (cellLocation != null) {
                ModelIdentifier location = new ModelIdentifier(cellLocation, "inventory");
                provideModel(modelMap, location, cell);
            }
            
            Identifier capacitorLocation = Registry.ITEM.getId(pack.capacitor);
            if (capacitorLocation != null) {
                ModelIdentifier location = new ModelIdentifier(capacitorLocation, "inventory");
                provideModel(modelMap, location, capacitor);
            }
            
            Identifier thrusterLocation = Registry.ITEM.getId(pack.thruster);
            if (thrusterLocation != null) {
                ModelIdentifier location = new ModelIdentifier(thrusterLocation, "inventory");
                provideModel(modelMap, location, thruster);
            }
            
            Identifier jetpackLocation = Registry.ITEM.getId(pack.item);
            if (jetpackLocation != null) {
                ModelIdentifier location = new ModelIdentifier(jetpackLocation, "inventory");
                provideModel(modelMap, location, jetpack);
            }
        });
        ModelLoadingRegistry.INSTANCE.registerVariantProvider(resourceManager -> (modelIdentifier, modelProviderContext) -> {
            return modelMap.get(modelIdentifier);
        });
    }
    
    private static void provideModel(Map<ModelIdentifier, UnbakedModel> modelMap, ModelIdentifier modelIdentifier, Identifier redirectedId) {
        modelMap.put(modelIdentifier, new UnbakedModel() {
            @Override
            public Collection<Identifier> getModelDependencies() {
                return Collections.emptyList();
            }
            
            @Override
            public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
                return Collections.emptyList();
            }
            
            @Override
            public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
                return loader.bake(redirectedId, rotationContainer);
            }
        });
    }
}
