package edu.buffalo.cse.cse486586.simpledht;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import android.net.Uri;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

/**
 * Created by deep on 4/1/15.
 */
class ServerTaskJoinRequest extends AsyncTask<ServerSocket,String, Void> {

    Collection<String> c;
    ArrayList<String> keys=new ArrayList<String >();
    String Message_Type;
    String splitArray[];
    String ReceiverportNumber;
    String SenderPortNumber;

    @Override
    protected void onProgressUpdate(String... values) {
        try
        {

            splitArray =values[0].split(Constants.Delimiter);
            Message_Type=splitArray[0];
            ReceiverportNumber=splitArray[splitArray.length-1];
            SenderPortNumber=splitArray[1];

            Log.d(Constants.TAG,"Received Message "+values[0]+" at "+ ReceiverportNumber);


            switch(Message_Type)
            {
                case Constants.Join_Request :
                {
                    String x=CommanMethods.genHash(String.valueOf(Integer.parseInt(ReceiverportNumber)/2));
                    Log.d(Constants.TAG,"Calculated HashValue for "+ReceiverportNumber +" as " +x);

                    Constants.nodesParticipating.put(x,String.valueOf(Integer.parseInt(ReceiverportNumber)));
                    Log.d(Constants.TAG,"Size of Map "+Constants.nodesParticipating.size());
                   /* Log.d(Constants.TAG,"Sending Confirmation from server to "+ReceiverportNumber);
                    new ClientTaskJoinRequest().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,Constants.Join_Confirmation+
                                    Constants.Delimiter+
                                    ReceiverportNumber
                                    );*/

                }
                break;

                case Constants.Join_Confirmation : {

//                    Constants.isJoined=true;
//                    Log.d(Constants.TAG,"Received Confirmation from server to "+ReceiverportNumber);
//
//                    if(Constants.myPort.equals(Constants.REMOTE_PORT0))
//                    {
//
//                        Timer timer = new Timer();
//                        timer.schedule(new TimerTask() {
//
//                            public void run() {
//                                Log.d(Constants.TAG,"Starting the process of sending successors and predecessors");
//                                //here you can start your Activity B.
//                               c=nodesParticipating.keySet();
//                               for(String key:c)
//                               {
//                                   keys.add(key);
//                               }
//                                c.clear();
//
//                                for(int pos=0;pos<keys.size();pos++)
//                                {
//                                    if(pos==0)
//                                    {
//                                        new ClientTaskJoinRequest().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, Constants.Predecessor_Successor +
//                                                Constants.Delimiter +
//                                                nodesParticipating.get(keys.get(pos)) +
//                                                Constants.Delimiter +
//                                                nodesParticipating.get(keys.get(keys.size()-1)) +
//                                                Constants.Delimiter +
//                                                nodesParticipating.get(keys.get((pos + 1) % keys.size())) +
//                                                Constants.Delimiter +
//                                                Constants.myPort);
//                                        Log.d(Constants.TAG,"Predecessor : "+nodesParticipating.get(keys.get(keys.size()-1))+
//                                                "Successor : "+nodesParticipating.get(keys.get((pos + 1) % keys.size()))+
//                                        "for Node "+nodesParticipating.get(keys.get(pos)));
//                                    }
//                                    else {
//                                        new ClientTaskJoinRequest().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, Constants.Predecessor_Successor +
//                                                Constants.Delimiter +
//                                                nodesParticipating.get(keys.get(pos)) +
//                                                Constants.Delimiter +
//                                                nodesParticipating.get(keys.get((pos - 1) % keys.size())) +
//                                                Constants.Delimiter +
//                                                nodesParticipating.get(keys.get((pos + 1) % keys.size())) +
//                                                Constants.Delimiter +
//                                                Constants.myPort);
//
//                                        Log.d(Constants.TAG,"Predecessor : "+nodesParticipating.get(keys.get((pos - 1) % keys.size()))+
//                                                "Successor : "+nodesParticipating.get(keys.get((pos + 1) % keys.size()))+
//                                                "for Node "+nodesParticipating.get(keys.get(pos)));
//                                    }
//
//
//
//                                }
//
//                            }
//
//                        }, 3000);
//
//                    }




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
                    String key=splitArray[2];
                    String value=splitArray[3];
                    if(splitArray[4].equals("true"))
                    {
                        Constants.insertFinal=true;
                    }
                    ContentValues cv = new ContentValues();
                    cv.put("key",key);
                    cv.put("value", value);
                    Constants.cv=cv;
                   Uri Uri = CommanMethods.buildUri("content", "edu.buffalo.cse.cse486586.simpledht.provider");
                    /*Log.d(Constants.TAG,"Received Predecessor : "+Constants.predecessor+"" +
                            "And Successor "+Constants.successor
                            +" " +
                            "from server to "+ReceiverportNumber);*/
                    SimpleDhtProvider sht=new SimpleDhtProvider();
                    sht.insert(Uri,cv);
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
