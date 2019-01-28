package com.jeekrs.MineRobot.blockevent;

import net.minecraft.world.World;

public abstract class BlockEventNode {
    public final World world;
    public BlockEventNode(final World world) {
        this.world = world;
    }

    public abstract boolean checkStart();
    public abstract boolean checkFinish();
    public abstract void work();

    public abstract void finish();
}
