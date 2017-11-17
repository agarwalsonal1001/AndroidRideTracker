package com.wondercars.ridetracker.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.wondercars.ridetracker.BaseClasses.BaseActivity;
import com.wondercars.ridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.ridetracker.R;
import com.wondercars.ridetracker.Retrofit.DTOs.LoginServiceDTOs.LoginRequestObj;
import com.wondercars.ridetracker.Retrofit.DTOs.LoginServiceDTOs.LoginResponseObj;
import com.wondercars.ridetracker.Utils.APIConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;

import static com.wondercars.ridetracker.Utils.AppConstants.PLEASE_ENTER_PASSWORD;
import static com.wondercars.ridetracker.Utils.AppConstants.PLEASE_ENTER_USERNAME;

public class LoginActivity extends BaseActivity {

    private static final int USER_LOGIN_API = 1;
    @BindView(R.id.edt_enter_username)
    EditText edtEnterUsername;
    @BindView(R.id.edt_enter_password)
    EditText edtEnterPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
      //  callUserLoginApi();

    }

    private LoginRequestObj getLoginApiRequestObject() {
        LoginRequestObj loginRequestObj = new LoginRequestObj();
        loginRequestObj.setUsername(edtEnterUsername.getText().toString());
        loginRequestObj.setPassword(edtEnterPassword.getText().toString());
        return loginRequestObj;
    }

    private void callUserLoginApi() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(LoginActivity.this,
                APIConstants.baseurl, getLoginApiRequestObject(), LoginResponseObj.class,
                Constants.RetrofitMethodConstants.LOGIN_API, USER_LOGIN_API, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
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
            //showLongToast(Constants.ToastMessages.SOMETHING_WENT_WRONG);
        }
    };

    private boolean validateFields() {
        if (TextUtils.isEmpty(edtEnterUsername.getText().toString())) {
            showShortToast(PLEASE_ENTER_USERNAME);
            return false;
        }
        if (TextUtils.isEmpty(edtEnterPassword.getText().toString())) {
            showShortToast(PLEASE_ENTER_PASSWORD);
            return false;
        }

        return true;
    }

    @OnClick(R.id.tv_login)
    public void onClick() {
        if (validateFields()) {
            callUserLoginApi();
        }
    }
}
