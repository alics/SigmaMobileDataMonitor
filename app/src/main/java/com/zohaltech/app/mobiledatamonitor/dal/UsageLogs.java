package com.zohaltech.app.mobiledatamonitor.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.classes.LicenseManager;
import com.zohaltech.app.mobiledatamonitor.classes.MyRuntimeException;
import com.zohaltech.app.mobiledatamonitor.entities.DailyTrafficHistory;
import com.zohaltech.app.mobiledatamonitor.entities.DataPackage;
import com.zohaltech.app.mobiledatamonitor.entities.PackageHistory;
import com.zohaltech.app.mobiledatamonitor.entities.UsageLog;

import java.util.ArrayList;

public class UsageLogs {

    static final String TableName    = "UsageLogs";
    static final String Id           = "Id";
    static final String TrafficBytes = "TrafficBytes";
    static final String LogDateTime  = "LogDateTime";

    static final String CreateTable = "CREATE TABLE " + TableName + " (" +
                                      Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                      TrafficBytes + " BIGINT NOT NULL, " +
                                      LogDateTime + " CHAR(19) );";

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
                                                cursor.getString(cursor.getColumnIndex(LogDateTime)));
                    logList.add(log);
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
        return logList;
    }

    public static ArrayList<UsageLog> select() {
        return select("", null);
    }

    public static long insert(UsageLog usageLog) {
        String maxUsageLogDate = getMaxDateTime().substring(0, 10);
        String currentDateTime = Helper.getCurrentDateTime();
        String currentDate = Helper.getCurrentDate();
        Log.i("sdj maxUsageLogDate", maxUsageLogDate);
        Log.i("sdj currentDateTime", currentDateTime);
        Log.i("sdj currentDate", currentDate);

        if (!Helper.addDay(-1).equals(DailyTrafficHistories.getMaxDate()) && currentDate.compareTo(maxUsageLogDate) > 0) {
            Log.i("sdj", "integrated");
            integrateSumUsedTrafficPerDay();
        }

        if (usageLog.getTrafficBytes() != 0) {
            ContentValues values = new ContentValues();
            values.put(TrafficBytes, usageLog.getTrafficBytes());
            values.put(LogDateTime, currentDateTime);

            Log.i("sdj", "usage log inserted " + usageLog.getTrafficBytes());
            DataAccess da = new DataAccess();
            return da.insert(TableName, values);
        }

        return 0;
    }

    public static long update(UsageLog usageLog) {
        ContentValues values = new ContentValues();

        values.put(TrafficBytes, usageLog.getTrafficBytes());
        values.put(LogDateTime, usageLog.getLogDateTime());

        DataAccess da = new DataAccess();
        return da.update(TableName, values, Id + " =? ", new String[]{String.valueOf(usageLog.getId())});
    }

    public static long delete(UsageLog usageLog) {
        DataAccess db = new DataAccess();
        return db.delete(TableName, Id + " =? ", new String[]{String.valueOf(usageLog.getId())});
    }

    private static String getMaxDateTime() {
        String maxLogDate = null;
        DataAccess da = new DataAccess();
        SQLiteDatabase db = da.getReadableDB();
        Cursor cursor = null;
        try {
            String query = "SELECT MAX(" + LogDateTime + ") MaxLogDate FROM " + TableName;
            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    maxLogDate = cursor.getString(cursor.getColumnIndex("MaxLogDate"));
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
        return maxLogDate;
    }

    public static void integrateSumUsedTrafficPerDay() {
        LicenseManager.validateLicense();
        DataAccess da = new DataAccess();
        SQLiteDatabase db = da.getReadableDB();
        Cursor cursor = null;
        try {
            String query = "SELECT SUM(TrafficBytes) SumTraffic,SUBSTR(LogDateTime,1,10) date FROM " + TableName +
                           " WHERE SUBSTR(LogDateTime,1,10) > " +
                           " (SELECT MAX(" + DailyTrafficHistories.LogDate + ") FROM "+DailyTrafficHistories.TableName+")" +
                           " AND SUBSTR(LogDateTime,1,10)<>'" + Helper.getCurrentDate() + "'  AND " +
                           " FROM " + DailyTrafficHistories.TableName + ")'" +
                           " GROUP BY SUBSTR(LogDateTime,1,10)";

            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    long sum = cursor.getLong(cursor.getColumnIndex("SumTraffic"));
                    String date = cursor.getString(cursor.getColumnIndex("date"));
                    DailyTrafficHistory history = new DailyTrafficHistory(sum, date);
                    DailyTrafficHistories.insert(history);
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
    }


    public static long getUsedPrimaryTrafficOfPackage(DataPackage dataPackage, PackageHistory history) {
        long usedTraffic = 0;
        DataAccess da = new DataAccess();
        SQLiteDatabase db = da.getReadableDB();
        Cursor cursor = null;
        try {
            String query = generateQueryForPrimaryTraffic(dataPackage, history);
            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    usedTraffic = cursor.getLong(cursor.getColumnIndex("SumTraffic"));
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

        return usedTraffic;
    }

    private static String generateQueryForPrimaryTraffic(DataPackage dataPackage, PackageHistory history) {

        if (dataPackage.getSecondaryTraffic() == null || dataPackage.getSecondaryTraffic() == 0)
            return "SELECT SUM(" + TrafficBytes + ") SumTraffic FROM " + TableName + " WHERE " + LogDateTime + " > '" + history.getStartDateTime() + "'";

        if (history.getSecondaryTrafficEndDateTime() == null || "".equals(history.getSecondaryTrafficEndDateTime())) {
            return "SELECT SUM(" + TrafficBytes + ") SumTraffic FROM " + TableName + " WHERE " + LogDateTime + " > '" + history.getStartDateTime() + "'" +
                   "AND SUBSTR(" + LogDateTime + ",11,9) NOT BETWEEN '" + dataPackage.getSecondaryTrafficStartTime() + "' AND '" + dataPackage.getSecondaryTrafficEndTime() + "'";
        }
        return "SELECT (SELECT SUM(" + TrafficBytes + ") FROM " + TableName + " WHERE " + LogDateTime + " >= '" + history.getStartDateTime() + "' )-\n" +
               "(SELECT SUM(TrafficBytes)  FROM " + TableName + " WHERE (" + LogDateTime + " BETWEEN '" + history.getStartDateTime() + "' AND '" + history.getSecondaryTrafficEndDateTime() + "')\n";
    }

    public static long getUsedSecondaryTrafficOfPackage(DataPackage dataPackage, PackageHistory history) {
        long usedTraffic = 0;
        DataAccess da = new DataAccess();
        SQLiteDatabase db = da.getReadableDB();
        Cursor cursor = null;
        try {
            String query = "SELECT SUM(TrafficBytes) SumTraffic FROM " + TableName +
                           " WHERE " + LogDateTime + " > '" + history.getStartDateTime() + "'" +
                           "AND SUBSTR(" + LogDateTime + ",11,9) BETWEEN '" + dataPackage.getSecondaryTrafficStartTime() + "' AND '" + dataPackage.getSecondaryTrafficEndTime() + "'";

            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    usedTraffic = cursor.getLong(cursor.getColumnIndex("SumTraffic"));
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
        if (usedTraffic >= dataPackage.getSecondaryTraffic()) {
            PackageHistories.terminateDataPackageSecondaryPlan(history);
        }
        return usedTraffic;
    }

    public static void deleteLogs(String date) {
        ArrayList<UsageLog> logs = select("Where " + LogDateTime + " < " + date, null);
        for (int i = 0; i < logs.size(); i++) {
            delete(logs.get(i));
        }
    }
}



