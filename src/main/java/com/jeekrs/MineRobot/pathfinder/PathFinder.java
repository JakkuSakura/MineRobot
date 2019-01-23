package com.jeekrs.MineRobot.pathfinder;

import com.jeekrs.MineRobot.util.Utils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class PathFinder {

    private World world;
    private BlockPos to;
    private PriorityQueue<PathNode> qu;
    private Set<BlockPos> vis;

    public PathFinder(World world, BlockPos to) {
        this.world = world;
        this.to = to;
    }

    // todo
    public void walkTo(BlockPos pos) {

    }

    private boolean canStand(BlockPos pos) {
        boolean body = Utils.checkAccessible(world, pos.up(1));
        boolean legs = Utils.checkAccessible(world, pos);
        boolean ground = Utils.checkAccessible(world, pos.down(1));
        return body && legs && !ground;
    }

    private double eval(BlockPos pos, BlockPos to) {
        return canStand(pos) ? pos.distanceSq(to) : 1e10;
    }

    private void addOne(BlockPos pos, PathNode last) {
        if (vis.contains(pos))
            return;
        qu.add(new PathNode(pos, last.h + last.pos.distanceSq(pos), eval(pos, to), last));
        vis.add(to);
    }

    private void drop(BlockPos pos, PathNode last) {
        // todo keep down with health protect
        boolean body = Utils.checkAccessible(world, pos.up(1));
        boolean legs = Utils.checkAccessible(world, pos);
        boolean ground = Utils.checkAccessible(world, pos.down(1));
        if (body && legs && ground) {
            for (int y = pos.getY() - 1; y > 0; --y)
                if (!Utils.checkAccessible(world, new BlockPos(pos.getX(), y, pos.getZ()))) {
                    addOne(new BlockPos(pos.getX(), y + 1, pos.getZ()), last);
                }

        }
    }

    public List<PathNode> getPath(BlockPos from_pos, BlockPos to_pos) {
        qu = new PriorityQueue<>();
        vis = new HashSet<>();

        PathNode best = new PathNode(from_pos, 0, eval(from_pos, to_pos), null);
        qu.add(best);
        while (!qu.isEmpty()) {
            PathNode node = qu.poll();
            if (node.g < best.g)
                best = node;
            if (node.pos == to_pos) {
                best = node;
                qu.clear();
                break;
            }
            BlockPos up = node.pos.up();
            if (Utils.checkAccessible(world, node.pos.up(2))) {
                for (int i = -1; i <= 1; ++i)
                    for (int j = -1; j <= 1; ++j) {
                        if (i != 0 && j != 0) {
                            BlockPos pos = up.add(i, 0, j);
                            if (canStand(pos))
                                addOne(pos, node);
                            drop(pos, node);
                        }
                    }
            }

            BlockPos feet = node.pos;
            for (int i = -2; i <= 2; ++i)
                for (int j = -2; j <= 2; ++j) {
                    if (i != 0 && j != 0) {
                        BlockPos pos = feet.add(i, 0, j);
                        if (canStand(pos))
                            addOne(pos, node);
                        drop(pos, node);
                    }
                }

        }

        List<PathNode> lst = new LinkedList<>();
        PathNode node = best;
        while (node != null) {
            lst.add(0, node);
            node = node.last;
        }

        return lst;
    }

}

