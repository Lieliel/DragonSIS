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
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ManagerViewProducts extends AppCompatActivity {

    ImageView img_back_view_products;
    Button img_add_product;
    SearchView srch_product;
    BroadcastReceiver broadcastReceiver3;

    Spinner spin_man_view_prod_category;
    String[] categoryArray = {"None","Lumber", "Nails", "Screws", "Cement", "Gravel", "Sand", "Steel Bars", "Varnish", "Paint", "Brush/Roller", "PVC", "Special"};
    Spinner spin_man_sort_products;
    String[] sortProdChoices = {"None","A-Z","Z-A","Low Quantity First","High Quantity First"};

    DbManager db;
    ListView list_products;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_view_products);

        img_back_view_products = findViewById(R.id.img_back_view_products);
        srch_product = findViewById(R.id.srch_product);
        img_add_product = findViewById(R.id.img_add_product);
        spin_man_view_prod_category = findViewById(R.id.spin_prod_category);
        spin_man_sort_products = findViewById(R.id.spin_prod_sort);
        db = new DbManager(this);

        //Back to Manager Menu
        img_back_view_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerViewProducts.this, ManagerMenu.class);
                startActivity(i);
                Intent endActivity = new Intent("finish_activity");
                sendBroadcast(endActivity);
                finish();
            }
        });

        /*FloatingActionButton fab = findViewById(R.id.float_add_product);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerViewProducts.this, ManagerAddProduct.class);
                startActivity(i);
            }
        });*/

        //Redirect to Add Products Page
        img_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerViewProducts.this, ManagerAddProduct.class);
                startActivity(i);
            }
        });

        //Adapt Spinner for Category
        ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoryArray);
        spin_man_view_prod_category.setAdapter(categoryAdapter);

        //Adapt Spinner for Sorting
        ArrayAdapter sortAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, sortProdChoices);
        spin_man_sort_products.setAdapter(sortAdapter);

        //Adapt Products List
        list_products = findViewById(R.id.list_products);
        ArrayList<HashMap<String, String>> productList = db.getProducts();
        listAdapter = new SimpleAdapter(ManagerViewProducts.this, productList, R.layout.list_row_product, new String[]{"prod_name"/*,"prod_total_quantity"*/,"prod_price","prod_category"}, new int[]{R.id.row_product_name, /*R.id.row_product_description,*/ R.id.row_product_price, R.id.row_product_category}){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get Current View
                View view = super.getView(position, convertView, parent);

                // Initialize Values
                int prod_total_quant = Integer.parseInt(db.getProductByProductName(productList.get(position).get("prod_name")).get(0).get("prod_total_quantity"));
                int prod_crit_num = Integer.parseInt(db.getProductByProductName(productList.get(position).get("prod_name")).get(0).get("prod_critical_num"));

                //Compare Total Product Quantity to Product Critical Number
                if(prod_total_quant <= prod_crit_num){
                    ((TextView)view.findViewById(R.id.row_product_name)).setTextColor(Color.parseColor("#FFFFFFFF"));
                    ((TextView)view.findViewById(R.id.row_product_price)).setTextColor(Color.parseColor("#FFFFFFFF"));
                    ((TextView)view.findViewById(R.id.row_product_category)).setTextColor(Color.parseColor("#FFFFFFFF"));
                    view.setBackgroundColor(Color.parseColor("#FFF45B69"));
                }else{
                    view.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                }

                return view;

            }
        };
        list_products.setAdapter(listAdapter);

        //Search Product Function
        srch_product.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ((SimpleAdapter) ManagerViewProducts.this.listAdapter).getFilter().filter(s);
                return false;
            }
        });

        //Select item from Product List then redirect to Update Products
        list_products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                SharedPreferences pref = getSharedPreferences("product_list", MODE_PRIVATE);

                //Add information of selected item to Shared Preferences
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("prod_id", productList.get(i).get("prod_ID"));
                edit.putString("prod_name", productList.get(i).get("prod_name"));
                edit.putString("prod_critical_num", productList.get(i).get("prod_critical_num"));
                edit.putString("prod_total_quantity", productList.get(i).get("prod_total_quantity"));
                edit.putString("prod_price", productList.get(i).get("prod_price"));
                edit.putString("prod_category", productList.get(i).get("prod_category"));
                edit.commit();

                Intent intent = new Intent(ManagerViewProducts.this, ManagerUpdateProducts.class);
                startActivity(intent);

            }
        });

        //Filter List According to Category
        spin_man_view_prod_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String prodCategory;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //get Category Selected in the Spinner
                prodCategory = spin_man_view_prod_category.getSelectedItem().toString();

                //default Category
                if(prodCategory.equals("None")){
                    list_products = findViewById(R.id.list_products);
                    ArrayList<HashMap<String, String>> productList = db.getProducts();
                    listAdapter = new SimpleAdapter(ManagerViewProducts.this, productList, R.layout.list_row_product, new String[]{"prod_name"/*,"prod_total_quantity"*/,"prod_price","prod_category"}, new int[]{R.id.row_product_name, /*R.id.row_product_description,*/ R.id.row_product_price, R.id.row_product_category}){
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            // Get Current View
                            View view = super.getView(position, convertView, parent);

                            // Initialize Values
                            int prod_total_quant = Integer.parseInt(db.getProductByProductName(productList.get(position).get("prod_name")).get(0).get("prod_total_quantity"));
                            int prod_crit_num = Integer.parseInt(db.getProductByProductName(productList.get(position).get("prod_name")).get(0).get("prod_critical_num"));

                            //Compare Total Product Quantity to Product Critical Number
                            if(prod_total_quant <= prod_crit_num){
                                ((TextView)view.findViewById(R.id.row_product_name)).setTextColor(Color.parseColor("#FFFFFFFF"));
                                ((TextView)view.findViewById(R.id.row_product_price)).setTextColor(Color.parseColor("#FFFFFFFF"));
                                ((TextView)view.findViewById(R.id.row_product_category)).setTextColor(Color.parseColor("#FFFFFFFF"));
                                view.setBackgroundColor(Color.parseColor("#FFF45B69"));
                            }else{
                                view.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                            }

                            return view;

                        }
                    };
                    list_products.setAdapter(listAdapter);
                }else{
                    //Adapt Categorized Products to the List View
                    ArrayList<HashMap<String, String>> productList = db.getCategorizedProducts(prodCategory);
                    listAdapter = new SimpleAdapter(ManagerViewProducts.this, productList, R.layout.list_row_product, new String[]{"prod_name"/*,"prod_total_quantity"*/,"prod_price","prod_category"}, new int[]{R.id.row_product_name, /*R.id.row_product_description,*/ R.id.row_product_price, R.id.row_product_category}){
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            // Get Current View
                            View view = super.getView(position, convertView, parent);

                            // Initialize Values
                            int prod_total_quant = Integer.parseInt(db.getProductByProductName(productList.get(position).get("prod_name")).get(0).get("prod_total_quantity"));
                            int prod_crit_num = Integer.parseInt(db.getProductByProductName(productList.get(position).get("prod_name")).get(0).get("prod_critical_num"));

                            //Log.i("TOTALNUM TAG", String.valueOf(prod_total_quant));
                            //Log.i("CRITNUM TAG", String.valueOf(prod_crit_num));

                            //Compare Total Product Quantity to Product Critical Number
                            if(prod_total_quant <= prod_crit_num){
                                ((TextView)view.findViewById(R.id.row_product_name)).setTextColor(Color.parseColor("#FFFFFFFF"));
                                ((TextView)view.findViewById(R.id.row_product_price)).setTextColor(Color.parseColor("#FFFFFFFF"));
                                ((TextView)view.findViewById(R.id.row_product_category)).setTextColor(Color.parseColor("#FFFFFFFF"));
                                view.setBackgroundColor(Color.parseColor("#FFF45B69"));
                            }else{
                                view.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                            }

                            return view;

                        }
                    };
                    list_products.setAdapter(listAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Sort List According to Chosen Option
        spin_man_sort_products.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String prodSort;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                prodSort = spin_man_sort_products.getSelectedItem().toString();
                Log.i("CATEGORY TAG", prodSort);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        broadcastReceiver3 = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity_man_view_products")) {
                    finish();
                    unregisterReceiver(broadcastReceiver3);
                }
            }
        };
        registerReceiver(broadcastReceiver3, new IntentFilter("finish_activity_man_view_products"));
    }
}