package com.zohaltech.app.sigma.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zohaltech.app.sigma.classes.MyRuntimeException;
import com.zohaltech.app.sigma.entities.AppsTrafficMonitor;
import com.zohaltech.app.sigma.entities.AppsUsageLog;
import com.zohaltech.app.sigma.fragments.AppsTrafficReportFragment;
import com.zohaltech.app.sigma.fragments.AppsTrafficReportFragment.RestrictionType;

import java.util.ArrayList;

public class AppsUsageLogs {
    static final String TableName        = "AppsUsageLogs";
    static final String Id               = "Id";
    static final String AppId            = "AppId";
    static final String TrafficBytes     = "TrafficBytes";
    static final String TrafficBytesWifi = "TrafficBytesWifi";
    static final String LogDateTime      = "LogDateTime";


    static final String CreateTable = "CREATE TABLE " + TableName + " (" +
                                      Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                      AppId + " INTEGER NOT NULL , " +
                                      TrafficBytes + " BIGINT NOT NULL  ," +
                                      TrafficBytesWifi + " BIGINT NOT NULL  ," +
                                      LogDateTime + " CHAR(19)   );";

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
                                                        cursor.getLong(cursor.getColumnIndex(TrafficBytesWifi)),
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
        values.put(TrafficBytesWifi, log.getTrafficBytesWifi());
        values.put(LogDateTime, log.getLogDateTime());

        DataAccess da = new DataAccess();
        return da.insert(TableName, values);
    }

    public static long update(AppsUsageLog log) {
        ContentValues values = new ContentValues();
        values.put(AppId, log.getAppId());
        values.put(TrafficBytes, log.getTrafficBytes());
        values.put(TrafficBytesWifi, log.getTrafficBytesWifi());
        values.put(LogDateTime, log.getLogDateTime());

        DataAccess da = new DataAccess();
        return da.update(TableName, values, Id + " =? ", new String[]{String.valueOf(log.getId())});
    }

    public static long delete(AppsUsageLog log) {
        DataAccess db = new DataAccess();
        return db.delete(TableName, Id + " =? ", new String[]{String.valueOf(log.getId())});
    }

    public static AppsUsageLog getAppById(long id) {
        String whereClause = " WHERE " + Id + " = " + id;
        ArrayList<AppsUsageLog> logs = new ArrayList<>();
        logs = select(whereClause, null);
        int count = logs.size();

        return (count == 0) ? null : logs.get(count - 1);
    }

    public static ArrayList<AppsTrafficMonitor> getAppsTrafficReport(AppsTrafficReportFragment.ReportType reportType, String date, AppsTrafficReportFragment.RestrictionType restrictionType) {
        ArrayList<AppsTrafficMonitor> appsTrafficMonitors = new ArrayList<>();
        DataAccess da = new DataAccess();
        SQLiteDatabase db = da.getReadableDB();
        Cursor cursor = null;

        try {
            //String query = "SELECT " + Applications.AppName + " appName, " +
            //               Applications.PackageName + " packageName, " +
            //               "sum(" + TrafficBytes + ") mobile, " +
            //               "sum(" + TrafficBytesWifi + ") wifi " +
            //               "FROM " + TableName + " log " +
            //               "INNER JOIN " + Applications.TableName + " app " +
            //               "ON app.Id=log.AppId " +
            //               "GROUP BY app.AppName " +
            //               "ORDER BY mobile DESC, wifi DESC ";

            if (reportType == AppsTrafficReportFragment.ReportType.BOTH) {
                String query = " SELECT " + AppId + " , " +
                               " sum(" + TrafficBytes + ") mobile, " +
                               " sum(" + TrafficBytesWifi + ") wifi " +
                               " FROM " + TableName + " log " +
                               " WHERE SUBSTR(" + LogDateTime + ", 12, 5)" +
                               (restrictionType == RestrictionType.FROM ? ">=" : "=") + "'" + date + "'" +
                               " GROUP BY " + AppId +
                               " ORDER BY (mobile+wifi) DESC ";

                cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        AppsTrafficMonitor trafficMonitor = new AppsTrafficMonitor(cursor.getInt(cursor.getColumnIndex(AppId)),
                                                                                   0L,
                                                                                   0L,
                                                                                   cursor.getLong(cursor.getColumnIndex("mobile")) +
                                                                                   cursor.getLong(cursor.getColumnIndex("wifi")));
                        appsTrafficMonitors.add(trafficMonitor);
                    } while (cursor.moveToNext());
                }
            } else if (reportType == AppsTrafficReportFragment.ReportType.WIFI) {
                String query = " SELECT " + AppId + " , " +
                               " sum(" + TrafficBytes + ") mobile, " +
                               " sum(" + TrafficBytesWifi + ") wifi " +
                               " FROM " + TableName + " log " +
                               " WHERE SUBSTR(" + LogDateTime + ", 12, 5)" +
                               (restrictionType == RestrictionType.FROM ? ">=" : "=") + "'" + date + "'" +
                               " GROUP BY " + AppId +
                               " ORDER BY  wifi DESC ";

                cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        AppsTrafficMonitor trafficMonitor = new AppsTrafficMonitor(cursor.getInt(cursor.getColumnIndex(AppId)),
                                                                                   0L,
                                                                                   cursor.getLong(cursor.getColumnIndex("wifi")),
                                                                                   0L);
                        appsTrafficMonitors.add(trafficMonitor);
                    } while (cursor.moveToNext());
                }
            } else if (reportType == AppsTrafficReportFragment.ReportType.DATA) {
                String query = " SELECT " + AppId + " , " +
                               " sum(" + TrafficBytes + ") mobile, " +
                               " sum(" + TrafficBytesWifi + ") wifi " +
                               " FROM " + TableName + " log " +
                               " WHERE SUBSTR(" + LogDateTime + ", 12, 5)" +
                               (restrictionType == RestrictionType.FROM ? ">=" : "=") + "'" + date + "'" +
                               " GROUP BY " + AppId +
                               " ORDER BY mobile DESC ";

                cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        AppsTrafficMonitor trafficMonitor = new AppsTrafficMonitor(cursor.getInt(cursor.getColumnIndex(AppId)),
                                                                                   cursor.getLong(cursor.getColumnIndex("mobile")),
                                                                                   0L,
                                                                                   0L);
                        appsTrafficMonitors.add(trafficMonitor);
                    } while (cursor.moveToNext());
                }
            }
        } catch (MyRuntimeException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            if (db != null && db.isOpen())
                db.close();
        }
        return appsTrafficMonitors;
    }
}
