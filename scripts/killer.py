from com.jeekrs.MineRobot.killer import Killer
from com.jeekrs.MineRobot.util import Utils
def killer(delay=300):
    delay = int(delay)
    while True:
        Killer.attackRound()
        Utils.delay(delay)
