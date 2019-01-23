from com.jeekrs.MineRobot.util import Utils

def automine(instance, args):
    # todo complete these args

    qu = arrange()
    for e in qu:
        if Utils.checkExists(e):
            Utils.changeTool("somewhat")


def arrange(face_front=None, beginpos=None, dis=3, length=64, num=100):
    if not face_front:
        face_front = Utils.getFaceDire()
    if not beginpos:
        beginpos = Utils.getPlayerPos()
    nowpos = beginpos
    qu = []

    for i in xrange(num):
        qu.extends([nowpos.add(face_front.multi(x)) for x in xrange(1, dis)])

        nowpos = nowpos.add(face_front)
        qu.extends([nowpos.add(face_front.left().multi(x)) for x in xrange(1, length + 1)])
        qu.extends([nowpos.add(face_front.left().multi(x)) for x in xrange(1, length + 1)])

    return qu

