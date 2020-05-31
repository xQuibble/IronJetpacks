package com.blakebr0.ironjetpacks.util;

import net.minecraft.util.Formatting;

import java.text.DecimalFormat;

public final class UnitUtils {
    private static final DecimalFormat FORMAT = new DecimalFormat("#.##");
    
    public static String formatEnergy(double energy, Formatting formatting) {
        if (energy >= 1000000000) {
            return FORMAT.format(energy / 1000000000) + (formatting != null ? formatting.toString() : "") + "G E";
        } else if (energy >= 1000000) {
            return FORMAT.format(energy / 1000000) + (formatting != null ? formatting.toString() : "") + "M E";
        } else if (energy >= 1000) {
            return FORMAT.format(energy / 1000) + (formatting != null ? formatting.toString() : "") + "k E";
        } else {
            return FORMAT.format(energy) + (formatting != null ? formatting.toString() : "") + " E";
        }
    }
}
