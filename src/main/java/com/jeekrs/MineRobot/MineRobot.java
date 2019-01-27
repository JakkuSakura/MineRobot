package com.jeekrs.MineRobot;

import com.jeekrs.MineRobot.blockevent.NodeProcessor;
import com.jeekrs.MineRobot.commands.StartupCommand;
import com.jeekrs.MineRobot.listener.Recorder;
import com.jeekrs.MineRobot.processor.KeyPresser;
import com.jeekrs.MineRobot.script.JythonEngine;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = MineRobot.MODID, name = MineRobot.NAME, version = MineRobot.VERSION, acceptableRemoteVersions = "*", acceptedMinecraftVersions = "[1.9,)")
public class MineRobot {
    public static final String MODID = "mine_robot";
    public static final String NAME = "MineRobot";
    public static final String VERSION = "1.0";
    public static final Logger LOGGER = LogManager.getLogger(NAME);

    // Activated Status (Client Side)
    public static boolean isActivated = false;

    public Recorder recorder;
    public StartupCommand startupCommand;
    public JythonEngine scriptEngine;
    public NodeProcessor nodeProcessor = new NodeProcessor();

    @Mod.Instance(MODID)
    public static MineRobot INSTANCE;

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void initClient(FMLInitializationEvent event) {

        isActivated = true;
        LOGGER.info("Init Recorder");
        recorder = new Recorder();
        MinecraftForge.EVENT_BUS.register(recorder);

        LOGGER.info("Init startupCommand");
        startupCommand = new StartupCommand();
        ClientCommandHandler.instance.registerCommand(startupCommand);
        LOGGER.info("Registered command");

        LOGGER.info("Init processors");
        MinecraftForge.EVENT_BUS.register(nodeProcessor);

        LOGGER.info("Init scriptEngine");
        scriptEngine = new JythonEngine();

//        Locale.setDefault(new Locale("en", "us"));
    }

}
