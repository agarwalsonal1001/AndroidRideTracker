package com.wondercars.ridetracker.Retrofit.DTOs.GetAllSRMDTOs;

import com.wondercars.ridetracker.Retrofit.DTOs.Status;

import java.util.List;

/**
 * Created by acer on 24/12/17.
 */

public class GetAllSrmResponseObj {

    Status status;
    private List<RideTrackerUser> rideTrackerUsers = null;

    public List<RideTrackerUser> getRideTrackerUsers() {
        return rideTrackerUsers;
    }

    public void setRideTrackerUsers(List<RideTrackerUser> rideTrackerUsers) {
        this.rideTrackerUsers = rideTrackerUsers;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
