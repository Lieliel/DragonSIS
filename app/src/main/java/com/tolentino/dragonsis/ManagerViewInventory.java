package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ManagerViewInventory extends AppCompatActivity {

    ImageView img_back_view_inventory;
    Button img_add_inventory;
    SearchView srch_inventory;
    BroadcastReceiver broadcastReceiverManInv;

    Spinner spin_view_inventory;
    String[] categoryArray = {"None","Lumber", "Nails", "Screws", "Cement", "Gravel", "Sand", "Steel Bars", "Varnish", "Paint", "Brush/Roller", "PVC", "Special"};
    Spinner spin_man_sort_inventory;
    String[] sortInvChoices = {"None","A-Z","Z-A","Low Quantity First","High Quantity First"};
    DbManager db;
    ListView list_inventory;
    ListAdapter listAdapter;
    ArrayList<HashMap<String, String>> inventoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_view_inventory);

        img_back_view_inventory = findViewById(R.id.img_back_view_inventory);
        img_add_inventory = findViewById(R.id.img_add_inventory);
        srch_inventory = findViewById(R.id.srch_inventory);
        spin_man_sort_inventory = findViewById(R.id.spin_sort_inventory);
        spin_view_inventory = findViewById(R.id.spin_view_inventory);
        db = new DbManager(this);

        //back to main menu
        img_back_view_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Adapt Spinner for Category
        ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoryArray);
        spin_view_inventory.setAdapter(categoryAdapter);

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
        inventoryList = db.getInventory();
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
                    ((TextView)view.findViewById(R.id.row_inventory_product_ID)).setTextColor(Color.parseColor("#FFFFFFFF"));
                    ((TextView)view.findViewById(R.id.row_inventory_name)).setTextColor(Color.parseColor("#FFFFFFFF"));
                    ((TextView)view.findViewById(R.id.row_inventory_date)).setTextColor(Color.parseColor("#FFFFFFFF"));
                    ((TextView)view.findViewById(R.id.row_inventory_quantity)).setTextColor(Color.parseColor("#FFFFFFFF"));
                    view.setBackgroundColor(Color.parseColor("#FFF45B69"));
                }else{
                    view.setBackgroundColor(Color.parseColor("#00FFFFFF"));
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
                onInventorySearch(s);
                return false;
            }
        });

        //Select item from Inventory List then redirect to Update Inventory
        list_inventory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
        spin_view_inventory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String prodCategory;
            String invSort;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //get Category Selected in the Spinner
                prodCategory = spin_view_inventory.getSelectedItem().toString();
                prodCategory = spin_view_inventory.getSelectedItem().toString();
                invSort = spin_man_sort_inventory.getSelectedItem().toString();
                OnCategoryOrSortSelected(prodCategory, invSort);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //Sort List According to Chosen Option
        spin_man_sort_inventory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String prodCategory;
            String invSort;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                prodCategory = spin_view_inventory.getSelectedItem().toString();
                invSort = spin_man_sort_inventory.getSelectedItem().toString();
                OnCategoryOrSortSelected(prodCategory, invSort);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        broadcastReceiverManInv = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity_man_view_inventory")) {
                    finish();
                    unregisterReceiver(broadcastReceiverManInv);
                }
            }
        };
        registerReceiver(broadcastReceiverManInv, new IntentFilter("finish_activity_man_view_inventory"));
    }

    public void OnCategoryOrSortSelected(String prodCategory, String invSort){

        //default Category
        if(prodCategory.equals("None")){
            list_inventory = findViewById(R.id.list_inventory);
            inventoryList = db.getSortedInventory(invSort);

            listAdapter = new SimpleAdapter(ManagerViewInventory.this, inventoryList, R.layout.list_row_inventory, new String[]{"inventory_ID","prod_name","inventory_date","inventory_quantity"}, new int[]{R.id.row_inventory_product_ID, R.id.row_inventory_name, R.id.row_inventory_date, R.id.row_inventory_quantity}){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    // Get Current View
                    View view = super.getView(position, convertView, parent);

                    boolean isProdCrit = db.checkProductCritical(db.getProductByProductName(inventoryList.get(position).get("prod_name")).get(0).get("prod_name"));
                    //Compare Total Product Quantity to Product Critical Number
                    if(isProdCrit){
                        ((TextView)view.findViewById(R.id.row_inventory_product_ID)).setTextColor(Color.parseColor("#FFFFFFFF"));
                        ((TextView)view.findViewById(R.id.row_inventory_name)).setTextColor(Color.parseColor("#FFFFFFFF"));
                        ((TextView)view.findViewById(R.id.row_inventory_date)).setTextColor(Color.parseColor("#FFFFFFFF"));
                        ((TextView)view.findViewById(R.id.row_inventory_quantity)).setTextColor(Color.parseColor("#FFFFFFFF"));
                        view.setBackgroundColor(Color.parseColor("#FFF45B69"));
                    }else{
                        view.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                    }

                    return view;
                }
            };
            list_inventory.setAdapter(listAdapter);
        }else{
            //Adapt Categorized Inventory to the List View
            inventoryList = db.getCategorizedInventory(prodCategory, invSort);
            Log.i("CATEGORY TAG", String.valueOf(inventoryList.toString()));
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
                                ((TextView)view.findViewById(R.id.row_inventory_product_ID)).setTextColor(Color.parseColor("#FFFFFFFF"));
                                ((TextView)view.findViewById(R.id.row_inventory_name)).setTextColor(Color.parseColor("#FFFFFFFF"));
                                ((TextView)view.findViewById(R.id.row_inventory_date)).setTextColor(Color.parseColor("#FFFFFFFF"));
                                ((TextView)view.findViewById(R.id.row_inventory_quantity)).setTextColor(Color.parseColor("#FFFFFFFF"));
                                view.setBackgroundColor(Color.parseColor("#FFF45B69"));
                            }else{
                                view.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                            }

                    return view;

                }
            };
            list_inventory.setAdapter(listAdapter);
        }

    }

    public void onInventorySearch(String q) {

        //default Category
        list_inventory = findViewById(R.id.list_inventory);
        inventoryList = db.getSearchedInventory(q);

        listAdapter = new SimpleAdapter(ManagerViewInventory.this, inventoryList, R.layout.list_row_inventory, new String[]{"inventory_ID", "prod_name", "inventory_date", "inventory_quantity"}, new int[]{R.id.row_inventory_product_ID, R.id.row_inventory_name, R.id.row_inventory_date, R.id.row_inventory_quantity}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get Current View
                View view = super.getView(position, convertView, parent);

                // Initialize Values
                int prod_total_quant = Integer.parseInt(db.getProductByProductName(inventoryList.get(position).get("prod_name")).get(0).get("prod_total_quantity"));
                int prod_crit_num = Integer.parseInt(db.getProductByProductName(inventoryList.get(position).get("prod_name")).get(0).get("prod_critical_num"));

                //Compare Total Product Quantity to Product Critical Number
                if (prod_total_quant <= prod_crit_num) {
                    ((TextView) view.findViewById(R.id.row_inventory_product_ID)).setTextColor(Color.parseColor("#FFFFFFFF"));
                    ((TextView) view.findViewById(R.id.row_inventory_name)).setTextColor(Color.parseColor("#FFFFFFFF"));
                    ((TextView) view.findViewById(R.id.row_inventory_date)).setTextColor(Color.parseColor("#FFFFFFFF"));
                    ((TextView) view.findViewById(R.id.row_inventory_quantity)).setTextColor(Color.parseColor("#FFFFFFFF"));
                    view.setBackgroundColor(Color.parseColor("#FFF45B69"));
                } else {
                    view.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                }

                return view;
            }
        };
        list_inventory.setAdapter(listAdapter);
    }
}

