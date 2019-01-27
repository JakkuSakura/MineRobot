package com.jeekrs.MineRobot.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.world.World;

public class Utils {
    public static void delay(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
