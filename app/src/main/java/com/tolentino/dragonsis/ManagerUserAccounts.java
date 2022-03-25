package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ManagerUserAccounts extends AppCompatActivity {

    ImageView img_back_user_accounts;
    Button img_add_user;
    DbManager db;
    ListView list_users;
    BroadcastReceiver broadcastReceiver4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_user_accounts);

        img_back_user_accounts = findViewById(R.id.img_back_user_accounts);
        img_add_user = findViewById(R.id.img_add_user);

        db = new DbManager(this);

        //Back to Main Menu
        img_back_user_accounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerUserAccounts.this, ManagerMenu.class);
                startActivity(i);
                Intent endActivity = new Intent("finish_activity");
                sendBroadcast(endActivity);
                finish();
            }
        });

        //Redirect to Add Users Page
        img_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerUserAccounts.this, ManagerAddUser.class);
                startActivity(i);
                finish();
            }
        });

        //Adapt Users List
        list_users = findViewById(R.id.list_users);
        ArrayList<HashMap<String, String>> userList = db.getUsers();
        ListAdapter listAdapter = new SimpleAdapter(ManagerUserAccounts.this, userList, R.layout.list_row_user, new String[]{"user_name","user_password","user_type"}, new int[]{R.id.row_username, R.id.row_password, R.id.row_usertype});
        list_users.setAdapter(listAdapter);

        //Select item from User List then redirect to Update Account
        list_users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(UserAccounts.this, userList.get(i).toString(),Toast.LENGTH_LONG).show();

                SharedPreferences pref = getSharedPreferences("user_list", MODE_PRIVATE);

                //Add information of selected item to Shared Preferences
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("username", userList.get(i).get("user_name"));
                edit.putString("password", userList.get(i).get("user_password"));
                edit.putString("usertype", userList.get(i).get("user_type"));
                edit.commit();

                Intent intent = new Intent(ManagerUserAccounts.this, ManagerUpdateAccount.class);
                startActivity(intent);

            }
        });

        broadcastReceiver4 = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity_man_accounts")) {
                    finish();
                    unregisterReceiver(broadcastReceiver4);
                }
            }
        };
        registerReceiver(broadcastReceiver4, new IntentFilter("finish_activity_man_accounts"));
    }
}