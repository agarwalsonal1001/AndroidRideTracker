package com.wondercars.ridetracker.Retrofit.DTOs.ViewAllRidesDTOs;

/**
 * Created by acer on 29/1/18.
 */

public class ViewRidesRequestObj {

    private String admin_uid;

    public String getAdminUid() { return this.admin_uid; }

    public void setAdminUid(String admin_uid) { this.admin_uid = admin_uid; }

    private String uid;

    public String getUid() { return this.uid; }

    public void setUid(String uid) { this.uid = uid; }

    private String createDate;

    public String getCreateDate() { return this.createDate; }

    public void setCreateDate(String createDate) { this.createDate = createDate; }

    private String ride_type;

    public String getRideType() { return this.ride_type; }

    public void setRideType(String ride_type) { this.ride_type = ride_type; }

    private String carId;

    public String getCarId() { return this.carId; }

    public void setCarId(String carId) { this.carId = carId; }
}
