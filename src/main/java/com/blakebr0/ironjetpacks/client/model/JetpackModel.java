package com.blakebr0.ironjetpacks.client.model;

import com.blakebr0.ironjetpacks.item.JetpackItem;
import com.blakebr0.ironjetpacks.item.storage.ItemSlotStorage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import team.reborn.energy.api.EnergyStorage;

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
        super(newParts());
        this.jetpack = jetpack;
        
        this.torso.visible = true;
        this.rightArm.visible = false;
        this.leftArm.visible = false;
        this.head.visible = false;
        this.helmet.visible = false;
        this.rightLeg.visible = false;
        this.leftLeg.visible = false;
        
        for (int i = 0; i < 6; i++) {
            energyBarLeft[i] = this.torso.getChild("energyBarLeft" + i);
            energyBarRight[i] = this.torso.getChild("energyBarRight" + i);
        }
    }
    
    private static ModelPart newParts() {
        ModelData data = getModelData(Dilation.NONE, 0.0F);
        ModelPartData child = data.getRoot().getChild("body");
        child.addChild("middle", ModelPartBuilder.create()
                        .mirrored()
                        .uv(0, 54)
                        .cuboid(-2F, 5F, 3.6F, 4, 3, 2),
                ModelTransform.NONE);
        child.addChild("leftCanister", ModelPartBuilder.create()
                        .mirrored()
                        .uv(0, 32)
                        .cuboid(0.5F, 2F, 2.6F, 4, 7, 4),
                ModelTransform.NONE);
        child.addChild("rightCanister", ModelPartBuilder.create()
                        .mirrored()
                        .uv(17, 32)
                        .cuboid(-4.5F, 2F, 2.6F, 4, 7, 4),
                ModelTransform.NONE);
        child.addChild("leftTip1", ModelPartBuilder.create()
                        .mirrored()
                        .uv(0, 45)
                        .cuboid(1F, 0F, 3.1F, 3, 2, 3),
                ModelTransform.NONE);
        child.addChild("leftTip2", ModelPartBuilder.create()
                        .mirrored()
                        .uv(0, 50)
                        .cuboid(1.5F, -1F, 3.6F, 2, 1, 2),
                ModelTransform.NONE);
        child.addChild("rightTip1", ModelPartBuilder.create()
                        .mirrored()
                        .uv(17, 45)
                        .cuboid(-4F, 0F, 3.1F, 3, 2, 3),
                ModelTransform.NONE);
        child.addChild("rightTip2", ModelPartBuilder.create()
                        .mirrored()
                        .uv(17, 50)
                        .cuboid(-3.5F, -1F, 3.6F, 2, 1, 2),
                ModelTransform.NONE);
        child.addChild("leftExhaust1", ModelPartBuilder.create()
                        .mirrored()
                        .uv(35, 32)
                        .cuboid(1F, 9F, 3.1F, 3, 1, 3),
                ModelTransform.NONE);
        child.addChild("leftExhaust2", ModelPartBuilder.create()
                        .mirrored()
                        .uv(35, 37)
                        .cuboid(0.5F, 10F, 2.6F, 4, 3, 4),
                ModelTransform.NONE);
        child.addChild("rightExhaust1", ModelPartBuilder.create()
                        .mirrored()
                        .uv(48, 32)
                        .cuboid(-4F, 9F, 3.1F, 3, 1, 3),
                ModelTransform.NONE);
        child.addChild("rightExhaust2", ModelPartBuilder.create()
                        .mirrored()
                        .uv(35, 45)
                        .cuboid(-4.5F, 10F, 2.6F, 4, 3, 4),
                ModelTransform.NONE);
        
        for (int i = 0; i < 6; i++) {
            child.addChild("energyBarLeft" + i, ModelPartBuilder.create()
                            .uv(16 + (i * 4), 55)
                            .cuboid(2F, 3F, 5.8F, 1, 5, 1),
                    ModelTransform.NONE);
            child.addChild("energyBarRight" + i, ModelPartBuilder.create()
                            .uv(16 + (i * 4), 55)
                            .cuboid(-3F, 3F, 5.8F, 1, 5, 1),
                    ModelTransform.NONE);
        }
        
        return data.getRoot().createPart(64, 64);
    }
    
    @Override
    public void setAngles(LivingEntity entity, float f1, float f2, float f3, float netHeadYaw, float headPitch) {
        super.setAngles(entity, f1, f2, f3, netHeadYaw, headPitch);
        
        if (this.jetpack.getJetpack().creative) {
            this.resetEnergyBars();
            this.energyBarLeft[5].visible = true;
            this.energyBarRight[5].visible = true;
        } else {
            ItemSlotStorage storage = new ItemSlotStorage(entity, EquipmentSlot.CHEST);
            EnergyStorage energy = EnergyStorage.ITEM.find(storage.getStack(), ContainerItemContext.ofSingleSlot(storage));
            double stored = (double) energy.getAmount() / energy.getCapacity();
            
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
