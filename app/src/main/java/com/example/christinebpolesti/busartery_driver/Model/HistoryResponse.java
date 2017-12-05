package com.example.christinebpolesti.busartery_driver.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by christine B. Polesti on 11/22/2017.
 */

public class HistoryResponse {

    @SerializedName("results")
    private List<History> results;

    @SerializedName("total_results")
    private int totalResults;

    public List<History> getResults() {
        return results;
    }

    public void setResults(List<History> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
