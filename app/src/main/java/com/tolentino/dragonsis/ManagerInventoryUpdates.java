package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ManagerInventoryUpdates extends AppCompatActivity {

    ImageView img_back_man_view_inventory_updates;
    ListView list_man_inv_upd;
    SearchView srch_man_view_inv_upd;
    DbManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_view_inventory_updates);

        img_back_man_view_inventory_updates = findViewById(R.id.img_back_man_view_inventory_updates);
        srch_man_view_inv_upd = findViewById(R.id.srch_man_view_inv_upd);
        db = new DbManager(this);

        //Back to Manager Menu
        img_back_man_view_inventory_updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerInventoryUpdates.this, ManagerMenu.class);
                startActivity(i);
                Intent endActivity = new Intent("finish_activity");
                sendBroadcast(endActivity);
                finish();
            }
        });

        //Adapt Inventory Updates List 
        list_man_inv_upd = findViewById(R.id.list_man_inv_upd);
        ArrayList<String> inventoryHislist = db.getInvMessage();
        Collections.reverse(inventoryHislist);
        ArrayAdapter listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, inventoryHislist);
        list_man_inv_upd.setAdapter(listAdapter);

        //Search Inventory Updates Function
        srch_man_view_inv_upd.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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