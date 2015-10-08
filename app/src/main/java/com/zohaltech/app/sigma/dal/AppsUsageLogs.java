package com.zohaltech.app.sigma.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zohaltech.app.sigma.classes.MyRuntimeException;
import com.zohaltech.app.sigma.entities.AppsUsageLog;
import com.zohaltech.app.sigma.entities.MobileOperator;

import java.util.ArrayList;

public class AppsUsageLogs {
    static final String TableName = "AppsUsageLogs";
    static final String Id = "Id";
    static final String AppId = "AppId";
    static final String TrafficBytes = "TrafficBytes";
    static final String LogDateTime = "LogDateTime";


    static final String CreateTable = "CREATE TABLE " + TableName + " (" +
            Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            AppId + " INTEGER REFERENCES " + Applications.TableName + " (" + DataPackages.Id + "), " +
            TrafficBytes + " CHAR(19)  ," +
            LogDateTime + " CHAR(19)   )";

    static final String DropTable = "Drop Table If Exists " + TableName;


    private static ArrayList<AppsUsageLog> select(String whereClause, String[] selectionArgs) {
        ArrayList<AppsUsageLog> usageLogs = new ArrayList<>();
        DataAccess da = new DataAccess();
        SQLiteDatabase db = da.getReadableDB();
        Cursor cursor = null;

        try {
            String query = "Select * From " + TableName + " " + whereClause;
            cursor = db.rawQuery(query, selectionArgs);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    AppsUsageLog log = new AppsUsageLog(cursor.getLong(cursor.getColumnIndex(Id)),
                            cursor.getInt(cursor.getColumnIndex(AppId)),
                            cursor.getLong(cursor.getColumnIndex(TrafficBytes)),
                            cursor.getString(cursor.getColumnIndex(LogDateTime)));

                    usageLogs.add(log);
                } while (cursor.moveToNext());
            }
        } catch (MyRuntimeException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            if (db != null && db.isOpen())
                db.close();
        }
        return usageLogs;
    }

    public static ArrayList<AppsUsageLog> select() {
        return select("", null);
    }

    public static long insert(AppsUsageLog log) {
        ContentValues values = new ContentValues();
        values.put(AppId, log.getAppId());
        values.put(TrafficBytes, log.getTrafficBytes());
        values.put(LogDateTime, log.getLogDateTime());

        DataAccess da = new DataAccess();
        return da.insert(TableName, values);
    }

    public static long update(AppsUsageLog log) {
        ContentValues values = new ContentValues();
        values.put(AppId, log.getAppId());
        values.put(TrafficBytes, log.getTrafficBytes());
        values.put(LogDateTime, log.getLogDateTime());

        DataAccess da = new DataAccess();
        return da.update(TableName, values, Id + " =? ", new String[]{String.valueOf(log.getId())});
    }

    public static long delete(MobileOperator operator) {
        DataAccess db = new DataAccess();
        return db.delete(TableName, Id + " =? ", new String[]{String.valueOf(operator.getId())});
    }

    public static AppsUsageLog getAppById(long id) {
        String whereClause = " WHERE " + Id + " = " + id;
        ArrayList<AppsUsageLog> logs = new ArrayList<>();
        logs = select(whereClause, null);
        int count = logs.size();

        return (count == 0) ? null : logs.get(count - 1);
    }
}
