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
        boolean gpsEnabled;

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
                    System.out.println(addresses.get(0).getLocality());
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

//    private void getCurrentTime() {
//        Calendar calendar = new GregorianCalendar();
//        Date currentDate = new Date();
//        calendar.setTime(currentDate);
//    }



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
