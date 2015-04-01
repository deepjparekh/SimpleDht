package edu.buffalo.cse.cse486586.simpledht;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class SimpleDhtProvider extends ContentProvider {

    public static SQLiteDatabase db;
    public static final String DATABASE_NAME = "Messages";
    public static final String TABLE_NAME = "msg";
    public static final int DATABASE_VERSION = 1;
    public static String Predecessor ;
    public static String Successor;
    public static final String KEY = "key";
    public static final String VALUE="value";

    public static final String CREATE_DB_TABLE =
            " CREATE TABLE " + TABLE_NAME +
                    "("
                    + KEY + " text not null, "
                    + VALUE + " text not null"
                    + ");";


    private  static class DatabaseHelper extends SQLiteOpenHelper {

        /*
           * Referred  http://www.vogella.com/tutorials/AndroidSQLite/article.html for understanding the framework and designing of the database class.
        */
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)

        {
            Log.e("create db :", CREATE_DB_TABLE);
            db.execSQL(CREATE_DB_TABLE);
            Log.e("DB Size ",String.valueOf(db.getMaximumSize()));
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
            onCreate(db);
        }
    }

    public Uri buildUri(String scheme, String authority) {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.authority(authority);
        uriBuilder.scheme(scheme);
        return uriBuilder.build();
    }



    public String[] getPredecessorAndSuccesor()
    {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO Auto-generated method stub

        /*Set<String> keys=values.keySet();
        TreeSet tsetKeys=new TreeSet(SimpleDhtActivity.nodesParticipating.keySet());
        Iterator<String> iter=keys.iterator();
        Iterator<String> iter1=tsetKeys.iterator();
        String insertedKey=iter.next();
        String deep=null;
        try {
            deep = genHash(insertedKey);
            }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        tsetKeys.add(deep);*/



        // vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv


        /*String keyValue=null;
        String ownId=null;
        if(values==null)
        {
            Log.d(Constants.TAG,"Trying to insert Null Values");
        }
        try
        {
             keyValue=CommanMethods.genHash(values.keySet().iterator().next());
             ownId=CommanMethods.genHash(SimpleDhtActivity.myPort);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

       // String fetchFromLeader[]=getPredecessorAndSuccesor();
      //  Successor=fetchFromLeader[0];
      //  Predecessor=fetchFromLeader[1];
        if(Successor==null && Predecessor==null)
        {
            Log.d(Constants.TAG,"Trying to insert with a single Node");
            db.insert(TABLE_NAME,null,values);


        }
        else if(ownId.compareTo(keyValue)<0)
        {
            //send to predecessor
        }
        else
        {
            if(Predecessor.compareTo(keyValue)>0)
            {
                //send to predecessor
                Log.d(Constants.TAG,"Trying to insert in predecessor node");

            }
            else if(Successor.compareTo(keyValue)>0)
            {
                //send to successor
                Log.d(Constants.TAG,"Trying to insert in successor node");
            }

            else
            {
                Log.d(Constants.TAG,"Trying to insert in same node");
                db.insert(TABLE_NAME,null,values);
            }
        }*/
        Log.d(Constants.TAG,"Trying to insert in same node key : " +values.keySet().iterator().next().toString() +" And value "+values.valueSet().iterator().next());
        db.insert(TABLE_NAME,null,values);
        return uri;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();


        qb.setTables(TABLE_NAME);
        Cursor c=null;
        try{
            if(selection.equals("*"))
            {
                Log.d(Constants.TAG,"Query for haha " +selection);
                db.rawQuery("select * from "+qb.getTables(),null);
                return db.rawQuery("select * from "+qb.getTables(),null);
            }
            else if(selection.equals("@"))
            {
                Log.d(Constants.TAG,"Query for baaha " +selection);
                db.rawQuery("select * from "+qb.getTables(),null);
                return db.rawQuery("select * from "+qb.getTables(),null);
            }
            else {
                Log.d(Constants.TAG,"Query for nana" +selection);
                c = qb.query(db, null, KEY + "= '" + selection + "'", null,
                        null, null, null);
                Log.d(Constants.TAG,"Returning " +c.toString());
                return c;
            }
        }
        catch (Exception e)
        {
            Log.e(e.toString(),qb.getTables());
        }
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }


}
