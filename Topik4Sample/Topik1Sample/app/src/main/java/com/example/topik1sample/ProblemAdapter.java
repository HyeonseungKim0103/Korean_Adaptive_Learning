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

    public boolean isChecked1(int position){
        return mData.get(position).checked1;
    }

    public boolean isChecked2(int position){
        return mData.get(position).checked2;
    }

    public boolean isChecked3(int position){
        return mData.get(position).checked3;
    }

    public boolean isChecked4(int position){
        return mData.get(position).checked4;
    }


    //최적화 작업을 위해서 뷰를 한번 로드하면 재사용하고 표시할 내용만 교체하기 위한 클래스
    static class ViewHolder{
        TextView arranged_num;
        TextView number;
        TextView common_question;
        TextView plural_question;
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

        TextView exampleText;
        TextView solutionText;

        TextView problemUserAnswer;
        TextView problemRealAnswer;
        TextView score;
        TextView probSet;

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
            TextView arranged_num = convertView.findViewById(R.id.arrangedNumber);
            TextView number = convertView.findViewById(R.id.number);
            TextView common_question = convertView.findViewById(R.id.common_question);
            TextView plural_question = convertView.findViewById(R.id.plural_question);
            TextView problemTextView = convertView.findViewById(R.id.problemTextView);
            TextView textTextView = convertView.findViewById(R.id.textTextView);
            TextView choiceNumber1 = convertView.findViewById(R.id.choiceNumber1);
            TextView choiceNumber2 = convertView.findViewById(R.id.choiceNumber2);
            TextView choiceNumber3 = convertView.findViewById(R.id.choiceNumber3);
            TextView choiceNumber4 = convertView.findViewById(R.id.choiceNumber4);
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

            TextView probSet = convertView.findViewById(R.id.probSet);

            holder.arranged_num = arranged_num;
            holder.number = number;
            holder.common_question = common_question;
            holder.plural_question = plural_question;
            holder.problemTextView = problemTextView;
            holder.textTextView = textTextView;

            holder.choiceNumber1 = choiceNumber1;
            holder.choiceNumber2 = choiceNumber2;
            holder.choiceNumber3 = choiceNumber3;
            holder.choiceNumber4 = choiceNumber4;

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

            holder.probSet = probSet;

            convertView.setTag(holder);
        } else{ //재사용 할 때
            holder = (ViewHolder) convertView.getTag();
        }
        ProblemSet problemSet = mData.get(position);

        holder.choice1Radio.setChecked(mData.get(position).checked1);
        holder.choice2Radio.setChecked(mData.get(position).checked2);
        holder.choice3Radio.setChecked(mData.get(position).checked3);
        holder.choice4Radio.setChecked(mData.get(position).checked4);

        holder.choice1Radio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean newState1 = !mData.get(position).isChecked1();
                mData.get(position).checked1 = newState1;
                mData.get(position).checked2 = false;
                mData.get(position).checked3 = false;
                mData.get(position).checked4 = false;

                holder.choice2Radio.setChecked(false);
                holder.choice3Radio.setChecked(false);
                holder.choice4Radio.setChecked(false);
                ChangeAnswer("1");
            }
            private void ChangeAnswer(String answer) {
                holder.problemUserAnswer.setText(answer);
                int numInt = Integer.parseInt(String.valueOf(answer));
                if (String.valueOf(numInt) == String.valueOf(1)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(2)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(3)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                }
            }

            public void DeleteSetItem(Integer userNumber, int arrangedNumber, int problemNumber, int point) {
                uAdapter.deleteItem(arrangedNumber);
                uAdapter.addItem(new UserSet(String.valueOf(arrangedNumber),String.valueOf(problemNumber), String.valueOf(userNumber), holder.problemRealAnswer.getText().toString(),String.valueOf(problemSet.getProb_set())));
                //Log.d("prob_set,PAdapter",mData.get(position).getProb_set());
            }
        });

        holder.choice2Radio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean newState2 = !mData.get(position).isChecked2();
                mData.get(position).checked1 = false;
                mData.get(position).checked2 = newState2;
                mData.get(position).checked3 = false;
                mData.get(position).checked4 = false;

                holder.choice1Radio.setChecked(false);
                holder.choice3Radio.setChecked(false);
                holder.choice4Radio.setChecked(false);
                ChangeAnswer("2");
            }

            private void ChangeAnswer(String answer) {
                holder.problemUserAnswer.setText(answer);
                int numInt = Integer.parseInt(String.valueOf(answer));
                if (String.valueOf(numInt) == String.valueOf(1)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(2)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(3)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                }
            }

            public void DeleteSetItem(Integer userNumber, int arrangedNumber, int problemNumber, int point) {
                uAdapter.deleteItem(arrangedNumber);
                uAdapter.addItem(new UserSet(String.valueOf(arrangedNumber),String.valueOf(problemNumber), String.valueOf(userNumber), holder.problemRealAnswer.getText().toString(),String.valueOf(problemSet.getProb_set())));
                //Log.d("prob_set,PAdapter",mData.get(position).getProb_set());
            }
        });

        holder.choice3Radio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean newState3 = !mData.get(position).isChecked3();
                mData.get(position).checked1 = false;
                mData.get(position).checked2 = false;
                mData.get(position).checked3 = newState3;
                mData.get(position).checked4 = false;

                holder.choice1Radio.setChecked(false);
                holder.choice2Radio.setChecked(false);
                holder.choice4Radio.setChecked(false);
                ChangeAnswer("3");
            }

            private void ChangeAnswer(String answer) {
                holder.problemUserAnswer.setText(answer);
                int numInt = Integer.parseInt(String.valueOf(answer));
                if (String.valueOf(numInt) == String.valueOf(1)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(2)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(3)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                }
            }

            public void DeleteSetItem(Integer userNumber, int arrangedNumber, int problemNumber, int point) {
                uAdapter.deleteItem(arrangedNumber);
                uAdapter.addItem(new UserSet(String.valueOf(arrangedNumber),String.valueOf(problemNumber), String.valueOf(userNumber), holder.problemRealAnswer.getText().toString(),String.valueOf(problemSet.getProb_set())));
                //Log.d("prob_set,PAdapter",mData.get(position).getProb_set());
            }

        });

        holder.choice4Radio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean newState4 = !mData.get(position).isChecked4();
                mData.get(position).checked1 = false;
                mData.get(position).checked2 = false;
                mData.get(position).checked3 = false;
                mData.get(position).checked4 = newState4;

                holder.choice1Radio.setChecked(false);
                holder.choice2Radio.setChecked(false);
                holder.choice3Radio.setChecked(false);
                ChangeAnswer("4");
            }

            private void ChangeAnswer(String answer) {
                holder.problemUserAnswer.setText(answer);
                int numInt = Integer.parseInt(String.valueOf(answer));
                if (String.valueOf(numInt) == String.valueOf(1)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(2)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(3)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                }
            }

            public void DeleteSetItem(Integer userNumber, int arrangedNumber, int problemNumber, int point) {
                uAdapter.deleteItem(arrangedNumber);
                uAdapter.addItem(new UserSet(String.valueOf(arrangedNumber),String.valueOf(problemNumber), String.valueOf(userNumber), holder.problemRealAnswer.getText().toString(),String.valueOf(problemSet.getProb_set())));
                Log.d("prob_set,PAdapter",problemSet.getProb_set());
            }
        });

        //데이터 설정
        holder.number.setText(problemSet.getProb_num());
        holder.arranged_num.setText(problemSet.getArranged_num()                                         );
        holder.common_question.setText(problemSet.getQuestion());
        holder.probSet.setText(problemSet.getProb_set());
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
            //O, X그리기
            O_drawing O_drawing = new O_drawing(convertView.getContext());
            X_drawing X_drawing = new X_drawing(convertView.getContext());
            if(Integer.parseInt(user_answer) ==Integer.parseInt(real_answer) ){
                //Log.d("O","O");
                holder.frameDraw.addView(O_drawing);
            } else {
                //Log.d("X","X");
                holder.frameDraw.addView(X_drawing);
            }

            //solution 용
            if(user_answer.equals("1")){
                holder.choice1Radio.setChecked(TRUE);
                holder.choice2Radio.setChecked(FALSE);
                holder.choice3Radio.setChecked(FALSE);
                holder.choice4Radio.setChecked(FALSE);
            }
            else if(user_answer.equals("2")){
                holder.choice1Radio.setChecked(FALSE);
                holder.choice2Radio.setChecked(TRUE);
                holder.choice3Radio.setChecked(FALSE);
                holder.choice4Radio.setChecked(FALSE);
            }
            else if(user_answer.equals("3")){
                holder.choice1Radio.setChecked(FALSE);
                holder.choice2Radio.setChecked(FALSE);
                holder.choice3Radio.setChecked(TRUE);
                holder.choice4Radio.setChecked(FALSE);
            }
            else{
                holder.choice1Radio.setChecked(FALSE);
                holder.choice2Radio.setChecked(FALSE);
                holder.choice3Radio.setChecked(FALSE);
                holder.choice4Radio.setChecked(TRUE);
            }
        }
        return convertView;
    }

    public UserAdapter return_uAdapter(){
        final UserAdapter uAdapter = this.uAdapter;
        return uAdapter;
    }
}
