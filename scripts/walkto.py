from com.jeekrs.MineRobot.util import Utils
from com.jeekrs.MineRobot.pathfinder import PathFinder
def walkto(instance, args):
    to_pos = instance.recorder.queue[-1].pos
    Utils.showMessage('will go to ' + to_pos.toString())
    finder = PathFinder(Utils.getEntityPlayer(), Utils.getWorld())
    path = finder.getPathToPos(to_pos)
    Utils.showMessage(path.toString())
    Utils.showMessage("All Done")
