package com.tolentino.dragonsis;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
    Button btn_man_trans;

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
        btn_man_trans = findViewById(R.id.btn_man_trans);

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
                finish();
            }
        });

        //Redirect to View Inventory Updates
        btn_viewinvupd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerMenu.this, ManagerInventoryUpdates.class);
                startActivity(i);
            }
        });

        btn_man_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerMenu.this, ManagerViewSales.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        ManagerMenu.super.onBackPressed();
                    }

                }).create().show();
    }
}