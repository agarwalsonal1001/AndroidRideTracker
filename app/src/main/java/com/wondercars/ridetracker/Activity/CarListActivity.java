package com.wondercars.ridetracker.Activity;

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
import com.wondercars.ridetracker.Retrofit.DTOs.GetCarsDTOs.CarDetailObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetCarsDTOs.GetCarsRequestObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetCarsDTOs.GetCarsResponseObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetExecutivesDTOs.GetExecutivesRequestObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetExecutivesDTOs.GetExecutivesResponseObj;
import com.wondercars.ridetracker.Utils.APIConstants;
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

    private static final int GET_CARS_SERVICE_ID = 0;
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

    private GetCarsRequestObj getCarsRequestObj() {
        GetCarsRequestObj getCarsRequestObj = new GetCarsRequestObj();
        //getCarsRequestObj.setCarName("Baleno");
        getCarsRequestObj.setAdmin_uid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));

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
    public void onItemClick(View view, int position, CarDetailObj object) {

        showLongToast(object.getRegistrationNumber());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
