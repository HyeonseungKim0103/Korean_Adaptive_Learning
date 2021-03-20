package com.example.problemtest1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends BaseAdapter{
    private final List<UserSet> mData;
    public UserAdapter(List<UserSet> data) {
        mData = data;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user,parent,false);

            TextView idText = convertView.findViewById(R.id.problem_id);
            TextView userText = convertView.findViewById(R.id.user_text);
            TextView problemText = convertView.findViewById(R.id.problem_text);
            holder.idText = idText;
            holder.userText = userText;
            holder.problemText = problemText;
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
        }

        UserSet userSet = mData.get(position);
        holder.idText.setText(userSet.getId());
        holder.userText.setText(userSet.getU_answer());
        holder.problemText.setText(userSet.getP_answer());
        return convertView;
    }

    static class ViewHolder{
        TextView idText;
        TextView userText;
        TextView problemText;
    }


}
