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

import javax.annotation.Nonnull;

public class Quiz_Command extends CommandBase {

    @Override
    @Nonnull
    public String getName() {
        return "quiz";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "quiz question";
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        if (args.length < 1 ||
                (!"begin".equalsIgnoreCase(args[0]) &&
                        !"answer".equalsIgnoreCase(args[0]) &&
                        !"quit".equalsIgnoreCase(args[0]))) {
            String usage = "/quiz begin -- Join the game\n/quiz answer [YOUR ANSWER] -- Answer the question\n/quiz quit -- Give up";
            sender.sendMessage(new TextComponentString(TextFormatting.DARK_GREEN + usage));
            return;
        }
        if ("quit".equalsIgnoreCase(args[0])) {
            ScenicViewServer.communication_quiz.clearEverything();
            // Minecraft.getMinecraft().world.getPlayerEntityByName(sender.getName()).setPositionAndUpdate(-3, 56, -19);
            MinecraftServer s = FMLCommonHandler.instance().getMinecraftServerInstance();
//            s.getCommandManager().executeCommand(s, "/tp " + sender.getName()+ " 755.5 1.0 -1538.5");
            s.getCommandManager().executeCommand(s, "/tp " + sender.getName() + " -2.5 57.0 -21.5");
            //sender.sendMessage(new TextComponentString("/tp " + sender.getName()+ " 755 1 -1539"));
        } else if ("begin".equalsIgnoreCase(args[0])) {

            //when someone who is not in the game type egin, there maybe a problem
            //sender is one of the player
            if (ScenicViewServer.communication_quiz.getPlayer_two().equals(sender.getName()) || ScenicViewServer.communication_quiz.getPlayer_one().equals(sender.getName())) {
                sender.sendMessage(new TextComponentString(TextFormatting.DARK_GREEN + "You already in the game"));
                return;
            }
            //payer one not assigned and the sender is not player two
            else if (ScenicViewServer.communication_quiz.getPlayer_one().isEmpty()) {
                ScenicViewServer.communication_quiz.setPlayer_one(sender.getName());
                sender.sendMessage(new TextComponentString(TextFormatting.BLUE + "You joined the game successfully, waiting for your partner..."));
            }
            //player two is not assigned and the sender is not player one
            else if (ScenicViewServer.communication_quiz.getPlayer_two().isEmpty()) {
                ScenicViewServer.communication_quiz.setPlayer_two(sender.getName());
                sender.sendMessage(new TextComponentString(TextFormatting.BLUE + "You joined the game successfully, waiting for your partner..."));
            }
            //both player one and two has been assigned and is not the sender
            else {
                sender.sendMessage(new TextComponentString(TextFormatting.BLUE + "Someone else is taking the quiz, wait a second"));
            }
            if (!ScenicViewServer.communication_quiz.getPlayer_two().isEmpty() && !ScenicViewServer.communication_quiz.getPlayer_one().isEmpty()) {
                EntityPlayer p1 = server.getEntityWorld().getPlayerEntityByName(ScenicViewServer.communication_quiz.getPlayer_one());
                EntityPlayer p2 = server.getEntityWorld().getPlayerEntityByName(ScenicViewServer.communication_quiz.getPlayer_two());
                execute_quiz(p1, p2);
            }

        } else if ("answer".equalsIgnoreCase(args[0])) {
            StringBuilder sb = new StringBuilder();
            sb.append(args[1]);
            for (int i = 2; i < args.length; i++) {
                sb.append(" ");
                sb.append(args[i]);
            }
            String answer = sb.toString();

            //not one of the player
            if (!ScenicViewServer.communication_quiz.getPlayer_one().equals(sender.getName()) && !ScenicViewServer.communication_quiz.getPlayer_two().equals(sender.getName())) {
                sender.sendMessage(new TextComponentString(TextFormatting.RED + "You are not in the quiz now"));
                return;
            }
            EntityPlayer p1 = server.getEntityWorld().getPlayerEntityByName(ScenicViewServer.communication_quiz.getPlayer_one());
            EntityPlayer p2 = server.getEntityWorld().getPlayerEntityByName(ScenicViewServer.communication_quiz.getPlayer_two());

            if (ScenicViewServer.communication_quiz.getPlayer_one().equals(sender.getName())) {
                //if player one has answered all the question being asked
                if (ScenicViewServer.communication_quiz.getPlayerA_answer().size() == ScenicViewServer.communication_quiz.getPlayerA_question().size()) {
                    sender.sendMessage(new TextComponentString(TextFormatting.RED + "Wait for a new question coming up"));
                    return;
                }

                ScenicViewServer.communication_quiz.setPlayerA_answer(answer);
            }
            if (ScenicViewServer.communication_quiz.getPlayer_two().equals(sender.getName())) {
                //if player two has answered all the question being asked
                if (ScenicViewServer.communication_quiz.getPlayerB_answer().size() == ScenicViewServer.communication_quiz.getPlayerB_question().size()) {
                    sender.sendMessage(new TextComponentString(TextFormatting.RED + "Wait for a new question coming up"));
                    return;
                }

                ScenicViewServer.communication_quiz.setPlayerB_answer(answer);
            }
            boolean playerA_isAnswered = (ScenicViewServer.communication_quiz.getPlayerA_answer().size() == ScenicViewServer.communication_quiz.getPlayerA_question().size());
            boolean playerB_isAnswered = (ScenicViewServer.communication_quiz.getPlayerB_answer().size() == ScenicViewServer.communication_quiz.getPlayerB_question().size());
            if (playerA_isAnswered && playerB_isAnswered) {
                execute_quiz(p1, p2);
            }
            else{
                sender.sendMessage(new TextComponentString(TextFormatting.RED + "Waiting for your partner to answer the question"));
            }
        }
    }

    private void execute_quiz(EntityPlayer p1, EntityPlayer p2) {
        if (checkIfGameOver(p1, p2, ScenicViewServer.communication_quiz)) {
            //teleport
            return;
        }

        int randQuestion = ScenicViewServer.communication_quiz.pick_questions_number();
        //check if the question has been asked;
        while (ScenicViewServer.communication_quiz.getQuestionNum().contains(randQuestion)) {
            randQuestion = ScenicViewServer.communication_quiz.pick_questions_number();
        }
        ScenicViewServer.communication_quiz.getQuestionNum().add(randQuestion);

        ScenicViewServer.communication_quiz.setQuestionA(ScenicViewServer.communication_quiz.pick_question(1, randQuestion));
        ScenicViewServer.communication_quiz.setPlayerA_question(ScenicViewServer.communication_quiz.getQuestionA());

        ScenicViewServer.communication_quiz.setQuestionB(ScenicViewServer.communication_quiz.pick_question(2, randQuestion));
        ScenicViewServer.communication_quiz.setPlayerB_question(ScenicViewServer.communication_quiz.getQuestionB());

        if (p1 != null && p2 != null) {
            p1.sendMessage(new TextComponentString(TextFormatting.BLUE + "QUESTION: " + ScenicViewServer.communication_quiz.getQuestionA() + "\n type \'\\quiz answer [your answer]\' to answer the question"));
            p2.sendMessage(new TextComponentString(TextFormatting.BLUE + "QUESTION: " + ScenicViewServer.communication_quiz.getQuestionB() + "\n type \'\\quiz answer [your answer]\' to answer the question"));
        }
    }

    private boolean checkIfGameOver(EntityPlayer p1, EntityPlayer p2, Communication_Quiz communication_quiz) {
        boolean over = false;
        if (communication_quiz.getPlayerA_answer().size() == 5 && communication_quiz.getPlayerB_answer().size() == 5) {
            over = true;
            p1.sendMessage(new TextComponentString(TextFormatting.BLUE + "The Quiz is over"));
            p2.sendMessage(new TextComponentString(TextFormatting.BLUE + "The Quiz is over"));

            MinecraftServer s = FMLCommonHandler.instance().getMinecraftServerInstance();

            int size = communication_quiz.getPlayerA_answer().size();
            int count_correction = printResult(p1, p2, communication_quiz);
            if (count_correction >= (int) Math.ceil(size / 2.0)) {
                p1.sendMessage(new TextComponentString(TextFormatting.BLUE + "Congratulation! You and your partner pass the test"));
                s.getCommandManager().executeCommand(s, "/tp " + p1.getName() + " -2.5 57.0 -21.5");

                p2.sendMessage(new TextComponentString(TextFormatting.BLUE + "Congratulation! You and your partner pass the test"));
                s.getCommandManager().executeCommand(s, "/tp " + p2.getName() + " -2.5 57.0 -21.5");

            } else {
                p1.sendMessage(new TextComponentString(TextFormatting.BLUE + "Sorry! You and your partner may need to talk more next time"));
                s.getCommandManager().executeCommand(s, "/tp " + p1.getName() + " -2.5 57.0 -21.5");

                p2.sendMessage(new TextComponentString(TextFormatting.BLUE + "Sorry! You and your partner may need to talk more next time"));
                s.getCommandManager().executeCommand(s, "/tp " + p2.getName() + " -2.5 57.0 -21.5");

            }

            ScenicViewServer.communication_quiz.clearEverything();
        }
        return over;
    }

    private int printResult(EntityPlayer p1, EntityPlayer p2, Communication_Quiz communication_quiz){
        int count = 0;
        StringBuilder playerA_result = new StringBuilder();
        StringBuilder playerB_result = new StringBuilder();
        playerA_result.append("Compare Answers: \n");
        playerB_result.append("Compare Answers: \n");
        while(!communication_quiz.getPlayerA_question().empty()){
            playerA_result.append(communication_quiz.getPlayerA_question().pop()).append("\n");
            playerB_result.append(communication_quiz.getPlayerB_question().pop()).append("\n");
            String pA = communication_quiz.popPlayerA_answer();
            String pB = communication_quiz.popPlayerB_answer();

            playerA_result.append("You Answered: ").append(pA).append("\n");
            playerA_result.append("Your Partner Answered: ").append(pB).append("\n");

            playerB_result.append("You Answered: ").append(pB).append("\n");
            playerB_result.append("Your Partner Answered: ").append(pA).append("\n");


            if (pA.equalsIgnoreCase(pB)) {
                count++;
            }
        }
        p1.sendMessage(new TextComponentString(TextFormatting.BLUE + playerA_result.toString()));
        p2.sendMessage(new TextComponentString(TextFormatting.BLUE + playerB_result.toString()));

        return count;
    }
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




//         if (args[0].equals("Question")) {
// //                sender.sendMessage(new TextComponentString(TextFormatting.RED + getUsage(sender)));
// //                return;
//             //if one of the player is missing, let the sender find a partner first
//             if (ScenicViewServer.communication_quiz.getPlayer_one().isEmpty() || ScenicViewServer.communication_quiz.getPlayer_two().isEmpty()) {
//                 sender.sendMessage(new TextComponentString(TextFormatting.RED + "Wait for your partner to be prepared or type \'/Quiz Quit\' to leave the quiz"));

//                 return;
//             }

//             EntityPlayer p1 = server.getEntityWorld().getPlayerEntityByName(ScenicViewServer.communication_quiz.getPlayer_one());
//             EntityPlayer p2 = server.getEntityWorld().getPlayerEntityByName(ScenicViewServer.communication_quiz.getPlayer_two());

//             //after answer 5 questions
//             if (checkIfGameOver(p1, p2, ScenicViewServer.communication_quiz)) {
//                 //teleport
//                 return;
//             }
//             //get a random number for question
//             if (ScenicViewServer.communication_quiz != null) {
//                 int randQuestion = ScenicViewServer.communication_quiz.pick_questions_number();
//                 //check if the question has been asked;
//                 while (ScenicViewServer.communication_quiz.getQuestionNum().contains(randQuestion))
//                     randQuestion = ScenicViewServer.communication_quiz.pick_questions_number();


//                 //if the sender is not one of the player, let him start the game first
//                 if (!ScenicViewServer.communication_quiz.getPlayer_one().equals(sender.getName()) && !ScenicViewServer.communication_quiz.getPlayer_two().equals(sender.getName())) {
//                     sender.sendMessage(new TextComponentString((TextFormatting.RED + "Start the game by typing \'/Quiz Begin\' first")));
//                     return;
//                 }
//                 //if one of the sender ask for a question
//                 else if (ScenicViewServer.communication_quiz.getPlayer_one().equals(sender.getName()) || ScenicViewServer.communication_quiz.getPlayer_two().equals((sender.getName()))) {
//                     ScenicViewServer.communication_quiz.setQuestionA(ScenicViewServer.communication_quiz.pick_question(1, randQuestion));
//                     ScenicViewServer.communication_quiz.setPlayerA_question(ScenicViewServer.communication_quiz.getQuestionA());

//                     ScenicViewServer.communication_quiz.setQuestionB(ScenicViewServer.communication_quiz.pick_question(2, randQuestion));
//                     ScenicViewServer.communication_quiz.setPlayerB_question(ScenicViewServer.communication_quiz.getQuestionB());


//                     if (p1 != null && p2 != null) {
//                         p1.sendMessage(new TextComponentString(TextFormatting.BLUE + ScenicViewServer.communication_quiz.getQuestionA()));
//                         p2.sendMessage(new TextComponentString(TextFormatting.BLUE + ScenicViewServer.communication_quiz.getQuestionB()));
//                     }

//                 }

//             }
//         }