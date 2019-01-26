package com.jeekrs.MineRobot.pathfinding;

import net.minecraft.util.math.BlockPos;

public class PathNode implements Comparable<PathNode> {

    public BlockPos pos;
    public double walked;
    public double guess;
    public PathNode father;
    public PathNode(BlockPos pos, double walked, double guess,PathNode father)
    {
        this.pos = pos;
        this.walked = walked;
        this.guess = guess;
        this.father = father;
    }
    @Override
    public int compareTo(PathNode pathNode) {
        return Double.compare(walked + guess * 0.9, pathNode.walked + guess * 0.9);
    }
}
