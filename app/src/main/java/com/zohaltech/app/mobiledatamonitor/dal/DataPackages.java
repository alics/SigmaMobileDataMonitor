package com.zohaltech.app.mobiledatamonitor.dal;

/**
 * Created by Ali on 7/15/2015.
 */
public class DataPackages {

    static final String TableName                 = "DataPackages";
    static final String Id                        = "Id";
    static final String OperatorId                = "OperatorId";
    static final String Title                     = "Title";
    static final String Period                    = "Period";
    static final String Price                     = "Price";
    static final String PrimaryTraffic            = "PrimaryTraffic";
    static final String SecondaryTraffic          = "SecondaryTraffic";
    static final String SecondaryTrafficStartTime = "SecondaryTrafficStartTime";
    static final String SecondaryTrafficEndTime   = "SecondaryTrafficEndTime";
    static final String UssdCode                  = "UssdCode";
    static final String Custom                    = "Custom";

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
}
