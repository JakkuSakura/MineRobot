package com.jeekrs.MineRobot.processor;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

import static com.jeekrs.MineRobot.util.Utils.*;

public class Processor {

    final public List<BlockEventNode> execQueue = new LinkedList<>();


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onServerTick(TickEvent.WorldTickEvent event) {
        if (execQueue.isEmpty()) {
            return;
        }

        BlockEventNode node = execQueue.get(0);
        if(!event.world.equals(node.world))
            return;

        if (!node.checkStart()) {
            execQueue.remove(node);
            node.finished = true;
            return;
        }

        node.started = true;
        node.work();

        if (node.checkFinish()) {
            execQueue.remove(node);
            node.finished = true;
            node.finish();
        }
    }

    public void clear() {
        execQueue.clear();
    }

    public void addNode(BlockEventNode node) {
        execQueue.add(node);
    }
}
