package com.example.topikapp1;

import java.io.Serializable;

public class UserSet implements Serializable{

    private String number; // problem_number
    private String u_answer; //user_answer
    private String r_answer; //Real_answer
    private String final_result;
    //private int resId;

    public UserSet(String number, String user_answer, String real_answer) {
        this.number = number;
        this.u_answer = user_answer;
        this.r_answer = real_answer;
        if(Integer.parseInt(u_answer) == Integer.parseInt(r_answer)){
            this.final_result = "O";
        } else {
            this.final_result = "X";
        }
    }

    // UserSet 만들 때 쓰는 것

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public void setFinal_result(String final_result) {this.final_result = final_result;}

//    public int getResId() {
//        return resId;
//    }
//
//    public void setResId(int resId) {
//        this.resId = resId;
//    }
}

