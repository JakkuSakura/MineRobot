package com.jeekrs.MineRobot.pathfinding;

import com.jeekrs.MineRobot.MineRobot;
import com.jeekrs.MineRobot.processor.Processor;
import com.jeekrs.MineRobot.util.BlockUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;

public class PathFinder extends Processor {
    private static final double DIRECT_DIS = 4.5;
    private static final long TICK_LIMIT = 100;
    private EntityPlayer player;
    private BlockPos target;
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
        justifyTarget();
    }

    private void justifyTarget() {
        while (target.getY() > 0 && BlockUtil.isPassable(player.world, target))
            target.down();
        target.up();
    }

    @Override
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (target == null)
            return;
        ++tickcount;
        if (tickcount > TICK_LIMIT || player.getDistanceSqToCenter(target) < 2) {
            target = null;
            toUpdate = false;
            planned = null;
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
        if (player.getDistanceSqToCenter(target) < DIRECT_DIS)
            return target;
        if (planned == null) {
            PathNode pn = getPath();
            // flatten path
            planned = new LinkedList<>();
            while (pn != null) {
                planned.addFirst(pn);
                pn = pn.father;
            }
        }
        return Objects.requireNonNull(planned.pollFirst()).pos;

    }

    private PathNode getPath() {
        HashSet<BlockPos> vis = new HashSet<>();
        PriorityQueue<PathNode> qu = new PriorityQueue<>();
        qu.add(new PathNode(new BlockPos(player), 0, player.getDistanceSqToCenter(target), null));
        PathNode nearest = null;
        while (!qu.isEmpty()) {
            PathNode node = qu.poll();

            if (node.pos.distanceSq(target) < DIRECT_DIS)
                return node;
            nearest = node;

            tryAddNode(vis, qu, node, node.pos.west());
            tryAddNode(vis, qu, node, node.pos.east());
            tryAddNode(vis, qu, node, node.pos.north());
            tryAddNode(vis, qu, node, node.pos.south());
        }
        return nearest;
    }

    private void tryAddNode(HashSet<BlockPos> vis, PriorityQueue<PathNode> qu, PathNode node, BlockPos west) {
        checkAndAdd(new PathNode(west, node.walked + 1, west.distanceSq(target), node), qu, vis);
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
