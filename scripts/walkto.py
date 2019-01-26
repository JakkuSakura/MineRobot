from com.jeekrs.MineRobot.util import Utils
from com.jeekrs.MineRobot import MineRobot
from com.jeekrs.MineRobot.pathfinding import PathFinder
from net.minecraft.client import Minecraft
from net.minecraft.util.math import BlockPos


def walkto(x=None, y=None, z=None, tip=True):
    instance = MineRobot.INSTANCE
    if x is None:
        to_pos = instance.recorder.lastPos(1)
    else:
        to_pos = BlockPos(int(x), int(y), int(z))
    if tip:
        Utils.showMessage('Will go to ' + to_pos.toString())
    instance.pathFinder.startPathfinding(Minecraft.getMinecraft().player, to_pos)
    while instance.pathFinder.target is not None:
        Utils.delay(10)
    if tip:
        Utils.showMessage("All Done")
