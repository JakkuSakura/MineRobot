package com.jeekrs.MineRobot.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

import static net.minecraft.client.Minecraft.getMinecraft;

public class ItemUtil {
    public static boolean isEmpty(ItemStack itemStack) {
        return itemStack == null || itemStack.isEmpty();
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

    public static int findIndexInInventory(String name) {
        EntityPlayerSP player = Utils.getEntityPlayer();
        NonNullList<ItemStack> inventory = player.inventory.mainInventory;
        for (int i = 0; i < inventory.size(); ++i) {
            Item it = inventory.get(i).getItem();
            if (it.getUnlocalizedName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public static int changeItem(int index) {
        EntityPlayerSP player = Utils.getEntityPlayer();
        if (index < 9)
        {
            int backup = player.inventory.currentItem;
            player.inventory.currentItem = index;
            return backup;
        }
        else {
            ItemUtil.windowClick(player.inventoryContainer, index, 0, ClickType.PICKUP, player);
            ItemUtil.windowClick(player.inventoryContainer, player.inventory.currentItem + 36, 0, ClickType.PICKUP, player);
            ItemUtil.windowClick(player.inventoryContainer, index, 0, ClickType.PICKUP, player);
            return index;

        }
    }
}
