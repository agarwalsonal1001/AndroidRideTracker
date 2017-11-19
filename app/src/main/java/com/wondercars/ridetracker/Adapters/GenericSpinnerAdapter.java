package com.wondercars.ridetracker.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wondercars.ridetracker.Retrofit.DTOs.GetVeriantsDTOs.VeriantsDetails;
import com.wondercars.ridetracker.Utils.AppConstants;

import java.util.List;



/**
 * Created by ubuntu-dev002 on 9/11/16.
 */
public class GenericSpinnerAdapter<T> extends ArrayAdapter<T> {
    private Context context;
    private List<T> itemList;
    private Activity mActivity;
    private LayoutInflater inflater;
    String responseObjectType;

    public GenericSpinnerAdapter(Activity mActivity, Context context, int resource, List<T> objectsList, String responseObjectType) {
        super(context, resource, objectsList);
        this.context = context;
        this.itemList = objectsList;
        this.mActivity = mActivity;
        this.responseObjectType = responseObjectType;
        inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public T getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public int getPosition(T item) {
        return itemList.indexOf(item);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       /* LayoutInflater inflater = mActivity.getLayoutInflater();
        View row = inflater.inflate(R.layout.spinner_item_layout, parent, false);
        TextView v = (TextView) row.findViewById(android.R.id.text1);
        v.setTextColor(Color.BLACK);
        v.setText(itemList.get(position).getDescription());*/
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
       /* LayoutInflater inflater = mActivity.getLayoutInflater();
        View row = inflater.inflate(R.layout.spinner_item_layout, parent, false);
        TextView v = (TextView) row.findViewById(android.R.id.text1);
        v.setTextColor(Color.BLACK);
        parent.setBackgroundColor(Color.WHITE);
        parent.setPadding(0, 5, 0, 5);
        v.setText(itemList.get(position).getDescription());*/
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);

        /***** Get each Model object from Arraylist ********/

        TextView label = (TextView) row.findViewById(android.R.id.text1);
        label.setText("");

        switch (responseObjectType){

            case AppConstants.ResponseObjectType.VERIANT_DETAILS:
                label.setText(((VeriantsDetails)itemList.get(position)).getVeriantName());
                break;
        }

        return row;
    }

    public List<T> getItemList() {
        return itemList;
    }
}
