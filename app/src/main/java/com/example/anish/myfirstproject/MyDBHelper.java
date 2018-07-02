package com.example.anish.myfirstproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

public static String NAME="name";
    public MyDBHelper(Context context)
    {
        super(context, "MyFirstDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
          db.execSQL("create table userdetails (id integer primary key AUTOINCREMENT, "+NAME+" text, address text, city text, email text, password text)");
//        db.execSQL("create table userdetails (id integer primary key AUTOINCREMENT, name text, address text, city text, email text, password text)");
//        db.execSQL("create table userdetails (id integer primary key AUTOINCREMENT, name text, address text, city text, email text, password text)");
//        db.execSQL("create table userdetails (id integer primary key AUTOINCREMENT, name text, address text, city text, email text, password text)");
//        db.execSQL("create table userdetails (id integer primary key AUTOINCREMENT, name text, address text, city text, email text, password text)");
//        db.execSQL("create table userdetails (id integer primary key AUTOINCREMENT, name text, address text, city text, email text, password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
