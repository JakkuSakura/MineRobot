package com.jeekrs.MineRobot.util;

import com.jeekrs.MineRobot.MineRobot;
import com.jeekrs.MineRobot.processor.BlockDestroyNode;
import com.jeekrs.MineRobot.processor.BlockEventNode;
import com.jeekrs.MineRobot.processor.BlockPutNode;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class Utils {
    static public void log(String s) {
        MineRobot.LOGGER.info(s);
    }

    static public void showMessage(String s) {
        if (Minecraft.getMinecraft().player != null)
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString(s));
        else
            log(s);
    }

    static public boolean checkExists(World world, BlockPos pos) {
        return !world.isAirBlock(pos);
    }
    static public boolean checkAccessible(World world, BlockPos pos)
    {
        return !world.getBlockState(pos).getBlock().isCollidable();
    }

    static public boolean checkExistsOre(World world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        return OredictUtil.isOre(block);
    }

    static public void applyBlockEvent(BlockEventNode node) {
        MineRobot.INSTANCE.processor.clear();
        MineRobot.INSTANCE.processor.addNode(node);
        try {
            while (!node.finished) {
                Thread.sleep(10);
            }
        } catch (InterruptedException ignore) {
        }
    }

    static public void destoryBlock(World world, BlockPos pos) {
        BlockEventNode node = new BlockDestroyNode(world, pos, 100);
        applyBlockEvent(node);

    }

    static public void putBlock(World world, BlockPos pos, String block) {
        BlockEventNode node = new BlockPutNode(world, pos, block);
        applyBlockEvent(node);
    }

    static public World getWorld() {
        return Minecraft.getMinecraft().world;
    }

    static public EntityPlayer getEntityPlayer() {
        return Minecraft.getMinecraft().player;
    }

    static public Vec3d getFaceDire() {
        return getEntityPlayer().getLookVec();
    }

    static public BlockPos getNowPos() {
        return getEntityPlayer().getPosition();
    }

    static public Vec3d getFloatPos() {
        return new Vec3d(getEntityPlayer().posX, getEntityPlayer().posY, getEntityPlayer().posZ);
    }
}
