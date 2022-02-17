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
    private static final String ACC_COL1 = "user_ID";
    private static final String ACC_COL2 = "user_password";
    private static final String ACC_COL3 = "user_name";
    private static final String ACC_COL4 = "user_email";
    private static final String ACC_COL5 = "user_type";

    //Products
    private static final String PROD_TABLE_NAME = "products_table";
    private static final String PROD_COL1 = "prod_ID";
    private static final String PROD_COL2 = "prod_name";
    private static final String PROD_COL3 = "prod_crit_num";

    //Inventory
    private static final String INV_TABLE_NAME = "inventory_table";
    private static final String INV_COL1 = "item_no";
    private static final String INV_COL2 = "item_name";
    private static final String INV_COL3 = "item_description";
    private static final String INV_COL4 = "item_quantity";
    private static final String INV_COL5 = "item_critical_num";
    private static final String INV_COL6 = "prod_ID";
    private static final String INV_COL7 = "user_ID";
    private static final String INV_COL8 = "category_ID";

    //Inventory History
    private static final String INV_HIS_TABLE_NAME = "inventory_history_table";
    private static final String INV_HIS_COL1 = "history_no";
    private static final String INV_HIS_COL2 = "item_quantity_change";
    private static final String INV_HIS_COL3 = "date_updated";
    private static final String INV_HIS_COL4 = "item_no";
    private static final String INV_HIS_COL5 = "item_name";

    //Sales History
    private static final String SALES_TABLE_NAME = "sales_table";
    private static final String SALES_COL1 = "sales_ID";
    private static final String SALES_COL2 = "sales_amount";
    private static final String SALES_COL3 = "items_sold";
    private static final String SALES_COL4 = "sales_dates";
    private static final String SALES_COL5 = "sales_time";

    //Category
    private static final String CAT_TABLE_NAME = "category_table";
    private static final String CAT_COL1 = "category_ID";
    private static final String CAT_COL2 = "category_name";
    private static final String CAT_COL3 = "category_description";

    //Customer
    private static final String CST_TABLE_NAME = "customer_table";
    private static final String CST_COL1 = "customer_ID";
    private static final String CST_COL2 = "customer_name";

    //Purchase Order
    private static final String PUR_TABLE_NAME = "purchase_table";
    private static final String PUR_COL1 = "order_ID";
    private static final String PUR_COL2 = "order_date";
    private static final String PUR_COL3 = "item_no";

    //Order Product Quantity
    private static final String QNT_TABLE_NAME = "purchase_table";
    private static final String QNT_COL1 = "item_no";
    private static final String QNT_COL2 = "order_quantity";



    public DbManager(Context context) {
        super(context, ACC_TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_Acc_Table = "CREATE TABLE " + ACC_TABLE_NAME + "("
                + ACC_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ACC_COL2 + " TEXT,"
                + ACC_COL3 + " TEXT UNIQUE,"
                + ACC_COL4 + " TEXT,"
                + ACC_COL5 + " TEXT)";
        db.execSQL(create_Acc_Table);

        //create_Prod_Table
        String create_Prod_Table = "CREATE TABLE " + PROD_TABLE_NAME + "("
                + PROD_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PROD_COL2 + " TEXT,"
                + PROD_COL3 + " TEXT)";
        db.execSQL(create_Prod_Table);

        //create_Inv_Table
        String create_Inv_Table = "CREATE TABLE " + INV_TABLE_NAME + "("
                + INV_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + INV_COL2 + " TEXT,"
                + INV_COL3 + " TEXT,"
                + INV_COL4 + " TEXT,"
                + INV_COL5 + " TEXT,"
                + INV_COL6 + " TEXT,"
                + INV_COL7 + " TEXT,"
                + INV_COL8 + " TEXT)";
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
                + INV_HIS_COL4 + " TEXT,"
                + INV_HIS_COL5 + " TEXT)";
        db.execSQL(create_Inv_His_Table);

        //create_Category_Table
        String create_Category_Table = "CREATE TABLE " + CAT_TABLE_NAME + "("
                + CAT_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CAT_COL2 + " TEXT,"
                + CAT_COL3 + " TEXT)";
        db.execSQL(create_Category_Table);

        //create_Customer_Table
        String create_Cst_Table = "CREATE TABLE " + CST_TABLE_NAME + "("
                + CST_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CST_COL2 + " TEXT)";
        db.execSQL(create_Cst_Table);

        //create_Purchase_Table
        String create_Purchase_Table = "CREATE TABLE " + PUR_TABLE_NAME + "("
                + PUR_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PUR_COL2 + " TEXT,"
                + PUR_COL3 + " TEXT)";
        db.execSQL(create_Purchase_Table);

        //create_ProdQnt_Table
        String create_ProdQnt_Table = "CREATE TABLE " + QNT_TABLE_NAME + "("
                + QNT_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + QNT_COL2 + " TEXT)";
        db.execSQL(create_ProdQnt_Table);

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
        cValues.put(ACC_COL2, user_password);
        cValues.put(ACC_COL3, user_name);
        cValues.put(ACC_COL4, user_email);
        cValues.put(ACC_COL5, user_type);
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
    void insertProduct(String prod_name, String prod_crit_num) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(PROD_COL2, prod_name);
        cValues.put(PROD_COL3, prod_crit_num);
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
            users.put("user_ID", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL1)));
            users.put("user_password", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL2)));
            users.put("user_name", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL3)));
            users.put("user_email", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL4)));
            users.put("user_type", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL5)));
            userList.add(users);

            Log.i("ADDED TO DATABASE",  cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL3))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL4))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL5)));
        }
        return userList;
    }

    // Get User Details based on Username
    public ArrayList<HashMap<String, String>> getUserByUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM " + ACC_TABLE_NAME;
        Cursor cursor = db.query(ACC_TABLE_NAME, new String[]{ACC_COL1, ACC_COL2, ACC_COL3, ACC_COL4, ACC_COL5}, ACC_COL3 + "=?", new String[]{String.valueOf(username)}, null, null, null, null);
        if (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("user_ID", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL1)));
            user.put("user_password", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL2)));
            user.put("user_name", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL3)));
            user.put("user_email", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL4)));
            user.put("user_type", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL5)));

            Log.i("ADDED TO DATABASE",  cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL3))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL4))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL5)));

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