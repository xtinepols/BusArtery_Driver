package com.example.christinebpolesti.busartery_driver.Interface;

import com.example.christinebpolesti.busartery_driver.Model.HistoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by christine B. Polesti on 11/22/2017.
 */

public interface HistoryApiService {

    @GET("history/name") //not sure
    Call<HistoryResponse> getDriverHistory(@Query("api_key") String apiKey);
}
