package com.jeekrs.MineRobot.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUtil {
    public static boolean isToolEffective(final ItemStack itemStack, final IBlockState state) {
        if (ItemUtil.isEmpty(itemStack)) {
            return false;
        }

        return itemStack.getItem().getToolClasses(itemStack).stream().anyMatch(type -> type.equals(state.getBlock().getHarvestTool(state)));
    }

    public static boolean isAir(Block parBlock) {
        Material mat = parBlock.getDefaultState().getMaterial();
        if (mat == Material.AIR) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEqual(Block parBlock, Block parBlock2) {
        return parBlock == parBlock2;
    }


    public static Block getBlockByName(String name) {
        try {
            return (Block) Block.REGISTRY.getObject(new ResourceLocation(name));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getNameByBlock(Block item) {
        return Block.REGISTRY.getNameForObject(item).toString();
    }

    public static boolean isFenceLike(Block block) {
        return block instanceof BlockFence || block == Blocks.COBBLESTONE_WALL;
    }

    public static boolean isPressurePlate(Block block) {
        return block == Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE || block == Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE || block == Blocks.STONE_PRESSURE_PLATE || block == Blocks.WOODEN_PRESSURE_PLATE;
    }

    public static boolean checkExists(World world, BlockPos pos) {
        return !world.isAirBlock(pos);
    }

    public static boolean isPassable(World world, BlockPos pos) {
        // it's strange that it do not need it
        // maybe other mods need it
        return world.getBlockState(pos).getBlock().isPassable(world, pos);
    }
    public static boolean isStandible(World world, BlockPos pos)
    {
        return isPassable(world, pos.up()) && isPassable(world, pos) && !isPassable(world, pos.down());
    }

    public static boolean checkExistsOre(World world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        return OredictUtil.isOre(block);
    }
    public static int getLightValue(World world, BlockPos pos) {
        return world.getBlockState(pos).getLightValue(world, pos);
    }
}
