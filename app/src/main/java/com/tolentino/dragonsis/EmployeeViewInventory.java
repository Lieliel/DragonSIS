package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeViewInventory extends AppCompatActivity {

    ImageView img_back_employee_inventory;
    Button img_add_inventory;
    SearchView search_inventory_emp;
    Spinner spin_view_inventory;
    Spinner spin_sort_inventory;

    DbManager db;
    ListView list_inventory_emp;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_view_inventory);

        img_back_employee_inventory = findViewById(R.id.img_back_employee_inventory);
        img_add_inventory = findViewById(R.id.img_add_inventory);
        search_inventory_emp = findViewById(R.id.search_inventory_emp);
        spin_sort_inventory = findViewById(R.id.spin_sort_inventory);
        spin_view_inventory = findViewById(R.id.spin_view_inventory);
        db = new DbManager(this);

        //back to main menu
        img_back_employee_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EmployeeViewInventory.this, EmployeeMenu.class);
                Intent endActivity = new Intent("emp_finish_activity");
                sendBroadcast(endActivity);
                startActivity(i);
                finish();
            }
        });

        //Adapt Inventory List
        list_inventory_emp = findViewById(R.id.list_emp_inventory);
        ArrayList<HashMap<String, String>> inventorylist = db.getInventory();
        listAdapter = new SimpleAdapter(EmployeeViewInventory.this, inventorylist, R.layout.list_row_inventory, new String[]{"inventory_ID","prod_name","inventory_date","inventory_quantity"}, new int[]{R.id.row_inventory_product_ID, R.id.row_inventory_name, R.id.row_inventory_date, R.id.row_inventory_quantity});
        list_inventory_emp.setAdapter(listAdapter);

        //Search Inventory Function
        search_inventory_emp.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                ((SimpleAdapter) EmployeeViewInventory.this.listAdapter).getFilter().filter(s);
                return false;
            }
        });

        //Select item from Inventory List then redirect to Update Inventory
        list_inventory_emp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(UserAccounts.this, userList.get(i).toString(),Toast.LENGTH_LONG).show();

                SharedPreferences pref = getSharedPreferences("inventory_list", MODE_PRIVATE);

                //Add information of selected item to Shared Preferences
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("inventory_ID", inventorylist.get(i).get("inventory_ID"));
                edit.putString("inventory_date", inventorylist.get(i).get("inventory_date"));
                edit.putString("inventory_quantity", inventorylist.get(i).get("inventory_quantity"));
                edit.putString("inventory_quantity_change", inventorylist.get(i).get("inventory_quantity_chang"));
                edit.putString("inventory_remark", inventorylist.get(i).get("inventory_remark"));
                edit.putString("inventory_date_updated", inventorylist.get(i).get("inventory_date_updated"));
                edit.putString("prod_name", inventorylist.get(i).get("prod_name"));
                edit.commit();

                Intent intent = new Intent(EmployeeViewInventory.this, EmployeeInventoryUpdates.class);
                startActivity(intent);

            }
        });
    }
}