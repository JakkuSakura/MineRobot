package com.jeekrs.MineRobot.processor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;

public class KeyPresser extends Processor{
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

    public void pressKey(KeyBinding binding) {
        pressed = true;
        if (nowKeybinding == binding) {
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

    public void update() {
        if (pressed)
            updatePressed();
    }

    public void releaseKey() {
        pressed = false;
        updatePressed();

    }

    @Override
    public void onServerTick(TickEvent.ServerTickEvent event) {
        update();
    }
}