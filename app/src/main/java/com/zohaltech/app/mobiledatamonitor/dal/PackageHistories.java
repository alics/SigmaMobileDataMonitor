package com.zohaltech.app.mobiledatamonitor.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.entities.PackageHistory;

import java.util.ArrayList;

public class PackageHistories {
    static final String TableName     = "PackageHistories";
    static final String Id            = "Id";
    static final String DataPackageId = "DataPackageId";
    static final String StartDateTime = "StartDateTime";
    static final String EndDateTime   = "EndDateTime";
    static final String SimId         = "SimId";
    static final String Active        = "Active";


    static final String CreateTable = "CREATE TABLE " + TableName + " (" +
            Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            DataPackageId + " INTEGER REFERENCES " + DataPackages.TableName + " (" + DataPackages.Id + "), " +
            StartDateTime + " CHAR(19) NOT NULL," +
            EndDateTime + " CHAR(19) NOT NULL," +
            SimId + " INTEGER  NOT NULL ," +
            Active + " BOOLEAN   );";

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
                                                                       cursor.getString(cursor.getColumnIndex(SimId)),
                                                                       cursor.getInt(cursor.getColumnIndex(Active)) == 1);
                    packageList.add(packageHistory);
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

    public static ArrayList<PackageHistory> select() {
        return select("", null);
    }

    public static long insert(PackageHistory packageHistory) {
        ContentValues values = new ContentValues();

        values.put(DataPackageId, packageHistory.getDataPackageId());
        values.put(StartDateTime, packageHistory.getStartDateTime().toString());
        values.put(EndDateTime, packageHistory.getEndDateTime().toString());
        values.put(SimId, packageHistory.getSimId());
        values.put(Active, packageHistory.getActive() ? 1 : 0);

        DataAccess da = new DataAccess();
        return da.insert(TableName, values);
    }

    public static long update(PackageHistory packageHistory) {
        ContentValues values = new ContentValues();

        values.put(DataPackageId, packageHistory.getDataPackageId());
        values.put(StartDateTime, packageHistory.getStartDateTime().toString());
        values.put(EndDateTime, packageHistory.getEndDateTime().toString());
        values.put(SimId, packageHistory.getSimId());
        values.put(Active, packageHistory.getActive() ? 1 : 0);

        DataAccess da = new DataAccess();
        return da.update(TableName, values, Id + " =? ", new String[]{String.valueOf(packageHistory.getId())});
    }

    public static long delete(PackageHistory packageHistory) {
        DataAccess db = new DataAccess();
        return db.delete(TableName, Id + " =? ", new String[]{String.valueOf(packageHistory.getId())});
    }
}
