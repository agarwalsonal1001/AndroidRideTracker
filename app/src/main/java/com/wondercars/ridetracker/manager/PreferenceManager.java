package com.wondercars.ridetracker.manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.wondercars.ridetracker.Application.RideTrackerApplicationClass;

/**
 * Created by acer on 18/11/17.
 */

public class PreferenceManager {

    private static final String APP_ID = "RideTracker_pref";
    private static final int WORLD_READABLE = Activity.MODE_PRIVATE;


    public static final String PREF_LOGIN_CURRENT_TAG = "PREF_LOGIN_CURRENT_TAG";
    public static final String PREF_NAVIGATION_FLAG = "PREF_NAVIGATION_FLAG";
    public static final String PREF_ADMIN_UID = "PREF_ADMIN_UID";
    public static final String PREF_ADMIN_USER_NAME = "PREF_ADMIN_USER_NAME";
    public static final String PREF_DEVICE_IDENTIFICATION_ID = "PREF_DEVICE_IDENTIFICATION_ID";
    public static final String PREF_NOTIFICATION_DEVICE_TOKEN = "PREF_NOTIFICATION_DEVICE_TOKEN";
    public static final String PREF_INDIVISUAL_ID = "PREF_INDIVISUAL_ID";
    public static final String PREF_INDIVISUAL_CONTACT_NUMBER = "PREF_INDIVISUAL_CONTACT_NUMBER";
    public static final String PREF_SESSION_ID = "PREF_SESSION_ID";

    public static boolean readBoolean(String key) {
        SharedPreferences pref = RideTrackerApplicationClass.getInstance().getApplicationContext().getSharedPreferences(APP_ID,
                WORLD_READABLE);
        return pref.getBoolean(key, false);
    }

    public static void writeBoolean(String key, boolean val) {
        SharedPreferences pref = RideTrackerApplicationClass.getInstance().getApplicationContext().getSharedPreferences(APP_ID,
                WORLD_READABLE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, val);
        editor.commit();
    }

    public static String readString( String key) {
        SharedPreferences pref = RideTrackerApplicationClass.getInstance().getApplicationContext().getSharedPreferences(APP_ID,
                WORLD_READABLE);
        return pref.getString(key, "");
    }

    public static void writeString( String key, String val) {
        SharedPreferences pref = RideTrackerApplicationClass.getInstance().getApplicationContext().getSharedPreferences(APP_ID,
                WORLD_READABLE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, val);
        editor.commit();
    }

    public static int readInteger( String key) {
        SharedPreferences pref = RideTrackerApplicationClass.getInstance().getApplicationContext().getSharedPreferences(APP_ID,
                WORLD_READABLE);
        return pref.getInt(key, 0);
    }

    public static void writeInteger( String key, int val) {
        SharedPreferences pref = RideTrackerApplicationClass.getInstance().getApplicationContext().getSharedPreferences(APP_ID,
                WORLD_READABLE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, val);
        editor.commit();
    }

    public static String getObjectToString(Object mObject) {
        Gson mGson = (RideTrackerApplicationClass.getInstance()).getGsonObject();
        return mGson.toJson(mObject);
    }

    public static Object getStringToObject(String stringToConvert, Class<?> cls) {
        Gson mGson = RideTrackerApplicationClass.getInstance().getGsonObject();
        Object mObject = mGson.fromJson(stringToConvert, cls);
        return mObject;
    }

    public static void clearAllPreference() {
        //SharedPreferences preferences = pref.c
        SharedPreferences pref = RideTrackerApplicationClass.getInstance().getApplicationContext().getSharedPreferences(APP_ID,
                WORLD_READABLE);
        pref.edit().clear().commit();
    }
}
