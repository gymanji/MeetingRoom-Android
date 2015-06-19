package com.airwatch.meetingroom;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private TextView tvLoadingSettings, tvContinue, tvUserCred;
    private LinearLayout llAuth, llTunnel, llSSO;
    final String username = java.lang.System.getProperty("aw-username");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createTextViewReferences();
        getSetAWUsername();

        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MeetingRooms.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }

    private void getSetAWUsername() {

        Handler mHandler = new Handler();
        int delayMillis = 2000;

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("username", "inside run() method");
                    tvLoadingSettings.setVisibility(View.GONE);
                    if (username != null) tvUserCred.setText(username);
                }
            }, delayMillis);
            magicallyAppear();

    }

    private void magicallyAppear() {

        Handler handler = new Handler();
        int delayMillis0 = 2050;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                LinearLayout[] linearLayoutsArray = {llSSO, llAuth, llTunnel};
                int delayMillis = 400;
                for(int i=0; i<linearLayoutsArray.length; i++){
                    final LinearLayout l = linearLayoutsArray[i];
                    l.postDelayed(new Runnable(){
                        public void run(){
                            l.setVisibility(View.VISIBLE);
                        }
                    }, delayMillis*i);
                }

                int delayMillis2 = 1500;
                tvContinue.postDelayed(new Runnable() {
                    public void run() {
                        tvContinue.setVisibility(View.VISIBLE);
                    }
                }, delayMillis2);

            }
        }, delayMillis0);

    }

    private void createTextViewReferences() {
        tvLoadingSettings = (TextView) findViewById(R.id.tvLoadingSettings);
        llAuth = (LinearLayout) findViewById(R.id.llAuth);
        llSSO = (LinearLayout) findViewById(R.id.llSSO);
        llTunnel = (LinearLayout) findViewById(R.id.llTunnel);
        tvContinue = (TextView) findViewById(R.id.tvContinue);
        tvUserCred = (TextView) findViewById(R.id.tvUserCred);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
}
