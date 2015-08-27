package com.zohaltech.app.mobiledatamonitor.dal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zohaltech.app.mobiledatamonitor.BuildConfig;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.CsvReader;
import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.classes.LicenseManager;
import com.zohaltech.app.mobiledatamonitor.classes.LicenseStatus;
import com.zohaltech.app.mobiledatamonitor.classes.MyRuntimeException;
import com.zohaltech.app.mobiledatamonitor.classes.TrafficUnitsUtil;

import java.io.InputStreamReader;


public class DataAccess extends SQLiteOpenHelper {

    public static final String DATABASE_NAME    = "ZT_DATA_MONITOR";
    public static final int    DATABASE_VERSION = 51;

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
            database.execSQL(Settings.CreateTable);

            insertDataFromAsset(database, MobileOperators.TableName, "data/operators.csv", ';');
            insertDataFromAsset(database, DataPackages.TableName, "data/packages.csv", ';');

            //prevent first daily history usage to be null
            ContentValues usageLogValues = new ContentValues();
            usageLogValues.put(UsageLogs.TrafficBytes, 0);
            usageLogValues.put(UsageLogs.LogDateTime, Helper.getCurrentDateTime());
            database.insert(UsageLogs.TableName, null, usageLogValues);

            ContentValues trafficHistoryValues = new ContentValues();
            for (int i = 0; i < 29; i++) {
                trafficHistoryValues.put(DailyTrafficHistories.LogDate, Helper.addDay(i - 29));
                trafficHistoryValues.put(DailyTrafficHistories.Traffic, 0);
                database.insert(DailyTrafficHistories.TableName, null, trafficHistoryValues);
            }

            // initial settings
            ContentValues settingsValues = new ContentValues();
            settingsValues.put(Settings.DataConnected, 1);
            settingsValues.put(Settings.DailyTraffic, 0);
            settingsValues.put(Settings.ShowAlarmAfterTerminate, 1);
            settingsValues.put(Settings.ShowAlarmAfterTerminateRes, 1);
            settingsValues.put(Settings.AlarmType, 1);
            settingsValues.put(Settings.PercentTrafficAlarm, 20 * TrafficUnitsUtil.power(1024, 2));
            settingsValues.put(Settings.LeftDaysAlarm, 1);
            settingsValues.put(Settings.AlarmTypeRes, 2);
            settingsValues.put(Settings.PercentTrafficAlarmRes, 20 * TrafficUnitsUtil.power(1024, 2));
            settingsValues.put(Settings.LeftDaysAlarmRes, 1);
            settingsValues.put(Settings.ShowNotification, 1);
            settingsValues.put(Settings.ShowNotificationWhenDataIsOn, 0);
            settingsValues.put(Settings.ShowNotificationInLockScreen, 1);
            settingsValues.put(Settings.TrafficAlarmHasShown, 0);
            settingsValues.put(Settings.SecondaryTrafficAlarmHasShown, 0);
            settingsValues.put(Settings.ShowUpDownSpeed, 0);
            settingsValues.put(Settings.LeftDaysAlarmHasShown, 0);
            long res = database.insert(Settings.TableName, null, settingsValues);

            LicenseStatus status = LicenseManager.getExistingLicense();
            if (status == null) {
                LicenseManager.initializeLicenseFile(new LicenseStatus(BuildConfig.VERSION_CODE + "",
                                                                       Helper.getDeviceId(),
                                                                       Helper.getCurrentDate(),
                                                                       LicenseManager.Status.TESTING_TIME.ordinal(),
                                                                       1));
            }
        } catch (MyRuntimeException e) {
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
            database.execSQL(Settings.DropTable);
            onCreate(database);
        } catch (MyRuntimeException e) {
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
        } catch (MyRuntimeException e) {
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
        } catch (MyRuntimeException e) {
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
        } catch (MyRuntimeException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void insertDataFromAsset(SQLiteDatabase db, String tableName, String filePathFromAsset, char delimiter) {

        InputStreamReader isr;
        try {
            isr = new InputStreamReader(App.context.getAssets().open(filePathFromAsset), "UTF-8");

            CsvReader csvReader = new CsvReader(isr, delimiter);
            csvReader.readHeaders();
            while (csvReader.readRecord()) {
                ContentValues values = new ContentValues();
                for (int i = 0; i < csvReader.getHeaderCount(); i++) {
                    values.put(csvReader.getHeader(i), csvReader.get(csvReader.getHeader(i)));
                }
                db.insert(tableName, null, values);
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}