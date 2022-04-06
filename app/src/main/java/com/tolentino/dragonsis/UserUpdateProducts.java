package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class UserUpdateProducts extends AppCompatActivity {

    ImageView img_back_upd_products;
    TextView txt_upd_prod_id;
    EditText edit_upd_prod_name;
    TextView txt_upd_prod_total_quantity;
    EditText edit_upd_prod_critnum;
    EditText edit_upd_prod_price;
    Spinner spin_upd_prod_category;
    String[] productCategoryArray = {"Lumber", "Nails", "Screws", "Cement", "Gravel", "Sand", "Steel Bars", "Varnish", "Paint", "Brush/Roller", "PVC", "Special"};
    Button btn_upd_prod_update;
    Button btn_upd_prod_remove;
    SharedPreferences pref;
    SharedPreferences user_pref;
    DbManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_products);

        img_back_upd_products = findViewById(R.id.img_back_upd_products);
        txt_upd_prod_id = findViewById(R.id.txt_upd_prod_id);
        edit_upd_prod_name = findViewById(R.id.edit_upd_prod_name);
        txt_upd_prod_total_quantity = findViewById(R.id.txt_upd_prod_total_quantity);
        edit_upd_prod_critnum = findViewById(R.id.edit_upd_prod_critnum);
        edit_upd_prod_price = findViewById(R.id.edit_upd_prod_price);
        spin_upd_prod_category = findViewById(R.id.spin_upd_prod_category);
        btn_upd_prod_update = findViewById(R.id.btn_upd_prod_update);
        btn_upd_prod_remove = findViewById(R.id.btn_upd_prod_remove);

        db = new DbManager(this);
        //Get Data passed from previous activity
        pref = getSharedPreferences("product_list", MODE_PRIVATE);
        user_pref = getSharedPreferences("acc_details", MODE_PRIVATE);
        Log.i("Product Shared Pref", pref.getString("prod_id", null) + ", " + pref.getString("prod_name", null) + ", " + pref.getString("prod_description", null) + ", " + pref.getString("prod_critical_num", null) + ", " + pref.getString("prod_price", null) + ", " + pref.getString("prod_category", null));

        //Back to Manager View Products
        img_back_upd_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user_pref.getString("user_type", null).equals("Manager")){
                    Intent i = new Intent(UserUpdateProducts.this, ManagerViewProducts.class);
                    Intent endActivity = new Intent("finish_activity_man_view_products");
                    sendBroadcast(endActivity);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(UserUpdateProducts.this, EmployeeViewProducts.class);
                    Intent endActivity = new Intent("finish_activity_man_view_products");
                    sendBroadcast(endActivity);
                    startActivity(i);
                    finish();
                }
            }
        });

        //Set Values in EditTexts

        txt_upd_prod_id.setText("Product ID: " + pref.getString("prod_id", null));
        edit_upd_prod_name.setText(pref.getString("prod_name", null));
        txt_upd_prod_total_quantity.setText(pref.getString("prod_total_quantity", null));
        edit_upd_prod_price.setText(pref.getString("prod_price", null));
        edit_upd_prod_critnum.setText(pref.getString("prod_critical_num", null));

        //Adapter for the user type spinner
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, productCategoryArray);
        spin_upd_prod_category.setAdapter(adapter);

        //Selects default Category upon entering the activity
        if(pref.getString("prod_category", null).equals("Lumber")){
            spin_upd_prod_category.setSelection(0);
        }else if(pref.getString("prod_category", null).equals("Nails")){
            spin_upd_prod_category.setSelection(1);
        }else if(pref.getString("prod_category", null).equals("Screws")){
            spin_upd_prod_category.setSelection(2);
        }else if(pref.getString("prod_category", null).equals("Cement")){
            spin_upd_prod_category.setSelection(3);
        }else if(pref.getString("prod_category", null).equals("Gravel")){
            spin_upd_prod_category.setSelection(4);
        }else if(pref.getString("prod_category", null).equals("Sand")){
            spin_upd_prod_category.setSelection(5);
        }else if(pref.getString("prod_category", null).equals("Steel Bars")){
            spin_upd_prod_category.setSelection(6);
        }else if(pref.getString("prod_category", null).equals("Varnish")){
            spin_upd_prod_category.setSelection(7);
        }else if(pref.getString("prod_category", null).equals("Paint")){
            spin_upd_prod_category.setSelection(8);
        }else if(pref.getString("prod_category", null).equals("Brush/Roller")){
            spin_upd_prod_category.setSelection(9);
        }else if(pref.getString("prod_category", null).equals("PVC")){
            spin_upd_prod_category.setSelection(10);
        }else if(pref.getString("prod_category", null).equals("Special")){
            spin_upd_prod_category.setSelection(11);
        }

        //Removes Product from Database
        btn_upd_prod_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteProduct(pref.getString("prod_id", null));
                //Log.i("PRODUCT TABLE", pref.getString("prod_id", null));
                Log.i("PRODUCT TABLE:", "Successfully deleted Product");
                Intent i = new Intent(UserUpdateProducts.this, ManagerViewProducts.class);
                Intent endActivity = new Intent("finish_activity_man_view_products");
                sendBroadcast(endActivity);
                startActivity(i);
                finish();
            }
        });

        //Updates the information in the database using the info in this activity
        btn_upd_prod_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Add Values in input objects into String
                String prod_id = pref.getString("prod_id", null);
                String prod_name = edit_upd_prod_name.getText().toString();
                String prod_total_quantity = txt_upd_prod_total_quantity.getText().toString();
                String prod_critnum = edit_upd_prod_critnum.getText().toString();
                String prod_price = edit_upd_prod_price.getText().toString();
                String prod_category = spin_upd_prod_category.getSelectedItem().toString();

                db.updateProduct(prod_id, prod_name, prod_total_quantity, prod_critnum, prod_price, prod_category);
                Intent i = new Intent(UserUpdateProducts.this, ManagerViewProducts.class);
                Intent endActivity = new Intent("finish_activity_man_view_products");
                sendBroadcast(endActivity);
                startActivity(i);
                finish();
            }
        });


    }
}