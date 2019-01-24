package com.jeekrs.MineRobot.pathfinding;

import com.jeekrs.MineRobot.MineRobot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static CoroUtil.util.CoroUtilPath.tryMoveOneSteopToWardXYZLongDistPlayer;

class Daeman {
    public void updatePressed() {
        if (pressedField != null && nowKeybinding != null) {
            try {
                pressedField.set(nowKeybinding, pressed);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Field pressedField;
    public KeyBinding nowKeybinding;
    boolean pressed = false;

    void pressKey(KeyBinding binding) {
        pressed = true;
        if(nowKeybinding == binding)
        {
            updatePressed();
            return;
        }

        nowKeybinding = binding;
        try {
            pressedField = nowKeybinding.getClass().getDeclaredField("pressed");
            pressedField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    void releaseKey() {
        pressed = false;
        updatePressed();

    }
}

public class PlayerOperationAgent {
    static final public Daeman daeman = new Daeman();

    public static void test() {
        World world = Minecraft.getMinecraft().world;
        new Thread(() -> {
            try {
                daeman.pressKey(Minecraft.getMinecraft().gameSettings.keyBindForward);
                Thread.sleep(1000);
                daeman.releaseKey();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        daeman.updatePressed();
    }
}
