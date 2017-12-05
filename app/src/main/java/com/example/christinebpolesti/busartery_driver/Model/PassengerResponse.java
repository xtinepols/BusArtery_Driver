package com.example.christinebpolesti.busartery_driver.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by christine B. Polesti on 11/22/2017.
 */

public class PassengerResponse {

    @SerializedName("results")
    private List<Passenger> results;

    @SerializedName("total_results")
    private int totalResults;

    public List<Passenger> getResults() {
        return results;
    }

    public void setResults(List<Passenger> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
