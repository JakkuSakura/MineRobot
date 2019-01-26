package com.jeekrs.MineRobot.processor;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.jeekrs.MineRobot.util.BlockUtil.checkExists;
import static net.minecraft.util.EnumFacing.DOWN;

public class BlockDestroyNode extends BlockEventNode {
    public final BlockPos pos;
    public int left_ticks;

    public BlockDestroyNode(final World world, final BlockPos pos, int left_ticks) {
        super(world);
        this.pos = pos;
        this.left_ticks = left_ticks;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BlockDestroyNode) {
            BlockDestroyNode node = (BlockDestroyNode) obj;
            if (world.equals(node.world))
                if (pos.equals(node.pos))
                    return true;
            return false;
        }
        return false;
    }

    @Override
    public boolean checkStart() {
        return left_ticks > 0 && checkExists(world, pos);
    }

    @Override
    public boolean checkFinish() {
        return left_ticks <= 0 || checkExists(world, pos);
    }

    @Override
    public void work() {
        Minecraft.getMinecraft().playerController.onPlayerDamageBlock(pos, DOWN);

        Minecraft.getMinecraft().effectRenderer.addBlockHitEffects(pos, DOWN);
        Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);

        left_ticks -= 1;
    }

    @Override
    public void finish() {
        Minecraft.getMinecraft().playerController.resetBlockRemoving();
    }

    @Override
    public String toString() {
        return "destroy:" + pos;
    }
}
