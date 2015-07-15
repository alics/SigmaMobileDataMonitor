package com.zohaltech.app.mobiledatamonitor.dal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.entities.DataPackage;
import com.zohaltech.app.mobiledatamonitor.entities.PackageHistory;

import java.util.ArrayList;

/**
 * Created by Ali on 7/15/2015.
 */
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
            StartDateTime + "DATE NOT NULL," +
            EndDateTime + " DATE NOT NULL," +
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
                                                              Helper.getDateTime(cursor.getString(cursor.getColumnIndex(StartDateTime))),
                                                                Helper.getDateTime(cursor.getString(cursor.getColumnIndex(EndDateTime))),
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
}
