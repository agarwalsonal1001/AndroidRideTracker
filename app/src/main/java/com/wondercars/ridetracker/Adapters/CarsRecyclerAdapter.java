package com.wondercars.ridetracker.Adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wondercars.ridetracker.R;
import com.wondercars.ridetracker.Retrofit.DTOs.GetCarsDTOs.CarDetailObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetExecutivesDTOs.ExecutivesDetailsObj;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by acer on 18/11/17.
 */

public class CarsRecyclerAdapter extends RecyclerView.Adapter<CarsRecyclerAdapter.MyViewHolder> {


    ArrayList<CarDetailObj> carDetailObjArrayList;
    Activity activity;
    OnItemClickListener onItemClickListener;


    public CarsRecyclerAdapter( Activity activity,ArrayList<CarDetailObj> carDetailObjArrayList, OnItemClickListener onItemClickListener) {
        this.carDetailObjArrayList = carDetailObjArrayList;
        this.activity = activity;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position, CarDetailObj object);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cars_recycler_child_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvCarNumber.setText(carDetailObjArrayList.get(position).getRegistrationNumber());
        holder.tvCarVariant.setText(carDetailObjArrayList.get(position).getVariantName());
        holder.tvCarFuelType.setText(carDetailObjArrayList.get(position).getFuelType());
        holder.tvCarMode.setText(carDetailObjArrayList.get(position).getMode());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onItemClickListener.onItemClick(v, position, carDetailObjArrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return carDetailObjArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_car_number)
        TextView tvCarNumber;
        @BindView(R.id.tv_car_fuel_type)
        TextView tvCarFuelType;
        @BindView(R.id.tv_car_variant)
        TextView tvCarVariant;
        @BindView(R.id.tv_car_mode)
        TextView tvCarMode;
        @BindView(R.id.card_view)
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
