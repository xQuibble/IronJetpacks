package com.blakebr0.ironjetpacks.compat.kubejs;

import com.blakebr0.ironjetpacks.crafting.JetpackDynamicRecipeManager;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class KubeJSCompat implements Consumer<Map<RecipeType<?>, Map<Identifier, Recipe<?>>>> {
    @Override
    public void accept(Map<RecipeType<?>, Map<Identifier, Recipe<?>>> recipeTypeMapMap) {
        Map<Identifier, Recipe<?>> builder = recipeTypeMapMap.computeIfAbsent(RecipeType.CRAFTING, recipeType -> new HashMap<>());
        JetpackDynamicRecipeManager.appendRecipes(builder::put);
    }
}
