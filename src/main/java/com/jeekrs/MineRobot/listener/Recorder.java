package com.jeekrs.MineRobot.listener;

import CoroUtil.util.CoroUtilInventory;
import CoroUtil.util.CoroUtilItem;
import com.jeekrs.MineRobot.processor.BlockDestroyNode;
import com.jeekrs.MineRobot.MineRobot;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

import static com.jeekrs.MineRobot.util.Utils.getEntityPlayer;
import static com.jeekrs.MineRobot.util.Utils.showMessage;

public class Recorder {
    final public List<BlockPos> queue = new LinkedList<>();
    public BlockPos lastPos(int n)
    {
        if (queue.isEmpty())
            return null;
        return queue.get(queue.size() - n);
    }
    static private final int MAX_BLOCK_LIMIT = 100;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerRightClick(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getEntityPlayer().equals(Minecraft.getMinecraft().player))
            return;
        // only process local world
        if (!event.getWorld().isRemote)
            return;

        // when user holds compass
        if(!getEntityPlayer().getHeldItemMainhand().getUnlocalizedName().equals("item.compass"))
            return;

        if (addNode(event.getPos()))
            showMessage("Added one pos");
    }

    public void clear() {
        queue.clear();
    }

    public void show() {
        StringBuilder sb = new StringBuilder();
        for (BlockPos e : queue) {
            sb.append(e);
            sb.append("\n");
        }
        showMessage("Robot: \n" + sb);
    }

    public boolean addNode(BlockPos node) {
        if (queue.isEmpty()) {
            queue.add(node);
            return true;
        } else {
            BlockPos last = queue.get(queue.size() - 1);
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

