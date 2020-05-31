package com.blakebr0.ironjetpacks.handler;

import com.blakebr0.ironjetpacks.item.Colored;
import com.blakebr0.ironjetpacks.registry.Jetpack;
import com.blakebr0.ironjetpacks.registry.JetpackRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.ItemConvertible;

import java.util.ArrayList;
import java.util.List;

public class ColorHandler {
    private static final List<ItemConvertible> COLORED_ITEMS = new ArrayList<>();
    
    public static void onClientSetup() {
        JetpackRegistry registry = JetpackRegistry.getInstance();
        
        if (registry.isErrored())
            return;
        
        for (Jetpack jetpack : registry.getAllJetpacks()) {
            COLORED_ITEMS.add(jetpack.item);
            COLORED_ITEMS.add(jetpack.cell);
            COLORED_ITEMS.add(jetpack.thruster);
            COLORED_ITEMS.add(jetpack.capacitor);
        }
        
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            Colored item = (Colored) stack.getItem();
            return item.getColorTint(tintIndex);
        }, COLORED_ITEMS.toArray(new ItemConvertible[0]));
    }
}
