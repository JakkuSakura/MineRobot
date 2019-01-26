package com.jeekrs.MineRobot.processor;

import com.jeekrs.MineRobot.MineRobot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

import static com.jeekrs.MineRobot.util.Utils.*;

abstract public class Processor {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event){};

}
