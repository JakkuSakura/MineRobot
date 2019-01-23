package com.jeekrs.MineRobot.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUtil {
    public static boolean isToolEffective(final ItemStack itemStack, final IBlockState state) {
        if (ItemUtil.isEmpty(itemStack)) {
            return false;
        }

        return itemStack.getItem().getToolClasses(itemStack).stream().anyMatch(type -> type.equals(state.getBlock().getHarvestTool(state)));
    }
}
