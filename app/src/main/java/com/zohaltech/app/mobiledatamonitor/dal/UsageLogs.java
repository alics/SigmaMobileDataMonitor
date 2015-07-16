package com.zohaltech.app.mobiledatamonitor.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.entities.UsageLog;

import java.util.ArrayList;
import java.util.Date;

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

    private static Date getMaxDateOfUsageLog() {
        Date maxLogDate = null;
        DataAccess da = new DataAccess();
        SQLiteDatabase db = da.getReadableDB();
        Cursor cursor = null;
        try {
            String query = "SELECT MAX(" + LogDateTime + ") MaxLogDate FROM UsageLogs";
            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    maxLogDate = Helper.getDate(cursor.getString(cursor.getColumnIndex("MaxLogDate")));
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
        return maxLogDate;
    }

    private static long getSumUsedTrafficUsageInDate(Date date) {
        long sum = 0;
        DataAccess da = new DataAccess();
        SQLiteDatabase db = da.getReadableDB();
        Cursor cursor = null;
        try {
            String query = "SELECT SUM(TrafficBytes) SumTrrafic FROM UsageLogs WHERE  SUBSTR(LogDateTime,1,10) = '" + date + "'";
            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    sum = cursor.getLong(cursor.getColumnIndex("SumTrrafic"));
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
        return sum;
    }

    public static ArrayList<UsageLog> select() {
        return select("", null);
    }

    public static long insert(UsageLog usageLog) {
        ContentValues values = new ContentValues();
        Date maxDate = getMaxDateOfUsageLog();
        Date currentDate = Helper.getDate(usageLog.getLogDateTime().toString());

        if (currentDate.compareTo(maxDate) > 0) {
            long sumBytes=getSumUsedTrafficUsageInDate(maxDate);
            DailyTrafficHistory dailyTrafficHistory=new DailyTrafficHistory(sumBytes,currentDate);
            DailyTrafficHistories.insert(dailyTrafficHistory);
        }

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