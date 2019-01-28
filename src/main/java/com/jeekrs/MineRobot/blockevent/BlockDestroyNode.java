package com.jeekrs.MineRobot.blockevent;

import com.jeekrs.MineRobot.util.BlockPos;
import com.jeekrs.MineRobot.util.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import static com.jeekrs.MineRobot.util.BlockUtil.checkExists;
import static net.minecraft.util.EnumFacing.DOWN;

public class BlockDestroyNode extends BlockEventNode {
    public final BlockPos pos;

    public BlockDestroyNode(final World world, final BlockPos pos) {
        super(world);
        this.pos = pos;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BlockDestroyNode) {
            BlockDestroyNode node = (BlockDestroyNode) obj;
            return world.equals(node.world) && pos.equals(node.pos);
        }
        return false;
    }

    @Override
    public boolean checkStart() {
        return checkExists(world, pos);
    }

    @Override
    public boolean checkFinish() {
        return !checkExists(world, pos);
    }

    @Override
    public void work() {
        if(!PlayerUtil.testDistance(pos))
            return;

        Minecraft.getMinecraft().playerController.onPlayerDamageBlock(pos.toMcPos(), DOWN);
        Minecraft.getMinecraft().effectRenderer.addBlockHitEffects(pos.toMcPos(), DOWN);
        Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);

    }


    @Override
    public void finish() {
        Minecraft.getMinecraft().playerController.resetBlockRemoving();
        Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);
    }

    @Override
    public String toString() {
        return "destroy:" + pos;
    }
}
