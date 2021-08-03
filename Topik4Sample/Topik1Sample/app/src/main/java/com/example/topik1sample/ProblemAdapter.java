package com.example.topik1sample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ProblemAdapter extends BaseAdapter{
    private final List<ProblemSet> mData;
    private UserAdapter uAdapter= new UserAdapter();

    public ProblemAdapter(List<ProblemSet> mData) {
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    } //아이템 갯수

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //최적화 작업을 위해서 뷰를 한번 로드하면 재사용하고 표시할 내용만 교체하기 위한 클래스
    static class ViewHolder{
        TextView number;
        TextView common_question;
        TextView plural_question;
        TextView problemTextView;
        TextView textTextView;
        RadioGroup radioGroup;
        RadioButton choice1Radio;
        RadioButton choice2Radio;
        RadioButton choice3Radio;
        RadioButton choice4Radio;
        TextView exampleText;
        TextView solutionText;

        TextView problemUserAnswer;
        TextView problemRealAnswer;
        TextView score;

        FrameLayout frameDraw;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) { //아이템 한 칸에 들어갈 레이아웃
        //MainActivity와 같은 activity에서는 setContentView해서 바로 가져오면 되지만
        //activity가 아닌 일반 class에서는 LayoutInflater로 로드한 View에서 명시적으로 findViewVyId를 사용해야함.
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_problem,parent,false); //item_weather에서 가져오기!
            //근데 이것만 가져오면 text랑 image가 안 나오니까 밑에처럼 각각의 View를 가져와야함
            //날씨, 도시, 기온 View
            TextView number = convertView.findViewById(R.id.number);
            TextView common_question = convertView.findViewById(R.id.common_question);
            TextView plural_question = convertView.findViewById(R.id.plural_question);
            TextView problemTextView = convertView.findViewById(R.id.problemTextView);
            TextView textTextView = convertView.findViewById(R.id.textTextView);
            RadioGroup radioGroup = convertView.findViewById(R.id.radioGroup);
            RadioButton choice1Radio = convertView.findViewById(R.id.choice1Radio);
            RadioButton choice2Radio = convertView.findViewById(R.id.choice2Radio);
            RadioButton choice3Radio = convertView.findViewById(R.id.choice3Radio);
            RadioButton choice4Radio = convertView.findViewById(R.id.choice4Radio);
            TextView exampleText = convertView.findViewById(R.id.exampleText);
            TextView solutionText = convertView.findViewById(R.id.solutionText);

            TextView problemUserAnswer = convertView.findViewById(R.id.problemUserAnswer);
            TextView problemRealAnswer = convertView.findViewById(R.id.problemRealAnswer);
            TextView score = convertView.findViewById(R.id.prob_point);

            FrameLayout frameDraw = convertView.findViewById(R.id.frameDraw);


            holder.number = number;
            holder.common_question = common_question;
            holder.plural_question = plural_question;
            holder.problemTextView = problemTextView;
            holder.textTextView = textTextView;
            holder.radioGroup =radioGroup;
            holder.choice1Radio = choice1Radio;
            holder.choice2Radio = choice2Radio;
            holder.choice3Radio = choice3Radio;
            holder.choice4Radio = choice4Radio;
            holder.exampleText = exampleText;
            holder.solutionText = solutionText;
            holder.frameDraw = frameDraw;

            holder.problemUserAnswer = problemUserAnswer;
            holder.problemRealAnswer = problemRealAnswer;
            holder.score = score;

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

                void DeleteSetItem(Integer userNumber, int problemNumber, int point) {
                    Log.d("g확인용", String.valueOf(problemNumber));
                    uAdapter.deleteItem(problemNumber);
                    uAdapter.addItem(new UserSet(String.valueOf(problemNumber), String.valueOf(userNumber), problemRealAnswer.getText().toString(),
                            String.valueOf(point)));
                }

                private void ChangeAnswer(String answer) {
                    problemUserAnswer.setText(answer);
                    holder.problemUserAnswer = problemUserAnswer;
                    int numInt = Integer.parseInt(String.valueOf(problemUserAnswer.getText()));
                    if (String.valueOf(numInt) == String.valueOf(1)) {
                        DeleteSetItem(numInt, Integer.parseInt(number.getText().toString()), Integer.parseInt(score.getText().toString()));
                    } else if (String.valueOf(numInt) == String.valueOf(2)) {
                        Log.d("안녕2222", score.getText().toString());
                        DeleteSetItem(numInt, Integer.parseInt(number.getText().toString()), Integer.parseInt(score.getText().toString()));
                    } else if (String.valueOf(numInt) == String.valueOf(3)) {
                        DeleteSetItem(numInt, Integer.parseInt(number.getText().toString()), Integer.parseInt(score.getText().toString()));
                    } else {
                        DeleteSetItem(numInt, Integer.parseInt(number.getText().toString()), Integer.parseInt(score.getText().toString()));
                    }
                }

            });

            convertView.setTag(holder);
        } else{ //재사용 할 때
            holder = (ViewHolder) convertView.getTag();
        }
        ProblemSet problemSet = mData.get(position);
        //데이터 설정
        holder.number.setText(problemSet.getProb_num());
        holder.common_question.setText(problemSet.getQuestion());
        if(holder.common_question.getText().equals("")){
            holder.common_question.setVisibility(View.GONE);
        }

        holder.plural_question.setText(problemSet.getPlural_question());
        if(!holder.plural_question.getText().equals("")){
            holder.plural_question.setVisibility(View.VISIBLE);
        }

        holder.problemTextView.setText(problemSet.getQuestion_example());
        if(!holder.problemTextView.getText().equals("")){
            holder.exampleText.setVisibility(View.VISIBLE);
        } else{
            holder.exampleText.setVisibility(View.GONE);
        }
        holder.textTextView.setText(problemSet.getText());
        if(!holder.textTextView.getText().equals("")){
            holder.textTextView.setVisibility(View.VISIBLE);
        }

        holder.number.setText(String.valueOf(problemSet.getProb_num()));
        holder.score.setText(problemSet.getScore());
        holder.choice1Radio.setText(problemSet.getChoice1());
        holder.choice2Radio.setText(problemSet.getChoice2());
        holder.choice3Radio.setText(problemSet.getChoice3());
        holder.choice4Radio.setText(problemSet.getChoice4());
        holder.solutionText.setText(problemSet.getSolution());

        holder.problemRealAnswer.setText(problemSet.getAnswer());
        holder.problemUserAnswer.setText(problemSet.getUser_answer());

        String user_answer = problemSet.getUser_answer();
        String real_answer = problemSet.getAnswer();

        if (user_answer != null){
            Log.d("유저 엔서", user_answer);
            if(user_answer.equals("1")){
                Log.d("유저 엔서1", user_answer);
                holder.radioGroup.check(R.id.choice1Radio);
            }else if(user_answer.equals("2")){
                Log.d("유저 엔서2", user_answer);
                holder.radioGroup.check(R.id.choice2Radio);
            }else if(user_answer.equals("3")){
                Log.d("유저 엔서3", user_answer);
                holder.radioGroup.check(R.id.choice3Radio);
            }else if(user_answer.equals("4")){
                Log.d("유저 엔서4", user_answer);
                holder.radioGroup.check(R.id.choice4Radio);
            }

            //O, X그리기
            O_drawing O_drawing = new O_drawing(convertView.getContext());
            X_drawing X_drawing = new X_drawing(convertView.getContext());

            Log.d("user_answer", user_answer);
            Log.d("real_answer",real_answer);
            if(Integer.parseInt(user_answer) ==Integer.parseInt(real_answer) ){
                //Log.d("O","O");
                holder.frameDraw.addView(O_drawing);
            } else {
                //Log.d("X","X");
                holder.frameDraw.addView(X_drawing);
            }
        }
        return convertView;
    }
    public UserAdapter return_uAdapter(){
        final UserAdapter uAdapter = this.uAdapter;
        return uAdapter;
    }
}
