package com.blakebr0.ironjetpacks.sound;

import com.blakebr0.ironjetpacks.IronJetpacks;
import com.google.common.base.Suppliers;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {
    public static final ResourceLocation JETPACK_ID = new ResourceLocation(IronJetpacks.MOD_ID, "jetpack");
    public static final Supplier<SoundEvent> JETPACK = Suppliers.memoize(() -> new SoundEvent(JETPACK_ID));
    
    public static void register() {
        Registry.register(Registry.SOUND_EVENT, JETPACK_ID, JETPACK.get());
    }
}
