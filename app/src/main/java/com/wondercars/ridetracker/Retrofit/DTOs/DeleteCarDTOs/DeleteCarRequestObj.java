package com.wondercars.ridetracker.Retrofit.DTOs.DeleteCarDTOs;

/**
 * Created by acer on 3/6/18.
 */

public class DeleteCarRequestObj {

    String carId;
    String admin_uid;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getAdmin_uid() {
        return admin_uid;
    }

    public void setAdmin_uid(String admin_uid) {
        this.admin_uid = admin_uid;
    }
}
