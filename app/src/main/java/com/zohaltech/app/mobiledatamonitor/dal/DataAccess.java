package com.zohaltech.app.mobiledatamonitor.dal;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zohaltech.app.mobiledatamonitor.classes.App;


public class DataAccess extends SQLiteOpenHelper {
    public static final String DATABASE_NAME    = "ZH_DATA_MONITOR";
    public static final int    DATABASE_VERSION = 1;

    public DataAccess() {
        super(App.context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        try {
            database.execSQL(ContactTypes.CreateTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        try {
            database.execSQL(ContactTypes.DropTable);
            onCreate(database);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    public SQLiteDatabase getReadableDB() {
        return this.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDB() {
        return this.getWritableDatabase();
    }

    public long insert(String table, ContentValues values) {
        long result = 0;
        try {
            SQLiteDatabase db = this.getWritableDB();
            result = db.insert(table, null, values);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public long update(String table, ContentValues values, String whereClause, String[] selectionArgs) {
        long result = 0;
        try {
            SQLiteDatabase db = this.getWritableDB();
            result = db.update(table, values, whereClause, selectionArgs);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public long delete(String table, String whereClause, String[] selectionArgs) {
        long result = 0;
        try {
            SQLiteDatabase db = this.getWritableDB();
            result = db.delete(table, whereClause, selectionArgs);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}