package com.wondercars.ridetracker.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wondercars.ridetracker.BaseClasses.BaseActivity;
import com.wondercars.ridetracker.R;
import com.wondercars.ridetracker.Retrofit.DTOs.LoginServiceDTOs.LoginRequestObj;
import com.wondercars.ridetracker.Retrofit.DTOs.LoginServiceDTOs.LoginResponseObj;
import com.wondercars.ridetracker.Utils.APIConstants;

import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;

public class LoginActivity extends BaseActivity {

    private static final int USER_LOGIN_API = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callUserLoginApi();

    }

    private LoginRequestObj getLoginApiRequestObject() {
        LoginRequestObj loginRequestObj = new LoginRequestObj();
        loginRequestObj.setUsername("admin");
        loginRequestObj.setPassword("admin123");
        return loginRequestObj;
    }

    private void callUserLoginApi() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(LoginActivity.this, APIConstants.baseurl,getLoginApiRequestObject(), LoginResponseObj.class, Constants.RetrofitMethodConstants.LOGIN_API, USER_LOGIN_API, Constants.ApiMethods.POST_METHOD,retrofitInterface)
                .setProgressDialog(new ProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
        // mUserRegistrationService.userLogin(retrofitParamsDTO, getLoginApiRequestObject());
    }

    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object object, int serviceId) {

            showLongToast(object.toString());

        }

        @Override
        public void onError(int serviceId) {
            showLongToast(Constants.ToastMessages.SOMETHING_WENT_WRONG);
        }
    };

}
