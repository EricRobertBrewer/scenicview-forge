
package edu.byu.cs.minecraft.scenicview.common.communication;

import edu.byu.cs.minecraft.scenicview.common.Communication_Quiz;
import edu.byu.cs.minecraft.scenicview.common.ScenicViewServer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

class WonGame_Command  extends  CommandBase{


    @Override
    @Nonnull
    public String getName() {
        return "wongame";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return " Records that two players won a mini-game and provides their rewarde";
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {

        return;
    }

}