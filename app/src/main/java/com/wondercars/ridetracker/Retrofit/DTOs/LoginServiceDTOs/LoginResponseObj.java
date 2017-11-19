package com.wondercars.ridetracker.Retrofit.DTOs.LoginServiceDTOs;

import com.wondercars.ridetracker.Retrofit.DTOs.Status;

/**
 * Created by umer on 8/11/17.
 */

public class LoginResponseObj {

    Status status;
    String admin_uid;

    public String getAdmin_uid() {
        return admin_uid;
    }

    public void setAdmin_uid(String admin_uid) {
        this.admin_uid = admin_uid;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
