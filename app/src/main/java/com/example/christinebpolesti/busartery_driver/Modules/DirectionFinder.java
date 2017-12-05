package com.example.christinebpolesti.busartery_driver.Modules;

import android.os.AsyncTask;

import com.example.christinebpolesti.busartery_driver.Activity.MapsActivity;
import com.example.christinebpolesti.busartery_driver.Interface.DirectionFinderListener;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by christine B. Polesti on 11/19/2017.
 */

public class DirectionFinder {

    private static final String DIRECTION_URL_API = "https://maps.googleapis.com/maps/api/directions/json?";
    private static final String GOOGLE_API_KEY = "AIzaSyBZrW-fmAJZXAq9YO7mCQrxQJuJfu174KM";
    private DirectionFinderListener listener;
    private String origin;
    private String destination;
    private String mode = "mode=driving";

    public DirectionFinder(MapsActivity listener, String origin, String destination, String mode) {
        this.listener = (DirectionFinderListener) listener;
        this.origin = origin;
        this.destination = destination;

        this.mode = mode;
    }

    public void execute() throws UnsupportedEncodingException {
        listener.onDirectionFinderStart();
        new DownloadRawData().execute(createUrl());
    }

    private String createUrl() throws UnsupportedEncodingException {
        String urlOrigin = URLEncoder.encode(origin, "utf-8");
        String urlDestination = URLEncoder.encode(destination,"utf-8");
        String urlMode = URLEncoder.encode(mode, "utf-8");

        return DIRECTION_URL_API + "origin=" + urlOrigin + "&destination=" +
                urlDestination + "&key=" + "&mode+" + urlMode + GOOGLE_API_KEY;
    }

    private class DownloadRawData extends AsyncTask<String, Void, String> {

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
                    buffer.append(line + "\n");
                }
                return buffer.toString();
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

    private void parseJson(String data) throws JSONException {
        if (data == null)
            return;

        List<Route> routes = new ArrayList<Route>();
        JSONObject jsonObjectData = new JSONObject(data);
        JSONArray jsonArrayRoutes = jsonObjectData.getJSONArray("routes");

        for (int i = 0; i < jsonArrayRoutes.length(); i++) {
            JSONObject jsonObjectRoute = jsonArrayRoutes.getJSONObject(i);
            Route route = new Route();

            JSONObject overview_polylineJson = jsonObjectRoute.getJSONObject("overview_polyline");
            JSONArray jsonArrayLeg = jsonObjectRoute.getJSONArray("legs");
            JSONObject jsonLeg = jsonArrayLeg.getJSONObject(0);
            JSONObject jsonDistace = jsonLeg.getJSONObject("distance");
            JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
            JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");
            JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");

            route.distance = new Distance(jsonDistace.getString("text"), jsonDistace.getInt("value"));
            route.duration = new Duration(jsonDuration.getString("text"), jsonDuration.getInt("value"));
            route.endAddress = jsonLeg.getString("end_address");
            route.startAddress = jsonLeg.getString("start_address");
            route.startLocation = new LatLng(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng"));
            route.endLocation = new LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng"));
            route.points = decodePolyline(overview_polylineJson.getString("points"));

            routes.add(route);
        }

        listener.onDirectionFinderSuccess(routes);
    }

    private List<LatLng> decodePolyline(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b > 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(lat / 100000d, lng / 100000d));
        }
        return decoded;
    }
}
