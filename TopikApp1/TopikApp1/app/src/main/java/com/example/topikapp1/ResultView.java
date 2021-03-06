package com.example.topikapp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResultView extends LinearLayout {
    private TextView resultNumber;
    private TextView userAnswer;
    private TextView realAnswer;
    private TextView result;

    public ResultView(Context context, UserSet mUserSet){
        super(context);

        //인플레이션
        LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_user,this,true);

        //set icon
        resultNumber = findViewById(R.id.resultNumber);
        //mIcon.setImageResource(data.getIcon());
        userAnswer = findViewById(R.id.userAnswer);

        realAnswer = findViewById(R.id.realAnswer);

        result = findViewById(R.id.result);;
    }

    public void setResultNumber(String data) {resultNumber.setText(data);};

    public void setUserAnswer(String data) {userAnswer.setText(data);};

    public void setRealAnswer(String data) {realAnswer.setText(data);}

    public void setResult(String data) {result.setText(data);};
}
