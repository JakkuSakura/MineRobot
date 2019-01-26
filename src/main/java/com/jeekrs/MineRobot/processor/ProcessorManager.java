package com.jeekrs.MineRobot.processor;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.print.attribute.standard.Sides;
import java.util.ArrayList;
import java.util.List;

public class ProcessorManager {
    public List<Processor> processors = new ArrayList<>();

    public void addProcessor(Processor processor) {
        processors.add(processor);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        for (Processor e : processors) {
            e.onServerTick(event);
        }
    }
}
