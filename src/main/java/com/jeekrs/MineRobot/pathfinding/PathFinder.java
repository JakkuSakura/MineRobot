package com.jeekrs.MineRobot.pathfinding;

import com.jeekrs.MineRobot.MineRobot;
import com.jeekrs.MineRobot.util.BlockUtil;
import com.jeekrs.MineRobot.util.PlayerUtil;
import com.jeekrs.MineRobot.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import java.util.*;

public class PathFinder extends Thread {
    private static final double DIRECT_DIS = .8;
    private EntityPlayer player;
    public BlockPos target;
    private BlockPos tempTarget;
    public boolean running;
    public long timeLimit = -1;

    public void setTarget(EntityPlayer player, BlockPos target) {
        this.player = player;
        this.target = target;
        planned = null;
        tempTarget = makeTempTarget();
        justify();
        timeLimit = -1;

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
    public void run() {
        running = true;
        long begin = System.currentTimeMillis();

        while (running) {
            if (timeLimit > 0 && System.currentTimeMillis() - begin > timeLimit)
                break;
            if (tempTarget == null || player.getDistanceSqToCenter(target) < DIRECT_DIS * DIRECT_DIS) {
                target = null;
                planned = null;
                tempTarget = null;
                MineRobot.INSTANCE.navigator.setTarget(null, null, null);
                break;
            }

            MineRobot.INSTANCE.navigator.setTarget(player.world, tempTarget, () -> {
                tempTarget = makeTempTarget();
            });
            Utils.delay(10);
        }
        MineRobot.INSTANCE.navigator.clean();
        running = false;

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
        PathNode nearest = tryAddNode(vis, qu, null, PlayerUtil.getNowPos());

        while (!qu.isEmpty()) {
            PathNode node = qu.poll();

            if (node.guess < nearest.guess)
                nearest = node;

            if (node.guess < DIRECT_DIS)
                break;

            if (qu.size() > 2000)
                break;


            tryAddNode(vis, qu, node, node.pos.west());
            tryAddNode(vis, qu, node, node.pos.east());
            tryAddNode(vis, qu, node, node.pos.north());
            tryAddNode(vis, qu, node, node.pos.south());

            if (BlockUtil.isPassable(Utils.getWorld(), drop(node.pos.south())) && BlockUtil.isPassable(Utils.getWorld(), drop(node.pos.west())))
                tryAddNode(vis, qu, node, node.pos.south().west());
            if (BlockUtil.isPassable(Utils.getWorld(), drop(node.pos.south())) && BlockUtil.isPassable(Utils.getWorld(), drop(node.pos.east())))
                tryAddNode(vis, qu, node, node.pos.south().east());
            if (BlockUtil.isPassable(Utils.getWorld(), drop(node.pos.north())) && BlockUtil.isPassable(Utils.getWorld(), drop(node.pos.west())))
                tryAddNode(vis, qu, node, node.pos.north().west());
            if (BlockUtil.isPassable(Utils.getWorld(), drop(node.pos.north())) && BlockUtil.isPassable(Utils.getWorld(), drop(node.pos.east())))
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
