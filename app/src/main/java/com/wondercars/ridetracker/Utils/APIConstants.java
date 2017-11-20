package com.wondercars.ridetracker.Utils;

/**
 * Created by umer on 8/11/17.
 */

public interface APIConstants {

    String baseurl = "https://ridetracker-login.herokuapp.com";

    interface RetrofitMethodConstants{

        String LOGIN_API ="/login";
        String ADMIN_LOGIN_API ="/adminlogin";
        String CREATE_EXECUTIVES_API="/upsertUser";
        String GET_EXECUTIVES= "/getExecutives";
        String GET_VARIANTS="/getVariants";
        String CREATE_CAR_API = "/createCar";
        String GET_CARS_API ="/getCars";

    }


    interface RetrofitConstants{

        int SUCCESS =1;
        int FAILURE = 0;
    }
}


