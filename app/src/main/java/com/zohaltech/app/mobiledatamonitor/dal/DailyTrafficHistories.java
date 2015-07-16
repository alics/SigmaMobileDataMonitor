package com.zohaltech.app.mobiledatamonitor.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.entities.DailyTrafficHistory;

import java.util.ArrayList;

/**
 * Created by Ali on 7/15/2015.
 */
public class DailyTrafficHistories {

    static final String TableName = "DailyTrafficHistories";
    static final String Id        = "Id";
    static final String Traffic   = "Traffic";
    static final String UsageDate = "UsageDate";

    static final String CreateTable = "CREATE TABLE " + TableName + " (" +
            Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            Traffic + " BIGINT  NOT NULL," +
            UsageDate + " Date  NOT NULL );";
    static final String DropTable   = "Drop Table If Exists " + TableName;


    private static ArrayList<DailyTrafficHistory> select(String whereClause, String[] selectionArgs) {
        ArrayList<DailyTrafficHistory> histories = new ArrayList<>();
        DataAccess da = new DataAccess();
        SQLiteDatabase db = da.getReadableDB();
        Cursor cursor = null;

        try {
            String query = "Select * From " + TableName + " " + whereClause;
            cursor = db.rawQuery(query, selectionArgs);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    DailyTrafficHistory history = new DailyTrafficHistory(cursor.getInt(cursor.getColumnIndex(Id)),
                                                                          cursor.getLong(cursor.getColumnIndex(Traffic)),
                                                                          Helper.getDate(cursor.getString(cursor.getColumnIndex(UsageDate))));
                    histories.add(history);
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
        return histories;
    }

    public static ArrayList<DailyTrafficHistory> select() {
        return select("", null);
    }

    public static long insert(DailyTrafficHistory trafficHistory)
    {
        ContentValues values = new ContentValues();

        values.put(Traffic, trafficHistory.getTraffic());
        values.put(UsageDate, trafficHistory.getUsageDate().toString());

        DataAccess da = new DataAccess();
        return da.insert(TableName, values);
    }

    public static long update(DailyTrafficHistory trafficHistory)
    {
        ContentValues values = new ContentValues();

        values.put(Traffic, trafficHistory.getTraffic());
        values.put(UsageDate, trafficHistory.getUsageDate().toString());

        DataAccess da = new DataAccess();
        return da.update(TableName, values, Id + " =? ", new String[]{String.valueOf(trafficHistory.getId())});
    }

    public static long delete(DailyTrafficHistory trafficHistory)
    {
        DataAccess db = new DataAccess();
        return db.delete(TableName, Id + " =? ", new String[]{String.valueOf(trafficHistory.getId())});
    }
}
