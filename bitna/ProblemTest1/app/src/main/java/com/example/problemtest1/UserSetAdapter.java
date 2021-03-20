//package com.example.problemtest1;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.firestore.auth.User;
//
//import java.util.ArrayList;
//
//public class UserSetAdapter extends CustomAdapter.Adapter<UserSetAdapter.ViewHolder> {
//
//    public ArrayList<UserSet> items = new ArrayList<UserSet>();
//
//    @NonNull
//
//    @Override
//
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup vg, int viewType){
//        LayoutInflater inflater = LayoutInflater.from(vg.getContext());
//
//        View itemView = inflater.inflate(R.layout.user_result,vg,false);
//        //ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.user_result,vg,false);
//
//        return new ViewHolder(itemView);
//        //return new ViewHolder(rootView);
//    }
//
//    @Override
//    public int getItemCount(){
//        return items.size();
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder vh, int position){
//        vh.onBind(items.get(position));
//    }
//
//    public void addItem(UserSet item){
//        items.add(item);
//    }
//
//    public void setItems(ArrayList<UserSet> items){
//        this.items = items;
//    }
//    public UserSet getItem(int position){
//        return items.get(position);
//    }
//
//    public void setItem(int position, UserSet item) {
//        items.set(position, item);
//    }
//
//    public class ViewHolder extends ListView.ViewHolder{
//
//        TextView number;
//        TextView user_answer;
//        TextView real_answer;
//        TextView result;
//
//        ViewHolder( View itemView) {
//            super(itemView);
//
//            number = itemView.findViewById(R.id.resultNumber);
//            user_answer = itemView.findViewById(R.id.userAnswer);
//            real_answer = itemView.findViewById(R.id.realAnswer);
//            result = itemView.findViewById(R.id.result);
//        }
//        void onBind(UserSet item) {
//            number.setText(item.getNumber());
//            user_answer.setText(item.getU_answer());
//            real_answer.setText(item.getP_answer());
//            if (user_answer.getText().toString() == real_answer.getText().toString()){
//                result.setText("O");
//            } else {
//                result.setText("X");
//            }
//        }
//    }
//}
//
