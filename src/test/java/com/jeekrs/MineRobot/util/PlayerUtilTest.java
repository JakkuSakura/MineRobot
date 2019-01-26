package com.jeekrs.MineRobot.util;

import com.jeekrs.MineRobot.processor.Navigator;
import org.junit.Assert;
import org.junit.Test;

public class PlayerUtilTest {

    @Test
    public void myArcTan() {
        Assert.assertEquals(Navigator.calcYaw(10, -10), 45, 1);
        Assert.assertEquals(Navigator.calcYaw(10, 10), 135, 1);
        Assert.assertEquals(Navigator.calcYaw(-10, 10), -135, 1);
        Assert.assertEquals(Navigator.calcYaw(-10, -10), -45, 1);

    }
}