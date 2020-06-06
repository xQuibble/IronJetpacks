package com.blakebr0.ironjetpacks.sound;

import com.blakebr0.ironjetpacks.IronJetpacks;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Lazy;
import net.minecraft.util.registry.Registry;

public class ModSounds {
    public static final Identifier JETPACK_ID = new Identifier(IronJetpacks.MOD_ID, "jetpack");
    public static final Lazy<SoundEvent> JETPACK = new Lazy<>(() -> new SoundEvent(JETPACK_ID));
    
    public static void register() {
        Registry.register(Registry.SOUND_EVENT, JETPACK_ID, JETPACK.get());
    }
}
