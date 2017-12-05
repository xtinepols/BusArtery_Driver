package com.example.christinebpolesti.busartery_driver.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by christine B. Polesti on 11/21/2017.
 */

public class History {

    @SerializedName("bus_num")
    private int busNum;

    @SerializedName("drivers_name")
    private String driversName;

    @SerializedName("route")
    private String route;

    @SerializedName("earnings")
    private int earnings;

    @SerializedName("time_started")
    private String timeStarted;

    @SerializedName("time_ended")
    private String timeEnded;

    @SerializedName("distance")
    private Double distance;

    @SerializedName("total_passenger")
    private int totalPassenger;

    @SerializedName("trip_count")
    private int tripCount;

    @SerializedName("username")
    private String username;

    @SerializedName("date")
    private String date;

    public History() {}

    public History(int busNum, String driversName, String route, int earnings,
                   String timeStarted, String timeEnded, Double distance,
                   int totalPassenger, int tripCount, String username,
                   String date) {
        this.busNum = busNum;
        this.driversName = driversName;
        this.route = route;
        this.earnings = earnings;
        this.timeStarted = timeStarted;
        this.timeEnded = timeEnded;
        this.distance = distance;
        this.totalPassenger = totalPassenger;
        this.tripCount = tripCount;
        this.username = username;
        this.date = date;
    }

    public History(int busNum, String driversName, String route, int earnings,
                   String timeStarted, String timeEnded, Double distance,
                   int totalPassenger, int tripCount, String date) {
        this.busNum = busNum;
        this.driversName = driversName;
        this.route = route;
        this.earnings = earnings;
        this.timeStarted = timeStarted;
        this.timeEnded = timeEnded;
        this.distance = distance;
        this.totalPassenger = totalPassenger;
        this.tripCount = tripCount;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBusNum(){ return  busNum;}

    public String getDriversName() {
        return driversName;
    }

    public String getRoute() {
        return route;
    }

    public int getEarnings() {
        return earnings;
    }

    public String getTimeStarted() {
        return timeStarted;
    }

    public String getTimeEnded() {return timeEnded;}

    public Double getDistance() {
        return distance;
    }

    public int getTotalPassenger() {
        return totalPassenger;
    }

    public int getTripCount() {
        return tripCount;
    }

    public String getDate(){return date;}

    public void setBusNum(int busNum) {
        this.busNum = busNum;
    }

    public void setDriversName(String driversName) {
        this.driversName = driversName;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setEarnings(int earnings) {
        this.earnings = earnings;
    }

    public void setTimeStarted(String timeStarted) {
        this.timeStarted = timeStarted;
    }

    public void setTimeEnded(String timeEnded) {
        this.timeEnded = timeEnded;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void setTotalPassenger(int totalPassenger) {
        this.totalPassenger = totalPassenger;
    }

    public void setTripCount(int tripCount) {
        this.tripCount = tripCount;
    }

    public void setDate(String date) {this.date = date;}
}
