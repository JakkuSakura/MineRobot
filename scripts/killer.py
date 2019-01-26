from com.jeekrs.MineRobot.killer import Killer
from com.jeekrs.MineRobot.util import Utils
def killer():
    while True:
        Killer.attackRound()
        Utils.delay(300)
