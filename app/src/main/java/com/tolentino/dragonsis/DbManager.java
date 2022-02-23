package com.tolentino.dragonsis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DbManager extends SQLiteOpenHelper {

    private static final String TAG = "DbManager";

    //Accounts
    private static final String ACC_TABLE_NAME = "accounts_table";
    private static final String ACC_COL1 = "user_name";
    private static final String ACC_COL2 = "user_password";
    private static final String ACC_COL3 = "user_type";
    private static final String ACC_COL4 = "user_email";

    //Products
    private static final String PROD_TABLE_NAME = "products_table";
    private static final String PROD_COL1 = "prod_ID";
    private static final String PROD_COL2 = "prod_name";
    private static final String PROD_COL3 = "prod_critical_num";
    private static final String PROD_COL4 = "prod_description";
    private static final String PROD_COL5 = "prod_price";
    private static final String PROD_COL6 = "prod_category";

    //Inventory
    private static final String INV_TABLE_NAME = "inventory_table";
    private static final String INV_COL1 = "inventory_ID";
    private static final String INV_COL2 = "inventory_date";
    private static final String INV_COL3 = "inventory_quantity";
    private static final String INV_COL4 = "inventory_quantity_change";
    private static final String INV_COL5 = "inventory_remark";
    private static final String INV_COL6 = "inventory_date_updated";
    private static final String INV_COL7 = "prod_name";

    //Inventory History
    private static final String INV_HIS_TABLE_NAME = "inventory_history_table";
    private static final String INV_HIS_COL1 = "update_ID";
    private static final String INV_HIS_COL2 = "inventory_update_date";
    private static final String INV_HIS_COL3 = "inventory_action";
    private static final String INV_HIS_COL4 = "inventory_quantity_change";
    private static final String INV_HIS_COL5 = "inventory_ID";
    private static final String INV_HIS_COL6 = "prod_name";

    //Sales History
    private static final String SALES_TABLE_NAME = "sales_table";
    private static final String SALES_COL1 = "sales_ID";
    private static final String SALES_COL2 = "sales_amount";
    private static final String SALES_COL3 = "items_sold";
    private static final String SALES_COL4 = "sales_dates";
    private static final String SALES_COL5 = "sales_time";



    public DbManager(Context context) {
        super(context, ACC_TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Users Table
        String create_Acc_Table = "CREATE TABLE " + ACC_TABLE_NAME + "("
                + ACC_COL1 + " TEXT PRIMARY KEY,"
                + ACC_COL2 + " TEXT,"
                + ACC_COL3 + " TEXT,"
                + ACC_COL4 + " TEXT)";
        db.execSQL(create_Acc_Table);

        //create Products Table
        String create_Prod_Table = "CREATE TABLE " + PROD_TABLE_NAME + "("
                + PROD_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PROD_COL2 + " TEXT,"
                + PROD_COL3 + " TEXT,"
                + PROD_COL4 + " TEXT,"
                + PROD_COL5 + " INTEGER,"
                + PROD_COL6 + " TEXT)";
        db.execSQL(create_Prod_Table);

        //create_Inv_Table
        String create_Inv_Table = "CREATE TABLE " + INV_TABLE_NAME + "("
                + INV_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + INV_COL2 + " TEXT,"
                + INV_COL3 + " INTEGER,"
                + INV_COL4 + " INTEGER,"
                + INV_COL5 + " TEXT,"
                + INV_COL6 + " TEXT,"
                + INV_COL7 + " TEXT)";
        db.execSQL(create_Inv_Table);

        //create_Sales_Table
        String create_Sales_Table = "CREATE TABLE " + SALES_TABLE_NAME + "("
                + SALES_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SALES_COL2 + " TEXT,"
                + SALES_COL3 + " TEXT,"
                + SALES_COL4 + " TEXT,"
                + SALES_COL5 + " TEXT)";
        db.execSQL(create_Sales_Table);

        //create_InvHis_Table
        String create_Inv_His_Table = "CREATE TABLE " + INV_HIS_TABLE_NAME + "("
                + INV_HIS_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + INV_HIS_COL2 + " TEXT,"
                + INV_HIS_COL3 + " TEXT,"
                + INV_HIS_COL4 + " INTEGER,"
                + INV_HIS_COL5 + " TEXT,"
                + INV_HIS_COL6 + " TEXT)";
        db.execSQL(create_Inv_His_Table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + ACC_TABLE_NAME);
        onCreate(db);
    }

    //user registered check
    public boolean LoginCheck(String user, String pass) throws SQLException
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mcursor = db.rawQuery("SELECT * FROM accounts_table WHERE user_name =? and user_password =?", new String[]{user,pass});
        if (mcursor!=null)
        {
            if (mcursor.getCount()>0)
            {
                return true;
            }
        }
        return false;
    }

    // Adding new User Details
    void insertUser(String user_password, String user_name, String user_email, String user_type) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(ACC_COL1, user_name);
        cValues.put(ACC_COL2, user_password);
        cValues.put(ACC_COL3, user_email);
        cValues.put(ACC_COL4, user_type);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(ACC_TABLE_NAME, null, cValues);

        if(newRowId == 1){
            Log.i("ACCOUNT TABLE:", "User Added Correctly");
        }else{
            Log.i("ACCOUNT TABLE:", "User not Added Correctly");
        }

        db.close();
    }

    // Adding New Product Details
    void insertProduct(String prod_name, Integer prod_critical_num, String prod_description, Integer prod_price, String prod_category) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(PROD_COL2, prod_name);
        cValues.put(PROD_COL3, prod_critical_num);
        cValues.put(PROD_COL4, prod_description);
        cValues.put(PROD_COL5, prod_price);
        cValues.put(PROD_COL6, prod_category);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(PROD_TABLE_NAME, null, cValues);

        if(newRowId == 1){
            Log.i("PRODUCT TABLE:", "Product Added Correctly");
        }else{
            Log.i("PRODUCT TABLE:", "Product not Added Correctly");
        }

        db.close();
    }

    // Update User Details
    void updateUser(){
        //tsaka na lagyan
    }

    // Get All User Details
    public ArrayList<HashMap<String, String>> getUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM " + ACC_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> users = new HashMap<>();
            users.put("user_name", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL1)));
            users.put("user_password", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL2)));
            users.put("user_email", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL3)));
            users.put("user_type", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL4)));
            userList.add(users);

            Log.i("ADDED TO DATABASE",  cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL3))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL4)));
        }
        return userList;
    }

    // Get User Details based on Username
    public ArrayList<HashMap<String, String>> getUserByUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM " + ACC_TABLE_NAME;
        Cursor cursor = db.query(ACC_TABLE_NAME, new String[]{ACC_COL1, ACC_COL2, ACC_COL3, ACC_COL4}, ACC_COL1 + "=?", new String[]{String.valueOf(username)}, null, null, null, null);
        if (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("user_name", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL1)));
            user.put("user_password", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL2)));
            user.put("user_email", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL3)));
            user.put("user_type", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL4)));

            Log.i("ADDED TO DATABASE",  cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL3))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL4)));

            userList.add(user);
        }
        return userList;
    }

    // Delete User Details
    public void deleteUser(String user_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ACC_TABLE_NAME, ACC_COL3 + " = ?", new String[]{String.valueOf(user_name)});
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