package com.zohaltech.app.mobiledatamonitor.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zohaltech.app.mobiledatamonitor.entities.ContactType;

import java.util.ArrayList;

public class ContactTypes
{
    static final String TableName   = "ContactTypes";
    static final String Id          = "Id";
    static final String Name        = "Name";

    static final String CreateTable = "CREATE TABLE " + TableName + " (" +
                                      Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                      Name + " VARCHAR(128));";
    static final String DropTable   = "Drop Table If Exists " + TableName;

    private static ArrayList<ContactType> select(String whereClause, String[] selectionArgs) {
        ArrayList<ContactType> contactTypes = new ArrayList<>();
        DataAccess da = new DataAccess();
        SQLiteDatabase db = da.getReadableDB();
        Cursor cursor = null;

        try
        {
            String query = "Select * From " + TableName + " " + whereClause;
            cursor = db.rawQuery(query, selectionArgs);
            if (cursor != null && cursor.moveToFirst())
            {
                do
                {
                    ContactType contactType = new ContactType(
                            cursor.getLong(cursor.getColumnIndex(Id)),
                            cursor.getString(cursor.getColumnIndex(Name)));
                    contactTypes.add(contactType);
                } while (cursor.moveToNext());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (cursor != null && !cursor.isClosed()) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }
        return contactTypes;
    }

    public static ArrayList<ContactType> select()
    {
        return select("", null);
    }

    public static ContactType select(long id)
    {
        ArrayList<ContactType> contactTypes = select("Where " + Id + " = " + id, null);
        if (contactTypes.size() > 0)
        {
            return contactTypes.get(0);
        }
        else
        {
            return null;
        }
    }

    public static long insert(ContactType contactType)
    {
        ContentValues values = new ContentValues();

        values.put(Name, contactType.getName());

        DataAccess da = new DataAccess();
        return da.insert(TableName, values);
    }

    public static long update(ContactType contactType)
    {
        ContentValues values = new ContentValues();

        values.put(Name, contactType.getName());

        DataAccess da = new DataAccess();
        return da.update(TableName, values, Id + " =? ", new String[]{String.valueOf(contactType.getId())});
    }

    public static long delete(ContactType contactType)
    {
        DataAccess db = new DataAccess();
        return db.delete(TableName, Id + " =? ", new String[]{String.valueOf(contactType.getId())});
    }
}
