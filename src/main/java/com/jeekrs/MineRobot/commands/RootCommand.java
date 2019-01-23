package com.jeekrs.MineRobot.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class RootCommand extends CommandBase {
    public final String name;

    public RootCommand(String name) {
        this.name = name;
    }

    public String getCommandName() {
        return name;
    }

    public String getCommandUsage(ICommandSender sender) {
        return "/" + this.getCommandName() + " help";
    }

    @Override
    public String getName() {
        return getCommandName();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return getCommandName() + ".usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
    }

    public String getFullCommandString() {
        return getCommandName();
    }
}
