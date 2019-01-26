package com.jeekrs.MineRobot.blockevent;

import com.jeekrs.MineRobot.processor.Processor;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.jeekrs.MineRobot.util.Utils.*;

public class NodeProcessor extends Processor {
    public BlockEventNode eventNode = null;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    @Override
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (eventNode == null)
            return;

        if (eventNode.world != Minecraft.getMinecraft().world || !eventNode.started && !eventNode.checkStart()) {
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
            eventNode = null;
        }
    }

    public void apply(BlockEventNode node) {
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
