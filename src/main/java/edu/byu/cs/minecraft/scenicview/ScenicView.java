package edu.byu.cs.minecraft.scenicview;

import edu.byu.cs.minecraft.scenicview.common.ScenicViewServer;
import edu.byu.cs.minecraft.scenicview.common.communication.CommunicationEventHandler;
import edu.byu.cs.minecraft.scenicview.common.communication.Quiz_Command;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = ScenicView.MODID,
        name = "Scenic View",
        version = "0.0.0",
        acceptedMinecraftVersions = "[1.12.2]"
)
public class ScenicView {

    static final String MODID = "scenicviewprovo";

    private Logger logger;

    public Logger getLogger() {
        return logger;
    }

    @Mod.Instance
    public static ScenicView instance;

    public static ScenicView getInstance() {
        return instance;
    }

    @SidedProxy(
            modId = MODID,
            clientSide = "edu.byu.cs.minecraft.scenicview.client.ScenicViewClient",
            serverSide = "edu.byu.cs.minecraft.scenicview.common.ScenicViewServer"
    )
    public static ScenicViewServer proxy;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        logger.info("onPreInit");
        proxy.onPreInit(event);
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        logger.info("onInit");
        proxy.onInit(event);
        MinecraftForge.EVENT_BUS.register(new CommunicationEventHandler());
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        logger.info("onPostInit");
        proxy.onPostInit(event);
    }

    @Mod.EventHandler
    public void onServerAboutToStart(FMLServerAboutToStartEvent event) {
        logger.info("onServerAboutToStart");
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        logger.info("onServerStarting");
        event.registerServerCommand(new Quiz_Command());
    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent event) {
        logger.info("onServerStarted");
    }

    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        logger.info("onServerStopping");
    }

    @Mod.EventHandler
    public void onServerStopped(FMLServerStoppedEvent event) {
        logger.info("onServerStopped");
    }
}
