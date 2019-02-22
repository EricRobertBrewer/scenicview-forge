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
        player_one_questions = new String[] {
                // Partner
                "Which city do your partner's parents live in?",
                "Which city does your partner live in?",
                "What is your partner's name?",
                "How many siblings does your partner have?",
                "Where do your partner's parents live?",
                "Does your partner live with his/her family?",
                "When did your partner have dinner with his/her family last time?",
                "Does your partner have children?",
                "Does your partner have pets?",
                "Is your partner married?",
                // You
                "Which city do your parents live in?",
                "Which city do you live in?",
                "What is your name?",
                "How many siblings do you have?",
                "Where do your parents live?",
                "Do you live with your family?",
                "When did you have dinner with his/her family last time?",
                "Do you have children?",
                "Do you have pets?",
                "Are you married?"
        };
        player_two_questions = new String[] {
                // You
                "Which city do your parents live in?",
                "Which city do you live in?",
                "What is your name?",
                "How many siblings do you have?",
                "Where do your parents live?",
                "Do you live with your family?",
                "When did you have dinner with your family last time?",
                "Do you have children?",
                "Do you have pets?",
                "Are you married?" ,
                // Partner
                "Which city do your partner's parents live?",
                "Which city does your partner live in?",
                "What is your partner's name?",
                "How many siblings does your partner have?",
                "Where do your partner's parents live?",
                "Does your partner live with his/her family?",
                "When did your partner have dinner with his/her family last time?",
                "Does your partner have children?",
                "Does your partner have pets?",
                "Is your partner married?"
        };
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
        return rand.nextInt(player_one_questions.length);
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
