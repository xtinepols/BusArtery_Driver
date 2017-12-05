package com.example.christinebpolesti.busartery_driver.Interface;

import com.example.christinebpolesti.busartery_driver.Model.HistoryResponse;
import com.example.christinebpolesti.busartery_driver.Model.PassengerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by christine B. Polesti on 11/22/2017.
 */

public interface PassengerApiService {

    @GET("passenger/name") //not sure
    Call<PassengerResponse> getDriverHistory(@Query("api_key") String apiKey);
}
