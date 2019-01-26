# coding=utf-8
from com.jeekrs.MineRobot.util import Utils
def hello(*args):
    a = args[0] if len(args) >= 1 else None
    b = args[1] if len(args) >= 2 else None
    c = args[2] if len(args) >= 3 else None

    hello2(a, b, c)

def hello2(a, b, c):
    print 'hello from stdout'
    for i in ['steve', 'alex', 'bob']:
        Utils.log('hello from logger ' + str(i))
    print a, b, c
    print "中文问题"
    Utils.log("wtf")

