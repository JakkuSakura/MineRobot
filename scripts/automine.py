from com.jeekrs.MineRobot.util import Utils
from com.jeekrs.MineRobot.util import BlockUtil
from com.jeekrs.MineRobot.util import PlayerUtil
from com.jeekrs.MineRobot.miner import AutoMine
import walkto
import destroy
import put
def automine(dis=3, length=64, num=1000):
    qu = list(AutoMine.makeList(PlayerUtil.getNowPos(), int(dis), int(length), int(num)))
    for e in qu:
        walkto.walkto(e.getX(), e.getY(), e.getZ(), tip=False)
        destroy.destroy_one(e)
        my_pos = PlayerUtil.getNowPos()
        if BlockUtil.getLightValue(Utils.getWorld(), my_pos) <= 7:
            put.put('tile.torch', my_pos.getX(), my_pos.getY(), my_pos.getZ())

