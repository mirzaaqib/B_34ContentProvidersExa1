package com.pallefire.b_34contentprovidersexa1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    //c. declare variable myhelper & initialise with increate method
   private MyHelper myHelper;

    //d. Here we prepapre the URI TO Table Mapping Logic

    private static UriMatcher uriMatcher=new UriMatcher(-1); //if url is not correct -1 means shows invalid url like 404 error
    static {
        uriMatcher.addURI("com.techpalle.b34","student",1);

        //end of mapping logic,,here 1 shows first table
    }


    //a.create one inner helper class

    public class MyHelper extends SQLiteOpenHelper{

        public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            //b. create table for student
            sqLiteDatabase.execSQL("create table student(_id integer primary key,sname text, ssub text);");

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
      return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        //e.here we are
        switch (uriMatcher.match(uri)){

            case 1:
                SQLiteDatabase sqLiteDatabase=myHelper.getWritableDatabase();
                sqLiteDatabase.insert("student",null,values);
                //means client is asking to insert into student table
                break;
            default:
                //means invalid table
                break;
        }
        return null;


    }

    @Override
    public boolean onCreate() {

        // same like the constructor in sqlite database operation & return true
        myHelper=new MyHelper(getContext(),"techpalle.db",null,1);
        // TODO: Implement this to initialize your content provider on startup.
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        //f.. this is the main logic for the content providers
        switch (uriMatcher.match(uri)){
            case 1:
                //means client is requesting to read from student table
                Cursor c=null;
                SQLiteDatabase sqLiteDatabase=myHelper.getWritableDatabase();
                c=sqLiteDatabase.query("student",null,null,null,null,null,null);
                return c;
            default:
                //means invalid table
                break;
        }
        return null;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;

    }
}
