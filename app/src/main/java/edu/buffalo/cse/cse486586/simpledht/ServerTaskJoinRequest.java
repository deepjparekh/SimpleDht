package edu.buffalo.cse.cse486586.simpledht;

import android.os.AsyncTask;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeMap;

/**
 * Created by deep on 4/1/15.
 */
class ServerTaskJoinRequest extends AsyncTask<ServerSocket, String, Void> {
    public static TreeMap<String,Boolean> nodesParticipating=new TreeMap<String,Boolean>();
    @Override
    protected void onProgressUpdate(String... values) {
        ;           try
        {
            String x=CommanMethods.genHash(String.valueOf(Integer.parseInt(values[0])/2));
            nodesParticipating.put(x,true);
        }

        catch (Exception e)
        {

        }
    }

    @Override
    protected Void doInBackground(ServerSocket... sockets) {
        ServerSocket serverSocket = sockets[0];
        Socket server = null;

        try {
            while (true) {
                server = serverSocket.accept();
                DataInputStream in = new DataInputStream(server.getInputStream());
                String incoming = in.readUTF();
                publishProgress(incoming);

            }


        } catch (Exception e) {
        } finally {
            try {

                server.close();

            } catch (Exception e) {
            }
        }
        return null;
    }
}
