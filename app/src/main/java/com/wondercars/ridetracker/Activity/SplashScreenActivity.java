package com.wondercars.ridetracker.Activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wondercars.ridetracker.BaseClasses.BaseActivity;
import com.wondercars.ridetracker.R;
import com.wondercars.ridetracker.manager.PreferenceManager;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler ha = new Handler();
        ha.postDelayed(new Runnable() {
            @Override
            public void run() {
                //call function
                if(PreferenceManager.readBoolean(PreferenceManager.PREF_LOGIN_CURRENT_TAG)){
                    callActivity(NavigationActivity.class);
                    finish();
                }else {
                    callActivity(LoginActivity.class);
                    finish();
                }

            }
        }, 3100);
    }

    // demo change
}
