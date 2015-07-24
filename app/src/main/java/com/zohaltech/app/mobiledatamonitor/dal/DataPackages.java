package com.zohaltech.app.mobiledatamonitor.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.entities.DataPackage;

import java.util.ArrayList;

public class DataPackages {

    static final String TableName = "DataPackages";
    static final String Id = "Id";
    static final String OperatorId = "OperatorId";
    static final String Title = "Title";
    static final String Period = "Period";
    static final String Price = "Price";
    static final String PrimaryTraffic = "PrimaryTraffic";
    static final String SecondaryTraffic = "SecondaryTraffic";
    static final String SecondaryTrafficStartTime = "SecondaryTrafficStartTime";
    static final String SecondaryTrafficEndTime = "SecondaryTrafficEndTime";
    static final String UssdCode = "UssdCode";
    static final String Custom = "Custom";

    static final String CreateTable = "CREATE TABLE " + TableName + " (" +
            Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            OperatorId + " INTEGER REFERENCES " + MobileOperators.TableName + " (" + MobileOperators.Id + "), " +
            Title + " VARCHAR(500)  NOT NULL ," +
            Period + " INTEGER  NOT NULL ," +
            Price + " INTEGER  NOT NULL ," +
            PrimaryTraffic + " BIGINT  NOT NULL ," +
            SecondaryTraffic + " BIGINT   ," +
            SecondaryTrafficStartTime + " TIME   ," +
            SecondaryTrafficEndTime + " TIME   ," +
            UssdCode + " VARCHAR(50)   ," +
            Custom + " BOOLEAN   )";

    static final String DropTable = "Drop Table If Exists " + TableName;

    private static ArrayList<DataPackage> select(String whereClause, String[] selectionArgs) {
        ArrayList<DataPackage> packageList = new ArrayList<>();
        DataAccess da = new DataAccess();
        SQLiteDatabase db = da.getReadableDB();
        Cursor cursor = null;

        try {
            String query = "Select * From " + TableName + " " + whereClause;
            cursor = db.rawQuery(query, selectionArgs);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    DataPackage dataPackage = new DataPackage(cursor.getInt(cursor.getColumnIndex(Id)),
                            cursor.getInt(cursor.getColumnIndex(OperatorId)),
                            cursor.getString(cursor.getColumnIndex(Title)),
                            cursor.getInt(cursor.getColumnIndex(Period)),
                            cursor.getInt(cursor.getColumnIndex(Price)),
                            cursor.getLong(cursor.getColumnIndex(PrimaryTraffic)),
                            cursor.getLong(cursor.getColumnIndex(SecondaryTraffic)),
                            Helper.getTime(cursor.getString(cursor.getColumnIndex(SecondaryTrafficStartTime))),
                            Helper.getTime(cursor.getString(cursor.getColumnIndex(SecondaryTrafficEndTime))),
                            cursor.getString(cursor.getColumnIndex(UssdCode)),
                            cursor.getInt(cursor.getColumnIndex(Custom)) == 1);
                    packageList.add(dataPackage);
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
        return packageList;
    }

    public static ArrayList<DataPackage> select() {
        return select("", null);
    }

    public static ArrayList<Integer> selectOperatorPeriods(int operatorId) {
        ArrayList<Integer> periodList = new ArrayList<>();

        DataAccess da = new DataAccess();
        SQLiteDatabase db = da.getReadableDB();
        Cursor cursor = null;

        try {
            String query = "Select DISTINCT " + Period + " FROM " + TableName + " WHERE " + OperatorId + " = " + operatorId + " Order By " + Period;
            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    periodList.add(cursor.getInt(cursor.getColumnIndex(Period)));
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
        return periodList;
    }

    public static ArrayList<DataPackage> selectPackagesByOperatorAndPeriod(int operatorId, int period) {
        String whereClause = " WHERE " + OperatorId + " = " + operatorId + " AND " + Period + " = " + period + " AND " + Custom + " = " +0;
        return select(whereClause, null);
    }

    public static ArrayList<DataPackage> selectPackagesById(int packageId) {
        String whereClause = " WHERE " + Id + " = " + packageId ;
        return select(whereClause, null);
    }

    public static long insert(DataPackage dataPackage) {
        ContentValues values = new ContentValues();

        values.put(OperatorId, dataPackage.getOperatorId());
        values.put(Title, dataPackage.getTitle());
        values.put(Period, dataPackage.getPeriod());
        values.put(Price, dataPackage.getPrice());
        values.put(PrimaryTraffic, dataPackage.getPrimaryTraffic());
        values.put(SecondaryTraffic, dataPackage.getSecondaryTraffic());
        values.put(SecondaryTrafficStartTime, dataPackage.getSecondaryTrafficStartTime().toString());
        values.put(SecondaryTrafficEndTime, dataPackage.getSecondaryTrafficEndTime().toString());
        values.put(UssdCode, dataPackage.getUssdCode());
        values.put(Custom, dataPackage.getCustom() ? 1 : 0);

        DataAccess da = new DataAccess();
        return da.insert(TableName, values);
    }

    public static long update(DataPackage dataPackage) {
        ContentValues values = new ContentValues();

        values.put(OperatorId, dataPackage.getOperatorId());
        values.put(Title, dataPackage.getTitle());
        values.put(Period, dataPackage.getPeriod());
        values.put(Price, dataPackage.getPrice());
        values.put(PrimaryTraffic, dataPackage.getPrimaryTraffic());
        values.put(SecondaryTraffic, dataPackage.getSecondaryTraffic());
        values.put(SecondaryTrafficStartTime, dataPackage.getSecondaryTrafficStartTime().toString());
        values.put(SecondaryTrafficEndTime, dataPackage.getSecondaryTrafficEndTime().toString());
        values.put(UssdCode, dataPackage.getUssdCode());
        values.put(Custom, dataPackage.getCustom() ? 1 : 0);

        DataAccess da = new DataAccess();
        return da.update(TableName, values, Id + " =? ", new String[]{String.valueOf(dataPackage.getId())});
    }

    public static long delete(DataPackage dataPackage) {
        DataAccess db = new DataAccess();
        return db.delete(TableName, Id + " =? ", new String[]{String.valueOf(dataPackage.getId())});
    }
}
