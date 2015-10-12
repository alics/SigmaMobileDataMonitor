package com.zohaltech.app.sigma.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zohaltech.app.sigma.classes.MyRuntimeException;
import com.zohaltech.app.sigma.entities.Application;
import com.zohaltech.app.sigma.entities.MobileOperator;

import java.util.ArrayList;

public class Applications {
    static final String TableName   = "Applications";
    static final String Id          = "Id";
    static final String Uid         = "Uid";
    static final String AppName     = "AppName";
    static final String PackageName = "PackageName";

    static final String CreateTable = "CREATE TABLE " + TableName + " (" +
                                      Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                      Uid + " INTEGER  ," +
                                      AppName + " VARCHAR(30)  ," +
                                      PackageName + " VARCHAR(50)   );";

    private static ArrayList<Application> select(String whereClause, String[] selectionArgs) {
        ArrayList<Application> applications = new ArrayList<>();
        DataAccess da = new DataAccess();
        SQLiteDatabase db = da.getReadableDB();
        Cursor cursor = null;

        try {
            String query = "Select * From " + TableName + " " + whereClause;
            cursor = db.rawQuery(query, selectionArgs);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Application app = new Application(cursor.getInt(cursor.getColumnIndex(Id)),
                                                      cursor.getInt(cursor.getColumnIndex(Uid)),
                                                      cursor.getString(cursor.getColumnIndex(AppName)),
                                                      cursor.getString(cursor.getColumnIndex(PackageName)));
                    applications.add(app);
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
        return applications;
    }

    public static ArrayList<Application> select() {
        return select("", null);
    }

    public static long insert(Application application) {
        ContentValues values = new ContentValues();
        values.put(Uid, application.getUid());
        values.put(AppName, application.getAppName());
        values.put(PackageName, application.getPackageName());

        DataAccess da = new DataAccess();
        return da.insert(TableName, values);
    }

    public static long update(Application application) {
        ContentValues values = new ContentValues();
        values.put(Uid, application.getUid());
        values.put(AppName, application.getAppName());
        values.put(PackageName, application.getPackageName());

        DataAccess da = new DataAccess();
        return da.update(TableName, values, Id + " =? ", new String[]{String.valueOf(application.getId())});
    }

    public static long delete(MobileOperator operator) {
        DataAccess db = new DataAccess();
        return db.delete(TableName, Id + " =? ", new String[]{String.valueOf(operator.getId())});
    }

    public static Application getAppById(long id) {
        String whereClause = " WHERE " + Id + " = " + id;
        ArrayList<Application> applications = new ArrayList<>();
        applications = select(whereClause, null);
        int count = applications.size();

        return (count == 0) ? null : applications.get(count - 1);
    }

    public static Application getAppByUid(int uid) {
        String whereClause = " WHERE " + Uid + " = " + uid;
        ArrayList<Application> applications = select(whereClause, null);
        int count = applications.size();

        return (count == 0) ? null : applications.get(count - 1);
    }
}
