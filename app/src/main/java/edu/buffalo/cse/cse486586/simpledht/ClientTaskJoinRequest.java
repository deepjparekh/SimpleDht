package edu.buffalo.cse.cse486586.simpledht;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by deep on 4/1/15.
 */
class ClientTaskJoinRequest extends AsyncTask<String, Void, Void> {

    //Task which sends join request messages to 5554
    @Override
    protected Void doInBackground(String... msgs) {
        Socket socket = null;
        OutputStream outStream = null;
        DataOutputStream out = null;
        String msgToSend =null;
        try {
            socket = new Socket(InetAddress.getByAddress(new byte[]{10, 0, 2, 2}), Integer.parseInt(Constants.REMOTE_PORT0));
            msgToSend = msgs[0];
            outStream = socket.getOutputStream();
            out = new DataOutputStream(outStream);
            out.writeUTF(msgToSend);
            out.flush();
            socket.close();
            Log.d("ClientTaskJoinRequest", "Sending JoinRequest to AVD 5554 from " + Constants.REMOTE_PORT0);
        } catch (UnknownHostException e) {
            //  Log.e(TAG, "ClientTask UnknownHostException "+remotePort);
        } catch (IOException e) {
            //  Log.e(TAG, "ClientTask socket IOException " +remotePort);
        }

        return null;
    }
}