package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagerMenu extends AppCompatActivity {

    Button btn_useraccounts;
    Button btn_inventory;
    Button btn_products;
    Button btn_pdf;
    Button btn_logout;
    Button btn_viewinvupd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_menu);

        btn_useraccounts = findViewById(R.id.btn_useraccounts);
        btn_inventory = findViewById(R.id.btn_inventory);
        btn_products = findViewById(R.id.btn_products);
        btn_pdf = findViewById(R.id.btn_pdf);
        btn_logout = findViewById(R.id.btn_logout);
        btn_viewinvupd = findViewById(R.id.btn_viewinvupd);

        //Redirect to User Accounts
        btn_useraccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerMenu.this, ManagerUserAccounts.class);
                startActivity(i);
            }
        });

        //Redirect to View Inventory
        btn_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerMenu.this, ManagerViewInventory.class);
                startActivity(i);
            }
        });

        //Redirect to Products
        btn_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerMenu.this, ManagerViewProducts.class);
                startActivity(i);
            }
        });

        //Redirect to Generate Pdf
        btn_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerMenu.this, ManagerGeneratePdfReport.class);
                startActivity(i);
            }
        });

        //Redirect to Logout
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerMenu.this, MainActivity.class);
                startActivity(i);
            }
        });

        //Redirect to View Inventory Updates
        btn_viewinvupd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerMenu.this, UserViewInventoryUpdates.class);
                startActivity(i);
            }
        });



    }
}