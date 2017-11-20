package com.wondercars.ridetracker.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.wondercars.ridetracker.BaseClasses.BaseActivity;
import com.wondercars.ridetracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NavigationActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_manage_executives)
    LinearLayout llManageExecutives;
    @BindView(R.id.ll_manage_cars)
    LinearLayout llManageCars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        setActionBar(toolbar, "Dashboard", false);
    }


    @OnClick({R.id.ll_manage_executives, R.id.ll_manage_cars})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_manage_executives:
                callActivity(ExecutiveListActivity.class);
                break;
            case R.id.ll_manage_cars:

                callActivity(CarListActivity.class);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
