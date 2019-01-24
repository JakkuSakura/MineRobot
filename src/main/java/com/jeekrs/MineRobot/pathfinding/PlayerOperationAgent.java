package com.jeekrs.MineRobot.pathfinding;

import com.jeekrs.MineRobot.MineRobot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static CoroUtil.util.CoroUtilPath.tryMoveOneSteopToWardXYZLongDistPlayer;

class Daeman extends Thread {
    public boolean running = false;

    @Override
    public void run() {
        running = true;

        try {
            while (running && Minecraft.getMinecraft().world != null) {
                updatePressed();
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    private void updatePressed()
    {
        if(pressedField != null && nowKeybinding != null)
        {
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
        nowKeybinding = binding;
        try {
            pressedField = nowKeybinding.getClass().getDeclaredField("pressed");
            pressedField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        pressed = true;
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
}
