package com.blakebr0.ironjetpacks.handler;

import com.blakebr0.ironjetpacks.IronJetpacks;
import com.blakebr0.ironjetpacks.client.util.HudHelper;
import com.blakebr0.ironjetpacks.client.util.HudHelper.HudPos;
import com.blakebr0.ironjetpacks.config.ModConfigs;
import com.blakebr0.ironjetpacks.item.JetpackItem;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class HudHandler {
    private static final Identifier HUD_TEXTURE = new Identifier(IronJetpacks.MOD_ID, "textures/gui/hud.png");
    
    public static void onRenderGameOverlay(float delta) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player != null) {
            if (ModConfigs.getClient().hud.enableHud && (ModConfigs.getClient().hud.showHudOverChat || !ModConfigs.getClient().hud.showHudOverChat && !(mc.currentScreen instanceof ChatScreen)) && !mc.options.hudHidden && !mc.options.debugEnabled) {
                ItemStack chest = mc.player.getEquippedStack(EquipmentSlot.CHEST);
                Item item = chest.getItem();
                if (!chest.isEmpty() && item instanceof JetpackItem) {
                    JetpackItem jetpack = (JetpackItem) item;
                    HudPos pos = HudHelper.getHudPos();
                    if (pos != null) {
                        int xPos = (int) (pos.x / 0.33) - 18;
                        int yPos = (int) (pos.y / 0.33) - 78;
                        
                        mc.getTextureManager().bindTexture(HUD_TEXTURE);
                        
                        RenderSystem.pushMatrix();
                        RenderSystem.scaled(0.33, 0.33, 1.0);
                        DrawableHelper.blit(xPos, yPos, 0, 0, 0, 28, 156, 256, 256);
                        int i2 = HudHelper.getEnergyBarScaled(jetpack, chest);
                        DrawableHelper.blit(xPos, 166 - i2 + yPos - 10, 0, 28, 156 - i2, 28, i2, 256, 256);
                        RenderSystem.popMatrix();
                        
                        String fuel = Formatting.GRAY + HudHelper.getFuel(jetpack, chest);
                        String engine = Formatting.GRAY + "E: " + HudHelper.getOn(jetpack.isEngineOn(chest));
                        String hover = Formatting.GRAY + "H: " + HudHelper.getOn(jetpack.isHovering(chest));
                        
                        if (pos.side == 1) {
                            mc.textRenderer.drawWithShadow(fuel, pos.x - 8 - mc.textRenderer.getStringWidth(fuel), pos.y - 21, 16383998);
                            mc.textRenderer.drawWithShadow(engine, pos.x - 8 - mc.textRenderer.getStringWidth(engine), pos.y + 4, 16383998);
                            mc.textRenderer.drawWithShadow(hover, pos.x - 8 - mc.textRenderer.getStringWidth(hover), pos.y + 14, 16383998);
                        } else {
                            mc.textRenderer.drawWithShadow(fuel, pos.x + 6, pos.y - 21, 16383998);
                            mc.textRenderer.drawWithShadow(engine, pos.x + 6, pos.y + 4, 16383998);
                            mc.textRenderer.drawWithShadow(hover, pos.x + 6, pos.y + 14, 16383998);
                        }
                        
                        mc.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_LOCATION);
                    }
                }
            }
        }
    }
}
