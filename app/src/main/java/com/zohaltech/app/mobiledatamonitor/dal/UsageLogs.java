package com.zohaltech.app.mobiledatamonitor.dal;

/**
 * Created by Ali on 7/15/2015.
 */
public class UsageLogs {
    static final String TableName = "UsageLogs";
    static final String Id = "Id";
    static final String TrafficBytes = "TrafficBytes";
    static final String LogDateTime = "LogDateTime";

    static final String CreateTable = "CREATE TABLE " + TableName + " (" +
            Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            TrafficBytes + "BIGINT NOT NULL, " +
            LogDateTime + "DATE );";

}