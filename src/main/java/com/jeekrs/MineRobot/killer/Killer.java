package com.jeekrs.MineRobot.killer;

import com.jeekrs.MineRobot.util.EntityUtil;
import com.jeekrs.MineRobot.util.PlayerUtil;
import com.jeekrs.MineRobot.util.Utils;
import net.minecraft.entity.Entity;

import java.util.List;

public class Killer {
    public static void attackRound() {
        List<Entity> entities = EntityUtil.sortEntitiesByDistance(Utils.getEntityPlayer(), EntityUtil.entityList(Utils.getWorld()));
        entities.forEach(e -> {
            if (e != Utils.getEntityPlayer() && e.canBeAttackedWithItem() && PlayerUtil.testDistance(e)) {
//                Utils.log(e.toString());
                PlayerUtil.attackEntity(e);
            }
        });
    }
}
