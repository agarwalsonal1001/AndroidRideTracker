package com.wondercars.ridetracker.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wondercars.ridetracker.Adapters.MyItemRecyclerViewAdapter;
import com.wondercars.ridetracker.CallBack.OnListFragmentInteractionListener;
import com.wondercars.ridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.ridetracker.R;
import com.wondercars.ridetracker.Retrofit.DTOs.GetExecutivesDTOs.ExecutivesDetailsObj;
import com.wondercars.ridetracker.Retrofit.DTOs.ViewAllTestDrives.RidesDetails;
import com.wondercars.ridetracker.Retrofit.DTOs.ViewAllTestDrives.ViewAllTestDrivesResponseObj;
import com.wondercars.ridetracker.Retrofit.DTOs.ViewAllTestDrives.ViewTestDrivesRequestObj;
import com.wondercars.ridetracker.Utils.APIConstants;
import com.wondercars.ridetracker.manager.PreferenceManager;

import java.util.ArrayList;

import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;
import umer.accl.utils.DateUtils;

import static com.wondercars.ridetracker.Utils.APIConstants.RetrofitConstants.SUCCESS;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CompletedTestDriveFragment extends Fragment {

    RecyclerView recyclerView;
    TextView tv_nodata;
    private final int VIEW_ALL_TESTDRIVE_SERVICE_ID = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CompletedTestDriveFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CompletedTestDriveFragment newInstance() {
        CompletedTestDriveFragment fragment = new CompletedTestDriveFragment();
        /*Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callViewAllRidesAPI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof LinearLayout) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view.findViewById(R.id.list);
            tv_nodata = (TextView)view.findViewById(R.id.tv_nodata);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(new ArrayList<RidesDetails>(), mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    OnListFragmentInteractionListener mListener = new OnListFragmentInteractionListener() {
        @Override
        public void onListFragmentInteraction(ExecutivesDetailsObj item) {


        }
    };


    private ViewTestDrivesRequestObj getViewRidesRequestObj() {
        ViewTestDrivesRequestObj viewRidesRequestObj = new ViewTestDrivesRequestObj();
        // viewRidesRequestObj.setUid(PreferenceManager.readString(PreferenceManager.PREF_INDIVISUAL_ID));
        viewRidesRequestObj.setAdminUid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        viewRidesRequestObj.setRide_completed_flg("Y");
      //  viewRidesRequestObj.setCreateDate(DateUtils.getCurrentDate());
        return viewRidesRequestObj;

    }

    private void callViewAllRidesAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(getActivity(),
                APIConstants.baseurl, getViewRidesRequestObj(), ViewAllTestDrivesResponseObj.class,
                APIConstants.RetrofitMethodConstants.VIEW_ALL_TESTDRIVES, VIEW_ALL_TESTDRIVE_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(getActivity()))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }


    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object o, int serviceId) {

            switch (serviceId) {

                case VIEW_ALL_TESTDRIVE_SERVICE_ID:

                    ViewAllTestDrivesResponseObj viewAllTestDrivesResponseObj= (ViewAllTestDrivesResponseObj) o;
                    if(viewAllTestDrivesResponseObj!=null && viewAllTestDrivesResponseObj.getStatus()!=null){
                        if (viewAllTestDrivesResponseObj.getStatus().getStatusCode() == SUCCESS) {
                            if (viewAllTestDrivesResponseObj.getTestDrives().size() > 0) {
                                recyclerView.setVisibility(View.VISIBLE);
                                tv_nodata.setVisibility(View.GONE);
                                MyItemRecyclerViewAdapter viewAllRidesRecyclerAdapter = new MyItemRecyclerViewAdapter( viewAllTestDrivesResponseObj.getTestDrives(), mListener);
                                recyclerView.setAdapter(viewAllRidesRecyclerAdapter);
                            }
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            tv_nodata.setVisibility(View.VISIBLE);
                            //showSnackBar(viewAllTestDrivesResponseObj.getStatus().getErrorDescription());
                        }
                    }
                    break;
            }
        }

        @Override
        public void onError(int serviceId) {

            switch (serviceId) {
                case VIEW_ALL_TESTDRIVE_SERVICE_ID:

                    break;
            }
        }
    };
}
