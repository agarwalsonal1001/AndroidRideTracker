package com.wondercars.ridetracker.Adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wondercars.ridetracker.R;
import com.wondercars.ridetracker.Retrofit.DTOs.GetExecutivesDTOs.ExecutivesDetailsObj;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by acer on 18/11/17.
 */

public class ExecutivesRecyclerAdapter extends RecyclerView.Adapter<ExecutivesRecyclerAdapter.MyViewHolder> {


    ArrayList<ExecutivesDetailsObj> executivesDetailsObjArrayList;
    Activity activity;
    OnItemClickListener onItemClickListener;


    public ExecutivesRecyclerAdapter(ArrayList<ExecutivesDetailsObj> executivesDetailsObjArrayList, Activity activity, OnItemClickListener onItemClickListener) {
        this.executivesDetailsObjArrayList = executivesDetailsObjArrayList;
        this.activity = activity;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position, ExecutivesDetailsObj object);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.executives_recycler_child_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvExecName.setText(executivesDetailsObjArrayList.get(position).getFullName());
        holder.tvExecNumber.setText(executivesDetailsObjArrayList.get(position).getPhoneNumber());
        holder.tvExecEmail.setText(executivesDetailsObjArrayList.get(position).getEmail());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onItemClickListener.onItemClick(v, position, executivesDetailsObjArrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return executivesDetailsObjArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvExecName;
        TextView tvExecNumber;
        TextView tvExecEmail;
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            tvExecName = (TextView) view.findViewById(R.id.tv_exec_name);
            tvExecNumber = (TextView) view.findViewById(R.id.tv_exec_number);
            tvExecEmail = (TextView) view.findViewById(R.id.tv_exec_email);

        }
    }
}
