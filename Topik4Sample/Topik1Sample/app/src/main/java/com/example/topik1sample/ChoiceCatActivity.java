package com.example.topik1sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ChoiceCatActivity extends AppCompatActivity{
    private Spinner spinner_cat;
    private Spinner spinner_prob_cat;
    private Spinner spinner_prob_cat2;
    private String choice_cat;
    private String choice_prob_cat;
    public static final String CHOICE_CAT = "choice_cat";
    public static final String CHOICE_PROB_CAT = "choice_prob_cat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_cat);

        spinner_cat = findViewById(R.id.spinner_cat);
        spinner_prob_cat = findViewById(R.id.spinner_prob_cat);
        spinner_prob_cat.setVisibility(View.VISIBLE);
        spinner_prob_cat2 = findViewById(R.id.spinner_prob_cat2);

        spinner_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_cat = parent.getItemAtPosition(position).toString();
                if(choice_cat.equals("상황 추론")){
                    spinner_prob_cat.setVisibility(View.GONE);
                    spinner_prob_cat2.setVisibility(View.VISIBLE);
                } else{
                    spinner_prob_cat2.setVisibility(View.GONE);
                    spinner_prob_cat.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_prob_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_prob_cat = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_prob_cat2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_prob_cat = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void go_solve_cat(View view) {
        //String value를 비교할 때는 ==이 아니라 equals를 사용해야함...
        Intent intent = new Intent(this, SolveCatActivity.class);
        if(choice_prob_cat.equals("5문제")){
            choice_prob_cat = "5";
        } else if(choice_prob_cat.equals("10문제")){
            choice_prob_cat = "10";
        } else if(choice_prob_cat.equals("3문제")){
            choice_prob_cat = "3";
        } else if(choice_prob_cat.equals("8문제")){
            choice_prob_cat = "8";
        }
        intent.putExtra(CHOICE_CAT,choice_cat);
        intent.putExtra(CHOICE_PROB_CAT,choice_prob_cat);
        startActivity(intent);
    }
}