package com.zohaltech.app.mobiledatamonitor.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.classes.MyRuntimeException;
import com.zohaltech.app.mobiledatamonitor.classes.SolarCalendar;
import com.zohaltech.app.mobiledatamonitor.entities.PackageHistory;

import java.util.ArrayList;

public class PackageHistories {
    static final String TableName                   = "PackageHistories";
    static final String Id                          = "Id";
    static final String DataPackageId               = "DataPackageId";
    static final String StartDateTime               = "StartDateTime";
    static final String EndDateTime                 = "EndDateTime";
    static final String SecondaryTrafficEndDateTime = "SecondaryTrafficEndDateTime";
    static final String SimSerial                   = "SimSerial";
    static final String Status                      = "Status";

    static final String CreateTable = "CREATE TABLE " + TableName + " (" +
                                      Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                      DataPackageId + " INTEGER REFERENCES " + DataPackages.TableName + " (" + DataPackages.Id + "), " +
                                      StartDateTime + " CHAR(19)  ," +
                                      EndDateTime + " CHAR(19)  ," +
                                      SecondaryTrafficEndDateTime + " " +
                                      "CHAR(19) ," +
                                      SimSerial + " VARCHAR(50) ," +
                                      Status + " INTEGER NOT NULL );";

    static final String DropTable = "Drop Table If Exists " + TableName;


    private static ArrayList<PackageHistory> select(String whereClause, String[] selectionArgs) {
        ArrayList<PackageHistory> packageList = new ArrayList<>();
        DataAccess da = new DataAccess();
        SQLiteDatabase db = da.getReadableDB();
        Cursor cursor = null;

        try {
            String query = "Select * From " + TableName + " " + whereClause;
            cursor = db.rawQuery(query, selectionArgs);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    PackageHistory packageHistory = new PackageHistory(cursor.getInt(cursor.getColumnIndex(Id)),
                                                                       cursor.getInt(cursor.getColumnIndex(DataPackageId)),
                                                                       cursor.getString(cursor.getColumnIndex(StartDateTime)),
                                                                       cursor.getString(cursor.getColumnIndex(EndDateTime)),
                                                                       cursor.getString(cursor.getColumnIndex(SecondaryTrafficEndDateTime)),
                                                                       cursor.getString(cursor.getColumnIndex(SimSerial)),
                                                                       cursor.getInt(cursor.getColumnIndex(Status)));

                    packageList.add(packageHistory);
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
        return packageList;
    }

    public static ArrayList<PackageHistory> select() {
        return select(" ORDER BY " + Id + " DESC", null);
    }

    public static long insert(PackageHistory packageHistory) {
        ContentValues values = new ContentValues();

        values.put(DataPackageId, packageHistory.getDataPackageId());
        values.put(StartDateTime, packageHistory.getStartDateTime());
        values.put(EndDateTime, packageHistory.getEndDateTime());
        values.put(SimSerial, packageHistory.getSimSerial());
        values.put(Status, packageHistory.getStatus());

        DataAccess da = new DataAccess();
        return da.insert(TableName, values);
    }

    public static long update(PackageHistory packageHistory) {
        ContentValues values = new ContentValues();

        values.put(DataPackageId, packageHistory.getDataPackageId());
        values.put(StartDateTime, packageHistory.getStartDateTime());
        values.put(EndDateTime, packageHistory.getEndDateTime());
        values.put(SimSerial, packageHistory.getSimSerial());
        values.put(Status, packageHistory.getStatus());

        DataAccess da = new DataAccess();
        return da.update(TableName, values, Id + " =? ", new String[]{String.valueOf(packageHistory.getId())});
    }

    public static long delete(PackageHistory packageHistory) {
        DataAccess db = new DataAccess();
        return db.delete(TableName, Id + " =? ", new String[]{String.valueOf(packageHistory.getId())});
    }

    public static PackageHistory getActivePackage() {
        String whereClause = " WHERE " + Status + " = " + PackageHistory.StatusEnum.ACTIVE.ordinal();
        ArrayList<PackageHistory> packageHistories = new ArrayList<>();
        packageHistories = select(whereClause, null);
        int count = packageHistories.size();

        return (count == 0) ? null : packageHistories.get(count - 1);
    }

    public static PackageHistory getReservedPackage() {
        String whereClause = " WHERE " + Status + " = " +  PackageHistory.StatusEnum.RESERVED.ordinal();
        ArrayList<PackageHistory> packageHistories = new ArrayList<>();
        packageHistories = select(whereClause, null);
        int count = packageHistories.size();

        return (count == 0) ? null : packageHistories.get(count - 1);
    }

    public static PackageHistory getPackageById(int id) {
        String whereClause = " WHERE " + Id + " = " + id;
        ArrayList<PackageHistory> packageHistories = new ArrayList<>();
        packageHistories = select(whereClause, null);
        int count = packageHistories.size();

        return (count == 0) ? null : packageHistories.get(count - 1);
    }

    public static void terminateDataPackage(PackageHistory packageHistory,PackageHistory.StatusEnum terminationStatus) {
        packageHistory.setStatus(terminationStatus.ordinal());
        packageHistory.setEndDateTime(Helper.getCurrentDateTime());

        if (packageHistory.getSecondaryTrafficEndDateTime() == null ||
            "".equals(packageHistory.getSecondaryTrafficEndDateTime())) {
            packageHistory.setSecondaryTrafficEndDateTime(Helper.getCurrentDateTime());
        }
        update(packageHistory);
    }

    public static void terminateAll(PackageHistory.StatusEnum terminationStatus) {
        for (PackageHistory packageHistory : PackageHistories.select()) {
            packageHistory.setStatus(terminationStatus.ordinal());
            packageHistory.setEndDateTime(Helper.getCurrentDateTime());
            PackageHistories.update(packageHistory);
        }
    }

    public static void terminateDataPackageSecondaryPlan(PackageHistory packageHistory) {
        packageHistory.setSecondaryTrafficEndDateTime(Helper.getCurrentDateTime());
        update(packageHistory);
    }

    public static void deletedReservedPackages() {
        ArrayList<PackageHistory> histories = select("Where " + Status + " = "+ PackageHistory.StatusEnum.RESERVED.ordinal(), null);
        for (int i = 0; i < histories.size(); i++) {
            delete(histories.get(i));
        }
    }

    public static void finishPackageProcess(PackageHistory history,PackageHistory.StatusEnum terminationStatus) {
        terminateDataPackage(history,terminationStatus);
        String yesterdayDateStr = SolarCalendar.getPastDateString(-1);
        UsageLogs.integrateSumUsedTrafficUsagePerHourInDate(yesterdayDateStr);
        UsageLogs.deleteLogs(yesterdayDateStr);
        PackageHistory reservedPackage = getReservedPackage();
        if (reservedPackage != null) {
            reservedPackage.setStatus(terminationStatus.ordinal());
            reservedPackage.setStartDateTime(Helper.getCurrentDateTime());
            update(reservedPackage);
        }
    }
}

