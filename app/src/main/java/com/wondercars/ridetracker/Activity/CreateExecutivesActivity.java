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
import com.wondercars.ridetracker.Retrofit.DTOs.CreateExecutivesDTOs.CreateExecutivesRequestObj;
import com.wondercars.ridetracker.Retrofit.DTOs.CreateExecutivesDTOs.CreateExecutivesResponsetObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetAllSRMDTOs.GetAllSrmResponseObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetAllSRMDTOs.RideTrackerUser;
import com.wondercars.ridetracker.Retrofit.DTOs.GetExecutivesDTOs.GetExecutivesRequestObj;
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

import static android.text.TextUtils.isEmpty;
import static com.wondercars.ridetracker.Utils.APIConstants.RetrofitConstants.FAILURE;
import static com.wondercars.ridetracker.Utils.APIConstants.RetrofitConstants.SUCCESS;
import static com.wondercars.ridetracker.Utils.AppConstants.EXECUTIVES_CREATED_SUCCESSFULLY;
import static com.wondercars.ridetracker.Utils.AppConstants.ResponseObjectType.GET_ALL_SRM;
import static com.wondercars.ridetracker.Utils.AppConstants.ResponseObjectType.GET_DESIGNATION_LIST;

public class CreateExecutivesActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_mobilenumber)
    EditText etMobilenumber;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.button_create)
    Button buttonCreate;
    @BindView(R.id.et_employee_number)
    EditText etEmployeeNumber;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.spinner_select_designation)
    Spinner spinnerSelectDesignation;
    @BindView(R.id.spinner_select_manager)
    Spinner spinnerSelectManager;
    @BindView(R.id.rb_mr)
    RadioButton rbMr;
    @BindView(R.id.rb_mrs)
    RadioButton rbMrs;
    @BindView(R.id.rg_selet_title)
    RadioGroup rgSeletTitle;

    public static final int CREATE_EXECUTIVES_SERVICE_ID = 0, GET_All_SRM_SERVICE_ID = 1;

    StringBuilder srmUid, designationBuilder;
    static ArrayList<String> designationArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_executives);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        setActionBar(toolbar, "Create Executive");
        callGetAllSRMAPI();
        srmUid = new StringBuilder();
        designationBuilder = new StringBuilder("");
        GenericSpinnerAdapter genericSpinnerAdapter = new GenericSpinnerAdapter(CreateExecutivesActivity.this, 1,
                getDesignationList(), GET_DESIGNATION_LIST);
        spinnerSelectDesignation.setAdapter(genericSpinnerAdapter);
        spinnerSelectDesignation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (designationBuilder.length() > 0) {
                    designationBuilder.setLength(0);
                    designationBuilder.trimToSize();
                }
                designationBuilder.append(((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                designationBuilder.append(((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(0).toString());
            }
        });
    }

    private ArrayList<String> getDesignationList() {
        if (designationArrayList == null) {
            designationArrayList = new ArrayList<>();
            designationArrayList.add("SRM");
            designationArrayList.add("RM");
            designationArrayList.add("Driver");
            designationArrayList.add("Analyst");
        }
        return designationArrayList;
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


    private GetExecutivesRequestObj getAllSRMRequestObj() {
        return new GetExecutivesRequestObj(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
    }

    private void callGetAllSRMAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getAllSRMRequestObj(), GetAllSrmResponseObj.class,
                APIConstants.RetrofitMethodConstants.GET_All_SRM_API, GET_All_SRM_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
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

                case GET_All_SRM_SERVICE_ID:
                    GetAllSrmResponseObj getAllSrmResponseObj = (GetAllSrmResponseObj) object;
                    if (getAllSrmResponseObj != null && getAllSrmResponseObj.getStatus() != null) {
                        if (getAllSrmResponseObj.getStatus().getStatusCode() == SUCCESS) {

                            if (getAllSrmResponseObj.getRideTrackerUsers() != null && getAllSrmResponseObj.getRideTrackerUsers().size() > 0) {
                                GenericSpinnerAdapter genericSpinnerAdapter = new GenericSpinnerAdapter(CreateExecutivesActivity.this, 1,
                                        getAllSrmResponseObj.getRideTrackerUsers(), GET_ALL_SRM);
                                spinnerSelectManager.setAdapter(genericSpinnerAdapter);
                                spinnerSelectManager.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        if (srmUid.length() > 0) {
                                            srmUid.setLength(0);
                                            srmUid.trimToSize();
                                        }
                                        srmUid.append(((RideTrackerUser) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(position)).getUid());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        srmUid.append(((RideTrackerUser) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(0)).getUid());
                                    }
                                });
                            }

                        } else if (getAllSrmResponseObj.getStatus().getStatusCode() == FAILURE) {
                            showLongToast(getAllSrmResponseObj.getStatus().getErrorDescription());
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
        createExecutivesRequestObj.setAdminUid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        createExecutivesRequestObj.setFullName(etName.getText().toString());
        createExecutivesRequestObj.setPhoneNumber(etMobilenumber.getText().toString());
        createExecutivesRequestObj.setEmail(etEmail.getText().toString());
        createExecutivesRequestObj.setPassword(etPassword.getText().toString());
        createExecutivesRequestObj.setDesignation(designationBuilder.toString());

        RadioButton radioButtonTitle = (RadioButton) findViewById(rgSeletTitle.getCheckedRadioButtonId());
        createExecutivesRequestObj.setTitle(radioButtonTitle.getText().toString());
        createExecutivesRequestObj.setActiveFlg("Y");
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

        if (isEmpty(etPassword.getText().toString())) {
            showLongToast("Please set password");
            return false;
        }
        if (rgSeletTitle.getCheckedRadioButtonId() == -1) {
            showLongToast("Please select Title");
            return false;
        }
        return true;
    }
}
