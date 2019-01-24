package com.jeekrs.MineRobot.commands;

import com.jeekrs.MineRobot.MineRobot;
import com.jeekrs.MineRobot.pathfinding.PlayerOperationAgent;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import static com.jeekrs.MineRobot.util.Utils.showMessage;

public class StartupCommand extends RootCommand {
    public StartupCommand() {
        super("robot");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1 || args[0].equals("help")) {
            showMessage("You should call with start xxx, stop, or show");
        } else if (args[0].equals("start")) {
            showMessage("Robot is started");
            if (args.length - 2 >= 0) {
                String[] s = new String[args.length - 2];
                System.arraycopy(args, 2, s, 0, args.length - 2);
                MineRobot.INSTANCE.scriptEngine.start(args[1], s);
            } else {
                showMessage("Missing arguments");
            }
        } else if (args[0].equals("stop")) {
            showMessage("Robot is stopped");
            MineRobot.INSTANCE.processor.clear();
            MineRobot.INSTANCE.recorder.clear();
            MineRobot.INSTANCE.scriptEngine.stop();
        } else if (args[0].equals("show")) {
            MineRobot.INSTANCE.recorder.show();
        } else if (args[0].equals("test")) {
            showMessage("started");
            PlayerOperationAgent.test();
        } else {
            showMessage("You should call with start xxx, stop, or show");
        }

    }

}
