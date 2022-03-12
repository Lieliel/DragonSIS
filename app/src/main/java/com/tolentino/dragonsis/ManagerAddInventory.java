package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

public class ManagerAddInventory extends AppCompatActivity {

    ImageView img_back_add_inventory;
    Spinner spin_add_inventory_productname;
    EditText edit_add_date;
    EditText edit_inv_quantity;
    Button btn_inv_submit;
    DbManager db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_add_inventory);

        img_back_add_inventory = findViewById(R.id.img_back_add_inventory);
        spin_add_inventory_productname = findViewById(R.id.spin_add_inventory_productname);
        edit_add_date = findViewById(R.id.edit_add_date);
        edit_inv_quantity = findViewById(R.id.edit_inv_quantity);
        btn_inv_submit = findViewById(R.id.btn_inv_submit);
        db = new DbManager(this);


        //Back to Manager View Inventory
        img_back_add_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerAddInventory.this, ManagerViewInventory.class);
                startActivity(i);
            }
        });

        //Adapter for Product Name Spinner
        ArrayList<String> prod_name_spinner;
        prod_name_spinner = db.getProductsName();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, prod_name_spinner);
        spin_add_inventory_productname.setAdapter(adapter);

        btn_inv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Convert to EditText Values to String
                String productDate = edit_add_date.getText().toString();
                String spin_prodName = spin_add_inventory_productname.getSelectedItem().toString();
                int invQuantity = Integer.parseInt(edit_inv_quantity.getText().toString());

                //Use db function to add record to products table
                db.insertInventory(spin_prodName, productDate, invQuantity);
                Log.i("INVENTORY TABLE", "Inventory Inserted: " + spin_prodName + ", " + productDate + ", " + invQuantity);

                Intent i = new Intent(ManagerAddInventory.this, ManagerViewInventory.class);
                startActivity(i);
            }
        });

    }


}