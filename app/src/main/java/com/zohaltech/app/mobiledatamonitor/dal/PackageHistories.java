package com.zohaltech.app.mobiledatamonitor.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.classes.MyRuntimeException;
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
    static final String Active                      = "Active";
    static final String Reserved                    = "Reserved";


    static final String CreateTable = "CREATE TABLE " + TableName + " (" +
                                      Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                      DataPackageId + " INTEGER REFERENCES " + DataPackages.TableName + " (" + DataPackages.Id + "), " +
                                      StartDateTime + " CHAR(19)  ," +
                                      EndDateTime + " CHAR(19)  ," +
                                      SecondaryTrafficEndDateTime + " CHAR(19) ," +
                                      SimSerial + " VARCHAR(50) NOT NULL ," +
                                      Reserved + " BOOLEAN NOT NULL ," +
                                      Active + " BOOLEAN NOT NULL );";

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
                                                                       cursor.getInt(cursor.getColumnIndex(Active)) == 1,
                                                                       cursor.getInt(cursor.getColumnIndex(Reserved)) == 1);
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
        return select("", null);
    }

    public static long insert(PackageHistory packageHistory) {
        ContentValues values = new ContentValues();

        values.put(DataPackageId, packageHistory.getDataPackageId());
        values.put(StartDateTime, packageHistory.getStartDateTime());
        values.put(EndDateTime, packageHistory.getEndDateTime());
        values.put(SimSerial, packageHistory.getSimSerial());
        values.put(Active, packageHistory.getActive() ? 1 : 0);
        values.put(Reserved, packageHistory.getReserved() ? 1 : 0);

        DataAccess da = new DataAccess();
        return da.insert(TableName, values);
    }

    public static long update(PackageHistory packageHistory) {
        ContentValues values = new ContentValues();

        values.put(DataPackageId, packageHistory.getDataPackageId());
        values.put(StartDateTime, packageHistory.getStartDateTime());
        values.put(EndDateTime, packageHistory.getEndDateTime());
        values.put(SimSerial, packageHistory.getSimSerial());
        values.put(Active, packageHistory.getActive() ? 1 : 0);
        values.put(Reserved, packageHistory.getReserved() ? 1 : 0);

        DataAccess da = new DataAccess();
        return da.update(TableName, values, Id + " =? ", new String[]{String.valueOf(packageHistory.getId())});
    }

    public static long delete(PackageHistory packageHistory) {
        DataAccess db = new DataAccess();
        return db.delete(TableName, Id + " =? ", new String[]{String.valueOf(packageHistory.getId())});
    }

    public static PackageHistory getActivePackage() {
        String whereClause = " WHERE " + Active + " = " + 1;
        ArrayList<PackageHistory> packageHistories = new ArrayList<>();
        packageHistories = select(whereClause, null);
        int count = packageHistories.size();

        return (count == 0) ? null : packageHistories.get(count - 1);
    }

    public static PackageHistory getReservedPackage() {
        String whereClause = " WHERE " + Reserved + " = " + 1;
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

    public static long getPackageUsedTraffic(int packageId) {
        PackageHistory packageHistory = getPackageById(packageId);
        if (packageHistory == null || !packageHistory.getActive())
            return 0;


        return 1;
    }



    public static void terminateDataPackage(PackageHistory packageHistory) {
        packageHistory.setActive(false);
        packageHistory.setEndDateTime(Helper.getCurrentDateTime());

        if (packageHistory.getSecondaryTrafficEndDateTime() == null ||
            "".equals(packageHistory.getSecondaryTrafficEndDateTime())) {
            packageHistory.setSecondaryTrafficEndDateTime(Helper.getCurrentDateTime());
        }
        update(packageHistory);
    }

    public static void terminateDataPackageSecondaryPlan(PackageHistory packageHistory) {
        packageHistory.setSecondaryTrafficEndDateTime(Helper.getCurrentDateTime());
        update(packageHistory);
    }
}

