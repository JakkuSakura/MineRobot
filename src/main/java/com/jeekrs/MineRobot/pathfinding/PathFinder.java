package com.jeekrs.MineRobot.pathfinding;

import com.jeekrs.MineRobot.processor.KeyPresser;
import com.jeekrs.MineRobot.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

import java.util.*;

public class PathFinder extends Thread {
    private static final double DIRECT_DIS = .5;
    private EntityPlayer player;
    public BlockPos target;
    private BlockPos tempTarget;
    public boolean running;
    public long timeLimit = -1;
    private LinkedList<PathNode> planned;
    public boolean tip;
    private KeyPresser keyPresser = new KeyPresser();

    public PathFinder(EntityPlayer player, BlockPos target, boolean tip) {
        this.player = player;
        this.target = drop(target);
        this.tip = tip;
        planned = null;
        tempTarget = makeTempTarget();
        timeLimit = -1;

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

        while (running && tempTarget != null) {
            if (timeLimit > 0 && System.currentTimeMillis() - begin > timeLimit)
                break;
            if (walkToward(tempTarget)) {
                tempTarget = makeTempTarget();
            }
            keyPresser.work();
            if (tempTarget != null)
                Utils.delay(50);
        }
        running = false;
        keyPresser.clear();
    }

    /**
     * @param player the player
     * @param pos    the target pos
     * @return arrived
     */
    public boolean walkToward(BlockPos pos) {
        if (pos == null)
            return true;
        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
        EntityPlayerSP player = Utils.getEntityPlayer();
        KeyBinding keyBindSneak = gameSettings.keyBindSneak;

        if (player.capabilities.isFlying) {
            keyPresser.pressKey(keyBindSneak);
            return false;
        } else {
            keyPresser.releaseKey(keyBindSneak);
        }

        double dis = getDist(player.posX - (pos.getX() + 0.5), player.posY - (pos.getY()), player.posZ - (pos.getZ() + 0.5));
        KeyBinding keyBindForward = gameSettings.keyBindForward;
        if (dis <= DIRECT_DIS) {
//            keyPresser.releaseKey(keyBindForward);
//            avoid unnecessary stop
            return true;
        }
//         it looks strange when walking
        lookAt(player, pos, player.capabilities.isFlying);
        keyPresser.pressKey(keyBindForward);
        return false;
    }

    public static void lookAt(EntityPlayer player, BlockPos pos, boolean pitch) {
        if (pos == null || player == null)
            return;
        double delX = player.posX - (pos.getX() + 0.5);
        double delZ = player.posZ - (pos.getZ() + 0.5);
        double yaw = calcYaw(delX, delZ);
        player.rotationYaw = (float) yaw;
        double delY = (player.posY + player.getEyeHeight()) - (pos.getY() + 0.5);
        double dist = getDist(delX, delZ);
        if (pitch) {
            player.rotationPitch = (float) (MathHelper.atan2(delY, dist) / Math.PI * 180);
            // todo this not work when player flying
        }
//        else
//            player.rotationPitch = 0;

    }

    public static double getDist(double dx, double dz) {
        return Math.sqrt(dx * dx + dz * dz);
    }

    public static double getDist(double dx, double dy, double dz) {
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }


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
            if (tip)
                LogUtil.showMessage("Planned " + planned.size() + " nodes");
        }

        PathNode a = planned.pollFirst();
        if (a == null)
            return null;
        if (tip)
            LogUtil.showMessage("Will go to " + a.pos.toString());
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
        if (!BlockUtil.isPassable(Utils.getWorld(), newpos.up()))
            return null;
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

    /**
     * calc yaw of two delta
     *
     * @param deltaX origin's x position subtracts destination's x position
     * @param deltaZ origin's z position subtracts destination's z position
     * @return the Yaw when looking at destination
     */
    public static double calcYaw(double deltaX, double deltaZ) {
        deltaZ = -deltaZ;
        double rad = MathHelper.atan2(deltaX, deltaZ);
        return rad / Math.PI * 180;
    }

}
