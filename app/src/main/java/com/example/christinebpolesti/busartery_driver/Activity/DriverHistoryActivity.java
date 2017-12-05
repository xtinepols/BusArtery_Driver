package com.example.christinebpolesti.busartery_driver.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.christinebpolesti.busartery_driver.Adapter.HistoryAdapter;
import com.example.christinebpolesti.busartery_driver.Interface.ItemClickListener;
import com.example.christinebpolesti.busartery_driver.Model.History;
import com.example.christinebpolesti.busartery_driver.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class DriverHistoryActivity extends AppCompatActivity
        implements ItemClickListener, AdapterView.OnItemSelectedListener {

    private LinearLayout layoutByDate;
    String name, route, date, sDate, strMonth, strDate,
            strDay, strTime, username, dTimeStarted, dTimeEnded;
    private List<History> historyList = new ArrayList<>();

    SharedPreferences pref, historyPref;
    SharedPreferences.Editor editor, historyEditor;

    private Calendar calendar;
    private EditText dateView;
    Spinner spnr, spnrChoose;
    private RecyclerView recycler;
    private HistoryAdapter adapter;
    private int year, month, day, busNum, earnings, totalPassenger, tripCount;
    private Double distance;

    List<String> spnrTime = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_history);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spnrChoose = (Spinner) findViewById(R.id.spinnerChoose);
        spnrChoose.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(this, R.array.history_choose, android.R.layout.simple_spinner_dropdown_item);
        spnrChoose.setAdapter(adapters);

        layoutByDate = (LinearLayout) findViewById(R.id.layoutByDate);

        spnr = (Spinner) findViewById(R.id.timeSpinner);
        spnr.setOnItemSelectedListener(DriverHistoryActivity.this);

        recycler = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(mLayoutManager);

        pref = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
        editor = pref.edit();

        historyPref = getApplicationContext().getSharedPreferences("historyData", MODE_PRIVATE);
        historyEditor = historyPref.edit();

        fill();

        dateView = (EditText) findViewById(R.id.datepick);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        showDate(year, month + 1, day);
    }

    private void setAdapter() {

        ArrayAdapter<String> adapters = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spnrTime);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr.setAdapter(adapters);
    }

    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year ; arg2 = month ; arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        if (month == 1)
            strMonth = "Jan";
        if (month == 2)
            strMonth = "Feb";
        if (month == 3)
            strMonth = "Mar";
        if (month == 4)
            strMonth = "Apr";
        if (month == 5)
            strMonth = "May";
        if (month == 6)
            strMonth = "Jun";
        if (month == 7)
            strMonth = "Jul";
        if (month == 8)
            strMonth = "Aug";
        if (month == 9)
            strMonth = "Sep";
        if (month == 10)
            strMonth = "Oct";
        if (month == 11)
            strMonth = "Nov";
        if (month == 12)
            strMonth = "Dec";

        if (day < 10) {
            if (day == 1)
                strDay = "01";
            if (day == 2)
                strDay = "02";
            if (day == 3)
                strDay = "03";
            if (day == 4)
                strDay = "04";
            if (day == 5)
                strDay = "05";
            if (day == 6)
                strDay = "06";
            if (day == 7)
                strDay = "07";
            if (day == 8)
                strDay = "08";
            if (day == 9)
                strDay = "09";

            dateView.setText(strMonth + "-" + strDay + "-" + year);
        } else {
            dateView.setText(strMonth + "-" + day + "-" + year);
        }

        strDate = dateView.getText().toString();

        username = pref.getString("username", "");

        historyList = new ArrayList<>();

        if (historyList.size() > 0) {
            historyList.clear();
        }

        if (strDate.equals(sDate)) {
            historyList.add(new History(busNum, name, route, earnings, dTimeStarted,
                    dTimeEnded, distance, totalPassenger, tripCount, date));
        }

        Collections.reverse(historyList);

        adapter = new HistoryAdapter(historyList);
        recycler.setAdapter(adapter);
        adapter.setClickListener(DriverHistoryActivity.this);

    }

    public void fill() {
        username = pref.getString("username", "");
        historyList = new ArrayList<>();

        if (historyList.size() > 0) {
            historyList.clear();
        }

//        busNum = .getValue().toString();
//        name = .getValue().toString();
//        route = .getValue().toString();
//        earnings = .getValue().toString();
//        dTimeStarted = .getValue().toString();
//        dTimeEnded = .getValue().toString();
//        distance = .getValue().toString();
//        totalPassenger = .getValue().toString();
//        tripCount = .getValue().toString();
//        date = .getValue().toString();
        //after getting the values, add to arraylist
        historyList.add(new History(busNum, name, route, earnings, dTimeStarted, dTimeEnded, distance, totalPassenger, tripCount, date));

        Collections.reverse(historyList);

        adapter = new HistoryAdapter(historyList);
        recycler.setAdapter(adapter);
        adapter.setClickListener(DriverHistoryActivity.this);
    }

    @Override
    public void onClick(View view, int position) {
        History history = historyList.get(position);

        historyEditor.putString("driversName", history.getDriversName());
        historyEditor.putInt("busNumber", history.getBusNum());
        historyEditor.putString("route", history.getRoute());
        historyEditor.putInt("earnings", history.getEarnings());
        historyEditor.putString("date", history.getDate());
        historyEditor.putString("timeStarted", history.getTimeStarted());
        historyEditor.putString("timeEnded", history.getTimeEnded());
        historyEditor.putString("distance", String.valueOf(history.getDistance()));
        historyEditor.putInt("passenger", history.getTotalPassenger());
        historyEditor.putInt("tripCount", history.getTripCount());
        historyEditor.commit();

        Intent i = new Intent(this, HistoryViewActivity.class);
        startActivity(i);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.spinnerChoose:
                String choose = adapterView.getItemAtPosition(i).toString();
                if (choose.equals("All")) {
                    layoutByDate.setVisibility(View.INVISIBLE);
                    fill();
                } else if (choose.equals("Date")) {
                    layoutByDate.setVisibility(View.VISIBLE);
                    dateView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.timeSpinner:
                strTime = adapterView.getItemAtPosition(i).toString();

                username = pref.getString("username", "");

                historyList = new ArrayList<>();

                if (historyList.size() > 0) {
                    historyList.clear();
                }
//                busNum = getValue().toString();
//                name = getValue().toString();
//                route = getValue().toString();
//                earnings = getValue().toString();
//                dTimeStarted = getValue().toString();
//                dTimeEnded = getValue().toString();
//                distance = getValue().toString();
//                totalPassenger = getValue().toString();
//                tripCount = getValue().toString();
//                date = getValue().toString();

                historyList.add(new History(busNum, name, route, earnings, dTimeStarted, dTimeEnded, distance, totalPassenger, tripCount, date));

                Collections.reverse(historyList);

                adapter = new HistoryAdapter(historyList);
                recycler.setAdapter(adapter);
                adapter.setClickListener(DriverHistoryActivity.this);

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
