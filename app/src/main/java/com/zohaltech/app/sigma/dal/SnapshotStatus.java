package com.zohaltech.app.sigma.dal;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zohaltech.app.sigma.classes.MyRuntimeException;

import java.util.ArrayList;

public class SnapshotStatus {
    public static int Running = 1;
    public static int Stopped = 2;

    static final String TableName            = "SnapshotStatus";
    static final String Id                   = "Id";
    static final String Status               = "Status";
    static final String InitializationStatus = "InitializationStatus";


    static final String CreateTable = "CREATE TABLE " + TableName + " (" +
                                      Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                      Status + " INTEGER NOT NULL," +
                                      InitializationStatus + " INTEGER NOT NULL );";

    static final String DropTable = "Drop Table If Exists " + TableName;


    private static ArrayList<SnapshotStatus> select(String whereClause, String[] selectionArgs) {
        ArrayList<SnapshotStatus> statuses = new ArrayList<>();
        DataAccess da = new DataAccess();
        SQLiteDatabase db = da.getReadableDB();
        Cursor cursor = null;

        try {
            String query = "Select * From " + TableName + " " + whereClause;
            cursor = db.rawQuery(query, selectionArgs);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    SnapshotStatus status = new SnapshotStatus(cursor.getInt(cursor.getColumnIndex(Id)),
                                                               cursor.getInt(cursor.getColumnIndex(Status)),
                                                               cursor.getInt(cursor.getColumnIndex(InitializationStatus)));
                    statuses.add(status);
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
        return statuses;
    }

    public static ArrayList<SnapshotStatus> select() {
        return select("", null);
    }

    public static SnapshotStatus getCurrentSnapshotStatus() {
        ArrayList<SnapshotStatus> statuses = select("", null);
        int count = statuses.size();

        return (count == 0) ? null : statuses.get(0);
    }

    public static long update(SnapshotStatus status) {
        ContentValues values = new ContentValues();
        values.put(Status, status.getStatus());
        values.put(InitializationStatus,status.getInitializationStatus());

        DataAccess dataAccess = new DataAccess();
        return dataAccess.update(TableName, values, Id + " = ? ", new String[]{String.valueOf(status.getId())});
    }

    private int id;
    private int status;
    private int initializationStatus;

    public enum InitStatus{FIRST_SNAPSHOT,BEFORE_FIRST_BOOT,NORMAL}

    public SnapshotStatus(int id, int status, int initialized) {
        this(status, initialized);
        this.id = id;
    }

    public SnapshotStatus(int status, int initialized) {
        setStatus(status);
        setInitializationStatus(initialized);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getInitializationStatus() {
        return initializationStatus;
    }

    public void setInitializationStatus(int initializationStatus) {
        this.initializationStatus = initializationStatus;
    }
}
