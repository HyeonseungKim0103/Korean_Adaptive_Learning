package com.example.topikapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    //로그인 관련 변수
    private GoogleApiClient mGoogleApiClient;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private String mUsername;
    private String mPhotoUrl;

    //문제변수
    private RecyclerView mRecyclerView;
    public static final String PROBLEM_LISTENING = "probset_listening";
    public static final String PROBLEM_READING = "probset_reading";
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<ProblemSet, ProblemViewHolder> mFirebaseAdapter;
    private int count = 1;

    ArrayList<String> roundList;
    String mRound;

    //결과 변수

    //private UserSetAdapter userSetAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    public static final String USER_LIST = "user_list";
    private List<UserSet> userList;

    //private ArrayList<UserSet> userSet = null;
    private UserAdapter cAdapter = null;
    private ListView listView = null;
    private static final String PROB_ROUND = "prob_round";


    public class ProblemViewHolder extends RecyclerView.ViewHolder {
        TextView commonQuestion;
        TextView commonContent;
        CardView commonCardView;
        TextView exampleText;
        TextView problemNumber;
        TextView problemTextView;
        TextView textTextView;
        TextView choiceNumber1;
        TextView choiceNumber2;
        TextView choiceNumber3;
        TextView choiceNumber4;
        RadioButton choice1Radio;
        RadioButton choice2Radio;
        RadioButton choice3Radio;
        RadioButton choice4Radio;

        //세트문제
        TextView bracket1;
        TextView bracket2;
        TextView commonNumber1;
        TextView commonNumber2;
        TextView mark;

        RadioGroup radioGroup;

        TextView problemUserAnswer;
        TextView problemRealAnswer;

        public ProblemViewHolder(@NonNull View itemView) {
            super(itemView);
            commonQuestion = itemView.findViewById(R.id.common_question); //공통발문
            commonContent = itemView.findViewById(R.id.common_content); //공통보기
            commonCardView = itemView.findViewById(R.id.cardView); //공통 보기 cardView
            exampleText = itemView.findViewById(R.id.exampleText); //보기라는 단순한 글자

            problemNumber = itemView.findViewById(R.id.number); //문제 번호
            problemTextView = itemView.findViewById(R.id.problemTextView); //발문
            textTextView = itemView.findViewById(R.id.textTextView); //보기


            choiceNumber1 = itemView.findViewById(R.id.choiceNumber1); //선지번호
            choiceNumber2 = itemView.findViewById(R.id.choiceNumber2);
            choiceNumber3 = itemView.findViewById(R.id.choiceNumber3);
            choiceNumber4 = itemView.findViewById(R.id.choiceNumber4);

            choice1Radio = itemView.findViewById(R.id.choice1Radio); //선지
            choice2Radio = itemView.findViewById(R.id.choice2Radio);
            choice3Radio = itemView.findViewById(R.id.choice3Radio);
            choice4Radio = itemView.findViewById(R.id.choice4Radio);

            //세트문제
            bracket1 = itemView.findViewById(R.id.bracket1);
            bracket2 = itemView.findViewById(R.id.bracket2);
            commonNumber1 = itemView.findViewById(R.id.common_number1);
            commonNumber2 = itemView.findViewById(R.id.common_number2);
            mark = itemView.findViewById(R.id.mark);


            radioGroup = itemView.findViewById(R.id.radioGroup);
            //radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

            problemUserAnswer = itemView.findViewById(R.id.problemUserAnswer);
            problemRealAnswer = itemView.findViewById(R.id.problemRealAnswer);
            //문제1. 새로운 아이템이 불러와지면 버튼이 체크되어 있음.
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.choice1Radio:
                            ChangeAnswer("1");
                            break;
                        case R.id.choice2Radio:
                            ChangeAnswer("2");
                            break;
                        case R.id.choice3Radio:
                            ChangeAnswer("3");
                            break;
                        case R.id.choice4Radio:
                            ChangeAnswer("4");
                            break;
                        default:
                            break;
                    }
                }

                void DeleteSetItem(Integer userNumber, int problemNumber) {
                    cAdapter.deleteItem(problemNumber);
                    cAdapter.addItem(new UserSet(String.valueOf(problemNumber), String.valueOf(userNumber), problemRealAnswer.getText().toString()));
                    //Toast.makeText(MainActivity.this, String.valueOf(number.getText()), Toast.LENGTH_LONG).show();
                }

                private void ChangeAnswer(String answer) {
                    problemUserAnswer.setText(answer);
                    int numInt = Integer.parseInt(String.valueOf(problemUserAnswer.getText()));
                    //Toast.makeText(MainActivity.this, "" + numInt, Toast.LENGTH_SHORT).show();
                    //이거 왜 이렇게 한 건지? 어차피 해당 번호 지울거면 DeleteSetItem(numInt)만 하면 되는 거 아닐까?
                    if (String.valueOf(numInt) == String.valueOf(1)) {
                        DeleteSetItem(numInt, Integer.parseInt(problemNumber.getText().toString()));
                    } else if (String.valueOf(numInt) == String.valueOf(2)) {
                        DeleteSetItem(numInt, Integer.parseInt(problemNumber.getText().toString()));
                    } else if (String.valueOf(numInt) == String.valueOf(3)) {
                        DeleteSetItem(numInt, Integer.parseInt(problemNumber.getText().toString()));
                    } else {
                        DeleteSetItem(numInt, Integer.parseInt(problemNumber.getText().toString()));
                    }
                }

            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cAdapter = new UserAdapter(this);

        //로그인
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }

        //문제 표시
        mRecyclerView = findViewById(R.id.problem_recycler_view);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        roundList = new ArrayList<>();
        roundList.add("prob_set35");
        roundList.add("prob_set36");
        roundList.add("prob_set52");
        roundList.add("prob_set60");
        roundList.add("prob_set64");

        Random rnd = new Random();
        int num = rnd.nextInt(4);
        //mRound = roundList.get(num);
        mRound = "prob_set64";
        Query query = mFirebaseDatabaseReference.child(PROBLEM_READING).child(mRound).orderByChild("prob_num");
        FirebaseRecyclerOptions<ProblemSet> options = new FirebaseRecyclerOptions.Builder<ProblemSet>()
                .setQuery(query, ProblemSet.class)
                .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<ProblemSet, ProblemViewHolder>(options){

            @Override
            protected void onBindViewHolder(ProblemViewHolder problemViewHolder, int i, ProblemSet problemSet) {

                 //else if(problemSet.getMultiple_question() != null && Integer.parseInt(problemSet.getMultiple_question()) == problemSet.getProb_num()-1){
//                    problemViewHolder.commonNumber1.setText(String.valueOf(problemSet.getProb_num()));
//                    problemViewHolder.commonNumber2.setText(String.valueOf(problemSet.getProb_num()+1));
//                }
                if (problemSet.getExample().equals("")) {
                    problemViewHolder.exampleText.setVisibility(GONE);
                    problemViewHolder.commonCardView.setVisibility(GONE);
                    problemViewHolder.commonContent.setVisibility(GONE);

                } else {
                    problemViewHolder.exampleText.setVisibility(VISIBLE);
                    problemViewHolder.commonCardView.setVisibility(VISIBLE);
                    problemViewHolder.commonContent.setText(problemSet.getExample());
                    problemViewHolder.commonContent.setVisibility(VISIBLE);
                }

                if (problemSet.getQuestion().equals("")) {
                    problemViewHolder.commonQuestion.setVisibility(GONE);
                } else {
                    problemViewHolder.commonQuestion.setText(problemSet.getQuestion());
                    problemViewHolder.commonQuestion.setVisibility(VISIBLE);
                }

                if (problemSet.getText().equals("")) {
                    problemViewHolder.textTextView.setVisibility(GONE);
                } else {
                    problemViewHolder.textTextView.setText(problemSet.getText());
                    problemViewHolder.textTextView.setVisibility(VISIBLE);
                }

                problemViewHolder.choice1Radio.setText(problemSet.getChoice1());
                problemViewHolder.choice2Radio.setText(problemSet.getChoice2());
                problemViewHolder.choice3Radio.setText(problemSet.getChoice3());
                problemViewHolder.choice4Radio.setText(problemSet.getChoice4());
                problemViewHolder.problemRealAnswer.setText(String.valueOf(problemSet.getAnswer()));
                problemViewHolder.problemNumber.setText(String.valueOf(problemSet.getProb_num()));
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

    //파이어베이스 어댑터 쓸 거면 무조건 이거 해줘야함.
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
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void scoring(View view) {
        //나중에 정렬시키는 거 해보기.
        //넘길 때 회차정보인 child도 같이 넘기기. 굳이 어레이에 묶을 필요 없을 듯.
        userList = new ArrayList<UserSet>();
        userList = (ArrayList<UserSet>) cAdapter.returnList();
        Intent intent = new Intent(MainActivity.this, ScoringActivity.class);
        intent.putExtra(USER_LIST, (Serializable) userList);
        intent.putExtra(PROB_ROUND, mRound);
        startActivity(intent);
    }
}
