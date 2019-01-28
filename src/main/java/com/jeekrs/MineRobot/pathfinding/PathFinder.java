package com.jeekrs.MineRobot.pathfinding;

import com.jeekrs.MineRobot.util.KeyPresser;
import com.jeekrs.MineRobot.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

import java.util.*;
public class PathFinder extends Thread {
    private EntityPlayer player;
    public double find_eps = .5;
    public double walk_eps = .5;
    public BlockPos target;
    private BlockPos tempTarget;
    public boolean running;
    public long timeLimit = -1;
    private LinkedList<PathNode> planned;
    public boolean tip;
    private KeyPresser keyPresser = new KeyPresser();

    public PathFinder(EntityPlayer player, BlockPos target, boolean tip) {
        this.player = player;
        this.target = drop(target, true);
        this.tip = tip;
        planned = null;
        tempTarget = makeTempTarget();

    }

    private BlockPos drop(BlockPos pos, boolean raise) {
        BlockPos apos = pos;
        while (apos.getY() > 0 && BlockUtil.isPassable(player.world, apos))
            apos = apos.down();

        if (raise || pos.getY() > apos.getY())
            apos = apos.up();
        return apos;
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
        if (!BlockUtil.isStandible(Utils.getWorld(), pos))
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
        if (dis <= walk_eps) {
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
        PathNode nearest = new PathNode(PlayerUtil.getNowPos(), 0, getDist(target.getX(), target.getY(), target.getZ()), null);
        vis.add(nearest.pos);
        qu.add(nearest);

        while (!qu.isEmpty()) {
            PathNode node = qu.poll();

            if (node.guess < find_eps) {
                // accessible
                return node;
            }
            if (node.guess < nearest.guess)
                nearest = node;

            if (qu.size() > 2000)
                break;


            // todo support walking directly

            BlockPos pos = node.pos;
            tryAddNode(vis, qu, node, drop(pos.west(), false));
            tryAddNode(vis, qu, node, drop(pos.east(), false));
            tryAddNode(vis, qu, node, drop(pos.north(), false));
            tryAddNode(vis, qu, node, drop(pos.south(), false));

            boolean blockOnHead = !BlockUtil.isPassable(Utils.getWorld(), pos.up(2));
            if (!blockOnHead) {
                BlockPos upper = pos.up();
                tryAddNode(vis, qu, node, upper.west());
                tryAddNode(vis, qu, node, upper.east());
                tryAddNode(vis, qu, node, upper.north());
                tryAddNode(vis, qu, node, upper.south());
            }

        }
        return nearest;
    }

    private void tryAddNode(HashSet<BlockPos> vis, PriorityQueue<PathNode> qu, PathNode node, BlockPos pos) {
        if (!BlockUtil.isStandible(Utils.getWorld(), pos))
            return;
        boolean blockOnHead = !BlockUtil.isPassable(Utils.getWorld(), node.pos.up(2));
        if (blockOnHead && pos.getY() > node.pos.getY())
            return;
        if (!vis.contains(pos)) {
            vis.add(pos);
            qu.add(new PathNode(pos, node.walked + getVal(pos, node.pos), Math.sqrt(pos.distanceSq(target)), node));
        }
    }

    private double getVal(BlockPos newpos, BlockPos pos) {
        int ydis = Math.abs(newpos.getY() - pos.getY());
        return getDist(newpos.getX() - pos.getX(), newpos.getZ() - pos.getZ()) + (ydis < 4 ? 0 : ydis * 2);
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
