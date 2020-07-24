package com.blakebr0.ironjetpacks.crafting;

import com.blakebr0.ironjetpacks.IronJetpacks;
import com.blakebr0.ironjetpacks.config.ModConfigs;
import com.blakebr0.ironjetpacks.crafting.recipe.JetpackUpgradeRecipe;
import com.blakebr0.ironjetpacks.item.ModItems;
import com.blakebr0.ironjetpacks.registry.Jetpack;
import com.blakebr0.ironjetpacks.registry.JetpackRegistry;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

import java.util.Map;
import java.util.function.BiConsumer;

public class JetpackDynamicRecipeManager {
    public static void appendRecipes(BiConsumer<Identifier, Recipe<?>> appender) {
        JetpackRegistry.getInstance().getAllJetpacks().forEach(jetpack -> {
            ShapedRecipe cell = makeCellRecipe(jetpack);
            ShapedRecipe thruster = makeThrusterRecipe(jetpack);
            ShapedRecipe capacitor = makeCapacitorRecipe(jetpack);
            ShapedRecipe jetpackSelf = makeJetpackRecipe(jetpack);
            JetpackUpgradeRecipe jetpackUpgrade = makeJetpackUpgradeRecipe(jetpack);
            if (cell != null)
                appender.accept(cell.getId(), cell);
            if (thruster != null)
                appender.accept(thruster.getId(), thruster);
            if (capacitor != null)
                appender.accept(capacitor.getId(), capacitor);
            if (jetpackSelf != null)
                appender.accept(jetpackSelf.getId(), jetpackSelf);
            if (jetpackUpgrade != null)
                appender.accept(jetpackUpgrade.getId(), jetpackUpgrade);
        });
    }
    
    private static ShapedRecipe makeCellRecipe(Jetpack jetpack) {
        if (!ModConfigs.get().recipe.enableCellRecipes)
            return null;
        
        JetpackRegistry jetpacks = JetpackRegistry.getInstance();
        
        Ingredient material = jetpack.getCraftingMaterial();
        Item coilItem = jetpacks.getCoilForTier(jetpack.tier);
        if (material == Ingredient.EMPTY || coilItem == null)
            return null;
        
        Ingredient coil = Ingredient.ofItems(coilItem);
        Ingredient redstone = Ingredient.ofItems(Items.REDSTONE);
        DefaultedList<Ingredient> inputs = DefaultedList.copyOf(Ingredient.EMPTY,
                Ingredient.EMPTY, redstone, Ingredient.EMPTY,
                material, coil, material,
                Ingredient.EMPTY, redstone, Ingredient.EMPTY
        );
        
        Identifier name = new Identifier(IronJetpacks.MOD_ID, jetpack.name + "_cell");
        ItemStack output = new ItemStack(jetpack.cell);
        
        return new ShapedRecipe(name, "iron-jetpacks:cells", 3, 3, inputs, output);
    }
    
    private static ShapedRecipe makeThrusterRecipe(Jetpack jetpack) {
        if (!ModConfigs.get().recipe.enableThrusterRecipes)
            return null;
        
        JetpackRegistry jetpacks = JetpackRegistry.getInstance();
        
        Ingredient material = jetpack.getCraftingMaterial();
        Item coilItem = jetpacks.getCoilForTier(jetpack.tier);
        if (material == Ingredient.EMPTY || coilItem == null)
            return null;
        
        Ingredient coil = Ingredient.ofItems(coilItem);
        Ingredient cell = Ingredient.ofItems(jetpack.cell);
        Ingredient furnace = Ingredient.ofItems(Blocks.FURNACE);
        DefaultedList<Ingredient> inputs = DefaultedList.copyOf(Ingredient.EMPTY,
                material, coil, material,
                coil, cell, coil,
                material, furnace, material
        );
        
        Identifier name = new Identifier(IronJetpacks.MOD_ID, jetpack.name + "_thruster");
        ItemStack output = new ItemStack(jetpack.thruster);
        
        return new ShapedRecipe(name, "iron-jetpacks:thrusters", 3, 3, inputs, output);
    }
    
    private static ShapedRecipe makeCapacitorRecipe(Jetpack jetpack) {
        if (!ModConfigs.get().recipe.enableCapacitorRecipes)
            return null;
        
        Ingredient material = jetpack.getCraftingMaterial();
        if (material == Ingredient.EMPTY)
            return null;
        
        Ingredient cell = Ingredient.ofItems(jetpack.cell);
        DefaultedList<Ingredient> inputs = DefaultedList.copyOf(Ingredient.EMPTY,
                material, cell, material,
                material, cell, material,
                material, cell, material
        );
        
        Identifier name = new Identifier(IronJetpacks.MOD_ID, jetpack.name + "_capacitor");
        ItemStack output = new ItemStack(jetpack.capacitor);
        
        return new ShapedRecipe(name, "iron-jetpacks:capacitors", 3, 3, inputs, output);
    }
    
    private static ShapedRecipe makeJetpackRecipe(Jetpack jetpack) {
        if (!ModConfigs.get().recipe.enableJetpackRecipes)
            return null;
        
        JetpackRegistry jetpacks = JetpackRegistry.getInstance();
        if (jetpack.tier != jetpacks.getLowestTier())
            return null;
        
        Ingredient material = jetpack.getCraftingMaterial();
        if (material == Ingredient.EMPTY)
            return null;
        
        Ingredient capacitor = Ingredient.ofItems(jetpack.capacitor);
        Ingredient thruster = Ingredient.ofItems(jetpack.thruster);
        Ingredient strap = Ingredient.ofItems(ModItems.STRAP.get());
        DefaultedList<Ingredient> inputs = DefaultedList.copyOf(Ingredient.EMPTY,
                material, capacitor, material,
                material, strap, material,
                thruster, Ingredient.EMPTY, thruster
        );
        
        Identifier name = new Identifier(IronJetpacks.MOD_ID, jetpack.name + "_jetpack");
        ItemStack output = new ItemStack(jetpack.item);
        
        return new ShapedRecipe(name, "iron-jetpacks:jetpacks", 3, 3, inputs, output);
    }
    
    private static JetpackUpgradeRecipe makeJetpackUpgradeRecipe(Jetpack jetpack) {
        if (!ModConfigs.get().recipe.enableJetpackRecipes)
            return null;
        
        JetpackRegistry jetpacks = JetpackRegistry.getInstance();
        if (jetpack.tier == jetpacks.getLowestTier())
            return null;
        
        Ingredient material = jetpack.getCraftingMaterial();
        if (material == Ingredient.EMPTY)
            return null;
        
        Ingredient capacitor = Ingredient.ofItems(jetpack.capacitor);
        Ingredient thruster = Ingredient.ofItems(jetpack.thruster);
        Ingredient jetpackTier = Ingredient.ofItems(ModRecipeSerializers.ALL_JETPACKS.stream()
                .filter(item -> item.getJetpack().tier == jetpack.tier - 1)
                .toArray(ItemConvertible[]::new));
        DefaultedList<Ingredient> inputs = DefaultedList.copyOf(Ingredient.EMPTY,
                material, capacitor, material,
                material, jetpackTier, material,
                thruster, Ingredient.EMPTY, thruster
        );
        
        Identifier name = new Identifier(IronJetpacks.MOD_ID, jetpack.name + "_jetpack");
        ItemStack output = new ItemStack(jetpack.item);
        
        return new JetpackUpgradeRecipe(name, "iron-jetpacks:jetpacks", 3, 3, inputs, output);
    }
}
