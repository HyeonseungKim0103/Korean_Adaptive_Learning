//실제로 만들 때는 이 어플처럼 딱 필요한 부분만 테이블로 만들고,
//기록 할 때는 새로운 테이블에 즉, problem_set말고 다른 이름으로 똑같은 DB지만 다른 열 식으로 기록.
//실제로 만들 때는 이 어플처럼 딱 필요한 부분만 테이블로 만들고,
//기록 할 때는 새로운 테이블에 즉, problem_set말고 다른 이름으로 똑같은 DB지만 다른 열 식으로 기록.
package com.example.problemtest1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import static android.view.View.GONE;
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
    public static final String PROBLEM_SET = "problem_set";
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<ProblemSet, ProblemViewHolder> mFirebaseAdapter;
    private int count = 1;

    //결과 변수

    //private UserSetAdapter userSetAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;

    //private ArrayList<UserSet> userSet = null;
    private CustomAdapter cAdapter = null;
    private ListView listView = null;


    public class ProblemViewHolder extends RecyclerView.ViewHolder {
        TextView number;
        TextView problemTextView;
        TextView exampleTextView;
        TextView choiceNumber1;
        TextView choiceNumber2;
        TextView choiceNumber3;
        TextView choiceNumber4;
        RadioButton choice1Radio;
        RadioButton choice2Radio;
        RadioButton choice3Radio;
        RadioButton choice4Radio;

        RadioGroup radioGroup;

        TextView problemUserAnswer;
        TextView problemRealAnswer;

        public ProblemViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.number);
            problemTextView = itemView.findViewById(R.id.problemTextView);
            exampleTextView = itemView.findViewById(R.id.exampleTextView);

            choiceNumber1 = itemView.findViewById(R.id.choiceNumber1);
            choiceNumber2 = itemView.findViewById(R.id.choiceNumber2);
            choiceNumber3 = itemView.findViewById(R.id.choiceNumber3);
            choiceNumber4 = itemView.findViewById(R.id.choiceNumber4);

            choice1Radio = itemView.findViewById(R.id.choice1Radio);
            choice2Radio = itemView.findViewById(R.id.choice2Radio);
            choice3Radio = itemView.findViewById(R.id.choice3Radio);
            choice4Radio = itemView.findViewById(R.id.choice4Radio);

            radioGroup = itemView.findViewById(R.id.radioGroup);
            //radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

            problemUserAnswer = itemView.findViewById(R.id.problemUserAnswer);
            problemRealAnswer = itemView.findViewById(R.id.problemRealAnswer);

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    View radioButton = radioGroup.findViewById(checkedId);
                    int index = radioGroup.indexOfChild(radioButton);
                    switch (index) {
                        case 0:
                            ChangeAnswer("1");
                            break;
                        case 1:
                            ChangeAnswer("2");
                            break;
                        case 2:
                            ChangeAnswer("3");
                            break;
                        case 3:
                            ChangeAnswer("4");
                            break;
                        default:
                            break;
                    }
                }

                void DeleteSetItem(int problemNumber) {
                    cAdapter.deleteItem(problemNumber);
                    cAdapter.addItem(new UserSet(number.getText().toString(), problemUserAnswer.getText().toString(), problemRealAnswer.getText().toString()));
                    //Toast.makeText(MainActivity.this, String.valueOf(number.getText()), Toast.LENGTH_LONG).show();
                    listView = findViewById(R.id.result_list_view); //activity_main.xml
                    listView.setAdapter(cAdapter);
                }

                private void ChangeAnswer(String answer) {
                    problemUserAnswer.setText(answer);
                    if (String.valueOf(number.getText()) == String.valueOf(1)) {
                        DeleteSetItem(1);
                    } else if (String.valueOf(number.getText()) == String.valueOf(2)) {
                        DeleteSetItem(2);
                    } else if (String.valueOf(number.getText()) == String.valueOf(3)) {
                        DeleteSetItem(3);
                    } else {
                        DeleteSetItem(4);
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cAdapter = new CustomAdapter(this);

//        RecyclerView result_recycler_view = findViewById(R.id.result_recycler_view); //activity_main.xml
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL,false);
//        result_recycler_view.setLayoutManager(layoutManager);
//
//        userSetAdapter = new UserSetAdapter();

        Button sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addResultLayout();
            }

            private void addResultLayout() {
                listView = (ListView) findViewById(R.id.result_list_view);

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                inflater.inflate(R.layout.user_result, listView, false);

                LinearLayout resultLayout = (LinearLayout) findViewById(R.id.result_layout);
                resultLayout.setVisibility(VISIBLE);
                listView.setVisibility(VISIBLE);
                //result_list_view.setAdapter(userSetAdapter);
                listView.setAdapter(cAdapter);
            }
        });

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

        Query query = mFirebaseDatabaseReference.child(PROBLEM_SET);
        FirebaseRecyclerOptions<ProblemSet> options = new FirebaseRecyclerOptions.Builder<ProblemSet>()
                .setQuery(query, ProblemSet.class)
                .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<ProblemSet, ProblemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(ProblemViewHolder problemViewHolder, int i, ProblemSet problemSet) {
                problemViewHolder.problemTextView.setText(problemSet.getProblem());
                if (problemSet.getExample() == null) {
                    problemViewHolder.exampleTextView.setVisibility(GONE);
                } else {
                    problemViewHolder.exampleTextView.setText(problemSet.getExample());
                    problemViewHolder.exampleTextView.setVisibility(VISIBLE);
                }
                problemViewHolder.choice1Radio.setText(problemSet.getChoice1());
                problemViewHolder.choice2Radio.setText(problemSet.getChoice2());
                problemViewHolder.choice3Radio.setText(problemSet.getChoice3());
                problemViewHolder.choice4Radio.setText(problemSet.getChoice4());
                problemViewHolder.problemRealAnswer.setText(String.valueOf(problemSet.getAnswer()));

                problemViewHolder.number.setText(String.valueOf(count));
//                problemViewHolder.radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);
                count += 1;

                //각 레디오 버튼을 클릭했을 때, 선택한 선지 번호 저장, UserSetAdapter에 아이템 추가
//                problemViewHolder.choice1Radio.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //int id = problemViewHolder.radioGroup.getCheckedRadioButtonId();
//                        problemViewHolder.problemUserAnswer.setText("1");
//                        cAdapter.addItem(new UserSet(problemViewHolder.number.getText().toString(),problemViewHolder.problemUserAnswer.getText().toString(),problemViewHolder.problemRealAnswer.getText().toString()));
//
//                        listView = findViewById(R.id.result_list_view); //activity_main.xml
//                        listView.setAdapter(cAdapter);
//                    }
//                });
//
//                problemViewHolder.choice2Radio.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //int id = problemViewHolder.radioGroup.getCheckedRadioButtonId();
//                        problemViewHolder.problemUserAnswer.setText("2");
//                        cAdapter.addItem(new UserSet(problemViewHolder.number.getText().toString(),problemViewHolder.problemUserAnswer.getText().toString(),problemViewHolder.problemRealAnswer.getText().toString()));
//
//                        listView = findViewById(R.id.result_list_view); //activity_main.xml
//                        listView.setAdapter(cAdapter);
//                    }
//                });
//
//                problemViewHolder.choice3Radio.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //int id = problemViewHolder.radioGroup.getCheckedRadioButtonId();
//                        problemViewHolder.problemUserAnswer.setText("3");
//
//                        cAdapter.addItem(new UserSet(problemViewHolder.number.getText().toString(),problemViewHolder.problemUserAnswer.getText().toString(),problemViewHolder.problemRealAnswer.getText().toString()));
//
//                        listView = findViewById(R.id.result_list_view); //activity_main.xml
//                        listView.setAdapter(cAdapter);
//                    }
//                });
//
//                problemViewHolder.choice4Radio.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int id = problemViewHolder.radioGroup.getCheckedRadioButtonId();
//                        problemViewHolder.problemUserAnswer.setText("4");
//                        cAdapter.addItem(new UserSet(problemViewHolder.number.getText().toString(), problemViewHolder.problemUserAnswer.getText().toString(), problemViewHolder.problemRealAnswer.getText().toString()));
//
//                        listView = findViewById(R.id.result_list_view); //activity_main.xml
//                        listView.setAdapter(cAdapter);
//                    }
//                });
            }



            @NonNull
            @Override
            public ProblemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_problem, parent, false);

                return new ProblemViewHolder(view);
            }
        };

        count = 1;

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
}

