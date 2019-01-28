package com.jeekrs.MineRobot.blockevent;

import com.jeekrs.MineRobot.util.LogUtil;

public class NodeProcessor extends Thread {
    public BlockEventNode eventNode = null;
    public long timeLimit = -1;

    public void setNode(BlockEventNode node) {
        eventNode = node;
    }

    @Override
    public void run() {

    }
    public boolean work() {
        try {
            long begin = System.currentTimeMillis();
            LogUtil.showMessage("Begin: " + eventNode);
            if (eventNode.checkStart()) {
                while (!eventNode.checkFinish()) {
                    eventNode.work();
                    Thread.sleep(10);
                    if (timeLimit > 0 && System.currentTimeMillis() - begin > timeLimit) {
                        LogUtil.showMessage("Error: " + eventNode);
                        return false;
                    }
                }
                eventNode.finish();
                LogUtil.showMessage("Done: " + eventNode);
            }
        } catch (InterruptedException ignore) {
        }
        return true;
    }

}
