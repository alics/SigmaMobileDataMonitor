package com.zohaltech.app.sigma.dal;

import android.content.ContentValues;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.zohaltech.app.sigma.BuildConfig;
import com.zohaltech.app.sigma.activities.IntroductionActivity;
import com.zohaltech.app.sigma.classes.App;
import com.zohaltech.app.sigma.classes.CsvReader;
import com.zohaltech.app.sigma.classes.Helper;
import com.zohaltech.app.sigma.classes.LicenseManager;
import com.zohaltech.app.sigma.classes.LicenseStatus;
import com.zohaltech.app.sigma.classes.MyRuntimeException;

import java.io.File;
import java.io.InputStreamReader;
import java.util.Iterator;


public class DataAccess extends SQLiteOpenHelper {

    public static final String DATABASE_NAME    = "SIGMA";
    //public static final int    DATABASE_VERSION = 9; //published in versions 1.06, 1.07
    //public static final int    DATABASE_VERSION = 10; //published in versions 1.08
    //public static final int    DATABASE_VERSION = 11; //published in versions 1.1
    //public static final int    DATABASE_VERSION = 12; //published in versions 1.11
    public static final int    DATABASE_VERSION = 13; //published in versions 1.12

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
            database.execSQL(SystemSettings.CreateTable);
            
            //todo : uncomment below lines for app usages
            database.execSQL(Applications.CreateTable);
            database.execSQL(AppsUsageLogs.CreateTable);
            database.execSQL(SnapshotStatus.CreateTable);

            insertDataFromAsset(database, MobileOperators.TableName, "data/operators.csv", ';');
            insertDataFromAsset(database, DataPackages.TableName, "data/packages.csv", ';');

            //prevent first daily history usage to be null
            ContentValues usageLogValues = new ContentValues();
            usageLogValues.put(UsageLogs.TrafficBytes, 0);
            usageLogValues.put(UsageLogs.TrafficBytesWifi, 0);
            usageLogValues.put(UsageLogs.LogDateTime, Helper.getCurrentDateTime());
            database.insert(UsageLogs.TableName, null, usageLogValues);

            ContentValues trafficHistoryValues = new ContentValues();
            for (int i = 0; i < 29; i++) {
                trafficHistoryValues.put(DailyTrafficHistories.LogDate, Helper.addDay(i - 29));
                trafficHistoryValues.put(DailyTrafficHistories.Traffic, 0);
                trafficHistoryValues.put(DailyTrafficHistories.TrafficWifi, 0);
                database.insert(DailyTrafficHistories.TableName, null, trafficHistoryValues);
            }

            // initial settings
            ContentValues settingsValues = new ContentValues();
            settingsValues.put(Settings.DataConnected, 1);
            settingsValues.put(Settings.DailyTraffic, 0);
            settingsValues.put(Settings.AlarmType, 1);
            settingsValues.put(Settings.PercentTrafficAlarm, 85);
            settingsValues.put(Settings.LeftDaysAlarm, 1);
            settingsValues.put(Settings.AlarmTypeRes, 2);
            settingsValues.put(Settings.PercentTrafficAlarmRes, 85);
            settingsValues.put(Settings.LeftDaysAlarmRes, 1);
            settingsValues.put(Settings.ShowNotification, 1);
            settingsValues.put(Settings.ShowNotificationWhenDataOrWifiIsOn, 0);
            settingsValues.put(Settings.ShowWifiUsage, 1);
            settingsValues.put(Settings.ShowNotificationInLockScreen, 1);
            settingsValues.put(Settings.ShowUpDownSpeed, 0);
            settingsValues.put(Settings.VibrateInAlarms, 1);
            settingsValues.put(Settings.SoundInAlarms, 1);
            database.insert(Settings.TableName, null, settingsValues);


            ContentValues systemSettingsValues = new ContentValues();
            systemSettingsValues.put(SystemSettings.LeftDaysAlarmHasShown, 0);
            systemSettingsValues.put(SystemSettings.TrafficAlarmHasShown, 0);
            systemSettingsValues.put(SystemSettings.PrimaryTrafficFinishHasShown, 0);
            systemSettingsValues.put(SystemSettings.SecondaryTrafficFinishHasShown, 0);
            systemSettingsValues.put(SystemSettings.Installed, 0);
            systemSettingsValues.put(SystemSettings.Registered, 0);
            systemSettingsValues.put(SystemSettings.ActiveSim, 0);
            database.insert(SystemSettings.TableName, null, systemSettingsValues);

            ContentValues snapshot = new ContentValues();
            snapshot.put(SnapshotStatus.Status, SnapshotStatus.Stopped);
            snapshot.put(SnapshotStatus.InitializationStatus, SnapshotStatus.InitStatus.FIRST_SNAPSHOT.ordinal());
            database.insert(SnapshotStatus.TableName, null, snapshot);


            //todo : uncomment below lines for app usages
            insertHasInternetAccessApplications(database);
            deleteStatDirs();

            LicenseStatus status = LicenseManager.getExistingLicense();
            if (status == null) {
                LicenseManager.initializeLicenseFile(new LicenseStatus("" + BuildConfig.VERSION_CODE,
                                                                       Helper.getDeviceId(),
                                                                       Helper.getCurrentDate(),
                                                                       LicenseManager.Status.NOT_REGISTERED.ordinal()));
            }
        } catch (MyRuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        try {
            App.uiPreferences.edit().putBoolean(IntroductionActivity.INTRO_SHOWN, false).apply();
            database.execSQL("UPDATE " + SystemSettings.TableName + " SET " + SystemSettings.Installed + " = 0, " + SystemSettings.Registered + " = 0");

            if (oldVersion < 11) {
                LicenseStatus status = LicenseManager.getExistingLicense();
                if (status == null) {
                    LicenseManager.initializeLicenseFile(new LicenseStatus("" + BuildConfig.VERSION_CODE,
                                                                           Helper.getDeviceId(),
                                                                           Helper.getCurrentDate(),
                                                                           LicenseManager.Status.NOT_REGISTERED.ordinal()));
                } else {
                    status.setStatus(LicenseManager.Status.NOT_REGISTERED.ordinal());
                    LicenseManager.updateLicense(status);
                }
            }

            if (oldVersion < 9) {
                //todo : uncomment below lines for app usages
                database.execSQL(SystemSettings.DropTable);
                database.execSQL(Settings.DropTable);
                database.execSQL(PackageHistories.DropTable);
                database.execSQL(DataPackages.DropTable);
                database.execSQL(UsageLogs.DropTable);
                database.execSQL(DailyTrafficHistories.DropTable);
                database.execSQL(MobileOperators.DropTable);
                database.execSQL(AppsUsageLogs.DropTable);
                database.execSQL(Applications.DropTable);
                database.execSQL(SnapshotStatus.DropTable);
                onCreate(database);
            } else if (oldVersion == 9) {
                version9to10(database);
                version10to11(database);
                version11to12(database);
                version12to13(database);
                //version13to14(database);
                //version14to15(database);
            } else if (oldVersion == 10) {
                version10to11(database);
                version11to12(database);
                version12to13(database);
                //version13to14(database);
                //version14to15(database);
            } else if (oldVersion == 11) {
                version11to12(database);
                version12to13(database);
                //version13to14(database);
                //version14to15(database);
            } else if (oldVersion == 12) {
                version12to13(database);
                //version13to14(database);
                //version14to15(database);
            }
        } catch (MyRuntimeException e) {
            e.printStackTrace();
        }
    }

    private void version9to10(SQLiteDatabase database) {
        try {
            database.execSQL("UPDATE " + DataPackages.TableName + " SET " + DataPackages.Price + " = 10000 WHERE " + DataPackages.UssdCode + " = '*100*233#'");
            database.execSQL("UPDATE " + DataPackages.TableName + " SET " + DataPackages.Custom + " = 1 WHERE " + DataPackages.UssdCode + " = '*100*234#'");
            ContentValues values = new ContentValues();
            values.put(DataPackages.OperatorId, 1);
            values.put(DataPackages.Title, "آلفا+(نسل 3) 30 روزه 4 گیگابایت + (4 گیگابایت هدیه شبانه 2 الی 7 بامداد)");
            values.put(DataPackages.Period, 30);
            values.put(DataPackages.Price, 17000);
            values.put(DataPackages.PrimaryTraffic, 4294967296L);
            values.put(DataPackages.SecondaryTraffic, 4294967296L);
            values.put(DataPackages.SecondaryTrafficStartTime, "02:00");
            values.put(DataPackages.SecondaryTrafficEndTime, "07:00");
            values.put(DataPackages.UssdCode, "*100*234#");
            values.put(DataPackages.Custom, 0);
            database.insert(DataPackages.TableName, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void version10to11(SQLiteDatabase database) {
        try {
            database.execSQL("ALTER TABLE " + UsageLogs.TableName + " ADD COLUMN " + UsageLogs.TrafficBytesWifi + " BIGINT");
            database.execSQL("ALTER TABLE " + DailyTrafficHistories.TableName + " ADD COLUMN " + DailyTrafficHistories.TrafficWifi + " BIGINT");
            database.execSQL("ALTER TABLE " + Settings.TableName + " ADD COLUMN " + Settings.ShowWifiUsage + " BOOLEAN");
            database.execSQL("UPDATE " + Settings.TableName + " SET " + Settings.ShowWifiUsage + " = 1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void version11to12(SQLiteDatabase database) {
        database.execSQL("UPDATE " + DataPackages.TableName + " SET " + DataPackages.Custom + " = 1 WHERE " + DataPackages.Id + " IN (SELECT " + PackageHistories.DataPackageId + " FROM " + PackageHistories.TableName + ")");
        database.execSQL("DELETE FROM " + DataPackages.TableName + " WHERE " + DataPackages.Id + " NOT IN (SELECT " + PackageHistories.DataPackageId + " FROM " + PackageHistories.TableName + ")");
        insertDataFromAsset(database, DataPackages.TableName, "data/packages.csv", ';');
    }

    //todo : uncomment below lines for app usages
    private void version12to13(SQLiteDatabase database) {
        try {
            database.execSQL(Applications.CreateTable);
            database.execSQL(AppsUsageLogs.CreateTable);
            database.execSQL(SnapshotStatus.CreateTable);
            insertHasInternetAccessApplications(database);

            ContentValues snapshot = new ContentValues();
            snapshot.put(SnapshotStatus.Status, SnapshotStatus.Stopped);
            snapshot.put(SnapshotStatus.InitializationStatus, SnapshotStatus.InitStatus.FIRST_SNAPSHOT.ordinal());
            database.insert(SnapshotStatus.TableName, null, snapshot);

            deleteStatDirs();

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

    private void insertHasInternetAccessApplications(SQLiteDatabase database) {
        PackageManager pm = App.context.getPackageManager();
        Iterator iterator = pm.getInstalledPackages(12288).iterator();
        PackageInfo packageInfo;

        while (iterator.hasNext()) {
            packageInfo = (PackageInfo) iterator.next();
            String[] permissions = packageInfo.requestedPermissions;

            if (permissions != null && hasInternetAccess(permissions)) {
                ApplicationInfo info = packageInfo.applicationInfo;
                String appName = pm.getApplicationLabel(info).toString();

                ContentValues appsValues = new ContentValues();
                appsValues.put(Applications.AppName, appName);
                appsValues.put(Applications.PackageName, info.packageName);
                appsValues.put(Applications.Uid, info.uid);
                appsValues.put(Applications.Removed, 0);

                database.insert(Applications.TableName, null, appsValues);
            }
        }
    }

    private Boolean hasInternetAccess(String[] permissions) {
        for (String permission : permissions) {
            if ("android.permission.INTERNET".equals(permission)) {
                return true;
            }
        }
        return false;
    }

    private void deleteStatDirs() {
        File dirWlan = new File(App.context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/wlan0");
        File dirRnmt = new File(App.context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/rmnet0");
        File dirInitWlan = new File(App.context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/init/wlan0");
        File dirInitRnmt = new File(App.context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/init/rmnet0");
        deleteDir(dirRnmt);
        deleteDir(dirWlan);
        deleteDir(dirInitWlan);
        deleteDir(dirInitRnmt);
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    //todo : uncomment below lines for app usages
    //private void insertHasInternetAccessApplications(SQLiteDatabase database) {
    //    PackageManager pm = App.context.getPackageManager();
    //    Iterator iterator = pm.getInstalledPackages(12288).iterator();
    //    PackageInfo packageInfo;
    //
    //    while (iterator.hasNext()) {
    //        packageInfo = (PackageInfo) iterator.next();
    //        String[] permissions = packageInfo.requestedPermissions;
    //
    //        if (permissions != null && hasInternetAccess(permissions)) {
    //            ApplicationInfo info = packageInfo.applicationInfo;
    //            String appName = pm.getApplicationLabel(info).toString();
    //
    //            ContentValues appsValues = new ContentValues();
    //            appsValues.put(Applications.AppName, appName);
    //            appsValues.put(Applications.PackageName, info.packageName);
    //            appsValues.put(Applications.Uid, info.uid);
    //            appsValues.put(Applications.Removed, 0);
    //
    //            database.insert(Applications.TableName, null, appsValues);
    //        }
    //    }
    //}

    //todo : uncomment below lines for app usages
    //private Boolean hasInternetAccess(String[] permissions) {
    //    for (String permission : permissions) {
    //        if ("android.permission.INTERNET".equals(permission)) {
    //            return true;
    //        }
    //    }
    //    return false;
    //}
}