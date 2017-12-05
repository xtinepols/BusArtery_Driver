package com.example.christinebpolesti.busartery_driver.Modules;

import android.os.AsyncTask;

import com.example.christinebpolesti.busartery_driver.Activity.MapsActivity;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by christine B. Polesti on 11/21/2017.
 */

public class PassengerDestinationDistance {

    private LatLng mDriver;
    private LatLng mPassengerLocation;
    private MapsActivity mMapsActivity;
    String mDriverLat, mDriverLng, mPassengerLocLat, mPassengerLocLng, mDistance;

    private static final String DIRECTION_URL_API = "https://maps.googleapis.com/maps/api/directions/json?";
    private static final String GOOGLE_API_KEY = "AIzaSyBZrW-fmAJZXAq9YO7mCQrxQJuJfu174KM";

    public PassengerDestinationDistance(MapsActivity mapsActivity, LatLng driver, LatLng latLngPass) {
        this.mMapsActivity = mapsActivity;
        this.mDriver = driver;
        this.mPassengerLocation = latLngPass;
    }

    public void execute() throws UnsupportedEncodingException {
        mDriverLat = String.valueOf(mDriver.latitude);
        mDriverLng = String.valueOf(mDriver.longitude);

        mPassengerLocLat = String.valueOf(mPassengerLocation.latitude);
        mPassengerLocLng = String.valueOf(mPassengerLocation.longitude);
        new DownLoadRawData().execute(createUrl());
    }

    private String createUrl() throws UnsupportedEncodingException {
        String urlDriverLat = URLEncoder.encode(mDriverLat, "utf-8");
        String urlDriverLng = URLEncoder.encode(mDriverLng, "utf-8");
        String urlPassengerLocLat = URLEncoder.encode(mPassengerLocLat, "utf-8");
        String urlPassengerLocLng = URLEncoder.encode(mPassengerLocLng, "utf-8");

        return DIRECTION_URL_API + "origin" + urlDriverLat + ","
                + urlDriverLng + "&destination=" + urlPassengerLocLat
                + urlPassengerLocLng + "&key=" + GOOGLE_API_KEY;
    }

    private class DownLoadRawData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String link = strings[0];
            try {
                URL url = new URL(link);
                InputStream is = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line = "\n");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                parseJson(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void parseJson(String data) throws JSONException {
        final JSONObject jsonData = new JSONObject(data);
        JSONArray jsonRoutes = jsonData.getJSONArray("routes");

        JSONObject jsonRoute = jsonRoutes.getJSONObject(0);

        JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
        JSONObject jsonLeg = jsonLegs.getJSONObject(0);
        JSONObject jsonDistance = jsonLeg.getJSONObject("distance");

        mDistance = jsonDistance.getString("value");

//        mMapsActivity.destinationDistance(mDistance);
    }
}
