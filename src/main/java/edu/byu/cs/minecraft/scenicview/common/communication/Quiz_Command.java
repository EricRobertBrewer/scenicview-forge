package edu.byu.cs.minecraft.scenicview.common.communication;

import edu.byu.cs.minecraft.scenicview.common.Communication_Quiz;
import edu.byu.cs.minecraft.scenicview.common.ScenicViewServer;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Quiz_Command extends CommandBase {

    @Override
    public String getName() {
        return "Quiz";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Quiz question";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {


        if (args.length < 0) {
            String usage = "Type: /Quiz Begin to join the game";
            sender.sendMessage(new TextComponentString(TextFormatting.DARK_GREEN + usage));
        }
        if(args[0].equals("Quit")){
            ScenicViewServer.communication_quiz.clearEverything();
           // Minecraft.getMinecraft().world.getPlayerEntityByName(sender.getName()).setPositionAndUpdate(-3, 56, -19);
            MinecraftServer s = FMLCommonHandler.instance().getMinecraftServerInstance();
            s.getCommandManager().executeCommand(s, "/tp " + sender.getName()+ " 755.5 1.0 -1538.5");
           // s.getCommandManager().executeCommand(s, "/tp " + sender.getName()+ " -3.5 56.0 -18.5");
           //sender.sendMessage(new TextComponentString("/tp " + sender.getName()+ " 755 1 -1539"));
        }
        if (args[0].equals("Begin")) {

            //when someone who is not in the game type egin, there maybe a problem
            //sender is one of the player
            if (ScenicViewServer.communication_quiz.getPlayer_two().equals(sender.getName()) || ScenicViewServer.communication_quiz.getPlayer_one().equals(sender.getName())) {
                sender.sendMessage(new TextComponentString(TextFormatting.DARK_GREEN + "You already in the game"));
                return;
            }
            //payer one not assigned and the sender is not player two
            else if (ScenicViewServer.communication_quiz.getPlayer_one().isEmpty()) {
                ScenicViewServer.communication_quiz.setPlayer_one(sender.getName());
                sender.sendMessage(new TextComponentString(TextFormatting.BLUE + "Quiz Started"));
            }
            //player two is not assigned and the sender is not player one
            else if (ScenicViewServer.communication_quiz.getPlayer_two().isEmpty()) {
                ScenicViewServer.communication_quiz.setPlayer_two(sender.getName());
                sender.sendMessage(new TextComponentString(TextFormatting.BLUE + "Quiz Started"));
            }
            //both player one and two has been assigned and is not the sender
            else {
                sender.sendMessage(new TextComponentString(TextFormatting.BLUE + "Someone else is taking the quiz, wait a second"));
            }

        }
        if (args[0].equals("Question")) {
//                sender.sendMessage(new TextComponentString(TextFormatting.RED + getUsage(sender)));
//                return;
            //if one of the player is missing, let the sender find a partner first
            if (ScenicViewServer.communication_quiz.getPlayer_one().isEmpty() || ScenicViewServer.communication_quiz.getPlayer_two().isEmpty()) {
                sender.sendMessage(new TextComponentString(TextFormatting.RED + "Wait for your partner to be prepared or type \'/Quiz Quit\' to leave the quiz"));

                return;
            }

            EntityPlayer p1 = Minecraft.getMinecraft().world.getPlayerEntityByName(ScenicViewServer.communication_quiz.getPlayer_one());
            EntityPlayer p2 = Minecraft.getMinecraft().world.getPlayerEntityByName(ScenicViewServer.communication_quiz.getPlayer_two());

            //after answer 5 questions
            if (checkIfGameOver(p1, p2, ScenicViewServer.communication_quiz)) {
                //teleport

                return;
            }
            //get a random number for question
            if (ScenicViewServer.communication_quiz != null) {
                int randQuestion = ScenicViewServer.communication_quiz.pick_questions_number();
                //check if the question has been asked;
                while (ScenicViewServer.communication_quiz.getQuestionNum().contains(randQuestion))
                    randQuestion = ScenicViewServer.communication_quiz.pick_questions_number();


                //if the sender is not one of the player, let him start the game first
                if (!ScenicViewServer.communication_quiz.getPlayer_one().equals(sender.getName()) && !ScenicViewServer.communication_quiz.getPlayer_two().equals(sender.getName())) {
                    sender.sendMessage(new TextComponentString((TextFormatting.RED + "Start the game by typing \'/Quiz Begin\' first")));
                    return;
                }
                //if one of the sender ask for a question
                else if (ScenicViewServer.communication_quiz.getPlayer_one().equals(sender.getName()) || ScenicViewServer.communication_quiz.getPlayer_two().equals((sender.getName()))) {
                    ScenicViewServer.communication_quiz.setQuestionA(ScenicViewServer.communication_quiz.pick_question(1, randQuestion));
                    ScenicViewServer.communication_quiz.setPlayerA_question(ScenicViewServer.communication_quiz.getQuestionA());

                    ScenicViewServer.communication_quiz.setQuestionB(ScenicViewServer.communication_quiz.pick_question(2, randQuestion));
                    ScenicViewServer.communication_quiz.setPlayerB_question(ScenicViewServer.communication_quiz.getQuestionB());


                    if (p1 != null && p2 != null) {
                        p1.sendMessage(new TextComponentString(TextFormatting.BLUE + ScenicViewServer.communication_quiz.getQuestionA()));
                        p2.sendMessage(new TextComponentString(TextFormatting.BLUE + ScenicViewServer.communication_quiz.getQuestionB()));
                    }

                }

            }
        }

        if (args[0].equals("Answer")) {

            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                sb.append(i + " ");
            }
            String answer = sb.toString();

            //not one of the player
            if (!ScenicViewServer.communication_quiz.getPlayer_one().equals(sender.getName()) && !ScenicViewServer.communication_quiz.getPlayer_two().equals(sender.getName())) {
                sender.sendMessage(new TextComponentString(TextFormatting.RED + "You are not in the quiz now"));
            }
            if (ScenicViewServer.communication_quiz.getPlayer_one().equals(sender.getName())) {
                //if player one has answered all the question being asked
                if (ScenicViewServer.communication_quiz.getPlayerA_answer().size() ==ScenicViewServer.communication_quiz.getPlayerA_question().size()) {
                    sender.sendMessage(new TextComponentString(TextFormatting.RED + "choose a question first (by typing \'/Quiz Question\') "));
                    return;
                }

                ScenicViewServer.communication_quiz.setPlayerA_answer(answer);
            }
            if (ScenicViewServer.communication_quiz.getPlayer_two().equals(sender.getName())) {
                //if player two has answered all the question being asked
                if (ScenicViewServer.communication_quiz.getPlayerB_answer().size() == ScenicViewServer.communication_quiz.getPlayerB_question().size()) {
                    sender.sendMessage(new TextComponentString(TextFormatting.RED + "choose a question first (by typing \'/Quiz Question\') "));
                    return;
                }

                ScenicViewServer.communication_quiz.setPlayerB_answer(answer);
            }

        }
    }

    public int count_correction(Communication_Quiz communication_quiz) {
        int count = 0;
        for (int i = 0; i < communication_quiz.getPlayerA_answer().size(); i++) {
            String pA = communication_quiz.popPlayerA_answer();
            String pB = communication_quiz.popPlayerB_answer();
            if (pA.equalsIgnoreCase(pB)) {
                count++;
            }
//            else{
//                wrongAnswer.push(getPlayer_one() + ": " +pA +"\n" + getPlayer_two() + ": " + pB );
//            }
        }
        return count++;
    }



    public boolean checkIfGameOver(EntityPlayer p1, EntityPlayer p2, Communication_Quiz communication_quiz) {
        boolean over = false;
        if (communication_quiz.getPlayerA_answer().size() == 5 && communication_quiz.getPlayerB_answer().size() == 5) {
            over = true;
            p1.sendMessage(new TextComponentString(TextFormatting.BLUE + "Congratulation! Quiz is over"));
            p2.sendMessage(new TextComponentString(TextFormatting.BLUE + "Congratulation! Quiz is over"));

            MinecraftServer s = FMLCommonHandler.instance().getMinecraftServerInstance();

            if (count_correction(communication_quiz) > Math.ceil(communication_quiz.getPlayerA_answer().size() / 2)) {
                p1.sendMessage(new TextComponentString(TextFormatting.BLUE + "Congratulation! You and your partner pass the test"));
                s.getCommandManager().executeCommand(s, "/tp " + p1.getName()+ " -2.5 47.0 31.5");

                p2.sendMessage(new TextComponentString(TextFormatting.BLUE + "Congratulation! You and your partner pass the test"));
                s.getCommandManager().executeCommand(s, "/tp " + p2.getName()+ " -2.5 47.0 31.5");

            } else {
                p1.sendMessage(new TextComponentString(TextFormatting.BLUE + "Sorry! You and your partner may need to talk more"));
                s.getCommandManager().executeCommand(s, "/tp " + p1.getName()+ " -3.5 56.0 -18.5");

                p2.sendMessage(new TextComponentString(TextFormatting.BLUE + "Sorry! You and your partner may need to talk more"));
                s.getCommandManager().executeCommand(s, "/tp " + p2.getName()+ " -3.5 56.0 -18.5");

            }
            ScenicViewServer.communication_quiz.clearEverything();
        }
        return over;
    }

//    public boolean player_attend (){
//        double ax1 = (double) -11;
//        double ay1 = (double) 57;
//        double az1 = (double) 53;
//        double ax2 = (double) -5;
//        double ay2 = (double) 57;
//        double az2 = (double) 51;
//        AxisAlignedBB scan_place_red = new AxisAlignedBB(ax1, ay1, az1, ax2, ay2, az2);
//        boolean a = !Minecraft.getMinecraft().world.getEntitiesWithinAABB(EntityPlayer.class, scan_place_red).isEmpty();
//
//        double bx1 = (double) -11;
//        double by1 = (double) 57;
//        double bz1 = (double) 53;
//        double bx2 = (double) -5;
//        double by2 = (double) 57;
//        double bz2 = (double) 51;
//        AxisAlignedBB scan_place_blue = new AxisAlignedBB(bx1, by1, bz1, bx2, by2, bz2);
//        boolean b = Minecraft.getMinecraft().world.getEntitiesWithinAABB(EntityPlayer.class, scan_place_blue).isEmpty();
//
//        return a && b;
//    }


}

