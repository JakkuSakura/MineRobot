package com.jeekrs.MineRobot.util;

import com.jeekrs.MineRobot.MineRobot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class Utils {
    public static void delay(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void log(String s) {
        MineRobot.LOGGER.info(s);
    }

    public static void showMessage(String s) {
        //noinspection ConstantConditions
        if(Minecraft.getMinecraft() != null && Minecraft.getMinecraft().player != null)
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString(s));
        else
            log(s);
    }


    public static World getWorld() {
        return Minecraft.getMinecraft().world;
    }

    public static EntityPlayerSP getEntityPlayer() {
        return Minecraft.getMinecraft().player;
    }
    public static PlayerControllerMP getPlayerController() {
        return Minecraft.getMinecraft().playerController;
    }

}
