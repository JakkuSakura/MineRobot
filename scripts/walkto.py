from com.jeekrs.MineRobot.util import Utils
from com.jeekrs.MineRobot import MineRobot
from com.jeekrs.MineRobot.pathfinding import PathFinder
from com.jeekrs.MineRobot.util import LogUtil
from com.jeekrs.MineRobot.util import BlockPos
from com.jeekrs.MineRobot.pathfinding import PathFinder
def walkto(x=None, y=None, z=None, tip=True):
    instance = MineRobot.INSTANCE
    if x is None:
        to_pos = instance.recorder.lastPos(1)
    else:
        to_pos = BlockPos(int(x), int(y), int(z))

    pf = PathFinder(Utils.getEntityPlayer(), to_pos, tip)
    pf.run()

