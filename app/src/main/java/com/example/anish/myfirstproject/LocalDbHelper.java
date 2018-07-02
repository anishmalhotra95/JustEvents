package com.example.neha.myfirstproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Neha on 8/6/2016.
 */
public class LocalDbHelper extends SQLiteOpenHelper
{
    public LocalDbHelper(Context context)
    {
        super(context, "Event.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("Create table Details(User_id integer primary key AUTOINCREMENT,Email text,password text,contact text,IsLoggedIn integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}