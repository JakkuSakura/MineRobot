package com.jeekrs.MineRobot.processor;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.TreeSet;

public class KeyPresser extends Processor {
    private TreeSet<KeyBinding> pressed = new TreeSet<>();

    public KeyPresser() {
        try {
            pressedField = KeyBinding.class.getDeclaredField("pressed");
            pressedField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    @Override
    public void onServerTick(TickEvent.ServerTickEvent event) {
        try {
            for (KeyBinding e : pressed)
                pressedField.set(e, true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Field pressedField;

    public void pressKey(KeyBinding binding) {
        pressed.add(binding);
    }

    public void clear() {
        pressed.clear();
        KeyBinding.unPressAllKeys();
    }
    public void releaseKey(KeyBinding binding) {
        pressed.remove(binding);
        try {
            pressedField.set(binding, false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}