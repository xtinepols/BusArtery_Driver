package com.example.christinebpolesti.busartery_driver.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by christine B. Polesti on 11/21/2017.
 */

public class Passenger {

    @SerializedName("name")
    private String name;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("num_seat")
    private int numSeat;

    @SerializedName("destination")
    private String destination;

    public Passenger() {}

    public Passenger(String name, double latitude, double longitude, int numSeat, String destination) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.numSeat = numSeat;
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public int getNumSeat() {
        return numSeat;
    }

    public String getDestination() {
        return destination;
    }
}
