package com.zohaltech.app.mobiledatamonitor.dal;

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

}
