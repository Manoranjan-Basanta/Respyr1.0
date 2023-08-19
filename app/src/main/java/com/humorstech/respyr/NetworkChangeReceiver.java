package com.humorstech.respyr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkChangeReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            // There is a network connection
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // Connected to a Wi-Fi network
                Log.d(TAG, "Connected to Wi-Fi network");
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // Connected to a mobile network
                Log.d(TAG, "Connected to mobile network");
            }
        } else {
            // No network connection
            Log.d(TAG, "No network connection");
        }
    }
}