package com.jeekrs.MineRobot.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketClickWindow;

import java.util.Objects;

public class InventoryUtil {

	public static int getItemCount(IInventory inv, String id) {
		int count = 0;
		for(int j = 0; j < inv.getSizeInventory(); j++)
        {
			ItemStack is = inv.getStackInSlot(j);
			is.getItem();
			String itemName = ItemUtil.getNameByItem(is.getItem());
			if (itemName.equals(id)) {
				count += is.getCount();
			}
		}
		
		return count;
	}

	/**
	 * @param container   your container
	 * @param slotId      your item stack index
	 * @param mouseButton 0 left button / 1 right / 2 middle
	 * @param type        see ClickType.java
	 * @param player      yourself
	 * @return
	 */
	public static ItemStack windowClick(Container container, int slotId, int mouseButton, ClickType type, EntityPlayer player) {
		short short1 = container.getNextTransactionID(player.inventory);
		ItemStack itemstack = container.slotClick(slotId, mouseButton, type, player);
		Objects.requireNonNull(Minecraft.getMinecraft().getConnection()).sendPacket(new CPacketClickWindow(container.windowId, slotId, mouseButton, type, itemstack, short1));
		return itemstack;
	}

	public static int changeItem(int index) {
		EntityPlayerSP player = Utils.getEntityPlayer();
		if (index < 9) {
			int backup = player.inventory.currentItem;
			player.inventory.currentItem = index;
			return backup;
		} else {
			windowClick(player.inventoryContainer, index, 0, ClickType.PICKUP, player);
			windowClick(player.inventoryContainer, player.inventory.currentItem + 36, 0, ClickType.PICKUP, player);
			windowClick(player.inventoryContainer, index, 0, ClickType.PICKUP, player);
			return index;

		}
	}
}
