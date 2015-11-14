package com.tarustelesys.jake.taurustunnel;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.view.View.OnKeyListener;

import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    // Initialize variables
    private EditText givenText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Instantiate the key listener for the server_address text box
        server_address_listener();
    }

    // A key listener to watch the server_text box
    public void server_address_listener() {
        // Get the entered text
        givenText = (EditText) findViewById(R.id.server_address);
        givenText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d("UI EVENT", "Server address: " + givenText.getText());
                return true;
            }
        });
    }

//    public void server_text_listener() {
//        // Get the entered text
//        givenText = (EditText) findViewById(R.id.server_address);
//
//        // Create a key listener  Fucking callbacks!!!
//        givenText.setOnKeyListener(new OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                // If enter
//                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
//                    Log.d("UI EVENT", "Server address: " + givenText.getText());
//                    return true;
//                }
//                return false;
//            }
//        });
//    }

    // A simple method to test to make sure my button is working..
    public void test_button(View view) {
        Log.d("UI EVENT", "Button seems to be working fine...");
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

            // A try catch to handle ping exceptions
            try {
                // Open a process to ping the target
                Process p = Runtime.getRuntime().exec("ping " + target[0]);
                // Read it's output
                BufferedReader inputStream = new BufferedReader(
                        new InputStreamReader(p.getInputStream()));

                // Put the output in log file, will later be parsed...
                String output = "";
                for (int i=0; i<5; i++) {
                    output = inputStream.readLine();
                    Log.d("Network Task", "Ping output: " + output);
                }

                // Kill the process
                p.destroy();

            } catch (Exception e) {
                Log.d("Network Task", "PING EXCEPTION: " + e);
            }

            // Why do I need this, even though this is a Void method?
            return null;
        }
    }

}