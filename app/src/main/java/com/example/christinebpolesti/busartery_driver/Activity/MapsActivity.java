package com.example.christinebpolesti.busartery_driver.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.christinebpolesti.busartery_driver.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        NavigationView.OnNavigationItemSelectedListener {

    RequestQueue requestQueue;

    //    String baseUrl = ; //given by julius
    String url;

    SharedPreferences.Editor editor, editorBusnum, editorCapacity, editorRoute;
    SharedPreferences pref, prefBUSNUM, prefCAPACITY, prefROUTE;
    //strings for shared pref
    String usernamePref, driverUsernamePref, driverNamePref, routesPref, busNumPref;

    private FloatingActionButton fab, fab_numpass, fab_add, fab_minus, fab_vacancy, fab_unload;
    private LinearLayout layoutNumpass, layoutAdd, layoutMinus, layoutVacancy, layoutUnload;

    private EditText medtDestination;

    private GoogleApiClient mgooGoogleApiClient;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrentMarker;

    private static final String MIME_TEXT_PLAIN = "text/plain";
    private NfcAdapter mnfcNfcAdapter;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_nav);
        requestQueue = Volley.newRequestQueue(this);

        pref = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
        editor = pref.edit();

        prefROUTE = getApplicationContext().getSharedPreferences("route", MODE_PRIVATE);
        editorRoute = prefROUTE.edit();

        prefBUSNUM = getApplicationContext().getSharedPreferences("credentials", MODE_PRIVATE);
        editorBusnum = prefBUSNUM.edit();

        prefCAPACITY = getApplicationContext().getSharedPreferences("Capacity", MODE_PRIVATE);
        editorCapacity = prefCAPACITY.edit();

        setUpNavView();
        setUpToolbarDrawer();

        usernamePref = pref.getString("username", "");
        //busnum = prefBusnum.getString("busnumValue", "");
        driverUsernamePref = pref.getString("username", "");
        //driverName = pref.getString("driverName", "");
        driverNamePref = prefBUSNUM.getString("driverNameValue", "");
        //routes = prefRoute.getString("routes", "");
        routesPref = prefBUSNUM.getString("routeValue", "");

        busNumPref = pref.getString("busnum", "");

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        medtDestination = (EditText) findViewById(R.id.edtDestination);

        //get the bus destination designated for this driver
//        busDestination = busSnapShot.child("busDestination").getValue().toString().trim();
//        medtDestination.setText(busDestination);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        mbtnStartStop = (Button) findViewById(R.id.btnStartStop);
//        mbtnStartStop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Date date = new Date(mLastLocation.getTime());
//                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
//                timeStarted = dateFormat.format(date);
////                sendRequest();
//            }
//        });

        fab = (FloatingActionButton) findViewById(R.id.fab_showMore);
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add_pass);
        fab_minus = (FloatingActionButton) findViewById(R.id.fab_minus_pass);
        fab_numpass = (FloatingActionButton) findViewById(R.id.fab_num_pass);
        fab_vacancy = (FloatingActionButton) findViewById(R.id.fab_num_vacancy);
        fab_unload = (FloatingActionButton) findViewById(R.id.fab_unload);

        layoutAdd = (LinearLayout) findViewById(R.id.layoutAdd);
        layoutMinus = (LinearLayout) findViewById(R.id.layoutMinus);
        layoutNumpass = (LinearLayout) findViewById(R.id.layoutNumpass);
        layoutVacancy = (LinearLayout) findViewById(R.id.layoutVacancy);
        layoutUnload = (LinearLayout) findViewById(R.id.layoutUnload);

        fab.setTag(1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int status = (Integer) view.getTag();
                if (status == 1) {
                    layoutAdd.setVisibility(View.VISIBLE);
                    layoutMinus.setVisibility(View.VISIBLE);
                    layoutNumpass.setVisibility(View.VISIBLE);
                    layoutVacancy.setVisibility(View.VISIBLE);
                    layoutUnload.setVisibility(View.VISIBLE);
                    view.setTag(0);
                } else {
                    layoutAdd.setVisibility(View.INVISIBLE);
                    layoutMinus.setVisibility(View.INVISIBLE);
                    layoutNumpass.setVisibility(View.INVISIBLE);
                    layoutVacancy.setVisibility(View.INVISIBLE);
                    layoutUnload.setVisibility(View.INVISIBLE);
                    view.setTag(1);
                }
            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add one passenger per click of this button
                //voice message here
            }
        });

        fab_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //deduct one passenger per click of this button
                //voice message here
            }
        });

        fab_vacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //voice message for the number of vacancy of the seats
            }
        });

        fab_numpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //voice message for the total number of passenger(s)
            }
        });

        fab_unload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //unload all passenger.. emergency "TROUBLE"
            }
        });

        mnfcNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        //check if the smartphone has NFC
        if (mnfcNfcAdapter == null) {
            Toast.makeText(this, "NFC not supported to this device.", Toast.LENGTH_SHORT).show();
        }
        //check if NFC is enabled
        if (!mnfcNfcAdapter.isEnabled()) {
            Toast.makeText(this, "Enable your NFC.", Toast.LENGTH_SHORT).show();
        }

        handleIntent(getIntent());
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng Cebu = new LatLng(10.008154, 123.635460);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Cebu, 9));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mgooGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mgooGoogleApiClient.connect();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.drawer_driver_info) {
            Intent intent = new Intent(this, DriverInformationActivity.class);
            startActivity(intent);
        } else if (id == R.id.drawer_driver_history) {
            Intent intent = new Intent(this, DriverHistoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.drawer_change_password) {
            Intent intent = new Intent(this, ChangePasswordActivity.class);
            startActivity(intent);
        } else if (id == R.id.drawer_log_out) {
            logoutDialog();
        }

        item.setChecked(true);
        setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void setUpToolbarDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    public void setUpNavView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void logoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout?")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(MapsActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.veryLightBlue);
        alertDialog.show();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mgooGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrentMarker != null) {
            mCurrentMarker.remove();
        }
        //place current location marker
        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latlng);
        markerOptions.title("Current position");

        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        mCurrentMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
//        if (mgooGoogleApiClient != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mgooGoogleApiClient, this);
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                        if (mgooGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    //NFC
    public void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new MapsActivity.NdefReaderTask().execute(tag);
            } else {
                Toast.makeText(this, "Wrong mime type.", Toast.LENGTH_SHORT).show();
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            //in case we would still use the Tech Discovered Intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techlist = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techlist) {
                if (searchedTech.equals(tech)) {
                    new MapsActivity.NdefReaderTask().execute(tag);
                }
            }
        }
    }

    public static void setupForegroundDispatch(final Activity activity, NfcAdapter nfcAdapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(
                activity.getApplicationContext(), 0, intent, 0);
        IntentFilter[] filters = new IntentFilter[1];
        String[][] techlist = new String[][]{};

        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }
        nfcAdapter.enableForegroundDispatch(activity, pendingIntent, filters, techlist);
    }

    public static void stopForegroundDispatch(final Activity activity, NfcAdapter nfcAdapter) {
        nfcAdapter.disableForegroundDispatch(activity);
    }

    private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];

            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                return null;
            }

            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                        return readText(ndefRecord);
                    } catch (UnsupportedEncodingException e) {
                        Log.e("TAG", "Unsupported Encoding", e);
                    }
                }
            }
            return null;
        }

        private String readText(NdefRecord record) throws UnsupportedEncodingException {
            byte[] id = record.getPayload();
            //get the text encoding
            String textEncoding = ((id[0] & 128) == 0) ? "UTF-8" : "UTF-16";

            //get the language code
            int languageCodeLength = id[0] & 0063;

            //String languagecode = new String(id, 1, languageCodeLength, "US-ASCII");

            //get the text
            return new String(id, languageCodeLength + 1, id.length - languageCodeLength - 1, textEncoding);
        }

        @Override
        protected void onPostExecute(final String tagId) {
            if (tagId != null) {
                id = tagId;
                //deduct payment here
            }
        }
    }
}
