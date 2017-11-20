package com.wondercars.ridetracker.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wondercars.ridetracker.BaseClasses.BaseActivity;
import com.wondercars.ridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.ridetracker.R;
import com.wondercars.ridetracker.Retrofit.DTOs.CreateExecutivesDTOs.CreateExecutivesRequestObj;
import com.wondercars.ridetracker.Retrofit.DTOs.CreateExecutivesDTOs.CreateExecutivesResponsetObj;
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
import static com.wondercars.ridetracker.Utils.AppConstants.EXECUTIVES_CREATED_SUCCESSFULLY;
import static com.wondercars.ridetracker.Utils.AppConstants.LOGIN_SUCCESSFULLY;
import static com.wondercars.ridetracker.manager.PreferenceManager.PREF_ADMIN_UID;
import static com.wondercars.ridetracker.manager.PreferenceManager.PREF_LOGIN_CURRENT_TAG;

public class CreateExecutivesActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_mobilenumber)
    EditText etMobilenumber;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.button_create)
    Button buttonCreate;


    public static final int CREATE_EXECUTIVES_SERVICE_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_executives);
        ButterKnife.bind(this);
        setActionBar(toolbar, "Create Executive");
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

    @OnClick({R.id.button_create})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_create:
                if (validateFields()) {
                    callCreateExecutivesAPI();
                }
                break;

        }
    }

    private void callCreateExecutivesAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getCreateExecutivesRequestObj(), CreateExecutivesResponsetObj.class,
                APIConstants.RetrofitMethodConstants.CREATE_EXECUTIVES_API, CREATE_EXECUTIVES_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
        // mUserRegistrationService.userLogin(retrofitParamsDTO, getLoginApiRequestObject());
    }

    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object object, int serviceId) {

            switch (serviceId) {

                case CREATE_EXECUTIVES_SERVICE_ID:
                    CreateExecutivesResponsetObj responseObj = (CreateExecutivesResponsetObj) object;
                    if (responseObj != null && responseObj.getStatus() != null) {
                        if (responseObj.getStatus().getStatusCode() == SUCCESS) {
                            showLongToast(EXECUTIVES_CREATED_SUCCESSFULLY);
                            callActivity(ExecutiveListActivity.class);
                            finish();
                        } else if (responseObj.getStatus().getStatusCode() == FAILURE) {
                            showLongToast(responseObj.getStatus().getErrorDescription());
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


    private CreateExecutivesRequestObj getCreateExecutivesRequestObj() {

        CreateExecutivesRequestObj createExecutivesRequestObj = new CreateExecutivesRequestObj();
        createExecutivesRequestObj.setAdmin_uid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        createExecutivesRequestObj.setFullName(etName.getText().toString());
        createExecutivesRequestObj.setPhoneNumber(etMobilenumber.getText().toString());
        createExecutivesRequestObj.setEmail(etEmail.getText().toString());
        createExecutivesRequestObj.setUsername(etUsername.getText().toString());
        createExecutivesRequestObj.setPassword(etPassword.getText().toString());
        return createExecutivesRequestObj;


    }

    private boolean validateFields() {

        if (isEmpty(etName.getText().toString())) {
            showLongToast("Please enter name");
            return false;
        }

        if (isEmpty(etMobilenumber.getText().toString())) {
            showLongToast("Please enter mobile number");
            return false;
        } else if (etMobilenumber.getText().toString().length() < 10) {
            showLongToast("Please enter correct mobile number");
            return false;
        }


        if (isEmpty(etEmail.getText().toString())) {
            showLongToast("Please enter email");
            return false;
        }

        if (isEmpty(etUsername.getText().toString())) {
            showLongToast("Please enter user name");
            return false;
        }

        if (isEmpty(etPassword.getText().toString())) {
            showLongToast("Please enter password");
            return false;
        }

        return true;
    }
}
