package com.blakebr0.ironjetpacks.crafting;

import com.blakebr0.ironjetpacks.IronJetpacks;
import com.blakebr0.ironjetpacks.crafting.recipe.JetpackUpgradeRecipe;
import com.blakebr0.ironjetpacks.item.JetpackItem;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Lazy;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class ModRecipeSerializers {
    public static final List<JetpackItem> ALL_JETPACKS = new ArrayList<>();
    public static final Lazy<RecipeSerializer<JetpackUpgradeRecipe>> CRAFTING_JETPACK_UPGRADE = new Lazy<>(JetpackUpgradeRecipe.Serializer::new);
    
    public static void register() {
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(IronJetpacks.MOD_ID, "crafting_jetpack_upgrade"), CRAFTING_JETPACK_UPGRADE.get());
    }
    
    public static void onCommonSetup() {
        List<JetpackItem> jetpacks = new ArrayList<>();
        Registry.ITEM.stream().filter(i -> i instanceof JetpackItem).forEach(i -> jetpacks.add((JetpackItem) i));
        ALL_JETPACKS.addAll(jetpacks);
    }
}
