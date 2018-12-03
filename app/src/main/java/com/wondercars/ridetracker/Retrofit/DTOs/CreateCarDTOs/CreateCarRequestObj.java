package com.wondercars.ridetracker.Retrofit.DTOs.CreateCarDTOs;

/**
 * Created by acer on 19/11/17.
 */

public class CreateCarRequestObj {

    String carModelId;
    String registrationNumber;
    String variantId;
    String mode;
    String fuelType;
    String admin_uid;

    public String getcarModelId() {
        return carModelId;
    }

    public void setcarModelId(String carModelId) {
        this.carModelId = carModelId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getAdmin_uid() {
        return admin_uid;
    }

    public void setAdmin_uid(String admin_uid) {
        this.admin_uid = admin_uid;
    }
}
