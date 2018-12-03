package com.wondercars.ridetracker.Retrofit.DTOs.TrackExecutivesByLatLong;

/**
 * Created by acer on 4/2/18.
 */

public class GetLatLongRequestObj {

    private String carId;
    private String uid;
    private String adminUid;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAdminUid() {
        return adminUid;
    }

    public void setAdminUid(String adminUid) {
        this.adminUid = adminUid;
    }
}
