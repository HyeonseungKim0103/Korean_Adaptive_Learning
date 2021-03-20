package com.example.problemtest1;

import java.io.Serializable;
import java.util.ArrayList;

public class UserSet implements Serializable{

    public ArrayList<String[]> items = new ArrayList<String[]>();
    String u_answer; //user_answer
    String p_answer; //problem_answer
    String id;

    public UserSet(String id,String user_answer, String problem_answer){
        this.id = id;
        this.u_answer= user_answer;
        this.p_answer= problem_answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // UserSet 만들 때 쓰는 것
    public String getU_answer() { return u_answer; }

    public void setU_answer(String u_answer){
        this.u_answer =u_answer;
    }

    public String getP_answer(){
        return p_answer;
    }

    public void setP_answer(String p_answer){
        this.p_answer =p_answer;
    }

    public int getItemCount(){
        return items.size();
    }

    //UserSet array 사용
    public void addItem(UserSet item){
        String[] itemData = {item.u_answer,item.p_answer};
        items.add(itemData);
    }

    public void setItems(ArrayList<String[]> items){
        this.items = items;
    }

    public String[] getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, UserSet item) {

        String[] itemData = {item.u_answer, item.p_answer};
        items.set(position, itemData);
    }
}
