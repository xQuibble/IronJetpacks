package com.blakebr0.ironjetpacks.mixins;

import net.minecraft.recipe.ShapedRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ShapedRecipe.class)
public interface ShapedRecipeAccessor {
    @Accessor
    String getGroup();
}
