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
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

public class UserAccounts extends AppCompatActivity {

    ImageView img_back_user_accounts;
    SearchView srch_user;
    ImageView img_add_user;
    Spinner spin_sort_user;
    DbManager db;
    ListView list_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_accounts);

        img_back_user_accounts = findViewById(R.id.img_back_user_accounts);
        srch_user = findViewById(R.id.srch_user);
        img_add_user = findViewById(R.id.img_add_user);
        spin_sort_user = findViewById(R.id.spin_sort_user);

        db = new DbManager(this);
        list_users = findViewById(R.id.list_users);
        ArrayList<HashMap<String, String>> userList = db.getUsers();

        img_back_user_accounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserAccounts.this, ManagerMenu.class);
                startActivity(i);

            }
        });

        img_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserAccounts.this, AddUser.class);
                startActivity(i);

            }
        });

        ListAdapter listAdapter = new SimpleAdapter(UserAccounts.this, userList, R.layout.list_row_user, new String[]{"user_name","user_password","user_email","user_type"}, new int[]{R.id.row_user, R.id.row_password, R.id.row_email, R.id.row_usertype});
        list_users.setAdapter(listAdapter);
    }
}