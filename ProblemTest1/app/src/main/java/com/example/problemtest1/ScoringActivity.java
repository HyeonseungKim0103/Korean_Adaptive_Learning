//채점하기 버튼을 통해 넘어온 화면이다.
package com.example.problemtest1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class ScoringActivity extends AppCompatActivity{
    TextView mUserScoring;
    TextView mUserText;
    TextView mProblemText;
    ListView mScoringResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);

        //user가 작성한 answer 가져오기
        //유저가 버튼을 누른 데이터만 저장하고, 그 정답 리스트를 바로 출력했음.
        //실제로는 파이어베이스에 있는 정답과 유저가 선택한 답을 불러와서 비교해서 출력해야 할 듯.
        mUserScoring = findViewById(R.id.user_scoring);
        mUserText = findViewById(R.id.user_text);
        mProblemText = findViewById(R.id.problem_text);
        mScoringResult = findViewById(R.id.scoring_result);
        Intent intent = getIntent();
        ArrayList<UserSet> user_answer = (ArrayList<UserSet>) intent.getSerializableExtra("user_answer");
        UserAdapter adapter = new UserAdapter(user_answer);
        mScoringResult.setAdapter(adapter);
    }
}