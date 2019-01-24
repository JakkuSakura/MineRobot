from com.jeekrs.MineRobot.util import Utils
def destroy(instance, args):
    to_destroy = instance.recorder.queue
    Utils.showMessage(str(to_destroy.size()) + ' blocks to destroyed')
    for e in to_destroy:
        Utils.applyBlockEvent(e)
    Utils.showMessage("All Done")
