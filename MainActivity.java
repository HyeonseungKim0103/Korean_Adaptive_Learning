//실제로 만들 때는 이 어플처럼 딱 필요한 부분만 테이블로 만들고,
//기록 할 때는 새로운 테이블에 즉, problem_set말고 다른 이름으로 똑같은 DB지만 다른 열 식으로 기록.
package com.example.problemtest1;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

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

    public static class ProblemViewHolder extends RecyclerView.ViewHolder{
        TextView problemTextView;
        TextView textTextView;
        TextView answerTextView;

        public ProblemViewHolder(@NonNull View itemView) {
            super(itemView);
            problemTextView = itemView.findViewById(R.id.problemTextView);
            textTextView = itemView.findViewById(R.id.textTextView);
            answerTextView = itemView.findViewById(R.id.answerTextView);
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

        mFirebaseAdapter = new FirebaseRecyclerAdapter<ProblemSet, ProblemViewHolder>(options){
            @Override
            protected void onBindViewHolder(ProblemViewHolder problemViewHolder, int i, ProblemSet problemSet) {
                problemViewHolder.problemTextView.setText(problemSet.getProblem());
                problemViewHolder.textTextView.setText(problemSet.getText());
                problemViewHolder.answerTextView.setText(problemSet.getAnswer());
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
}