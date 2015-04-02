package edu.buffalo.cse.cse486586.simpledht;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.telephony.TelephonyManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.TreeMap;

public class SimpleDhtActivity extends Activity {

    //Devices Porth Number
    public static String myPort;
    private HashMap<String, Boolean> mapOfNodes = new HashMap<String, Boolean>();
    Thread t = new Thread();
    private Message message;
    // Map to find which node to redirect the insert command to


    ClientTaskJoinRequest ctsk = new ClientTaskJoinRequest();

    //Porth Numbers of Node 5554 AVD


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(Constants.TAG, "OnCreate Method Called for " + myPort);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_dht_main);

        TextView tv = (TextView) findViewById(R.id.textView1);
        tv.setMovementMethod(new ScrollingMovementMethod());
        findViewById(R.id.button3).setOnClickListener(
                new OnTestClickListener(tv, getContentResolver()));

        //Generating port numbers and multicasing it to 5554 for node joins

        TelephonyManager tel = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String portStr = tel.getLine1Number().substring(tel.getLine1Number().length() - 4);
        myPort = String.valueOf((Integer.parseInt(portStr) * 2));

        //Starting serverTaskJoinRequest only if it is AVD 5554
        if (myPort.equals(Constants.REMOTE_PORT0)) {
            Log.d(Constants.TAG, "Starting server for " + myPort);
            try {
                ServerSocket serverSocket = new ServerSocket(10000);
                new ServerTaskJoinRequest().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, serverSocket);

            } catch (Exception e) {
                Log.e(Constants.TAG, e.toString());
            }
        }

        //message.setFlagIsJoin(true);
        //message.setPortNumber(myPort);

        while (!Constants.isJoined) {
            ctsk.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, Constants.Join_Request +
                    Constants.Delimiter +
                    Constants.REMOTE_PORT0 +
                    Constants.Delimiter +
                    myPort);
            try {
                t.wait(10000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_simple_dht_main, menu);
        return true;
    }

}
