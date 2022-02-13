package com.tolentino.dragonsis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DbManager extends SQLiteOpenHelper {

    private static final String TAG = "DbManager";

    //Accounts
    private static final String ACC_TABLE_NAME = "accounts_table";
    private static final String ACC_COL1 = "username";
    private static final String ACC_COL2 = "password";
    private static final String ACC_COL3 = "usertype";

    //Products
    //insert here...

    //Inventory
    //insert here...

    //Sales History
    //insert here...

    //Inventory History
    //insert here...


    public DbManager(Context context) {
        super(context, ACC_TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_Acc_Table = "CREATE TABLE " + ACC_TABLE_NAME + "("
                + ACC_COL1 + " TEXT PRIMARY KEY,"
                + ACC_COL2 + " TEXT,"
                + ACC_COL3 + " TEXT)";
        db.execSQL(create_Acc_Table);

        //create_Prod_Table
        //insert here...

        //create_Inv_Table
        //insert here...

        //create_Sales_Table
        //insert here...

        //create_InvHis_Table
        //insert here...
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + ACC_TABLE_NAME);
        onCreate(db);
    }

    // Adding new User Details
    void insertUser(String username, String password, String usertype) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(ACC_COL1, username);
        cValues.put(ACC_COL2, password);
        cValues.put(ACC_COL3, usertype);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(ACC_TABLE_NAME, null, cValues);
        Log.i("ACCOUNST TABLE:", "New User Added");
        db.close();
    }

    // Get All User Details
    public ArrayList<HashMap<String, String>> getUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM " + ACC_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> users = new HashMap<>();
            users.put("username", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL1)));
            users.put("password", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL2)));
            users.put("usertype", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL3)));
            userList.add(users);

            Log.i("ADDED TO DATABASE",  cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL3)));
        }
        return userList;
    }

    // Get User Details based on Username
    public ArrayList<HashMap<String, String>> getUserByUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM " + ACC_TABLE_NAME;
        Cursor cursor = db.query(ACC_TABLE_NAME, new String[]{ACC_COL1, ACC_COL2, ACC_COL3}, ACC_COL1 + "=?", new String[]{String.valueOf(username)}, null, null, null, null);
        if (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("username", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL1)));
            user.put("password", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL2)));
            user.put("usertype", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL3)));

            Log.i("ADDED TO DATABASE",  cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL3)));

            userList.add(user);
        }
        return userList;
    }

    // Delete User Details
    public void deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ACC_TABLE_NAME, ACC_COL1 + " = ?", new String[]{String.valueOf(username)});
        db.close();
    }

    //check if User record exists
    public boolean checkUserValues(String fieldValue) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { ACC_COL1 };
        String selection = ACC_COL1 + " =?";
        String[] selectionArgs = { fieldValue };
        String limit = "1";

        Cursor cursor = db.query(ACC_TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

}