package com.blakebr0.ironjetpacks.sound;

import com.blakebr0.ironjetpacks.IronJetpacks;
import com.google.common.base.Suppliers;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class ModSounds {
    public static final Identifier JETPACK_ID = new Identifier(IronJetpacks.MOD_ID, "jetpack");
    public static final Supplier<SoundEvent> JETPACK = Suppliers.memoize(() -> new SoundEvent(JETPACK_ID));
    
    public static void register() {
        Registry.register(Registry.SOUND_EVENT, JETPACK_ID, JETPACK.get());
    }
}
