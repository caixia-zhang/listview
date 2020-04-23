package com.example.listview.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "note.db";
    public static final String TABLE_NAME = "notes";
    public static final String TEXTNAME = "textname";
    public static final String DATE = "date";
    public static final String PROGRESS = "progress";
    public static final  String DELDATE = "deldata";
    public static final String PHOTO = "photo";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    //blob是aqlite数据库中用于存储二进制对象的数据类型
    @Override
    public void onCreate(SQLiteDatabase db) {


        String sql= "create table " +  TABLE_NAME  + " (" + TEXTNAME + " varchar(100)," + DATE + " varchar(50)," + DELDATE + " varcher(50)," + PROGRESS + " varchar(20),"+ " " + PHOTO + " blob(20));";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
