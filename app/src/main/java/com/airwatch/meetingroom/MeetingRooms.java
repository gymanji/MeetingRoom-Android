package com.airwatch.meetingroom;

import android.app.Activity;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

//public class MeetingRooms extends Activity {
public class MeetingRooms extends Activity implements LocationListener {

    private TextView tvCurrentDate, tvCurrentTime, tvCurrentLocation;
    private LocationManager locationManager;
    private String provider, cityName;
    private double latitude, longitude;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_rooms);

        createTextViewReferences();

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
//            Log.d("GPS", "is enabled");
//            tvCurrentLocation.setText(R.string.gps_enabled);
            onLocationChanged(location);
            Log.d("GPS", "Latitude:" + latitude);
            Log.d("GPS", "Longitude:" + longitude);

        } else {
//            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(intent);
            tvCurrentLocation.setText(R.string.gps_disabled);
        }
    }

    public void onLocationChanged(Location location) {
        latitude = (location.getLatitude());
        longitude = (location.getLongitude());
        getCityName();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void createTextViewReferences() {
        tvCurrentDate = (TextView) findViewById(R.id.tvCurrentDate);
        tvCurrentTime = (TextView) findViewById(R.id.tvCurrentTime);
        tvCurrentLocation = (TextView) findViewById(R.id.tvCurrentLocation);
    }

//    private void getCurrentTime() {
//        Calendar calendar = new GregorianCalendar();
//        Date currentDate = new Date();
//        calendar.setTime(currentDate);
//    }

    private void getCityName() {
        cityName = null;
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());

        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(latitude, longitude, 1);

            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
                tvCurrentLocation.setText(cityName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            locationManager.removeUpdates(this);
        }

        @Override
        protected void onResume () {
            super.onResume();
            locationManager.requestLocationUpdates(provider, 400, 1, this);
        }
    }
