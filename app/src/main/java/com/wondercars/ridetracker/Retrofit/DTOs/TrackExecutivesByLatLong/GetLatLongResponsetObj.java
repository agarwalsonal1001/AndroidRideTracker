package com.wondercars.ridetracker.Retrofit.DTOs.TrackExecutivesByLatLong;

import com.wondercars.ridetracker.Retrofit.DTOs.Status;

import java.util.ArrayList;

/**
 * Created by acer on 4/2/18.
 */

public class GetLatLongResponsetObj {

    Status status;
    ArrayList<LatLongDetails> latLongs;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ArrayList<LatLongDetails> getLatLongs() {
        return latLongs;
    }

    public void setLatLongs(ArrayList<LatLongDetails> latLongs) {
        this.latLongs = latLongs;
    }
}
