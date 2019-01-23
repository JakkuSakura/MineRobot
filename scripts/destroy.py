from com.jeekrs.MineRobot.util import Utils
def destroy(instance):
    to_destroy = instance.recorder.queue
    Utils.showMessage(to_destroy.length + ' blocks to destroyed')
    for e in to_destroy:
        Utils.applyBlockEvent(e)
    Utils.showMessage("All Done")
