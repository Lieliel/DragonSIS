package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Collections;

public class EmployeeInventoryUpdates extends AppCompatActivity {

    ImageView img_back_emp_inv_upd;
    SearchView srch_emp_inv_upd;
    ListView list_emp_inv_upd;
    DbManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_inventory_updates);

        img_back_emp_inv_upd = findViewById(R.id.img_back_emp_inv_upd);
        srch_emp_inv_upd = findViewById(R.id.srch_emp_inv_upd);
        db = new DbManager(this);

        //Back to Employee Menu
        img_back_emp_inv_upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Adapt Inventory Updates List
        list_emp_inv_upd = findViewById(R.id.list_emp_inv_upd);
        ArrayList<String> inventoryHislist = db.getInvMessage();
        Collections.reverse(inventoryHislist);
        ArrayAdapter listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, inventoryHislist);
        list_emp_inv_upd.setAdapter(listAdapter);

        //Search Inventory Updates Function
        srch_emp_inv_upd.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                listAdapter.getFilter().filter(s);
                return false;
            }
        });

    }
}