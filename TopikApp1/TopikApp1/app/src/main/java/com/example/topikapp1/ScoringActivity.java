package com.example.topikapp1;

//채점하기 버튼을 통해 넘어온 화면이다.
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;

public class ScoringActivity extends AppCompatActivity{
    private UserAdapter cAdapter;
    private ListView listView;
    private ArrayList<UserSet> user_list;
    private String prob_num;
    private String user_answer;
    private String mRound;
    public static final String PROB_NUM = "problemNum";
    private static final String PROB_ROUND = "prob_round";
    private static final String USER_ANSWER = "user_answer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);

        cAdapter = new UserAdapter(this);
        Intent intent = getIntent();
        user_list = (ArrayList<UserSet>) intent.getSerializableExtra("user_list");
        mRound = intent.getStringExtra("prob_round");
        cAdapter.setList(user_list);
        listView = (ListView) findViewById(R.id.result_list_view2);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.item_user, listView, false);

        LinearLayout resultLayout = (LinearLayout) findViewById(R.id.result_layout2);
        resultLayout.setVisibility(VISIBLE);
        listView.setVisibility(VISIBLE);
        listView.setAdapter(cAdapter);

        //클릭 했을 때 해당 문제가 출력 되는 것. 해설도 같이
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                prob_num = (String) cAdapter.getNumber(position);
                user_answer = (String) cAdapter.getU_answer(position);
                Intent intent = new Intent(ScoringActivity.this, SolutionActivity.class);
                intent.putExtra(PROB_NUM,prob_num);
                intent.putExtra(PROB_ROUND, mRound);
                intent.putExtra(USER_ANSWER, user_answer);
                startActivity(intent);
            }
        });
    }
}