package com.zohaltech.app.mobiledatamonitor.dal;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.entities.MobileOperator;


public class DataAccess extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ZH_DATA_MONITOR";
    public static final int DATABASE_VERSION = 1;

    public DataAccess() {
        super(App.context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        try {
            database.execSQL(MobileOperators.CreateTable);
            database.execSQL(DailyTrafficHistories.CreateTable);
            database.execSQL(UsageLogs.CreateTable);
            database.execSQL(DataPackages.CreateTable);
            database.execSQL(PackageHistories.CreateTable);

            initPackages(database);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        try {
            database.execSQL(PackageHistories.DropTable);
            database.execSQL(DataPackages.DropTable);
            database.execSQL(MobileOperators.DropTable);
            database.execSQL(UsageLogs.DropTable);
            database.execSQL(DailyTrafficHistories.DropTable);
            onCreate(database);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    public SQLiteDatabase getReadableDB() {
        return this.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDB() {
        return this.getWritableDatabase();
    }

    public long insert(String table, ContentValues values) {
        long result = 0;
        try {
            SQLiteDatabase db = this.getWritableDB();
            result = db.insert(table, null, values);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public long update(String table, ContentValues values, String whereClause, String[] selectionArgs) {
        long result = 0;
        try {
            SQLiteDatabase db = this.getWritableDB();
            result = db.update(table, values, whereClause, selectionArgs);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public long delete(String table, String whereClause, String[] selectionArgs) {
        long result = 0;
        try {
            SQLiteDatabase db = this.getWritableDB();
            result = db.delete(table, whereClause, selectionArgs);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void initPackages(SQLiteDatabase database) {
        database.execSQL("INSERT INTO MobileOperators (Name) VALUES('همراه اول')");
        database.execSQL("INSERT INTO MobileOperators (Name) VALUES('رایتل')");
        database.execSQL("INSERT INTO MobileOperators (Name) VALUES('ایرانسل')");
        database.execSQL("INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,UssdCode,Custom) VALUES(1,'بسته آلفا',7,5000,838860800,'*100*12#',0)");
        database.execSQL("INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,UssdCode,Custom) VALUES(1,'بسته آلفا',30,10000,2684354560,'*100*13#',0)");
        database.execSQL("INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,UssdCode,Custom,SecondaryTrafficStartTime,SecondaryTrafficEndTime) VALUES(1,'بسته آلفا',7,1000,0,838860800,'*100*14#',0,'23:00','08:00')");
        database.execSQL("INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',1,1500,104857600,104857600,'02:00','07:00','*100*211#',0)");
        database.execSQL("INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',1,3500,314572800,314572800,'02:00','07:00','*100*212#',0)");
        database.execSQL("INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',1,9000,1073741824,1073741824,'02:00','07:00','*100*213#',0)");
        database.execSQL("INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',1,10000,3221225472,3221225472,'02:00','07:00','*100*214#',0)");
        database.execSQL("INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',7,2500,104857600,104857600,'02:00','07:00','*100*221#',0)");
        database.execSQL("INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',7,5000,314572800,314572800,'02:00','07:00','*100*222#',0)");
        database.execSQL("INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',7,10000,1073741824,1073741824,'02:00','07:00','*100*223#',0)");
        database.execSQL("INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',7,13500,3221225472,3221225472,'02:00','07:00','*100*224#',0)");
        database.execSQL("INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',30,4000,104857600,104857600,'02:00','07:00','*100*231#',0)");
        database.execSQL("INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',30,7000,314572800,314572800,'02:00','07:00','*100*232#',0)");
        database.execSQL("INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',30,13000,1073741824,1073741824,'02:00','07:00','*100*233#',0)");
        database.execSQL("INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',30,17000,3221225472,3221225472,'02:00','07:00','*100*234#',0)");
        database.execSQL("INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۲۰ مگابایت ویژه مشترکین اعتباری',1,5000,20971520,0,null,null,'*555*5*1*1#',0)");
        database.execSQL("INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۵۰ مگابایت ویژه مشترکین اعتباری',1,9000,52428800,0,null,null,'*555*5*1*9#',0)");

    }
}