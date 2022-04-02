package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class ManagerViewSales extends AppCompatActivity {

    ImageView btn_man_view_sales_back;
    ListView list_man_view_sales;
    DbManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_view_sales);

        btn_man_view_sales_back = findViewById(R.id.btn_man_view_sales_back);
        db = new DbManager(this);

        //Back to Manager Menu
        btn_man_view_sales_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerViewSales.this, ManagerMenu.class);
                startActivity(i);
                finish();
            }
        });

        //Adapt Sales List
        list_man_view_sales = findViewById(R.id.list_man_view_sales);
        ArrayList<String> salesList = db.getSalesMessage();
        Collections.reverse(salesList);
        ArrayAdapter listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, salesList);
        list_man_view_sales.setAdapter(listAdapter);
    }
}