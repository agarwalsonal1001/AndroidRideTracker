package com.wondercars.ridetracker.Adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    boolean showDeleteIcon;


    public ExecutivesRecyclerAdapter(ArrayList<ExecutivesDetailsObj> executivesDetailsObjArrayList, Activity activity, OnItemClickListener onItemClickListener) {
        this.executivesDetailsObjArrayList = executivesDetailsObjArrayList;
        this.activity = activity;
        this.onItemClickListener = onItemClickListener;
    }

    public ExecutivesRecyclerAdapter(ArrayList<ExecutivesDetailsObj> executivesDetailsObjArrayList, Activity activity, OnItemClickListener onItemClickListener, boolean showDeleteIcon) {
        this.executivesDetailsObjArrayList = executivesDetailsObjArrayList;
        this.activity = activity;
        this.onItemClickListener = onItemClickListener;
        this.showDeleteIcon = showDeleteIcon;
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
        try {
            final ExecutivesDetailsObj executivesDetailsObj = executivesDetailsObjArrayList.get(position);
            if (executivesDetailsObj != null) {
                holder.tvExecName.setText(executivesDetailsObj.getFullName());
                holder.tvExecNumber.setText(executivesDetailsObj.getPhoneNumber());
                holder.tvExecEmail.setText(executivesDetailsObj.getEmail());
                holder.tv_exec_designation.setText(executivesDetailsObj.getDesignation());

                if (showDeleteIcon) {
                    holder.ib_delete_executive.setVisibility(View.VISIBLE);
                    holder.ib_delete_executive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (onItemClickListener != null)
                                onItemClickListener.onItemClick(v, position, executivesDetailsObj);
                        }
                    });
                } else {
                    holder.ib_delete_executive.setVisibility(View.GONE);
                }
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (onItemClickListener != null)
                            onItemClickListener.onItemClick(v, position, executivesDetailsObj);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return executivesDetailsObjArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvExecName;
        TextView tvExecNumber;
        TextView tvExecEmail;
        TextView tv_exec_designation;
        CardView cardView;
        ImageButton ib_delete_executive;

        public MyViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            tvExecName = (TextView) view.findViewById(R.id.tv_exec_name);
            tvExecNumber = (TextView) view.findViewById(R.id.tv_exec_number);
            tvExecEmail = (TextView) view.findViewById(R.id.tv_exec_email);
            tv_exec_designation = (TextView) view.findViewById(R.id.tv_exec_designation);
            ib_delete_executive = (ImageButton) view.findViewById(R.id.ib_delete_executive);

        }
    }
}
