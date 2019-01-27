package com.jeekrs.MineRobot.util;

import com.jeekrs.MineRobot.MineRobot;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import org.apache.logging.log4j.Logger;

public class LogUtil {

    private static final Logger LOGGER = MineRobot.LOGGER;

    /**
     * For seldom used but important things to print out in production
     *
     * @param string
     */
    public static void log(String string) {
        LOGGER.info(string);

    }

    /**
     * For logging warnings/errors
     *
     * @param string
     */
    public static void err(String string) {
        LOGGER.error(string);

    }

    /**
     * For debugging things
     *
     * @param string
     */
    public static void dbg(String string) {
        LOGGER.info(string);

    }


    public static void showMessage(String s) {
        //noinspection ConstantConditions
        if(Minecraft.getMinecraft() != null && Minecraft.getMinecraft().player != null)
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString(s));
        else
            log(s);
    }
}
