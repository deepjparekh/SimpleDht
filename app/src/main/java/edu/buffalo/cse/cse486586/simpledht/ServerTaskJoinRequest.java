package edu.buffalo.cse.cse486586.simpledht;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

/**
 * Created by deep on 4/1/15.
 */
class ServerTaskJoinRequest extends AsyncTask<ServerSocket,String, Void> {
    public static TreeMap<String,String> nodesParticipating=new TreeMap<String,String>();
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
                    String x=CommanMethods.genHash(String.valueOf(Integer.parseInt(SenderPortNumber)/2));
                    Log.d(Constants.TAG,"Calculated HashValue for "+SenderPortNumber +" as " +x);
                    if(nodesParticipating.containsKey(x))
                    {
                        return;
                    }
                    nodesParticipating.put(x,String.valueOf(Integer.parseInt(SenderPortNumber)));

                    Log.d(Constants.TAG,"Sending Confirmation from server to "+ReceiverportNumber);
                    new ClientTaskJoinRequest().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,Constants.Join_Confirmation+
                                    Constants.Delimiter+
                                    ReceiverportNumber
                                    );

                }
                break;

                case Constants.Join_Confirmation : {

                    Constants.isJoined=true;
                    Log.d(Constants.TAG,"Received Confirmation from server to "+ReceiverportNumber);

                    if(Constants.myPort.equals(Constants.REMOTE_PORT0))
                    {
                        Log.d(Constants.TAG,"Starting the process of sending successors and predecessors");
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {

                            public void run() {

                                //here you can start your Activity B.
                               c=nodesParticipating.keySet();
                               for(String key:c)
                               {
                                   keys.add(key);
                               }
                                c.clear();

                                for(int pos=0;pos<keys.size();pos++)
                                {
                                    if(pos==0)
                                    {
                                        new ClientTaskJoinRequest().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, Constants.Predecessor_Successor +
                                                Constants.Delimiter +
                                                nodesParticipating.get(keys.get(pos)) +
                                                Constants.Delimiter +
                                                nodesParticipating.get(keys.get(keys.size()-1)) +
                                                Constants.Delimiter +
                                                nodesParticipating.get(keys.get((pos + 1) % keys.size())) +
                                                Constants.Delimiter +
                                                Constants.myPort);
                                        Log.d(Constants.TAG,"Predecessor : "+nodesParticipating.get(keys.get(keys.size()-1))+
                                                "Successor : "+nodesParticipating.get(keys.get((pos + 1) % keys.size()))+
                                        "for Node "+nodesParticipating.get(keys.get(pos)));
                                    }
                                    else {
                                        new ClientTaskJoinRequest().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, Constants.Predecessor_Successor +
                                                Constants.Delimiter +
                                                nodesParticipating.get(keys.get(pos)) +
                                                Constants.Delimiter +
                                                nodesParticipating.get(keys.get((pos - 1) % keys.size())) +
                                                Constants.Delimiter +
                                                nodesParticipating.get(keys.get((pos + 1) % keys.size())) +
                                                Constants.Delimiter +
                                                Constants.myPort);

                                        Log.d(Constants.TAG,"Predecessor : "+nodesParticipating.get(keys.get((pos - 1) % keys.size()))+
                                                "Successor : "+nodesParticipating.get(keys.get((pos + 1) % keys.size()))+
                                                "for Node "+nodesParticipating.get(keys.get(pos)));
                                    }



                                }

                            }

                        }, 5000);

                    }




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
