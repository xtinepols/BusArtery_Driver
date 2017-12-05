package com.example.christinebpolesti.busartery_driver.Modules;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by christine B. Polesti on 11/19/2017.
 */

public class Route {

    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}
