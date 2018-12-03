package com.wondercars.ridetracker.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.wondercars.ridetracker.Adapters.GenericSpinnerAdapter;
import com.wondercars.ridetracker.Adapters.ViewAllRidesRecyclerAdapter;
import com.wondercars.ridetracker.BaseClasses.BaseActivity;
import com.wondercars.ridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.ridetracker.R;
import com.wondercars.ridetracker.Retrofit.DTOs.GenerateReports.GenerateReportsRequestObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GenerateReports.ReportsResponseObj;
import com.wondercars.ridetracker.Retrofit.DTOs.GetVeriantsDTOs.VeriantsDetails;
import com.wondercars.ridetracker.Retrofit.DTOs.ViewAllRidesDTOs.RidesDetails;
import com.wondercars.ridetracker.Retrofit.DTOs.ViewAllRidesDTOs.ViewAllRidesResponseObj;
import com.wondercars.ridetracker.Retrofit.DTOs.ViewAllRidesDTOs.ViewRidesRequestObj;
import com.wondercars.ridetracker.Utils.APIConstants;
import com.wondercars.ridetracker.manager.PreferenceManager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;
import umer.accl.utils.DateUtils;

import static android.text.TextUtils.isEmpty;
import static com.wondercars.ridetracker.Utils.APIConstants.RetrofitConstants.SUCCESS;
import static com.wondercars.ridetracker.Utils.AppConstants.ResponseObjectType.GENERATE_REPORTS;
import static com.wondercars.ridetracker.Utils.AppConstants.ResponseObjectType.VERIANT_DETAILS;
import static umer.accl.utils.DateUtils.DD_MMM_YYYY_DASH_DATE_FORMAT;
import static umer.accl.utils.DateUtils.YYYY_MM_dd_DASH_DATE_FORMAT;


public class ViewAllRidesActivity extends BaseActivity {


    private static final int VIEW_ALLRIDES_SERVICE_ID = 1, SEND_REPORTS_SERVICE_ID = 2;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.spinner_select_ride_type)
    Spinner spinnerSelectRideType;
    @BindView(R.id.tv_select_from_date)
    TextView tvSelectFromDate;
    @BindView(R.id.ll_pick_from_date)
    LinearLayout llPickFromDate;
    @BindView(R.id.tv_select_To_date)
    TextView tvSelectToDate;
    @BindView(R.id.ll_pick_To_date)
    LinearLayout llPickToDate;
    private String SelectedFromDate, SelectedToDate, selectedRideType;

    ArrayList<String> rideType = new ArrayList<>();
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_rides);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
       /* callViewAllRidesAPI();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());*/
        rideType.add("Test Drive");
        rideType.add("Ride");
        calendar = Calendar.getInstance();
        setActionBar(toolbar, "Reports");
        setAdapter();

    }


    private GenerateReportsRequestObj generateReportsRequestObj() {
        GenerateReportsRequestObj generateReportsRequestObj = new GenerateReportsRequestObj();
        // viewRidesRequestObj.setUid(PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
        generateReportsRequestObj.setAdmin_uid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        generateReportsRequestObj.setType(selectedRideType);
        generateReportsRequestObj.setFromDate(SelectedFromDate);
        generateReportsRequestObj.setToDate(SelectedToDate);
        return generateReportsRequestObj;

    }

    private void callGenerateReportsAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, generateReportsRequestObj(), ReportsResponseObj.class,
                APIConstants.RetrofitMethodConstants.SEND_REPORTS, SEND_REPORTS_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }


    private ViewRidesRequestObj getViewRidesRequestObj() {
        ViewRidesRequestObj viewRidesRequestObj = new ViewRidesRequestObj();
        // viewRidesRequestObj.setUid(PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
        viewRidesRequestObj.setAdminUid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        return viewRidesRequestObj;

    }

    private void callViewAllRidesAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getViewRidesRequestObj(), ViewAllRidesResponseObj.class,
                APIConstants.RetrofitMethodConstants.VIEW_ALL_RIDES, VIEW_ALLRIDES_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }


    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object object, int serviceId) {

            switch (serviceId) {

                case SEND_REPORTS_SERVICE_ID:
                    ReportsResponseObj reportsResponseObj = (ReportsResponseObj) object;
                    if (reportsResponseObj != null && reportsResponseObj.getStatus() != null) {

                        if (reportsResponseObj.getStatus().getStatusCode() == SUCCESS) {
                            showLongToast("Report sent successfully");
                            onBackPressed();
                            finish();
                        } else {
                            showShortToast(reportsResponseObj.getStatus().getErrorDescription());
                        }
                    }
                    break;


                case VIEW_ALLRIDES_SERVICE_ID:
                    ViewAllRidesResponseObj viewAllRidesResponseObj = (ViewAllRidesResponseObj) object;
                    if (viewAllRidesResponseObj != null && viewAllRidesResponseObj.getStatus() != null) {

                        if (viewAllRidesResponseObj.getStatus().getStatusCode() == SUCCESS) {
                            if (viewAllRidesResponseObj.getRides().size() > 0) {

                                ViewAllRidesRecyclerAdapter viewAllRidesRecyclerAdapter = new ViewAllRidesRecyclerAdapter(ViewAllRidesActivity.this, viewAllRidesResponseObj.getRides(), onItemClickListener);
                                recyclerView.setAdapter(viewAllRidesRecyclerAdapter);
                            }
                        } else {
                            showShortToast(viewAllRidesResponseObj.getStatus().getErrorDescription());
                        }
                    }
                    break;
            }


        }

        @Override
        public void onError(int serviceId) {

        }
    };


    ViewAllRidesRecyclerAdapter.OnItemClickListener onItemClickListener = new ViewAllRidesRecyclerAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position, RidesDetails object) {

        }
    };


    private void setAdapter() {
        GenericSpinnerAdapter genericSpinnerAdapter = new GenericSpinnerAdapter(ViewAllRidesActivity.this, 1, rideType, GENERATE_REPORTS);
        spinnerSelectRideType.setAdapter(genericSpinnerAdapter);
        spinnerSelectRideType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedRideType = ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(position).toString();
                //variantStringBuilder.append(((VeriantsDetails) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(position)).getVariantName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // variantStringBuilder.append(((VeriantsDetails) ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(0)).getVariantName());
                selectedRideType = ((GenericSpinnerAdapter) (parent.getAdapter())).getItemList().get(0).toString();
            }
        });
    }

    @OnClick({R.id.ll_pick_from_date, R.id.ll_pick_To_date, R.id.button_next})
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.ll_pick_from_date:
            case R.id.ll_pick_To_date:

                DatePickerDialog datePickerDialog = new DatePickerDialog(ViewAllRidesActivity.this, R.style.datepicker, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        try {

                            switch (view.getId()) {
                                case R.id.ll_pick_To_date:
                                    SelectedToDate = DateUtils.getDateIn_YYYYMMdd_Dash_Format(year, month, dayOfMonth);// DateUtils.formatStringDateFromOneToAnother(DateUtils.getDateIn_YYYYMMdd_Dash_Format(year, month, dayOfMonth), YYYY_MM_dd_DASH_DATE_FORMAT, DD_MMM_YYYY_DASH_DATE_FORMAT);
                                    tvSelectToDate.setText(SelectedToDate);
                                    break;

                                case R.id.ll_pick_from_date:
                                    SelectedFromDate = DateUtils.getDateIn_YYYYMMdd_Dash_Format(year, month, dayOfMonth); // DateUtils.formatStringDateFromOneToAnother(DateUtils.getDateIn_YYYYMMdd_Dash_Format(year, month, dayOfMonth), YYYY_MM_dd_DASH_DATE_FORMAT, DD_MMM_YYYY_DASH_DATE_FORMAT);
                                    tvSelectFromDate.setText(SelectedFromDate);
                                    break;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // callAvailableGetSlotAPI();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                // datePickerDialog.getDatePicker().setMaxDate(addDays(new Date(), 7).getTimeInMillis());
                datePickerDialog.show();

                //DateUtils.showDatePicker(ViewAllRidesActivity.this, R.style.datepicker, 2018, 0, 1 );

                break;


            case R.id.button_next:

                if (validateFields()) {
                    callGenerateReportsAPI();
                }

                break;
        }
    }

    private boolean validateFields() {

        if (!isEmpty(SelectedToDate)) {
            if (isEmpty(SelectedFromDate)) {
                showLongToast("Please select From Date");
                return false;
            }
        }


        if (!isEmpty(SelectedFromDate)) {
            if (isEmpty(SelectedToDate)) {
                SelectedToDate = DateUtils.getCurrentDate(YYYY_MM_dd_DASH_DATE_FORMAT);
                //showLongToast("Please select From Date");
                return true;
            }
        }


        return true;
    }
}



