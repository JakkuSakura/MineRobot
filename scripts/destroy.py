from com.jeekrs.MineRobot.util import Utils
from com.jeekrs.MineRobot.util import LogUtil
from com.jeekrs.MineRobot import MineRobot
from com.jeekrs.MineRobot.processor import *
from com.jeekrs.MineRobot.blockevent import *
def destroy(qu=None):
    if qu is None:
        instance = MineRobot.INSTANCE
        qu = instance.recorder.queue
        LogUtil.showMessage(str(qu.size()) + ' blocks to destroyed')
        while not qu.isEmpty():
            e = qu.pollFirst()
            destroy_one(e)
        LogUtil.showMessage("All Done")
    else:
        destroy_one(qu)

def destroy_one(pos):
    instance = MineRobot.INSTANCE
    instance.nodeProcessor.apply(BlockDestroyNode(Utils.getWorld(), pos))
