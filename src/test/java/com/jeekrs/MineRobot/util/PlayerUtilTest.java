package com.jeekrs.MineRobot.util;

import com.jeekrs.MineRobot.pathfinding.PathFinder;
import org.junit.Assert;
import org.junit.Test;

public class PlayerUtilTest {

    @Test
    public void myArcTan() {
        Assert.assertEquals(PathFinder.calcYaw(10, -10), 45, 1);
        Assert.assertEquals(PathFinder.calcYaw(10, 10), 135, 1);
        Assert.assertEquals(PathFinder.calcYaw(-10, 10), -135, 1);
        Assert.assertEquals(PathFinder.calcYaw(-10, -10), -45, 1);

    }
}