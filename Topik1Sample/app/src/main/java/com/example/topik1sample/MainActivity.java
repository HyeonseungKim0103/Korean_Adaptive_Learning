package com.example.topik1sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity{
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

    TextView exampleText;
    TextView problemTextView;

    JSONArray problemList = null;
    ArrayList<HashMap<String,String>> probList; //여기에 DB의 data를 다 넣을 거임.

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = findViewById(R.id.listView);
        probList = new ArrayList<HashMap<String, String>>(); //우리껄로 만들거면 우리가 이미 만들어놓은 ProblemSet class나 UserSet class Type으로
        //리스트 만들면 될 듯.
//        getData("http://192.168.0.5:5000/topik1_exam"); // 스벅에서
        getData("http://172.17.201.149:5000/topik1_exam");

        //돌리려면 VS code를 실행해놓고 해야 나옴. 실행 안 하면 빈화면만 출력.

        exampleText = findViewById(R.id.exampleText);
        problemTextView = findViewById(R.id.problemTextView);

    }

    protected void showList(){ //가공은 String으로 다 하고 List로 만들때 int로 바꿔서 만들 수 있을까?
        try{
            JSONObject jsonObject = new JSONObject(myJson); //가공된 json 파일.
            problemList = jsonObject.getJSONArray(TAG_RESULTS);

            for(int i = 0; i < problemList.length(); i++){
                JSONObject c = problemList.getJSONObject(i);
                String prob_num = c.getString(PROB_NUM);
                String question = c.getString(QUESTION); //각 칼럼들 가져오기. 여기서는 다 String이네.
                String plural_question = c.getString(PLURAL_QUESTION);
                String question_example = c.getString(QUESTION_EXAMPLE);
                String text = c.getString(TEXT);
                String choice1 = c.getString(CHOICE1);
                String choice2 = c.getString(CHOICE2);
                String choice3 = c.getString(CHOICE3);
                String choice4 = c.getString(CHOICE4);

                if(question.equals("NA")){
                    question = "";
                }

                if(plural_question.equals("NA")){
                    plural_question = "";
                }


                if(question_example.equals("NA")){
                    question_example = "";
                }
                if(text.equals("NA")){
                    text = "";
                }


                prob_data.add(new ProblemSet(prob_num, question,plural_question ,question_example, text, choice1,
                        choice2, choice3, choice4));

            }

            ProblemAdapter adapter = new ProblemAdapter(prob_data);

            listview.setAdapter(adapter);
        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0]; //위에서 내가 써놨던 http://192.168.0.4/simpletopik1.php 이거 가져옴.
                //getDate(String url)을 params로 받아서 링크를 가져옴.

                BufferedReader bufferedReader = null;
                try{
                    URL url = new URL(uri); //String으로 받아온 uri를 URL 타입으로 변경
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim(); // 받아온 json의 공백 제거.
                } catch (Exception e){
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) { //doInBackground에서 return한 값을 받음.
                if(result != null){
                    myJson = result;
                    showList();

                } else{
                    Log.d("없다", "없다...");
                }
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    public void scoring(View view) {
    }
}