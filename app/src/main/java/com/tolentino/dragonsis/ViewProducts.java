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

public class ViewProducts extends AppCompatActivity {

    ImageView img_back_view_products;
    SearchView srch_product;
    ImageView img_add_product;
    DbManager db;
    ListView list_products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products2);

        img_back_view_products = findViewById(R.id.img_back_view_products);
        srch_product = findViewById(R.id.srch_product);
        img_add_product = findViewById(R.id.img_add_product);

        db = new DbManager(this);

        img_back_view_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewProducts.this, ManagerMenu.class);
                startActivity(i);
            }
        });

        //Redirect to Add Products Page
        img_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewProducts.this, AddProduct.class);
                startActivity(i);

            }
        });

        //Adapt Products List
        list_products = findViewById(R.id.list_products);
        ArrayList<HashMap<String, String>> productlist = db.getProducts();
        ListAdapter listAdapter = new SimpleAdapter(ViewProducts.this, productlist, R.layout.list_row_product, new String[]{"prod_name","prod_description","prod_price","prod_category"}, new int[]{R.id.row_product_name, R.id.row_product_description, R.id.row_product_price, R.id.row_product_category});
        list_products.setAdapter(listAdapter);

    }
}