package com.jeekrs.MineRobot.util;

import net.minecraft.client.settings.KeyBinding;

import java.lang.reflect.Field;
import java.util.TreeSet;

/**
 * Usage: use as daemon or just call work() when you need it
 */
public class KeyPresser extends Thread {
    private TreeSet<KeyBinding> pressed = new TreeSet<>();
    private boolean running;
    static private Field pressedField;
    static {
        try {
            pressedField = KeyBinding.class.getDeclaredField("pressed");
            pressedField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void work() {
        try {
            for (KeyBinding e : pressed) {
                pressedField.set(e, true);
            }
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            running = true;
            while (running) {
                work();
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            running = true;
        }
    }


    public void pressKey(KeyBinding binding) {
        pressed.add(binding);
    }

    public void terminate() {
        running = false;
        clear();
    }

    public void clear() {
        pressed.forEach(this::releaseKey);
    }

    public void releaseKey(KeyBinding binding) {
        if (binding == null)
            return;
        pressed.remove(binding);
        try {
            pressedField.set(binding, false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}