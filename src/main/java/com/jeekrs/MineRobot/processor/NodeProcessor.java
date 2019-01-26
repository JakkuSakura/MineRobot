package com.jeekrs.MineRobot.processor;

import com.jeekrs.MineRobot.MineRobot;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

import static com.jeekrs.MineRobot.util.Utils.*;

public class NodeProcessor extends Processor {
    public BlockEventNode eventNode = null;

    @Override
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (eventNode == null)
            return;

        if (eventNode.world != Minecraft.getMinecraft().world)
            return;

        if (!eventNode.started && !eventNode.checkStart()) {
            eventNode.finished = true;
            eventNode.successed = false;
            eventNode = null;
            return;
        }

        eventNode.started = true;
        eventNode.work();

        if (eventNode.checkFinish()) {
            eventNode.finished = true;
            eventNode.successed = true;
            eventNode.finish();
        }
    }

    public void applyBlockEvent(BlockEventNode node) {
        // todo a bug here
        // to test
        eventNode = node;
        try {
            showMessage("Begin:" + node);
            while (!node.finished) {
                Thread.sleep(10);
            }
            showMessage("Done:" + node);
        } catch (InterruptedException ignore) {
        }
    }

}
