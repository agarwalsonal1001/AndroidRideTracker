package com.wondercars.ridetracker.Retrofit.DTOs.GenerateReports;

/**
 * Created by acer on 22/7/18.
 */

public class GenerateReportsRequestObj {

    String admin_uid;
    String type;
    String fromDate;
    String toDate;

    public String getAdmin_uid() {
        return admin_uid;
    }

    public void setAdmin_uid(String admin_uid) {
        this.admin_uid = admin_uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
