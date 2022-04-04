package com.blakebr0.ironjetpacks.lib;

import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;

public final class Tooltip {
    private final String translationKey;
    
    public Tooltip(String translationKey) {
        this.translationKey = Objects.requireNonNull(translationKey);
    }
    
    public MutableComponent color(ChatFormatting formatting) {
        return new TranslatableComponent(translationKey).withStyle(formatting);
    }
    
    public MutableComponent args(Object... args) {
        return new TranslatableComponent(translationKey, args);
    }
    
    public String asFormattedString() {
        return new TranslatableComponent(translationKey).getString();
    }
    
    public MutableComponent build() {
        return new TranslatableComponent(translationKey);
    }
}
