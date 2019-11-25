package com.example.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class  myDatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "myDatabaseFile";
    public static final int VERSION_NUM = 1;
    public final static String TABLE_NAME = "Messages";
    public final static String COL_ID = "_id";
    public final static String COL_SENDORRE = "SENDorRESPONSE";
    public final static String COL_MESSAGE = "MSG_CONTENT";

    public myDatabaseOpenHelper(Activity ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createtable = "create table " + TABLE_NAME + "( "
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_MESSAGE + " TEXT, " + COL_SENDORRE + " TEXT)";
        db.execSQL(createtable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item, boolean SorR){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_MESSAGE, item);
        if(SorR){
            contentValues.put(COL_SENDORRE, 1);
        }else{
            contentValues.put(COL_SENDORRE, 0);
        }

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public int deleteEntry(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereCluse = COL_ID+ "=?";
        int numberOfEntriesDeleted = db.delete(TABLE_NAME, whereCluse, new String[]{Integer.toString(id)});
        return numberOfEntriesDeleted;
    }
}
