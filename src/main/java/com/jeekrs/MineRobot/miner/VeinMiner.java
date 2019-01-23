package com.jeekrs.MineRobot.miner;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class VeinMiner {
    private static final Vec3i FIND_AREA = new Vec3i(1, 1, 1);
    private final Block origin;
    private final World world;
    private final VeinMode veinMode;
    public final List<BlockPos> result;

    private int digged;
    private final int limit;

    public VeinMiner(final BlockPos origin, final World world, final int limit, final VeinMode veinMode) {
        this.origin = world.getBlockState(origin).getBlock();
        this.world = world;
        this.limit = limit;
        this.veinMode = veinMode;
        this.result = new ArrayList<>();

        this.veinMine(origin);
    }

    private void veinMine(final BlockPos in) {
        for (BlockPos pos : BlockPos.getAllInBox(in.subtract(FIND_AREA), in.add(FIND_AREA))) {
            Block block = this.world.getBlockState(pos).getBlock();

            if (block == this.origin && !this.result.contains(pos)) {

                this.digged++;
                this.result.add(pos);

                if (this.digged < this.limit) {
                    this.veinMine(pos);
                }
            }
        }
    }
}
