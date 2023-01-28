package com.blakebr0.ironjetpacks.item;

import com.blakebr0.ironjetpacks.IronJetpacks;
import com.blakebr0.ironjetpacks.config.ModJetpacks;
import com.blakebr0.ironjetpacks.registry.Jetpack;
import com.blakebr0.ironjetpacks.registry.JetpackRegistry;
import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.function.Supplier;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import static com.blakebr0.ironjetpacks.IronJetpacks.ITEM_GROUP;
public class ModItems {
    public static final Map<ResourceLocation, Supplier<Item>> ENTRIES = Maps.newHashMap();
    
    public static final Supplier<Item> STRAP = register("strap");
    public static final Supplier<Item> BASIC_COIL = register("basic_coil");
    public static final Supplier<Item> ADVANCED_COIL = register("advanced_coil");
    public static final Supplier<Item> ELITE_COIL = register("elite_coil");
    public static final Supplier<Item> ULTIMATE_COIL = register("ultimate_coil");
    public static final Supplier<Item> EXPERT_COIL = register("expert_coil");
    
    public static void register() {
        var registry = BuiltInRegistries.ITEM;
        JetpackRegistry jetpacks = JetpackRegistry.getInstance();
        
        ENTRIES.forEach((id, item) -> {
            Registry.register(registry, id, item.get());
            ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register((content) -> {
                content.accept(item.get());
            });
        });
        
        ModJetpacks.loadJsons();
        
        // Energy Cells
        for (Jetpack jetpack : jetpacks.getAllJetpacks()) {
            ComponentItem item = new ComponentItem(jetpack, "cell", new Item.Properties());
            jetpack.setCellItem(item);
            Registry.register(registry, new ResourceLocation(IronJetpacks.MOD_ID, jetpack.name + "_cell"), item);
            ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register((content) -> {
                content.accept(item);
            });
        }
        
        // Thrusters
        for (Jetpack jetpack : jetpacks.getAllJetpacks()) {
            ComponentItem item = new ComponentItem(jetpack, "thruster", new Item.Properties());
            jetpack.setThrusterItem(item);
            Registry.register(registry, new ResourceLocation(IronJetpacks.MOD_ID, jetpack.name + "_thruster"), item);
            ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register((content) -> {
                content.accept(item);
            });
        }
        
        // Capacitors
        for (Jetpack jetpack : jetpacks.getAllJetpacks()) {
            ComponentItem item = new ComponentItem(jetpack, "capacitor", new Item.Properties());
            jetpack.setCapacitorItem(item);
            Registry.register(registry, new ResourceLocation(IronJetpacks.MOD_ID, jetpack.name + "_capacitor"), item);
            ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register((content) -> {
                content.accept(item);
            });
        }
        
        // Jetpacks
        for (Jetpack jetpack : jetpacks.getAllJetpacks()) {
            Registry.register(registry, new ResourceLocation(IronJetpacks.MOD_ID, jetpack.name + "_jetpack"), jetpack.item.get());
            ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register((content) -> {
                content.accept(jetpack.item.get());
            });
        }
    }
    
    private static Supplier<Item> register(String name) {
        return register(name, Suppliers.memoize(() -> new Item(new Item.Properties())));
    }
    
    private static Supplier<Item> register(String name, Supplier<Item> item) {
        ResourceLocation loc = new ResourceLocation(IronJetpacks.MOD_ID, name);
        ENTRIES.put(loc, item);
        return item;
    }
}
