package com.example.christinebpolesti.busartery_driver.Interface;

import com.example.christinebpolesti.busartery_driver.Modules.Route;

import java.util.List;

/**
 * Created by christine B. Polesti on 11/19/2017.
 */

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
