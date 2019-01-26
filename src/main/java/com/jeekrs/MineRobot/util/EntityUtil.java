package com.jeekrs.MineRobot.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.List;

interface ICheck {
    boolean check(Entity entity);
}

public class EntityUtil {
    public static List<Entity> sortEntitiesByDistance(EntityPlayer player, List<Entity> entities) {
        entities.sort(Comparator.comparingDouble(entity -> entity.getDistanceSq(player)));
        return entities;
    }

    public static List<Entity> entityList(World world) {
        return world.loadedEntityList;
    }

    public static Entity nearestEntity(EntityPlayer player, ICheck checker) {
        if (checker == null)
            checker = entity -> entity instanceof EntityLivingBase;

        World world = player.world;
        float closest = Float.MAX_VALUE;
        Entity thisOne = null;
        for (int i = 0; i < world.loadedEntityList.size(); i++) {
            Entity entity = world.loadedEntityList.get(i);
            if (player.getDistance(entity) < closest) {
                if (checker.check(entity) && entity != player) {
                    closest = entity.getDistance(player);
                    thisOne = entity;
                }
            }
        }
        return thisOne;
    }

    public static Entity nearestMob(EntityPlayer player) {
        return nearestEntity(player, e -> e instanceof EntityMob);
    }

    public static EntityPlayer nearestPlayer(EntityPlayer player) {
        return (EntityPlayer) nearestEntity(player, e -> e instanceof EntityPlayer);
    }
}
