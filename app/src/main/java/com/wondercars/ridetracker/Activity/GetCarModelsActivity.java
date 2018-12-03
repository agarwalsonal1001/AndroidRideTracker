package com.wondercars.ridetracker.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wondercars.ridetracker.Adapters.CarModelsRecyclerAdapter;
import com.wondercars.ridetracker.BaseClasses.BaseActivity;
import com.wondercars.ridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.ridetracker.CustomClasses.ManageCarsAlertDialog;
import com.wondercars.ridetracker.CustomClasses.MuseosanNormalTextView;
import com.wondercars.ridetracker.R;
import com.wondercars.ridetracker.Retrofit.DTOs.GetCarModelsDTOs.CarModels;
import com.wondercars.ridetracker.Retrofit.DTOs.GetCarModelsDTOs.GetCarModelsRequestObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetCarModelsDTOs.GetCarModelsResponseObject;
import com.wondercars.ridetracker.Retrofit.DTOs.GetCarsDTOs.CarDetailObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetCarsDTOs.GetCarsRequestObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetCarsDTOs.GetCarsResponseObj;
import com.wondercars.ridetracker.Utils.APIConstants;
import com.wondercars.ridetracker.Utils.AppConstants;
import com.wondercars.ridetracker.manager.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;

import static com.wondercars.ridetracker.Utils.APIConstants.RetrofitConstants.FAILURE;
import static com.wondercars.ridetracker.Utils.APIConstants.RetrofitConstants.SUCCESS;

public class GetCarModelsActivity extends BaseActivity implements CarModelsRecyclerAdapter.OnItemClickListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recy_view)
    RecyclerView recyView;
    @BindView(R.id.tv_no_carsfound)
    MuseosanNormalTextView tvNoCarsfound;
    @BindView(R.id.fab_add_car)
    FloatingActionButton fabAddCar;
    private static final int GET_CARMODELS_SERVICEID = 1;

    GetCarsRequestObj getCarsRequestObj;
    private CarModelsRecyclerAdapter carsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_car_models);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        try {
            setActionBar(toolbar, "Manage Cars");

            callGetCarModelsAPI();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyView.setLayoutManager(mLayoutManager);
            recyView.setItemAnimator(new DefaultItemAnimator());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.fab_add_car)
    public void onClick() {
        callActivity(CreateCarActivity.class);
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

    private GetCarModelsRequestObj getCarModelsRequestObj() {
        GetCarModelsRequestObj getCarModelsRequestObj = new GetCarModelsRequestObj();
        //getCarModelsRequestObj.setCarName("Baleno");
        getCarModelsRequestObj.setAdmin_uid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        return getCarModelsRequestObj;
    }

    private void callGetCarModelsAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getCarModelsRequestObj(), GetCarModelsResponseObject.class,
                APIConstants.RetrofitMethodConstants.GET_CAR_MODELS_API, GET_CARMODELS_SERVICEID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }

    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object object, int serviceId) {

            switch (serviceId) {

                case GET_CARMODELS_SERVICEID:
                    try {
                        GetCarModelsResponseObject responseObj = (GetCarModelsResponseObject) object;
                        if (responseObj != null && responseObj.getStatus() != null) {
                            if (responseObj.getStatus().getStatusCode() == SUCCESS) {
                                if (responseObj.getCarModels() != null && responseObj.getCarModels().size() > 0) {
                                    setUIAccordingToIsCarsAvailable(true);
                                    carsRecyclerAdapter = new CarModelsRecyclerAdapter(GetCarModelsActivity.this, responseObj.getCarModels(), GetCarModelsActivity.this);
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


    @Override
    public void onItemClick(View view, int position, CarModels object) {
        getCarsRequestObj = new GetCarsRequestObj();
        getCarsRequestObj.setCarModelName(object.getCarModelName());
        showAlertDialog();
        //showLongToast(object.getCarModelName());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showAlertDialog() {

        ManageCarsAlertDialog manageCarsAlertDialog = new ManageCarsAlertDialog();
        final AlertDialog alertDialog = manageCarsAlertDialog.createAlertDialog(GetCarModelsActivity.this);
        Button button = (Button) alertDialog.findViewById(R.id.button_apply);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetCarModelsActivity.this, CarListActivity.class);
                intent.putExtra("requestObj", getRequestObj(alertDialog));
                startActivity(intent);
                alertDialog.dismiss();
            }
        });
    }


    private GetCarsRequestObj getRequestObj(AlertDialog alertDialog) {
        RadioGroup rgFuelType = (RadioGroup) alertDialog.findViewById(R.id.rg_fuel_type);
        RadioGroup rgCarMode = (RadioGroup) alertDialog.findViewById(R.id.rg_car_mode);
        RadioGroup rgCarVariant = (RadioGroup) alertDialog.findViewById(R.id.rg_select_variant);

        getCarsRequestObj.setAdmin_uid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));

        RadioButton radioButtonFuelType = (RadioButton) alertDialog.findViewById(rgFuelType.getCheckedRadioButtonId());
        if (radioButtonFuelType != null) {
            getCarsRequestObj.setFuelType(radioButtonFuelType.getText().toString());
        }

        RadioButton radioButtonCarMode = (RadioButton) alertDialog.findViewById(rgCarMode.getCheckedRadioButtonId());
        if (radioButtonCarMode != null) {
            getCarsRequestObj.setMode(radioButtonCarMode.getText().toString());
        }

        RadioButton radioButtonVariant = (RadioButton) alertDialog.findViewById(rgCarVariant.getCheckedRadioButtonId());
        if (radioButtonVariant != null) {
            getCarsRequestObj.setVariantName(radioButtonVariant.getText().toString());
        }
        return getCarsRequestObj;
    }

}
