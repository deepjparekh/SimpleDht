package edu.buffalo.cse.cse486586.simpledht;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeMap;

/**
 * Created by deep on 4/1/15.
 */
class ServerTaskJoinRequest extends AsyncTask<ServerSocket,String, Void> {
    public static TreeMap<String,String> nodesParticipating=new TreeMap<String,String>();
    String Message_Type;
    String splitArray[];
    String ReceiverportNumber;
    String


    @Override
    protected void onProgressUpdate(String... values) {
        try
        {
            splitArray =values[0].split(Constants.Delimiter);
            Message_Type=splitArray[0];
            ReceiverportNumber=splitArray[splitArray.length-1];
            switch(Message_Type)
            {
                case Constants.Join_Request :
                {
                    String x=CommanMethods.genHash(String.valueOf(Integer.parseInt(values[0])/2));
                    if(nodesParticipating.containsKey(x))
                    {
                        return;
                    }
                    nodesParticipating.put(x,String.valueOf(Integer.parseInt(values[0])/2));
                    /*Message m=new Message();
                    m.setJoined(true);*/
                    Log.d(Constants.TAG,"Sending Confirmation from server to "+ReceiverportNumber);
                    new ClientTaskJoinRequest().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,Constants.Join_Confirmation+
                                    Constants.Delimiter+
                                    ReceiverportNumber+
                                    Constants.Delimiter
                                    );

                }
                break;

                case Constants.Join_Confirmation : {

                    Constants.isJoined=true;
                    Log.d(Constants.TAG,"Received Confirmation from server to "+ReceiverportNumber);
                }
                break;

                case Constants.Predecessor_Successor :
                {
                    Constants.predecessor=splitArray[2];
                    Constants.successor=splitArray[3];
                    Log.d(Constants.TAG,"Received Predecessor : "+Constants.predecessor+"" +
                            "And Successor "+Constants.successor
                             +" " +
                            "from server to "+ReceiverportNumber);
                }
                break;

                case Constants.Insert :
                {
                    Constants.predecessor=splitArray[2];
                    Constants.successor=splitArray[3];
                    Log.d(Constants.TAG,"Received Predecessor : "+Constants.predecessor+"" +
                            "And Successor "+Constants.successor
                            +" " +
                            "from server to "+ReceiverportNumber);
                }
                break;



            }


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
