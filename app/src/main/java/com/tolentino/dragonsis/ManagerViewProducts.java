package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ManagerViewProducts extends AppCompatActivity {

    ImageView img_back_view_products;
    ImageView img_add_product;
    SearchView srch_product;

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
                Intent i = new Intent(ManagerViewProducts.this, ManagerAddProduct.class);
                startActivity(i);
                finish();

            }
        });

        //Adapt Products List
        list_products = findViewById(R.id.list_products);
        ArrayList<HashMap<String, String>> productList = db.getProducts();
        listAdapter = new SimpleAdapter(ManagerViewProducts.this, productList, R.layout.list_row_product, new String[]{"prod_name","prod_description","prod_price","prod_category"}, new int[]{R.id.row_product_name, R.id.row_product_description, R.id.row_product_price, R.id.row_product_category});
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
                //Toast.makeText(UserAccounts.this, userList.get(i).toString(),Toast.LENGTH_LONG).show();

                SharedPreferences pref = getSharedPreferences("product_list", MODE_PRIVATE);

                //Add information of selected item to Shared Preferences
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("prod_id", productList.get(i).get("prod_ID"));
                edit.putString("prod_name", productList.get(i).get("prod_name"));
                edit.putString("prod_critical_num", productList.get(i).get("prod_critical_num"));
                edit.putString("prod_description", productList.get(i).get("prod_description"));
                edit.putString("prod_price", productList.get(i).get("prod_price"));
                edit.putString("prod_category", productList.get(i).get("prod_category"));
                edit.commit();

                Intent intent = new Intent(ManagerViewProducts.this, ManagerUpdateProducts.class);
                startActivity(intent);

            }
        });
    }
}