package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    Spinner spin_man_view_inv_category;
    String[] categoryArray = {"None","Lumber", "Nails", "Screws", "Cement", "Gravel", "Sand", "Steel Bars", "Varnish", "Paint", "Brush/Roller", "PVC", "Special"};
    Spinner spin_man_sort_inventory;
    String[] sortInvChoices = {"None","A-Z","Z-A","Low Quantity First","High Quantity First"};
    DbManager db;
    ListView list_inventory;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_view_inventory);

        img_back_view_inventory = findViewById(R.id.img_back_view_inventory);
        img_add_inventory = findViewById(R.id.img_add_inventory);
        srch_inventory = findViewById(R.id.srch_inventory);
        spin_man_sort_inventory = findViewById(R.id.spin_man_sort_inventory);
        spin_man_view_inv_category = findViewById(R.id.spin_man_view_inv_category);
        db = new DbManager(this);

        //back to main menu
        img_back_view_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerViewInventory.this, ManagerMenu.class);
                startActivity(i);
                finish();
            }
        });

        //Adapt Spinner for Category
        ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoryArray);
        spin_man_view_inv_category.setAdapter(categoryAdapter);

        //Adapt Spinner for Sorting
        ArrayAdapter sortAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, sortInvChoices);
        spin_man_sort_inventory.setAdapter(sortAdapter);

        //redirect to Add Inventory Activity
        img_add_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerViewInventory.this, UserAddInventory.class);
                startActivity(i);
                finish();

            }
        });

        //Adapt Inventory List
        list_inventory = findViewById(R.id.list_inventory);
        ArrayList<HashMap<String, String>> inventoryList = db.getInventory();
        listAdapter = new SimpleAdapter(ManagerViewInventory.this, inventoryList, R.layout.list_row_inventory, new String[]{"inventory_ID","prod_name","inventory_date","inventory_quantity"}, new int[]{R.id.row_inventory_product_ID, R.id.row_inventory_name, R.id.row_inventory_date, R.id.row_inventory_quantity}){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get Current View
                View view = super.getView(position, convertView, parent);

                // Initialize Values
                int prod_total_quant = Integer.parseInt(db.getProductByProductName(inventoryList.get(position).get("prod_name")).get(0).get("prod_total_quantity"));
                int prod_crit_num = Integer.parseInt(db.getProductByProductName(inventoryList.get(position).get("prod_name")).get(0).get("prod_critical_num"));

                //Compare Total Product Quantity to Product Critical Number
                if(prod_total_quant <= prod_crit_num){
                    view.setBackgroundColor(Color.parseColor("#FFB6B546"));
                }else{
                    view.setBackgroundColor(Color.parseColor("#FFCCCB4C"));
                }

                return view;

            }
        };
        list_inventory.setAdapter(listAdapter);

        //Search Inventory Function
        srch_inventory.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ((SimpleAdapter) ManagerViewInventory.this.listAdapter).getFilter().filter(s);
                return false;
            }
        });

        //Select item from Inventory List then redirect to Update Inventory
        list_inventory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(UserAccounts.this, userList.get(i).toString(),Toast.LENGTH_LONG).show();

                SharedPreferences pref = getSharedPreferences("inventory_list", MODE_PRIVATE);

                //Add information of selected item to Shared Preferences
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("inventory_ID", inventoryList.get(i).get("inventory_ID"));
                edit.putString("inventory_date", inventoryList.get(i).get("inventory_date"));
                edit.putString("inventory_quantity", inventoryList.get(i).get("inventory_quantity"));
                edit.putString("inventory_quantity_change", inventoryList.get(i).get("inventory_quantity_chang"));
                edit.putString("inventory_remark", inventoryList.get(i).get("inventory_remark"));
                edit.putString("inventory_date_updated", inventoryList.get(i).get("inventory_date_updated"));
                edit.putString("prod_name", inventoryList.get(i).get("prod_name"));
                edit.commit();

                Intent intent = new Intent(ManagerViewInventory.this, UserUpdateInventory.class);
                startActivity(intent);

            }
        });

        //Filter List According to Category
        spin_man_view_inv_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String prodCategory;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //get Category Selected in the Spinner
                prodCategory = spin_man_view_inv_category.getSelectedItem().toString();

                //default Category
                if(prodCategory.equals("None")){
                    list_inventory = findViewById(R.id.list_inventory);
                    ArrayList<HashMap<String, String>> inventoryList = db.getInventory();
                    listAdapter = new SimpleAdapter(ManagerViewInventory.this, inventoryList, R.layout.list_row_inventory, new String[]{"inventory_ID","prod_name","inventory_date","inventory_quantity"}, new int[]{R.id.row_inventory_product_ID, R.id.row_inventory_name, R.id.row_inventory_date, R.id.row_inventory_quantity}){
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            // Get Current View
                            View view = super.getView(position, convertView, parent);

                            // Initialize Values
                            int prod_total_quant = Integer.parseInt(db.getProductByProductName(inventoryList.get(position).get("prod_name")).get(0).get("prod_total_quantity"));
                            int prod_crit_num = Integer.parseInt(db.getProductByProductName(inventoryList.get(position).get("prod_name")).get(0).get("prod_critical_num"));

                            //Compare Total Product Quantity to Product Critical Number
                            if(prod_total_quant <= prod_crit_num){
                                view.setBackgroundColor(Color.parseColor("#FFB6B546"));
                            }else{
                                view.setBackgroundColor(Color.parseColor("#FFCCCB4C"));
                            }

                            return view;

                        }
                    };
                    list_inventory.setAdapter(listAdapter);
                }else{
                    //Adapt Categorized Inventory to the List View
                    ArrayList<HashMap<String, String>> inventoryList = db.getCategorizedInventory(prodCategory);
                    listAdapter = new SimpleAdapter(ManagerViewInventory.this, inventoryList, R.layout.list_row_inventory, new String[]{"inventory_ID","prod_name","inventory_date","inventory_quantity"}, new int[]{R.id.row_inventory_product_ID, R.id.row_inventory_name, R.id.row_inventory_date, R.id.row_inventory_quantity}){
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            // Get Current View
                            View view = super.getView(position, convertView, parent);

                            // Initialize Values
                            int prod_total_quant = Integer.parseInt(db.getProductByProductName(inventoryList.get(position).get("prod_name")).get(0).get("prod_total_quantity"));
                            int prod_crit_num = Integer.parseInt(db.getProductByProductName(inventoryList.get(position).get("prod_name")).get(0).get("prod_critical_num"));

                            //Compare Total Product Quantity to Product Critical Number
                            if(prod_total_quant <= prod_crit_num){
                                view.setBackgroundColor(Color.parseColor("#FFB6B546"));
                            }else{
                                view.setBackgroundColor(Color.parseColor("#FFCCCB4C"));
                            }

                            return view;

                        }
                    };
                    list_inventory.setAdapter(listAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Sort List According to Chosen Option
        spin_man_sort_inventory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String invSort;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                invSort = spin_man_sort_inventory.getSelectedItem().toString();
                Log.i("CATEGORY TAG", invSort);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}