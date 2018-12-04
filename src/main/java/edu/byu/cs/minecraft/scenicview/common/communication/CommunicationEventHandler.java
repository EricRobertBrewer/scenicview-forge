package edu.byu.cs.minecraft.scenicview.common.communication;

import edu.byu.cs.minecraft.scenicview.ScenicView;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommunicationEventHandler {

    @SubscribeEvent
    public void onPlayerInteractEntitySpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        if (event.getHand() == EnumHand.OFF_HAND) {
            return;
        }
        ScenicView.getInstance().getLogger().info("onPlayerInteractEntitySpecific: entity=" + event.getEntity().getName() + " target=" + event.getTarget().getName());
    }
}
