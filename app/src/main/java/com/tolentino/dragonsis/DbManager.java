package com.tolentino.dragonsis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DbManager extends SQLiteOpenHelper {

    private static final String TAG = "DbManager";

    //Accounts
    private static final String ACC_TABLE_NAME = "accounts_table";
    private static final String ACC_COL1 = "user_ID";
    private static final String ACC_COL2 = "user_name";
    private static final String ACC_COL3 = "user_password";
    private static final String ACC_COL4 = "user_type";

    //Products
    private static final String PROD_TABLE_NAME = "products_table";
    private static final String PROD_COL1 = "prod_ID";
    private static final String PROD_COL2 = "prod_name";
    private static final String PROD_COL3 = "prod_critical_num";
    private static final String PROD_COL4 = "prod_total_quantity";
    private static final String PROD_COL5 = "prod_price";
    private static final String PROD_COL6 = "prod_category";

    //Inventory
    private static final String INV_TABLE_NAME = "inventory_table";
    private static final String INV_COL1 = "inventory_ID";
    private static final String INV_COL2 = "inventory_date"; //kung kelan stock in
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
    private static final String INV_HIS_COL7 = "inventory_update_time";


    //Sales History
    private static final String SALES_TABLE_NAME = "sales_table";
    private static final String SALES_COL1 = "sales_ID";
    private static final String SALES_COL2 = "sales_amount";
    private static final String SALES_COL3 = "items_sold";
    private static final String SALES_COL4 = "sales_dates";
    private static final String SALES_COL5 = "sales_time";
    private static final String SALES_COL6 = "product_sold";

    public DbManager(Context context) {
        super(context, ACC_TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Users Table
        String create_Acc_Table = "CREATE TABLE " + ACC_TABLE_NAME + "("
                + ACC_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ACC_COL2 + " TEXT,"
                + ACC_COL3 + " TEXT,"
                + ACC_COL4 + " TEXT)";
        db.execSQL(create_Acc_Table);

        //create Products Table
        String create_Prod_Table = "CREATE TABLE " + PROD_TABLE_NAME + "("
                + PROD_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PROD_COL2 + " TEXT,"
                + PROD_COL3 + " INTEGER,"
                + PROD_COL4 + " INTEGER,"
                + PROD_COL5 + " FLOAT,"
                + PROD_COL6 + " TEXT)";
        db.execSQL(create_Prod_Table);

        //create Inventory Table
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
                + SALES_COL2 + " FLOAT,"
                + SALES_COL3 + " TEXT,"
                + SALES_COL4 + " TEXT,"
                + SALES_COL5 + " TEXT,"
                + SALES_COL6 + " TEXT)";
        db.execSQL(create_Sales_Table);

        //create_InvHis_Table
        String create_Inv_His_Table = "CREATE TABLE " + INV_HIS_TABLE_NAME + "("
                + INV_HIS_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + INV_HIS_COL2 + " TEXT,"
                + INV_HIS_COL3 + " TEXT,"
                + INV_HIS_COL4 + " INTEGER,"
                + INV_HIS_COL5 + " TEXT,"
                + INV_HIS_COL6 + " TEXT,"
                + INV_HIS_COL7 + " TEXT)";
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


    ////////////////////////////////////////////////////////////////////
    //////////////////////// USER FUNCTIONS ////////////////////////////
    ////////////////////////////////////////////////////////////////////

    // Adding new User Details
    void insertUser(String user_password, String user_name, String user_type) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(ACC_COL2, user_name);
        cValues.put(ACC_COL3, user_password);
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


    // Update User Details
    void updateUser(String username, String password, String usertype, String origUser){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String ID = getUserByUsername(origUser).get(0).get("user_ID");
        values.put(ACC_COL2, username);
        values.put(ACC_COL3, password);
        values.put(ACC_COL4, usertype);

        //Cursor cursor = db.rawQuery("Select * from " + ACC_TABLE_NAME + " where " + ACC_COL1 + "= ?",new String[]{username});

        long newRowId = db.update(ACC_TABLE_NAME, values, ACC_COL1 + "=?", new String[]{ID});

        if(newRowId == 1){
            Log.i("ACCOUNT TABLE", "User Updated Correctly");
        }else{
            Log.i("ACCOUNT TABLE", "User not Updated Correctly");
        }

    }


    // Get All User Details
    public ArrayList<HashMap<String, String>> getUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM " + ACC_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> users = new HashMap<>();
            users.put("user_name", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL2)));
            users.put("user_password", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL3)));
            users.put("user_type", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL4)));
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
        Cursor cursor = db.query(ACC_TABLE_NAME, new String[]{ACC_COL1,ACC_COL2, ACC_COL3, ACC_COL4}, ACC_COL2 + "=?", new String[]{String.valueOf(username)}, null, null, null, null);
        if (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("user_ID", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL1)));
            user.put("user_name", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL2)));
            user.put("user_password", cursor.getString(cursor.getColumnIndexOrThrow(ACC_COL3)));
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
        db.delete(ACC_TABLE_NAME, ACC_COL2 + " = ?", new String[]{String.valueOf(user_name)});
        db.close();
    }

    ////////////////////////////////////////////////////////////////////
    ///////////////////// PRODUCT FUNCTIONS ////////////////////////////
    ////////////////////////////////////////////////////////////////////


    // Adding New Product Details
    void insertProduct(String prod_name, int prod_total_quantity, int prod_critical_num, int prod_price, String prod_category) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(PROD_COL2, prod_name);
        cValues.put(PROD_COL3, prod_critical_num);
        cValues.put(PROD_COL4, prod_total_quantity);
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
            products.put("prod_total_quantity", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL4)));
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

    // Get All Product Details
    public ArrayList<HashMap<String, String>> getCategorizedProducts(String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> productList = new ArrayList<>();
        String query = "SELECT * FROM " + PROD_TABLE_NAME + " WHERE " + PROD_COL6 + "='" + category + "'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> products = new HashMap<>();
            products.put("prod_ID", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL1)));
            products.put("prod_name", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL2)));
            products.put("prod_critical_num", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL3)));
            products.put("prod_total_quantity", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL4)));
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

    //Function to add Products List to Inventory Spinner
    public ArrayList<String> getProductsName() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> productNameList = new ArrayList<String>();
        String query = "SELECT * FROM " + PROD_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                productNameList.add(cursor.getString(1));
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }

        return productNameList;

    }


    // Get Product Details based on Product ID
    public ArrayList<HashMap<String, String>> getProductByProductID(String productID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> productList = new ArrayList<>();
        String query = "SELECT * FROM " + PROD_TABLE_NAME;
        Cursor cursor = db.query(PROD_TABLE_NAME, new String[]{PROD_COL1, PROD_COL2, PROD_COL3, PROD_COL4, PROD_COL5, PROD_COL6}, PROD_COL1 + "=?", new String[]{String.valueOf(productID)}, null, null, null, null);
        if (cursor.moveToNext()) {
            HashMap<String, String> product = new HashMap<>();
            product.put("prod_id", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL1)));
            product.put("prod_name", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL2)));
            product.put("prod_critical_num", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL3)));
            product.put("prod_total_quantity", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL4)));
            product.put("prod_price", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL5)));
            product.put("prod_category", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL6)));

            Log.i("ADDED TO DATABASE",  cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL4))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL5))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL6)));

            productList.add(product);
        }
        return productList;
    }

    // Get Product Details based on Product Name
    public ArrayList<HashMap<String, String>> getProductByProductName(String product_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> productList = new ArrayList<>();
        String query = "SELECT * FROM " + PROD_TABLE_NAME;
        Cursor cursor = db.query(PROD_TABLE_NAME, new String[]{PROD_COL1, PROD_COL2, PROD_COL3, PROD_COL4, PROD_COL5, PROD_COL6}, PROD_COL2 + "=?", new String[]{String.valueOf(product_name)}, null, null, null, null);
        if (cursor.moveToNext()) {
            HashMap<String, String> product = new HashMap<>();
            product.put("prod_id", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL1)));
            product.put("prod_name", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL2)));
            product.put("prod_critical_num", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL3)));
            product.put("prod_total_quantity", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL4)));
            product.put("prod_price", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL5)));
            product.put("prod_category", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL6)));

            Log.i("ADDED TO DATABASE",  cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL4))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL5))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL6)));

            productList.add(product);
        }
        return productList;
    }

    // Get Sorted Products Details
    public ArrayList<HashMap<String, String>> getSortedProduct(String sort) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> productList = new ArrayList<>();
        String inv_sort_query_ext = null;

        //SQL query according to kung ano yung gusto ni user na sort, naubusan ako ng english
        if(sort.equals("A-Z")){
            inv_sort_query_ext = " ORDER BY " + PROD_TABLE_NAME + "." + PROD_COL2;
        }else if(sort.equals("Z-A")){
            inv_sort_query_ext = " ORDER BY " + PROD_TABLE_NAME + "." + PROD_COL2 + " DESC";
        }else if(sort.equals("Low Quantity First")){
            inv_sort_query_ext = " ORDER BY " + PROD_TABLE_NAME + "." + PROD_COL4;
        }else if(sort.equals("High Quantity First")){
            inv_sort_query_ext = " ORDER BY " + PROD_TABLE_NAME + "." + PROD_COL4 + " DESC";
        }else if(sort.equals("None")){
            inv_sort_query_ext = "";
        }

        String query = "SELECT * FROM " + PROD_TABLE_NAME + inv_sort_query_ext;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> products = new HashMap<>();
            products.put("prod_ID", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL1)));
            products.put("prod_name", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL2)));
            products.put("prod_critical_num", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL3)));
            products.put("prod_total_quantity", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL4)));
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

    // Get Categorized Inventory Details
    public ArrayList<HashMap<String, String>> getCategorizedProduct(String category, String sort) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> productList = new ArrayList<>();
        String inv_sort_query_ext = null;

        //SQL query according to kung ano yung gusto ni user na sort, naubusan ako ng english
        if(sort.equals("A-Z")){
            inv_sort_query_ext = " ORDER BY " + PROD_TABLE_NAME + "." + PROD_COL2;
        }else if(sort.equals("Z-A")){
            inv_sort_query_ext = " ORDER BY " + PROD_TABLE_NAME + "." + PROD_COL2 + " DESC";
        }else if(sort.equals("Low Quantity First")){
            inv_sort_query_ext = " ORDER BY " + PROD_TABLE_NAME + "." + PROD_COL4;
        }else if(sort.equals("High Quantity First")){
            inv_sort_query_ext = " ORDER BY " + PROD_TABLE_NAME + "." + PROD_COL4 + " DESC";
        }else if(sort.equals("None")){
            inv_sort_query_ext = "";
        }

        String query = "SELECT * FROM " + PROD_TABLE_NAME + " WHERE " + PROD_COL6 + "='" + category + "'" + inv_sort_query_ext;;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> products = new HashMap<>();
            products.put("prod_ID", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL1)));
            products.put("prod_name", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL2)));
            products.put("prod_critical_num", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL3)));
            products.put("prod_total_quantity", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL4)));
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


    // Delete Product Details
    public void deleteProduct(String prod_ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PROD_TABLE_NAME, PROD_COL1 + " = ?", new String[]{String.valueOf(prod_ID)});
        db.close();
    }

    // Get Searched Product Details
    public ArrayList<HashMap<String, String>> getSearchedProduct(String q) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> productList = new ArrayList<>();

        String query = "SELECT * FROM " + PROD_TABLE_NAME + " WHERE " + PROD_COL2 + " LIKE '%" + q + "%'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> products = new HashMap<>();
            products.put("prod_ID", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL1)));
            products.put("prod_name", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL2)));
            products.put("prod_critical_num", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL3)));
            products.put("prod_total_quantity", cursor.getString(cursor.getColumnIndexOrThrow(PROD_COL4)));
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

    boolean checkProductCritical(String prod){
        int prod_total_quant = Integer.parseInt(getProductByProductName(prod).get(0).get("prod_total_quantity"));
        int prod_crit_num = Integer.parseInt(getProductByProductName(prod).get(0).get("prod_critical_num"));

        //return true if product critical
        if(prod_total_quant <= prod_crit_num){
            return true;
        }else{
            return false;
        }

    }

    // Update Product Details
    void updateProduct(String prod_id, String prod_name, String prod_total_quantity, String prod_critnum, String prod_price, String prod_category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROD_COL2, prod_name);
        values.put(PROD_COL3, prod_critnum);
        values.put(PROD_COL4, prod_total_quantity);
        values.put(PROD_COL5, prod_price);
        values.put(PROD_COL6, prod_category);

        //Cursor cursor = db.rawQuery("Select * from " + ACC_TABLE_NAME + " where " + ACC_COL1 + "= ?",new String[]{username});

        long newRowId = db.update(PROD_TABLE_NAME, values, PROD_COL1 + "=?", new String[]{prod_id});

        if(newRowId == 1){
            Log.i("PRODUCT TABLE:", "Product Updated Correctly");
        }else{
            Log.i("PRODUCT TABLE:", "Product not Updated Correctly");
        }

    }

    // Subtract Product Total Quantity when Inventory changes are made
    void subtractProductTotalQuant(String prod_name, int prod_quantity_change){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROD_COL2, prod_name);
        int prod_curr_total_prod_quantity = Integer.parseInt(getProductByProductName(prod_name).get(0).get("prod_total_quantity"));
        int prod_new_total_prod_quantity = prod_curr_total_prod_quantity - prod_quantity_change;
        values.put(PROD_COL4, prod_new_total_prod_quantity);

        //Cursor cursor = db.rawQuery("Select * from " + ACC_TABLE_NAME + " where " + ACC_COL1 + "= ?",new String[]{username});

        long newRowId = db.update(PROD_TABLE_NAME, values, PROD_COL2 + "=?", new String[]{prod_name});

        if(newRowId == 1){
            Log.i("PRODUCT TABLE:", "Product Quantity Updated Correctly");
        }else{
            Log.i("PRODUCT TABLE:", "Product Quantity not Updated Correctly");
        }

    }

    // Add Product Total Quantity when Inventory changes are made
    void addProductTotalQuant(String prod_name, int prod_quantity_change){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROD_COL2, prod_name);
        int prod_curr_total_prod_quantity = Integer.parseInt(getProductByProductName(prod_name).get(0).get("prod_total_quantity"));
        int prod_new_total_prod_quantity = prod_curr_total_prod_quantity + prod_quantity_change;
        values.put(PROD_COL4, prod_new_total_prod_quantity);

        //Cursor cursor = db.rawQuery("Select * from " + ACC_TABLE_NAME + " where " + ACC_COL1 + "= ?",new String[]{username});

        long newRowId = db.update(PROD_TABLE_NAME, values, PROD_COL2 + "=?", new String[]{prod_name});

        if(newRowId == 1){
            Log.i("PRODUCT TABLE:", "Product Quantity Updated Correctly");
        }else{
            Log.i("PRODUCT TABLE:", "Product Quantity not Updated Correctly");
        }

    }

    ////////////////////////////////////////////////////////////////////
    ///////////////////// INVENTORY FUNCTIONS //////////////////////////
    ////////////////////////////////////////////////////////////////////

    // Adding new Inventory
    void insertInventory(String prod_name, String inventory_date, int inventory_quantity) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        //cValues.put(INV_COL1, inventory_ID);
        cValues.put(INV_COL2, inventory_date);
        cValues.put(INV_COL3, inventory_quantity);
        //cValues.put(INV_COL4, inventory_quantity_change);
        //cValues.put(INV_COL5, inventory_remark);
        //cValues.put(INV_COL6, inventory_date_updated);
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

    // Update Inventory Details
    void updateInventory(String inv_id, String inv_quantity, String inv_remark, String prod_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(INV_COL3, inv_quantity);
        values.put(INV_COL5, inv_remark);
        values.put(INV_COL7, prod_name);

        //Cursor cursor = db.rawQuery("Select * from " + ACC_TABLE_NAME + " where " + ACC_COL1 + "= ?",new String[]{username});

        long newRowId = db.update(INV_TABLE_NAME, values, INV_COL1 + "=?", new String[]{inv_id});

        if(newRowId == 1){
            Log.i("INVENTORY TABLE:", "Product Updated Correctly");
        }else{
            Log.i("INVENTORY TABLE:", "Product not Updated Correctly");
        }

    }

    // Get Categorized Inventory Details
    public ArrayList<HashMap<String, String>> getCategorizedInventory(String category, String sort) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> inventoryList = new ArrayList<>();
        String inv_sort_query_ext = null;

        //SQL query according to kung ano yung gusto ni user na sort, naubusan ako ng english
        if(sort.equals("A-Z")){
            inv_sort_query_ext = " ORDER BY " + INV_TABLE_NAME + "." + INV_COL7;
        }else if(sort.equals("Z-A")){
            inv_sort_query_ext = " ORDER BY " + INV_TABLE_NAME + "." + INV_COL7 + " DESC";
        }else if(sort.equals("Low Quantity First")){
            inv_sort_query_ext = " ORDER BY " + INV_TABLE_NAME + "." + INV_COL3;
        }else if(sort.equals("High Quantity First")){
            inv_sort_query_ext = " ORDER BY " + INV_TABLE_NAME + "." + INV_COL3 + " DESC";
        }else if(sort.equals("None")){
            inv_sort_query_ext = "";
        }

        String query = "SELECT "
                + INV_TABLE_NAME + "." + INV_COL1 + ", "
                + INV_TABLE_NAME + "." + INV_COL2 + ", "
                + INV_TABLE_NAME + "." + INV_COL3 + ", "
                + INV_TABLE_NAME + "." + INV_COL4 + ", "
                + INV_TABLE_NAME + "." + INV_COL5 + ", "
                + INV_TABLE_NAME + "." + INV_COL6 + ", "
                + INV_TABLE_NAME + "." + INV_COL7 + ", "
                + PROD_TABLE_NAME + "." + PROD_COL2 + ", "
                + PROD_TABLE_NAME + "." + PROD_COL6
                + " FROM " + INV_TABLE_NAME
                + " INNER JOIN " + PROD_TABLE_NAME + " ON "
                + INV_TABLE_NAME + "." + INV_COL7 + " = "
                + PROD_TABLE_NAME + "." + PROD_COL2
                + " WHERE " + PROD_TABLE_NAME + "." + PROD_COL6 + "='" + category + "'"
                + inv_sort_query_ext;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> inventory = new HashMap<>();
            inventory.put("inventory_ID", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL1)));
            inventory.put("inventory_date", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL2)));
            inventory.put("inventory_quantity", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL3)));
            inventory.put("inventory_quantity_change", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL4)));
            inventory.put("inventory_remark", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL5)));
            inventory.put("inventory_date_updated", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL6)));
            inventory.put("prod_name", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL7)));
            inventoryList.add(inventory);

            Log.i("ADDED TO INV TABLE",  cursor.getString(cursor.getColumnIndexOrThrow(INV_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL3))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL4))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL5))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL6))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL7)));
        }
        return inventoryList;
    }

    // Get Inventory Details
    public ArrayList<HashMap<String, String>> getInventory() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> inventoryList = new ArrayList<>();
        String query = "SELECT * FROM " + INV_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> inventory = new HashMap<>();
            inventory.put("inventory_ID", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL1)));
            inventory.put("inventory_date", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL2)));
            inventory.put("inventory_quantity", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL3)));
            inventory.put("inventory_quantity_change", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL4)));
            inventory.put("inventory_remark", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL5)));
            inventory.put("inventory_date_updated", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL6)));
            inventory.put("prod_name", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL7)));
            inventoryList.add(inventory);

            Log.i("ADDED TO INV TABLE",  cursor.getString(cursor.getColumnIndexOrThrow(INV_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL3))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL4))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL5))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL6))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL7)));
        }
        return inventoryList;
    }

    // Get Inventory Details
    public ArrayList<HashMap<String, String>> getSortedInventory(String sort) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> inventoryList = new ArrayList<>();
        String inv_sort_query_ext = null;
        //SQL query according to kung ano yung gusto ni user na sort, naubusan ako ng english
        if(sort.equals("A-Z")){
            inv_sort_query_ext = " ORDER BY " + INV_TABLE_NAME + "." + INV_COL7;
        }else if(sort.equals("Z-A")){
            inv_sort_query_ext = " ORDER BY " + INV_TABLE_NAME + "." + INV_COL7 + " DESC";
        }else if(sort.equals("Low Quantity First")){
            inv_sort_query_ext = " ORDER BY " + INV_TABLE_NAME + "." + INV_COL3;
        }else if(sort.equals("High Quantity First")){
            inv_sort_query_ext = " ORDER BY " + INV_TABLE_NAME + "." + INV_COL3 + " DESC";
        }else if(sort.equals("None")){
            inv_sort_query_ext = "";
        }
        String query = "SELECT * FROM " + INV_TABLE_NAME + inv_sort_query_ext;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> inventory = new HashMap<>();
            inventory.put("inventory_ID", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL1)));
            inventory.put("inventory_date", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL2)));
            inventory.put("inventory_quantity", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL3)));
            inventory.put("inventory_quantity_change", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL4)));
            inventory.put("inventory_remark", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL5)));
            inventory.put("inventory_date_updated", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL6)));
            inventory.put("prod_name", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL7)));
            inventoryList.add(inventory);

            Log.i("ADDED TO INV TABLE",  cursor.getString(cursor.getColumnIndexOrThrow(INV_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL3))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL4))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL5))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL6))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL7)));
        }
        return inventoryList;
    }

    // Get Inventory Details based on Inventory ID
    public ArrayList<HashMap<String, String>> getInventoryByInventoryID(String inventoryID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> inventoryList = new ArrayList<>();
        String query = "SELECT * FROM " + INV_TABLE_NAME;
        Cursor cursor = db.query(INV_TABLE_NAME, new String[]{INV_COL1, INV_COL2, INV_COL3, INV_COL4, INV_COL5, INV_COL6, INV_COL7}, INV_COL1 + "=?", new String[]{String.valueOf(inventoryID)}, null, null, null, null);
        if (cursor.moveToNext()) {
            HashMap<String, String> inventory = new HashMap<>();
            inventory.put("inventory_ID", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL1)));
            inventory.put("inventory_date", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL2)));
            inventory.put("inventory_quantity", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL3)));
            inventory.put("inventory_quantity_change", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL4)));
            inventory.put("inventory_remark", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL5)));
            inventory.put("inventory_date_updated", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL6)));
            inventory.put("prod_name", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL7)));

            Log.i("ADDED TO DATABASE",  cursor.getString(cursor.getColumnIndexOrThrow(INV_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL3))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL4))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL5))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL5))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL7)));

            inventoryList.add(inventory);
        }
        return inventoryList;
    }

    // Delete Inventory Details
    public void deleteInventory(String inventory_ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(INV_TABLE_NAME, INV_COL1 + " = ?", new String[]{String.valueOf(inventory_ID)});
        db.close();
    }

    // Get Searched Inventory Details
    public ArrayList<HashMap<String, String>> getSearchedInventory(String s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> inventoryList = new ArrayList<>();
        String inv_sort_query_ext = null;

        String query = "SELECT * FROM " + INV_TABLE_NAME + " WHERE " + INV_COL7 + " LIKE '%" + s + "%'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> inventory = new HashMap<>();
            inventory.put("inventory_ID", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL1)));
            inventory.put("inventory_date", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL2)));
            inventory.put("inventory_quantity", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL3)));
            inventory.put("inventory_quantity_change", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL4)));
            inventory.put("inventory_remark", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL5)));
            inventory.put("inventory_date_updated", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL6)));
            inventory.put("prod_name", cursor.getString(cursor.getColumnIndexOrThrow(INV_COL7)));
            inventoryList.add(inventory);

            Log.i("ADDED TO INV TABLE",  cursor.getString(cursor.getColumnIndexOrThrow(INV_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL3))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL4))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL5))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL6))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_COL7)));
        }
        return inventoryList;
    }

    ////////////////////////////////////////////////////////////////////
    ///////////////// INVENTORY HISTORY FUNCTIONS //////////////////////
    ////////////////////////////////////////////////////////////////////

    // Adding new Inventory History
    void insertInvHis(/*Integer update_ID, */String inventory_update_date, String inventory_action, Integer inventory_quantity_change,/* Integer inventory_ID, */String inventory_name, String inventory_update_time) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        //cValues.put(INV_HIS_COL1, inventory_ID);
        cValues.put(INV_HIS_COL2, inventory_update_date);
        cValues.put(INV_HIS_COL3, inventory_action);
        cValues.put(INV_HIS_COL4, inventory_quantity_change);
        //cValues.put(INV_HIS_COL5, inventory_ID);
        cValues.put(INV_HIS_COL6, inventory_name);
        cValues.put(INV_HIS_COL7, inventory_update_time);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(INV_HIS_TABLE_NAME, null, cValues);

        if(newRowId == 1){
            Log.i("INV HISTORY TABLE:", "Inventory History Added Correctly");
        }else{
            Log.i("INV HISTORY TABLE:", "Inventory History not Added Correctly");
        }

        db.close();
    }

    //Function to create Inventory Message ArrayList
    public ArrayList<String> getInvMessage() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> invMessageList = new ArrayList<String>();
        String query = "SELECT * FROM " + INV_HIS_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                invMessageList.add(cursor.getString(2) + cursor.getString(3) + " " + cursor.getString(5) + " on " + cursor.getString(1) + " at " + cursor.getString(6));
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }

        return invMessageList;

    }

    // Get All Inventory History Details
    public ArrayList<HashMap<String, String>> getInventoryHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> historyList = new ArrayList<>();
        String query = "SELECT * FROM " + INV_HIS_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> history = new HashMap<>();
            history.put("update_ID", cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL1)));
            history.put("inventory_update_date", cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL2)));
            history.put("inventory_action", cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL3)));
            history.put("inventory_quantity_change", cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL4)));
            history.put("inventory_ID", cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL5)));
            history.put("prod_name", cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL6)));
            history.put("inventory_update_time", cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL7)));
            historyList.add(history);

            Log.i("ADDED TO INVHIS TABLE",  cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL3))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL4))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL5))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL6)));
        }
        return historyList;
    }


    // Get Inventory History Details based on Update ID
    public ArrayList<HashMap<String, String>> getInventoryHistoryByUpdateID(String updateID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> historyList = new ArrayList<>();
        String query = "SELECT * FROM " + INV_HIS_TABLE_NAME;
        Cursor cursor = db.query(INV_HIS_TABLE_NAME, new String[]{INV_HIS_COL1, INV_HIS_COL2, INV_HIS_COL3, INV_HIS_COL4, INV_HIS_COL5, INV_HIS_COL6}, INV_HIS_COL1 + "=?", new String[]{String.valueOf(updateID)}, null, null, null, null);
        if (cursor.moveToNext()) {
            HashMap<String, String> history = new HashMap<>();
            history.put("inventory_ID", cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL1)));
            history.put("inventory_date", cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL2)));
            history.put("inventory_quantity", cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL3)));
            history.put("inventory_quantity_change", cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL4)));
            history.put("inventory_remark", cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL5)));
            history.put("inventory_date_updated", cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL6)));

            Log.i("ADDED TO DATABASE",  cursor.getString(cursor.getColumnIndexOrThrow(INV_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL3))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL4))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL5))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(INV_HIS_COL6)));

            historyList.add(history);
        }
        return historyList;
    }

    ////////////////////////////////////////////////////////////////////
    ///////////////////////// SALES FUNCTIONS //////////////////////////
    ////////////////////////////////////////////////////////////////////

    // Adding new Sales Details
    void insertSales(int sales_amount, int items_sold, String sales_dates, String sales_time, String product_sold) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        //cValues.put(SALES_COL1, sales_ID);
        cValues.put(SALES_COL2, sales_amount);
        cValues.put(SALES_COL3, items_sold);
        cValues.put(SALES_COL4, sales_dates);
        cValues.put(SALES_COL5, sales_time);
        cValues.put(SALES_COL6, product_sold);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(SALES_TABLE_NAME, null, cValues);

        if(newRowId == 1){
            Log.i("SALES TABLE:", "Sales Added Correctly");
        }else{
            Log.i("SALES TABLE:", "Sales not Added Correctly");
        }

        db.close();
    }

    //Function to create Inventory Message ArrayList
    public ArrayList<String> getSalesMessage() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> salesMessageList = new ArrayList<String>();
        String query = "SELECT * FROM " + SALES_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                salesMessageList.add("Sold " + cursor.getString(2) + " " + cursor.getString(5) + " for " + cursor.getString(1) + "php on " + cursor.getString(3) + " at " + cursor.getString(4));
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }

        return salesMessageList;
    }


    // Get All Sales Details
    public ArrayList<HashMap<String, String>> getSales() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> salesList = new ArrayList<>();
        String query = "SELECT * FROM " + SALES_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> sales = new HashMap<>();
            sales.put("sales_ID", cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL1)));
            sales.put("sales_amount", cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL2)));
            sales.put("items_sold", cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL3)));
            sales.put("sales_dates", cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL4)));
            sales.put("sales_time", cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL5)));
            sales.put("product_sold", cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL6)));
            salesList.add(sales);

            Log.i("ADDED TO DATABASE",  cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL3))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL4))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL5))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL6)));
        }
        return salesList;
    }

    // Get Sales Details based on Sales ID
    public ArrayList<HashMap<String, String>> getSalesBySalesID(String salesID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> salesList = new ArrayList<>();
        String query = "SELECT * FROM " + SALES_TABLE_NAME;
        Cursor cursor = db.query(SALES_TABLE_NAME, new String[]{SALES_COL1, SALES_COL2, SALES_COL3, SALES_COL4, SALES_COL5}, SALES_COL1 + "=?", new String[]{String.valueOf(salesID)}, null, null, null, null);
        if (cursor.moveToNext()) {
            HashMap<String, String> sales = new HashMap<>();
            sales.put("sales_ID", cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL1)));
            sales.put("sales_amount", cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL2)));
            sales.put("items_sold", cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL3)));
            sales.put("sales_dates", cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL4)));
            sales.put("sales_time", cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL5)));

            Log.i("ADDED TO DATABASE",  cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL1))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL2))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL3))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL4))
                    + " " + cursor.getString(cursor.getColumnIndexOrThrow(SALES_COL5)));

            salesList.add(sales);
        }
        return salesList;
    }

    // Delete Sales Details
    public void deleteSales(String sales_ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SALES_TABLE_NAME, SALES_COL1 + " = ?", new String[]{String.valueOf(sales_ID)});
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