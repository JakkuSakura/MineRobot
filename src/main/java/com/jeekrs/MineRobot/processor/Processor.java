package com.jeekrs.MineRobot.processor;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

abstract public class Processor {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event){};

}
