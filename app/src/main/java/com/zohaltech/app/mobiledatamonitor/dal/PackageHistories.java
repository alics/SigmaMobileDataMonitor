package com.zohaltech.app.mobiledatamonitor.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.classes.MyRuntimeException;
import com.zohaltech.app.mobiledatamonitor.entities.PackageHistory;
import com.zohaltech.app.mobiledatamonitor.entities.Setting;

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
        ArrayList<PackageHistory> packageHistories = select(whereClause, null);
        int count = packageHistories.size();

        return (count == 0) ? null : packageHistories.get(count - 1);
    }

    public static PackageHistory getReservedPackage() {
        String whereClause = " WHERE " + Status + " = " + PackageHistory.StatusEnum.RESERVED.ordinal();
        ArrayList<PackageHistory> packageHistories = select(whereClause, null);
        int count = packageHistories.size();

        return (count == 0) ? null : packageHistories.get(count - 1);
    }

    public static void terminateDataPackage(PackageHistory packageHistory, PackageHistory.StatusEnum terminationStatus) {
        packageHistory.setStatus(terminationStatus.ordinal());
        packageHistory.setEndDateTime(Helper.getCurrentDateTime());

        if (packageHistory.getSecondaryTrafficEndDateTime() == null ||
            "".equals(packageHistory.getSecondaryTrafficEndDateTime())) {
            packageHistory.setSecondaryTrafficEndDateTime(Helper.getCurrentDateTime());
        }
        update(packageHistory);

        Setting setting = Settings.getCurrentSettings();
        setting.setTrafficAlarmHasShown(false);
        setting.setSecondaryTrafficAlarmHasShown(false);
        setting.setLeftDaysAlarmHasShown(false);
        Settings.update(setting);
    }

    //public static void terminateAll(PackageHistory.StatusEnum terminationStatus) {
    //    for (PackageHistory packageHistory : PackageHistories.select()) {
    //        terminateDataPackage(packageHistory,terminationStatus);
    //        packageHistory.setStatus(terminationStatus.ordinal());
    //        packageHistory.setEndDateTime(Helper.getCurrentDateTime());
    //        PackageHistories.update(packageHistory);
    //    }
    //}

    public static void terminateDataPackageSecondaryPlan(PackageHistory packageHistory) {
        packageHistory.setSecondaryTrafficEndDateTime(Helper.getCurrentDateTime());
        update(packageHistory);
    }

    public static void deletedReservedPackages() {
        ArrayList<PackageHistory> histories = select("Where " + Status + " = " + PackageHistory.StatusEnum.RESERVED.ordinal(), null);
        for (int i = 0; i < histories.size(); i++) {
            delete(histories.get(i));
        }
    }

    public static void finishPackageProcess(PackageHistory history, PackageHistory.StatusEnum terminationStatus) {
        history.setStatus(terminationStatus.ordinal());
        history.setEndDateTime(Helper.getCurrentDateTime());
        if (history.getSecondaryTrafficEndDateTime() == null || "".equals(history.getSecondaryTrafficEndDateTime())) {
            history.setSecondaryTrafficEndDateTime(Helper.getCurrentDateTime());
        }
        update(history);

        String yesterdayDateStr = Helper.addDay(-1);
        UsageLogs.deleteLogs(yesterdayDateStr);
        PackageHistory reservedPackage = getReservedPackage();
        Setting setting = Settings.getCurrentSettings();
        if (reservedPackage != null) {
            reservedPackage.setStatus(terminationStatus.ordinal());
            reservedPackage.setStartDateTime(Helper.getCurrentDateTime());
            update(reservedPackage);
            setting.setAlarmType(setting.getAlarmTypeRes());
            setting.setLeftDaysAlarm(setting.getLeftDaysAlarmRes());
            setting.setRemindedByteAlarm(setting.getRemindedByteAlarmRes());
            setting.setShowAlarmAfterTerminate(setting.getShowAlarmAfterTerminateRes());
        }
        setting.setTrafficAlarmHasShown(false);
        setting.setSecondaryTrafficAlarmHasShown(false);
        setting.setLeftDaysAlarmHasShown(false);
        Settings.update(setting);
    }
}

