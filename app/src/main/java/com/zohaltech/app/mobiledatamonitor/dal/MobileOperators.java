package com.zohaltech.app.mobiledatamonitor.dal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zohaltech.app.mobiledatamonitor.entities.MobileOperator;

import java.util.ArrayList;

/**
 * Created by Ali on 7/15/2015.
 */
public class MobileOperators {
    static final String TableName = "MobileOperators";
    static final String Id        = "Id";
    static final String Name      = "Name";

    static final String CreateTable = "CREATE TABLE " + TableName + " (" +
            Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            Name + " VARCHAR(50)  NOT NULL ) ;";
    static final String DropTable   = "Drop Table If Exists " + TableName;

    private static ArrayList<MobileOperator> select(String whereClause, String[] selectionArgs) {
        ArrayList<MobileOperator> operatorList = new ArrayList<>();
        DataAccess da = new DataAccess();
        SQLiteDatabase db = da.getReadableDB();
        Cursor cursor = null;

        try {
            String query = "Select * From " + TableName + " " + whereClause;
            cursor = db.rawQuery(query, selectionArgs);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    MobileOperator operator = new MobileOperator(cursor.getInt(cursor.getColumnIndex(Id)),
                                                                 cursor.getString(cursor.getColumnIndex(Name))
                    );
                    operatorList.add(operator);
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
        return operatorList;
    }

    public static ArrayList<MobileOperator> select() {
        return select("", null);
    }
}
