package com.tarustelesys.jake.taurustunnel;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // A simple method to test to make sure my button is working..
    public void test_button(View view) {
        Log.d("Button test", "Button seems to be working fine...");
    }

    public void ping_server(View view) {
        // Make sure we have a network connection
        ConnectivityManager ConMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Active network connection
            Log.d("Network connection", "Network connection is active");
            // Create another thread for network stuff
            //new NetworkTask().execute(R.string.localhost);
            new NetworkTask().execute("127.0.0.1");

        } else {
            // No active network connection
            Log.d("Network connection", "Network connection isn't active");
        }
    }

    // A new class for network stuff thread
    private class NetworkTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... target) {
            Log.d("Network Task", "Attempting to ping: " + target[0]);
            return null;
        }
    }

}