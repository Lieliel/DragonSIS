package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class ManagerViewSales extends AppCompatActivity {

    ImageView btn_man_view_sales_back;
    ListView list_man_view_sales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_view_sales);

        btn_man_view_sales_back = findViewById(R.id.btn_man_view_sales_back);
        list_man_view_sales = findViewById(R.id.list_man_view_sales);

        btn_man_view_sales_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerViewSales.this, ManagerMenu.class);
                startActivity(i);
                finish();
            }
        });
    }
}