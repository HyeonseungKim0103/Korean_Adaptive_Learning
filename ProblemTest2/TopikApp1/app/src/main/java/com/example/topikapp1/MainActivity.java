package com.example.topikapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<ProblemSet, ProblemViewHolder> mFirebaseAdapter;
    private int count = 1;

    public class ProblemViewHolder extends RecyclerView.ViewHolder {
        TextView problemNumber;
        TextView problemTextView;
        TextView exampleTextView;
        TextView conversationTextView;
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
            problemNumber = itemView.findViewById(R.id.number); //문제 번호
            problemTextView = itemView.findViewById(R.id.problemTextView); //발문
            exampleTextView = itemView.findViewById(R.id.exampleTextView); //보기
            conversationTextView = itemView.findViewById(R.id.conversationTextView); //대화문

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

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                }

            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        Query query = mFirebaseDatabaseReference.child(PROBLEM_LISTENING).child("prob_set35").orderByChild("prob_num");
        FirebaseRecyclerOptions<ProblemSet> options = new FirebaseRecyclerOptions.Builder<ProblemSet>()
                .setQuery(query, ProblemSet.class)
                .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<ProblemSet, ProblemViewHolder>(options){

            @Override
            protected void onBindViewHolder(ProblemViewHolder problemViewHolder, int i, ProblemSet problemSet) {
                if(problemSet.getMultiple_question() == null){
                    problemViewHolder.bracket1.setVisibility(INVISIBLE);
                    problemViewHolder.mark.setVisibility(INVISIBLE);
                    problemViewHolder.bracket2.setVisibility(INVISIBLE);
                    problemViewHolder.commonNumber1.setVisibility(INVISIBLE);
                    problemViewHolder.commonNumber2.setVisibility(INVISIBLE);
                } //else if(problemSet.getMultiple_question() != null && Integer.parseInt(problemSet.getMultiple_question()) == problemSet.getProb_num()-1){
//                    problemViewHolder.commonNumber1.setText(String.valueOf(problemSet.getProb_num()));
//                    problemViewHolder.commonNumber2.setText(String.valueOf(problemSet.getProb_num()+1));
//                }
                problemViewHolder.problemTextView.setText(problemSet.getQuestion());
                if (problemSet.getExample() == null) {
                    problemViewHolder.exampleTextView.setVisibility(GONE);
                } else {
                    problemViewHolder.exampleTextView.setText(problemSet.getExample());
                    problemViewHolder.exampleTextView.setVisibility(VISIBLE);
                }

                if (problemSet.getConversation() == null) {
                    problemViewHolder.conversationTextView.setVisibility(GONE);
                } else {
                    problemViewHolder.conversationTextView.setText(problemSet.getConversation());
                    problemViewHolder.conversationTextView.setVisibility(VISIBLE);
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
}
