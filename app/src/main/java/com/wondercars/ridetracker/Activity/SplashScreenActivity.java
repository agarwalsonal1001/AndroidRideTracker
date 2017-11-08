package com.wondercars.ridetracker.Activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wondercars.ridetracker.BaseClasses.BaseActivity;
import com.wondercars.ridetracker.R;

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
                callActivity(LoginActivity.class);
            }
        }, 3000);
    }

    // demo change
}
