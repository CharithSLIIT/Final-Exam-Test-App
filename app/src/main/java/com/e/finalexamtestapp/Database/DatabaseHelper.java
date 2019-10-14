package com.e.finalexamtestapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Developers Link
    //https://developer.android.com/training/data-storage/sqlite

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UsersMaster.Users.TABLE_USER + " (" +
                    UsersMaster.Users.USER_TABLE_COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UsersMaster.Users.USER_TABLE_COLUMN_NAME_USERNAME + " TEXT," +
                    UsersMaster.Users.USER_TABLE_COLUMN_NAME_PASSWORD + " TEXT," +
                    UsersMaster.Users.USER_TABLE_COLUMN_NAME_GENDER + " TEXT," +
                    UsersMaster.Users.USER_TABLE_COLUMN_NAME_TYPE + " TEXT," +
                    UsersMaster.Users.USER_TABLE_COLUMN_NAME_STAR + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UsersMaster.Users.TABLE_USER;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FeedReader.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


    //Insert Function
    public boolean addData(String username, String password, String gender, String type, int star)
    {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.USER_TABLE_COLUMN_NAME_USERNAME, username);
        values.put(UsersMaster.Users.USER_TABLE_COLUMN_NAME_PASSWORD, password);
        values.put(UsersMaster.Users.USER_TABLE_COLUMN_NAME_GENDER, gender);
        values.put(UsersMaster.Users.USER_TABLE_COLUMN_NAME_TYPE, type);
        values.put(UsersMaster.Users.USER_TABLE_COLUMN_NAME_STAR, star);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UsersMaster.Users.TABLE_USER, null, values);

        return newRowId != -1;
    }


    //Retrieve Function 01
    public Cursor readAllInfo1()
    {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                UsersMaster.Users.USER_TABLE_COLUMN_NAME_USERNAME,
                UsersMaster.Users.USER_TABLE_COLUMN_NAME_PASSWORD,
                UsersMaster.Users.USER_TABLE_COLUMN_NAME_GENDER,
                UsersMaster.Users.USER_TABLE_COLUMN_NAME_TYPE,
                UsersMaster.Users.USER_TABLE_COLUMN_NAME_STAR
        };

        // Filter results WHERE "title" = 'My Title'
//        String selection = UsersMaster.Users.TABLE_USER + " = ?";
//        String[] selectionArgs = { username };

        // How you want the results sorted in the resulting Cursor
//        String sortOrder =
//                UsersMaster.Users.USER_TABLE_COLUMN_NAME_STAR + " DESC";

        //Assigning null for now
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;

        return db.query(
                UsersMaster.Users.TABLE_USER,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
    }

    //Retrieve Function 02
    public Cursor readAllInfo2()
    {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String query = "SELECT * FROM " + UsersMaster.Users.TABLE_USER;

        return sqLiteDatabase.rawQuery(query, null);
    }

    //Delete Function
    public boolean deleteUser(String username)
    {
        SQLiteDatabase db = getReadableDatabase();

        // Define 'where' part of query.
        String selection = UsersMaster.Users.TABLE_USER + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { username };

        // Issue SQL statement.
        int deletedRows = db.delete(UsersMaster.Users.TABLE_USER, selection, selectionArgs);

        return deletedRows != 0;
    }

    //Update function
    public boolean updateUser(String username, int star)
    {
        SQLiteDatabase db = getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.USER_TABLE_COLUMN_NAME_STAR, star);

        // Which row to update, based on the title
        String selection = UsersMaster.Users.USER_TABLE_COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = { username };

        int count = db.update(
                UsersMaster.Users.TABLE_USER,
                values,
                selection,
                selectionArgs);

        return count != 0;
    }
}
