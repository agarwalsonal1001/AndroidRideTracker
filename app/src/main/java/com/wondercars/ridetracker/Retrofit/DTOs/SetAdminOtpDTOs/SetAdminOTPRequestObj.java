package com.wondercars.ridetracker.Retrofit.DTOs.SetAdminOtpDTOs;

/**
 * Created by acer on 24/6/18.
 */

public class SetAdminOTPRequestObj {

    String admin_otp;
    String admin_uid;

    public String getAdmin_otp() {
        return admin_otp;
    }

    public void setAdmin_otp(String admin_otp) {
        this.admin_otp = admin_otp;
    }

    public String getAdmin_uid() {
        return admin_uid;
    }

    public void setAdmin_uid(String admin_uid) {
        this.admin_uid = admin_uid;
    }
}
