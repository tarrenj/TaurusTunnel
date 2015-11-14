package com.tarustelesys.jake.taurustunnel;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import android.view.KeyEvent;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Initialize variables
    private EditText givenText;
    private String target = "127.0.0.1";

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Instantiate the key listener for the server_address text box
        server_address_listener();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }



    // A key listener to watch the server_text box
    public void server_address_listener() {
        //throw new Error("Things are happening");
        // Get the entered text
        givenText = (EditText) findViewById(R.id.target_textbox);
        Log.d("UI EVENT", "server_address_listener called");
        givenText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Hide the soft keyboard
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(givenText.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                // Do the things
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    target = givenText.getText().toString();
                    return true;
                }
                else {
                    Log.d("UI EVENT", "Server address using wrong actionID");
                    return false;
                }
            }
        });
    }

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
            // We have an active network connection
            // Create another thread for network stuff
            NetworkTask NetTask = new NetworkTask();
            // Execute the ping
            NetTask.execute(target);
        } else {
            // No active network connection
            Log.d("Network connection", "Network connection isn't active");
        }
    }

    // A new class for network stuff thread
    private class NetworkTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... target) {
            Log.d("NETWORK THREAD", "Attempting to ping: " + target[0]);

            // A try catch to handle ping exceptions
            try {
                // Open a process to ping the target
                Process ping = Runtime.getRuntime().exec("ping -c 4 " + target[0]);
                ping.waitFor();
                if (ping.exitValue() == 0) {
                    return true;
                }
                else {
                    return false;
                }
            } catch (Exception e) {
                Log.d("NETWORK THREAD", "PING EXCEPTION: " + e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            TextView result_view = (TextView)findViewById(R.id.body_text);
            result_view.setText("Ping result: " + result);
        }
    }

}