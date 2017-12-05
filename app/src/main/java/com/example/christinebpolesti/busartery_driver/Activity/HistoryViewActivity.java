package com.example.christinebpolesti.busartery_driver.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.christinebpolesti.busartery_driver.R;

public class HistoryViewActivity extends AppCompatActivity {

    SharedPreferences historyPref;
    SharedPreferences.Editor historyEditor;

    private EditText driversName;
    private EditText busNumber;
    private EditText route;
    private EditText earning;
    private EditText date;
    private EditText timeStarted;
    private EditText timeEnded;
    private EditText distance;
    private EditText passenger;
    private EditText tripCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_view);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        historyPref = getApplicationContext().getSharedPreferences("historyData", MODE_PRIVATE);
        historyEditor = historyPref.edit();

        driversName = (EditText) findViewById(R.id.edtDriversName);
        busNumber = (EditText) findViewById(R.id.edtBusnum);
        route = (EditText) findViewById(R.id.edtRoute);
        earning = (EditText) findViewById(R.id.edtEarning);
        date = (EditText) findViewById(R.id.edtDate);
        timeStarted = (EditText) findViewById(R.id.edtTimeStarted);
        timeEnded = (EditText) findViewById(R.id.edtTimeEnded);
        distance = (EditText) findViewById(R.id.edtDistance);
        passenger = (EditText) findViewById(R.id.edtTotalPass);
        tripCount = (EditText) findViewById(R.id.edtTripCount);

        driversName.setText(historyPref.getString("driversName", ""));
        busNumber.setText(historyPref.getString("busNumber", ""));
        route.setText(historyPref.getString("route", ""));
        earning.setText(historyPref.getString("earnings", ""));
        date.setText(historyPref.getString("date", ""));
        timeStarted.setText(historyPref.getString("timeStarted", ""));
        timeEnded.setText(historyPref.getString("timeEnded", ""));
        distance.setText(historyPref.getString("distance", ""));
        passenger.setText(historyPref.getString("passenger", ""));
        tripCount.setText(historyPref.getString("tripCount", ""));

        historyEditor.clear();
        historyEditor.commit();
    }
}
