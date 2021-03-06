package com.example.topik1sample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ChoiceCatActivity extends AppCompatActivity{
    private Spinner spinner_cat;
    private Spinner spinner_cat2;
    private Spinner spinner_cat3;
    private Spinner spinner_cat_num;
    private Spinner spinner_prob_cat;
    private Spinner spinner_prob_cat2;
    private Spinner Two_total_spinner_prob_cat;
    private Spinner Three_total_spinner_prob_cat;

    private String choice_cat;
    private String choice_cat2;
    private String choice_cat3;
    private String choice_cat_num;
    private String choice_prob_cat;
    public static final String CHOICE_CAT = "choice_cat";
    public static final String CHOICE_CAT2 = "choice_cat2";
    public static final String CHOICE_CAT3 = "choice_cat3";
    public static final String CHOICE_CAT_NUM = "choice_cat_num";
    public static final String CHOICE_PROB_CAT = "choice_prob_cat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_cat);

        spinner_cat_num = findViewById(R.id.spinner_cat_num);
        spinner_cat = findViewById(R.id.spinner_cat);
        spinner_cat2 = findViewById(R.id.spinner_cat2);
        spinner_cat3 = findViewById(R.id.spinner_cat3);
        spinner_prob_cat = findViewById(R.id.spinner_prob_cat);
        spinner_prob_cat.setVisibility(View.VISIBLE);
        spinner_prob_cat2 = findViewById(R.id.spinner_prob_cat2);
        Two_total_spinner_prob_cat = findViewById(R.id.Two_total_spinner_prob_cat);
        Three_total_spinner_prob_cat = findViewById(R.id.Three_total_spinner_prob_cat);


        spinner_cat_num.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_cat_num = parent.getItemAtPosition(position).toString();
                if(choice_cat_num.equals("1???")){
                    spinner_cat2.setVisibility(View.GONE);
                    spinner_cat3.setVisibility(View.GONE);
                    spinner_prob_cat.setVisibility(View.VISIBLE);
                    spinner_prob_cat2.setVisibility(View.GONE);
                    Two_total_spinner_prob_cat.setVisibility(View.GONE);
                    Three_total_spinner_prob_cat.setVisibility(View.GONE);
                }else if(choice_cat_num.equals("2???")){
                    spinner_cat2.setVisibility(View.VISIBLE);
                    spinner_cat3.setVisibility(View.GONE);
                    spinner_prob_cat.setVisibility(View.GONE);
                    spinner_prob_cat2.setVisibility(View.GONE);
                    Two_total_spinner_prob_cat.setVisibility(View.VISIBLE);
                    Three_total_spinner_prob_cat.setVisibility(View.GONE);

                } else{
                    spinner_cat2.setVisibility(View.VISIBLE);
                    spinner_cat3.setVisibility(View.VISIBLE);
                    spinner_prob_cat.setVisibility(View.GONE);
                    spinner_prob_cat2.setVisibility(View.GONE);
                    Two_total_spinner_prob_cat.setVisibility(View.GONE);
                    Three_total_spinner_prob_cat.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //????????? ??????????????? ????????????, ???????????? ?????????! ????????? num??? ??????????????? ???????????? ??????.

        spinner_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_cat = parent.getItemAtPosition(position).toString();
                if(choice_cat_num.equals("1???")){
                    if(choice_cat.equals("?????? ??????")){
                        spinner_prob_cat.setVisibility(View.GONE);
                        spinner_prob_cat2.setVisibility(View.VISIBLE);
                    } else{
                        spinner_prob_cat2.setVisibility(View.GONE);
                        spinner_prob_cat.setVisibility(View.VISIBLE);
                    }
                } else {
                    spinner_prob_cat.setVisibility(View.GONE);
                    spinner_prob_cat2.setVisibility(View.GONE);
                }
                Log.d("choice_cat",choice_cat);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_cat2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_cat2 = parent.getItemAtPosition(position).toString();
                Log.d("choice_cat2",choice_cat2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_cat3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_cat3 = parent.getItemAtPosition(position).toString();
                Log.d("choice_cat3",choice_cat3);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_prob_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_prob_cat = parent.getItemAtPosition(position).toString();
                //Log.d("choice_prob",choice_prob_cat);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_prob_cat2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_prob_cat = parent.getItemAtPosition(position).toString();
                //Log.d("choice_prob",choice_prob_cat);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Two_total_spinner_prob_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_prob_cat = parent.getItemAtPosition(position).toString();
                Log.d("choice_prob",choice_prob_cat);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Three_total_spinner_prob_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_prob_cat = parent.getItemAtPosition(position).toString();
                Log.d("choice_prob",choice_prob_cat);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void go_solve_cat(View view) {
        //String value??? ????????? ?????? ==??? ????????? equals??? ???????????????...
        Intent intent = new Intent(this, SolveCatActivity.class);
//        if(choice_prob_cat.equals("5??????")){
//            choice_prob_cat = "5";
//        } else if(choice_prob_cat.equals("10??????")){
//            choice_prob_cat = "10";
//        } else if(choice_prob_cat.equals("3??????")){
//            choice_prob_cat = "3";
//        } else if(choice_prob_cat.equals("8??????")){
//            choice_prob_cat = "8";
//        }
        if(choice_prob_cat.equals("5??????")){
            choice_prob_cat = "5";
        } else if(choice_prob_cat.equals("10??????")){
            choice_prob_cat = "10";
        } else if(choice_prob_cat.equals("3??????")){
            choice_prob_cat = "3";
        } else if(choice_prob_cat.equals("8??????")){
            choice_prob_cat = "8";
        } else if(choice_prob_cat.equals("15??????")){
            choice_prob_cat = "15";
        } else if(choice_prob_cat.equals("20??????")){
            choice_prob_cat = "20";
        }

        Log.d("choice_prob_final",choice_prob_cat);

        intent.putExtra(CHOICE_CAT,choice_cat);
        if (choice_cat_num.equals("2???")){
            intent.putExtra(CHOICE_CAT2,choice_cat2);
        } else if(choice_cat_num.equals("3???")){
            intent.putExtra(CHOICE_CAT2,choice_cat2);
            intent.putExtra(CHOICE_CAT3,choice_cat3);
        }
        Log.d("?????? ??????",choice_cat_num);
        intent.putExtra(CHOICE_CAT_NUM,choice_cat_num);
        intent.putExtra(CHOICE_PROB_CAT,choice_prob_cat);
        startActivity(intent);
    }
}