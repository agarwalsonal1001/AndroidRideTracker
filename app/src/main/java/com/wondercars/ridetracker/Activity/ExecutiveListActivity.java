package com.wondercars.ridetracker.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wondercars.ridetracker.Adapters.ExecutivesRecyclerAdapter;
import com.wondercars.ridetracker.BaseClasses.BaseActivity;
import com.wondercars.ridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.ridetracker.CustomClasses.MuseosanNormalTextView;
import com.wondercars.ridetracker.R;
import com.wondercars.ridetracker.Retrofit.DTOs.GetExecutivesDTOs.ExecutivesDetailsObj;
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

import static com.wondercars.ridetracker.Utils.APIConstants.RetrofitConstants.FAILURE;
import static com.wondercars.ridetracker.Utils.APIConstants.RetrofitConstants.SUCCESS;
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
                                    executivesRecyclerAdapter = new ExecutivesRecyclerAdapter(((ArrayList) responseObj.getExecutivesDetailsObjArrayList()), ExecutiveListActivity.this, ExecutiveListActivity.this);
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
    public void onItemClick(View view, int position, ExecutivesDetailsObj object) {

        showShortToast(object.getFullName());
    }
}
