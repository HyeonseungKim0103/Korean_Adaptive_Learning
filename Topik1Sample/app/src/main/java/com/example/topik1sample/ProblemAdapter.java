package com.example.topik1sample;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProblemAdapter extends BaseAdapter{
    private final List<ProblemSet> mData;

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
        TextView number;
        TextView common_question;
        TextView plural_question;
        TextView problemTextView;
        TextView textTextView;
        TextView choice1Radio;
        TextView choice2Radio;
        TextView choice3Radio;
        TextView choice4Radio;
        TextView exampleText;
        RadioButton radio1;
        RadioButton radio2;
        RadioButton radio3;
        RadioButton radio4;

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
            TextView choice1Radio = convertView.findViewById(R.id.choice1Radio);
            TextView choice2Radio = convertView.findViewById(R.id.choice2Radio);
            TextView choice3Radio = convertView.findViewById(R.id.choice3Radio);
            TextView choice4Radio = convertView.findViewById(R.id.choice4Radio);
            TextView exampleText = convertView.findViewById(R.id.exampleText);
            RadioButton radio1 = convertView.findViewById(R.id.choice1Radio);
            RadioButton radio2 = convertView.findViewById(R.id.choice2Radio);
            RadioButton radio3 = convertView.findViewById(R.id.choice3Radio);
            RadioButton radio4 = convertView.findViewById(R.id.choice4Radio);


            holder.number = number;
            holder.common_question = common_question;
            holder.plural_question = plural_question;
            holder.problemTextView = problemTextView;
            holder.textTextView = textTextView;
            holder.choice1Radio = choice1Radio;
            holder.choice2Radio = choice2Radio;
            holder.choice3Radio = choice3Radio;
            holder.choice4Radio = choice4Radio;
            holder.exampleText = exampleText;
            holder.radio1 = radio1;
            holder.radio2 = radio2;
            holder.radio3 = radio3;
            holder.radio4 = radio4;
            convertView.setTag(holder);

        } else{ //재사용 할 때
            holder = (ViewHolder) convertView.getTag();
        }
        ProblemSet problemSet = mData.get(position);

        //라디오 버튼
        holder.radio1.setChecked(mData.get(position).checked1);
        holder.radio2.setChecked(mData.get(position).checked2);
        holder.radio3.setChecked(mData.get(position).checked3);
        holder.radio4.setChecked(mData.get(position).checked4);

        holder.radio1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean newState1 = !mData.get(position).isChecked1();
                mData.get(position).checked1 = newState1;
                mData.get(position).checked2 = false;
                mData.get(position).checked3 = false;
                mData.get(position).checked4 = false;

                holder.radio2.setChecked(false);
                holder.radio3.setChecked(false);
                holder.radio4.setChecked(false);
            }
        });

        holder.radio2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean newState2 = !mData.get(position).isChecked2();
                mData.get(position).checked2 = newState2;
                mData.get(position).checked1 = false;
                mData.get(position).checked3 = false;
                mData.get(position).checked4 = false;

                holder.radio1.setChecked(false);
                holder.radio3.setChecked(false);
                holder.radio4.setChecked(false);

            }
        });

        holder.radio3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean newState3 = !mData.get(position).isChecked3();
                mData.get(position).checked3 = newState3;
                mData.get(position).checked1 = false;
                mData.get(position).checked2 = false;
                mData.get(position).checked4 = false;

                holder.radio1.setChecked(false);
                holder.radio2.setChecked(false);
                holder.radio4.setChecked(false);

            }
        });

        holder.radio4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean newState4 = !mData.get(position).isChecked4();
                mData.get(position).checked4 = newState4;
                mData.get(position).checked1 = false;
                mData.get(position).checked2 = false;
                mData.get(position).checked3 = false;

                holder.radio1.setChecked(false);
                holder.radio2.setChecked(false);
                holder.radio3.setChecked(false);

            }
        });


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

        holder.choice1Radio.setText(problemSet.getChoice1());
        holder.choice2Radio.setText(problemSet.getChoice2());
        holder.choice3Radio.setText(problemSet.getChoice3());
        holder.choice4Radio.setText(problemSet.getChoice4());

        return convertView;
    }

}
