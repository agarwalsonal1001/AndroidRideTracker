package com.wondercars.ridetracker.Retrofit.DTOs.GetExecutivesDTOs;

import com.wondercars.ridetracker.Retrofit.DTOs.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 18/11/17.
 */

public class GetExecutivesResponseObj {

    Status status;
    List<ExecutivesDetailsObj> rideTrackerUsers;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ExecutivesDetailsObj> getExecutivesDetailsObjArrayList() {
        return rideTrackerUsers;
    }

    public void setExecutivesDetailsObjArrayList(List<ExecutivesDetailsObj> executivesDetailsObjArrayList) {
        this.rideTrackerUsers = executivesDetailsObjArrayList;
    }
}
