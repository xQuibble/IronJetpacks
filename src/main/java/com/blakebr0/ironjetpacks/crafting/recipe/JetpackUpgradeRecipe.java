package com.blakebr0.ironjetpacks.crafting.recipe;

import com.blakebr0.ironjetpacks.crafting.ModRecipeSerializers;
import com.blakebr0.ironjetpacks.item.JetpackItem;
import com.blakebr0.ironjetpacks.mixins.ShapedRecipeAccessor;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class JetpackUpgradeRecipe extends ShapedRecipe {


    public JetpackUpgradeRecipe(ResourceLocation resourceLocation, String string, CraftingBookCategory craftingBookCategory, int i, int j, NonNullList<Ingredient> nonNullList, ItemStack itemStack) {
        super(resourceLocation, string, craftingBookCategory, i, j, nonNullList, itemStack);
    }

    @Override
    public ItemStack assemble(CraftingContainer inv) {
        ItemStack jetpack = inv.getItem(4);
        ItemStack result = this.getResultItem().copy();
        
        if (!jetpack.isEmpty() && jetpack.getItem() instanceof JetpackItem) {
            CompoundTag tag = jetpack.getTag();
            if (tag != null) {
                result.setTag(tag);
                return result;
            }
        }
        
        return result;
    }
    
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CRAFTING_JETPACK_UPGRADE.get();
    }
    
    public static class Serializer implements RecipeSerializer<JetpackUpgradeRecipe> {
        @Override
        public JetpackUpgradeRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ShapedRecipe recipe = RecipeSerializer.SHAPED_RECIPE.fromJson(recipeId, json);
            return new JetpackUpgradeRecipe(recipeId, ((ShapedRecipeAccessor) recipe).getGroup(), CraftingBookCategory.EQUIPMENT, recipe.getWidth(), recipe.getHeight(), recipe.getIngredients(), recipe.getResultItem());
        }
        
        @Override
        public JetpackUpgradeRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            int i = friendlyByteBuf.readVarInt();
            int j = friendlyByteBuf.readVarInt();
            String string = friendlyByteBuf.readUtf();
            CraftingBookCategory craftingBookCategory = (CraftingBookCategory)friendlyByteBuf.readEnum(CraftingBookCategory.class);
            NonNullList<Ingredient> nonNullList = NonNullList.withSize(i * j, Ingredient.EMPTY);

            for(int k = 0; k < nonNullList.size(); ++k) {
                nonNullList.set(k, Ingredient.fromNetwork(friendlyByteBuf));
            }

            ItemStack itemStack = friendlyByteBuf.readItem();
            return new JetpackUpgradeRecipe(resourceLocation, string, craftingBookCategory, i, j, nonNullList, itemStack);
        }
        
        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, JetpackUpgradeRecipe shapedRecipe) {
            friendlyByteBuf.writeVarInt(shapedRecipe.getWidth());
            friendlyByteBuf.writeVarInt(shapedRecipe.getHeight());
            friendlyByteBuf.writeUtf(shapedRecipe.getGroup());
            friendlyByteBuf.writeEnum(CraftingBookCategory.EQUIPMENT);
            var var3 = shapedRecipe.getIngredients().iterator();

            while(var3.hasNext()) {
                Ingredient ingredient = (Ingredient)var3.next();
                ingredient.toNetwork(friendlyByteBuf);
            }

            friendlyByteBuf.writeItem(shapedRecipe.getResultItem());
        }
    }
}
