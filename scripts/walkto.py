from com.jeekrs.MineRobot.util import Utils
from com.jeekrs.MineRobot import MineRobot
from com.jeekrs.MineRobot.pathfinding import PathFinder
from net.minecraft.client import Minecraft
from net.minecraft.util.math import BlockPos
from net.minecraft.client.settings import KeyBinding
from com.jeekrs.MineRobot.util import LogUtil
def walkto(x=None, y=None, z=None, tip=True):
    instance = MineRobot.INSTANCE
    if x is None:
        to_pos = instance.recorder.lastPos(1)
    else:
        to_pos = BlockPos(int(x), int(y), int(z))
    if tip:
        LogUtil.showMessage('Will go to ' + to_pos.toString())

    instance.pathFinder.setTarget(Minecraft.getMinecraft().player, to_pos)
    instance.pathFinder.run()

    # instance.keyPresser.clear()
    # instance.navigator.clean()
    if tip:
        LogUtil.showMessage("Arrived " + to_pos.toString())
