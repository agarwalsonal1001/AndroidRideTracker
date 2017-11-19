package com.wondercars.ridetracker.Retrofit.DTOs.CreateExecutivesDTOs;

import com.wondercars.ridetracker.Retrofit.DTOs.Status;

/**
 * Created by acer on 18/11/17.
 */

public class CreateExecutivesResponsetObj {

    Status status;
    String uid;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
