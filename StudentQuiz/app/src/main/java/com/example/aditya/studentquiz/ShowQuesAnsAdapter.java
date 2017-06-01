package com.example.aditya.studentquiz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aditya on 16-05-2017.
 */

public class ShowQuesAnsAdapter extends RecyclerView.Adapter<ShowQuesAnsAdapter.ViewHolder> {

    private Context context;
    private List<QuesAns> list;

    ShowQuesAnsAdapter(Context context, List<QuesAns> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.show_ques_ans,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        QuesAns quesAns=list.get(position);
        holder.ques.setText("  "+quesAns.getQues());
        holder.ans.setText("  "+quesAns.getAns());
        holder.result.setText(" "+quesAns.getResult());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder  extends RecyclerView.ViewHolder{

        public TextView ques;
        public TextView ans;
        public TextView result;

        public ViewHolder(View view){
            super(view);
            result=(TextView)view.findViewById(R.id.result);
            ques=(TextView)view.findViewById(R.id.ques);
            ans=(TextView)view.findViewById(R.id.ans);
        }
    }
}
