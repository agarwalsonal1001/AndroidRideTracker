package com.wondercars.ridetracker.Retrofit.DTOs.GetVeriantsDTOs;

/**
 * Created by acer on 19/11/17.
 */

public class VeriantsDetails {

    String veriantUid;
    String veriantName;


    public String getVeriantName() {
        return veriantName;
    }

    public void setVeriantName(String veriantName) {
        this.veriantName = veriantName;
    }

    public String getVeriantUid() {

        return veriantUid;
    }

    public void setVeriantUid(String veriantUid) {
        this.veriantUid = veriantUid;
    }
}
