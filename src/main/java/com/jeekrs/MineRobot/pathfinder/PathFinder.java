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
        boolean body = Utils.checkExists(world, pos.up(1));
        boolean legs = Utils.checkExists(world, pos);
        boolean ground = Utils.checkExists(world, pos.down(1));
        return body && legs && ground;
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
        boolean body = Utils.checkExists(world, pos.up(1));
        boolean legs = Utils.checkExists(world, pos);
        boolean ground = Utils.checkExists(world, pos.down(1));
        if(body && legs && !ground)
        {

        }
    }

    public List<PathNode> getPath(BlockPos from_pos, BlockPos to_pos) {
        qu = new PriorityQueue<>();
        vis = new HashSet<>();

        qu.add(new PathNode(from_pos, 0, eval(from_pos, to_pos), null));
        while (!qu.isEmpty()) {
            PathNode node = qu.poll();

            BlockPos up = node.pos.up();
            addOne(up.north(), node);
            addOne(up.south(), node);
            addOne(up.west(), node);
            addOne(up.east(), node);

            addOne(node.pos.north(), node);
            addOne(node.pos.south(), node);
            addOne(node.pos.west(), node);
            addOne(node.pos.east(), node);
            // todo add more directions

        }

        List<PathNode> lst = new ArrayList<>();
        // todo to complete
        return lst;
    }

}

