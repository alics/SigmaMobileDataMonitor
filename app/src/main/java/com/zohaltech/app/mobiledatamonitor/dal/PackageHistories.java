package com.zohaltech.app.mobiledatamonitor.dal;

/**
 * Created by Ali on 7/15/2015.
 */
public class PackageHistories {
    static final String TableName = "PackageHistories";
    static final String Id = "Id";
    static final String DataPackageId = "DataPackageId";
    static final String StartDateTime = "StartDateTime";
    static final String EndDateTime = "EndDateTime";
    static final String SimId = "SimId";
    static final String Active = "Active";


    static final String CreateTable = "CREATE TABLE " + TableName + " (" +
            Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            DataPackageId + " INTEGER REFERENCES " + DataPackages.TableName + " (" + DataPackages.Id + "), " +
            StartDateTime + "DATE NOT NULL," +
            EndDateTime + " DATE NOT NULL," +
            SimId + " INTEGER  NOT NULL ," +
            Active + " BOOLEAN   );";


    static final String DropTable = "Drop Table If Exists " + TableName;
}
