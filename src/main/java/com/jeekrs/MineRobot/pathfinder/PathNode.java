package com.jeekrs.MineRobot.pathfinder;

import com.sun.istack.internal.NotNull;
import net.minecraft.util.math.BlockPos;

public class PathNode implements Comparable<PathNode> {
    public BlockPos pos;
    public double h = 0;
    public double g = 0;
    public PathNode last;

    public PathNode(BlockPos pos, double h, double g, PathNode last) {

        this.pos = pos;
        this.h = h;
        this.g = g;
        this.last = last;
    }

    // h is the distance from where begin, and g is the future way
    @Override
    public int compareTo(PathNode o) {
        return Double.compare(h + g * 1.1, o.h + o.g * 1.1);
    }
}
