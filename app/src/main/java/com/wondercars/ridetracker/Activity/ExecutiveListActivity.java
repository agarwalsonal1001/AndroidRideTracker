package com.wondercars.ridetracker.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.wondercars.ridetracker.Adapters.ExecutivesRecyclerAdapter;
import com.wondercars.ridetracker.BaseClasses.BaseActivity;
import com.wondercars.ridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.ridetracker.CustomClasses.MuseosanNormalTextView;
import com.wondercars.ridetracker.R;
import com.wondercars.ridetracker.Retrofit.DTOs.CreateExecutivesDTOs.CreateExecutivesRequestObj;
import com.wondercars.ridetracker.Retrofit.DTOs.CreateExecutivesDTOs.CreateExecutivesResponsetObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetExecutivesDTOs.ExecutivesDetailsObj;
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

import static com.wondercars.ridetracker.Utils.APIConstants.RetrofitConstants.FAILURE;
import static com.wondercars.ridetracker.Utils.APIConstants.RetrofitConstants.SUCCESS;
import static com.wondercars.ridetracker.Utils.AppConstants.EXECUTIVES_CREATED_SUCCESSFULLY;
import static com.wondercars.ridetracker.Utils.AppConstants.EXECUTIVES_DELETED_SUCCESSFULLY;
import static java.security.AccessController.getContext;

public class ExecutiveListActivity extends BaseActivity implements ExecutivesRecyclerAdapter.OnItemClickListener {

    public static final int GET_EXECUTIVES_SERVICE_ID = 0;

    @BindView(R.id.recy_view_executives)
    RecyclerView recyViewExecutives;
    @BindView(R.id.tv_no_executivesfind)
    MuseosanNormalTextView tvNoExecutivesfind;
    @BindView(R.id.fab_add_executives)
    FloatingActionButton fabAddExecutives;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ExecutivesRecyclerAdapter executivesRecyclerAdapter;
    private final int DELETE_EXECUTIVES_SERVICE_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executive_list);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        try {
            setActionBar(toolbar, "Executives");
            callGetExecutivesAPI();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyViewExecutives.setLayoutManager(mLayoutManager);
            recyViewExecutives.setItemAnimator(new DefaultItemAnimator());
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

    private ExecutivesDetailsObj getUpsertExecutivesRequestObj(ExecutivesDetailsObj executivesRequestObj) {
        executivesRequestObj.setActiveFlg("N");
        return executivesRequestObj;


    }

    private void callCreateExecutivesAPI(ExecutivesDetailsObj executivesRequestObj) {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getUpsertExecutivesRequestObj(executivesRequestObj), CreateExecutivesResponsetObj.class,
                APIConstants.RetrofitMethodConstants.CREATE_EXECUTIVES_API, DELETE_EXECUTIVES_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
        // mUserRegistrationService.userLogin(retrofitParamsDTO, getLoginApiRequestObject());
    }

    private GetExecutivesRequestObj getExecutivesRequestObj() {
        GetExecutivesRequestObj getExecutivesRequestObj = new GetExecutivesRequestObj(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        return getExecutivesRequestObj;
    }

    private void callGetExecutivesAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getExecutivesRequestObj(), GetExecutivesResponseObj.class,
                APIConstants.RetrofitMethodConstants.GET_EXECUTIVES, GET_EXECUTIVES_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }

    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object object, int serviceId) {

            switch (serviceId) {

                case GET_EXECUTIVES_SERVICE_ID:
                    try {
                        GetExecutivesResponseObj responseObj = (GetExecutivesResponseObj) object;
                        if (responseObj != null && responseObj.getStatus() != null) {
                            if (responseObj.getStatus().getStatusCode() == SUCCESS) {
                                if (responseObj.getExecutivesDetailsObjArrayList() != null && responseObj.getExecutivesDetailsObjArrayList().size() > 0) {
                                    setUIAccordingToIsExecutivesAvailable(true);
                                    executivesRecyclerAdapter = new ExecutivesRecyclerAdapter(((ArrayList) responseObj.getExecutivesDetailsObjArrayList()), ExecutiveListActivity.this, ExecutiveListActivity.this,true);
                                    recyViewExecutives.setAdapter(executivesRecyclerAdapter);
                                } else {
                                    setUIAccordingToIsExecutivesAvailable(false);
                                }
                            } else if (responseObj.getStatus().getStatusCode() == FAILURE) {
                                showLongToast(responseObj.getStatus().getErrorDescription());
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case DELETE_EXECUTIVES_SERVICE_ID:
                    CreateExecutivesResponsetObj responseObj = (CreateExecutivesResponsetObj) object;
                    if (responseObj != null && responseObj.getStatus() != null) {
                        if (responseObj.getStatus().getStatusCode() == SUCCESS) {
                            showLongToast(EXECUTIVES_DELETED_SUCCESSFULLY);
                            callGetExecutivesAPI();
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


    private void setUIAccordingToIsExecutivesAvailable(boolean isAvailable) {
        try {
            if (isAvailable) {
                recyViewExecutives.setVisibility(View.VISIBLE);
                tvNoExecutivesfind.setVisibility(View.GONE);
            } else {
                recyViewExecutives.setVisibility(View.GONE);
                tvNoExecutivesfind.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.recy_view_executives, R.id.fab_add_executives})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recy_view_executives:
                break;
            case R.id.fab_add_executives:
                callActivity(CreateExecutivesActivity.class);
                break;
        }
    }


    @Override
    public void onItemClick(View view, int position, final ExecutivesDetailsObj object) {

        switch (view.getId()) {

            case R.id.ib_delete_executive:
                AppAlertDialog.showAlertDialog(ExecutiveListActivity.this, "Alert", "Do you really want to delete Executive  " + object.getFullName(),
                        true, "Yes", "No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                callCreateExecutivesAPI(object);
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                break;
        }
    }
}
