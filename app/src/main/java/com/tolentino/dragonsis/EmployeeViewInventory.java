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

public class EmployeeViewInventory extends AppCompatActivity {

    ImageView img_back_emp_view_inventory;
    Button img_emp_add_inv;
    SearchView search_emp_inv;
    Spinner spin_emp_inv_category;
    String[] categoryArray = {"None","Lumber", "Nails", "Screws", "Cement", "Gravel", "Sand", "Steel Bars", "Varnish", "Paint", "Brush/Roller", "PVC", "Special"};
    Spinner spin_emp_inv_sort;
    String[] sortInvChoices = {"None","A-Z","Z-A","Low Quantity First","High Quantity First"};
    ListView list_emp_view_inv;

    DbManager db;
    ListAdapter listAdapter;
    ArrayList<HashMap<String, String>> inventoryList;
    BroadcastReceiver broadcastReceiverEmpInv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_view_inventory);

        img_back_emp_view_inventory = findViewById(R.id.img_back_emp_view_inventory);
        img_emp_add_inv = findViewById(R.id.img_emp_add_inv);
        search_emp_inv = findViewById(R.id.search_emp_inv);
        spin_emp_inv_category = findViewById(R.id.spin_emp_inv_category);
        spin_emp_inv_sort = findViewById(R.id.spin_emp_inv_sort);
        list_emp_view_inv = findViewById(R.id.list_emp_view_inv);
        db = new DbManager(this);

        //back to main menu
        img_back_emp_view_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Adapt Spinner for Category
        ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoryArray);
        spin_emp_inv_category.setAdapter(categoryAdapter);

        //Adapt Spinner for Sorting
        ArrayAdapter sortAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, sortInvChoices);
        spin_emp_inv_sort.setAdapter(sortAdapter);

        //redirect to Add Inventory Activity
        img_emp_add_inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EmployeeViewInventory.this, UserAddInventory.class);
                startActivity(i);
            }
        });

        //Adapt Inventory List
        list_emp_view_inv = findViewById(R.id.list_emp_view_inv);
        inventoryList = db.getInventory();
        listAdapter = new SimpleAdapter(EmployeeViewInventory.this, inventoryList, R.layout.list_row_inventory, new String[]{"inventory_ID","prod_name","inventory_date","inventory_quantity"}, new int[]{R.id.row_inventory_product_ID, R.id.row_inventory_name, R.id.row_inventory_date, R.id.row_inventory_quantity});
        list_emp_view_inv.setAdapter(listAdapter);

        //Search Inventory Function
        search_emp_inv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        list_emp_view_inv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                Intent intent = new Intent(EmployeeViewInventory.this, UserUpdateInventory.class);
                startActivity(intent);

            }
        });

        //Filter List According to Category
        spin_emp_inv_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String prodCategory;
            String invSort;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //get Category Selected in the Spinner
                prodCategory = spin_emp_inv_category.getSelectedItem().toString();
                invSort = spin_emp_inv_sort.getSelectedItem().toString();
                OnCategoryOrSortSelected(prodCategory, invSort);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //Sort List According to Chosen Option
        spin_emp_inv_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String prodCategory;
            String invSort;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                prodCategory = spin_emp_inv_category.getSelectedItem().toString();
                invSort = spin_emp_inv_sort.getSelectedItem().toString();
                OnCategoryOrSortSelected(prodCategory, invSort);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        broadcastReceiverEmpInv = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity_emp_view_inventory")) {
                    finish();
                    unregisterReceiver(broadcastReceiverEmpInv);
                }
            }
        };
        registerReceiver(broadcastReceiverEmpInv, new IntentFilter("finish_activity_emp_view_inventory"));
    }

    public void OnCategoryOrSortSelected(String prodCategory, String invSort){

        //default Category
        if(prodCategory.equals("None")){
            list_emp_view_inv = findViewById(R.id.list_emp_view_inv);
            inventoryList = db.getSortedInventory(invSort);

            listAdapter = new SimpleAdapter(EmployeeViewInventory.this, inventoryList, R.layout.list_row_inventory, new String[]{"inventory_ID","prod_name","inventory_date","inventory_quantity"}, new int[]{R.id.row_inventory_product_ID, R.id.row_inventory_name, R.id.row_inventory_date, R.id.row_inventory_quantity}){
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
            list_emp_view_inv.setAdapter(listAdapter);
        }else{
            //Adapt Categorized Inventory to the List View
            inventoryList = db.getCategorizedInventory(prodCategory, invSort);
            Log.i("CATEGORY TAG", String.valueOf(inventoryList.toString()));
            listAdapter = new SimpleAdapter(EmployeeViewInventory.this, inventoryList, R.layout.list_row_inventory, new String[]{"inventory_ID","prod_name","inventory_date","inventory_quantity"}, new int[]{R.id.row_inventory_product_ID, R.id.row_inventory_name, R.id.row_inventory_date, R.id.row_inventory_quantity}){
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
            list_emp_view_inv.setAdapter(listAdapter);
        }

    }

    public void onInventorySearch(String q) {

        //default Category
        list_emp_view_inv = findViewById(R.id.list_emp_view_inv);
        inventoryList = db.getSearchedInventory(q);

        listAdapter = new SimpleAdapter(EmployeeViewInventory.this, inventoryList, R.layout.list_row_inventory, new String[]{"inventory_ID", "prod_name", "inventory_date", "inventory_quantity"}, new int[]{R.id.row_inventory_product_ID, R.id.row_inventory_name, R.id.row_inventory_date, R.id.row_inventory_quantity}) {
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
        list_emp_view_inv.setAdapter(listAdapter);
    }

}