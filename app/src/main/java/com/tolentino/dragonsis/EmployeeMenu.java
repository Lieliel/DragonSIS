package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmployeeMenu extends AppCompatActivity {

    Button btn_viewinv;
    Button btn_invupd;
    Button btn_prodlist;
    Button btn_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_menu);

        btn_viewinv = findViewById(R.id.btn_viewinv);
        btn_invupd = findViewById(R.id.btn_invupd);
        btn_prodlist = findViewById(R.id.btn_prodlist);
        btn_log = findViewById(R.id.btn_log);

        //Redirect to Inventory List
        btn_viewinv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EmployeeMenu.this, EmployeeViewInventory.class);
                startActivity(i);
            }
        });

        //Redirect to Products List
        btn_prodlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EmployeeMenu.this, EmployeeViewProducts.class);
                startActivity(i);
            }
        });

        //Redirect to View Inventory
        btn_invupd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EmployeeMenu.this, UserViewInventoryUpdates.class);
                startActivity(i);
            }
        });

        //Log out
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EmployeeMenu.this, MainActivity.class);
                startActivity(i);
            }
        });




    }
}