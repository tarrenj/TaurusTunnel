package com.tarustelesys.jake.taurustunnel;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    public void ping_server(View view) {
//        //String server = "192.168.1.1";
//        String server = "127.0.0.1";
//        int port = 1;
//        try {
//            //Socket sock = new Socket(server, port);
//            Socket sock = new Socket("192.168.1.1", 1);
//            sock.getOutputStream().write((byte) '\n');
//            int ch = sock.getInputStream().read();
//            if (ch == '\n') {
//                // Server responded
//            } else {
//                // No response
//            }
//            sock.close();
//        } catch (UnknownHostException e) {
//            // Unknown host
//            try {
//                Thread.sleep(5);
//            } catch (InterruptedException ex) {
//            }
//        } catch (IOException e) {
//            // No I/O
//            try {
//                Thread.sleep(5);
//            } catch (InterruptedException ex) {
//            }
//        }
//    }

    public void ping_server(View view) {
        // Make sure we have a network connection
        ConnectivityManager ConMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Active network connection
            Log.d("Network connection","Network connection is active");
        } else {
            // No active network connection
            Log.d("Network connection", "Network connection isn't active");
        }
    }

    public void test_button(View view) {
        Log.d("Button test", "Button seems to be working fine...");
    }
}