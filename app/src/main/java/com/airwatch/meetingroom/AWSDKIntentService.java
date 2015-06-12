package com.airwatch.meetingroom;

import android.content.Context;

import com.airwatch.sdk.AirWatchSDKBaseIntentService;
import com.airwatch.sdk.profile.AnchorAppStatus;
import com.airwatch.sdk.profile.ApplicationProfile;

/**
 * Created by zachreed on 6/12/15.
 */
public class AWSDKIntentService extends AirWatchSDKBaseIntentService {
    @Override
    protected void onApplicationProfileReceived(Context context, String s, ApplicationProfile applicationProfile) {

    }

    @Override
    protected void onClearAppDataCommandReceived(Context context) {

    }

    @Override
    protected void onAnchorAppStatusReceived(Context context, AnchorAppStatus anchorAppStatus) {

    }

    @Override
    protected void onAnchorAppUpgrade(Context context, boolean b) {

    }
}
