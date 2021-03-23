//채점하기 버튼을 통해 넘어온 화면이다.
package com.example.problemtest1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;

public class ScoringActivity extends AppCompatActivity{
private CustomAdapter cAdapter;
private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);

        cAdapter = new CustomAdapter(this);
        Intent intent = getIntent();
        ArrayList<UserSet> user_list = (ArrayList<UserSet>) intent.getSerializableExtra("user_list");
        cAdapter.setList(user_list);
        listView = (ListView) findViewById(R.id.result_list_view2);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.user_result, listView, false);

        LinearLayout resultLayout = (LinearLayout) findViewById(R.id.result_layout2);
        resultLayout.setVisibility(VISIBLE);
        listView.setVisibility(VISIBLE);
        listView.setAdapter(cAdapter);


        //user가 작성한 answer 가져오기
        //유저가 버튼을 누른 데이터만 저장하고, 그 정답 리스트를 바로 출력했음.
        //실제로는 파이어베이스에 있는 정답과 유저가 선택한 답을 불러와서 비교해서 출력해야 할 듯.
//        mUserScoring = findViewById(R.id.user_scoring);
//        mUserText = findViewById(R.id.user_text);
//        mProblemText = findViewById(R.id.problem_text);
//        mScoringResult = findViewById(R.id.scoring_result);
//        Intent intent = getIntent();
//        ArrayList<UserSet> user_answer = (ArrayList<UserSet>) intent.getSerializableExtra("user_answer");
//        CustomAdapter adapter = new CustomAdapter(user_answer);
//        mScoringResult.setAdapter(adapter);
    }
}