package com.blakebr0.ironjetpacks.handler;

import com.blakebr0.ironjetpacks.IronJetpacks;
import com.blakebr0.ironjetpacks.item.JetpackItem;
import com.blakebr0.ironjetpacks.lib.ModTooltips;
import com.blakebr0.ironjetpacks.network.NetworkHandler;
import com.blakebr0.ironjetpacks.network.message.ToggleEngineMessage;
import com.blakebr0.ironjetpacks.network.message.ToggleHoverMessage;
import com.blakebr0.ironjetpacks.network.message.UpdateInputMessage;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class KeyBindingsHandler {
    private static FabricKeyBinding keyEngine;
    private static FabricKeyBinding keyHover;
    private static FabricKeyBinding keyDescend;
    
    private static boolean up = false;
    private static boolean down = false;
    private static boolean forwards = false;
    private static boolean backwards = false;
    private static boolean left = false;
    private static boolean right = false;
    
    public static void onClientSetup() {
        keyEngine = create("engine", GLFW.GLFW_KEY_V, IronJetpacks.NAME);
        keyHover = create("hover", GLFW.GLFW_KEY_G, IronJetpacks.NAME);
        keyDescend = create("descend", InputUtil.UNKNOWN_KEYCODE.getKeyCode(), IronJetpacks.NAME);
        
        KeyBindingRegistry.INSTANCE.addCategory(IronJetpacks.NAME);
        KeyBindingRegistry.INSTANCE.register(keyEngine);
        KeyBindingRegistry.INSTANCE.register(keyHover);
        KeyBindingRegistry.INSTANCE.register(keyDescend);
    }
    
    private static FabricKeyBinding create(String id, int key, String category) {
        return FabricKeyBinding.Builder.create(new Identifier(IronJetpacks.MOD_ID, id), InputUtil.Type.KEYSYM, key, category).build();
    }
    
    public static void onClientTick(MinecraftClient client) {
        handleInputs(client);
        updateInputs(client);
    }
    
    private static void handleInputs(MinecraftClient client) {
        PlayerEntity player = client.player;
        if (player == null)
            return;
        
        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
        Item item = chest.getItem();
        
        if (item instanceof JetpackItem) {
            JetpackItem jetpack = (JetpackItem) item;
            
            while (keyEngine.wasPressed()) {
                NetworkHandler.sendToServer(new ToggleEngineMessage());
                boolean on = !jetpack.isEngineOn(chest);
                Text state = on ? ModTooltips.ON.color(Formatting.GREEN) : ModTooltips.OFF.color(Formatting.RED);
                player.addMessage(ModTooltips.TOGGLE_ENGINE.args(state), true);
            }
            
            while (keyHover.wasPressed()) {
                NetworkHandler.sendToServer(new ToggleHoverMessage());
                boolean on = !jetpack.isHovering(chest);
                Text state = on ? ModTooltips.ON.color(Formatting.GREEN) : ModTooltips.OFF.color(Formatting.RED);
                player.addMessage(ModTooltips.TOGGLE_HOVER.args(state), true);
            }
        }
    }
    
    /*
     * Keyboard handling borrowed from Simply Jetpacks
     * https://github.com/Tomson124/SimplyJetpacks-2/blob/1.12/src/main/java/tonius/simplyjetpacks/client/handler/KeyTracker.java
     */
    public static void updateInputs(MinecraftClient client) {
        GameOptions settings = client.options;
        
        if (client.getNetworkHandler() == null)
            return;
        
        boolean upNow = settings.keyJump.isPressed();
        boolean downNow = keyDescend.isNotBound() ? settings.keySneak.isPressed() : keyDescend.isPressed();
        boolean forwardsNow = settings.keyForward.isPressed();
        boolean backwardsNow = settings.keyBack.isPressed();
        boolean leftNow = settings.keyLeft.isPressed();
        boolean rightNow = settings.keyRight.isPressed();
        
        if (upNow != up || downNow != down || forwardsNow != forwards || backwardsNow != backwards || leftNow != left || rightNow != right) {
            up = upNow;
            down = downNow;
            forwards = forwardsNow;
            backwards = backwardsNow;
            left = leftNow;
            right = rightNow;
            
            NetworkHandler.sendToServer(new UpdateInputMessage(upNow, downNow, forwardsNow, backwardsNow, leftNow, rightNow));
            InputHandler.update(client.player, upNow, downNow, forwardsNow, backwardsNow, leftNow, rightNow);
        }
    }
}
