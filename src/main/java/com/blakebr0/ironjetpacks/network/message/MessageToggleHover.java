package com.blakebr0.ironjetpacks.network.message;

import com.blakebr0.ironjetpacks.item.ItemJetpack;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageToggleHover implements IMessage {

	public MessageToggleHover() {
		
	}

	@Override
	public void fromBytes(ByteBuf buf) {

	}

	@Override
	public void toBytes(ByteBuf buf) {

	}

	public static class Handler implements IMessageHandler<MessageToggleHover, IMessage> {

		@Override
		public IMessage onMessage(MessageToggleHover message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(MessageToggleHover message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
			if (stack.getItem() instanceof ItemJetpack) {
				ItemJetpack jetpack = (ItemJetpack) stack.getItem();
				jetpack.toggleHover(stack);
			}
		}
	}
}
