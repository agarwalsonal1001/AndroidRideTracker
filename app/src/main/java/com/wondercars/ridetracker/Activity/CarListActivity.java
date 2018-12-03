package com.wondercars.ridetracker.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.wondercars.ridetracker.Adapters.CarsRecyclerAdapter;
import com.wondercars.ridetracker.Adapters.ExecutivesRecyclerAdapter;
import com.wondercars.ridetracker.BaseClasses.BaseActivity;
import com.wondercars.ridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.ridetracker.CustomClasses.MuseosanNormalTextView;
import com.wondercars.ridetracker.R;
import com.wondercars.ridetracker.Retrofit.DTOs.DeleteCarDTOs.DeleteCarRequestObj;
import com.wondercars.ridetracker.Retrofit.DTOs.DeleteCarDTOs.DeleteCarResponseObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetCarsDTOs.CarDetailObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetCarsDTOs.GetCarsRequestObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetCarsDTOs.GetCarsResponseObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetExecutivesDTOs.GetExecutivesRequestObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetExecutivesDTOs.GetExecutivesResponseObj;
import com.wondercars.ridetracker.Utils.APIConstants;
import com.wondercars.ridetracker.Utils.AppAlertDialog;
import com.wondercars.ridetracker.Utils.AppConstants;
import com.wondercars.ridetracker.manager.PreferenceManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;

import static com.wondercars.ridetracker.Activity.ExecutiveListActivity.GET_EXECUTIVES_SERVICE_ID;
import static com.wondercars.ridetracker.Utils.APIConstants.RetrofitConstants.FAILURE;
import static com.wondercars.ridetracker.Utils.APIConstants.RetrofitConstants.SUCCESS;

public class CarListActivity extends BaseActivity implements CarsRecyclerAdapter.OnItemClickListener {

    private static final int GET_CARS_SERVICE_ID = 0, DELETE_CAR_SERVICE_ID = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recy_view)
    RecyclerView recyView;
    @BindView(R.id.tv_no_carsfound)
    MuseosanNormalTextView tvNoCarsfound;
    @BindView(R.id.fab_add_car)
    FloatingActionButton fabAddCar;

    CarsRecyclerAdapter carsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        try {
            setActionBar(toolbar, "Cars");

            callGetCarsAPI();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyView.setLayoutManager(mLayoutManager);
            recyView.setItemAnimator(new DefaultItemAnimator());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:

                onBackPressed();
                break;
        }
        return (super.onOptionsItemSelected(menuItem));

    }


    private void callDeleteCarAPI(DeleteCarRequestObj deleteCarRequestObj) {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, deleteCarRequestObj, DeleteCarResponseObj.class,
                APIConstants.RetrofitMethodConstants.DELETE_CAR, DELETE_CAR_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }

    private GetCarsRequestObj getCarsRequestObj() {
        GetCarsRequestObj getCarsRequestObj = null;
        if (getIntent().hasExtra("requestObj")) {
            getCarsRequestObj = (GetCarsRequestObj) getIntent().getSerializableExtra("requestObj");
        } else {
            getCarsRequestObj = new GetCarsRequestObj();
            //getCarsRequestObj.setCarName("Baleno");
            getCarsRequestObj.setAdmin_uid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        }
        return getCarsRequestObj;
    }

    private void callGetCarsAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getCarsRequestObj(), GetCarsResponseObj.class,
                APIConstants.RetrofitMethodConstants.GET_CARS_API, GET_CARS_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }

    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object object, int serviceId) {

            switch (serviceId) {

                case GET_CARS_SERVICE_ID:
                    try {
                        GetCarsResponseObj responseObj = (GetCarsResponseObj) object;
                        if (responseObj != null && responseObj.getStatus() != null) {
                            if (responseObj.getStatus().getStatusCode() == SUCCESS) {
                                if (responseObj.getCars() != null && responseObj.getCars().size() > 0) {
                                    setUIAccordingToIsCarsAvailable(true);
                                    carsRecyclerAdapter = new CarsRecyclerAdapter(CarListActivity.this, responseObj.getCars(), CarListActivity.this);
                                    recyView.setAdapter(carsRecyclerAdapter);
                                } else {
                                    setUIAccordingToIsCarsAvailable(false);
                                }
                            } else if (responseObj.getStatus().getStatusCode() == FAILURE) {
                                showLongToast(responseObj.getStatus().getErrorDescription());
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;


                case DELETE_CAR_SERVICE_ID:
                    try {
                        DeleteCarResponseObj deleteCarResponseObj = (DeleteCarResponseObj) object;
                        if (deleteCarResponseObj != null && deleteCarResponseObj.getStatus() != null) {
                            if (deleteCarResponseObj.getStatus().getStatusCode() == SUCCESS) {
                                showLongToast("Car Deleted Successfully");
                                callGetCarsAPI();
                            } else if (deleteCarResponseObj.getStatus().getStatusCode() == FAILURE) {
                                showLongToast(deleteCarResponseObj.getStatus().getErrorDescription());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                default:

                    break;
            }
        }

        @Override
        public void onError(int serviceId) {
            showLongToast(AppConstants.ToastMessages.SOMETHING_WENT_WRONG);
        }
    };

    private void setUIAccordingToIsCarsAvailable(boolean isAvailable) {
        try {
            if (isAvailable) {
                recyView.setVisibility(View.VISIBLE);
                tvNoCarsfound.setVisibility(View.GONE);
            } else {
                recyView.setVisibility(View.GONE);
                tvNoCarsfound.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.fab_add_car)
    public void onClick() {

        callActivity(CreateCarActivity.class);
    }

    @Override
    public void onItemClick(View view, int position, final CarDetailObj object) {
        AppAlertDialog.showAlertDialog(CarListActivity.this, "Alert", "Do you want to delete this car", false, "Yes", "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DeleteCarRequestObj deleteCarRequestObj = new DeleteCarRequestObj();
                deleteCarRequestObj.setAdmin_uid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
                deleteCarRequestObj.setCarId(object.getCarId());
                callDeleteCarAPI(deleteCarRequestObj);
                dialog.dismiss();
                //callUpsertSlotAPI(object);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
