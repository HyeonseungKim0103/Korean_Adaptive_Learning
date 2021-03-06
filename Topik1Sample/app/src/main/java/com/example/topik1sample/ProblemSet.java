package com.example.topik1sample;

import org.json.JSONObject;

public class ProblemSet{
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private String prob_num;
    private String question;
    private String plural_question;
    private String question_example;
    private String text;

    //라디오버튼
    boolean checked1;
    boolean checked2;
    boolean checked3;
    boolean checked4;

    public ProblemSet(){}

    public ProblemSet(String prob_num, String question, String plural_question, String question_example, String text, String choice1,
                      String choice2, String choice3, String choice4, Boolean b, Boolean b2, Boolean b3, Boolean b4){
        this.prob_num = prob_num;
        this.question = question;
        this.plural_question = plural_question;
        this.question_example = question_example;
        this.text = text;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.checked1 = b;
        this.checked2 = b2;
        this.checked3 = b3;
        this.checked4 = b4;

    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public String getChoice4() {
        return choice4;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public String getProb_num() {
        return prob_num;
    }

    public void setProb_num(String prob_num) {
        this.prob_num = prob_num;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPlural_question() {
        return plural_question;
    }

    public void setPlural_question(String plural_question) {
        this.plural_question = plural_question;
    }


    public String getQuestion_example() {
        return question_example;
    }

    public void setQuestion_example(String question_example) {
        this.question_example = question_example;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked1(){
        return checked1;
    }

    public boolean isChecked2(){
        return checked2;
    }

    public boolean isChecked3(){
        return checked3;
    }

    public boolean isChecked4(){
        return checked4;
    }
}