package com.airwatch.meetingroom;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MeetingRooms extends ActionBarActivity  {
//public class MeetingRooms extends Activity implements LocationListener {

    private TextView tvCurrentDate, tvCurrentTime, tvCurrentLocation, tvBESURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_rooms);

        createTextViewReferences();

//        Adding compatibility for pre-KitKat devices to support copying string from MAG address
//        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
        tvBESURL.setKeyListener(null);
        tvBESURL.setFocusable(true);

        getLocationDetails();

        getCurrentDateTime();

        tvBESURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeetingRooms.this, WebViewZ.class);
                MeetingRooms.this.startActivity(intent);
            }
        });

}


    private void getLocationDetails() {
        double currentLongitude, currentLatitude;
        Location getLastLocation;
        boolean gpsEnabled = false;

        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        getLastLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        gpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (getLastLocation == null | gpsEnabled == false) {
            Log.d("GPS", "getLastLocation is null");
            tvCurrentLocation.setText(R.string.gps_undetectable);
        } else {
            currentLatitude = getLastLocation.getLatitude();
            currentLongitude = getLastLocation.getLongitude();

            String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(currentLatitude, currentLongitude, 1);
                if (addresses.size() > 0) {
//                    System.out.println(addresses.get(0).getLocality());
                    cityName = addresses.get(0).getLocality();
                    Log.d("GPS", cityName);
                    tvCurrentLocation.setText(cityName);
                } else {
                    tvCurrentLocation.setText(R.string.gps_undetectable);
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(intent);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }


    private void createTextViewReferences() {
        tvCurrentDate = (TextView) findViewById(R.id.tvCurrentDate);
        tvCurrentTime = (TextView) findViewById(R.id.tvCurrentTime);
        tvCurrentLocation = (TextView) findViewById(R.id.tvCurrentLocation);
        tvBESURL = (TextView) findViewById(R.id.tvBESURL);
    }

    private void getCurrentDateTime() {
        //Date setting
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        tvCurrentDate.setText(currentDate);

        // Gather data for current Hour, Minute, & AM/PM
        SimpleDateFormat hour = new SimpleDateFormat("h");
        SimpleDateFormat minute = new SimpleDateFormat("m");
        SimpleDateFormat ampm = new SimpleDateFormat("a");
        Date time2 = new Date();
        String currentTime_ampm = ampm.format(time2);
        String currentTime_hour = hour.format(time2);
        String currentTime_minute = minute.format(time2);
//        Log.d("Time: ", " Hour-" + currentTime_hour + " Minute-" + currentTime_minute + " AMPM-" + currentTime_ampm);

        //Parse String Minute & Hour value into Integer for calculation
        int minute_int = 0;
        try {
            minute_int = Integer.parseInt(currentTime_minute);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

        int hourStart_int = 0;
        try {
            hourStart_int = Integer.parseInt(currentTime_hour);
            String hour_string = String.valueOf(hourStart_int);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

        //Build custom Array with values depending on meeting time range and define constants
        final int ZERO = 0, THIRTY = 30, SIXTY = 60;
        String zeros = "00 ", thirty = "30", space = " ", colon = ":", hyphen = "-";
        String hourInit = "0";
        String[] timeArray = {hourInit, colon, zeros, space, hyphen, space, hourInit, colon, zeros, space, currentTime_ampm};

        // Swap AM/PM values at 11 - 12 rollover
        if (hourStart_int == 11 && currentTime_ampm == "PM") {
            timeArray[10] = "AM";
        } else timeArray[10] = "PM";

        //Get nearest 30 minute time block based on current time
        if (minute_int > ZERO && minute_int < THIRTY) {
            timeArray[2] = thirty;
            timeArray[8] = zeros;
            timeArray[0] = currentTime_hour;

            //Handle time rollover for non-military syntax
            if (hourStart_int == 12) {
                hourStart_int = 1;
                String myNewFinalTime = String.valueOf(hourStart_int);
                timeArray[6] = myNewFinalTime;
            } else {
                hourStart_int += 1;
                String myNewFinalTime = String.valueOf(hourStart_int);
                timeArray[6] = myNewFinalTime;
            }
        } else {

            timeArray[2] = zeros;
            timeArray[8] = thirty;
            hourStart_int += 1;
            String myNewFinalTime = String.valueOf(hourStart_int);
            timeArray[0] = myNewFinalTime;
            timeArray[6] = myNewFinalTime;
        }

        //Loop through Array to combine new time format to place in TextView
        StringBuilder builder = new StringBuilder();
        for (String s : timeArray) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(s);
        }
        String finalTimeString = builder.toString();
        tvCurrentTime.setText(finalTimeString);
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_meeting_rooms, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        @Override
        protected void onPause () {
            super.onPause();
        }

        @Override
        protected void onResume () {
            super.onResume();
        }
    }
