package com.example.topik1sample;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class UserSet implements Serializable,Comparable<UserSet>{

    //정렬 기준
    private String prob_num; // problem_number

    private String u_answer; //user_answer
    private String r_answer; //Real_answer
    private String final_result;
    private String score;
    private int totalScore;
    //private int resId;

    public UserSet(String number, String user_answer, String real_answer, String score) {
        this.prob_num = number;
        this.u_answer = user_answer;
        this.r_answer = real_answer;
        this.score = score;
        if(Integer.parseInt(u_answer) == Integer.parseInt(r_answer)){
            this.final_result = "O";
        } else {
            this.final_result = "X";
        }
    }

    public UserSet(String number, String user_answer, String real_answer) {
        this.prob_num = number;
        this.u_answer = user_answer;
        this.r_answer = real_answer;
        if(Integer.parseInt(u_answer) == Integer.parseInt(r_answer)){
            this.final_result = "O";
        } else {
            this.final_result = "X";
        }
    }

    // UserSet 만들 때 쓰는 것

    public int getTotal() {
        return totalScore;
    }

    public void setTotal(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getProb_num() {
        return prob_num;
    }

    public void setNumber(String number) {
        this.prob_num = number;
    }

    public String getU_answer() {
        return u_answer;
    }

    public void setU_answer(String u_answer) {
        this.u_answer = u_answer;
    }

    public String getP_answer() {
        return r_answer;
    }

    public void setP_answer(String r_answer) {
        this.r_answer = r_answer;
    }

    public String getFinal_result() {return final_result;}

    public String getR_answer() {
        return r_answer;
    }

    public void setR_answer(String r_answer) {
        this.r_answer = r_answer;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setFinal_result(String final_result) {this.final_result = final_result;}

    @Override
    public int compareTo(@NotNull UserSet userSet) {
        return this.prob_num.compareTo(userSet.prob_num);
    }

//    public int getResId() {
//        return resId;
//    }
//
//    public void setResId(int resId) {
//        this.resId = resId;
//    }
}
