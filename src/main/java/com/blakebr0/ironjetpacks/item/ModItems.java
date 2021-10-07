package com.blakebr0.ironjetpacks.item;

import com.blakebr0.ironjetpacks.IronJetpacks;
import com.blakebr0.ironjetpacks.config.ModJetpacks;
import com.blakebr0.ironjetpacks.registry.Jetpack;
import com.blakebr0.ironjetpacks.registry.JetpackRegistry;
import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Map;
import java.util.function.Supplier;

import static com.blakebr0.ironjetpacks.IronJetpacks.ITEM_GROUP;

public class ModItems {
    public static final Map<Identifier, Supplier<Item>> ENTRIES = Maps.newHashMap();
    
    public static final Supplier<Item> STRAP = register("strap");
    public static final Supplier<Item> BASIC_COIL = register("basic_coil");
    public static final Supplier<Item> ADVANCED_COIL = register("advanced_coil");
    public static final Supplier<Item> ELITE_COIL = register("elite_coil");
    public static final Supplier<Item> ULTIMATE_COIL = register("ultimate_coil");
    public static final Supplier<Item> EXPERT_COIL = register("expert_coil");
    
    public static void register() {
        Registry<Item> registry = Registry.ITEM;
        JetpackRegistry jetpacks = JetpackRegistry.getInstance();
        
        ENTRIES.forEach((id, item) -> Registry.register(registry, id, item.get()));
        
        ModJetpacks.loadJsons();
        
        // Energy Cells
        for (Jetpack jetpack : jetpacks.getAllJetpacks()) {
            ComponentItem item = new ComponentItem(jetpack, "cell", new Item.Settings().group(ITEM_GROUP));
            jetpack.setCellItem(item);
            Registry.register(registry, new Identifier(IronJetpacks.MOD_ID, jetpack.name + "_cell"), item);
        }
        
        // Thrusters
        for (Jetpack jetpack : jetpacks.getAllJetpacks()) {
            ComponentItem item = new ComponentItem(jetpack, "thruster", new Item.Settings().group(ITEM_GROUP));
            jetpack.setThrusterItem(item);
            Registry.register(registry, new Identifier(IronJetpacks.MOD_ID, jetpack.name + "_thruster"), item);
        }
        
        // Capacitors
        for (Jetpack jetpack : jetpacks.getAllJetpacks()) {
            ComponentItem item = new ComponentItem(jetpack, "capacitor", new Item.Settings().group(ITEM_GROUP));
            jetpack.setCapacitorItem(item);
            Registry.register(registry, new Identifier(IronJetpacks.MOD_ID, jetpack.name + "_capacitor"), item);
        }
        
        // Jetpacks
        for (Jetpack jetpack : jetpacks.getAllJetpacks()) {
            Registry.register(registry, new Identifier(IronJetpacks.MOD_ID, jetpack.name + "_jetpack"), jetpack.item);
        }
    }
    
    private static Supplier<Item> register(String name) {
        return register(name, Suppliers.memoize(() -> new Item(new Item.Settings().group(ITEM_GROUP))));
    }
    
    private static Supplier<Item> register(String name, Supplier<Item> item) {
        Identifier loc = new Identifier(IronJetpacks.MOD_ID, name);
        ENTRIES.put(loc, item);
        return item;
    }
}
