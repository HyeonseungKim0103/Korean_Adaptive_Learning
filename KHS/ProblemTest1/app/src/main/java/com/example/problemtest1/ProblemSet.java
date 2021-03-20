package com.example.problemtest1;

public class ProblemSet{
    private String id;
    private String problem;
    private String text;
    private String answer;
    private String round;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;

    public ProblemSet(){}

    public ProblemSet(String id,String problem, String text, String answer,String choice1,String choice2,String choice3,
                      String choice4,String round) {
        this.problem = problem;
        this.text = text;
        this.answer = answer;
        this.round = round;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setRound(String round) { this.round = round; }

    public String getRound(){ return round; }

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

}
