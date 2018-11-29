package edu.byu.cs.minecraft.scenicview;

import edu.byu.cs.minecraft.scenicview.common.ScenicViewServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = ScenicView.MODID,
        name = "Scenic View",
        version = "1.0",
        acceptedMinecraftVersions = "[1.12.2]"
)
public class ScenicView {

    static final String MODID = "scenicviewprovo";

    private Logger logger;

    @Mod.Instance
    public static ScenicView instance;

    @SidedProxy(
            modId = MODID,
            clientSide = "edu.byu.cs.minecraft.scenicview.client.ScenicViewClient",
            serverSide = "edu.byu.cs.minecraft.scenicview.common.ScenicViewServer"
    )
    public static ScenicViewServer proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
