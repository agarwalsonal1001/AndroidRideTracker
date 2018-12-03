package com.wondercars.ridetracker.Adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wondercars.ridetracker.R;
import com.wondercars.ridetracker.Retrofit.DTOs.GetCarModelsDTOs.CarModels;
import com.wondercars.ridetracker.Retrofit.DTOs.GetCarsDTOs.CarDetailObj;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by acer on 18/11/17.
 */

public class CarModelsRecyclerAdapter extends RecyclerView.Adapter<CarModelsRecyclerAdapter.MyViewHolder> {


    ArrayList<CarModels> carModelsArrayList;
    Activity activity;
    OnItemClickListener onItemClickListener;


    public CarModelsRecyclerAdapter(Activity activity, ArrayList<CarModels> carModelsArrayList, OnItemClickListener onItemClickListener) {
        this.carModelsArrayList = carModelsArrayList;
        this.activity = activity;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position, CarModels object);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_models_recycler_child_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
            holder.tvCarMode.setText(carModelsArrayList.get(position).getCarModelName());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onItemClickListener.onItemClick(v, position, carModelsArrayList.get(position));
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return carModelsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tvCarModel)
        TextView tvCarMode;
        @BindView(R.id.cardView)
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
