package com.jeekrs.MineRobot.miner;

import com.jeekrs.MineRobot.util.PlayerUtil;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;

import java.util.LinkedList;

public class AutoMine {
    /**s
     * @param pos where you are
     * @param dis the distance (sub 1) of two lines
     * @param length how long is a line
     * @param num how far you wanna dig
     * @return
     */
    public static LinkedList<BlockPos> makeList(BlockPos pos, int dis, int length, int num) {
        BlockPos face_front = PlayerUtil.getFaceDire();
        BlockPos face_left = face_front.rotate(Rotation.COUNTERCLOCKWISE_90);
        BlockPos face_right = face_front.rotate(Rotation.CLOCKWISE_90);
        BlockPos nowpos = pos;
        LinkedList<BlockPos> qu = new LinkedList<>();

        for (int i = 0; i < num; ++i) {
            addLine(dis, nowpos, face_front, qu);
            nowpos = nowpos.add(new BlockPos(face_front.getX() * dis, face_front.getY() * dis, face_front.getZ() * dis));
            addLine(length, nowpos, face_front, qu);
            addLine(length, nowpos, face_left, qu);
            addLine(length, nowpos, face_right, qu);

        }
        return qu;
    }

    public static void addLine(int cnt, BlockPos base, BlockPos fix, LinkedList<BlockPos> qu) {
        for (int j = 1; j <= cnt; ++j) {
            qu.add(base.add(new BlockPos(fix.getX() * j, fix.getY() * j, fix.getZ() * j)));
        }
    }
}
