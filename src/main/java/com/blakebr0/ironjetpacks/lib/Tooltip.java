package com.blakebr0.ironjetpacks.lib;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.Objects;

public final class Tooltip {
    private final String translationKey;
    
    public Tooltip(String translationKey) {
        this.translationKey = Objects.requireNonNull(translationKey);
    }
    
    public MutableText color(Formatting formatting) {
        return new TranslatableText(translationKey).formatted(formatting);
    }
    
    public MutableText args(Object... args) {
        return new TranslatableText(translationKey, args);
    }
    
    public String asFormattedString() {
        return new TranslatableText(translationKey).getString();
    }
    
    public MutableText build() {
        return new TranslatableText(translationKey);
    }
}
