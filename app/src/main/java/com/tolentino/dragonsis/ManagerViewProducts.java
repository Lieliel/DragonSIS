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

public class ManagerViewProducts extends AppCompatActivity {

    ImageView img_back_view_products;
    ImageView img_add_product;
    SearchView srch_product;

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
        spin_man_view_prod_category = findViewById(R.id.spin_man_view_prod_category);
        spin_man_sort_products = findViewById(R.id.spin_man_sort_products);
        db = new DbManager(this);

        //Back to Manager Menu
        img_back_view_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerViewProducts.this, ManagerMenu.class);
                startActivity(i);
                finish();
            }
        });

        //Redirect to Add Products Page
        img_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerViewProducts.this, UserAddProduct.class);
                startActivity(i);
                finish();

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
                    view.setBackgroundColor(Color.parseColor("#FFB6B546"));
                }else{
                    view.setBackgroundColor(Color.parseColor("#FFCCCB4C"));
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

                Intent intent = new Intent(ManagerViewProducts.this, UserUpdateProducts.class);
                startActivity(intent);

            }
        });

        //Filter List According to Category
        spin_man_view_prod_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String prodCategory;
            String invSort;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //get Category Selected in the Spinner
                prodCategory = spin_man_view_prod_category.getSelectedItem().toString();
                invSort = spin_man_sort_products.getSelectedItem().toString();
                OnCategoryOrSortSelected(prodCategory, invSort);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Sort List According to Chosen Option
        spin_man_sort_products.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String prodCategory;
            String invSort;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                prodCategory = spin_man_view_prod_category.getSelectedItem().toString();
                invSort = spin_man_sort_products.getSelectedItem().toString();
                OnCategoryOrSortSelected(prodCategory, invSort);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void OnCategoryOrSortSelected(String prodCategory, String invSort){

        //default Category
        if(prodCategory.equals("None")){
            list_products = findViewById(R.id.list_products);
            ArrayList<HashMap<String, String>> productList = db.getSortedProduct(invSort);
            Log.i("CATEG PROD TAG", productList.toString());

            listAdapter = new SimpleAdapter(ManagerViewProducts.this, productList, R.layout.list_row_product, new String[]{"prod_name"/*,"prod_total_quantity"*/,"prod_price","prod_category"}, new int[]{R.id.row_product_name, /*R.id.row_product_description,*/ R.id.row_product_price, R.id.row_product_category}) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    // Get Current View
                    View view = super.getView(position, convertView, parent);

                    // Initialize Values
                    int prod_total_quant = Integer.parseInt(db.getProductByProductName(productList.get(position).get("prod_name")).get(0).get("prod_total_quantity"));
                    int prod_crit_num = Integer.parseInt(db.getProductByProductName(productList.get(position).get("prod_name")).get(0).get("prod_critical_num"));

                    //Compare Total Product Quantity to Product Critical Number
                    if (prod_total_quant <= prod_crit_num) {
                        view.setBackgroundColor(Color.parseColor("#FFB6B546"));
                    } else {
                        view.setBackgroundColor(Color.parseColor("#FFCCCB4C"));
                    }

                    return view;

                }
            };
            list_products.setAdapter(listAdapter);
        }else{
            //Adapt Categorized Products to the List View
            ArrayList<HashMap<String, String>> productList = db.getCategorizedProduct(prodCategory, invSort);
            Log.i("CATEG PROD TAG", productList.toString());
            listAdapter = new SimpleAdapter(ManagerViewProducts.this, productList, R.layout.list_row_product, new String[]{"prod_name"/*,"prod_total_quantity"*/,"prod_price","prod_category"}, new int[]{R.id.row_product_name, /*R.id.row_product_description,*/ R.id.row_product_price, R.id.row_product_category}){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    // Get Current View
                    View view = super.getView(position, convertView, parent);

                    // Initialize Values
                    int prod_total_quant = Integer.parseInt(db.getProductByProductName(productList.get(position).get("prod_name")).get(0).get("prod_total_quantity"));
                    int prod_crit_num = Integer.parseInt(db.getProductByProductName(productList.get(position).get("prod_name")).get(0).get("prod_critical_num"));

                    //Compare Total Product Quantity to Product Critical Number
                    if (prod_total_quant <= prod_crit_num) {
                        view.setBackgroundColor(Color.parseColor("#FFB6B546"));
                    } else {
                        view.setBackgroundColor(Color.parseColor("#FFCCCB4C"));
                    }

                    return view;

                }
            };
            list_products.setAdapter(listAdapter);
        }

    }

}
