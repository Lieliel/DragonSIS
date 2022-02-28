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
                + PROD_COL3 + " INTEGER,"
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
        cValues.put(ACC_COL3, user_type);
        cValues.put(ACC_COL4, user_email);
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
    void insertProduct(String prod_name, int prod_critical_num, String prod_description, int prod_price, String prod_category) {
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


    // Adding new Inventory
    void insertUser(Integer inventory_ID, String inventory_date, Integer inventory_quantity, Integer inventory_quantity_change, String inventory_remark, String inventory_date_updated, String prod_name) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(INV_COL1, inventory_ID);
        cValues.put(INV_COL2, inventory_date);
        cValues.put(INV_COL3, inventory_quantity);
        cValues.put(INV_COL4, inventory_quantity_change);
        cValues.put(INV_COL5, inventory_remark);
        cValues.put(INV_COL6, inventory_date_updated);
        cValues.put(INV_COL7, prod_name);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(INV_TABLE_NAME, null, cValues);

        if(newRowId == 1){
            Log.i("INVENTORY TABLE:", "Inventory Added Correctly");
        }else{
            Log.i("INVENTORY TABLE:", "Inventory not Added Correctly");
        }

        db.close();
    }



    // Adding new User Details
    void insertUser(Integer sales_ID, Integer sales_amount, Integer items_sold, String sales_dates, String sales_time) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(SALES_COL1, sales_ID);
        cValues.put(SALES_COL2, sales_amount);
        cValues.put(SALES_COL3, items_sold);
        cValues.put(SALES_COL4, sales_dates);
        cValues.put(SALES_COL5, sales_time);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(SALES_TABLE_NAME, null, cValues);

        if(newRowId == 1){
            Log.i("SALES TABLE:", "Sales Added Correctly");
        }else{
            Log.i("SALES TABLE:", "Sales not Added Correctly");
        }

        db.close();
    }


    // Adding new Inventory History
    void insertUser(Integer update_ID, String inventory_update_date, String inventory_action, Integer inventory_quantity_change, Integer inventory_ID, String inventory_name) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(INV_HIS_COL1, inventory_ID);
        cValues.put(INV_HIS_COL2, inventory_update_date);
        cValues.put(INV_HIS_COL3, inventory_action);
        cValues.put(INV_HIS_COL4, inventory_quantity_change);
        cValues.put(INV_HIS_COL5, inventory_ID);
        cValues.put(INV_HIS_COL6, inventory_name);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(INV_HIS_TABLE_NAME, null, cValues);

        if(newRowId == 1){
            Log.i("INVENTORYHISTORY TABLE:", "Inventory History Added Correctly");
        }else{
            Log.i("INVENTORYHISTORY TABLE:", "Inventory History not Added Correctly");
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
            users.put("user_type", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL3)));
            users.put("user_email", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL4)));
            userList.add(users);

            Log.i("ADDED TO DATABASE",  cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL3))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL4)));
        }
        return userList;
    }


    // Get All Product Details
    public ArrayList<HashMap<String, String>> getProducts() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> productList = new ArrayList<>();
        String query = "SELECT * FROM " + PROD_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> products = new HashMap<>();
            products.put("prod_ID", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL1)));
            products.put("prod_name", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL2)));
            products.put("prod_critical_num", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL3)));
            products.put("prod_description", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL4)));
            products.put("prod_price", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL5)));
            products.put("prod_category", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL6)));
            productList.add(products);

            Log.i("ADDED TO DATABASE",  cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL3))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL4))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL5))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL6)));
        }
        return productList;
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
            user.put("user_type", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL3)));
            user.put("user_email", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL4)));

            Log.i("ADDED TO DATABASE",  cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL3))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL4)));

            userList.add(user);
        }
        return userList;
    }


    // Get Product Details based on Product ID
    public ArrayList<HashMap<String, String>> getProductByProductID(String productID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> productList = new ArrayList<>();
        String query = "SELECT * FROM " + PROD_TABLE_NAME;
        Cursor cursor = db.query(PROD_TABLE_NAME, new String[]{PROD_COL1, PROD_COL2, PROD_COL3, PROD_COL4, PROD_COL5, PROD_COL6}, PROD_COL1 + "=?", new String[]{String.valueOf(productID)}, null, null, null, null);
        if (cursor.moveToNext()) {
            HashMap<String, String> product = new HashMap<>();
            product.put("prod_ID", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL1)));
            product.put("prod_name", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL2)));
            product.put("prod_critical_num", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL3)));
            product.put("prod_description", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL4)));
            product.put("prod_price", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL5)));
            product.put("prod_category", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL6)));

            Log.i("ADDED TO DATABASE",  cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL3))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL4)));

            productList.add(product);
        }
        return productList;
    }


    // Delete User Details
    public void deleteUser(String user_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ACC_TABLE_NAME, ACC_COL1 + " = ?", new String[]{String.valueOf(user_name)});
        db.close();
    }


    // Delete Product Details
    public void deleteProduct(String prod_ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PROD_TABLE_NAME, PROD_COL1 + " = ?", new String[]{String.valueOf(prod_ID)});
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