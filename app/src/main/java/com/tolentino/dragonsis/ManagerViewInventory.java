package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

public class ManagerViewInventory extends AppCompatActivity {

    ImageView img_back_view_inventory;
    ImageView img_add_inventory;
    SearchView srch_inventory;
    Spinner spin_view_inventory;
    Spinner spin_sort_inventory;

    DbManager db;
    ListView list_inventory;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inventory);

        img_back_view_inventory = findViewById(R.id.img_back_view_inventory);
        img_add_inventory = findViewById(R.id.img_add_inventory);
        srch_inventory = findViewById(R.id.srch_inventory);
        spin_sort_inventory = findViewById(R.id.spin_sort_inventory);
        spin_view_inventory = findViewById(R.id.spin_view_inventory);

        //back to main menu
        img_back_view_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerViewInventory.this, ManagerMenu.class);
                startActivity(i);
            }
        });

        //redirect to Add Inventory Activity
        img_add_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerViewInventory.this, ManagerAddInventory.class);
                startActivity(i);

            }
        });

        //Adapt Inventory List
        list_inventory = findViewById(R.id.list_inventory);
        ArrayList<HashMap<String, String>> inventorylist = db.getInventory();
        listAdapter = new SimpleAdapter(ManagerViewInventory.this, inventorylist, R.layout.list_row_inventory, new String[]{"inventory_ID","prod_name","inventory_date","inventory_quantity"}, new int[]{R.id.row_inventory_product_ID, R.id.row_inventory_name, R.id.row_inventory_date, R.id.row_inventory_quantity});
        list_inventory.setAdapter(listAdapter);


    }
}