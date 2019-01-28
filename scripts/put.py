from com.jeekrs.MineRobot.blockevent import BlockPutNode
from com.jeekrs.MineRobot.blockevent import BlockDestroyNode
from com.jeekrs.MineRobot.blockevent import NodeProcessor
from com.jeekrs.MineRobot.util import Utils
from com.jeekrs.MineRobot import MineRobot
from com.jeekrs.MineRobot.util import BlockPos
processor = NodeProcessor()
def put(name, x, y, z):
    processor.setNode(BlockPutNode(Utils.getWorld(), BlockPos(int(x), int(y), int(z)), name))
    processor.run()
