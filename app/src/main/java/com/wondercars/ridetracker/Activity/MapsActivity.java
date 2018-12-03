package com.wondercars.ridetracker.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

import com.google.android.gms.location.LocationListener;

import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wondercars.ridetracker.CustomClasses.AppProgressDialog;
import com.wondercars.ridetracker.R;
import com.wondercars.ridetracker.Retrofit.DTOs.GetExecutivesDTOs.ExecutivesDetailsObj;
import com.wondercars.ridetracker.Retrofit.DTOs.TrackExecutivesByLatLong.GetLatLongRequestObj;
import com.wondercars.ridetracker.Retrofit.DTOs.TrackExecutivesByLatLong.GetLatLongResponsetObj;
import com.wondercars.ridetracker.Utils.APIConstants;
import com.wondercars.ridetracker.manager.PreferenceManager;

import umer.accl.interfaces.RetrofitInterface;
import umer.accl.retrofit.RetrofitParamsDTO;
import umer.accl.utils.Constants;

import static com.wondercars.ridetracker.Utils.APIConstants.RetrofitConstants.SUCCESS;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 12345;
    private static final int GET_LAT_LONG_API_SERVICE_ID = 0;

    LocationManager manager;
    ExecutivesDetailsObj executivesDetailsObj;
    Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        init();
    }

    private void init() {
        if (getIntent().hasExtra("ExecutivesDetailsObj")) {
            executivesDetailsObj = (ExecutivesDetailsObj) getIntent().getSerializableExtra("ExecutivesDetailsObj");
            callGetLatLongAPI();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }


    private GetLatLongRequestObj getLatLongRequestObj() {
        GetLatLongRequestObj getLatLongRequestObj = new GetLatLongRequestObj();
        getLatLongRequestObj.setAdminUid(PreferenceManager.readString(PreferenceManager.PREF_ADMIN_UID));
        getLatLongRequestObj.setUid(executivesDetailsObj.getUid());
        return getLatLongRequestObj;

    }

    private void callGetLatLongAPI() {
        RetrofitParamsDTO retrofitParamsDTO = new RetrofitParamsDTO.RetrofitBuilder(this,
                APIConstants.baseurl, getLatLongRequestObj(), GetLatLongResponsetObj.class,
                APIConstants.RetrofitMethodConstants.GET_LAT_LONG_API, GET_LAT_LONG_API_SERVICE_ID, Constants.ApiMethods.POST_METHOD, retrofitInterface)
                .setProgressDialog(new AppProgressDialog(this))
                .setShowDialog(true)
                .build();
        retrofitParamsDTO.execute(retrofitParamsDTO);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            displayPromptForEnablingGPS(this);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
       /* LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_ACCESS_FINE_LOCATION);
            return;
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    buildGoogleApiClient();
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(MapsActivity.this, "Please enable permissions to use map", Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    RetrofitInterface retrofitInterface = new RetrofitInterface() {
        @Override
        public void onSuccess(Object object, int serviceId) {
            switch (serviceId) {

                case GET_LAT_LONG_API_SERVICE_ID:

                    GetLatLongResponsetObj getLatLongResponsetObj = (GetLatLongResponsetObj) object;
                    if (getLatLongResponsetObj != null && getLatLongResponsetObj.getStatus() != null) {

                        if (getLatLongResponsetObj.getStatus().getStatusCode() == SUCCESS) {
                            if (getLatLongResponsetObj.getLatLongs() != null && getLatLongResponsetObj.getLatLongs().size() > 0) {

                                for (int i = 0; i < getLatLongResponsetObj.getLatLongs().size(); i++) {
                                    LatLng latLng = new LatLng(Double.parseDouble(getLatLongResponsetObj.getLatLongs().get(i).getLatitude()), Double.parseDouble(getLatLongResponsetObj.getLatLongs().get(i).getLongitude()));
                                    mMap.addMarker(new MarkerOptions().position(latLng).title("Position" + i));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                }
                            }
                        }
                    }

                    break;

            }
        }

        @Override
        public void onError(int i) {

        }
    };


    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (mLastLocation == null) {
                mLastLocation = location;

                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title("Current Position"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
               /* MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));*/
            }
        }
    };

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public static void displayPromptForEnablingGPS(
            final Activity activity) {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "Enable either GPS or any other location"
                + " service to find current location.  Click OK to go to"
                + " location services settings to let you do so.";

        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.startActivity(new Intent(action));

                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });
        builder.create().show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locationListener);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
