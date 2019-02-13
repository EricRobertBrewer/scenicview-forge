package edu.byu.cs.minecraft.scenicview.common;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Communication_Quiz {
    private String[] player_one_questions;
    private String[] player_two_questions;

    private String player_one;
    private String player_two;
    private String questionA ;
    private String questionB ;
    private String answer;
    private Stack<String> playerA_question;
    private Stack<String> playerB_question;
    private Stack<String> playerA_answer;
    private Stack<String> playerB_answer;
    //    private Stack<String> wrongAnswer = new Stack<>();
    private List<Integer> questionNum;

    public Communication_Quiz(){
        player_one_questions = new String[]{"How many siblings does your partner have?", "Where does your partner's parents live?", "does your partner live with his/her family?", "when did your partner have dinner with his/her family last time?", "Does your partner have children?", "does your partner have pets?", "Is your partner married?",
                "How many siblings do you have?", "Where do your parents live?", "Do your live with his/her family?", "when did you have dinner with his/her family last time?", "Do you have children?", "Do you have pets?", "Are you married?"};
        player_two_questions = new String[]{"How many siblings do you have?", "Where do your parents live?", "Do your live with his/her family?", "when did you have dinner with his/her family last time?", "Do you have children?", "Do you have pets?", "Are you married?" ,
                "How many siblings does your partner have?", "Where does your partner's parents live?", "does your partner live with his/her family?", "when did your partner have dinner with his/her family last time?", "Does your partner have children?", "does your partner have pets?", "Is your partner married?"};
        player_one = "";
        player_two = "";
        questionA = "";
        questionB = "";
        answer = "";
        playerA_answer = new Stack<>();
        playerB_answer = new Stack<>();
        playerA_question = new Stack<>();
        playerB_question = new Stack<>();
        questionNum = new ArrayList<>();
    }

    public void clearEverything() {
        setPlayer_one("");
        setPlayer_two("");
        playerA_question.clear();
        playerB_question.clear();
        playerB_answer.clear();
        playerA_answer.clear();
        questionNum.clear();

    }
    public int pick_questions_number(){
        Random rand = new Random();
        return rand.nextInt(14);
    }

    public String pick_question(int num, int randQuestion){
        if(num==1){
            return player_one_questions[randQuestion];
        }
         else return player_two_questions[randQuestion];
    }
    public String getPlayer_one() {
        return player_one;
    }

    public void setPlayer_one(String player_one) {
        this.player_one = player_one;
    }

    public String getPlayer_two() {
        return player_two;
    }

    public void setPlayer_two(String player_two) {
        this.player_two = player_two;
    }

    public String getQuestionA() {
        return questionA;
    }

    public void setQuestionA(String questionA) {
        this.questionA = questionA;
    }

    public String getQuestionB() {
        return questionB;
    }

    public void setQuestionB(String questionB) {
        this.questionB = questionB;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Stack<String> getPlayerA_question() {
        return playerA_question;
    }

    public void setPlayerA_question(String playerA_q) {
        playerA_question.push(playerA_q);
    }

    public Stack<String> getPlayerB_question() {
        return playerB_question;
    }

    public void setPlayerB_question(String playerB_q) {
        playerB_question.push(playerB_q);
    }

    public Stack<String> getPlayerA_answer() {
        return playerA_answer;
    }

    public void setPlayerA_answer(String playerA_a) {
       playerA_answer.push(playerA_a);
    }
    public String popPlayerA_answer(){
        return playerA_answer.pop();
    }

    public Stack<String> getPlayerB_answer() {
        return playerB_answer;
    }

    public void setPlayerB_answer(String playerB_a) {
        playerB_answer.push(playerB_a);
    }

    public String popPlayerB_answer(){
        return playerB_answer.pop();
    }
    public List<Integer> getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(List<Integer> questionNum) {
        this.questionNum = questionNum;
    }
}
