from com.jeekrs.MineRobot.util import Utils
from com.jeekrs.MineRobot.util import BlockUtil
from com.jeekrs.MineRobot.util import PlayerUtil
from com.jeekrs.MineRobot.miner import AutoMine
import walkto
import destroy
import put


class AutoMiner:
    def __init__(self, dis, length, num):
        self.dis = dis
        self.length = length
        self.num = num
        self.qu = AutoMine.makeList(PlayerUtil.getNowPos(), int(dis), int(length), int(num))

    def run(self):
        while not self.qu.isEmpty():
            e = self.qu.pollFirst()
            if not BlockUtil.isPassable(Utils.getWorld(), e) or not BlockUtil.isPassable(Utils.getWorld(), e.up()):
                walkto.walkto(e.getX(), PlayerUtil.getNowPos().getY(), e.getZ(), eps=1, tip=True)
                while not destroy.destroy_one(e.up(), 5000):
                    pass
                while not destroy.destroy_one(e, 5000):
                    pass

                # noinspection PyTypeChecker
                self.find(e)
                self.find(e.up())

            my_pos = PlayerUtil.getNowPos()
            if BlockUtil.getLightValue(Utils.getWorld(), my_pos) <= 7:
                put.put('tile.torch', my_pos.getX(), my_pos.getY(), my_pos.getZ())

    def find(self, pos):
        if BlockUtil.checkExistsOre(Utils.getWorld(), pos.up()):
            self.qu.add(0, pos.up())
        if BlockUtil.checkExistsOre(Utils.getWorld(), pos.down()):
            self.qu.add(0, pos.down())
        if BlockUtil.checkExistsOre(Utils.getWorld(), pos.south()):
            self.qu.add(0, pos.south())
        if BlockUtil.checkExistsOre(Utils.getWorld(), pos.north()):
            self.qu.add(0, pos.north())
        if BlockUtil.checkExistsOre(Utils.getWorld(), pos.west()):
            self.qu.add(0, pos.west())
        if BlockUtil.checkExistsOre(Utils.getWorld(), pos.east()):
            self.qu.add(0, pos.east())


def automine(dis=3, length=10, num=20):
    AutoMiner(dis, length, num).run()
