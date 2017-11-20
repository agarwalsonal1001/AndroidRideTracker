package com.wondercars.ridetracker.Retrofit.DTOs.CreateCarDTOs;

import com.wondercars.ridetracker.Retrofit.DTOs.Status;

/**
 * Created by acer on 19/11/17.
 */

public class CreateCarsResponseObj {

    Status status;
    String carId;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
}
