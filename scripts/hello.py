from com.jeekrs.MineRobot.util import Utils
def hello(instance, args):
    print 'hello from stdout'
    for i in ['steve', 'alex', 'bob']:
        Utils.log('hello from logger ' + str(i))

    print instance
    instance and instance.logger.log("hello from logger 2")

