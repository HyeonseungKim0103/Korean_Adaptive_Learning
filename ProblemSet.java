package com.example.problemtest1;

public class ProblemSet{
    private String id;
    private String problem;
    private String text;
    private String answer;

    public ProblemSet(){}

    public ProblemSet(String problem, String text, String answer) {
        this.problem = problem;
        this.text = text;
        this.answer = answer;
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
}
