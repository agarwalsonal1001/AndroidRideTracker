package com.wondercars.ridetracker.Adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.wondercars.ridetracker.R;
import com.wondercars.ridetracker.Retrofit.DTOs.ViewAllRidesDTOs.RidesDetails;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by acer on 18/11/17.
 */

public class ViewAllRidesRecyclerAdapter extends RecyclerView.Adapter<ViewAllRidesRecyclerAdapter.MyViewHolder> {


    ArrayList<RidesDetails> bookingSlotsObjArrayList;
    Activity activity;
    OnItemClickListener onItemClickListener;


    public ViewAllRidesRecyclerAdapter(Activity activity, ArrayList<RidesDetails> bookingSlotsObjArrayList, OnItemClickListener onItemClickListener) {
        this.bookingSlotsObjArrayList = bookingSlotsObjArrayList;
        this.activity = activity;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position, RidesDetails object);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.childview_viewallrides_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {

            final RidesDetails ridesDetails = bookingSlotsObjArrayList.get(position);
            if (ridesDetails != null) {
                holder.tvEnquirynumber.setText(ridesDetails.getCustomerEnquiryNo());
                holder.tvCustomername.setText(ridesDetails.getCustomerName());
                holder.tvCustomerNumber.setText(ridesDetails.getCustomerMobile());
                holder.tvRideType.setText(ridesDetails.getRideType());
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onItemClickListener!=null)
                        onItemClickListener.onItemClick(v, position, ridesDetails);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return bookingSlotsObjArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_enquirynumber)
        TextView tvEnquirynumber;
        @BindView(R.id.tv_customername)
        TextView tvCustomername;
        @BindView(R.id.tv_customerNumber)
        TextView tvCustomerNumber;
        @BindView(R.id.tv_ride_type)
        TextView tvRideType;
        @BindView(R.id.card_view)
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
