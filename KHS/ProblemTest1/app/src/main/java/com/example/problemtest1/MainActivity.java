//실제로 만들 때는 이 어플처럼 딱 필요한 부분만 테이블로 만들고,
//기록 할 때는 새로운 테이블에 즉, problem_set말고 다른 이름으로 똑같은 DB지만 다른 열 식으로 기록.
package com.example.problemtest1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    //로그인 관련 변수
    private GoogleApiClient mGoogleApiClient;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private String mUsername;
    private String mPhotoUrl;

    //문제변수
    private RecyclerView mRecyclerView;
    public static final String PROBLEM_SET = "problem_set";
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<ProblemSet,ProblemViewHolder> mFirebaseAdapter;
    Button mScoring;
    Button mSaving;
    EditText mAnswerEdit;
    private String mUserAnswer;
    private String mProblemAnswer;
    private List<UserSet> mAnswerList;

    //User 풀이 기록
    public static final String USER_SET = "user_set";
    public static final String USER_ANSWER = "user_answer";
    public static final String PROBLEM_ANSWER = "problem_answer";
    private String problem_id;

    public class ProblemViewHolder extends RecyclerView.ViewHolder{
        TextView problemTextView;
        TextView textTextView;
        TextView answerTextView;
        TextView resultTextView;
        TextView choice1;
        TextView choice2;
        TextView choice3;
        TextView choice4;
        TextView problemIdText;
        RadioButton radio0;
        RadioButton radio1;
        RadioButton radio2;
        RadioButton radio3;

        public ProblemViewHolder(@NonNull View itemView) {
            super(itemView);
            problemTextView = itemView.findViewById(R.id.problemTextView);
            textTextView = itemView.findViewById(R.id.textTextView);
            answerTextView = itemView.findViewById(R.id.answerTextView);
            resultTextView = itemView.findViewById(R.id.result_text);
            choice1 = itemView.findViewById(R.id.choice1_text);
            choice2 = itemView.findViewById(R.id.choice2_text);
            choice3 = itemView.findViewById(R.id.choice3_text);
            choice4 = itemView.findViewById(R.id.choice4_text);
            problemIdText = itemView.findViewById(R.id.problem_id);
            RadioButton radio0 = itemView.findViewById(R.id.radio0);
            RadioButton radio1 = itemView.findViewById(R.id.radio1);
            RadioButton radio2 = itemView.findViewById(R.id.radio2);
            RadioButton radio3 = itemView.findViewById(R.id.radio3);
            RadioGroup radioGroup = itemView.findViewById(R.id.radioGroup1);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(checkedId == R.id.radio0){
                        resultTextView.setText("1");
                        mUserAnswer = "1";
                        mProblemAnswer = "3";
                        problem_id = "64-1";
                        UserSet userSet = new UserSet(problem_id,mUserAnswer,mProblemAnswer);
                        if(!radio0.isChecked()){
                            mAnswerList.add(userSet);
                        } else{
                            mAnswerList.clear();
                        }
                    } else if(checkedId == R.id.radio1) {
                        resultTextView.setText("2");
                        mUserAnswer = "2";
                        mProblemAnswer = "3";
                        problem_id = "64-2";
                        UserSet userSet = new UserSet(problem_id,mUserAnswer,mProblemAnswer);
                        if(!radio1.isChecked()){
                            mAnswerList.add(userSet);
                        } else{
                            mAnswerList.clear();
                        }

                    } else if(checkedId == R.id.radio2) {
                        resultTextView.setText("3");
                        mUserAnswer = "3";
                        mProblemAnswer = "3";
                        problem_id = "64-3";
                        UserSet userSet = new UserSet(problem_id,mUserAnswer,mProblemAnswer);
                        if(!radio2.isChecked()){
                            mAnswerList.add(userSet);
                        } else{
                            mAnswerList.clear();
                            mAnswerList.add(userSet);
                        }

                    } else if(checkedId == R.id.radio3) {
                        resultTextView.setText("4");
                        mUserAnswer = "4";
                        mProblemAnswer = "3";
                        problem_id = "64-4";
                        UserSet userSet = new UserSet(problem_id,mUserAnswer,mProblemAnswer);
                        if(!radio3.isChecked()){
                            mAnswerList.add(userSet);
                        } else{
                            mAnswerList.clear();
                            mAnswerList.add(userSet);
                        }

                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSaving = findViewById(R.id.saving_button);
        mScoring = findViewById(R.id.scoring_button);
        mAnswerEdit = findViewById(R.id.answer_edit);
        mAnswerList = new ArrayList<>(); //사용자가 기록한 정답을 저장하기 위한 리스트

        //로그인
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if(mFirebaseUser == null){
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else{
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null){
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }

        //문제 표시
        mRecyclerView = findViewById(R.id.problem_recycler_view);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        List<String> random_round = new ArrayList<>();
        random_round.add("64");
        random_round.add("35");
        random_round.add("1");
        random_round.add("2");
        Random random = new Random();

        Query query = mFirebaseDatabaseReference.child(PROBLEM_SET).orderByChild("round").equalTo("64");
        //Query query = mFirebaseDatabaseReference.child(PROBLEM_SET).orderByChild("round").equalTo(random_round.get(random.nextInt(4)));
        //여기서 실제로 돌릴 때는 저기 equalTo 부분을 우리가 가지고 있는 회차 리스트를 만들어서 랜덤하게 넣어서 보여주면 될듯.
        // 또 뒤에 orderByChild해서 문제 번호 열이 있을 때 그것까지도 정렬되는지 나중에 확인해 볼 것. 번호 순서대로 나오게.
        FirebaseRecyclerOptions<ProblemSet> options = new FirebaseRecyclerOptions.Builder<ProblemSet>()
                .setQuery(query, ProblemSet.class)
                .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<ProblemSet, ProblemViewHolder>(options){
            @Override
            protected void onBindViewHolder(ProblemViewHolder problemViewHolder, int i, ProblemSet problemSet) {
                problemViewHolder.problemTextView.setText(problemSet.getProblem());
                problemViewHolder.textTextView.setText(problemSet.getText());
                problemViewHolder.choice1.setText(problemSet.getChoice1());
                problemViewHolder.choice2.setText(problemSet.getChoice2());
                problemViewHolder.choice3.setText(problemSet.getChoice3());
                problemViewHolder.choice4.setText(problemSet.getChoice4());
            }

            @NonNull
            @Override
            public ProblemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_problem, parent, false);
                return new ProblemViewHolder(view);
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFirebaseAdapter.stopListening();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show();
    }

    //로그아웃
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();;
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mUsername = "";
                startActivity(new Intent(this,SignInActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //저장하기 버튼, Firebase에 기록 저장 및 list에 정보 저장
    //일단 problem_answer은 문제 테이블에서 불러와야 하는데 그건 보류
    //라디오 버튼이면, 버튼 체크할 때의 값을 List로 저장하면 되겠지.
    //현재는 내가 저장한 정답들을 List에 추가해서 다른 화면으로 넘겼음.
    public void saving(View view) {
        ProblemSet problemSet = new ProblemSet();
//        mUserAnswer = "4";
//        mProblemAnswer = "3";
//        problem_id = "64-4";
//        UserSet userSet = new UserSet(problem_id,mUserAnswer,mProblemAnswer);
//        mFirebaseDatabaseReference.child(USER_SET)
//                .push()
//                .setValue(userSet);

    }

    public void scoring(View view) {
        Intent intent = new Intent(this, ScoringActivity.class);
        intent.putExtra(USER_ANSWER, (Serializable) mAnswerList);
        startActivity(intent);
    }

}