package com.wondercars.ridetracker.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wondercars.ridetracker.BaseClasses.BaseActivity;
import com.wondercars.ridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.ridetracker.R;
import com.wondercars.ridetracker.Retrofit.DTOs.GetAdminOTPRequestObj.GetAdminOTPRequestObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetAdminOTPRequestObj.GetAdminOTPResponseObj;
import com.wondercars.ridetracker.Retrofit.DTOs.SetAdminOtpDTOs.SetAdminOTPRequestObj;
import com.wondercars.ridetracker.Retrofit.DTOs.SetAdminOtpDTOs.SetAdminOTPResponseObj;
import com.wondercars.ridetracker.Utils.APIConstants;
import com.wondercars.ridetracker.Utils.AppAlertDialog;
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
import static com.wondercars.ridetracker.manager.PreferenceManager.PREF_ADMIN_UID;
import static com.wondercars.ridetracker.manager.PreferenceManager.PREF_ADMIN_USER_NAME;

public class NavigationActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_manage_executives)
    LinearLayout llManageExecutives;
    @BindView(R.id.ll_manage_cars)
    LinearLayout llManageCars;
    @BindView(R.id.ll_track_rides)
    LinearLayout llTrackRides;
    @BindView(R.id.ll_generate_reports)
    LinearLayout llGenerateReports;


    final int GET_ADMIN_OTP_API = 0, UPDATE_ADMIN_OTP_API = 1;

    TextView textViewCurrentOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        setActionBar(toolbar, "Dashboard", false);
    }
    ///getAdminOtp


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_options_menu, menu);
        return true;
    }


    private GetAdminOTPRequestObj getAdminOTPRequestObj() {
        GetAdminOTPRequestObj loginRequestObj = new GetAdminOTPRequestObj();
        loginRequestObj.setAdmin_uid(PreferenceManager.readString(PREF_ADMIN_UID));
        return loginRequestObj;
    }

    private void callGetAdminOTPApi() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(NavigationActivity.this,
                APIConstants.baseurl, getAdminOTPRequestObj(), GetAdminOTPResponseObj.class,
                APIConstants.RetrofitMethodConstants.GET_ADMIN_OTP, GET_ADMIN_OTP_API, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
        // mUserRegistrationService.userLogin(retrofitParamsDTO, getLoginApiRequestObject());
    }


    private SetAdminOTPRequestObj getSetAdminOTPRequestObj(String otp) {
        SetAdminOTPRequestObj loginRequestObj = new SetAdminOTPRequestObj();
        loginRequestObj.setAdmin_uid(PreferenceManager.readString(PREF_ADMIN_UID));
        loginRequestObj.setAdmin_otp(otp);
        return loginRequestObj;
    }

    private void callSetAdminOTPApi(String otp) {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(NavigationActivity.this,
                APIConstants.baseurl, getSetAdminOTPRequestObj(otp), SetAdminOTPResponseObj.class,
                APIConstants.RetrofitMethodConstants.UPDATE_ADMIN_OTP, UPDATE_ADMIN_OTP_API, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
        // mUserRegistrationService.userLogin(retrofitParamsDTO, getLoginApiRequestObject());
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                //onBackPressed();
                return true;

            case R.id.logout:
                AppAlertDialog.showAlertDialog(this, "Alert", "Are you sure you want to Logout?", true, "Yes", "No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PreferenceManager.clearAllPreference();
                                callActivity(LoginActivity.class);
                                dialog.dismiss();
                                finish();
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                return true;

            case R.id.profile:
                AppAlertDialog.showAlertDialog(this, "Profile Info", "Logged in as :  " + PreferenceManager.readString(PREF_ADMIN_USER_NAME), true, "Ok", null,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }, null);

                return true;


            case R.id.getAdminOtp:

                showSetOtpDialog();
                callGetAdminOTPApi();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick({R.id.ll_manage_executives, R.id.ll_manage_cars, R.id.ll_track_rides, R.id.ll_generate_reports})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_manage_executives:
                callActivity(ExecutiveListActivity.class);
                break;

            case R.id.ll_manage_cars:
                callActivity(GetCarModelsActivity.class);
                break;

            case R.id.ll_track_rides:
                callActivity(TrackTestDriveActivity.class);
                break;

            case R.id.ll_generate_reports:
                callActivity(ViewAllRidesActivity.class);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object object, int serviceId) {

            switch (serviceId) {

                case GET_ADMIN_OTP_API:
                    GetAdminOTPResponseObj getAdminOTPResponseObj = (GetAdminOTPResponseObj) object;
                    if (getAdminOTPResponseObj != null && getAdminOTPResponseObj.getStatus() != null) {
                        if (getAdminOTPResponseObj.getStatus().getStatusCode() == SUCCESS) {

                            textViewCurrentOTP.setText("Current OTP : " + getAdminOTPResponseObj.getAdmin_otp());
                            showLongToast("Share OTP : " + getAdminOTPResponseObj.getAdmin_otp());
                            //showLongToast();
                        } else if (getAdminOTPResponseObj.getStatus().getStatusCode() == FAILURE) {
                            showLongToast(getAdminOTPResponseObj.getStatus().getErrorDescription());
                        }
                    }

                    break;

                case UPDATE_ADMIN_OTP_API:

                    SetAdminOTPResponseObj setAdminOTPResponseObj = (SetAdminOTPResponseObj) object;

                    if (setAdminOTPResponseObj != null && setAdminOTPResponseObj.getStatus() != null) {
                        if (setAdminOTPResponseObj.getStatus().getStatusCode() == SUCCESS) {
                            showLongToast("Share OTP : " + setAdminOTPResponseObj.getAdmin_otp());
                            //showLongToast();
                        } else if (setAdminOTPResponseObj.getStatus().getStatusCode() == FAILURE) {
                            showLongToast(setAdminOTPResponseObj.getStatus().getErrorDescription());
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


    private void showSetOtpDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_set_otp_layout, null);
        textViewCurrentOTP = (TextView) alertLayout.findViewById(R.id.tv_old_otp);
        final EditText etUsername = (EditText) alertLayout.findViewById(R.id.et_otp);
        final Button cancelButton = (Button) alertLayout.findViewById(R.id.button_cancel);
        final Button setButton = (Button) alertLayout.findViewById(R.id.button_set);


        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Admin OTP");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (TextUtils.isEmpty(etUsername.getText().toString()) && etUsername.getText().toString().length() < 4) {
                    Toast.makeText(getApplicationContext(), "Please enter correct OTP", Toast.LENGTH_LONG).show();
                } else {
                    callSetAdminOTPApi(etUsername.getText().toString());
                }
            }
        });
        alert.setCancelable(true);
        AlertDialog dialog = alert.create();
        dialog.show();

    }
}
