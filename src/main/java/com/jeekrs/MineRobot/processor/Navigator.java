package com.jeekrs.MineRobot.processor;

import com.jeekrs.MineRobot.MineRobot;
import com.jeekrs.MineRobot.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Navigator extends Processor {
    public World world;
    public BlockPos target;
    private Runnable callback;

    /**
     * @param player the player
     * @param pos the target pos
     * @return arrived
     */
    public static boolean walkTo(EntityPlayerSP player, BlockPos pos)
    {
        double disXZ = getDist(player.posX - (pos.getX() + 0.5), player.posZ - (pos.getZ() + 0.5));
        if(disXZ < 0.6)
        {
            MineRobot.INSTANCE.keyPresser.releaseKey();
            return true;
        }
        lookAt(player, pos);
        MineRobot.INSTANCE.keyPresser.pressKey(Minecraft.getMinecraft().gameSettings.keyBindForward);
        return false;
    }

    public static void lookAt(EntityPlayer player, BlockPos pos) {
        if (pos == null || player == null)
            return;
        double delX = player.posX - (pos.getX() + 0.5);
        double delZ = player.posZ - (pos.getZ() + 0.5);
        double yaw = calcYaw(delX, delZ);
        player.rotationYaw = (float) yaw;
        double delY = (player.posY + player.getEyeHeight()) - (pos.getY() + 0.5);
        double dist = getDist(delX, delZ);
        player.rotationPitch = (float) (MathHelper.atan2(delY, dist) / Math.PI * 180);
    }

    public static double getDist(double dx, double dz) {
        return Math.sqrt(dx * dx + dz * dz);
    }

    public static double getDist(double dx, double dy, double dz) {
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * calc yaw of two delta
     *
     * @param deltaX origin's x position subtracts destination's x position
     * @param deltaZ origin's z position subtracts destination's z position
     * @return the Yaw when looking at destination
     */
    public static double calcYaw(double deltaX, double deltaZ) {
        deltaZ = -deltaZ;
        double rad = MathHelper.atan2(deltaX, deltaZ);
        return rad / Math.PI * 180;
    }

    public void setTarget(World world, BlockPos target) {
        this.world = world;
        this.target = target;
    }

    public void setTarget(World world, BlockPos target, Runnable callback) {
        this.world = world;
        this.target = target;
        this.callback = callback;
    }

    @Override
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (target != null) {
            if (world != null && world == Minecraft.getMinecraft().world) {
                EntityPlayerSP player = Utils.getEntityPlayer();
                if (walkTo(player, target)) {
                    if (callback != null)
                        callback.run();
                    callback = null;
                    target = null;
                }
            }

        }
    }
}
