package com.zohaltech.app.mobiledatamonitor.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.entities.DailyTrafficHistory;
import com.zohaltech.app.mobiledatamonitor.entities.TrafficMonitor;

import java.util.ArrayList;

public class DailyTrafficHistories {

    static final String TableName         = "DailyTrafficHistories";
    static final String Id                = "Id";
    static final String Traffic           = "Traffic";
    static final String BeginningDateTime = "BeginningDateTime";
    static final String EndingDateTime    = "EndingDateTime";

    static final String CreateTable = "CREATE TABLE " + TableName + " (" +
                                      Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                      Traffic + " BIGINT  NOT NULL," +
                                      BeginningDateTime + " CHAR(19)  NOT NULL, " +
                                      EndingDateTime + " CHAR(19)  NOT NULL );";
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
                                                                          cursor.getString(cursor.getColumnIndex(BeginningDateTime)),
                                                                          cursor.getString(cursor.getColumnIndex(EndingDateTime)));
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

    public static long insert(DailyTrafficHistory trafficHistory) {
        ContentValues values = new ContentValues();

        values.put(Traffic, trafficHistory.getTraffic());
        values.put(BeginningDateTime, trafficHistory.getBeginningDateTime());
        values.put(EndingDateTime, trafficHistory.getEndingDateTime());

        DataAccess da = new DataAccess();
        return da.insert(TableName, values);
    }

    public static long update(DailyTrafficHistory trafficHistory) {
        ContentValues values = new ContentValues();

        values.put(Traffic, trafficHistory.getTraffic());
        values.put(BeginningDateTime, trafficHistory.getBeginningDateTime());
        values.put(EndingDateTime, trafficHistory.getEndingDateTime());

        DataAccess da = new DataAccess();
        return da.update(TableName, values, Id + " =? ", new String[]{String.valueOf(trafficHistory.getId())});
    }

    public static long delete(DailyTrafficHistory trafficHistory) {
        DataAccess db = new DataAccess();
        return db.delete(TableName, Id + " =? ", new String[]{String.valueOf(trafficHistory.getId())});
    }

    public static ArrayList<TrafficMonitor> getMonthlyTraffic() {
        ArrayList<TrafficMonitor> trafficMonitors = new ArrayList<>();
        String currentDate = Helper.getCurrentDate();
        DataAccess da = new DataAccess();
        SQLiteDatabase db = da.getReadableDB();
        Cursor cursor = null;

        try {
            String query = "SELECT  SUM(Traffic) total,SUBSTR(BeginningDateTime,0,11) date FROM (\n" +
                           "SELECT  *  FROM DailyTrafficHistories  \n" +
                           "ORDER BY Id DESC LIMIT 30) t\n" +
                           "GROUP BY SUBSTR(BeginningDateTime,0,11)";

            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    TrafficMonitor trafficMonitor = new TrafficMonitor(cursor.getLong(cursor.getColumnIndex("total")),
                                                                       cursor.getString(cursor.getColumnIndex("date")));
                    trafficMonitors.add(trafficMonitor);
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
        return trafficMonitors;
    }
}
