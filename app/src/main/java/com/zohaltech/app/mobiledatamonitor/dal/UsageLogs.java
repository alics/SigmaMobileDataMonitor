package com.zohaltech.app.mobiledatamonitor.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.entities.UsageLog;

import java.util.ArrayList;

/**
 * Created by Ali on 7/15/2015.
 */
public class UsageLogs {
    static final String TableName    = "UsageLogs";
    static final String Id           = "Id";
    static final String TrafficBytes = "TrafficBytes";
    static final String LogDateTime  = "LogDateTime";

    static final String CreateTable = "CREATE TABLE " + TableName + " (" +
                                      Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                      TrafficBytes + "BIGINT NOT NULL, " +
                                      LogDateTime + "DATE );";

    static final String DropTable = "Drop Table If Exists " + TableName;

    private static ArrayList<UsageLog> select(String whereClause, String[] selectionArgs) {
        ArrayList<UsageLog> logList = new ArrayList<>();
        DataAccess da = new DataAccess();
        SQLiteDatabase db = da.getReadableDB();
        Cursor cursor = null;

        try {
            String query = "Select * From " + TableName + " " + whereClause;
            cursor = db.rawQuery(query, selectionArgs);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    UsageLog log = new UsageLog(cursor.getInt(cursor.getColumnIndex(Id)),
                                                cursor.getLong(cursor.getColumnIndex(TrafficBytes)),
                                                Helper.getDate(cursor.getString(cursor.getColumnIndex(LogDateTime))));
                    logList.add(log);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            if (db != null && db.isOpen())
                db.close();
        }
        return logList;
    }

    public static ArrayList<UsageLog> select() {
        return select("", null);
    }

    public static long insert(UsageLog usageLog) {
        ContentValues values = new ContentValues();

        values.put(TrafficBytes, usageLog.getTrafficBytes());
        values.put(LogDateTime, usageLog.getLogDateTime().toString());

        DataAccess da = new DataAccess();
        return da.insert(TableName, values);
    }

    public static long update(UsageLog usageLog) {
        ContentValues values = new ContentValues();

        values.put(TrafficBytes, usageLog.getTrafficBytes());
        values.put(LogDateTime, usageLog.getLogDateTime().toString());

        DataAccess da = new DataAccess();
        return da.update(TableName, values, Id + " =? ", new String[]{String.valueOf(usageLog.getId())});
    }

    public static long delete(UsageLog usageLog) {
        DataAccess db = new DataAccess();
        return db.delete(TableName, Id + " =? ", new String[]{String.valueOf(usageLog.getId())});
    }

}