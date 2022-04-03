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

import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeViewProducts extends AppCompatActivity {

    ImageView img_back_emp_products;
    //Button img_add_product_emp = findViewById(R.id.img_add_product_emp);
    SearchView search_product_emp;

    DbManager db;
    ListView list_products;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_view_products);

        img_back_emp_products = findViewById(R.id.img_back_emp_products);
        search_product_emp = findViewById(R.id.search_product_emp);

        db = new DbManager(this);

        //Back to Employee Menu
        img_back_emp_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EmployeeViewProducts.this, EmployeeMenu.class);
                Intent endActivity = new Intent("emp_finish_activity");
                sendBroadcast(endActivity);
                startActivity(i);
                finish();
            }
        });

        //Adapt Products List
        list_products = findViewById(R.id.list_products);
        ArrayList<HashMap<String, String>> productList = db.getProducts();
        listAdapter = new SimpleAdapter(EmployeeViewProducts.this, productList, R.layout.list_row_product, new String[]{"prod_name"/*,"prod_total_quantity"*/,"prod_price","prod_category"}, new int[]{R.id.row_product_name, /*R.id.row_product_description,*/ R.id.row_product_price, R.id.row_product_category});
        list_products.setAdapter(listAdapter);

        //Search Product Function
        search_product_emp.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ((SimpleAdapter) EmployeeViewProducts.this.listAdapter).getFilter().filter(s);
                return false;
            }
        });



    }
}