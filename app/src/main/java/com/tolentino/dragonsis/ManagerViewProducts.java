package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
            }
        });

        //Redirect to Add Products Page
        img_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerViewProducts.this, ManagerAddProduct.class);
                startActivity(i);

            }
        });

        //Adapt Products List
        list_products = findViewById(R.id.list_products);
        ArrayList<HashMap<String, String>> productlist = db.getProducts();
        listAdapter = new SimpleAdapter(ManagerViewProducts.this, productlist, R.layout.list_row_product, new String[]{"prod_name","prod_description","prod_price","prod_category"}, new int[]{R.id.row_product_name, R.id.row_product_description, R.id.row_product_price, R.id.row_product_category});
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
    }
}