package com.jeekrs.MineRobot.listener;

import com.jeekrs.MineRobot.processor.BlockDestroyNode;
import com.jeekrs.MineRobot.MineRobot;
import com.jeekrs.MineRobot.processor.BlockEventNode;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

import static com.jeekrs.MineRobot.util.Utils.showMessage;

public class Recorder {
    final public List<BlockEventNode> queue = new LinkedList<>();
    static private final int MAX_BLOCK_LIMIT = 100;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerRightClick(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getEntityPlayer().equals(Minecraft.getMinecraft().player))
            return;
        // only process local world
        if (!event.getWorld().isRemote)
            return;

        // todo check the tool user hold
        BlockDestroyNode node = new BlockDestroyNode(event.getWorld(), event.getPos(), 100);
        if (addNode(node))
            showMessage("Added one");
    }

    public void clear() {
        queue.clear();
    }

    public void show() {
        StringBuilder sb = new StringBuilder();
        for (BlockEventNode e : queue) {
            sb.append(e);
            sb.append("\n");
        }
        showMessage("Robot: \n" + sb);
    }

    public boolean addNode(BlockEventNode node) {
        if (queue.isEmpty()) {
            queue.add(node);
            return true;
        } else {
            BlockEventNode last = queue.get(queue.size() - 1);
            if (!node.equals(last)) {
                queue.add(node);
                return true;
            }
        }

        if (queue.size() > MAX_BLOCK_LIMIT)
            queue.remove(0);
        return false;
    }


}

