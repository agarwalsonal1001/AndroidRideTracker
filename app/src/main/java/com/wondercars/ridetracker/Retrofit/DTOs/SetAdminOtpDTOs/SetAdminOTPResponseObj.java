package com.wondercars.ridetracker.Retrofit.DTOs.SetAdminOtpDTOs;

import com.wondercars.ridetracker.Retrofit.DTOs.Status;

/**
 * Created by acer on 24/6/18.
 */

public class SetAdminOTPResponseObj {


    Status status;
    String admin_uid;
    String admin_otp;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAdmin_uid() {
        return admin_uid;
    }

    public void setAdmin_uid(String admin_uid) {
        this.admin_uid = admin_uid;
    }

    public String getAdmin_otp() {
        return admin_otp;
    }

    public void setAdmin_otp(String admin_otp) {
        this.admin_otp = admin_otp;
    }
}
