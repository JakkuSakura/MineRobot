package com.jeekrs.MineRobot.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;

import net.minecraft.util.math.Vec3d;

public class PlayerUtil {

    public static boolean testDistance(BlockPos pos) {
        float distance = Utils.getPlayerController().getBlockReachDistance();
        return Utils.getEntityPlayer().getDistanceSqToCenter(pos.toMcPos()) <= distance * distance;
    }
    public static boolean testDistance(Entity pos) {
        float distance = Utils.getPlayerController().getBlockReachDistance();
        return Utils.getEntityPlayer().getDistanceSq(pos) <= distance * distance;
    }

    public static boolean attackEntity(Entity entity) {
        if (entity == null)
            return false;
        if (!entity.canBeAttackedWithItem())
            return false;

        if (testDistance(new BlockPos(entity.getPosition()))) {
            Utils.getPlayerController().attackEntity(Utils.getEntityPlayer(), entity);
            return true;
        } else
            return false;
    }

    public static BlockPos getFaceDire() {
        EnumFacing facing = Utils.getEntityPlayer().getHorizontalFacing();
        return new BlockPos(facing.getDirectionVec());

    }

    public static BlockPos getNowPos() {
        return new BlockPos(Utils.getEntityPlayer());
    }

    public static Vec3d getFloatPos() {
        return new Vec3d(Utils.getEntityPlayer().posX, Utils.getEntityPlayer().posY, Utils.getEntityPlayer().posZ);
    }
}
