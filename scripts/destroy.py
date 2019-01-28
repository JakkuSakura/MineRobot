from com.jeekrs.MineRobot.util import Utils
from com.jeekrs.MineRobot.util import LogUtil
from com.jeekrs.MineRobot import MineRobot
from com.jeekrs.MineRobot.blockevent import BlockDestroyNode
from com.jeekrs.MineRobot.blockevent import NodeProcessor


processor = NodeProcessor()

def destroy(qu=None, limit=-1):
    if qu is None:
        instance = MineRobot.INSTANCE
        qu = instance.recorder.queue
        LogUtil.showMessage(str(qu.size()) + ' blocks to destroyed')
        while not qu.isEmpty():
            e = qu.pollFirst()
            destroy_one(e, limit)
        LogUtil.showMessage("All Done")
    else:
        destroy_one(qu)



def destroy_one(pos, limit=-1):
    processor.timeLimit = limit
    processor.setNode(BlockDestroyNode(Utils.getWorld(), pos))
    return processor.work()
