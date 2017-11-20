package com.wondercars.ridetracker.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.wondercars.ridetracker.Adapters.GenericSpinnerAdapter;
import com.wondercars.ridetracker.BaseClasses.BaseActivity;
import com.wondercars.ridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.ridetracker.R;
import com.wondercars.ridetracker.Retrofit.DTOs.CreateCarDTOs.CreateCarRequestObj;
import com.wondercars.ridetracker.Retrofit.DTOs.CreateCarDTOs.CreateCarsResponseObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetVeriantsDTOs.GetVeriantsRequestObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetVeriantsDTOs.GetVeriantsResponseObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetVeriantsDTOs.VeriantsDetails;
import com.wondercars.ridetracker.Utils.APIConstants;
import com.wondercars.ridetracker.Utils.AppConstants;
import com.wondercars.ridetracker.manager.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;

import static android.text.TextUtils.isEmpty;
import static com.wondercars.ridetracker.Utils.APIConstants.RetrofitConstants.FAILURE;
import static com.wondercars.ridetracker.Utils.APIConstants.RetrofitConstants.SUCCESS;
import static com.wondercars.ridetracker.Utils.AppConstants.ResponseObjectType.VERIANT_DETAILS;

public class CreateCarActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_car_name)
    EditText etCarName;
    @BindView(R.id.spinner_select_variant)
    Spinner spinnerSelectVariant;
    @BindView(R.id.et_car_number)
    EditText etCarNumber;
    @BindView(R.id.button_create)
    Button buttonCreate;

    StringBuilder variantId;

    private static final int GET_VARIANTS_SERVICE_ID = 0, CREATE_CAR_SERVICE_ID = 1;
    @BindView(R.id.rb_petrol)
    RadioButton rbPetrol;
    @BindView(R.id.rb_diesel)
    RadioButton rbDiesel;
    @BindView(R.id.rg_fuel_type)
    RadioGroup rgFuelType;
    @BindView(R.id.rb_automatic)
    RadioButton rbAutomatic;
    @BindView(R.id.rb_manual)
    RadioButton rbManual;
    @BindView(R.id.rg_car_mode)
    RadioGroup rgCarMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_car);
        ButterKnife.bind(this);
        setActionBar(toolbar, "Add Car");
        callGetVariantsAPI();
       // variantStringBuilder = new StringBuilder();
        variantId = new StringBuilder();
    }

    @OnClick({R.id.button_create})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_create:
                if (validateFields()) {
                    callCreateCarAPI();
                }
                break;
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

    private boolean validateFields() {

        if (isEmpty(etCarName.getText().toString())) {
            showLongToast("Please enter car name");
        }
        if (isEmpty((etCarNumber.getText().toString()))) {
            showLongToast("Please enter car number");
        }

        if (rgFuelType.getCheckedRadioButtonId() == -1) {
            showLongToast("Please select car fuel type");
            return false;
        }

        if (rgCarMode.getCheckedRadioButtonId() == -1) {
            showLongToast("Please select car mode");
            return false;
        }
        return true;
    }


    private GetVeriantsRequestObj getVeriantsRequestObj() {

        return new GetVeriantsRequestObj(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
    }


    private void callGetVariantsAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getVeriantsRequestObj(), GetVeriantsResponseObj.class,
                APIConstants.RetrofitMethodConstants.GET_VARIANTS, GET_VARIANTS_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
        // mUserRegistrationService.userLogin(retrofitParamsDTO, getLoginApiRequestObject());
    }


    private void callCreateCarAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getCreateCarRequestObj(), CreateCarsResponseObj.class,
                APIConstants.RetrofitMethodConstants.CREATE_CAR_API, CREATE_CAR_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
        // mUserRegistrationService.userLogin(retrofitParamsDTO, getLoginApiRequestObject());
    }

    private CreateCarRequestObj getCreateCarRequestObj() {

        CreateCarRequestObj createCarRequestObj = new CreateCarRequestObj();
        createCarRequestObj.setAdmin_uid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        createCarRequestObj.setCarName(etCarName.getText().toString());
        createCarRequestObj.setRegistrationNumber(etCarNumber.getText().toString());

        RadioButton radioButton = (RadioButton) findViewById(rgFuelType.getCheckedRadioButtonId());
        createCarRequestObj.setFuelType(radioButton.getText().toString());

        RadioButton radioButtonCarMode = (RadioButton) findViewById(rgCarMode.getCheckedRadioButtonId());
        createCarRequestObj.setMode(radioButtonCarMode.getText().toString());
        createCarRequestObj.setVariantId(variantId.toString());
        return createCarRequestObj;

    }

    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object object, int serviceId) {

            switch (serviceId) {

                case GET_VARIANTS_SERVICE_ID:
                    GetVeriantsResponseObj responseObj = (GetVeriantsResponseObj) object;
                    if (responseObj != null && responseObj.getStatus() != null) {
                        if (responseObj.getStatus().getStatusCode() == SUCCESS) {
                            if (responseObj.getCarVariants() != null && responseObj.getCarVariants().size() > 0) {

                                GenericSpinnerAdapter genericSpinnerAdapter = new GenericSpinnerAdapter(CreateCarActivity.this, 1,
                                        responseObj.getCarVariants(), VERIANT_DETAILS);
                                spinnerSelectVariant.setAdapter(genericSpinnerAdapter);
                                spinnerSelectVariant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        if (variantId.length() > 0) {
                                            variantId.setLength(0);
                                            variantId.trimToSize();
                                        }
                                        variantId.append(((VeriantsDetails) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(position)).getVariantId());
                                        //variantStringBuilder.append(((VeriantsDetails) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(position)).getVariantName());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                       // variantStringBuilder.append(((VeriantsDetails) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(0)).getVariantName());
                                        variantId.append(((VeriantsDetails) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(0)).getVariantId());
                                    }
                                });
                            }


                        } else if (responseObj.getStatus().getStatusCode() == FAILURE) {
                            showLongToast(responseObj.getStatus().getErrorDescription());
                        }
                    }

                    break;

                case CREATE_CAR_SERVICE_ID:

                    CreateCarsResponseObj carsResponseObj = (CreateCarsResponseObj) object;
                    if (carsResponseObj != null && carsResponseObj.getStatus() != null) {
                        if (carsResponseObj.getStatus().getStatusCode() == SUCCESS) {

                            showLongToast("Car added successfully");
                        } else {
                            showLongToast(carsResponseObj.getStatus().getErrorDescription());
                        }

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


    @OnClick(R.id.button_create)
    public void onClick() {
    }
}
