package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class UserAddInventory extends AppCompatActivity {

    ImageView img_back_add_inventory;
    Spinner spin_add_inventory_productname;
    EditText edit_add_date;
    EditText edit_inv_quantity;
    Button btn_inv_submit;
    DbManager db;
    SharedPreferences user_pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_inventory);

        img_back_add_inventory = findViewById(R.id.img_back_add_inventory);
        spin_add_inventory_productname = findViewById(R.id.spin_add_inventory_productname);
        edit_add_date = findViewById(R.id.edit_add_date);
        edit_inv_quantity = findViewById(R.id.edit_inv_quantity);
        btn_inv_submit = findViewById(R.id.btn_inv_submit);
        db = new DbManager(this);
        user_pref = getSharedPreferences("acc_details", MODE_PRIVATE);


        //Back to Respective Parent Class
        img_back_add_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*if(user_pref.getString("user_type", null).equals("Manager")){
                    Intent i = new Intent(UserAddInventory.this, ManagerViewInventory.class);
                    Intent endActivity = new Intent("finish_activity_man_view_inventory");
                    sendBroadcast(endActivity);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(UserAddInventory.this, EmployeeViewInventory.class);
                    Intent endActivity = new Intent("finish_activity_emp_view_inventory");
                    sendBroadcast(endActivity);
                    startActivity(i);
                    finish();
                }*/

                finish();
            }
        });

        //Adapter for Product Name Spinner
        ArrayList<String> prod_name_spinner;
        prod_name_spinner = db.getProductsName();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, prod_name_spinner);
        spin_add_inventory_productname.setAdapter(adapter);



        //Add Inventory
        btn_inv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Convert EditText Values to String
                String productDate = edit_add_date.getText().toString();
                String spin_prodName = spin_add_inventory_productname.getSelectedItem().toString();
                int invQuantity = -1;

                if(edit_inv_quantity.getText().toString().equals("")){
                    Toast.makeText(UserAddInventory.this, "Please make sure to input in all fields.", Toast.LENGTH_SHORT).show();
                }else{
                    invQuantity = Integer.parseInt(edit_inv_quantity.getText().toString());
                }


                if (TextUtils.isEmpty(productDate) || TextUtils.isEmpty(spin_prodName) || invQuantity == -1){
                    Toast.makeText(UserAddInventory.this, "Please make sure to input in all fields.", Toast.LENGTH_SHORT).show();
                }else {

                    //Use db function to add record to inventory table
                    db.insertInventory(spin_prodName, productDate, invQuantity);
                    db.addProductTotalQuant(spin_prodName, invQuantity);
                    Log.i("INVENTORY TABLE", "Inventory Inserted: " + spin_prodName + ", " + productDate + ", " + invQuantity);
                }
                //Back to View Inventory
                if(user_pref.getString("user_type", null).equals("Manager")){
                    Intent i = new Intent(UserAddInventory.this, ManagerViewInventory.class);
                    Intent endActivity = new Intent("finish_activity_man_view_inventory");
                    sendBroadcast(endActivity);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(UserAddInventory.this, EmployeeViewInventory.class);
                    Intent endActivity = new Intent("finish_activity_emp_view_inventory");
                    sendBroadcast(endActivity);
                    startActivity(i);
                    finish();
                }
            }
        });

    }


}