package com.airwatch.meetingroom;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private TextView tvContinue;
    LinearLayout llAuth, llTunnel, llSSO;
    LinearLayout[] linearLayoutsArray = {llAuth, llTunnel, llSSO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createTextViewReferences();
        tvContinue.setVisibility(View.VISIBLE);
//        magicallyAppear();

        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MeetingRooms.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }

    private void magicallyAppear() {
        for (final LinearLayout l : linearLayoutsArray) {
            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(200);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    } finally {
                        l.setVisibility(View.VISIBLE);
                    }
                }
            };
            timer.start();
        }

//        Thread timer2 = new Thread() {
//            public void run() {
//                try {
//                    sleep(400);
//                } catch (InterruptedException ie) {
//                    ie.printStackTrace();
//                } finally {
//                    tvContinue.setVisibility(View.VISIBLE);
//                }
//            }
//        };
//        timer2.start();
    }

    private void createTextViewReferences() {
        tvContinue = (TextView) findViewById(R.id.tvContinue);
        llAuth = (LinearLayout) findViewById(R.id.llAuth);
        llSSO = (LinearLayout) findViewById(R.id.llSSO);
        llTunnel = (LinearLayout) findViewById(R.id.llTunnel);
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
