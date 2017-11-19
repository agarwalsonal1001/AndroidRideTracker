package com.wondercars.ridetracker.Application;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

import com.google.gson.Gson;
import com.wondercars.ridetracker.manager.PreferenceManager;

import java.util.UUID;

/**
 * Created by umer on 8/11/17.
 */

public class RideTrackerApplicationClass extends Application {
    public static RideTrackerApplicationClass myInstance;
    public static boolean isGreaterKitkat = false;
    private Gson mGson;
    private static TelephonyManager tManager;

    @Override
    public void onCreate() {
        super.onCreate();
        myInstance = this;
        isGreaterKitkat = versionK();
        mGson = new Gson();
        tManager = (TelephonyManager) getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
    }

    public static RideTrackerApplicationClass getInstance() {

        //Environment.configure(Environment.PRODUCTION, Constants.MerchantId.MERCHANT_ID);
        return RideTrackerApplicationClass.myInstance;
    }

    public Gson getGsonObject() {
        return mGson;
    }

    public String getIMEINumber(Context context) {
        String identifier = null;
        if (PreferenceManager.readString(PreferenceManager.PREF_DEVICE_IDENTIFICATION_ID).equalsIgnoreCase("")) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                UUID uuid = UUID.randomUUID();
                identifier = uuid.toString();
                PreferenceManager.writeString(PreferenceManager.PREF_DEVICE_IDENTIFICATION_ID, identifier);
            } else {
                identifier = tManager.getDeviceId();
                PreferenceManager.writeString(PreferenceManager.PREF_DEVICE_IDENTIFICATION_ID, identifier);
            }

        } else {
            identifier = PreferenceManager.readString(PreferenceManager.PREF_DEVICE_IDENTIFICATION_ID);
        }
        return identifier;
    }

    private static boolean versionK() {
        boolean isGreaterKitkat = false;
        try {
            int currentapiVersion = Build.VERSION.SDK_INT;
            if (currentapiVersion >= Build.VERSION_CODES.KITKAT) {
                isGreaterKitkat = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return isGreaterKitkat;
        }
        return isGreaterKitkat;
    }


}
