package com.blakebr0.ironjetpacks.client.model;

import com.blakebr0.ironjetpacks.item.JetpackItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import team.reborn.energy.EnergyHandler;

/*
 * This is a slightly modified version of the model from Simply Jetpacks
 * https://github.com/Tomson124/SimplyJetpacks-2/blob/1.12/src/main/java/tonius/simplyjetpacks/client/model/ModelJetpack.java
 */
@Environment(EnvType.CLIENT)
public class JetpackModel extends BipedEntityModel<LivingEntity> {
    private final JetpackItem jetpack;
    private final ModelPart[] energyBarLeft = new ModelPart[6];
    private final ModelPart[] energyBarRight = new ModelPart[6];
    
    public JetpackModel(JetpackItem jetpack) {
        super(1.0F, 0, 64, 64);
        this.jetpack = jetpack;
        
        this.torso.visible = true;
        this.rightArm.visible = false;
        this.leftArm.visible = false;
        this.head.visible = false;
        this.helmet.visible = false;
        this.rightLeg.visible = false;
        this.leftLeg.visible = false;
        
        ModelPart middle = new ModelPart(this, 0, 54);
        middle.addCuboid(-2F, 5F, 3.6F, 4, 3, 2);
        middle.setPivot(0F, 0F, 0F);
        middle.mirror = true;
        this.setRotation(middle, 0F, 0F, 0F);
        
        ModelPart leftCanister = new ModelPart(this, 0, 32);
        leftCanister.addCuboid(0.5F, 2F, 2.6F, 4, 7, 4);
        leftCanister.setPivot(0F, 0F, 0F);
        leftCanister.mirror = true;
        this.setRotation(leftCanister, 0F, 0F, 0F);
        
        ModelPart rightCanister = new ModelPart(this, 17, 32);
        rightCanister.addCuboid(-4.5F, 2F, 2.6F, 4, 7, 4);
        rightCanister.setPivot(0F, 0F, 0F);
        rightCanister.mirror = true;
        this.setRotation(rightCanister, 0F, 0F, 0F);
        
        ModelPart leftTip1 = new ModelPart(this, 0, 45);
        leftTip1.addCuboid(1F, 0F, 3.1F, 3, 2, 3);
        leftTip1.setPivot(0F, 0F, 0F);
        leftTip1.mirror = true;
        this.setRotation(leftTip1, 0F, 0F, 0F);
        
        ModelPart leftTip2 = new ModelPart(this, 0, 50);
        leftTip2.addCuboid(1.5F, -1F, 3.6F, 2, 1, 2);
        leftTip2.setPivot(0F, 0F, 0F);
        leftTip2.mirror = true;
        this.setRotation(leftTip2, 0F, 0F, 0F);
        
        ModelPart rightTip1 = new ModelPart(this, 17, 45);
        rightTip1.addCuboid(-4F, 0F, 3.1F, 3, 2, 3);
        rightTip1.setPivot(0F, 0F, 0F);
        rightTip1.mirror = true;
        this.setRotation(rightTip1, 0F, 0F, 0F);
        
        ModelPart rightTip2 = new ModelPart(this, 17, 50);
        rightTip2.addCuboid(-3.5F, -1F, 3.6F, 2, 1, 2);
        rightTip2.setPivot(0F, 0F, 0F);
        rightTip2.mirror = true;
        this.setRotation(rightTip2, 0F, 0F, 0F);
        
        ModelPart leftExhaust1 = new ModelPart(this, 35, 32);
        leftExhaust1.addCuboid(1F, 9F, 3.1F, 3, 1, 3);
        leftExhaust1.setPivot(0F, 0F, 0F);
        leftExhaust1.mirror = true;
        this.setRotation(leftExhaust1, 0F, 0F, 0F);
        
        ModelPart leftExhaust2 = new ModelPart(this, 35, 37);
        leftExhaust2.addCuboid(0.5F, 10F, 2.6F, 4, 3, 4);
        leftExhaust2.setPivot(0F, 0F, 0F);
        leftExhaust2.mirror = true;
        this.setRotation(leftExhaust2, 0F, 0F, 0F);
        
        ModelPart rightExhaust1 = new ModelPart(this, 48, 32);
        rightExhaust1.addCuboid(-4F, 9F, 3.1F, 3, 1, 3);
        rightExhaust1.setPivot(0F, 0F, 0F);
        rightExhaust1.mirror = true;
        this.setRotation(rightExhaust1, 0F, 0F, 0F);
        
        ModelPart rightExhaust2 = new ModelPart(this, 35, 45);
        rightExhaust2.addCuboid(-4.5F, 10F, 2.6F, 4, 3, 4);
        rightExhaust2.setPivot(0F, 0F, 0F);
        rightExhaust2.mirror = true;
        this.setRotation(rightExhaust2, 0F, 0F, 0F);
        
        this.torso.addChild(middle);
        this.torso.addChild(leftCanister);
        this.torso.addChild(rightCanister);
        this.torso.addChild(leftTip1);
        this.torso.addChild(leftTip2);
        this.torso.addChild(rightTip1);
        this.torso.addChild(rightTip2);
        this.torso.addChild(leftExhaust1);
        this.torso.addChild(leftExhaust2);
        this.torso.addChild(rightExhaust1);
        this.torso.addChild(rightExhaust2);
        
        for (int i = 0; i < 6; i++) {
            ModelPart left = new ModelPart(this, 16 + (i * 4), 55);
            this.energyBarLeft[i] = left;
            left.addCuboid(2F, 3F, 5.8F, 1, 5, 1, 0F);
            left.visible = false;
            this.setRotation(left, 0F, 0F, 0F);
            this.torso.addChild(left);
            
            ModelPart right = new ModelPart(this, 16 + (i * 4), 55);
            this.energyBarRight[i] = right;
            right.addCuboid(-3F, 3F, 5.8F, 1, 5, 1, 0F);
            right.visible = false;
            this.setRotation(right, 0F, 0F, 0F);
            this.torso.addChild(right);
        }
    }
    
    @Override
    public void setAngles(LivingEntity entity, float f1, float f2, float f3, float netHeadYaw, float headPitch) {
        super.setAngles(entity, f1, f2, f3, netHeadYaw, headPitch);
        
        if (this.jetpack.getJetpack().creative) {
            this.resetEnergyBars();
            this.energyBarLeft[5].visible = true;
            this.energyBarRight[5].visible = true;
        } else {
            ItemStack chest = entity.getEquippedStack(EquipmentSlot.CHEST);
            EnergyHandler energy = this.jetpack.getEnergyStorage(chest);
            double stored = energy.getEnergy() / energy.getMaxStored();
            
            int state = 0;
            if (stored > 0.8) {
                state = 5;
            } else if (stored > 0.6) {
                state = 4;
            } else if (stored > 0.4) {
                state = 3;
            } else if (stored > 0.2) {
                state = 2;
            } else if (stored > 0) {
                state = 1;
            }
            
            this.resetEnergyBars();
            this.energyBarLeft[state].visible = true;
            this.energyBarRight[state].visible = true;
        }
    }
    
    private void setRotation(ModelPart model, float x, float y, float z) {
        model.pitch = x;
        model.yaw = y;
        model.roll = z;
    }
    
    private void resetEnergyBars() {
        for (int i = 0; i < 6; i++) {
            this.energyBarLeft[i].visible = false;
            this.energyBarRight[i].visible = false;
        }
    }
}
