package com.wondercars.ridetracker.Retrofit.DTOs.LoginServiceDTOs;

/**
 * Created by umer on 8/11/17.
 */

public class LoginRequestObj {
    String username;
    //: "admin",
      String password;
    //: "admin123"


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
