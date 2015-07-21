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

            database.execSQL(getInitDbQuery());

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

    private String getInitDbQuery() {
        String initDbQuery = "INSERT INTO MobileOperators (Name) VALUES('همراه اول');\n" +
                "INSERT INTO MobileOperators (Name) VALUES('رایتل');\n" +
                "INSERT INTO MobileOperators (Name) VALUES('ایرانسل');\n" +
                "\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,UssdCode,Custom) VALUES(1,'بسته آلفا',1,1000,157286400,'*100*11#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,UssdCode,Custom) VALUES(1,'بسته آلفا',7,5000,838860800,'*100*12#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,UssdCode,Custom) VALUES(1,'بسته آلفا',30,10000,2684354560,'*100*13#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,UssdCode,Custom,SecondaryTrafficStartTime,SecondaryTrafficEndTime) VALUES(1,'بسته آلفا',7,1000,0,838860800,'*100*14#',0,'23:00','08:00');\n" +
                "\n" +
                "\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',1,1500,104857600,104857600,'02:00','07:00','*100*211#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',1,3500,314572800,314572800,'02:00','07:00','*100*212#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',1,9000,1073741824,1073741824,'02:00','07:00','*100*213#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',1,10000,3221225472,3221225472,'02:00','07:00','*100*214#',0);\n" +
                "\n" +
                "\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',7,2500,104857600,104857600,'02:00','07:00','*100*221#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',7,5000,314572800,314572800,'02:00','07:00','*100*222#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',7,10000,1073741824,1073741824,'02:00','07:00','*100*223#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',7,13500,3221225472,3221225472,'02:00','07:00','*100*224#',0);\n" +
                "\n" +
                "\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',30,4000,104857600,104857600,'02:00','07:00','*100*231#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',30,7000,314572800,314572800,'02:00','07:00','*100*232#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',30,13000,1073741824,1073741824,'02:00','07:00','*100*233#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(1,'بسته آلفا+',30,17000,3221225472,3221225472,'02:00','07:00','*100*234#',0);\n" +
                "\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۲۰ مگابایت ویژه مشترکین اعتباری',1,5000,20971520,0,null,null,'*555*5*1*1#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۵۰ مگابایت ویژه مشترکین اعتباری',1,9000,52428800,0,null,null,'*555*5*1*9#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۱ گیگابایت (۲ تا ۸ صبح) ویژه مشترکین اعتباری',1,10000,0,1073741824,'02:00','08:00','*555*5*1*7#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۲۰۰ مگابایت (۵۰ مگابایت + ۱۵۰ مگابایت (۲ بامداد تا ۲ ظهر)) ویژه مشترکین اعتباری',1,12000,52428800,157286400,'02:00','14:00','*555*5*1*6#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۲۵۰ مگابایت(۱۵۰ مگابایت + ۱۰۰ مگابایت (۲ بامداد تا ۲ ظهر))',1,15000,157286400,104857600,'02:00','14:00','*555*5*1*10#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۳۰۰ مگابایت (۱۵۰ مگابایت + ۱۵۰ مگابایت (۲ بامداد تا ۲ ظهر)) ویژه مشترکین اعتباریویژه اعتباری',1,20000,157286400,157286400,'02:00','14:00','*555*5*1*2#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۶۰۰ مگابایت (۲۰۰ مگابایت + ۴۰۰ مگابایت (۲ بامداد تا ۲ ظهر)) ویژه مشترکین اعتباری',1,30000,209715200,419430400,'02:00','14:00','*555*5*1*5#',0);\n" +
                "\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۴۰ مگابایت',7,15000,41943040,0,null,null,'*555*5*2*8#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۶۰ مگابایت',7,20000,62914560,0,null,null,'*555*5*2*10#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۲۰۰ مگابایت (۱۰۰ مگابایت + ۱۰۰ مگابایت (۲ بامداد تا ۲ ظهر)) ویژه مشترکین اعتباری',7,25000,104857600,104857600,'02:00','14:00','*555*5*2*2#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۴۰۰ مگابایت (۲۰۰ مگابایت + ۲۰۰ مگابایت (۲ بامداد تا ۲ ظهر)) ویژه مشترکین اعتباری',7,40000,209715200,209715200,'02:00','14:00','*555*5*2*3#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۱۰۰۰ مگابایت (۹ شب تا ۹ صبح) ویژه مشترکین اعتباری',7,40000,0,1073741824,'21:00','09:00','*555*5*2*11#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۹۰۰ مگابایت (۴۰۰ مگابایت + ۵۰۰ مگابایت (۲ بامداد تا ۲ ظهر)) ویژه مشترکین اعتباری',7,50000,419430400,524288000,'02:00','14:00','*555*5*2*1#',0);\n" +
                "\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۲۰۰ مگابایت',30,5000,209715200,0,null,null,'*555*5*3*1#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۳ گیگابایت ویژه مشترکین اعتباری',30,13000,3221225472,0,null,null,'*555*5*3*5#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۵ گیگابایت',30,20000,5368709120,0,null,null,'*555*5*8*2#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۱۰ گیگابایت',30,32500,10737418240,0,null,null,'*555*5*8*3#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۷۰۰ مگابایت (۴۰۰ مگابایت + ۳۰۰ مگابایت (۲ بامداد تا ۲ ظهر)) ویژه مشترکین اعتباری',30,8000,419430400,314572800,'02:00','14:00','*555*5*3*11#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۲/۵ گیگابایت (۱.۵ گیگابایت + ۱ گیگابایت ۱۲ شب تا ۱۲ ظهر) ویژه مشترکین اعتباری',30,10000,1610612736,1073741824,'00:00','12:00','*555*5*3*2#',0);\n" +
                "\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۶ گیگابایت',90,25000,6442450944,0,null,null,'*555*5*8*5#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۱۲ گیگابایت',90,37500,12884901888,0,null,null,'*555*5*8*7#',0);\n" +
                "\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۹گیگابایت ویژه مشترکین اعتباری',180,35000,9663676416,0,null,null,'*555*5*4*12#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۳۰گیگابایت ویژه مشترکین اعتباری',180,60000,9663676416,0,null,null,'*555*5*4*1#',0);\n" +
                "\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۱۲ گیگابایت ویژه مشترکین اعتباری',360,42000,9663676416,0,null,null,'*555*5*4*13#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(2,'۱۰۰ گیگابایت',360,175000,107374182400,0,null,null,'*555*5*8*13#',0);\n" +
                "\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(3,'اینترنت پایه 200 مگابایت 7 روزه',7,3000,209715200,0,null,null,'*142*1*1*1*1#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(3,'اینترنت پایه 1.5 گیگابایت 30 روزه',30,12500,1610612736,0,null,null,'*142*1*1*1*2#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(3,'اینترنت پایه 3 گیگابایت 30 روزه',30,19900,3221225472,0,null,null,'*142*1*1*1*3#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(3,'اینترنت پایه 4.5 گیگابایت 90 روزه',90,32250,4831838208,0,null,null,'*142*1*1*1*4#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(3,'اینترنت پایه 9 گیگابایت 90 روزه',90,56118,9663676416,0,null,null,'*142*1*1*1*5#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(3,'اینترنت پایه 9 گیگابایت 180 روزه',180,67500,9663676416,0,null,null,'*142*1*1*1*6#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(3,'اینترنت پایه 18 گیگابایت 180 روزه',180,107460,19327352832,0,null,null,'*142*1*1*1*7#',0);\n" +
                "\n" +
                "\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(3,'اینترنت حرفه ای 350 مگابایت 10 روزه',10,6000,367001600,0,null,null,'*142*1*1*3*1#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(3,'اینترنت حرفه ای 2 گیگابایت 30 روزه',30,23000,2147483648,0,null,null,'*142*1*1*3*2#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(3,'اینترنت حرفه ای 4 گیگابایت 30 روزه',30,37000,3221225472,0,null,null,'*142*1*1*3*3#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(3,'اینترنت حرفه ای 6 گیگابایت 90 روزه',90,64840,4831838208,0,null,null,'*142*1*1*3*4#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(3,'اینترنت حرفه ای 12 گیگابایت 90 روزه',90,104340,9663676416,0,null,null,'*142*1*1*3*5#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(3,'اینترنت حرفه ای 12 گیگابایت 180 روزه',180,124200,9663676416,0,null,null,'*142*1*1*3*6#',0);\n" +
                "INSERT INTO DataPackages (OperatorId,Title,Period,Price,PrimaryTraffic,SecondaryTraffic,SecondaryTrafficStartTime,SecondaryTrafficEndTime,UssdCode,Custom) VALUES(3,'اینترنت حرفه ای 24 گیگابایت 180 روزه',180,199800,19327352832,0,null,null,'*142*1*1*3*7#',0);\n";

        return initDbQuery;
    }
}