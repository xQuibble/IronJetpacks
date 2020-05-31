package com.blakebr0.ironjetpacks.client.util;

import com.blakebr0.ironjetpacks.config.ModConfigs;
import com.blakebr0.ironjetpacks.item.JetpackItem;
import com.blakebr0.ironjetpacks.lib.ModTooltips;
import com.blakebr0.ironjetpacks.util.UnitUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;
import team.reborn.energy.EnergyHandler;

public class HudHelper {
    public static HudPos getHudPos() {
        Window window = MinecraftClient.getInstance().getWindow();
        int xOffset = ModConfigs.getClient().hud.hudOffsetX;
        int yOffset = ModConfigs.getClient().hud.hudOffsetY;
        
        switch (ModConfigs.getClient().hud.hudPosition) {
            case 0:
                return new HudPos(10 + xOffset, 30 + yOffset, 0);
            case 1:
                return new HudPos(10 + xOffset, window.getScaledHeight() / 2 + yOffset, 0);
            case 2:
                return new HudPos(10 + xOffset, window.getScaledHeight() - 30 + yOffset, 0);
            case 3:
                return new HudPos(window.getScaledWidth() - 8 - xOffset, 30 + yOffset, 1);
            case 4:
                return new HudPos(window.getScaledWidth() - 8 - xOffset, window.getScaledHeight() / 2 + yOffset, 1);
            case 5:
                return new HudPos(window.getScaledWidth() - 8 - xOffset, window.getScaledHeight() - 30 + yOffset, 1);
        }
        
        return null;
    }
    
    public static int getEnergyBarScaled(JetpackItem jetpack, ItemStack stack) {
        if (jetpack.getJetpack().creative) return 156;
        EnergyHandler energy = jetpack.getEnergyStorage(stack);
        double i = energy.getEnergy();
        double j = energy.getMaxStored();
        return (int) (j != 0 && i != 0 ? (long) i * 156 / j : 0);
    }
    
    public static String getFuel(JetpackItem jetpack, ItemStack stack) {
        if (jetpack.getJetpack().creative) return ModTooltips.INFINITE.asFormattedString() + Formatting.GRAY + " E";
        double number = jetpack.getEnergyStorage(stack).getEnergy();
        return UnitUtils.formatEnergy(number, Formatting.GRAY);
    }
    
    public static String getOn(boolean on) {
        return on ? ModTooltips.ON.color(Formatting.GREEN).asFormattedString() : ModTooltips.OFF.color(Formatting.RED).asFormattedString();
    }
    
    public static class HudPos {
        public int x;
        public int y;
        public int side;
        
        public HudPos(int x, int y, int side) {
            this.x = x;
            this.y = y;
            this.side = side;
        }
    }
}
