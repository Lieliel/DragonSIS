package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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

        list_users = findViewById(R.id.list_users);
        ArrayList<HashMap<String, String>> userList = db.getUsers();
        ListAdapter listAdapter = new SimpleAdapter(UserAccounts.this, userList, R.layout.list_row_user, new String[]{"user_name","user_password","user_type","user_email"}, new int[]{R.id.row_user, R.id.row_password, R.id.row_usertype, R.id.row_email});
        list_users.setAdapter(listAdapter);

        list_users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(UserAccounts.this, userList.get(i).toString(),Toast.LENGTH_LONG).show();

                SharedPreferences pref = getSharedPreferences("user_list", MODE_PRIVATE);

                SharedPreferences.Editor edit = pref.edit();
                edit.putString("username", userList.get(i).get("user_name"));
                edit.putString("password", userList.get(i).get("user_password"));
                edit.putString("usertype", userList.get(i).get("user_type"));
                edit.putString("email", userList.get(i).get("user_email"));
                edit.commit();

                Intent intent = new Intent(UserAccounts.this, UpdateAccount.class);
                startActivity(intent);

            }
        });
    }
}