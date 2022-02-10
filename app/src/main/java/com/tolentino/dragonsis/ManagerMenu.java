package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagerMenu extends AppCompatActivity {

    Button btn_useracc;
    Button btn_inventory;
    Button btn_products;
    Button btn_pdf;
    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_menu);

        btn_useracc = findViewById(R.id.btn_useracc);
        btn_inventory = findViewById(R.id.btn_inventory);
        btn_products = findViewById(R.id.btn_products);
        btn_pdf = findViewById(R.id.btn_pdf);
        btn_logout = findViewById(R.id.btn_logout);

        btn_useracc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerMenu.this, UserAccounts.class);
                startActivity(i);
            }
        });
        btn_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerMenu.this, ViewInventory.class);
                startActivity(i);
            }
        });
        btn_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerMenu.this, ViewProducts.class);
                startActivity(i);
            }
        });
        btn_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerMenu.this, GeneratePdfReport.class);
                startActivity(i);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerMenu.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}