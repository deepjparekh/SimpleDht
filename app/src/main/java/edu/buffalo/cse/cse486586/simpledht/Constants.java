package edu.buffalo.cse.cse486586.simpledht;

import android.content.ContentValues;

import java.util.TreeMap;

/**
 * Created by deep on 4/1/15.
 */
public class Constants {

    public static final String REMOTE_PORT0 = "11108";
    public static final String REMOTE_PORT1 = "11112";
    public static final String REMOTE_PORT2 = "11116";
    public static final String REMOTE_PORT3= "11120";
    public static final String REMOTE_PORT4 = "11124";
    public static final String TAG="Project 3";
    public static boolean isJoined=false;
    public static final String Join_Request="Join_Request";
    public static final String Join_Confirmation="Join_Confirmation";
    public static final String Insert="Insert";
    public  static final String Query="Query";
    public  static final String QueryStar="QueryStar";
    public static final String Delete="Delete";
    public static final String Predecessor_Successor="Predecessor_Successor";
    public static final String Delimiter="DeepDhtDelimiter";
    public static String successor=null;
    public static String predecessor=null;
    public static String myPort=null;
    public static String smallestPort;
    public static String biggestPort;
    public static TreeMap<String,String> nodesParticipating=new TreeMap<String,String>();
    public static  boolean got_Join_Request;
    public static  boolean got_Join_Confirmation;
    public static  boolean got_insert;
    public  static  boolean got_Query;
    public  static  boolean got_QueryStar;
    public static  boolean got_Delete;
    public static  boolean got_Predecessor_Successor;
    public static boolean insertFinal;
    public static ContentValues cv;

}

