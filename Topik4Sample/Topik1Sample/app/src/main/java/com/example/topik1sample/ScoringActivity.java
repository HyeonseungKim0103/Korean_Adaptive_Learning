package com.example.topik1sample;

//채점하기 버튼을 통해 넘어온 화면이다.
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import static android.view.View.VISIBLE;

public class ScoringActivity extends AppCompatActivity{

    private UserAdapter uAdapter;
    private ListView listView;
    private ArrayList<UserSet> user_list;
    private String prob_num;
    private String user_answer;
    private String real_answer;
    private String mRound;
    private String result;
    private String point;

    String myJson;
    ArrayList<ProblemSet> prob_data = new ArrayList<>();

    //따로 class 만들면 좋은데, 여기서는 Main에서 다 만들어 놓음.
    public static final String TAG_RESULTS = "result";
    public static final String PROB_NUM = "prob_num";
    public static final String QUESTION = "question";
    public static final String PLURAL_QUESTION = "plural_question";
    public static final String QUESTION_EXAMPLE = "question_example";
    public static final String TEXT = "text";
    public static final String CHOICE1 = "choice1";
    public static final String CHOICE2 = "choice2";
    public static final String CHOICE3 = "choice3";
    public static final String CHOICE4 = "choice4";
    public static final String ANSWER = "answer";
    private static final String RESULT = "result";
    private static final String PROB_ROUND = "prob_round";
    private static final String USER_ANSWER = "user_answer";
    private static final String REAL_ANSWER = "real_answer";

    TextView exampleText;
    TextView problemTextView;

    JSONArray problemList = null;
    ArrayList<HashMap<String,String>> probList; //여기에 DB의 data를 다 넣을 거임.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);
        listView = (ListView) findViewById(R.id.result_list_view2);
        probList = new ArrayList<HashMap<String, String>>(); //우리껄로 만들거면 우리가 이미 만들어놓은 ProblemSet class나 UserSet class Type으로

        exampleText = findViewById(R.id.exampleText);
        problemTextView = findViewById(R.id.problemTextView);

        uAdapter = new UserAdapter(this);
        Intent intent = getIntent();
        user_list = (ArrayList<UserSet>) intent.getSerializableExtra("user_list");
        mRound = intent.getStringExtra("prob_round");
        uAdapter.setList(user_list);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.item_user_result, listView, false);

        LinearLayout resultLayout = (LinearLayout) findViewById(R.id.result_layout2);
        resultLayout.setVisibility(VISIBLE);
        listView.setVisibility(VISIBLE);
        listView.setAdapter(uAdapter);

        //클릭 했을 때 해당 문제가 출력 되는 것. 해설도 같이
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                prob_num = (String) uAdapter.getNumber(position);
                Log.d("숫자1",prob_num);
                user_answer = (String) uAdapter.getU_answer(position);
                real_answer = (String) uAdapter.getP_answer(position);
                result = (String) uAdapter.getResult(position);
                point = (String) uAdapter.getScore(position);
                Intent intent = new Intent(ScoringActivity.this, SolutionActivity.class);
                intent.putExtra(PROB_NUM, prob_num);
                intent.putExtra(PROB_ROUND, mRound);
                intent.putExtra(USER_ANSWER, user_answer);
                intent.putExtra(REAL_ANSWER, real_answer);
                //intent.putExtra(RESULT,result);
                startActivity(intent);
            }
        });
    }
}