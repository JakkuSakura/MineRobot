package com.jeekrs.MineRobot.pathfinding;

import com.jeekrs.MineRobot.MineRobot;
import com.jeekrs.MineRobot.processor.Processor;
import com.jeekrs.MineRobot.util.BlockUtil;
import com.jeekrs.MineRobot.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;

public class PathFinder extends Processor {
    private static final double DIRECT_DIS = .8;
    private EntityPlayer player;
    public BlockPos target;
    public boolean running;
    private long tickcount;
    private BlockPos tempTarget;
    private boolean toUpdate;

    public void startPathfinding(EntityPlayer player, BlockPos target) {
        this.player = player;
        tickcount = 0;
        this.target = target;
        tempTarget = makeTempTarget();
        toUpdate = true;
        justify();
        running = true;
    }

    private void justify() {
        target = drop(target);
    }

    private BlockPos drop(BlockPos pos) {
        while (pos.getY() > 0 && BlockUtil.isPassable(player.world, pos))
            pos = pos.down();
        pos = pos.up();
        return pos;
    }

    @Override
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (target == null)
            return;
        if (!running)
            return;
        ++tickcount;
        if (player.getDistanceSqToCenter(target) < DIRECT_DIS * DIRECT_DIS) {
            target = null;
            toUpdate = false;
            planned = null;
            tempTarget = null;
            return;
        }

        if (toUpdate) {
            toUpdate = false;
            MineRobot.INSTANCE.navigator.setTarget(player.world, tempTarget, () -> {
                tempTarget = makeTempTarget();
                toUpdate = true;
            });
        }

    }


    private LinkedList<PathNode> planned;

    private BlockPos makeTempTarget() {
        if (target == null)
            return null;

        if (planned == null) {
            PathNode pn = getPath();
            // flatten path
            planned = new LinkedList<>();
            while (pn != null) {
                planned.addFirst(pn);
                pn = pn.father;
            }
        }

        PathNode a = planned.pollFirst();
        if (a == null)
            return null;
        return a.pos;

    }

    private PathNode getPath() {
        HashSet<BlockPos> vis = new HashSet<>();
        PriorityQueue<PathNode> qu = new PriorityQueue<>();
        PathNode nearest = tryAddNode(vis, qu, null, player.getPosition());

        while (!qu.isEmpty()) {
            PathNode node = qu.poll();

            if (node.guess < nearest.guess)
                nearest = node;

            if (node.guess < DIRECT_DIS)
                break;


            tryAddNode(vis, qu, node, node.pos.west());
            tryAddNode(vis, qu, node, node.pos.east());
            tryAddNode(vis, qu, node, node.pos.north());
            tryAddNode(vis, qu, node, node.pos.south());

            if(BlockUtil.isPassable(Utils.getWorld(), drop(node.pos.south())) && BlockUtil.isPassable(Utils.getWorld(), drop(node.pos.west())))
                tryAddNode(vis, qu, node, node.pos.south().west());
            if(BlockUtil.isPassable(Utils.getWorld(), drop(node.pos.south())) && BlockUtil.isPassable(Utils.getWorld(), drop(node.pos.east())))
                tryAddNode(vis, qu, node, node.pos.south().east());
            if(BlockUtil.isPassable(Utils.getWorld(), drop(node.pos.north())) && BlockUtil.isPassable(Utils.getWorld(), drop(node.pos.west())))
                tryAddNode(vis, qu, node, node.pos.north().west());
            if(BlockUtil.isPassable(Utils.getWorld(), drop(node.pos.north())) && BlockUtil.isPassable(Utils.getWorld(), drop(node.pos.east())))
                tryAddNode(vis, qu, node, node.pos.north().east());
        }
        return nearest;
    }

    private PathNode tryAddNode(HashSet<BlockPos> vis, PriorityQueue<PathNode> qu, PathNode node, BlockPos newpos) {
        newpos = drop(newpos);
        PathNode pathNode = new PathNode(newpos, node != null ? node.walked + 1 : 0, Math.sqrt(newpos.distanceSq(target)), node);
        checkAndAdd(pathNode, qu, vis);
        return pathNode;
    }

    private void checkAndAdd(PathNode node, Queue<PathNode> queue, Set<BlockPos> vis) {
        if (!BlockUtil.isStandible(player.world, node.pos))
            return;

        if (!vis.contains(node.pos)) {
            vis.add(node.pos);
            queue.add(node);
        }
    }

}
