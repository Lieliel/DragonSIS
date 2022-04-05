package com.tolentino.dragonsis;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EmployeeMenu extends AppCompatActivity {

    Button btn_viewinv;
    Button btn_invupd;
    Button btn_prodlist;
    Button btn_log;
    BroadcastReceiver empbroadcastReceiver1;
    TextView user_name_emp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_menu);

        btn_viewinv = findViewById(R.id.btn_viewinv);
        btn_invupd = findViewById(R.id.btn_invupd);
        btn_prodlist = findViewById(R.id.btn_prodlist);
        btn_log = findViewById(R.id.btn_log);

        user_name_emp = (TextView) findViewById(R.id.user_name_emp);
        Intent intentName = getIntent();
        String userName = intentName.getStringExtra("user_name");
        user_name_emp.setText(userName);

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
                Intent i = new Intent(EmployeeMenu.this, EmployeeInventoryUpdates.class);
                startActivity(i);
            }
        });

        //Log out
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EmployeeMenu.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        empbroadcastReceiver1 = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("emp_finish_activity")) {
                    finish();
                    unregisterReceiver(empbroadcastReceiver1);
                }
            }
        };
        registerReceiver(empbroadcastReceiver1, new IntentFilter("emp_finish_activity"));
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        EmployeeMenu.super.onBackPressed();
                    }

                }).create().show();
    }
}