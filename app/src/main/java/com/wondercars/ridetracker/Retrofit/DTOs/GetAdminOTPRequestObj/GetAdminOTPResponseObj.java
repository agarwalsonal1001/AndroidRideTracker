package com.wondercars.ridetracker.Retrofit.DTOs.GetAdminOTPRequestObj;

import com.wondercars.ridetracker.Retrofit.DTOs.Status;

/**
 * Created by acer on 24/6/18.
 */

public class GetAdminOTPResponseObj {

    Status status;
    String admin_otp;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAdmin_otp() {
        return admin_otp;
    }

    public void setAdmin_otp(String admin_otp) {
        this.admin_otp = admin_otp;
    }
}
